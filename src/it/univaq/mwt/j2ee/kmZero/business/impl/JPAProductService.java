package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.RequestGridProducts;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Category;
import it.univaq.mwt.j2ee.kmZero.business.model.Measure;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.Rating;
import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.business.service.ProductService;

@Service
public class JPAProductService implements ProductService{
	
	@PersistenceContext
	private EntityManager em;

	
	
	@Override
	@Transactional

	public Product createProduct() throws BusinessException {

        Product product = new Product();
        product.setActive(true);
        Rating rating = new Rating();
        em.persist(rating);
        product.setRating(rating);   
        em.persist(product);
        return product;

	}
	
	
	@Override
	@Transactional
	public void updateProduct(Product product, long seller_id) throws BusinessException {
		
		Product p = em.find(Product.class, product.getId());
        Seller s = em.find(Seller.class, seller_id);
        product.setImages(p.getImages());
        
        if(p.getSeller() != null && s != p.getSeller()){
	        p.getSeller().deleteProduct(p);
        }
        
        product.setSeller(s);
        product = em.merge(product);
        s.addProduct(product);   
	}

	
	@Override
	@Transactional
	public ResponseGrid<Product> viewProducts(RequestGridProducts requestGrid) throws BusinessException {

		//Dati per la query
	    boolean active = true;
        Date today = new Date();
        String sortCol = requestGrid.getSortCol();
        String sortDir = requestGrid.getSortDir();
        Long categoryId = requestGrid.getCategoryId();
        Long sellerId = requestGrid.getSellerId();

        int minRows = (int) (long) requestGrid.getiDisplayStart();
        int maxRows = (int) (long) requestGrid.getiDisplayLength();
        
        String search = ConversionUtility.addPercentSuffix(requestGrid.getsSearch().toLowerCase());
        //Criteria Builider
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> q = cb.createQuery(Product.class);
        Root<Product> p = q.from(Product.class);
        //Seleziona tutti i prodotti...
        q.select(p);
        
        /*Predicati per la clausola where - START*/
        
        Predicate categoryPredicate;
        
		if(categoryId != null){
			Category category = em.find(Category.class, categoryId);
			if(!category.getChilds().isEmpty()){
				List<Predicate> lp= new ArrayList<Predicate>();
				for(Iterator<Category> i = category.getChilds().iterator(); i.hasNext();){
					Category c = (Category)i.next();
					lp.add(cb.equal(p.get("category").as(Category.class),c));
				}
				categoryPredicate = cb.or(lp.toArray(new Predicate[lp.size()]));
			}else{
				categoryPredicate = cb.equal(p.get("category").as(Category.class),category);
			}	
		}else{
			categoryPredicate = cb.and();
		}
		
		Predicate sellerPredicate;
		if(sellerId != null){
			Seller seller = em.find(Seller.class, sellerId);
			sellerPredicate = cb.equal(p.get("seller").as(Seller.class),seller);
		}else{
			sellerPredicate = cb.and();
		}
		
		/*Predicati END*/

        /*MAIN PREDICATE*/
        Predicate predicate = cb.and(
									cb.equal(p.get("active").as(Boolean.class), active),
									cb.lessThanOrEqualTo(p.get("dateIn").as(Date.class), today),
									cb.greaterThanOrEqualTo(p.get("dateOut").as(Date.class), today),
									categoryPredicate,
									sellerPredicate,
									cb.or(
					        				cb.like(cb.lower(p.get("name").as(String.class)),search),
					        				cb.like(cb.lower(p.get("description").as(String.class)),search),
					        				cb.like(cb.lower(p.get("price").as(String.class)),search)
					    			)
		);
        
        /*Setto la clausola where con il predicato costruito*/
        q.where(predicate);
        
        //imposto orderby asc or desc
        if(sortDir.equals("ASC"))
        	q.orderBy(cb.asc(p.get(sortCol)));
        else
        	q.orderBy(cb.desc(p.get(sortCol)));
        
        TypedQuery<Product> query = em.createQuery(q);
        query.setMaxResults(maxRows);
        query.setFirstResult(minRows);
        List<Product> products = query.getResultList();
        Long records = (long) products.size();

        /*Creo una seconda query per effettuare il conteggio delle righe,in cui utilizzo lo stesso predicato per il where costruito per la prima*/
        CriteriaQuery<Long> qc = cb.createQuery(Long.class);
        qc.select(cb.count(p));
        qc.where(predicate);
        Long totalRecords = em.createQuery(qc).getSingleResult();

		return new ResponseGrid<Product>(requestGrid.getsEcho(), totalRecords, records, products);

	}
	
