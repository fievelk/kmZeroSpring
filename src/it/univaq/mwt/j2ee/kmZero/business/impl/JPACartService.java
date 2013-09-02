package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.ResponseCarts;
import it.univaq.mwt.j2ee.kmZero.business.model.Cart;
import it.univaq.mwt.j2ee.kmZero.business.model.CartLine;
import it.univaq.mwt.j2ee.kmZero.business.model.Feedback;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.Rating;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.business.service.CartService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JPACartService implements CartService{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional
	public Cart createCart(String address, long id_product, int quantity, User user) throws BusinessException {
		Product p = em.find(Product.class, id_product);
		CartLine cl = new CartLine();
		cl.setProduct(p);
		cl.setQuantity(quantity);
		cl.setLineTotal(p.getPrice().multiply(new BigDecimal(quantity)));
		
		Cart c = new Cart();
		Collection<CartLine> cls = new ArrayList<CartLine>();
		cl.setCart(c);
		cls.add(cl);
		c.setName(user.getName());
		c.setSurname(user.getSurname());
		c.setUser(user);
		c.setAddress(address);
		c.setCartLines(cls);
		c.setCreated(new Date());
		
		em.persist(cl);
		em.persist(c);
		
		return c;
	}

	@Override
	@Transactional
	public Cart addCartLine(long id_product, int quantity, User user) throws BusinessException {
		Product p = em.find(Product.class, id_product);
		CartLine cl = null;
		Cart c = null;
		Collection<CartLine> cls = null;
		boolean clExist = false;
		
    	// Carrello esiste
    	Query cartQuery = em.createQuery("Select c FROM Cart c WHERE c.user = :user" +
    			" ORDER BY c.created DESC", Cart.class).setMaxResults(1);
    	cartQuery.setParameter("user", user);
    	c = (Cart)cartQuery.getSingleResult();
    	cls = c.getCartLines();
    	Iterator<CartLine> i = cls.iterator();
    	// ciclo la collection per vedere se quella cartline c'e' gia' oppure no
    	while (i.hasNext() && !clExist){
    		CartLine temp = i.next();
    		if (temp.getProduct().getId() == id_product){
    			cl = temp;
    			clExist = true;
    		}
    	}
    	if (!clExist){
    		// Questo prodotto non e' stato ancora inserito nel carrello
    		cl = new CartLine();
    		cl.setQuantity(quantity);
    		BigDecimal tot = p.getPrice().multiply(new BigDecimal(cl.getQuantity()));
    		cl.setLineTotal(tot);
    		cl.setProduct(p);
    		cl.setCart(c);
    		cls = c.getCartLines();
        	cls.add(cl);
    	} else {
    		// Questo prodotto e' stato gia' inserito nel carrello
    		Query update = em.createQuery("UPDATE CartLine SET quantity = :quantity, lineTotal = :lineTotal WHERE id = :id");
    		cl.setQuantity(cl.getQuantity() + quantity);
    		cl.setLineTotal(p.getPrice().multiply(new BigDecimal(cl.getQuantity())));
    		update.setParameter("quantity", cl.getQuantity());
    		update.setParameter("lineTotal", cl.getLineTotal());
    		update.setParameter("id", cl.getId());
    		update.executeUpdate();
    	}
		
		em.persist(c);
		return c;
	}

	@Override
	@Transactional
	public void deleteCartLine(long id_cartline, long id_cart) throws BusinessException {
		CartLine cl = em.find(CartLine.class, id_cartline);
		em.remove(cl);
		Cart cart = em.find(Cart.class, id_cart);
		int size = cart.getCartLines().size();
		if (size == 0){
			em.remove(cart);
		}
		em.getEntityManagerFactory().getCache().evictAll();
	}

	@Override
	public ResponseCarts<CartLine> viewCartlines(User user) throws BusinessException {
		Cart cart = null;
		Collection<CartLine> cartLines = null;
		int size = 0;
		long id = 0;
		Query queryExistCart = em.createQuery("Select Count (c) FROM Cart c WHERE c.user = :user");
		queryExistCart.setParameter("user", user);
		Long exist = (Long)queryExistCart.getSingleResult();
		
		if (exist != 0){
			Query queryCart = em.createQuery("Select c FROM Cart c WHERE c.user = :user and c.created IS NOT NULL" +
	    			" ORDER BY c.created DESC", Cart.class).setMaxResults(1);
			queryCart.setParameter("user", user);
			cart = (Cart)queryCart.getSingleResult();
			
			if (cart.getPaid() == null){
				id = cart.getId();
				cartLines = cart.getCartLines();
				size = cartLines.size();
			}
		}
		return new ResponseCarts<CartLine>(id, size, cartLines);
	}

	@Override
	public Cart findCartById(long id) throws BusinessException {
		Cart cart = em.find(Cart.class, id);
		
		return cart;
	}
	
	@Override
	@Transactional
	public Cart findCartToCheckout(long id, String email) throws BusinessException {
		Query queryUser = em.createQuery("Select u FROM User u WHERE u.email = :email");
		queryUser.setParameter("email", email);
		User user = (User)queryUser.getSingleResult();
		
		Query query = em.createQuery("UPDATE Cart SET name = :name, surname = :surname, user = :user WHERE id = :id");
		query.setParameter("name", user.getName());
		query.setParameter("surname", user.getSurname());
		query.setParameter("user", user);
		query.setParameter("id", id);
		query.executeUpdate();
		
		Cart cart = em.find(Cart.class, id);
		
		return cart;
	}

	@Override
	@Transactional
	public void paid(String transaction_id, long cart_id) throws BusinessException {
		Cart cart = em.find(Cart.class, cart_id);
		cart.setPaid(new Date());
		cart.setTransaction_id(transaction_id);
		cart.setSession_id(null);
		em.merge(cart);
	}

	@Override
	@Transactional
	public void confirmCart(long id_cart, Date delivery_date) throws BusinessException {
		Query query = em.createQuery("UPDATE Cart SET delivery_date = :delivery_date WHERE id = :id");
		query.setParameter("delivery_date", delivery_date);
		query.setParameter("id", id_cart);
		query.executeUpdate();
	}

	@Override
	public CartLine findCartLineById(long id) {
		CartLine cartLine = em.find(CartLine.class, id);
		
		return cartLine;
	}

	@Override
	@Transactional
	public void updateCartLineRating(CartLine cartLine, int rating) {
		cartLine.setRating(rating);
        em.merge(cartLine);
        
        Product product = cartLine.getProduct();
        Rating productRatingObject = product.getRating();
        System.out.println("RatingObject " +productRatingObject);
        
        // Aumenta di 1 il numero di voti totali rilasciati
        int ratingVotes = productRatingObject.getRatingVotes();
        System.out.println("RatingVotes " +ratingVotes);
        int newRatingVotes = 0;
        newRatingVotes = ++ratingVotes ;
        
        System.out.println("newRatingVotes " +newRatingVotes);
        productRatingObject.setRatingVotes(newRatingVotes);
        
        // Somma il rating appena rilasciato al totale dei rating del prodotto
        int absoluteRating = productRatingObject.getAbsoluteRating();
        int newAbsoluteRating = absoluteRating + rating;
        productRatingObject.setAbsoluteRating(newAbsoluteRating);
        
        // Calcola la media del rating del prodotto
        float productRating = (float) newAbsoluteRating / newRatingVotes;
        productRatingObject.setRating(productRating);
        
        /* Associo il nuovo Rating all'owner della relazione (product) e ne faccio il merge. 
         * Per il cascade, il merge viene effettuato anche sul rating */
        productRatingObject.setProduct(product);
        product.setRating(productRatingObject);
        
        em.merge(product);
        
	}

	@Override
	public Collection<Cart> getCartsToDeliver() throws BusinessException {
		TypedQuery<Cart> query = em.createQuery("Select c FROM Cart c WHERE c.paid IS NOT NULL AND c.dispatched IS NULL", Cart.class);
   
        Collection<Cart> result = query.getResultList();
        
		return result;
	}

	@Override
	public Collection<Cart> findUserPaidCarts(User user) throws BusinessException {
		TypedQuery<Cart> query = em.createQuery("Select c FROM Cart c WHERE c.paid IS NOT NULL and c.user = :user", Cart.class);
		query.setParameter("user", user);
		
        List<Cart> result = query.getResultList();
        return result;
	
	}

	@Override
	public Collection<CartLine> findSellerReceivedOrders(Seller seller)	throws BusinessException {
	    TypedQuery<CartLine> query = em.createQuery("Select cl FROM CartLine cl " +
	    											"LEFT JOIN cl.cart c " +
	    											"LEFT JOIN cl.product p " +
	    											"WHERE p.seller = :seller AND c.dispatched = null",CartLine.class);
	    query.setParameter("seller", seller);
        Collection<CartLine> cartLines = query.getResultList();
        return cartLines;
        
	}

	@Override
	public Collection<CartLine> findCartLinesToDeliver() throws BusinessException {
		TypedQuery<CartLine> query = em.createQuery("Select cl FROM CartLine cl " +
	    											"WHERE cl.cart.paid IS NOT NULL " +
	    											"AND cl.cart.dispatched IS NULL " +
	    											"ORDER BY cl.cart.delivery_date, cl.product.seller",CartLine.class);
	    
        Collection<CartLine> cartLines = query.getResultList();
		return cartLines;
	}

	@Override
	@Transactional
	public void createFeedback(CartLine cartLine, String feedbackString) throws BusinessException {
		Product product = cartLine.getProduct();

        Feedback feedback = new Feedback();
        feedback.setFeedbackContent(feedbackString);
        feedback.setCartLine(cartLine);
        cartLine.setFeedback(feedback);
        product.getFeedbacks().add(feedback); // Aggiunge il feedback alla collezione di feedbacks del prodotto
        feedback.setProduct(product);
        
        em.persist(feedback);
        em.merge(cartLine);
        em.merge(product);
	}        
		
	@Override
	@Transactional
	public ResponseCarts<CartLine> persistCartSession(Cart cart, User user)
			throws BusinessException {
		cart.setUser(user);
		cart.setName(user.getName());
		cart.setSurname(user.getSurname());
		cart.setCreated(new Date());
		cart.setId(0);
		em.persist(cart);
		Iterator<CartLine> i = cart.getCartLines().iterator();
		while(i.hasNext()){
			CartLine cartLine = i.next();
			cartLine.setId(0);
			cartLine.setCart(cart);
			em.persist(cartLine);
		}
		
		return new ResponseCarts<CartLine>(cart.getId(), cart.getCartLines().size(), cart.getCartLines());
	}

	@Override
	public Rating findRatingById(long ratingId) throws BusinessException {
		Rating rating = em.find(Rating.class, ratingId);
		
		return rating;
	}

	@Override
	@Transactional
	public void emptyCart(long cartId) throws BusinessException {
		Cart cart = em.find(Cart.class, cartId);
		cart.setCartLines(null);
		em.merge(cart);
		em.getEntityManagerFactory().getCache().evictAll();
	}
	
}
