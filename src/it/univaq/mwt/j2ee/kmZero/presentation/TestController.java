package it.univaq.mwt.j2ee.kmZero.presentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.TestService;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.common.Warehouse;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestService service;
	
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
		service.testNumberFive();	
		model.addAttribute("test", 5);
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

//	@ModelAttribute
//	public void findWarehouse(Model model) {
//		//String warehouse = Warehouse.getCoordinates();
//		//model.addAttribute("warehouse", warehouse);
//		
//		float wareLat = Warehouse.getLatitude();
//		float wareLon = Warehouse.getLongitude();
//		
//		model.addAttribute("wareLat", wareLat);
//		model.addAttribute("wareLon", wareLon);
//		System.out.println("WAREHOUSE " + wareLat +"," + wareLon);
//	}
	
	@RequestMapping(value="/addressValidationTest")
	public String addressValidationTest(Model model) throws BusinessException {
//		service.testNumberOne();	
//		model.addAttribute("test", 1);
		return "test.addressValidation";
	}
	
}
