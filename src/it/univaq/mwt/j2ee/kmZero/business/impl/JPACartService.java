package it.univaq.mwt.j2ee.kmZero.business.impl;

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
import it.univaq.mwt.j2ee.kmZero.business.service.CartService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.PostRemove;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

public class JPACartService implements CartService{
	
	@PersistenceUnit
	private EntityManagerFactory emf;

	@Override
	public void createCart(long id_product, int quantity, String session_id) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		Product p = em.find(Product.class, id_product);
		CartLine cl = null;
		Cart c = null;
		Collection<CartLine> cls = null;
		
		Query cartExist = em.createQuery("Select Count (c) FROM Cart c WHERE c.session_id = :session_id");
		cartExist.setParameter("session_id", session_id);
        
        Long cExist = (Long)cartExist.getSingleResult();
        
        if (cExist == 0){
        	// Carrello non esiste
        	cl = new CartLine();
    		cl.setQuantity(quantity);
    		float tot = cl.getQuantity() * p.getPrice();
    		cl.setLineTotal(tot);
    		cl.setProduct(p);
        	cls = new ArrayList<CartLine>();
    		cls.add(cl);
    		c = new Cart();
    		c.setSession_id(session_id);
    		c.setCartLines(cls);
        } else {
        	// Carrello esiste
        	Query cartQuery = em.createQuery("Select c FROM Cart c WHERE c.session_id = :session_id");
        	cartQuery.setParameter("session_id", session_id);
        	c = (Cart)cartQuery.getSingleResult();
        	cls = c.getCartLines();
        	Iterator<CartLine> i = cls.iterator();
        	boolean clExist = false;
        	while (i.hasNext() && !clExist){
        		CartLine temp = i.next();
        		if (temp.getProduct().getId() == id_product){
        			cl = temp;
        			clExist = true;
        		}
        	}
        	if (!clExist){
        		// Questo prodotto non è stato ancora inserito nel carrello
        		cl = new CartLine();
        		cl.setQuantity(quantity);
        		float tot = cl.getQuantity() * p.getPrice();
        		cl.setLineTotal(tot);
        		cl.setProduct(p);
        		cls = c.getCartLines();
            	cls.add(cl);
        	} else {
        		// Questo prodotto è stato già inserito nel carrello
        		Query update = em.createQuery("UPDATE CartLine SET quantity = :quantity, lineTotal = :lineTotal WHERE id = :id");
        		cl.setQuantity(cl.getQuantity() + quantity);
        		cl.setLineTotal(p.getPrice() * cl.getQuantity());
        		update.setParameter("quantity", cl.getQuantity());
        		update.setParameter("lineTotal", cl.getLineTotal());
        		update.setParameter("id", cl.getId());
        		update.executeUpdate();
        	}
        }
		
        em.persist(cl);
		em.persist(c);
        
		et.commit();
		em.close();
		
	}

	@Override
	public void deleteCartLine(long id) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		CartLine cl = em.find(CartLine.class, id);
		em.remove(cl);
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
		Query query = em.createQuery("Select c FROM Cart c WHERE c.session_id = :session_id");
        query.setParameter("session_id", session_id);
        
        try{
        	cart = (Cart) query.getSingleResult();
    		cartlines = cart.getCartLines();
        } catch (NoResultException e){
        	throw new BusinessException();
        }
        
		et.commit();
		em.close();
		
		return new ResponseCarts<CartLine>(cart.getId(), cartlines);
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
	public Cart findCartToCheckout(long id, String name, String surname, Date created) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		Cart cart = em.find(Cart.class, id);
		cart.setName(name);
		cart.setSurname(surname);
		cart.setCreated(created);
		em.merge(cart);
		
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
		em.merge(cart);
		
		et.commit();
		em.close();
	}

}
