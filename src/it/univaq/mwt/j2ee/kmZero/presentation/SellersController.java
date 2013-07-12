package it.univaq.mwt.j2ee.kmZero.presentation;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.business.service.UserService;
import it.univaq.mwt.j2ee.kmZero.common.DateEditor;

import java.util.Date;

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
@RequestMapping("/sellers")
public class SellersController {
	
	@Autowired
	private UserService service;
	
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
	@RequestMapping("/views.do")
	public String views(){
		return "sellers.views";
	}
	
	@RequestMapping("/viewAllSellersPaginated.do")
	@ResponseBody
	public ResponseGrid<Seller> findAllUsersPaginated(@ModelAttribute RequestGrid requestGrid) throws BusinessException{
		ResponseGrid<Seller> result = service.viewAllSellersPaginated(requestGrid);
		return result;
	}
	
	@RequestMapping("/create_start.do")
	public String createStart(Model model) throws BusinessException{
		model.addAttribute("seller", new Seller());
		return "sellers.createform";
	}
	
	@RequestMapping(value="/create.do", method=RequestMethod.POST)
	public String create(@ModelAttribute Seller seller, BindingResult bindingResult) throws BusinessException {
		service.createSeller(seller);
		return "redirect:/sellers/views.do";
	}
	
	// DA SISTEMARE
	@RequestMapping("/update_start.do")
	public String updateStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		Seller seller = service.findSellerById(id);
		model.addAttribute("seller", seller);
		return "seller.updateform";
	}
	
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	public String update(@ModelAttribute Seller seller, BindingResult bindingResult) throws BusinessException {
		service.updateSeller(seller);
		return "redirect:/sellers/views.do";
	}
	
	@RequestMapping("/update_start_admin.do")
	public String updateStartByAdmin(@RequestParam("id") Long id, Model model) throws BusinessException {
		Seller seller = service.findSellerById(id);
		model.addAttribute("seller", seller);
		return "seller.updateformadmin";
	}
	
	
	@RequestMapping(value="/update_admin.do", method = RequestMethod.POST)
	public String updateByAdmin(@ModelAttribute Seller seller, BindingResult bindingResult) throws BusinessException {
		service.updateSellerByAdmin(seller);
		return "redirect:/sellers/views.do";
	}
	
	@RequestMapping("/delete_start.do")
	public String deleteStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		Seller seller = service.findSellerById(id);
		model.addAttribute("seller", seller);
		return "seller.deleteform";
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.POST)
	public String delete(@ModelAttribute Seller seller) throws BusinessException {
		service.deleteSeller(seller);
		return "redirect:/sellers/views.do";
	}

}
