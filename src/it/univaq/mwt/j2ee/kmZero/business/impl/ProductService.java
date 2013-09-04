package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.List;


import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.RequestGridProducts;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Category;
import it.univaq.mwt.j2ee.kmZero.business.model.Measure;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;


public interface ProductService {
	
	// Metodi per i prodotti
		
	Product createProduct() throws BusinessException;
	
	void updateProduct(Product product, long seller_id) throws BusinessException;
	
	void deleteProduct(Product product, long seller_id);
	
	ResponseGrid<Product> viewProducts(RequestGridProducts requestGrid)	throws BusinessException;
	
	ResponseGrid<Product> viewProductsBySellerIdPaginated(RequestGrid requestGrid, long seller_id) throws BusinessException;
	
	Product findProductById(long id) throws BusinessException;
	
	// Metodi per le categorie
	
	void createCategory(Category category) throws BusinessException;
	
	void updateCategory(Category category) throws BusinessException;
	
	void deleteCategory(long categoryId) throws BusinessException;
	
	List<Category> findAllCategories() throws BusinessException;
	
	List<Category> findAllRootCategories() throws BusinessException;
	
	Category findCategoryById(long id) throws BusinessException;

	// Metodi per le unità di misura
	
	List<Measure> findAllMeasures() throws BusinessException;

	void createMeasure(Measure measure) throws BusinessException;

	void updateMeasure(Measure measure) throws BusinessException;

	Measure findMeasureById(long id) throws BusinessException;

	void deleteMeasure(Measure measure) throws BusinessException;
	
	// Metodi per ricerche
	
	List<Product> getFavouriteProducts() throws BusinessException;

	List<Product> getSameCategoryProducts(Long prodId) throws BusinessException;

	List<Product> getAllProducts() throws BusinessException;

}
