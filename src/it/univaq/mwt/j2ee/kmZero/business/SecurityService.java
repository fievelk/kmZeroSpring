package it.univaq.mwt.j2ee.kmZero.business;

import it.univaq.mwt.j2ee.kmZero.business.model.User;

public interface SecurityService {
	
	User authenticate(String username) throws BusinessException;

}
