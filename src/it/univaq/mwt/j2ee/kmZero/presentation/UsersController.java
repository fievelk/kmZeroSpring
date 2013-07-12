package it.univaq.mwt.j2ee.kmZero.presentation;

import java.util.Date;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.business.service.UserService;
import it.univaq.mwt.j2ee.kmZero.common.DateEditor;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private UserService service;
	
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
	@RequestMapping("/views.do")
	public String views(){
		return "users.views";
	}
	
	@RequestMapping("/viewAllUsersPaginated")
	@ResponseBody
	public ResponseGrid<User> findAllUsersPaginated(@ModelAttribute RequestGrid requestGrid) throws BusinessException{
		ResponseGrid<User> result = service.viewAllUsersPaginated(requestGrid);
		return result;
	}
	
	@RequestMapping("/create_start.do")
	public String createStart(Model model) throws BusinessException{
		model.addAttribute("user", new User());
		return "users.createform";
	}
	
	@RequestMapping(value="/create.do", method=RequestMethod.POST)
	public String create(@ModelAttribute User user, BindingResult bindingResult) throws BusinessException {
		service.createUser(user);
		return "redirect:/users/views.do";
	}
	
	@RequestMapping("/update_start.do")
	public String updateStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		User user = service.findUserById(id);
		model.addAttribute("user", user);
		return "user.updateform";
	}
	
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	public String update(@ModelAttribute User user, BindingResult bindingResult) throws BusinessException {
		service.updateUser(user);
		return "redirect:/users/views.do";
	}
	
	@RequestMapping("/delete_start.do")
	public String deleteStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		User user = service.findUserById(id);
		model.addAttribute("user", user);
		return "user.deleteform";
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.POST)
	public String delete(@ModelAttribute User user) throws BusinessException {
		service.deleteUser(user);
		return "redirect:/users/views.do";
	}

}
