package it.univaq.mwt.j2ee.kmZero.business.service;

import java.util.Collection;
import java.util.List;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Password;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.SellerContent;
import it.univaq.mwt.j2ee.kmZero.business.model.User;

public interface UserService {
	
	void createUser(User user) throws BusinessException;
	
	void updateUser(User user) throws BusinessException;
	
	void deleteUser(User user) throws BusinessException;
	
	User findUserById(long id) throws BusinessException;
	
	User findUserSellerById(long id) throws BusinessException;
	
	ResponseGrid<User> viewAllUsersPaginated(RequestGrid requestGrid) throws BusinessException;
	
	void createSeller(Seller seller) throws BusinessException;
	
	void updateSeller(Seller seller) throws BusinessException;
	
	void updateSellerByAdmin(Seller seller) throws BusinessException;
	
	void deleteSeller(Long sellerId) throws BusinessException;
	
	void upgradeSeller(Seller seller) throws BusinessException;
	
	Seller findSellerById(long id) throws BusinessException;
	
	ResponseGrid<Seller> viewAllSellersToEnablePaginated(RequestGrid requestGrid) throws BusinessException;
	
	ResponseGrid<Seller> viewAllSellersEnabledPaginated(RequestGrid requestGrid) throws BusinessException;
	
	List<Seller> viewAllSellers() throws BusinessException;
	
	List<Seller> getSellersFromPaidCarts() throws BusinessException;
	
	void editPassword(long id, String password) throws BusinessException;
	
	String oldPassword(long id) throws BusinessException;
	
//	void editSellerContent(Seller seller) throws BusinessException;
	
	boolean emailExist(String email) throws BusinessException;

	
	boolean checkSellerContentProperty(long sellerId, long sellerContentId) throws BusinessException;

	ResponseGrid<SellerContent> viewAllPageContentsPaginated(RequestGrid requestGrid,long seller_id) throws BusinessException;

	void createPageContent(SellerContent content, long userId) throws BusinessException;

	void updatePageContent(SellerContent content, long userId) throws BusinessException;

	void deletePageContent(long contentId, long userId) throws BusinessException;
	
	SellerContent findSellerContentById(long id) throws BusinessException;

	/*Featured Items*/
	
	List<Seller> getFavouriteSellers() throws BusinessException;

	List<Seller> getAllSellers() throws BusinessException;

}
