package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.internal.expressions.ParameterExpression;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Category;
import it.univaq.mwt.j2ee.kmZero.business.model.Image;
import it.univaq.mwt.j2ee.kmZero.business.model.Measure;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.business.service.ProductService;

public class JPAProductService implements ProductService{
	
	@PersistenceUnit
	private EntityManagerFactory emf;

	@Override
	public void createProduct(Product product, long seller_id) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		
        tx.begin();
       
        product.setActive(true);
        Seller s = em.find(Seller.class, seller_id);
        product.setSeller(s);
        em.persist(product);

        tx.commit();
        em.close();

	}	
	
	@Override
	public void updateProduct(Product product,List<Image> images, long seller_id) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
        tx.begin();  
        
        Seller s = em.find(Seller.class, seller_id);
        product.setImages(images);
        product.setSeller(s);
        em.merge(product);
    
        tx.commit();
        em.close();
        		
	}

	// Mostra tutti i prodotti con active=1 e date nel range (lato User)
	/*@Override
	public ResponseGrid<Product> viewProducts(RequestGrid requestGrid) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		if ("id".equals(requestGrid.getSortCol())) {
			requestGrid.setSortCol("p.id");
		} else {
			requestGrid.setSortCol("p." + requestGrid.getSortCol());
		}
		
		Date today = new Date();
		
		tx.begin();
        
        int maxRows = (int) (long) requestGrid.getiDisplayLength(); // Doppio cast per ottenere le rows massime
        int minRows = (int) (long) requestGrid.getiDisplayStart(); // Doppio cast per ottenere le rows minime
        System.out.println("MINROWS:"+minRows);    
        System.out.println("MAXROWS:"+maxRows);
        String search  = ConversionUtility.addPercentSuffix(requestGrid.getsSearch());
        
		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.active=1 AND :today BETWEEN p.date_in AND p.date_out" +
				 ((!"".equals(requestGrid.getsSearch())) ? " AND lower(p.name) LIKE '" + search.toLowerCase() + "'" : "") +
				 ((!"".equals(requestGrid.getSortCol()) && !"".equals(requestGrid.getSortDir())) ? " order by " + requestGrid.getSortCol() : ""), Product.class);

		query.setMaxResults(maxRows);
		query.setFirstResult(minRows);
		
		query.setParameter("today", today);
		List<Product> products = query.getResultList();
		Long records = (long) products.size();
		
		Query count = em.createQuery("SELECT COUNT (p) FROM Product p WHERE p.active=1 AND :today BETWEEN p.date_in AND p.date_out" + ((!"".equals(requestGrid.getsSearch())) ? " AND lower(p.name) LIKE '" + search.toLowerCase() + "'" : ""));
		count.setParameter("today", today);
		Long totalRecords = (Long) count.getSingleResult();
        
        tx.commit();
        
        em.close();
        		
		return new ResponseGrid(requestGrid.getsEcho(),totalRecords, records, products);
	}*/
	
	@Override
	public ResponseGrid<Product> viewProducts(RequestGrid requestGrid) throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
	    tx.begin();
	    
	    //Dati per la query
	    boolean active = true;
        Date today = new Date();
        String sortCol = requestGrid.getSortCol().equals("category.name") ? "category" : requestGrid.getSortCol();
        String sortDir = requestGrid.getSortDir();
        int minRows = (int) (long) requestGrid.getiDisplayStart(); // Doppio cast per ottenere le rows minime + 1
        int maxRows = (int) (long) requestGrid.getiDisplayLength(); // Doppio cast per ottenere le rows massime
        String search  = ConversionUtility.addPercentSuffix(requestGrid.getsSearch());
        
        //Criteria Builider
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> q = cb.createQuery(Product.class);
        Root<Product> p = q.from(Product.class);
               
        q.select(p);
        Predicate predicate = cb.and(
									cb.equal(p.get("active").as(Boolean.class), active),
									cb.lessThanOrEqualTo(p.get("date_in").as(Date.class), today),
									cb.greaterThanOrEqualTo(p.get("date_out").as(Date.class), today),
									cb.or(
					        				cb.like(p.get("name").as(String.class),search),
					        				cb.like(p.get("description").as(String.class),search),
					        				cb.like(p.get("price").as(String.class),search)
					    			)
		);
        
        q.where(predicate);
        
        CriteriaQuery<Long> qc = cb.createQuery(Long.class);
        qc.select(cb.count(p));
        qc.where(predicate);
        
        Long totalRecords = em.createQuery(qc).getSingleResult();
        
        //orderby asc or desc
        if(sortDir.equals("asc"))
        	q.orderBy(cb.asc(p.get(sortCol)));
        else
        	q.orderBy(cb.desc(p.get(sortCol)));
        
        TypedQuery<Product> query = em.createQuery(q);
        query.setMaxResults(maxRows);
        query.setFirstResult(minRows);
        List<Product> products = query.getResultList();
        Long records = (long) products.size();

        tx.commit();
        em.close();
              
		return new ResponseGrid(requestGrid.getsEcho(), totalRecords, records, products);
        //return null;
	}
	
	@Override
	public ResponseGrid<Product> viewProductsBySellerIdPaginated(RequestGrid requestGrid,long seller_id) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
	    tx.begin();
	    
	    //Dati per la query
	    boolean active = true;
        Date today = new Date();
        String sortCol = requestGrid.getSortCol().equals("category.name") ? "category" : requestGrid.getSortCol();
        String sortDir = requestGrid.getSortDir();
        int minRows = (int) (long) requestGrid.getiDisplayStart(); // Doppio cast per ottenere le rows minime + 1
        int maxRows = (int) (long) requestGrid.getiDisplayLength(); // Doppio cast per ottenere le rows massime
        String search  = ConversionUtility.addPercentSuffix(requestGrid.getsSearch());
        
        //Criteria Builider
        User u = em.find(User.class, seller_id);
        if(u.getRoles().contains(new Role(3))){
        	u = em.find(Seller.class, seller_id);
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> q = cb.createQuery(Product.class);
        Root<Product> p = q.from(Product.class);
        
        //La clausola cb.and() � sempre vera - viene usata se l'utente loggato ha ruolo admin
        Predicate adminOrSeller =  u.getClass().equals(Seller.class) ? cb.equal(p.get("seller"), u) : cb.and();
              
        Predicate predicate = cb.and(
        							adminOrSeller,
									cb.equal(p.get("active").as(Boolean.class), active),	
									cb.or(
					        				cb.like(p.get("name").as(String.class),search),
					        				cb.like(p.get("description").as(String.class),search),
					        				cb.like(p.get("price").as(String.class),search)
					        				
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
        
        TypedQuery<Product> query = em.createQuery(q);
        query.setMaxResults(maxRows);
        query.setFirstResult(minRows);
        List<Product> products = query.getResultList();

        tx.commit();
        em.close();
        System.out.println("DATI RESPONSEGRID USER - ECHO:"+requestGrid.getsEcho()+"TOTREC:"+ totalRecords+"TOTREC:"+ totalRecords+"ROWS:"+ products);      

		return new ResponseGrid(requestGrid.getsEcho(), totalRecords, totalRecords, products);
        //return null;
	}

	
/*	@Override
	public ResponseGrid<Product> viewProductsBySellerIdPaginated(RequestGrid requestGrid, Seller seller) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		if ("id".equals(requestGrid.getSortCol())) {
			requestGrid.setSortCol("p.id");
		} else {
			if ("category.name".equals(requestGrid.getSortCol())) {
				requestGrid.setSortCol("p.category.name");
			} else {
				requestGrid.setSortCol("p." + requestGrid.getSortCol());
			}
			
		} 
		
	    tx.begin();

        int minRows = (int) (long) requestGrid.getiDisplayStart(); // Doppio cast per ottenere le rows minime + 1
        int maxRows = (int) (long) requestGrid.getiDisplayLength(); // Doppio cast per ottenere le rows massime
        
		VERSIONE CON IL SELLER 
 		//TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.active=1 and p.seller = :seller" +
        
		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.active=1" +
				 ((!"".equals(requestGrid.getsSearch())) ? " AND p.name LIKE '" + ConversionUtility.addPercentSuffix(requestGrid.getsSearch()) + "'" : "") +
				 ((!"".equals(requestGrid.getSortCol()) && !"".equals(requestGrid.getSortDir())) ? " order by " + requestGrid.getSortCol() + " " + requestGrid.getSortDir() : ""), Product.class);

		query.setParameter("seller", seller);
        
		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.active=1" +
				 ((!"".equals(requestGrid.getsSearch())) ? " AND p.name LIKE '" + ConversionUtility.addPercentSuffix(requestGrid.getsSearch()) + "'" : "") +
				 ((!"".equals(requestGrid.getSortCol()) && !"".equals(requestGrid.getSortDir())) ? " order by " + requestGrid.getSortCol() + " " + requestGrid.getSortDir() : ""), Product.class);

		query.setMaxResults(maxRows);
		query.setFirstResult(minRows);
		
		List<Product> products = query.getResultList();
		
		Query count = em.createQuery("SELECT COUNT (p) FROM Product p WHERE p.active=1");
		Long records = (Long) count.getSingleResult();
        
        tx.commit();
        em.close();
        
		return new ResponseGrid(requestGrid.getsEcho(), records, records, products);
	}*/

	// CATEGORIES //
	
	@Override
	public void createCategory(Category category) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        em.persist(category);
        
        tx.commit();
        em.close();
	}
	
	@Override
	public void updateCategory(Category category) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        em.merge(category);
           
        tx.commit();
        em.close();
        		
	}
	
	
	@Override
	public void deleteCategory(Category category) {
		
		if (!category.getName().equals("Unclassified")) {
			
		
			EntityManager em = this.emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
	
			
			TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.category=:category", Product.class);
			query.setParameter("category", category);
			List<Product> products = query.getResultList();
			
			Query count = em.createQuery("SELECT COUNT (c) FROM Category c WHERE c.name='Unclassified'");
			Long countResult = (Long) count.getSingleResult();
			
			if (countResult == 0L) {
				Category unclassifiedCategory = new Category("Unclassified");
				em.persist(unclassifiedCategory);
			}
			
			TypedQuery<Category> categoryQuery = em.createQuery("SELECT c FROM Category c WHERE c.name='Unclassified'", Category.class);
			Category unclassifiedCategory = categoryQuery.getSingleResult();
			
			for (Product p : products) {
				p.setCategory(unclassifiedCategory);
				em.merge(p); // Si deve fare per ogni p? Non si può fare una volta sola su tutta la collection di p?
			}
			
			category = em.merge(category);
			em.remove(category);
	
			tx.commit();
			em.close();
		
		} else {
			System.out.println("You cannot delete the Unclassified category!");
		}
	}
	
	
	@Override
	public Category findCategoryById(long id) throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Category category = em.find(Category.class, id);	
		
		tx.commit();
		em.close();
		return category;
	}
	
	@Override
	public List<Category> findAllCategories() throws BusinessException {
	
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
		List<Category> categories = query.getResultList();
		
		tx.commit();
		em.close();
		return categories;
	}

	
	@Override
	public Product findProductById(long id) throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Product product = em.find(Product.class, id);	
		
		tx.commit();
		em.close();
		return product;
	}


	@Override
	public void deleteProduct(Product product,long seller_id) {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Seller s = em.find(Seller.class,seller_id);
		product = em.merge(product);
		s.deleteProduct(product);
		//product = em.merge(product); // Esegue l'attachment del product
		//product.setActive(false);
		
		tx.commit();
		em.close();
	}

	
	@Override
	public boolean checkProductProperty(long sellerId, long prodId) throws BusinessException{
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		long sp;
		try {
			Product p = findProductById(prodId);
			sp = p.getSeller().getId();
			tx.commit();
			return (sp == sellerId);
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			return false;
		}
		
		
	}
	
	/* Measures */

	@Override
	public List<Measure> findAllMeasures() throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		TypedQuery<Measure> query = em.createQuery("SELECT m FROM Measure m", Measure.class);
		List<Measure> measures = query.getResultList();
		
		tx.commit();
		em.close();
		return measures;
		
	}


	@Override
	public void createMeasure(Measure measure) throws BusinessException {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        em.persist(measure);
        
        tx.commit();
        em.close();
		
	}


	@Override
	public void updateMeasure(Measure measure) throws BusinessException {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        em.merge(measure);
        
        tx.commit();
        em.close();
		
	}
	
	@Override
	public Measure findMeasureById(long id) throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Measure measure = em.find(Measure.class, id);	
		
		tx.commit();
		em.close();
		return measure;
	}	

	
	@Override
	public void deleteMeasure(Measure measure) throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.measure=:measure", Product.class);
		query.setParameter("measure", measure);
		List<Product> products = query.getResultList();
		
		Query count = em.createQuery("SELECT COUNT (m) FROM Measure m WHERE m.name='Unclassified'");
		Long countResult = (Long) count.getSingleResult();
		
		if (countResult == 0L) {
			Measure unclassifiedMeasure = new Measure("Unclassified");
			em.persist(unclassifiedMeasure);
		}
		
		TypedQuery<Measure> measureQuery = em.createQuery("SELECT m FROM Measure m WHERE m.name='Unclassified'", Measure.class);

		Measure unclassifiedMeasure = measureQuery.getSingleResult();
		
		for (Product p : products) {
			p.setMeasure(unclassifiedMeasure);
			em.merge(p);
		}
		
		measure = em.merge(measure);
		em.remove(measure);
		
		tx.commit();
		em.close();
	}	
}
