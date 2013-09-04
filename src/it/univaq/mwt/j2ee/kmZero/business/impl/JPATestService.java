package it.univaq.mwt.j2ee.kmZero.business.impl;

import it.univaq.mwt.j2ee.kmZero.business.TestService;
import it.univaq.mwt.j2ee.kmZero.business.model.Cart;
import it.univaq.mwt.j2ee.kmZero.business.model.CartLine;
import it.univaq.mwt.j2ee.kmZero.business.model.Category;
import it.univaq.mwt.j2ee.kmZero.business.model.Measure;
import it.univaq.mwt.j2ee.kmZero.business.model.Password;
import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.Warehouse;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class JPATestService implements TestService{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional
	public void testNumberOne() {
        
        System.out.println ("Transaction begins.");
        
		Role r1 = new Role(1, "seller", "seller");
		Role r2 = new Role(2, "user", "user");
		Role r3 = new Role(3, "admin", "admin");
		Set<Role> rs1 = new HashSet<Role>();
		Set<Role> rs2 = new HashSet<Role>();
		Set<Role> rs3 = new HashSet<Role>();
		rs1.add(r1);
		rs2.add(r2);
		rs3.add(r3);
		
		em.persist(r1);
		em.persist(r2);
		em.persist(r3);
		
		
		Seller s1 = new Seller("Pippo", "Goofy", "pippo@gmail.com", null, new Date(), null, "Via delle Napee, Francavilla Al Mare, CH, Italia", "78969678", "87696879", "Goofy G.A.S.", null, null, true);
		Seller s2 = new Seller("Topolino", "Mickey", "topolino@gmail.com", null, new Date(), null, "Via delle Piscine, Sant'agata, PE, Italia", "78969678", "87696879", "Mickey G.A.S.", null, null, true);

		User u3 = new User("admin", "admin", "admin@email.it", null, new Date(), null, "Via dei Tigli, Brecciarola, CH, Italia");
		Password p1 = new Password();
		Password p2 = new Password();
		Password p3 = new Password();
		Password p4 = new Password();
		p1.setPassword(DigestUtils.md5Hex("p"));
		p2.setPassword(DigestUtils.md5Hex("f"));
		p3.setPassword(DigestUtils.md5Hex("a"));

		p4.setPassword(DigestUtils.md5Hex("t"));

		s1.setPassword(p1);

		u3.setPassword(p3);

		s2.setPassword(p4);
		s1.setRoles(rs1);
		s2.setRoles(rs1);

		u3.setRoles(rs3);

		em.persist(s1);
		em.persist(s2);

		em.persist(u3);

		Category cat2 = new Category(2L, "Frutta", null);
		Category cat3 = new Category(3L, "Verdura", null);
		
		em.persist(cat2);
		em.persist(cat3);
		
		Measure meas1 = new Measure(1L, "Grammi" ,"gr.");
		Measure meas2 = new Measure(2L, "Kilogrammi" ,"kg.");
		Measure meas3 = new Measure(3L, "Litri","lt.");
		Measure meas4 = new Measure(4L, "Numero","#");
		
		em.persist(meas1);
		em.persist(meas2);
		em.persist(meas3);
		em.persist(meas4);
		
		Warehouse warehouse = new Warehouse("Warehouse", "Via dei Vestini, 66100 Chieti CH, Italia");
		em.persist(warehouse);
			
        System.out.println ("Transaction ends.");
	}

	@Override
	public void testNumberTwo() {
		//Esempio di ITERATOR
		/*Collection<User> userslist = (Collection)query.getResultList();
        System.out.println("DOPO getResultList");
        for (Iterator i = userslist.iterator(); i.hasNext();){
        	User u = (User)i.next();
        	System.out.println("USER:"+u.getAddress());
        }
		*/
	}

	@Override
	public void testNumberThree() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testNumberFour() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CartLine> testNumberFive() {
			Seller s = em.find(Seller.class, 3L);
			
		    TypedQuery<CartLine> query = em.createQuery("Select cl FROM CartLine cl " +
		    											"LEFT JOIN cl.cart c " +
		    											"LEFT JOIN cl.product p " +
		    											"WHERE p.seller = :seller AND c.deliveryDate = null",CartLine.class);
		    query.setParameter("seller", s);
	        List<CartLine> rs = query.getResultList();
	        return rs;
		     	
	}

	@Override
	public Set<Role> getAllRoles() {
		TypedQuery<Role> query = em.createQuery("Select R FROM Role R",Role.class);
   
        Set<Role> result = new HashSet<Role>(query.getResultList());
       
        return result;
	}
	
	@Override
	public User getUser() {
   
        User rs = null;
        TypedQuery<User> query = em.createQuery("Select U FROM User U WHERE U.name='federico'",User.class);
        rs = query.getSingleResult();
       
        return rs;
	}

	@Override
	@Transactional
	public void updateUser(User user) {
        System.out.println("ROLESSSS:"+user.getRoles());
        Role r1 = new Role(1, "seller", "seller");
		Role r2 = new Role(2, "user", "user");
		Set<Role> rs = new HashSet<Role>();
		rs.add(r1); rs.add(r2);
		user.setRoles(rs);
        em.merge(user);
	
	}
	
	@Override
	public List<User> getAllUsersTest() {
        TypedQuery<User> query = em.createQuery("Select u FROM User u", User.class);
   
        List<User> result = query.getResultList();
   
       System.out.println("RISULTATO " + result);
        return result;
	}

	@Override
	public List<Cart> getCartsToDeliverTest() {
		
        TypedQuery<Cart> query = em.createQuery("Select c FROM Cart c WHERE c.paid IS NOT NULL", Cart.class);
   
        List<Cart> result = query.getResultList();
		
		return result;
	}	

/*	@Override
	public List<Cart> viewPaidCartBySessionId(String session_id) throws BusinessException {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
		EntityManager em = emf.createEntityManager();
		
        TypedQuery<Cart> query = em.createQuery("Select c FROM Cart c WHERE c.paid IS NOT NULL", Cart.class);
   
        List<Cart> result = query.getResultList();
   
        em.close();
        emf.close();
		
		return result;
	}	*/
		
}
