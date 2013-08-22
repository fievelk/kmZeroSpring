package it.univaq.mwt.j2ee.kmZero.presentation;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.TestService;
import it.univaq.mwt.j2ee.kmZero.business.model.Cart;
import it.univaq.mwt.j2ee.kmZero.business.model.CartLine;
import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.business.service.CartService;
import it.univaq.mwt.j2ee.kmZero.business.service.UserService;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestService service;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping(value="/testNumberOne")
	public String testNumberOne(Model model) throws BusinessException {
		service.testNumberOne();	
		model.addAttribute("test", 1);
		return "test.test";
	}
	
	@RequestMapping(value="/testNumberTwo")
	public String testNumberTwo(Model model) throws BusinessException {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		WebAuthenticationDetails wad = (WebAuthenticationDetails) a.getDetails();
		System.out.println("SESSION_ID;"+wad.getSessionId());
		/*System.out.println("PRINCIPAL;"+a.getPrincipal());
		System.out.println("CREDENTIAL;"+a.getCredentials());
		System.out.println("CREDENTIAL;"+a.getDetails());
		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		System.out.println("NAME;"+udi.getUser().getName());*/
		
	
		return "test.test";
	}
	
	@RequestMapping(value="/testNumberThree")
	public String testNumberThree(Model model) throws BusinessException {
		service.testNumberThree();	
		model.addAttribute("test", 3);
		return "test.test";
	}
	
	@RequestMapping(value="/testNumberFour")
	public String testNumberFour(Model model) throws BusinessException {
		
		model.addAttribute("test", 4);
		return "test.test";
	}

	@RequestMapping(value="/testNumberFive")
	public String testNumberFive(Model model) throws BusinessException {
		List<CartLine> cl = service.testNumberFive();	
		model.addAttribute("test", 5);
		System.out.println("CARTLINES"+cl);
		return "test.test";
	}
	
	@RequestMapping(value="/test_user_start")
	public String testUserStart(Model model) throws BusinessException {
		User u = service.getUser();
		
		
        Map <Integer, String> hm = new HashMap<Integer, String>();
 
        for(Role r: u.getRoles()){
        	hm.put(r.getId(), r.getName());
        }
        
		model.addAttribute("user",u);
		model.addAttribute("ruoli",hm);
		return "test.test_user_start";
	}
	@RequestMapping(value="/test_user_create", method = RequestMethod.POST)
	public String update(@ModelAttribute User user, BindingResult bindingResult) throws BusinessException {
		System.out.println("USER:"+user.getName());
		System.out.println("ROLES:"+user.getRoles());
		service.updateUser(user);
		return "redirect:/test/test_user_start";
	}
	
	// MAP TEST
	
	@RequestMapping(value="/maptest")
	public String viewMapTest(Model model) {
		List<User> users = service.getAllUsersTest();
		System.out.println("USERS DA CONTROLLER " + users);
		model.addAttribute("users", users);
		return "test.maptest";
	}

	
	@RequestMapping(value="/addressValidationTest")
	public String addressValidationTest(Model model) throws BusinessException {
//		service.testNumberOne();	
//		model.addAttribute("test", 1);
		return "test.addressValidation";
	}
	

	@RequestMapping(value="/cartstodeliver")
	public String cartsToDeliver(Model model) {
		List<Cart> cartsToDeliver = service.getCartsToDeliverTest();
		
		model.addAttribute("cartsToDeliver", cartsToDeliver);
		return "test.cartsToDeliver";
	}
	
	
	@RequestMapping(value="/userOrderViewTest")
	public String userOrderViewTest(Model model) throws BusinessException {

		// l'IF si potrà togliere quando si metterà lo strato di sicurezza
		
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
			UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			long id = udi.getId();
			User user = userService.findUserById(id);
			Collection<Cart> carts = user.getCart(); // In questo modo trova anche i carrelli non ancora pagati. Modificare
//			System.out.println("STO FACENDO QUALCOSA "+user+" ," +carts);
			model.addAttribute("carts", carts);
			return "test.userOrderViewTest";
		} else {
			return "common.login";	
		}
	}
	
	
	@RequestMapping(value="/updateCartLineRating")
	@ResponseBody
	public void updateCartLineRating(@RequestParam("id") long cartLineId, @RequestParam("r") int rating) {
		CartLine cartLine = cartService.findCartLineById(cartLineId);
		cartService.updateCartLineRating(cartLine, rating);
		// Qui deve eseguire un metodo che aggiorni il rating globale del prodotto (media e numero di click)
	}
	
}
