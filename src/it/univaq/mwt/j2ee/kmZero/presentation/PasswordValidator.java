package it.univaq.mwt.j2ee.kmZero.presentation;

import it.univaq.mwt.j2ee.kmZero.business.model.Password;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.UserDetailsImpl;
import it.univaq.mwt.j2ee.kmZero.common.spring.validation.ValidationUtility;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class PasswordValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> klass) {
		return Password.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Password password = (Password) target;
		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(!udi.isAdmin()){
			ValidationUtils.rejectIfEmpty(errors, "password.oldPassword", "errors.required");
			if (password.getOldPassword() != null){
				ValidationUtility.checkPassword(errors, "password.oldPassword", "errors.oldPassword", password.getDbPassword(), DigestUtils.md5Hex(password.getOldPassword()));
			}
		}
		
		ValidationUtils.rejectIfEmpty(errors, "password.password", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "password.confirmPassword", "errors.required");
		
		if (password.getPassword() != null){
			ValidationUtility.checkPassword(errors, "password.confirmPassword", "errors.password", password.getPassword(), password.getConfirmPassword());
		}
	}

}
