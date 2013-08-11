package it.univaq.mwt.j2ee.kmZero.presentation;

import java.util.Date;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.ResponseCarts;
import it.univaq.mwt.j2ee.kmZero.business.model.Cart;
import it.univaq.mwt.j2ee.kmZero.business.model.CartLine;
import it.univaq.mwt.j2ee.kmZero.business.service.CartService;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/carts")
public class CartsController {
	
	@Autowired
	private CartService service;
	
	/*@RequestMapping("/addressvalidation.do")
	@ResponseBody
	public ResponseCarts<CartLine> addressValidation() throws BusinessException{
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		WebAuthenticationDetails wad = (WebAuthenticationDetails) a.getDetails();
		String s = wad.getSessionId();
		ResponseCarts<CartLine> result = service.viewCartlines(s);
		return result;
	}*/
	
	@RequestMapping("/addressvalidated.do")
	@ResponseBody
	public String modalAddressStart(@RequestParam("a") String address, 
			@RequestParam("id") long id_product, @RequestParam("q") int quantity) throws BusinessException{
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		WebAuthenticationDetails wad = (WebAuthenticationDetails) a.getDetails();
		String s = wad.getSessionId();
		service.createCart(address, s, id_product, quantity);
		return null;
	}
	
	@RequestMapping("/viewcartpaginated.do")
	@ResponseBody
	public ResponseCarts<CartLine> findAllUsersPaginated() throws BusinessException{
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		WebAuthenticationDetails wad = (WebAuthenticationDetails) a.getDetails();
		String s = wad.getSessionId();
		ResponseCarts<CartLine> result = service.viewCartlines(s);
		return result;
	}
	
	@RequestMapping("/existcart.do")
	@ResponseBody
	public ResponseCarts<CartLine> existCart() throws BusinessException{
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		WebAuthenticationDetails wad = (WebAuthenticationDetails) a.getDetails();
		String s = wad.getSessionId();
		ResponseCarts<CartLine> result = service.existCart(s);
		return result;
	}
	
	@RequestMapping("/create.do")
	@ResponseBody
	public String addCartLine(@RequestParam("id") long id_product, @RequestParam("q") int q) 
			throws BusinessException{
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		WebAuthenticationDetails wad = (WebAuthenticationDetails) a.getDetails();
		String s = wad.getSessionId();
		service.addCartLine(id_product, q, s);
		return null;
	}
	
	@RequestMapping("/delete_cartline.do")
	@ResponseBody
	public String deleteCartLine(@RequestParam("id") long id) throws BusinessException{
		service.deleteCartLine(id);
		return null;
	}
	
	@RequestMapping("/checkout_start.do")
	public String checkoutStart(@RequestParam("id") long id, Model model) throws BusinessException{
		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//TODO: Qui, oltre al nome, il cognome e la data di creazione, bisogna anche passargli l'indirizzo.
		Cart cart = service.findCartToCheckout(id, udi.getName(), udi.getSurname());
		model.addAttribute("cart", cart);
		return "carts.checkout";
	}
	
	@RequestMapping("/paid.do")
	public String paid(@RequestParam("tx") String transaction_id, @RequestParam("cm") long cart_id) throws BusinessException{
		service.paid(transaction_id, cart_id);
		return "carts.paid";
	}

}
