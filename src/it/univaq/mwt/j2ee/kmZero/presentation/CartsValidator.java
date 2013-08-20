package it.univaq.mwt.j2ee.kmZero.presentation;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.model.Cart;
import it.univaq.mwt.j2ee.kmZero.business.service.CartService;
import it.univaq.mwt.j2ee.kmZero.common.spring.validation.ValidationUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CartsValidator implements Validator{
	
	 @Autowired
	 private CartService service;

	@Override
	public boolean supports(Class<?> klass) {
		return Cart.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Cart cart = (Cart) target;
		Cart tmp = null;
		try {
			tmp = service.findCartById(cart.getId());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ValidationUtils.rejectIfEmpty(errors, "delivery_date", "errors.required");
		if (cart.getDelivery_date() != null){
			ValidationUtility.checkDelivery_date(errors, "delivery_date", "errors.delivery_date", cart.getCartLines(), cart.getDelivery_date());
		}
		
	}

}
