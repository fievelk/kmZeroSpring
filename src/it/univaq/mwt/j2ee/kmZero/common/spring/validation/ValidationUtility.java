package it.univaq.mwt.j2ee.kmZero.common.spring.validation;

import java.util.Collection;

import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.UserDetailsImpl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;

public class ValidationUtility {

	public static void rejectIfMaxLength(Errors errors, String fieldName, String errorMessage, String fieldValue, int maxlength) {
		if (fieldValue != null && fieldValue.length() > maxlength) {
			Object[] args = { maxlength };
			errors.rejectValue(fieldName, errorMessage, args, "");
		}
	}
	
	public static void checkPassword(Errors errors, String fieldName, String errorMessage, String p1, String p2){
		/*if (!cp.equals("") && !p.equals(cp)){
			errors.rejectValue(fieldName, errorMessage);
		}*/
		if (!p2.equals("") && !p1.equals(p2)){
			errors.rejectValue(fieldName, errorMessage);
		}
	}
	
	public static void checkEmail(Errors errors, String fieldName, String errorMessage, String e){
		if (!e.contains("@") && !e.equals("") ){
			errors.rejectValue(fieldName, errorMessage);
		}
	}
	
	public static void existEmail(Errors errors, String fieldName, String errorMessage, boolean b){
		if (b){
			errors.rejectValue(fieldName, errorMessage);
		}
		
	}
	
	public static void checkIdentity(Errors errors, String fieldName, String errorMessage, long id){
		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		long userid = udi.getId();/*
		if (id != userid && !udi.getRoles().contains(new Role(3))){

			errors.rejectValue(fieldName, errorMessage);
		}*/
		
	}
	
	
}
