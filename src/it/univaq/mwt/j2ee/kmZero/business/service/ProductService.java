package it.univaq.mwt.j2ee.kmZero.business.service;

import java.util.Collection;
import java.util.List;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Category;
import it.univaq.mwt.j2ee.kmZero.business.model.Image;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;


public interface ProductService {
		
	void createProduct(Product product) throws BusinessException;
	
	ResponseGrid<Product> viewProductsBySellerIdPaginated(RequestGrid requestGrid) throws BusinessException;
	
	void updateProduct(Product product,List<Image> images) throws BusinessException;
	
	ResponseGrid<Product> viewProducts(RequestGrid requestGrid)	throws BusinessException;

	List<Category> findAllCategories() throws BusinessException;

	Product findProductById(long id) throws BusinessException;

	void deleteProduct(Product product);


}
