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
	
	void updateProduct(Product product) throws BusinessException;
	
	ResponseGrid<Product> viewProducts(RequestGrid requestGrid)	throws BusinessException;

	List<Category> findAllCategories() throws BusinessException;

	Product findProductById(long id) throws BusinessException;

	void deleteProduct(Product product);

	void setProductImages(Long id, Collection<Image> ci) throws BusinessException;

	Image getImage(Long id) throws BusinessException;

	Collection<Image> getProductImages(Long id) throws BusinessException;

	Collection<Image> getProductImagesIdName(Long id) throws BusinessException;

	boolean deleteImage(Long id) throws BusinessException;;



}
