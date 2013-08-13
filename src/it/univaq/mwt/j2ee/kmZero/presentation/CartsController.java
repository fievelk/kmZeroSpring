package it.univaq.mwt.j2ee.kmZero.presentation;

import java.util.Date;

import javax.xml.ws.BindingType;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.ResponseCarts;
import it.univaq.mwt.j2ee.kmZero.business.model.Cart;
import it.univaq.mwt.j2ee.kmZero.business.model.CartLine;
import it.univaq.mwt.j2ee.kmZero.business.service.CartService;
import it.univaq.mwt.j2ee.kmZero.common.DateEditor;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
	private CartsValidator validator;
	
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
	
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
	public String deleteCartLine(@RequestParam("idcl") long id_cl, @RequestParam("idc") long id_c) throws BusinessException{
		service.deleteCartLine(id_cl, id_c);
		return null;
	}
	
	@RequestMapping("/confirmcart_start.do")
	public String confirmCart(@RequestParam("id") long id, Model model) throws BusinessException{
		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Cart cart = service.findCartToCheckout(id, udi.getName(), udi.getSurname());
		model.addAttribute("cart", cart);
		return "carts.confirm";
	}
	
	@RequestMapping(value="/confirmcart.do", method=RequestMethod.POST)
	public String checkout(@ModelAttribute Cart cart, BindingResult bindingResult) throws BusinessException{
		validator.validate(cart, bindingResult);
		if (bindingResult.hasErrors()){
			return "carts.confirm";
		}
		service.confirmCart(cart.getId(), cart.getDelivery_date());
		return "carts.checkout";
	}
	
	/*@RequestMapping("/checkout_start.do")
	public String checkoutStart(@RequestParam("id") long id, Model model) throws BusinessException{
		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//TODO: Qui, oltre al nome, il cognome e la data di creazione, bisogna anche passargli l'indirizzo.
		Cart cart = service.findCartToCheckout(id, udi.getName(), udi.getSurname());
		model.addAttribute("cart", cart);
		return "carts.checkout";
	}*/
	
	@RequestMapping("/paid.do")
	public String paid(@RequestParam("tx") String transaction_id, @RequestParam("cm") long cart_id) throws BusinessException{
		service.paid(transaction_id, cart_id);
		return "carts.paid";
	}

}
