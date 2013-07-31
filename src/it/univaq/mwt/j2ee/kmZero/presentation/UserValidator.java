package it.univaq.mwt.j2ee.kmZero.presentation;

import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.common.spring.validation.ValidationUtility;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> klass) {
		return User.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		ValidationUtils.rejectIfEmpty(errors, "name", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "surname", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "email", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "date_of_birth", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "address", "errors.required");
		
		ValidationUtility.rejectIfMaxLength(errors, "name", "errors.maxlength", user.getName(), 255);
		ValidationUtility.rejectIfMaxLength(errors, "surname", "errors.maxlength", user.getSurname(), 255);
		ValidationUtility.rejectIfMaxLength(errors, "address", "errors.maxlength", user.getAddress(), 255);
		
		// Se la Password esiste, ci si trova nel createuserform ed allora fa questa validazione
		if (user.getPassword() != null){
			ValidationUtils.rejectIfEmpty(errors, "password.password", "errors.required");
			ValidationUtils.rejectIfEmpty(errors, "password.confirm_password", "errors.required");
			ValidationUtility.checkPassword(errors, "password.confirm_password", "errors.password", user.getPassword().getPassword(), user.getPassword().getConfirm_password());
		}
		ValidationUtility.checkEmail(errors, "email", "errors.email", user.getEmail());
	}

}
