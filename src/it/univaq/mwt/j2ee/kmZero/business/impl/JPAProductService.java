package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Category;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.service.ProductService;

@Service
public class JPAProductService implements ProductService{

	@Override
	public void createProduct(Product product) throws BusinessException {
		
		//Questa parte delle categoria andrà rimossa (quando le categorie saranno già presenti nel DB)
		Category cat = new Category();
		cat.setName("Cat1");
		Category cat2 = new Category();
		cat2.setName("Cat2");
		Category cat3 = new Category();
		cat3.setName("Cat3");
		
		product.setCategory(cat);
		product.setActive(true);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
        
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        em.persist(cat);
        em.persist(cat2);
        em.persist(cat3);
        em.persist(product);

        
        tx.commit();
        
        em.close();
        emf.close();
	}	
	
		
	@Override
	public void updateProduct(Product product) throws BusinessException {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        
        em.merge(product);
        
        tx.commit();
        
        em.close();
        emf.close();
		
	}

	// Mostra tutti i prodotti con active=1 e date nel range (lato User)
	@Override
	public ResponseGrid<Product> viewProducts(RequestGrid requestGrid) throws BusinessException {
		
		if ("id".equals(requestGrid.getSortCol())) {
			requestGrid.setSortCol("p.id");
		} else {
			requestGrid.setSortCol("p." + requestGrid.getSortCol());
		}
		
		Date today = new Date();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
        
        EntityTransaction tx = em.getTransaction();
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
        emf.close();
		
		return new ResponseGrid(requestGrid.getsEcho(), records, records, products);
	}

	
	@Override
	public ResponseGrid<Product> viewProductsBySellerIdPaginated(RequestGrid requestGrid) throws BusinessException {
		
/*		if ("id".equals(requestGrid.getSortCol())) {
			requestGrid.setSortCol("p.id");
		} else {
			if ("category.name".equals(requestGrid.getSortCol())) {
				requestGrid.setSortCol("p.category.name");
			} else {
				requestGrid.setSortCol("p." + requestGrid.getSortCol());
			}
			
		} */
		
		if ("id".equals(requestGrid.getSortCol())) {
			requestGrid.setSortCol("p.id");
		} else {
			requestGrid.setSortCol("p." + requestGrid.getSortCol());
		}
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
        
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        int minRows = (int) (long) (requestGrid.getiDisplayStart() + 1); // Doppio cast per ottenere le rows minime + 1
        int maxRows = (int) (long) requestGrid.getiDisplayLength(); // Doppio cast per ottenere le rows massime
        
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
        emf.close();
		
		return new ResponseGrid(requestGrid.getsEcho(), records, records, products);
	}

	@Override
	public List<Category> findAllCategories() throws BusinessException {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
		List<Category> categories = query.getResultList();
		
		return categories;
	}

	@Override
	public Product findProductById(long id) throws BusinessException {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Product product = em.find(Product.class, id);
		
		tx.commit();
		em.close();
		emf.close();
		
		return product;
	}


	@Override
	public void deleteProduct(Product product) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		System.out.println("METODO DELETE");
		product = em.merge(product); // Esegue l'attachment del product
		product.setActive(false);
		
		tx.commit();
		em.close();
		emf.close();
		
	}

}
