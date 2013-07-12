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
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
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
        
        Query query = em.createQuery("UPDATE Seller SET name= :name, surname= :surname, email= :email, " +
				"date_of_birth= :date_of_birth, address= :address WHERE id= :id");
		query.setParameter("name", user.getName());
		query.setParameter("surname", user.getSurname());
		query.setParameter("email", user.getEmail());
		query.setParameter("date_of_birth", user.getDate_of_birth());
		query.setParameter("address", user.getAddress());
		query.setParameter("id", user.getId());
		query.executeUpdate();
        
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
        
		TypedQuery<User> query = em.createQuery("SELECT NEW it.univaq.mwt.j2ee.kmZero.business.model.User (u.id, u.name, u.surname, u.email, u.created, u.date_of_birth, u.last_access, u.address) FROM User u" +
				 ((!"".equals(requestGrid.getsSearch())) ? " AND u.name LIKE '" + ConversionUtility.addPercentSuffix(requestGrid.getsSearch()) + "'" : "") +
				 ((!"".equals(requestGrid.getSortCol()) && !"".equals(requestGrid.getSortDir())) ? " order by " + requestGrid.getSortCol() + " " + requestGrid.getSortDir() : ""), User.class);
		
		//System.out.println("Questa è la query:" + query);
		
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

	@Override
	public void createSeller(Seller seller) throws BusinessException {
		String password = seller.getPassword();
		seller.setPassword(DigestUtils.md5Hex(password));
		seller.setCreated(new Date());
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		em.persist(seller);
		et.commit();
		
		em.close();
		emf.close();
		
	}

	@Override
	public void updateSeller(Seller seller) throws BusinessException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		Query query = em.createQuery("UPDATE Seller SET name= :name, surname= :surname, email= :email, " +
				"date_of_birth= :date_of_birth, address= :address, url= :url, phone= :phone WHERE id= :id");
		query.setParameter("name", seller.getName());
		query.setParameter("surname", seller.getSurname());
		query.setParameter("email", seller.getEmail());
		query.setParameter("date_of_birth", seller.getDate_of_birth());
		query.setParameter("address", seller.getAddress());
		query.setParameter("url", seller.getUrl());
		query.setParameter("phone", seller.getPhone());
		query.setParameter("id", seller.getId());
		query.executeUpdate();
		
		et.commit();
		em.close();
		emf.close();
		
	}

	@Override
	public void updateSellerByAdmin(Seller seller) throws BusinessException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		Query query = em.createQuery("UPDATE Seller SET name= :name, surname= :surname, email= :email, " +
				"date_of_birth= :date_of_birth, address= :address, p_iva= :p_iva, cod_fisc= :cod_fisc," +
				"company= :company url= :url, phone= :phone WHERE id= :id");
		query.setParameter("name", seller.getName());
		query.setParameter("surname", seller.getSurname());
		query.setParameter("email", seller.getEmail());
		query.setParameter("date_of_birth", seller.getDate_of_birth());
		query.setParameter("address", seller.getAddress());
		query.setParameter("p_iva", seller.getP_iva());
		query.setParameter("cod_fisc", seller.getCod_fisc());
		query.setParameter("company", seller.getCompany());
		query.setParameter("url", seller.getUrl());
		query.setParameter("phone", seller.getPhone());
		query.setParameter("id", seller.getId());
		query.executeUpdate();
		
		et.commit();
		em.close();
		emf.close();
	}

	@Override
	public void deleteSeller(Seller seller) throws BusinessException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.remove(em.merge(seller));
		et.commit();
		em.close();
		emf.close();
		
	}

	@Override
	public void upgradeSeller(User user) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findSellerById(long id) throws BusinessException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Seller seller = em.find(Seller.class, id);
		et.commit();
		em.close();
		emf.close();
		return seller;
	}

	@Override
	public ResponseGrid<Seller> viewAllSellersPaginated(RequestGrid requestGrid)
			throws BusinessException {
		if ("id".equals(requestGrid.getSortCol())) {
			requestGrid.setSortCol("s.id");
		} else {
			requestGrid.setSortCol("s." + requestGrid.getSortCol());
		}
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
        
        EntityTransaction et = em.getTransaction();
        et.begin();
        
        int maxRows = (int) (long) requestGrid.getiDisplayLength(); // Doppio cast per ottenere le rows massime
        int minRows = (int) (long) requestGrid.getiDisplayStart(); // Doppio cast per ottenere le rows minime
        
		TypedQuery<Seller> query = em.createQuery("SELECT NEW it.univaq.mwt.j2ee.kmZero.business.model.Seller (s.id, s.name, s.surname, s.p_iva, s.company, s.phone) FROM Seller s" +
				 ((!"".equals(requestGrid.getsSearch())) ? " AND s.name LIKE '" + ConversionUtility.addPercentSuffix(requestGrid.getsSearch()) + "'" : "") +
				 ((!"".equals(requestGrid.getSortCol()) && !"".equals(requestGrid.getSortDir())) ? " order by " + requestGrid.getSortCol() + " " + requestGrid.getSortDir() : ""), Seller.class);
		
		query.setMaxResults(maxRows);
		query.setFirstResult(minRows);
		
		List<Seller> sellers = query.getResultList();
		
		Query count = em.createQuery("SELECT COUNT (s) FROM Seller s");
		Long records = (Long) count.getSingleResult();
        
        et.commit();
        
        em.close();
        emf.close();
		
		return new ResponseGrid<Seller>(requestGrid.getsEcho(), records, records, sellers);
	}

}
