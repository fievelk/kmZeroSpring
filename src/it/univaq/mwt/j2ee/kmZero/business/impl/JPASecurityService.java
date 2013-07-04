package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.SecurityService;
import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.model.User;

@Service
public class JPASecurityService implements SecurityService {

	@Override
	public User authenticate(String e) throws BusinessException {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
   
        User result = null;
        Query query = em.createQuery("Select U FROM User U WHERE U.email = :email");
        query.setParameter("email", e);
        
        result = (User)query.getSingleResult();
        System.out.println("USER:"+result.getName());
        em.close();
        emf.close();
       
        return result;
	}

}
