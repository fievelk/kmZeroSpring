package it.univaq.mwt.j2ee.kmZero.business.service;

import java.util.Date;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.ResponseCarts;
import it.univaq.mwt.j2ee.kmZero.business.model.Cart;
import it.univaq.mwt.j2ee.kmZero.business.model.CartLine;

public interface CartService {
	
	void createCart(long id_product, int quantity, String session_id) throws BusinessException;
	
	void deleteCartLine(long id) throws BusinessException;
	
	// Visualizza le CartLine
	ResponseCarts<CartLine> viewCartlines(String session_id) throws BusinessException;

	Cart findCartById(long id) throws BusinessException;
	
	// Trova il carrello sul quale fare il Checkout passandogli i dati dell'utente
	Cart findCartToCheckout(long id, String name, String surname, Date created) throws BusinessException;

	// Il carrello è stato pagato
	void paid(String transaction_id, long cart_id) throws BusinessException;

}
