package it.univaq.mwt.j2ee.kmZero.common.spring.security;

import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.model.User;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.InitBinder;

public class LoggedUser {
	public UserDetailsImpl getUserDetails() {
		 return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	}
	
	//da errore perchè dice che non è possibile effettuare un cast da UserDetailsImpl a User
	//it.univaq.mwt.j2ee.kmZero.common.spring.security.UserDetailsImpl cannot be cast to it.univaq.mwt.j2ee.kmZero.business.model.User
/*	public User getUserDetails() {
		 return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	}*/

	public boolean isAdmin() {
		return this.getUserDetails().getRoles().contains(new Role("admin"));
	}
}
