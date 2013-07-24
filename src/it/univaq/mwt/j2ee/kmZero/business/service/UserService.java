package it.univaq.mwt.j2ee.kmZero.business.service;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.User;

public interface UserService {
	
	void createUser(User user) throws BusinessException;
	
	void updateUser(User user) throws BusinessException;
	
	void deleteUser(User user) throws BusinessException;
	
	User findUserById(long id) throws BusinessException;
	
	ResponseGrid<User> viewAllUsersPaginated(RequestGrid requestGrid) throws BusinessException;
	
	void createSeller(Seller seller) throws BusinessException;
	
	void updateSeller(Seller seller) throws BusinessException;
	
	void updateSellerByAdmin(Seller seller) throws BusinessException;
	
	void deleteSeller(Seller seller) throws BusinessException;
	
	void upgradeSeller(User user) throws BusinessException;
	
	Seller findSellerById(long id) throws BusinessException;
	
	ResponseGrid<Seller> viewAllSellersToEnablePaginated(RequestGrid requestGrid) throws BusinessException;
	
	ResponseGrid<Seller> viewAllSellersEnabledPaginated(RequestGrid requestGrid) throws BusinessException;

}
