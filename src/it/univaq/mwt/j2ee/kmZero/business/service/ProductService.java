package it.univaq.mwt.j2ee.kmZero.business.service;

import java.util.Collection;
import java.util.List;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Category;
import it.univaq.mwt.j2ee.kmZero.business.model.Image;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;


public interface ProductService {
	
	// Metodi per i prodotti
		
	void createProduct(Product product, long seller_id) throws BusinessException;
	
	void updateProduct(Product product,List<Image> images, long seller_id) throws BusinessException;
	
	void deleteProduct(Product product, long seller_id);
	
	ResponseGrid<Product> viewProducts(RequestGrid requestGrid)	throws BusinessException;
	
	ResponseGrid<Product> viewProductsBySellerIdPaginated(RequestGrid requestGrid, long seller_id) throws BusinessException;
	
	//ResponseGrid<Product> viewProductsBySellerIdPaginated(RequestGrid requestGrid, Seller seller) throws BusinessException;

	Product findProductById(long id) throws BusinessException;
	
	// Metodi per le categorie
	
	void createCategory(Category category) throws BusinessException;
	
	void updateCategory(Category category) throws BusinessException;
	
	void deleteCategory(Category category) throws BusinessException;
	
	List<Category> findAllCategories() throws BusinessException;
	
	Category findCategoryById(long id) throws BusinessException;

	// Metodi per la validazione (chiamato da ImageValidator)
	
	boolean checkProductProperty(long sellerId, long prodId) throws BusinessException;




}
