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
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
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
	public void createCart(String address, String session_id, long id_product, 
			int quantity) throws BusinessException {
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
		c.setAddress(address);
		c.setSession_id(session_id);
		c.setCartLines(cls);
		c.setCreated(new Date());
		
		em.persist(cl);
		em.persist(c);
		
		et.commit();
		em.close();
		
	}

	@Override
	public void addCartLine(long id_product, int quantity, String session_id) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		Product p = em.find(Product.class, id_product);
		CartLine cl = null;
		Cart c = null;
		Collection<CartLine> cls = null;
		boolean clExist = false;
		
    	// Carrello esiste
    	Query cartQuery = em.createQuery("Select c FROM Cart c WHERE c.session_id = :session_id");
    	cartQuery.setParameter("session_id", session_id);
    	c = (Cart)cartQuery.getSingleResult();
    	cls = c.getCartLines();
    	Iterator<CartLine> i = cls.iterator();
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
		
        //em.persist(cl);
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
	public ResponseCarts<CartLine> viewCartlines(String session_id) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		Cart cart = null;
		Collection<CartLine> cartlines = null;
		int size = 0;
		long id = 0;
		Query querycount = em.createQuery("Select Count (c) FROM Cart c WHERE c.session_id = :session_id");
		querycount.setParameter("session_id", session_id);
		Long count = (Long) querycount.getSingleResult();
		
		if (count != 0){
			Query query = em.createQuery("Select c FROM Cart c WHERE c.session_id = :session_id");
	        query.setParameter("session_id", session_id);
	        cart = (Cart) query.getSingleResult();
    		cartlines = cart.getCartLines();
    		size = cartlines.size();
    		id = cart.getId();
		}
        
		et.commit();
		em.close();
		
		return new ResponseCarts<CartLine>(id, size, cartlines);
	}
	
	/*@Override
	public ResponseCarts<CartLine> existCart(String session_id) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		long id = 0;
		int size = 0;
		
		Query querycount = em.createQuery("Select Count (c) FROM Cart c WHERE c.session_id = :session_id");
		querycount.setParameter("session_id", session_id);
        
        Long exist = (Long) querycount.getSingleResult();
        
        if (exist != 0){
    		Query query = em.createQuery("Select c FROM Cart c WHERE c.session_id = :session_id");
            query.setParameter("session_id", session_id);
        	Cart cart = (Cart)query.getSingleResult();
    		size = cart.getCartLines().size();
    		id = cart.getId();
        }
        
		et.commit();
		em.close();
		
		return new ResponseCarts<CartLine>(id, size, null);
	}*/

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
		
		Query query = em.createQuery("UPDATE Cart SET name = :name, surname = :surname WHERE id = :id");
		query.setParameter("name", user.getName());
		query.setParameter("surname", user.getSurname());
		query.setParameter("id", id);
		query.executeUpdate();
		
		Cart cart = em.find(Cart.class, id);
		Collection<Cart> carts = user.getCart();
		carts.add(cart);
		user.setCart(carts);
		
		em.persist(user);
		
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
        
        // Aumenta di 1 il numero di voti totali rilasciati
        int ratingVotes = product.getRatingVotes();
        System.out.println("RatingVotes " +ratingVotes);
        int newRatingVotes = 0;
        newRatingVotes = ++ratingVotes ;
        
        System.out.println("newRatingVotes " +newRatingVotes);
        product.setRatingVotes(newRatingVotes);
        
        // Somma il rating appena rilasciato al totale dei rating del prodotto
        int absoluteRating = product.getAbsoluteRating();
        int newAbsoluteRating = absoluteRating + rating;
        product.setAbsoluteRating(newAbsoluteRating);
        
        // Calcola la media del rating del prodotto
        float productRating = (float) newAbsoluteRating / newRatingVotes;
        product.setRating(productRating);
        
        em.merge(product);
        
        tx.commit();
        em.close();

	}

	@Override
	public List<Cart> getCartsToDeliver() throws BusinessException {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
		EntityManager em = emf.createEntityManager();
		
        TypedQuery<Cart> query = em.createQuery("Select c FROM Cart c WHERE c.paid IS NOT NULL AND c.dispatched IS NULL", Cart.class);
   
        List<Cart> result = query.getResultList();
   
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

	
}
