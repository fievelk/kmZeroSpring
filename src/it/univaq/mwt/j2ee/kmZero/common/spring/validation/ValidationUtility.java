package it.univaq.mwt.j2ee.kmZero.common.spring.validation;

import it.univaq.mwt.j2ee.kmZero.business.model.Password;

import org.springframework.validation.Errors;

public class ValidationUtility {

	public static void rejectIfMaxLength(Errors errors, String fieldName, String errorMessage, String fieldValue, int maxlength) {
		if (fieldValue != null && fieldValue.length() > maxlength) {
			Object[] args = { maxlength };
			errors.rejectValue(fieldName, errorMessage, args, "");
		}
	}
	
	public static void checkPassword(Errors errors, String fieldName, String errorMessage, Password p){
		/*if (!cp.equals("") && !p.equals(cp)){
			errors.rejectValue(fieldName, errorMessage);
		}*/
		if (!p.getConfirm_password().equals("") && !p.getPassword().equals(p.getConfirm_password())){
			errors.rejectValue(fieldName, errorMessage);
		}
	}
	
	public static void checkEmail(Errors errors, String fieldName, String errorMessage, String e){
		if (!e.contains("@") && !e.equals("") ){
			errors.rejectValue(fieldName, errorMessage);
		}
	}
}
