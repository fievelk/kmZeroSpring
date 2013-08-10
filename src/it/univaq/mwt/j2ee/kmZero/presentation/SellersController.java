package it.univaq.mwt.j2ee.kmZero.presentation;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.model.Category;
import it.univaq.mwt.j2ee.kmZero.business.model.Image;
import it.univaq.mwt.j2ee.kmZero.business.model.Password;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.SellerContent;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.business.service.UserService;
import it.univaq.mwt.j2ee.kmZero.common.DateEditor;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.LoggedUser;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.UserDetailsImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@Autowired
	private LoggedUser loggedUser;
	
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
	@RequestMapping("/admin/viewsToEnable.do")
	public String viewsToEnable(){
		return "sellerstoenable.views";
	}
	
	@RequestMapping("/admin/viewsEnabled.do")
	public String viewsEnabled(){
		return "sellersenabled.views";
	}
	
	@RequestMapping("/admin/viewAllSellersToEnablePaginated.do")
	@ResponseBody
	public ResponseGrid<Seller> findAllSellersToEnablePaginated(@ModelAttribute RequestGrid requestGrid) throws BusinessException{
		ResponseGrid<Seller> result = service.viewAllSellersToEnablePaginated(requestGrid);
		return result;
	}
	
	@RequestMapping("/admin/viewAllSellersEnabledPaginated.do")
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
		return "redirect:/";
	}
	
	@RequestMapping("/update_start.do")
	public String updateStart(Model model) throws BusinessException {
		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		long id = udi.getId();
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
		/*if (seller.getEnable()){
			return "redirect:/sellers/viewsEnabled.do";
		} else {
			return "redirect:/sellers/viewsToEnable.do";
		}*/
		return "redirect:/welcome";
		
	}
	
	@RequestMapping("/admin/update_start.do")
	public String updateStartByAdmin(@RequestParam("id") Long id, Model model) throws BusinessException {
		Seller seller = service.findSellerById(id);
		model.addAttribute("seller", seller);
		return "sellers.updateformadmin";
	}
	
	
	@RequestMapping(value="/admin/update.do", method = RequestMethod.POST)
	public String updateByAdmin(@ModelAttribute Seller seller, BindingResult bindingResult) throws BusinessException {
		validator.validate(seller, bindingResult);
		if (bindingResult.hasErrors()){
			return "sellers.updateformadmin";
		}
		service.updateSellerByAdmin(seller);
		if (seller.getEnable()){
			return "redirect:/sellers/admin/viewsEnabled.do";
		} else {
			return "redirect:/sellers/admin/viewsToEnable.do";
		}
	}
	
	@RequestMapping("/admin/delete_start.do")
	public String deleteStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		Seller seller = service.findSellerById(id);
		model.addAttribute("seller", seller);
		return "sellers.deleteform";
	}
	
	@RequestMapping(value="/admin/delete.do", method = RequestMethod.POST)
	public String delete(@ModelAttribute Seller seller) throws BusinessException {
		service.deleteSeller(seller);
		if (seller.getEnable()){
			return "redirect:/sellers/admin/viewsEnabled.do";
		} else {
			return "redirect:/sellers/admin/viewsToEnable.do";
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
		return "redirect:/";
	}
	

	
	
	@RequestMapping("/content_start.do")
	public String updateContentStart(Model model) throws BusinessException {
		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		long id = udi.getId();
		Seller seller = service.findSellerById(id);
		model.addAttribute("seller", seller);
		return "sellers.contentform";
	}
	
	@RequestMapping("/admin/content_start.do")
	public String updateContentStartByAdmin(@RequestParam("id") Long id, Model model) throws BusinessException {
		Seller seller = service.findSellerById(id);
		model.addAttribute("seller", seller);
		return "sellers.contentform";
	}
	
	
	@RequestMapping(value="/content.do", method = RequestMethod.POST)
	public String updateContent(@RequestParam("title") String t, @RequestParam("input") String d,  @ModelAttribute Seller seller, BindingResult bindingResult) throws BusinessException {
		/*validator.validate(seller, bindingResult);
		if (bindingResult.hasErrors()){
			return "sellers.updateform";
		}*/
		SellerContent content = new SellerContent(t, d);
		Collection<SellerContent> contents = new ArrayList<SellerContent>();
		contents.add(content);
		seller.setContents(contents);
		service.editSellerContent(seller);
		/*if (seller.getEnable()){
			return "redirect:/sellers/viewsEnabled.do";
		} else {
			return "redirect:/sellers/viewsToEnable.do";
		}*/
		return "redirect:views.do?id=" + seller.getId();
	}
	
	@RequestMapping("/views.do")
	public String viewContent(@RequestParam("id") Long id, Model model) throws BusinessException {
		Seller seller = service.findSellerById(id);
		if (seller.getEnable()){
			model.addAttribute("seller", seller);
			return "sellers.viewcontent";
		}
		return "/404 pagina non trovata";
	}
	
	@RequestMapping("/list.do")
	public String viewAllSellers(Model model) throws BusinessException {
		List<Seller> sellers = service.viewAllSellers();
		model.addAttribute("sellers", sellers);
		return "sellers.list";
	}

	@RequestMapping("/pagecontents")
	public String viewPageContents(Model model) throws BusinessException {
		return "sellers.pagecontents.views";
		
	}
	
	@RequestMapping("/createpagecontent_start")
	public String createPageContentStart(Model model) throws BusinessException {
		model.addAttribute("sellercontent", new SellerContent());
		return "sellers.pagecontent.createform";
	}
	
	@RequestMapping(value="/createpagecontent", method = RequestMethod.POST)
	public String createPageContent(@ModelAttribute SellerContent content , BindingResult bindingResult) throws BusinessException {
		service.createPageContent(content,loggedUser.getUserDetails().getId());
		return "redirect:/sellers/pagecontents";
	}
	
	@RequestMapping("/updatepagecontent_start")
	public String createPageContentStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		SellerContent sellercontent = service.findSellerContentById(id);
		model.addAttribute("sellercontent", sellercontent);
		model.addAttribute("image", sellercontent.getImage());
		model.addAttribute("id", id);
		return "sellers.pagecontent.updateform";
	}
	
	@RequestMapping(value="/updatepagecontent", method = RequestMethod.POST)
	public String updatePageContent(@ModelAttribute SellerContent content , BindingResult bindingResult) throws BusinessException {
		service.updatePageContent(content,loggedUser.getUserDetails().getId());
		return "redirect:/sellers/pagecontents";
	}
	
	@RequestMapping("/deletepagecontent_start")
	public String deletePageContentStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		SellerContent sellercontent = service.findSellerContentById(id);
		model.addAttribute("sellercontent", sellercontent);
		model.addAttribute("id", id);
		return "sellers.pagecontent.deleteform";
	}
	@RequestMapping(value="/deletepagecontent", method = RequestMethod.POST)
	public String updatePageContent(@ModelAttribute("id") long contentId , BindingResult bindingResult) throws BusinessException {
		service.deletePageContent(contentId,loggedUser.getUserDetails().getId());
		return "redirect:/sellers/pagecontents";
	}
	
	@RequestMapping("/viewPageContentsPaginated")
	@ResponseBody
	public ResponseGrid<SellerContent> findAllPageContentsPaginated(@ModelAttribute RequestGrid requestGrid) throws BusinessException{
		ResponseGrid<SellerContent> result = service.viewAllPageContentsPaginated(requestGrid,loggedUser.getUserDetails().getId());
		return result;
	}	
	
	@RequestMapping(value="/{sellerid}")
	public String updateImageStart(@PathVariable("sellerid")Long sellerid, Model model) throws BusinessException {
		Seller s = service.findSellerById(sellerid);
		model.addAttribute("seller", s);
		model.addAttribute("contents", s.getContents());
		return "sellers.view";
	}
}
