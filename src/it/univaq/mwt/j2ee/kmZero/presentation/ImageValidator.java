package it.univaq.mwt.j2ee.kmZero.presentation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.service.ProductService;
import it.univaq.mwt.j2ee.kmZero.business.service.UserService;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.LoggedUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;

@Component
public class ImageValidator {
	
	@Autowired
	private LoggedUser loggedUser;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;

	public boolean isAdmin() {
		return loggedUser.getUserDetails().getRoles().contains(new Role("admin"));
	}
	
	public boolean validateSellerImage(long sellerId){
		
		return loggedUser.getUserDetails().getId() == sellerId || isAdmin();
	}
	
	public boolean validateProdImage(long prodId) throws BusinessException{
		
		long userId = loggedUser.getUserDetails().getId();
		return (productService.checkProductProperty(userId, prodId) || isAdmin());
	}
	
	public boolean validateSellerContentImage(long sellerContentId) throws BusinessException{
		long userId = loggedUser.getUserDetails().getId();
		return (userService.checkSellerContentProperty(userId, sellerContentId) || isAdmin());
	}

}
