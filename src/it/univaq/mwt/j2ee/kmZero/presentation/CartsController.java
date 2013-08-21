package it.univaq.mwt.j2ee.kmZero.presentation;

import java.util.ArrayList;
import java.util.Collection;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.ResponseCarts;
import it.univaq.mwt.j2ee.kmZero.business.model.Cart;
import it.univaq.mwt.j2ee.kmZero.business.model.CartLine;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.business.service.CartService;
import it.univaq.mwt.j2ee.kmZero.business.service.UserService;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/carts")
public class CartsController {
	
	@Autowired
	private CartService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartsValidator validator;
	
	@RequestMapping("/addressvalidated")
	@ResponseBody
	public String modalAddressStart(@RequestParam("a") String address, @RequestParam("id") long id_product, 
			@RequestParam("q") int quantity, @CookieValue("kmzero") String cookie) throws BusinessException{
		service.createCart(address, cookie, id_product, quantity);
		return null;
	}
	
	@RequestMapping("/viewcartpaginated")
	@ResponseBody
	public ResponseCarts<CartLine> findAllCartLinesPaginated(@CookieValue("kmzero") String cookie) throws BusinessException{
		ResponseCarts<CartLine> result = service.viewCartlines(cookie);
		return result;
	}
	
	/*@RequestMapping("/existcart.do")
	@ResponseBody
	public ResponseCarts<CartLine> existCart(@CookieValue("kmzero") String cookie) throws BusinessException{
		ResponseCarts<CartLine> result = service.existCart(cookie);
		return result;
	}*/
	
	@RequestMapping("/create")
	@ResponseBody
	public String addCartLine(@RequestParam("id") long id_product, @RequestParam("q") int q,
			@CookieValue("kmzero") String cookie) throws BusinessException{
		service.addCartLine(id_product, q, cookie);
		return null;
	}
	
	@RequestMapping("/delete_cartline")
	@ResponseBody
	public String deleteCartLine(@RequestParam("idcl") long id_cl, @RequestParam("idc") long id_c) throws BusinessException{
		service.deleteCartLine(id_cl, id_c);
		return null;
	}
	
	@RequestMapping("/confirmcart_start")
	public String confirmCart(@RequestParam("id") long id, Model model) throws BusinessException{
		String s = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		/* Questo confronto va fatto meglio usando i template */
		if (s.equals("anonymousUser")){
			return "common.login";
		}
		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Cart cart = service.findCartToCheckout(id, udi.getEmail());
		model.addAttribute("cart", cart);
		return "carts.confirm";
	}
	
	@RequestMapping(value="/confirmcart", method=RequestMethod.POST)
	public String checkout(@ModelAttribute Cart cart, BindingResult bindingResult) throws BusinessException{
		validator.validate(cart, bindingResult);
		if (bindingResult.hasErrors()){
			return "carts.confirm";
		}
		service.confirmCart(cart.getId(), cart.getDelivery_date());
		return "carts.checkout";
	}
	
	@RequestMapping("/paid")
	public String paid(@RequestParam("tx") String transaction_id, @RequestParam("cm") long cart_id) throws BusinessException{
		service.paid(transaction_id, cart_id);
		return "carts.paid";
	}
	
	@RequestMapping(value="/userOrderView")
	public String userOrderViewTest(Model model) throws BusinessException {

//		// l'IF si potrà togliere quando si metterà lo strato di sicurezza
//		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
			UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			long id = udi.getId();
			User user = userService.findUserById(id);
			
			// Trovo tutti i carrelli
			Collection<Cart> carts = user.getCart(); 
			
			// Seleziono solo i carrelli pagati e li aggiungo al model
			Collection<Cart> paidCarts = new ArrayList<Cart>();

			for (Cart cart : carts) {
				if (cart.getPaid() != null) {
					paidCarts.add(cart);
				}
			}
			
			model.addAttribute("carts", paidCarts);
			return "carts.userOrderView";
			
//		} else {
//			return "common.login";	
//		}
	}
	
	
	@RequestMapping(value="/updateCartLineRating")
	@ResponseBody
	public void updateCartLineRating(@RequestParam("id") long cartLineId, @RequestParam("r") int rating) {
		CartLine cartLine = service.findCartLineById(cartLineId);
		service.updateCartLineRating(cartLine, rating);
		// Qui deve eseguire un metodo che aggiorni il rating globale del prodotto (media e numero di click)
	}

}
