package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Category;
import it.univaq.mwt.j2ee.kmZero.business.model.Image;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.service.ProductService;

public class JPAProductService implements ProductService{
	
	@PersistenceUnit
	private EntityManagerFactory emf;

	@Override
	public void createProduct(Product product) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		//Questa parte delle categoria andrà rimossa (quando le categorie saranno già presenti nel DB)
		/*Category cat = new Category();
		cat.setName("Cat1");
		Category cat2 = new Category();
		cat2.setName("Cat2");
		Category cat3 = new Category();
		cat3.setName("Cat3");
		
		product.setCategory(cat);*/
		product.setActive(true);
		
        tx.begin();
        
		Seller seller = em.find(Seller.class, 126L); // Cambiare l'ID finché si fanno le prove. Poi eliminare del tutto questa parte
		System.out.println("SELLERNAME " + seller.getName());
		//em.merge(seller);
		product.setSeller(seller);
        
       // em.persist(cat);
       // em.persist(cat2);
       // em.persist(cat3);
        em.persist(product);

        
        tx.commit();
        em.close();
       
	}	
	
		
	@Override
	public void updateProduct(Product product) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Collection<Image> images = getProductImages(product.getId());
        
        product.setImages(images);
        em.merge(product);
           
        tx.commit();
        em.close();
        		
	}

	// Mostra tutti i prodotti con active=1 e date nel range (lato User)
	@Override
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
        
		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.active=1 AND :today BETWEEN p.date_in AND p.date_out" +
				 ((!"".equals(requestGrid.getsSearch())) ? " AND p.name LIKE '" + ConversionUtility.addPercentSuffix(requestGrid.getsSearch()) + "'" : "") +
				 ((!"".equals(requestGrid.getSortCol()) && !"".equals(requestGrid.getSortDir())) ? " order by " + requestGrid.getSortCol() + " " + requestGrid.getSortDir() : ""), Product.class);

		query.setMaxResults(maxRows);
		query.setFirstResult(minRows);
		
		query.setParameter("today", today);
		List<Product> products = query.getResultList();
		
		Query count = em.createQuery("SELECT COUNT (p) FROM Product p WHERE p.active=1");
		Long records = (Long) count.getSingleResult();
        
        tx.commit();
        
        em.close();
        		
		return new ResponseGrid(requestGrid.getsEcho(), records, records, products);
	}

	
	@Override
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
        
/*		VERSIONE CON IL SELLER */
 		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.active=1 and p.seller = :seller" +
				 ((!"".equals(requestGrid.getsSearch())) ? " AND p.name LIKE '" + ConversionUtility.addPercentSuffix(requestGrid.getsSearch()) + "'" : "") +
				 ((!"".equals(requestGrid.getSortCol()) && !"".equals(requestGrid.getSortDir())) ? " order by " + requestGrid.getSortCol() + " " + requestGrid.getSortDir() : ""), Product.class);

		query.setParameter("seller", seller);
        
/*		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.active=1" +
				 ((!"".equals(requestGrid.getsSearch())) ? " AND p.name LIKE '" + ConversionUtility.addPercentSuffix(requestGrid.getsSearch()) + "'" : "") +
				 ((!"".equals(requestGrid.getSortCol()) && !"".equals(requestGrid.getSortDir())) ? " order by " + requestGrid.getSortCol() + " " + requestGrid.getSortDir() : ""), Product.class);
*/
		query.setMaxResults(maxRows);
		query.setFirstResult(minRows);
		
		List<Product> products = query.getResultList();
		
		Query count = em.createQuery("SELECT COUNT (p) FROM Product p WHERE p.active=1");
		Long records = (Long) count.getSingleResult();
        
        tx.commit();
        em.close();
        
		return new ResponseGrid(requestGrid.getsEcho(), records, records, products);
	}


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
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		category = em.merge(category);
		em.remove(category);

		tx.commit();
		em.close();
	
	}
	
	
	@Override
	public Category findCategoryById(long id) throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		Category category = em.find(Category.class, id);	
		em.close();
		return category;
	}
	
	@Override
	public List<Category> findAllCategories() throws BusinessException {
	
		EntityManager em = this.emf.createEntityManager();
		
		TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
		List<Category> categories = query.getResultList();
		em.close();
		return categories;
	}

	
	@Override
	public Product findProductById(long id) throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		Product product = em.find(Product.class, id);	
		em.close();
		return product;
	}


	@Override
	public void deleteProduct(Product product) {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		product = em.merge(product); // Esegue l'attachment del product
		product.setActive(false);
		
		tx.commit();
		em.close();
	
	}


	@Override
	public void setProductImages(Long id, Collection<Image> ci) throws BusinessException {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		Product p = findProductById(id);
		//riprendo la collezione di immagini gi� associate all'oggetto e...
		Collection<Image> c = new HashSet<Image>(p.getImages());
		//...aggiungo la nuova collezione (le fondo assieme)
		c.addAll(ci);
		p.setImages(c);
		em.merge(p);
		tx.commit();
		em.close();
	}

	//questo metodo recupera solo id e nome dell'immagine (se funzionasse lazy fetch...)
	//Ritorna solo l'id e il nome dell'imagine senza i dati blob
	@Override
	public Collection<Image> getProductImagesIdName(Long id) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
				
		TypedQuery<Image> query = em.createQuery("SELECT NEW it.univaq.mwt.j2ee.kmZero.business.model.Image(i.id,i.name) FROM Image i JOIN Product p WHERE p.id=?1", Image.class);
		query.setParameter(1, id);
		List<Image> images = query.getResultList();
		
		em.close();
		
		return images;	
	}


	@Override
	public Collection<Image> getProductImages(Long id) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
	
		Product p = em.find(Product.class, id);

		return p.getImages();
	}



	@Override
	public boolean deleteImage(Long id, Long product_id) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		
		boolean val=false;
		try {		
			tx.begin();
			Image i = em.find(Image.class, id);
			Product p = em.find(Product.class, product_id);
			em.merge(p);
			p.getImages().remove(i);
			tx.commit();
			val=true;
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		em.close();
		return val;
	}




}
