package it.univaq.mwt.j2ee.kmZero.presentation;


import java.util.Collection;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.CartService;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.UserService;
import it.univaq.mwt.j2ee.kmZero.business.model.Cart;
import it.univaq.mwt.j2ee.kmZero.business.model.Password;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserValidator validator;
	
	@Autowired
	private PasswordValidator validatorPassword;
	
	@RequestMapping("/admin/views")
	public String views(){
		return "users.views";
	}
	
	@RequestMapping("/admin/viewAllUsersPaginated")
	@ResponseBody
	public ResponseGrid<User> findAllUsersPaginated(@ModelAttribute RequestGrid requestGrid) throws BusinessException{
		ResponseGrid<User> result = service.viewAllUsersPaginated(requestGrid);
		return result;
	}
	
	@RequestMapping("/create_start")
	public String createStart(Model model) throws BusinessException{
		model.addAttribute("user", new User());
		return "users.createform";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String create(@ModelAttribute User user, BindingResult bindingResult) throws BusinessException {
		validator.validate(user, bindingResult);
		if (bindingResult.hasErrors()){
			return "users.createform";
		}
		service.createUser(user);
		return "common.login";
	}
	
	@RequestMapping("/update_start")
	public String updateStart(Model model) throws BusinessException {
		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		long id = udi.getId();
		User user = service.findUserById(id);
		model.addAttribute("user", user);
		return "users.updateform";
	}
	
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@ModelAttribute User user, BindingResult bindingResult) throws BusinessException {
		validator.validate(user, bindingResult);
		if (bindingResult.hasErrors()){
			return "users.updateform";
		}
		service.updateUser(user);
		return "redirect:/welcome";
	}
	
	@RequestMapping("/admin/update_start")
	public String updateStartByAdmin(@RequestParam("id") Long id, Model model) throws BusinessException {
		User user = service.findUserById(id);
		model.addAttribute("user", user);
		return "users.updateformadmin";
	}
	
	
	@RequestMapping(value="/admin/update", method=RequestMethod.POST)
	public String updateByAdmin(@ModelAttribute User user, BindingResult bindingResult) throws BusinessException {
		validator.validate(user, bindingResult);
		if (bindingResult.hasErrors()){
			return "users.updateformadmin";
		}
		service.updateUser(user);
		return "redirect:/users/admin/views";
	}
	
	@RequestMapping("/admin/delete_start")
	public String deleteStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		User user = service.findUserById(id);
		model.addAttribute("user", user);
		return "users.deleteform";
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public String delete(@ModelAttribute User user) throws BusinessException {
		service.deleteUser(user);
		return "redirect:/users/admin/views";
	}
	
	@RequestMapping("/edit_start_password")
	public String editStartPassword(Model model) throws BusinessException {
		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		long id = udi.getId();
		User user = service.findUserById(id);
		model.addAttribute("user", user);
		return "users.passwordform";
	}
	
	@RequestMapping(value="/edit_password", method = RequestMethod.POST)
	public String editPassword(@ModelAttribute User user, BindingResult bindingResult) throws BusinessException {
		Password password = user.getPassword();
		password.setDbPassword(service.oldPassword(user.getId()));
		
		validatorPassword.validate(password, bindingResult);
		if (bindingResult.hasErrors()){
			return "users.passwordform";
		}
		service.editPassword(user.getId(), user.getPassword().getPassword());
		return "redirect:/welcome";
	}
	
	@RequestMapping(value="/userordersview")
	public String userOrdersView(Model model) throws BusinessException {

		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		long id = udi.getId();
		User user = service.findUserById(id);
		
		Collection<Cart> carts = cartService.findUserPaidCarts(user); 
		
		model.addAttribute("carts", carts);
		return "users.userordersview";
	}

}
