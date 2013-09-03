package it.univaq.mwt.j2ee.kmZero.presentation;

import it.univaq.mwt.j2ee.kmZero.business.model.Cart;
import it.univaq.mwt.j2ee.kmZero.common.spring.validation.ValidationUtility;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CartsValidator implements Validator{

	@Override
	public boolean supports(Class<?> klass) {
		return Cart.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Cart cart = (Cart) target;
		
		ValidationUtils.rejectIfEmpty(errors, "deliveryDate", "errors.required");
		if (cart.getDeliveryDate() != null){
			ValidationUtility.checkdeliveryDate(errors, "deliveryDate", "errors.deliveryDate", cart.getCartLines(), cart.getDeliveryDate());
		}
	}
}
