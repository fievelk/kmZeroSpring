package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.stereotype.Service;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Category;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.business.service.ProductService;

@Service
public class JPAProductService implements ProductService{

	@Override
	public void createProduct(Product product) throws BusinessException {
		
		//Questa parte delle categoria andrà rimossa
		Category cat = new Category();
		cat.setName("Cat1");
		Category cat2 = new Category();
		cat2.setName("Cat2");
		Category cat3 = new Category();
		cat3.setName("Cat3");
		
		product.setCategory(cat);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
        EntityManager em = emf.createEntityManager();
        
        EntityTransaction tx = em.getTransaction();
        System.out.println ("Transaction begins.");
        tx.begin();
        
        em.persist(cat);
        em.persist(cat2);
        em.persist(cat3);
        em.persist(product);

        
        tx.commit();
        System.out.println ("Transaction ends.");
        
        em.close();
        emf.close();
	}	
	
/*	@Override
	public void createProduct(Product product) throws BusinessException {
			
			//Product productvero = new Product();
			//productvero.setName("prodottodiprova");
			
			product.setName("prodottodiprova");
			
			Category cat = new Category();
			cat.setName("Cat1");
			Category cat2 = new Category();
			cat2.setName("Cat2");
			Category cat3 = new Category();
			cat3.setName("Cat3");
			
			product.setCategory(cat);
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("kmz");
	        EntityManager em = emf.createEntityManager();
	        
	        EntityTransaction tx = em.getTransaction();
	        System.out.println ("Transaction begins.");
	        tx.begin();
	        
	        em.persist(cat);
	        em.persist(cat2);
	        em.persist(cat3);
	        em.persist(product);

	        
	        tx.commit();
	        System.out.println ("Transaction ends.");
	        
	        em.close();
	        emf.close();
	}*/
		
	@Override
	public void updateProduct(Product product) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> viewProducts() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseGrid<Product> viewProductsBySellerIdPaginated(
			RequestGrid requestGrid) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> findAllCategories() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product findProductById(long id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
