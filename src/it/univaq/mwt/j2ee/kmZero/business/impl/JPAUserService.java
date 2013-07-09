package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.business.service.UserService;

@Service
public class JPAUserService implements UserService{

	@Override
	public void createUser(User user) throws BusinessException {
		String password = user.getPassword();
		user.setPassword(DigestUtils.md5Hex(password));
		user.setCreated(new Date());
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(user);
		et.commit();
		
		em.close();
		emf.close();
		
	}

	@Override
	public void updateUser(User user) throws BusinessException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        
        et.begin();
        
        em.merge(user);
        
        et.commit();
        
        em.close();
        emf.close();
		
	}

	@Override
	public void deleteUser(User user) throws BusinessException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		em.remove(em.merge(user));
		et.commit();
		em.close();
		emf.close();
		
	}
	
	@Override
	public User findUserById(long id) throws BusinessException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		User user = em.find(User.class, id);
		et.commit();
		em.close();
		emf.close();
		
		return user;
	}

	@Override
	public ResponseGrid<User> viewAllUsersPaginated(RequestGrid requestGrid) throws BusinessException {
		if ("id".equals(requestGrid.getSortCol())) {
			requestGrid.setSortCol("u.id");
		} else {
			requestGrid.setSortCol("u." + requestGrid.getSortCol());
		}
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
        
        EntityTransaction et = em.getTransaction();
        et.begin();
        
        int maxRows = (int) (long) requestGrid.getiDisplayLength(); // Doppio cast per ottenere le rows massime
        int minRows = (int) (long) requestGrid.getiDisplayStart(); // Doppio cast per ottenere le rows minime
        
		TypedQuery<User> query = em.createQuery("SELECT u.id, u.name, u.surname, u.email, u.created, u.date_of_birth, u.last_access, u.address FROM User u" +
				 ((!"".equals(requestGrid.getsSearch())) ? " AND u.name LIKE '" + ConversionUtility.addPercentSuffix(requestGrid.getsSearch()) + "'" : "") +
				 ((!"".equals(requestGrid.getSortCol()) && !"".equals(requestGrid.getSortDir())) ? " order by " + requestGrid.getSortCol() + " " + requestGrid.getSortDir() : ""), User.class);

		System.out.println(query);
		
		query.setMaxResults(maxRows);
		query.setFirstResult(minRows);
		
		List<User> users = query.getResultList();
		
		Query count = em.createQuery("SELECT COUNT (u) FROM User u");
		Long records = (Long) count.getSingleResult();
        
        et.commit();
        
        em.close();
        emf.close();
		
		return new ResponseGrid<User>(requestGrid.getsEcho(), records, records, users);
	}

}
