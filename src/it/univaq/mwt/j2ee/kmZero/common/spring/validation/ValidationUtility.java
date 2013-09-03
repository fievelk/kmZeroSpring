package it.univaq.mwt.j2ee.kmZero.common.spring.validation;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import it.univaq.mwt.j2ee.kmZero.business.model.CartLine;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;

import org.springframework.validation.Errors;

public class ValidationUtility {

	public static void rejectIfMaxLength(Errors errors, String fieldName, String errorMessage, String fieldValue, int maxlength) {
		if (fieldValue != null && fieldValue.length() > maxlength) {
			Object[] args = { maxlength };
			errors.rejectValue(fieldName, errorMessage, args, "");
		}
	}
	
	public static void checkPassword(Errors errors, String fieldName, String errorMessage, String p1, String p2){
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
	
	public static void checkdeliveryDate(Errors errors, String fieldName, String errorMessage,
			Collection<CartLine> cartLines, Date deliveryDate) {
		Iterator<CartLine> i = cartLines.iterator();
		int x = 0;
		while (i.hasNext()){
			CartLine cl = i.next();
			Product p = cl.getProduct();
			if (deliveryDate.after(p.getDateOut())){
				x++;
			}
		}
		
		if (x > 0){
			errors.rejectValue(fieldName, errorMessage);
		}
		
	}
	
	public static void rejectIfNegativeBD(Errors errors, String fieldName, String errorMessage, BigDecimal value) {
		BigDecimal zero = new BigDecimal("0");
		if (value != null) {
			if (value.compareTo(zero)==-1){
				errors.rejectValue(fieldName, errorMessage);
			}
		}	
	}
	
	public static void rejectIfNegative(Errors errors, String fieldName, String errorMessage, int value) {
			if (value < 0){
				errors.rejectValue(fieldName, errorMessage);
			}
		}	
	
	
}
