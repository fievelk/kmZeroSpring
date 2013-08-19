package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Category;
import it.univaq.mwt.j2ee.kmZero.business.model.Image;
import it.univaq.mwt.j2ee.kmZero.business.model.Password;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.SellerContent;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.business.service.UserService;


public class JPAUserService implements UserService{

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@Override
	public void createUser(User user) throws BusinessException {
		Password password = user.getPassword();
		password.setPassword(DigestUtils.md5Hex(password.getPassword()));
		user.setPassword(password);
		user.setCreated(new Date());
		
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Setting del ruolo
		Role u = em.find(Role.class, 2);
		Set<Role> roles = new HashSet<Role>();
		roles.add(u);
		user.setRoles(roles);
		
		em.persist(user);
		et.commit();
		
		em.close();
		
	}

	@Override
	public void updateUser(User user) throws BusinessException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        
        et.begin();
        
        Query query = em.createQuery("UPDATE User SET name= :name, surname= :surname, email= :email, " +
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
	}

	@Override
	public void deleteUser(User user) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		em.remove(em.merge(user));
		et.commit();
		em.close();	
	}
	
	@Override
	public User findUserById(long id) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		User user = em.find(User.class, id);
		et.commit();
		em.close();
		
		return user;
	}

	@Override
	public ResponseGrid<User> viewAllUsersPaginated(RequestGrid requestGrid) throws BusinessException {
		if ("id".equals(requestGrid.getSortCol())) {
			requestGrid.setSortCol("u.id");
		} else {
			requestGrid.setSortCol("u." + requestGrid.getSortCol());
		}
		
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
        
        EntityTransaction et = em.getTransaction();
        et.begin();
        
        int maxRows = (int) (long) requestGrid.getiDisplayLength(); // Doppio cast per ottenere le rows massime
        int minRows = (int) (long) requestGrid.getiDisplayStart(); // Doppio cast per ottenere le rows minime
        
		TypedQuery<User> query = em.createQuery("SELECT NEW it.univaq.mwt.j2ee.kmZero.business.model.User (u.id, u.name, u.surname, u.email, u.created, u.date_of_birth, u.last_access, u.address) FROM User u" +
				 ((!"".equals(requestGrid.getsSearch())) ? " AND u.name LIKE '" + ConversionUtility.addPercentSuffix(requestGrid.getsSearch()) + "'" : "") +
				 ((!"".equals(requestGrid.getSortCol()) && !"".equals(requestGrid.getSortDir())) ? " order by " + requestGrid.getSortCol() + " " + requestGrid.getSortDir() : ""), User.class);
		
		//System.out.println("Questa ÔøΩ la query:" + query);
		
		query.setMaxResults(maxRows);
		query.setFirstResult(minRows);
		
		List<User> users = query.getResultList();
		
		Query count = em.createQuery("SELECT COUNT (u) FROM User u");
		Long records = (Long) count.getSingleResult();
        
        et.commit();
        em.close();
		
		return new ResponseGrid<User>(requestGrid.getsEcho(), records, records, users);
	}

	@Override
	public void createSeller(Seller seller) throws BusinessException {
		Password password = seller.getPassword();
		password.setPassword(DigestUtils.md5Hex(password.getPassword()));
		seller.setPassword(password); 
		seller.setCreated(new Date());
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		
		// Setting del ruolo
		Role u = em.find(Role.class, 2);
		Set<Role> roles = new HashSet<Role>();
		roles.add(u);
		seller.setRoles(roles);
		
		em.persist(seller);
		et.commit();
		
		em.close();
	}

	@Override
	public void updateSeller(Seller seller) throws BusinessException {
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
	}

	@Override
	public void updateSellerByAdmin(Seller seller) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		
		// Effettua il cambio del ruolo del seller e, nel caso dell'abilitazione, la creazione della relativa pagina
		if (seller.getEnable()) {
			Role s = em.find(Role.class, 1);
			Set<Role> roles = new HashSet<Role>();
			roles.add(s);
			seller.setRoles(roles);
			SellerContent content = new SellerContent ("Titolo", "Descrizione");
			Collection<SellerContent> contents = new ArrayList<SellerContent>();
			contents.add(content);
			seller.setContents(contents);
		} else {
			Role s = em.find(Role.class, 1);
			Set<Role> roles = new HashSet<Role>();
			roles.remove(s);
			seller.setRoles(roles);
			//TypedQuery<SellerContent> contents = em.createQuery("SELECT s FROM Seller s WHERE seller_fk=" + seller.getId(), SellerContent.class);
			seller.setContents(null);
			/*TypedQuery<Seller> query = em.createQuery("SELECT s FROM Seller s", Seller.class);
			List<Seller> sellers = query.getResultList();*/
		}
		
		// Vecchia query per l'aggiornamento
		/*Query query = em.createQuery("UPDATE Seller SET name= :name, surname= :surname, email= :email, " +
				"date_of_birth= :date_of_birth, address= :address, p_iva= :p_iva, cod_fisc= :cod_fisc, " +
				"company= :company, url= :url, phone= :phone, enable= :enable WHERE id= :id");
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
		query.setParameter("enable", seller.getEnable());
		query.setParameter("id", seller.getId());
		query.executeUpdate();*/
		
		Seller old_seller = em.find(Seller.class, seller.getId());
		seller.setPassword(old_seller.getPassword());
		seller.setLast_access(old_seller.getLast_access());
		seller.setCreated(old_seller.getCreated());
		
		em.merge(seller);
		et.commit();
		em.close();
	}

	@Override
	public void deleteSeller(Seller seller) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.remove(em.merge(seller));
		et.commit();
		em.close();
		
	}

	@Override
	public void upgradeSeller(Seller seller) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		User user = em.find(User.class, seller.getId());
		seller.setPassword(user.getPassword());
		seller.setLast_access(user.getLast_access());
		seller.setCreated(user.getCreated());
		
		em.remove(em.merge(user));
		et.commit();
		
		et.begin();
		em.merge(seller);
		et.commit();
		em.close();
	}

	@Override
	public Seller findSellerById(long id) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Seller seller = em.find(Seller.class, id);
		et.commit();
		em.close();
		return seller;
	}

	@Override
	public ResponseGrid<Seller> viewAllSellersToEnablePaginated(RequestGrid requestGrid)
			throws BusinessException {
		if ("id".equals(requestGrid.getSortCol())) {
			requestGrid.setSortCol("s.id");
		} else {
			requestGrid.setSortCol("s." + requestGrid.getSortCol());
		}
		
        EntityManager em = emf.createEntityManager();
        
        EntityTransaction et = em.getTransaction();
        et.begin();
        
        int maxRows = (int) (long) requestGrid.getiDisplayLength(); // Doppio cast per ottenere le rows massime
        int minRows = (int) (long) requestGrid.getiDisplayStart(); // Doppio cast per ottenere le rows minime
        
		TypedQuery<Seller> query = em.createQuery("SELECT NEW it.univaq.mwt.j2ee.kmZero.business.model.Seller (s.id, s.name, s.surname, s.p_iva, s.company, s.phone) FROM Seller s WHERE s.enable=0" +
				 ((!"".equals(requestGrid.getsSearch())) ? " AND s.name LIKE '" + ConversionUtility.addPercentSuffix(requestGrid.getsSearch()) + "'" : "") +
				 ((!"".equals(requestGrid.getSortCol()) && !"".equals(requestGrid.getSortDir())) ? " order by " + requestGrid.getSortCol() + " " + requestGrid.getSortDir() : ""), Seller.class);
		
		query.setMaxResults(maxRows);
		query.setFirstResult(minRows);
		
		List<Seller> sellers = query.getResultList();
		
		Query count = em.createQuery("SELECT COUNT (s) FROM Seller s");
		Long records = (Long) count.getSingleResult();
        
        et.commit();
        em.close();
		
		return new ResponseGrid<Seller>(requestGrid.getsEcho(), records, records, sellers);
	}
	
	@Override
	public ResponseGrid<Seller> viewAllSellersEnabledPaginated(RequestGrid requestGrid)
			throws BusinessException {
		if ("id".equals(requestGrid.getSortCol())) {
			requestGrid.setSortCol("s.id");
		} else {
			requestGrid.setSortCol("s." + requestGrid.getSortCol());
		}
		
        EntityManager em = emf.createEntityManager();
        
        EntityTransaction et = em.getTransaction();
        et.begin();
        
        int maxRows = (int) (long) requestGrid.getiDisplayLength(); // Doppio cast per ottenere le rows massime
        int minRows = (int) (long) requestGrid.getiDisplayStart(); // Doppio cast per ottenere le rows minime
        
		TypedQuery<Seller> query = em.createQuery("SELECT NEW it.univaq.mwt.j2ee.kmZero.business.model.Seller (s.id, s.name, s.surname, s.p_iva, s.company, s.phone) FROM Seller s WHERE s.enable=1" +
				 ((!"".equals(requestGrid.getsSearch())) ? " AND s.name LIKE '" + ConversionUtility.addPercentSuffix(requestGrid.getsSearch()) + "'" : "") +
				 ((!"".equals(requestGrid.getSortCol()) && !"".equals(requestGrid.getSortDir())) ? " order by " + requestGrid.getSortCol() + " " + requestGrid.getSortDir() : ""), Seller.class);
		
		query.setMaxResults(maxRows);
		query.setFirstResult(minRows);
		
		List<Seller> sellers = query.getResultList();
		
		Query count = em.createQuery("SELECT COUNT (s) FROM Seller s");
		Long records = (Long) count.getSingleResult();
        
        et.commit();
        em.close();
		
		return new ResponseGrid<Seller>(requestGrid.getsEcho(), records, records, sellers);
	}

	@Override
	public void editPassword(long id, String password) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		User user = em.find(User.class, id);
		Password p = new Password();
		p.setPassword(DigestUtils.md5Hex(password));
		user.setPassword(p);
		em.merge(user);
		
		et.commit();
		em.close();
	}

	@Override
	public String oldPassword(long id) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		User user = em.find(User.class, id);
		String db_password = user.getPassword().getPassword();
		
		et.commit();
		em.close();
		
		return db_password;
	}

	@Override
	public List<Seller> viewAllSellers() throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		TypedQuery<Seller> query = em.createQuery("SELECT s FROM Seller s WHERE s.enable=1", Seller.class);
		List<Seller> sellers = query.getResultList();
		
		et.commit();
		em.close();
		return sellers;
	}

	@Override
	public List<Seller> getSellersFromPaidCarts() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editSellerContent(Seller seller) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		Seller s = em.find(Seller.class, seller.getId());
		s.setContents(seller.getContents());
		em.merge(s);
		
		et.commit();
		em.close();
	}

	@Override
	public boolean emailExist(String email) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		boolean exist = true;
		et.begin();
		
		Query query = em.createQuery("Select u FROM User u WHERE u.email = :email");
        query.setParameter("email", email);
        
        try {
        	query.getSingleResult();
        } catch (NoResultException e){
        	exist = false;
        }
        
		et.commit();
		em.close();
		
		return exist;
	}

	/*SELLER CONTENTS*/
	
	@Override
	public boolean checkSellerContentProperty(long sellerId, long sellerContentId) throws BusinessException{
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		SellerContent sc = em.find(SellerContent.class, sellerContentId);
		long id = sc.getSeller().getId();
		tx.commit();
		return (id == sellerId);
		
	}

	@Override
	public ResponseGrid<SellerContent> viewAllPageContentsPaginated(RequestGrid requestGrid,long seller_id) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
	    tx.begin();
	    
	    //Dati per la query
        String sortCol = requestGrid.getSortCol();
        String sortDir = requestGrid.getSortDir();
        int minRows = (int) (long) requestGrid.getiDisplayStart(); // Doppio cast per ottenere le rows minime + 1
        int maxRows = (int) (long) requestGrid.getiDisplayLength(); // Doppio cast per ottenere le rows massime
        String search  = ConversionUtility.addPercentSuffix(requestGrid.getsSearch());
        
        //Criteria Builder
        User u = em.find(User.class, seller_id);
        if(u.getRoles().contains(new Role(3))){
        	u = em.find(Seller.class, seller_id);
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SellerContent> q = cb.createQuery(SellerContent.class);
        Root<SellerContent> p = q.from(SellerContent.class);
        
        //La clausola cb.and() è sempre vera - viene usata se l'utente loggato ha ruolo admin
        Predicate adminOrSeller =  u.getClass().equals(Seller.class) ? cb.equal(p.get("seller"), u) : cb.and();
              
        Predicate predicate = cb.and(
        							adminOrSeller,	
									cb.or(
					        				cb.like(p.get("title").as(String.class),search),
					        				cb.like(p.get("description").as(String.class),search)		
					    			)
		);
        
        q.select(p);
        //setto il where della query principale
        q.where(predicate);
        
        CriteriaQuery<Long> qc = cb.createQuery(Long.class);
        qc.select(cb.count(p));
        //setto il where della query count 
        qc.where(predicate);
        
        Long totalRecords = em.createQuery(qc).getSingleResult();
        
        //orderby asc or desc
        if(sortDir.equals("asc"))
        	q.orderBy(cb.asc(p.get(sortCol)));
        else
        	q.orderBy(cb.desc(p.get(sortCol)));
      
        TypedQuery<SellerContent> query = em.createQuery(q);
        query.setMaxResults(maxRows);
        query.setFirstResult(minRows);
        List<SellerContent> sellercontents = query.getResultList();
      
        tx.commit();
        em.close();
        
		return new ResponseGrid(requestGrid.getsEcho(), totalRecords, totalRecords, sellercontents);
	}

	@Override
	public void createPageContent(SellerContent content, long userId) throws BusinessException{
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
	    tx.begin();
	    	Seller s = em.find(Seller.class,userId);
	    	content.setSeller(s);
	    	em.persist(content);
	    tx.commit();
	    em.close();
		
	}
	


	@Override
	public SellerContent findSellerContentById(long id) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
	    tx.begin();
	    	SellerContent sc = em.find(SellerContent.class,id);
	    tx.commit();
	    em.close();
	    return sc;
	}

	@Override
	public void updatePageContent(SellerContent content, long userId) throws BusinessException{
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
	    tx.begin();
	    	Seller s = em.find(Seller.class,userId);
	    	SellerContent sc = em.find(SellerContent.class, content.getId());
	    	Image i = sc.getImage();
	    	content.setSeller(s);
	    	content.setImage(i);
	    	em.merge(content);
	    tx.commit();
	    em.close();	
	}

	@Override
	public void deletePageContent(long contentId, long userId) throws BusinessException{
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
	    tx.begin();
	    	SellerContent sc = em.find(SellerContent.class,contentId);
	    	em.remove(sc);
	    tx.commit();
	    em.close();	
		
	}
	
	/*Featured Items*/

	@Override
	public List<Seller> getFavouriteSellers() throws BusinessException{
		EntityManager em = this.emf.createEntityManager();
		TypedQuery<Seller> query = em.createQuery("SELECT s FROM Seller s WHERE s.enable=1", Seller.class);
		List<Seller> sellers = query.getResultList();
		em.close();
		return sellers;
	}

	@Override
	public List<Seller> getAllSellers() throws BusinessException{
		EntityManager em = this.emf.createEntityManager();
		TypedQuery<Seller> query = em.createQuery("SELECT s FROM Seller s WHERE s.enable=1", Seller.class);
		List<Seller> sellers = query.getResultList();
		em.close();
		return sellers;
	}



}
