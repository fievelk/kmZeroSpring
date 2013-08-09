package it.univaq.mwt.j2ee.kmZero.common.spring.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.InitBinder;

public class LoggedUser {

	public UserDetailsImpl getUserDetails() {
		 return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	}

}
