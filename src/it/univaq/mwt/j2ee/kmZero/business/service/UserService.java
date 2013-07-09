package it.univaq.mwt.j2ee.kmZero.business.service;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.User;

public interface UserService {
	
	void createUser(User user) throws BusinessException;
	
	void updateUser(User user) throws BusinessException;
	
	void deleteUser(User user) throws BusinessException;
	
	User findUserById(long id) throws BusinessException;
	
	ResponseGrid<User> viewAllUsersPaginated(RequestGrid requestGrid) throws BusinessException;

}
