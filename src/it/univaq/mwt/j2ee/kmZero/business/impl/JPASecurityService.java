package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.SecurityService;
import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.model.User;

@Service
public class JPASecurityService implements SecurityService {

	/*@PersistenceUnit
	private EntityManagerFactory emf;*/
	
	@Override
	public User authenticate(String e) throws BusinessException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
   
        User result = null;
        Query query = em.createQuery("Select U FROM User U WHERE U.email = :email");
        query.setParameter("email", e);
        
        result = (User)query.getSingleResult();
        
        et.begin();
        
        result.setLast_access(new Date());
        Query update = em.createQuery("UPDATE User SET last_access= :last_access WHERE id= :id");
        update.setParameter("last_access", new Date());
        update.setParameter("id", result.getId());
        update.executeUpdate();
        
        System.out.println("USER:"+result.getName());
        
        et.commit();
        em.close();
        emf.close();
       
        return result;
	}

}