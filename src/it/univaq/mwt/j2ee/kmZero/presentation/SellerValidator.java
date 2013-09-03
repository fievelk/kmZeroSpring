package it.univaq.mwt.j2ee.kmZero.presentation;

import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.UserDetailsImpl;
import it.univaq.mwt.j2ee.kmZero.common.spring.validation.ValidationUtility;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SellerValidator implements Validator {

	@Override
	public boolean supports(Class<?> klass) {
		return Seller.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Seller seller = (Seller) target;
		
		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ValidationUtils.rejectIfEmpty(errors, "name", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "surname", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "email", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "dateOfBirth", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "phone", "errors.required");
		ValidationUtility.rejectIfMaxLength(errors, "name", "errors.maxlength", seller.getName(), 255);
		ValidationUtility.rejectIfMaxLength(errors, "surname", "errors.maxlength", seller.getSurname(), 255);
		ValidationUtility.checkEmail(errors, "email", "errors.email", seller.getEmail());
		
		if(udi.isAdmin() || udi.toString() == "anonymousUser" || udi.isUser()){
			ValidationUtils.rejectIfEmpty(errors, "address", "errors.required");
			ValidationUtility.rejectIfMaxLength(errors, "address", "errors.maxlength", seller.getAddress(), 255);
		// Questi campi vengono validati solo alla creazione o se e' l'admin a modificare un venditore
		
			ValidationUtils.rejectIfEmpty(errors, "p_iva", "errors.required");
			ValidationUtils.rejectIfEmpty(errors, "cod_fisc", "errors.required");
			ValidationUtils.rejectIfEmpty(errors, "company", "errors.required");
			ValidationUtility.rejectIfMaxLength(errors, "cod_fisc", "errors.maxlength", seller.getCod_fisc(), 16);
			ValidationUtility.rejectIfMaxLength(errors, "p_iva", "errors.maxlength", seller.getP_iva(), 11);
			if(udi.toString() == "anonymousUser"){
				ValidationUtils.rejectIfEmpty(errors, "password.password", "errors.required");
				ValidationUtils.rejectIfEmpty(errors, "password.confirmPassword", "errors.required");
				ValidationUtility.checkPassword(errors, "password.confirmPassword", "errors.password", seller.getPassword().getPassword(), seller.getPassword().getConfirmPassword());
			}
	
		}
		
	}

}
