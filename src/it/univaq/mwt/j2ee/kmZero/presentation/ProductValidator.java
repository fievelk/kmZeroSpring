package it.univaq.mwt.j2ee.kmZero.presentation;

import java.math.BigDecimal;

import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.common.spring.validation.ValidationUtility;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> klass) {
		return Product.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Product product = (Product) target;
		
		ValidationUtils.rejectIfEmpty(errors, "name", "errors.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "errors.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stock", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "measure.id", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "category.id", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "date_in", "errors.required");
		ValidationUtils.rejectIfEmpty(errors, "date_out", "errors.required");
		
		ValidationUtility.rejectIfMaxLength(errors, "name", "errors.maxlength", product.getName(), 255);
		ValidationUtility.rejectIfMaxLength(errors, "description", "errors.maxlength", product.getDescription(), 500);
		
		ValidationUtility.rejectIfNegativeBD(errors, "price", "errors.notNegative", product.getPrice());
		ValidationUtility.rejectIfNegative(errors, "stock", "errors.notNegative", product.getStock());
		

	}
	

}