	@Override
	@Transactional
	public ResponseGrid<Product> viewProductsBySellerIdPaginated(RequestGrid requestGrid,long seller_id) throws BusinessException {
		
	    //Dati per la query

		boolean active = true;
        String sortCol = requestGrid.getSortCol().equals("category.name") ? "category" : requestGrid.getSortCol();
        sortCol = requestGrid.getSortCol().equals("seller.company") ? "seller" : requestGrid.getSortCol();
        String sortDir = requestGrid.getSortDir();
        int minRows = (int) (long) requestGrid.getiDisplayStart();
        int maxRows = (int) (long) requestGrid.getiDisplayLength();
        String search  = ConversionUtility.addPercentSuffix(requestGrid.getsSearch());

        //Criteria Builider
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> q = cb.createQuery(Product.class);
        Root<Product> p = q.from(Product.class);
        
        User u = em.find(User.class, seller_id);
        //La clausola cb.and() e' sempre vera - viene usata se l'utente loggato ha ruolo admin
        Predicate adminOrSeller =  u.getRoles().contains(new Role("admin")) ? cb.and() : cb.equal(p.get("seller"), u);
             
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

		return new ResponseGrid<Product>(requestGrid.getsEcho(), totalRecords, totalRecords, products);

	}

	// CATEGORIES //
	
	@Override
	@Transactional
	public void createCategory(Category category) throws BusinessException {
	
        Category parent = em.find(Category.class, category.getParent().getId());
        if(parent != null){
        	parent.addChild(category);	
        }else{
        	category.setParent(null);
        	em.persist(category);
        }   
	}
	
	@Override
	@Transactional
	public void updateCategory(Category category) throws BusinessException {
		
        Category c = em.find(Category.class, category.getId());
        //prendo il parent precedente
        if(c.getParent() != null){
        	Category oldParent = em.find(Category.class,c.getParent().getId());
            //rimuovo la referenza al figlio (se il padre e' null non faccio niente)
        	oldParent.removeChild(c);
        }
        //riassegno il figlio al nuovo parent
        Category newParent = em.find(Category.class, category.getParent().getId());
        if(newParent != null) newParent.addChild(c); else category.setParent(null);
        em.merge(category);
	}
	
	
	@Override
	@Transactional
	public void deleteCategory(long categoryId) {
		Category c = em.find(Category.class,categoryId);
		//per ogni child setto il parent a null
		List<Category> childs = c.getChilds();	
		for(Iterator<Category> i = childs.iterator();i.hasNext();){
			Category child = i.next();
			child.setParent(null);
		}	
		//se esiste il padre, da questo rimuovo la referenza al figlio
		if(c.getParent()!=null){
			Category parent = em.find(Category.class, c.getParent().getId());
			parent.getChilds().remove(c);
		}
		//se ho prodotti associati, elimino l'associazione
		c.getProducts().clear();
		
		//infine rimuovo il nodo 
		em.remove(c);
	}
	
	
	@Override
	@Transactional
	public Category findCategoryById(long id) throws BusinessException {
		Category category = em.find(Category.class, id);	
		return category;
	}
	
	@Override
	@Transactional
	public List<Category> findAllCategories() throws BusinessException {
	
		TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
		List<Category> categories = query.getResultList();
		return categories;
		
	}
	

	@Override
	@Transactional
	public List<Category> findAllRootCategories() throws BusinessException {
		TypedQuery<Category>  query = em.createQuery("SELECT c FROM Category c WHERE c.parent IS NULL", Category.class);
		List<Category> categories =   query.getResultList();
		return categories;
		
	}

	
	@Override
	@Transactional
	public Product findProductById(long id) throws BusinessException {
		Product product = em.find(Product.class, id);	
		return product;
		
	}


	@Override
	@Transactional
	public void deleteProduct(Product product,long seller_id) {
		
//		Seller s = em.find(Seller.class,seller_id);
		product.setActive(false);
		product = em.merge(product);
//		s.deleteProduct(product);
	}

	

	
	/* Measures */

	@Override
	@Transactional
	public List<Measure> findAllMeasures() throws BusinessException {
		TypedQuery<Measure> query = em.createQuery("SELECT m FROM Measure m", Measure.class);
		List<Measure> measures = query.getResultList();
		return measures;
		
	}


	@Override
	@Transactional
	public void createMeasure(Measure measure) throws BusinessException {

        em.persist(measure); 
       
	}


	@Override
	@Transactional
	public void updateMeasure(Measure measure) throws BusinessException {

        em.merge(measure);
	}
	
	@Override
	@Transactional
	public Measure findMeasureById(long id) throws BusinessException {
		Measure measure = em.find(Measure.class, id);	
		return measure;
		
	}	

	
	@Override
	@Transactional
	public void deleteMeasure(Measure measure) throws BusinessException {
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
		
	}

	
	@Override
	@Transactional
	public List<Product> getFavouriteProducts() {
		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.active=:active", Product.class);
		query.setParameter("active", true);
		query.setMaxResults(3);
		List<Product> products = query.getResultList();
		return products;
		
	}	
	

	@Override
	@Transactional
	public List<Product> getSameCategoryProducts(Long prodId) {
		Product product = em.find(Product.class, prodId);
		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.category=:category AND p.active=:active", Product.class);
		query.setParameter("category", product.getCategory());
		query.setParameter("active", true);
		List<Product> products = query.getResultList();
		products.remove(product);
		return products;
		
	}

	@Override
	@Transactional
	public List<Product> getAllProducts() {
		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.active=:active", Product.class);
		query.setParameter("active", true);
		List<Product> products = query.getResultList();
		return products;
		
	}
}
