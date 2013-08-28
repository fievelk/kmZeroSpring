package it.univaq.mwt.j2ee.kmZero.business.service;

import java.util.Collection;
import java.util.Date;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.ResponseCarts;
import it.univaq.mwt.j2ee.kmZero.business.model.Cart;
import it.univaq.mwt.j2ee.kmZero.business.model.CartLine;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.User;

public interface CartService {
	
	void createCart(String address, String session_id, long id_product, int quantity) throws BusinessException;
	
	void addCartLine(long id_product, int quantity, String session_id) throws BusinessException;
	
	void deleteCartLine(long id_cartline, long id_cart) throws BusinessException;
	
	// Visualizza le CartLine
	ResponseCarts<CartLine> viewCartlines(String session_id) throws BusinessException;
	
	// Il carrello esiste oppure no
	//ResponseCarts<CartLine> existCart(String session_id) throws BusinessException;

	Cart findCartById(long id) throws BusinessException;
	
	// Trova il carrello sul quale fare il Checkout passandogli i dati dell'utente
	Cart findCartToCheckout(long id, String email) throws BusinessException;

	// Il carrello � stato pagato
	void paid(String transaction_id, long cart_id) throws BusinessException;

	// Conferma il carrello prima di fare il checkout
	void confirmCart(long id_cart, Date delivery_date) throws BusinessException;

	CartLine findCartLineById(long cartLineId) throws BusinessException;

	void updateCartLineRating(CartLine cartLine, int rating) throws BusinessException;

	Collection<Cart> getCartsToDeliver() throws BusinessException;

	Collection<Cart> findUserPaidCarts(User user) throws BusinessException;

	Collection<CartLine> findSellerReceivedOrders(Seller seller) throws BusinessException;

	Collection<CartLine> findCartLinesToDeliver() throws BusinessException;

	void createFeedback(CartLine cartLine, String feedback) throws BusinessException;

}
