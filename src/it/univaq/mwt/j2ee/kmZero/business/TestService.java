package it.univaq.mwt.j2ee.kmZero.business;

import java.util.List;


import java.util.Set;

import it.univaq.mwt.j2ee.kmZero.business.model.Cart;
import it.univaq.mwt.j2ee.kmZero.business.model.CartLine;
import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.model.User;


public interface TestService {
	
	void testNumberOne();
	void testNumberTwo();
	void testNumberThree();
	void testNumberFour();
	List<CartLine> testNumberFive();
	Set<Role> getAllRoles();
	User getUser();
	void updateUser(User user);
	List<User> getAllUsersTest();
	List<Cart> getCartsToDeliverTest();
}
