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
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


public class JPACartService implements CartService{
	
	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@Override
	public void createCart(String address, long id_product, int quantity, User user) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		Product p = em.find(Product.class, id_product);
		CartLine cl = new CartLine();
		cl.setProduct(p);
		cl.setQuantity(quantity);
		cl.setLineTotal(p.getPrice().multiply(new BigDecimal(quantity)));
		
		Collection<CartLine> cls = new ArrayList<CartLine>();
		cls.add(cl);
		Cart c = new Cart();
		c.setName(user.getName());
		c.setSurname(user.getSurname());
		c.setUser(user);
		c.setAddress(address);
		c.setCartLines(cls);
		c.setCreated(new Date());
		cl.setCart(c);
		
		em.persist(cl);
		em.persist(c);
		
		et.commit();
		em.close();
		
	}

	@Override
	public void addCartLine(long id_product, int quantity, User user) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
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
        
		et.commit();
		em.close();
		
	}

	@Override
	public void deleteCartLine(long id_cartline, long id_cart) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		CartLine cl = em.find(CartLine.class, id_cartline);
		em.remove(cl);
		Cart cart = em.find(Cart.class, id_cart);
		int size = cart.getCartLines().size();
		if (size == 0){
			em.remove(cart);
		}
		et.commit();
		em.getEntityManagerFactory().getCache().evictAll();
		em.close();
	}

	@Override
	public ResponseCarts<CartLine> viewCartlines(User user) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
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
        
		et.commit();
		em.close();
		
		return new ResponseCarts<CartLine>(id, size, cartLines);
	}

	@Override
	public Cart findCartById(long id) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		Cart cart = em.find(Cart.class, id);
		
		et.commit();
		em.close();
		return cart;
	}
	
	@Override
	public Cart findCartToCheckout(long id, String email) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
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
		
		et.commit();
		em.close();
		
		return cart;
	}

	@Override
	public void paid(String transaction_id, long cart_id) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		Cart cart = em.find(Cart.class, cart_id);
		cart.setPaid(new Date());
		cart.setTransaction_id(transaction_id);
		cart.setSession_id(null);
		em.merge(cart);
		
		et.commit();
		em.close();
	}

	@Override
	public void confirmCart(long id_cart, Date delivery_date) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		Query query = em.createQuery("UPDATE Cart SET delivery_date = :delivery_date WHERE id = :id");
		query.setParameter("delivery_date", delivery_date);
		query.setParameter("id", id_cart);
		query.executeUpdate();
		
		et.commit();
		em.close();
		
	}

	@Override
	public CartLine findCartLineById(long id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		CartLine cartLine = em.find(CartLine.class, id);
		
		et.commit();
		em.close();
		return cartLine;
	}

	@Override
	public void updateCartLineRating(CartLine cartLine, int rating) {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
        tx.begin();
        
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
        
        em.merge(productRatingObject);
        em.merge(product);
        
        tx.commit();
        em.close();

	}

	@Override
	public Collection<Cart> getCartsToDeliver() throws BusinessException {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
		EntityManager em = emf.createEntityManager();
		
        TypedQuery<Cart> query = em.createQuery("Select c FROM Cart c WHERE c.paid IS NOT NULL AND c.dispatched IS NULL", Cart.class);
   
        Collection<Cart> result = query.getResultList();
   
        em.close();
        emf.close();
		
		return result;
	}

	@Override
	public Collection<Cart> findUserPaidCarts(User user) throws BusinessException {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
		EntityManager em = emf.createEntityManager();
		
		TypedQuery<Cart> query = em.createQuery("Select c FROM Cart c WHERE c.paid IS NOT NULL and c.user = :user", Cart.class);
		query.setParameter("user", user);
		
        List<Cart> result = query.getResultList();
        return result;
	
	}

	@Override
	public Collection<CartLine> findSellerReceivedOrders(Seller seller)	throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		
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
		
		EntityManager em = this.emf.createEntityManager();
		
	    TypedQuery<CartLine> query = em.createQuery("Select cl FROM CartLine cl " +
	    											"WHERE cl.cart.paid IS NOT NULL " +
	    											"AND cl.cart.dispatched IS NULL " +
	    											"ORDER BY cl.cart.delivery_date, cl.product.seller",CartLine.class);
	    
        Collection<CartLine> cartLines = query.getResultList();
		return cartLines;
	}

	@Override
	public void createFeedback(CartLine cartLine, String feedbackString) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
        tx.begin();

        Product product = cartLine.getProduct();

        Feedback feedback = new Feedback();
        feedback.setFeedbackContent(feedbackString);
        cartLine.setFeedback(feedback);
        feedback.setProduct(product);
        
        em.persist(feedback);
        em.merge(cartLine);
        em.merge(product);
        
        tx.commit();
        em.close();
	}        
		
	@Override
	public ResponseCarts<CartLine> persistCartSession(Cart cart, User user)
			throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
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
		
		
		et.commit();
		em.close();
		
		return new ResponseCarts<CartLine>(cart.getId(), cart.getCartLines().size(), cart.getCartLines());
	}

	@Override
	public Rating findRatingById(long ratingId) throws BusinessException {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Rating rating = em.find(Rating.class, ratingId);	
		
		tx.commit();
		em.close();
		return rating;
	}
	
}
