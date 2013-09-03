package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Image;
import it.univaq.mwt.j2ee.kmZero.business.model.Password;
import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.SellerContent;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.business.service.UserService;

@Service
public class JPAUserService implements UserService{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional
	public void createUser(User user) throws BusinessException {
		Password password = user.getPassword();
		password.setPassword(DigestUtils.md5Hex(password.getPassword()));
		user.setPassword(password);
		user.setCreated(new Date());
		
		// Setting del ruolo
		Role u = em.find(Role.class, 2);
		Set<Role> roles = new HashSet<Role>();
		roles.add(u);
		user.setRoles(roles);
		
		em.persist(user);
	}

	@Override
	@Transactional
	public void updateUser(User user) throws BusinessException {
        Query query = em.createQuery("UPDATE User SET name= :name, surname= :surname, email= :email, " +
				"dateOfBirth= :dateOfBirth, address= :address WHERE id= :id");
		query.setParameter("name", user.getName());
		query.setParameter("surname", user.getSurname());
		query.setParameter("email", user.getEmail());
		query.setParameter("dateOfBirth", user.getDateOfBirth());
		query.setParameter("address", user.getAddress());
		query.setParameter("id", user.getId());
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void deleteUser(User user) throws BusinessException {
		em.remove(em.merge(user));
	}
	
	@Override
	public User findUserById(long id) throws BusinessException {
		User user = em.find(User.class, id);
		return user;
	}
	
	@Override
	public User findUserSellerById(long id) throws BusinessException {
		User user = em.find(Seller.class, id);
		return user;
	}
	

	@Override
	public ResponseGrid<User> viewAllUsersPaginated(RequestGrid requestGrid) throws BusinessException {
		if ("id".equals(requestGrid.getSortCol())) {
			requestGrid.setSortCol("u.id");
		} else {
			requestGrid.setSortCol("u." + requestGrid.getSortCol());
		}
		
        int maxRows = (int) (long) requestGrid.getiDisplayLength();
        int minRows = (int) (long) requestGrid.getiDisplayStart();
        
		TypedQuery<User> query = em.createQuery("SELECT NEW it.univaq.mwt.j2ee.kmZero.business.model.User (u.id, u.name, u.surname, u.email, u.created, u.dateOfBirth, u.lastAccess, u.address) FROM User u" +
				 ((!"".equals(requestGrid.getsSearch())) ? " AND u.name LIKE '" + ConversionUtility.addPercentSuffix(requestGrid.getsSearch()) + "'" : "") +
				 ((!"".equals(requestGrid.getSortCol()) && !"".equals(requestGrid.getSortDir())) ? " order by " + requestGrid.getSortCol() + " " + requestGrid.getSortDir() : ""), User.class);
		
		query.setMaxResults(maxRows);
		query.setFirstResult(minRows);
		
		List<User> users = query.getResultList();
		
		Query count = em.createQuery("SELECT COUNT (u) FROM User u");
		Long records = (Long) count.getSingleResult();
        
        return new ResponseGrid<User>(requestGrid.getsEcho(), records, records, users);
	}

	@Override
	@Transactional
	public void createSeller(Seller seller) throws BusinessException {
		Password password = seller.getPassword();
		password.setPassword(DigestUtils.md5Hex(password.getPassword()));
		seller.setPassword(password); 
		seller.setCreated(new Date());
		
		// Setting del ruolo
		Role u = em.find(Role.class, 2);
		Set<Role> roles = new HashSet<Role>();
		roles.add(u);
		seller.setRoles(roles);
		
		em.persist(seller);
	}

	@Override
	@Transactional
	public void updateSeller(Seller seller) throws BusinessException {
		
		Query query = em.createQuery("UPDATE Seller SET name= :name, surname= :surname, email= :email, " +
				"dateOfBirth= :dateOfBirth, address= :address, url= :url, phone= :phone WHERE id= :id");
		query.setParameter("name", seller.getName());
		query.setParameter("surname", seller.getSurname());
		query.setParameter("email", seller.getEmail());
		query.setParameter("dateOfBirth", seller.getDateOfBirth());
		query.setParameter("address", seller.getAddress());
		query.setParameter("url", seller.getUrl());
		query.setParameter("phone", seller.getPhone());
		query.setParameter("id", seller.getId());
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void updateSellerByAdmin(Seller seller) throws BusinessException {
		// Effettua il cambio del ruolo del seller e, nel caso dell'abilitazione, la creazione della relativa pagina
		if (seller.getEnable()) {
			Role s = em.find(Role.class, 1);
			Set<Role> roles = new HashSet<Role>();
			roles.add(s);
			seller.setRoles(roles);
		} else {
			Role s = em.find(Role.class, 1);
			Set<Role> roles = new HashSet<Role>();
			roles.remove(s);
			seller.setRoles(roles);
		}
		Seller s = em.find(Seller.class, seller.getId());
		seller.setPassword(s.getPassword());
		seller.setLastAccess(s.getLastAccess());
		seller.setCreated(s.getCreated());
		seller.setImages(s.getImages());
		seller.setContents(s.getContents());
		
		em.merge(seller);
	
	}

	@Override
	@Transactional
	public void deleteSeller(Long sellerId) throws BusinessException {
		Seller s = em.find(Seller.class, sellerId);
		if(!s.getProducts().isEmpty()){
			s.deleteAllProducts();
		}
		em.remove(s);
	}

	@Override
	@Transactional
	public void upgradeSeller(Seller seller) throws BusinessException {
		User user = em.find(User.class, seller.getId());
		seller.setPassword(user.getPassword());
		seller.setLastAccess(user.getLastAccess());
		seller.setCreated(user.getCreated());
		
		em.remove(em.merge(user));
		seller.setId(0);
		em.persist(seller);
	}

	@Override
	public Seller findSellerById(long id) throws BusinessException {
		Seller seller = em.find(Seller.class, id);
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
		
        int maxRows = (int) (long) requestGrid.getiDisplayLength();
        int minRows = (int) (long) requestGrid.getiDisplayStart();
        
		TypedQuery<Seller> query = em.createQuery("SELECT NEW it.univaq.mwt.j2ee.kmZero.business.model.Seller (s.id, s.name, s.surname, s.p_iva, s.company, s.phone) FROM Seller s WHERE s.enable=0" +
				 ((!"".equals(requestGrid.getsSearch())) ? " AND s.name LIKE '" + ConversionUtility.addPercentSuffix(requestGrid.getsSearch()) + "'" : "") +
				 ((!"".equals(requestGrid.getSortCol()) && !"".equals(requestGrid.getSortDir())) ? " order by " + requestGrid.getSortCol() + " " + requestGrid.getSortDir() : ""), Seller.class);
		
		query.setMaxResults(maxRows);
		query.setFirstResult(minRows);
		
		List<Seller> sellers = query.getResultList();
		
		Query count = em.createQuery("SELECT COUNT (s) FROM Seller s");
		Long records = (Long) count.getSingleResult();
        
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
		
        int maxRows = (int) (long) requestGrid.getiDisplayLength();
        int minRows = (int) (long) requestGrid.getiDisplayStart();
        
		TypedQuery<Seller> query = em.createQuery("SELECT NEW it.univaq.mwt.j2ee.kmZero.business.model.Seller (s.id, s.name, s.surname, s.p_iva, s.company, s.phone) FROM Seller s WHERE s.enable=1" +
				 ((!"".equals(requestGrid.getsSearch())) ? " AND s.name LIKE '" + ConversionUtility.addPercentSuffix(requestGrid.getsSearch()) + "'" : "") +
				 ((!"".equals(requestGrid.getSortCol()) && !"".equals(requestGrid.getSortDir())) ? " order by " + requestGrid.getSortCol() + " " + requestGrid.getSortDir() : ""), Seller.class);
		
		query.setMaxResults(maxRows);
		query.setFirstResult(minRows);
		
		List<Seller> sellers = query.getResultList();
		
		Query count = em.createQuery("SELECT COUNT (s) FROM Seller s");
		Long records = (Long) count.getSingleResult();
        
        return new ResponseGrid<Seller>(requestGrid.getsEcho(), records, records, sellers);
	}

	@Override
	@Transactional
	public void editPassword(long id, String password) throws BusinessException {
		User user = em.find(User.class, id);
		Password p = new Password();
		p.setPassword(DigestUtils.md5Hex(password));
		user.setPassword(p);
		
		em.merge(user);
	}

	@Override
	public String oldPassword(long id) throws BusinessException {
		User user = em.find(User.class, id);
		String dbPassword = user.getPassword().getPassword();
		
		return dbPassword;
	}

	@Override
	public List<Seller> viewAllSellers() throws BusinessException {
		TypedQuery<Seller> query = em.createQuery("SELECT s FROM Seller s WHERE s.enable=1", Seller.class);
		List<Seller> sellers = query.getResultList();
		
		return sellers;
	}


	@Override
	public boolean emailExist(String email) throws BusinessException {
		boolean exist = true;
		Query query = em.createQuery("Select u FROM User u WHERE u.email = :email");
        query.setParameter("email", email);
        try {
        	query.getSingleResult();
        } catch (NoResultException e){
        	exist = false;
        }
        return exist;
	}

	/*SELLER CONTENTS*/
	
	@Override
	public boolean checkSellerContentProperty(long sellerId, long sellerContentId) throws BusinessException{
		SellerContent sc = em.find(SellerContent.class, sellerContentId);
		long id = sc.getSeller().getId();
		return (id == sellerId);
	}

	@Override
	public ResponseGrid<SellerContent> viewAllPageContentsPaginated(RequestGrid requestGrid,long seller_id) 
			throws BusinessException {
		//Dati per la query
        String sortCol = requestGrid.getSortCol();
        String sortDir = requestGrid.getSortDir();
        int minRows = (int) (long) requestGrid.getiDisplayStart();
        int maxRows = (int) (long) requestGrid.getiDisplayLength();
        String search  = ConversionUtility.addPercentSuffix(requestGrid.getsSearch());
        
        //Criteria Builder
        User u = em.find(User.class, seller_id);
        if(u.getRoles().contains(new Role(3))){
        	u = em.find(Seller.class, seller_id);
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SellerContent> q = cb.createQuery(SellerContent.class);
        Root<SellerContent> sc = q.from(SellerContent.class);
        
        //La clausola cb.and() e' sempre vera - viene usata se l'utente loggato ha ruolo admin
       // Predicate adminOrSeller =  u.getClass().equals(Seller.class) ? cb.equal(p.get("seller"), u) : cb.and();
              
        Predicate predicate = cb.and(
        							cb.equal(sc.get("seller"), u),
        							//adminOrSeller,	
									cb.or(
					        				cb.like(sc.get("title").as(String.class),search),
					        				cb.like(sc.get("description").as(String.class),search)		
					    			)
		);
        
        q.select(sc);
        //setto il where della query principale
        q.where(predicate);
        
        CriteriaQuery<Long> qc = cb.createQuery(Long.class);
        qc.select(cb.count(sc));
        //setto il where della query count 
        qc.where(predicate);
        
        Long totalRecords = em.createQuery(qc).getSingleResult();
        
        //orderby asc or desc
        if(sortDir.equals("asc"))
        	q.orderBy(cb.asc(sc.get(sortCol)));
        else
        	q.orderBy(cb.desc(sc.get(sortCol)));
      
        TypedQuery<SellerContent> query = em.createQuery(q);
        query.setMaxResults(maxRows);
        query.setFirstResult(minRows);
        List<SellerContent> sellercontents = query.getResultList();
      
        return new ResponseGrid<SellerContent>(requestGrid.getsEcho(), totalRecords, totalRecords, sellercontents);
	}

	@Override
	@Transactional
	public void createPageContent(SellerContent content, long userId) throws BusinessException{
		Seller s = em.find(Seller.class,userId);
    	//prima associo il seller al content (owner della relazione)
    	content.setSeller(s);
    	//utilizzo il cascade definito nel seller per fare persistenza del contenuto
    	//non e' automatica l'associazione del content verso il seller quindi va esplicitata prima con content.setSeller(s);
    	s.addContent(content);
	}
	

	@Override
	public SellerContent findSellerContentById(long id) throws BusinessException {
		SellerContent sc = em.find(SellerContent.class,id);
	    return sc;
	}

	@Override
	@Transactional
	public void updatePageContent(SellerContent content, long userId) throws BusinessException{
		Seller s = em.find(Seller.class,userId);
    	SellerContent sc = em.find(SellerContent.class, content.getId());
    	Image i = sc.getImage();
    	content.setSeller(s);
    	content.setImage(i);
    	em.merge(content);
	}

	@Override
	@Transactional
	public void deletePageContent(long contentId, long userId) throws BusinessException{
    	SellerContent sc = em.find(SellerContent.class,contentId);
    	Seller s = em.find(Seller.class, userId);
    	s.getContents().remove(sc);
	}
	
	/*Prodotti in evidenza*/

	@Override
	public List<Seller> getFavouriteSellers() throws BusinessException{
		TypedQuery<Seller> query = em.createQuery("SELECT s FROM Seller s WHERE s.enable=1", Seller.class);
		List<Seller> sellers = query.getResultList();
		return sellers;
	}

	@Override
	public List<Seller> getAllSellers() throws BusinessException{
		TypedQuery<Seller> query = em.createQuery("SELECT s FROM Seller s WHERE s.enable=1", Seller.class);
		List<Seller> sellers = query.getResultList();
		return sellers;
	}
}
