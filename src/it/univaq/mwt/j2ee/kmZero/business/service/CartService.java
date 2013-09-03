package it.univaq.mwt.j2ee.kmZero.business.service;

import java.util.Collection;
import java.util.Date;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.ResponseCarts;
import it.univaq.mwt.j2ee.kmZero.business.model.Cart;
import it.univaq.mwt.j2ee.kmZero.business.model.CartLine;
import it.univaq.mwt.j2ee.kmZero.business.model.Rating;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.User;

public interface CartService {
	
	Cart createCart(String address, long id_product, int quantity, User user) throws BusinessException;
	
	Cart addCartLine(long id_product, int quantity, User user) throws BusinessException;
	
	void deleteCartLine(long id_cartline, long id_cart) throws BusinessException;
	
	// Visualizza le CartLine e controlla se il carrello esiste oppure no
	ResponseCarts<CartLine> viewCartlines(User user) throws BusinessException;

	Cart findCartById(long id) throws BusinessException;
	
	// Trova il carrello sul quale fare il Checkout passandogli i dati dell'utente
	Cart findCartToCheckout(long id, String email) throws BusinessException;

	// Il carrello e' stato pagato
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
	
	// Il carrello anonimo viene reso persistente
	ResponseCarts<CartLine> persistCartSession(Cart cart, User user) throws BusinessException;

	Rating findRatingById(long ratingId) throws BusinessException;
	
	void emptyCart(long cartId) throws BusinessException;

}
