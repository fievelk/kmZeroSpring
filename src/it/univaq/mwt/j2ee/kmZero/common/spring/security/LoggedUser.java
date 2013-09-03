package it.univaq.mwt.j2ee.kmZero.common.spring.security;

import it.univaq.mwt.j2ee.kmZero.business.model.Role;

import org.springframework.security.core.context.SecurityContextHolder;

public class LoggedUser {
	public UserDetailsImpl getUserDetails() {
		 return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	}
	
	//da errore perche' dice che non e' possibile effettuare un cast da UserDetailsImpl a User
	//it.univaq.mwt.j2ee.kmZero.common.spring.security.UserDetailsImpl cannot be cast to it.univaq.mwt.j2ee.kmZero.business.model.User
/*	public User getUserDetails() {
		 return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	}*/

	public boolean isAdmin() {
		return this.getUserDetails().getRoles().contains(new Role("admin"));
	}
}
