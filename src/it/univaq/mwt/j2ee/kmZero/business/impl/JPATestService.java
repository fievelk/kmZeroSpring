package it.univaq.mwt.j2ee.kmZero.business.impl;

import it.univaq.mwt.j2ee.kmZero.business.TestService;
import it.univaq.mwt.j2ee.kmZero.business.model.Role;
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






import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class JPATestService implements TestService{

	@Override
	public void testNumberOne() {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
        
        System.out.println ("Transaction begins.");
        em.getTransaction().begin();
        try {
			Role r1 = new Role("vendor", "vendor");
			Role r2 = new Role("user", "user");
			Set<Role> rs = new HashSet<Role>();
			rs.add(r1); rs.add(r2);
			
			em.persist(r1);
			em.persist(r2);
			
			User u1 = new User("paolo", "paolo","paolo@gmail.com","pp",null,null, "via brancastello", rs);
			User u2 = new User("federico", "federico","federico@gmail.com","ff",null,null, "via paganica", rs);
   
			
			em.persist(u1);
			em.persist(u2);
			
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
        Role r1 = new Role("vendor", "vendor");
		Role r2 = new Role("user", "user");
		Set<Role> rs = new HashSet<Role>();
		rs.add(r1); rs.add(r2);
		user.setRoles(rs);
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
        emf.close();
	
	}
	
	

		
}
