package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.List;

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
		// TODO Auto-generated method stub
		
	}

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
