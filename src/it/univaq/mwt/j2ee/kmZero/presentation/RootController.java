package it.univaq.mwt.j2ee.kmZero.presentation;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.impl.ProductService;
import it.univaq.mwt.j2ee.kmZero.business.impl.UserService;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.LoggedUser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {
	
	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private LoggedUser loggedUser;
	
	
	@RequestMapping("/")
	public String getFavouriteSellers(Model model) throws BusinessException{
		List<Seller> l = userService.getFavouriteSellers(); 
		List<Product> p = productService.getFavouriteProducts();
		model.addAttribute("sellers", l);
		model.addAttribute("products", p);
		return "common.index";
	}
	
	@RequestMapping("/welcome")
	public String welcome(Model model) throws BusinessException{
		User user;
		Long userId = loggedUser.getUserDetails().getId();
		if(loggedUser.getUserDetails().isSeller()){
			user = userService.findUserSellerById(userId);
		}else{
			user = userService.findUserById(userId);
		}
		model.addAttribute("user", user);
		return "common.welcome";
	}


}
