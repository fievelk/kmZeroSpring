package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.HashSet;
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
             
        User result = new User();
        
        String jpql = "SELECT U FROM USERS WHERE U.email = :email";
        Query query = em.createQuery(jpql);
        query.setParameter("email", e);
        result = (User)query.getSingleResult();
      
        em.close();
        emf.close();
        return result;
        
	}

}
