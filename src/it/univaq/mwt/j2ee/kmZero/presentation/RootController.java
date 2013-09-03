package it.univaq.mwt.j2ee.kmZero.presentation;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.service.ProductService;
import it.univaq.mwt.j2ee.kmZero.business.service.UserService;

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
	
	
	@RequestMapping("/")
	public String getFavouriteSellers(Model model) throws BusinessException{
		List<Seller> l = userService.getFavouriteSellers(); 
		List<Product> p = productService.getFavouriteProducts();
		model.addAttribute("sellers", l);
		model.addAttribute("products", p);
		return "common.index";
	}


}
