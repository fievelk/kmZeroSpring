package it.univaq.mwt.j2ee.kmZero.business.impl;

import it.univaq.mwt.j2ee.kmZero.business.TestService;
import it.univaq.mwt.j2ee.kmZero.business.model.Category;
import it.univaq.mwt.j2ee.kmZero.business.model.Password;
import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.SellerContent;
import it.univaq.mwt.j2ee.kmZero.business.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;






import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




public class JPATestService implements TestService{

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@Override
	public void testNumberOne() {
		
        EntityManager em = emf.createEntityManager();
        
        System.out.println ("Transaction begins.");
        em.getTransaction().begin();
        try {
			Role r1 = new Role(1, "seller", "seller");
			Role r2 = new Role(2, "user", "user");
			Role r3 = new Role(3, "admin", "admin");
			Set<Role> rs1 = new HashSet<Role>();
			Set<Role> rs2 = new HashSet<Role>();
			Set<Role> rs3 = new HashSet<Role>();
			rs1.add(r1);
			rs1.add(r2);
			rs2.add(r2);
			rs3.add(r3);
			
			em.persist(r1);
			em.persist(r2);
			em.persist(r3);
			
			User u1 = new User("paolo", "paolo", "paolo@gmail.com", null, null, null, "via brancastello");
			User u2 = new User("federico", "federico","federico@gmail.com", null ,null, null, "via paganica");
			Seller s = new Seller ("3453", "fff3254", "fedecompany", "www.fede.it", "385784");
			s.setName(u2.getName());
			s.setSurname(u2.getSurname());
			s.setEmail(u2.getEmail());
			s.setAddress(u2.getAddress());
			s.setCreated(new Date());
			s.setEnable(true);
			User u3 = new User("admin", "admin", "admin@email.it", null, null, null, "via, admin 1");
			Password p1 = new Password();
			Password p2 = new Password();
			Password p3 = new Password();
			p1.setPassword(DigestUtils.md5Hex("p"));
			p2.setPassword(DigestUtils.md5Hex("f"));
			p3.setPassword(DigestUtils.md5Hex("a"));
			u1.setPassword(p1);
			s.setPassword(p2);
			u3.setPassword(p3);
			u1.setRoles(rs1);
			s.setRoles(rs2);
			u3.setRoles(rs3);
   
			SellerContent content = new SellerContent("Titolo", "Descrizione");
			Collection<SellerContent> contents = new ArrayList<SellerContent>();
			contents.add(content);
			s.setContents(contents);
			
			em.persist(u1);
			em.persist(s);
			em.persist(u3);

			Category cat1 = new Category(1L, "Unclassified", 0);
			Category cat2 = new Category(2L, "Frutta", 0);
			Category cat3 = new Category(3L, "Verdura", 0);
			
			em.persist(cat1);
			em.persist(cat2);
			em.persist(cat3);
			
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
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
	public void testNumberFive() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Role> getAllRoles() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Role> query = em.createQuery("Select R FROM Role R",Role.class);
   
        Set<Role> result = new HashSet<Role>(query.getResultList());
   
        em.close();
        emf.close();
       
        return result;
	}
	
	@Override
	public User getUser() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
   
        User rs = null;
        TypedQuery<User> query = em.createQuery("Select U FROM User U WHERE U.name='federico'",User.class);
        rs = query.getSingleResult();
     
        em.close();
        emf.close();
       
        return rs;
	}

	@Override
	public void updateUser(User user) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
        System.out.println("ROLESSSS:"+user.getRoles());
        Role r1 = new Role(1, "seller", "seller");
		Role r2 = new Role(2, "user", "user");
		Set<Role> rs = new HashSet<Role>();
		rs.add(r1); rs.add(r2);
		user.setRoles(rs);
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
        emf.close();
	
	}
	
	@Override
	public List<User> getAllUsersTest() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("Select u FROM User u", User.class);
   
        List<User> result = query.getResultList();
   
        em.close();
        emf.close();
       System.out.println("RISULTATO " + result);
        return result;
	}	

		
}
