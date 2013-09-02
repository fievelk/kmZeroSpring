package it.univaq.mwt.j2ee.kmZero.presentation;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.persistence.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.ResponseCarts;
import it.univaq.mwt.j2ee.kmZero.business.model.Cart;
import it.univaq.mwt.j2ee.kmZero.business.model.CartLine;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.service.CartService;
import it.univaq.mwt.j2ee.kmZero.business.service.ProductService;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.CookieGenerator;

@Controller
@RequestMapping("/carts")
@SessionAttributes("cart")
public class CartsController {
	
	@Autowired
	private CartService service;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CartsValidator validator;
	
	@RequestMapping("/addressvalidated")
	@ResponseBody
	public ResponseCarts<CartLine> modalAddressStart(@RequestParam("address") String address, @RequestParam("id") long id_product, 
			@RequestParam("quantity") int quantity, HttpSession session) throws BusinessException{
		String s = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Cart cart = null;
		if (s.equals("anonymousUser")){
			Product product = productService.findProductById(id_product);
			CartLine cartLine = new CartLine();
			cartLine.setProduct(product);
			cartLine.setQuantity(quantity);
			cartLine.setLineTotal(product.getPrice().multiply(new BigDecimal(quantity)));
			Collection<CartLine> cartLines = new ArrayList<CartLine>();
			cartLines.add(cartLine);
			cart = new Cart();
			cart.setAddress(address);
			cart.setCartLines(cartLines);
			session.setAttribute("cart", cart);
			
		} else {
			UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			cart = service.createCart(address, id_product, quantity, udi.getUser());
		}
		return new ResponseCarts<CartLine> (cart.getId(), cart.getCartLines().size(), cart.getCartLines());
	}
	
	@RequestMapping("/viewcartpaginated")
	@ResponseBody
	public ResponseCarts<CartLine> findAllCartLinesPaginated(HttpSession session) throws BusinessException{
		ResponseCarts<CartLine> result = null;
		String s = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Cart cart = (Cart)session.getAttribute("cart");
		if (s.equals("anonymousUser")){
			if (cart != null){
				result = new ResponseCarts<CartLine>(1, cart.getCartLines().size(), cart.getCartLines());
			} else {
				result = new ResponseCarts<CartLine>(0, 0, null);
			}
		} else {
			UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			/* Questo controllo viene fatto in modo che, se l'utente fa un carrello in "anonimo", se dovesse effettuare il login,
			 * potrà ritrovarselo */
			if (cart != null && cart.getId() < 2){
				result = service.persistCartSession(cart, udi.getUser());
			} else {
				result = service.viewCartlines(udi.getUser());
			}
			
		}
		return result;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public ResponseCarts<CartLine> addCartLine(@RequestParam("id") long id_product, @RequestParam("quantity") int quantity,
			HttpSession session) throws BusinessException{
		Cart cart = null;
		String s = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		boolean clExist = false;
		CartLine cl = null;
		Product p = productService.findProductById(id_product);
		if (s.equals("anonymousUser")){
			cart = (Cart)session.getAttribute("cart");
			Collection<CartLine> cartLines = new ArrayList<CartLine>();
			cartLines = cart.getCartLines();
			Iterator<CartLine> i = cartLines.iterator();
	    	// ciclo la collection per vedere se quella cartline c'e' gia' oppure no, se c'e' aggiorno quantità e linetotal
	    	while (i.hasNext() && !clExist){
	    		CartLine temp = i.next();
	    		if (temp.getProduct().getId() == id_product){
	    			cl = temp;
	    			clExist = true;
	    			cl.setQuantity(cl.getQuantity() + quantity);
	    			cl.setLineTotal(p.getPrice().multiply(new BigDecimal(cl.getQuantity())));
	    		}
	    	}
	    	if (!clExist){
	    		// Questo prodotto non e' stato ancora inserito nel carrello
	    		cl = new CartLine();
	    		cl.setQuantity(quantity);
	    		BigDecimal tot = p.getPrice().multiply(new BigDecimal(cl.getQuantity()));
	    		cl.setLineTotal(tot);
	    		cl.setProduct(p);
	    		cartLines = cart.getCartLines();
	    		cl.setId(cartLines.size());
	    		cartLines.add(cl);
	    		cart.setCartLines(cartLines);
	    		session.setAttribute("cart", cart);
	    	} else {
	    		session.setAttribute("cart", cart);
	    		// Questo prodotto e' stato gia' inserito nel carrello, lo fa direttamente sopra
	    	}
		} else {
			UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			cart = service.addCartLine(id_product, quantity, udi.getUser());
		}
		
		return new ResponseCarts<CartLine> (cart.getId(), cart.getCartLines().size(), cart.getCartLines());
	}
	
	@RequestMapping("/delete_cartline")
	@ResponseBody
	public String deleteCartLine(@RequestParam("idcartline") long idCartLine, @RequestParam("idcart") long idCart,
			@RequestParam("idanonymous") long idCartLineAnonymous, HttpSession session) 
			throws BusinessException{
		String s = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		if (s.equals("anonymousUser")){
			Cart cart = (Cart)session.getAttribute("cart");
			Collection<CartLine> cartLines = cart.getCartLines();
			Iterator<CartLine> i = cartLines.iterator();
			int j = 0;
			while(j <= idCartLineAnonymous){
				CartLine temp = i.next();
				if (j==idCartLineAnonymous){
					cartLines.remove(temp);
				}
				j++;
			}
			cart.setCartLines(cartLines);
			session.setAttribute("cart", cart);
			/*if (cart.getCartLines().size() == 0){
				session.setAttribute("cart", cart);
			} else {
				session.setAttribute("cart", cart);
			}*/
		} else {
			service.deleteCartLine(idCartLine, idCart);
		}
		return null;
	}
	
	@RequestMapping("/confirmcart_start")
	public String confirmCart(@RequestParam("id") long id, Model model) throws BusinessException{
		String s = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
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
	
	
	@RequestMapping(value="/updateCartLineRating")
	@ResponseBody
	public void updateCartLineRating(@RequestParam("id") long cartLineId, @RequestParam("rating") int rating) throws BusinessException {
		CartLine cartLine = service.findCartLineById(cartLineId);
		service.updateCartLineRating(cartLine, rating);
	}

	@RequestMapping(value="/createFeedback")
	@ResponseBody
	public void createFeedback(@RequestParam("id") long cartLineId, @RequestParam("feedback") String feedbackString) throws BusinessException {
		CartLine cartLine = service.findCartLineById(cartLineId);
		service.createFeedback(cartLine, feedbackString);
	}
	
	@RequestMapping("/emptycart")
	@ResponseBody
	public String emptyCart(@RequestParam("id") long cartId, HttpSession session) throws BusinessException {
		String s = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Cart cart = null;
		if (s.equals("anonymousUser")){
			cart = (Cart)session.getAttribute("cart");
			Collection<CartLine> cls = cart.getCartLines();
			cls.removeAll(cls);
			cart.setCartLines(cls);
			session.setAttribute("cart", cart);
		} else {
			service.emptyCart(cartId);
		}
		return null;
	}
}
