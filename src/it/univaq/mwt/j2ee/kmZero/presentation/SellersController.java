package it.univaq.mwt.j2ee.kmZero.presentation;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Password;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.business.service.UserService;
import it.univaq.mwt.j2ee.kmZero.common.DateEditor;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.UserDetailsImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/sellers")
public class SellersController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private SellerValidator validator;
	
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
	@RequestMapping("/viewsToEnable.do")
	public String viewsToEnable(){
		return "sellerstoenable.views";
	}
	
	@RequestMapping("/viewsEnabled.do")
	public String viewsEnabled(){
		return "sellersenabled.views";
	}
	
	@RequestMapping("/viewAllSellersToEnablePaginated.do")
	@ResponseBody
	public ResponseGrid<Seller> findAllSellersToEnablePaginated(@ModelAttribute RequestGrid requestGrid) throws BusinessException{
		ResponseGrid<Seller> result = service.viewAllSellersToEnablePaginated(requestGrid);
		return result;
	}
	
	@RequestMapping("/viewAllSellersEnabledPaginated.do")
	@ResponseBody
	public ResponseGrid<Seller> findAllSellersEnabledPaginated(@ModelAttribute RequestGrid requestGrid) throws BusinessException{
		ResponseGrid<Seller> result = service.viewAllSellersEnabledPaginated(requestGrid);
		return result;
	}
	
	@RequestMapping("/create_start.do")
	public String createStart(Model model) throws BusinessException{
		model.addAttribute("seller", new Seller());
		return "sellers.createform";
	}
	
	@RequestMapping(value="/create.do", method=RequestMethod.POST)
	public String create(@ModelAttribute Seller seller, BindingResult bindingResult) throws BusinessException {
		validator.validate(seller, bindingResult);
		if (bindingResult.hasErrors()){
			return "sellers.createform";
		}
		service.createSeller(seller);
		return "redirect:/sellers/viewsToEnable.do";
	}
	
	@RequestMapping("/update_start.do")
	public String updateStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		Seller seller = service.findSellerById(id);
		model.addAttribute("seller", seller);
		return "sellers.updateform";
	}
	
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	public String update(@ModelAttribute Seller seller, BindingResult bindingResult) throws BusinessException {
		validator.validate(seller, bindingResult);
		if (bindingResult.hasErrors()){
			return "sellers.updateform";
		}
		service.updateSeller(seller);
		if (seller.getEnable()){
			return "redirect:/sellers/viewsEnabled.do";
		} else {
			return "redirect:/sellers/viewsToEnable.do";
		}
		
	}
	
	@RequestMapping("/update_start_admin.do")
	public String updateStartByAdmin(@RequestParam("id") Long id, Model model) throws BusinessException {
		Seller seller = service.findSellerById(id);
		model.addAttribute("seller", seller);
		return "sellers.updateformadmin";
	}
	
	
	@RequestMapping(value="/update_admin.do", method = RequestMethod.POST)
	public String updateByAdmin(@ModelAttribute Seller seller, BindingResult bindingResult) throws BusinessException {
		validator.validate(seller, bindingResult);
		if (bindingResult.hasErrors()){
			return "sellers.updateformadmin";
		}
		service.updateSellerByAdmin(seller);
		if (seller.getEnable()){
			return "redirect:/sellers/viewsEnabled.do";
		} else {
			return "redirect:/sellers/viewsToEnable.do";
		}
	}
	
	@RequestMapping("/delete_start.do")
	public String deleteStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		Seller seller = service.findSellerById(id);
		model.addAttribute("seller", seller);
		return "sellers.deleteform";
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.POST)
	public String delete(@ModelAttribute Seller seller) throws BusinessException {
		service.deleteSeller(seller);
		if (seller.getEnable()){
			return "redirect:/sellers/viewsEnabled.do";
		} else {
			return "redirect:/sellers/viewsToEnable.do";
		}
	}
	
	@RequestMapping("/upgrade_start.do")
	public String upgradeStart(Model model) throws BusinessException {
		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		long id = udi.getId();
		User user = service.findUserById(id);
		
		Seller seller = new Seller();
		seller.setId(user.getId());
		seller.setName(user.getName());
		seller.setSurname(user.getSurname());
		seller.setPassword(user.getPassword());
		seller.setEmail(user.getEmail());
		seller.setCreated(user.getCreated());
		seller.setDate_of_birth(user.getDate_of_birth());
		seller.setLast_access(user.getLast_access());
		seller.setAddress(user.getAddress());
		
		//seller = (Seller) user;
		model.addAttribute("seller", seller);
		return "sellers.upgradeform";
	}
	
	@RequestMapping(value="/upgrade.do", method = RequestMethod.POST)
	public String upgrade(@ModelAttribute Seller seller, BindingResult bindingResult) throws BusinessException {
		validator.validate(seller, bindingResult);
		if (bindingResult.hasErrors()){
			return "sellers.upgradeform";
		}
		service.upgradeSeller(seller);
		return "redirect:/sellers/viewsToEnable.do";
	}

}
