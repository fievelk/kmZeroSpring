package it.univaq.mwt.j2ee.kmZero.business.impl;

import it.univaq.mwt.j2ee.kmZero.business.TestService;
import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.model.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;




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
        Role r1 = new Role("vendor", "vendor");
        Role r2 = new Role("user", "user");
        Set<Role> rs = new HashSet<Role>();
        rs.add(r1); rs.add(r2);
        
        em.persist(r1);
        em.persist(r2);
        
        User u = new User("paolo", "paolo","paolo@gmail.com","pp",null,null, "via brancastello", rs);
    
        
        em.persist(u);
        
        em.getTransaction().commit();
        System.out.println ("Transaction ends.");
	}

	@Override
	public void testNumberTwo() {
		// TODO Auto-generated method stub
		
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
	
	

		
}
