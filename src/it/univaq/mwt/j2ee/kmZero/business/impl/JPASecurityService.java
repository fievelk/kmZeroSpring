package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.SecurityService;
import it.univaq.mwt.j2ee.kmZero.business.model.User;

@Service
public class JPASecurityService implements SecurityService {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional
	public User authenticate(String e) throws BusinessException {
        User result = null;
        Query query = em.createQuery("Select U FROM User U WHERE U.email = :email");
        query.setParameter("email", e);
        
        result = (User)query.getSingleResult();
        
        result.setLastAccess(new Date());
        Query update = em.createQuery("UPDATE User SET lastAccess= :lastAccess WHERE id= :id");
        update.setParameter("lastAccess", new Date());
        update.setParameter("id", result.getId());
        update.executeUpdate();
       
        return result;
	}

}