package it.univaq.mwt.j2ee.kmZero.presentation;

import it.univaq.mwt.j2ee.kmZero.business.model.Password;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.common.spring.validation.ValidationUtility;

import org.apache.commons.codec.digest.DigestUtils;
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
		/*User user = (User) target;
		
		ValidationUtils.rejectIfEmpty(errors, "password.old_password", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "password.password", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "password.confirm_password", "errors.required");
		
		if (user.getPassword().getOld_password() != null && user.getPassword().getPassword() != null){
			ValidationUtility.checkPassword(errors, "password.confirm_password", "errors.old_password", user.getPassword().getDb_password(), user.getPassword().getOld_password());
			ValidationUtility.checkPassword(errors, "password.confirm_password", "errors.password", user.getPassword().getPassword(), user.getPassword().getConfirm_password());
		}*/
		
		Password password = (Password) target;
		
		ValidationUtils.rejectIfEmpty(errors, "password.old_password", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "password.password", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "password.confirm_password", "errors.required");
		
		if (password.getOld_password() != null){
			ValidationUtility.checkPassword(errors, "password.old_password", "errors.old_password", password.getDb_password(), DigestUtils.md5Hex(password.getOld_password()));
		}
		
		if (password.getPassword() != null){
			ValidationUtility.checkPassword(errors, "password.confirm_password", "errors.password", password.getPassword(), password.getConfirm_password());
		}
	}

}
