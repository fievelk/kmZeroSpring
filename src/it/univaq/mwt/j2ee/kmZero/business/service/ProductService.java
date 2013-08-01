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
		
	void createProduct(Product product) throws BusinessException;
	
	void deleteProduct(Product product) throws BusinessException;

	
	
	void updateProduct(Product product,List<Image> images) throws BusinessException;
	
	ResponseGrid<Product> viewProducts(RequestGrid requestGrid)	throws BusinessException;
	
	ResponseGrid<Product> viewProductsBySellerIdPaginated(RequestGrid requestGrid) throws BusinessException;
	
	//ResponseGrid<Product> viewProductsBySellerIdPaginated(RequestGrid requestGrid, Seller seller) throws BusinessException;

	Product findProductById(long id) throws BusinessException;
	
	// Metodi per le categorie
	
	void createCategory(Category category) throws BusinessException;
	
	void updateCategory(Category category) throws BusinessException;
	
	void deleteCategory(Category category) throws BusinessException;
	
	List<Category> findAllCategories() throws BusinessException;
	
	Category findCategoryById(long id) throws BusinessException;
	
	// Metodi per le immagini dei prodotti

	void setProductImages(Long id, Collection<Image> ci) throws BusinessException;

}
