package it.univaq.mwt.j2ee.kmZero.presentation;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.CartService;
import it.univaq.mwt.j2ee.kmZero.business.ProductService;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.UserService;
import it.univaq.mwt.j2ee.kmZero.business.WarehouseService;
import it.univaq.mwt.j2ee.kmZero.business.model.Cart;
import it.univaq.mwt.j2ee.kmZero.business.model.CartLine;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.SellerContent;
import it.univaq.mwt.j2ee.kmZero.business.model.User;
import it.univaq.mwt.j2ee.kmZero.business.model.Warehouse;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.LoggedUser;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.UserDetailsImpl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	private ProductService productService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private LoggedUser loggedUser;
	
	@Autowired
	private WarehouseService warehouseService;
	
	@RequestMapping("/admin/viewsToEnable")
	public String viewsToEnable(){
		return "sellerstoenable.views";
	}
	
	@RequestMapping("/admin/viewsEnabled")
	public String viewsEnabled(){
		return "sellersenabled.views";
	}
	
	@RequestMapping("/admin/viewAllSellersToEnablePaginated")
	@ResponseBody
	public ResponseGrid<Seller> findAllSellersToEnablePaginated(@ModelAttribute RequestGrid requestGrid) throws BusinessException{
		ResponseGrid<Seller> result = service.viewAllSellersToEnablePaginated(requestGrid);
		return result;
	}
	
	@RequestMapping("/admin/viewAllSellersEnabledPaginated")
	@ResponseBody
	public ResponseGrid<Seller> findAllSellersEnabledPaginated(@ModelAttribute RequestGrid requestGrid) throws BusinessException{
		ResponseGrid<Seller> result = service.viewAllSellersEnabledPaginated(requestGrid);
		return result;
	}
	
	@RequestMapping("/create_start")
	public String createStart(Model model) throws BusinessException{
		model.addAttribute("seller", new Seller());
		return "sellers.createform";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String create(@ModelAttribute Seller seller, BindingResult bindingResult) throws BusinessException {
		validator.validate(seller, bindingResult);
		if (bindingResult.hasErrors()){
			return "sellers.createform";
		}
		service.createSeller(seller);
		return "common.login";
	}
	
	@RequestMapping("/update_start")
	public String updateStart(Model model) throws BusinessException {
		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		long id = udi.getId();
		Seller seller = service.findSellerById(id);
		model.addAttribute("seller", seller);
		return "sellers.updateform";
	}
	
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update(@ModelAttribute Seller seller, BindingResult bindingResult) throws BusinessException {
		validator.validate(seller, bindingResult);
		if (bindingResult.hasErrors()){
			return "sellers.updateform";
		}
		service.updateSeller(seller);
		return "redirect:/welcome";
	}
	
	@RequestMapping("/admin/update_start")
	public String updateStartByAdmin(@RequestParam("id") Long id, Model model) throws BusinessException {
		Seller seller = service.findSellerById(id);
		model.addAttribute("seller", seller);
		return "sellers.updateformadmin";
	}
	
	
	@RequestMapping(value="/admin/update", method = RequestMethod.POST)
	public String updateByAdmin(@ModelAttribute Seller seller, BindingResult bindingResult) throws BusinessException {
		validator.validate(seller, bindingResult);
		if (bindingResult.hasErrors()){
			return "sellers.updateformadmin";
		}
		service.updateSellerByAdmin(seller);
		if (seller.getEnable()){
			return "redirect:/sellers/admin/viewsEnabled";
		} else {
			return "redirect:/sellers/admin/viewsToEnable";
		}
	}
	
	@RequestMapping("/admin/delete_start")
	public String deleteStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		Seller seller = service.findSellerById(id);
		model.addAttribute("seller", seller);
		return "sellers.deleteform";
	}
	
	@RequestMapping(value="/admin/delete", method = RequestMethod.POST)
	public String delete(@ModelAttribute Seller seller) throws BusinessException {
		service.deleteSeller(seller.getId());
		if (seller.getEnable()){
			return "redirect:/sellers/admin/viewsEnabled";
		} else {
			return "redirect:/sellers/admin/viewsToEnable";
		}
	}
	
	@RequestMapping("/upgrade_start")
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
		seller.setDateOfBirth(user.getDateOfBirth());
		seller.setLastAccess(user.getLastAccess());
		seller.setAddress(user.getAddress());
		
		model.addAttribute("seller", seller);
		return "sellers.upgradeform";
	}
	
	@RequestMapping(value="/upgrade", method = RequestMethod.POST)
	public String upgrade(@ModelAttribute Seller seller, BindingResult bindingResult) throws BusinessException {
		validator.validate(seller, bindingResult);
		if (bindingResult.hasErrors()){
			return "sellers.upgradeform";
		}
		service.upgradeSeller(seller);
		return "redirect:/welcome";
	}
	
	
	@RequestMapping("/pagecontents")
	public String viewPageContents(Model model,@RequestParam("id") Long sellerId) throws BusinessException {
		model.addAttribute("sellerId", sellerId);
		return "sellers.pagecontents.views";
	}
	
	@RequestMapping("/createpagecontent_start")
	public String createPageContentStart(Model model,@RequestParam(value="sellerId") Long sellerId) throws BusinessException {
		model.addAttribute("sellerId", sellerId);
		model.addAttribute("sellercontent", new SellerContent());
		return "sellers.pagecontent.createform";
	}
	
	@RequestMapping(value="/createpagecontent", method = RequestMethod.POST)
	public String createPageContent(@ModelAttribute SellerContent content,@ModelAttribute("sellerId") Long sellerId, BindingResult bindingResult) throws BusinessException {
		Long id = loggedUser.isAdmin() ? sellerId : loggedUser.getUserDetails().getId();
		service.createPageContent(content,id);
		return "redirect:/sellers/pagecontents?id="+id;
	}
	
	@RequestMapping("/updatepagecontent_start")
	public String updatePageContentStart(Model model,@RequestParam("id") Long id,@RequestParam("sellerId") Long sellerId) throws BusinessException {
		SellerContent sellercontent = service.findSellerContentById(id);
		model.addAttribute("sellercontent", sellercontent);
		model.addAttribute("image", sellercontent.getImage());
		model.addAttribute("sellerId", sellerId);
		return "sellers.pagecontent.updateform";
	}
	
	@RequestMapping(value="/updatepagecontent", method = RequestMethod.POST)
	public String updatePageContent(@ModelAttribute SellerContent content ,@ModelAttribute("sellerId") Long sellerId, BindingResult bindingResult) throws BusinessException {
		Long id = loggedUser.isAdmin() ? sellerId : loggedUser.getUserDetails().getId();
		service.updatePageContent(content,id);
		return "redirect:/sellers/pagecontents?id="+id;
	}
	
	@RequestMapping("/deletepagecontent_start")
	public String deletePageContentStart(Model model,@RequestParam("id") Long id,@RequestParam("sellerId") Long sellerId) throws BusinessException {
		SellerContent sellercontent = service.findSellerContentById(id);
		model.addAttribute("sellercontent", sellercontent);
		model.addAttribute("sellerId", sellerId);
		return "sellers.pagecontent.deleteform";
	}
	
	@RequestMapping(value="/deletepagecontent", method = RequestMethod.POST)
	public String updatePageContent(@ModelAttribute("id") long contentId,@ModelAttribute("sellerId") Long sellerId, BindingResult bindingResult) throws BusinessException {
		Long id = loggedUser.isAdmin() ? sellerId : loggedUser.getUserDetails().getId();
		service.deletePageContent(contentId,id);
		return "redirect:/sellers/pagecontents?id="+id;
	}
		
	@RequestMapping("/viewPageContentsPaginated")
	@ResponseBody
	public ResponseGrid<SellerContent> findAllPageContentsPaginated(@ModelAttribute RequestGrid requestGrid,@RequestParam(value="sellerId")Long sellerId) throws BusinessException{
		Long id  = loggedUser.isAdmin() ? sellerId : loggedUser.getUserDetails().getId();
		ResponseGrid<SellerContent> result = service.viewAllPageContentsPaginated(requestGrid,id);
		return result;
	}
	
	@RequestMapping(value="/{sellerid}/*")
	public String updateImageStart(@PathVariable("sellerid")Long sellerid, Model model) throws BusinessException {
		Seller s = service.findSellerById(sellerid);
		model.addAttribute("seller", s);
		List<Seller> ls = service.getAllSellers();
		model.addAttribute("sellers", ls);	
		model.addAttribute("contents", s.getContents());
		return "seller.view";
	}
	
	@RequestMapping(value="")
	public String sellersView(Model model) throws BusinessException {
		List<Seller> ls = service.getAllSellers();
		model.addAttribute("sellers", ls);
		List<Product> lp = productService.getAllProducts();
		model.addAttribute("products", lp);
		String warehouse = warehouseService.findWarehouseAddress();
		model.addAttribute("warehouse", warehouse);
		return "sellers.view";
	}

	// WAREHOUSE

	@RequestMapping("/admin/viewWarehouses")
	public String viewWarehouses(Model model) throws BusinessException {
		Warehouse warehouse = warehouseService.findWarehouse();
		model.addAttribute("warehouse", warehouse);
		return "warehouses.views";
	}
	
	@RequestMapping("/admin/updateWarehouse_start")
	public String updateWarehouseStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		Warehouse warehouse = warehouseService.findWarehouseById(id);
		model.addAttribute("warehouse", warehouse);
		model.addAttribute("id", id);
		return "warehouses.updateform";
	}
	
	@RequestMapping(value="/admin/updateWarehouse", method=RequestMethod.POST)
	public String updateWarehouse(@ModelAttribute Warehouse warehouse, BindingResult bindingResult) throws BusinessException {
		warehouseService.updateWarehouse(warehouse);
		return "redirect:/sellers/admin/viewWarehouses";
	}	
	
	@RequestMapping("/findWarehouseAddress")
	@ResponseBody
	/*The @ResponseBody annotation can be put on a method and indicates that the return type
	* should be written straight to the HTTP response body (and not placed in a Model, or
	* interpreted as a view name).*/
	public String findWarehouseAddress() throws BusinessException {
		String address = warehouseService.findWarehouseAddress();	
		return address;
	}
	
	@RequestMapping(value="/admin/usersdeliverymap")
	public String usersDeliveryMap(Model model) throws BusinessException {
		Collection<Cart> cartsToDeliver = cartService.getCartsToDeliver();
		
		model.addAttribute("cartsToDeliver", cartsToDeliver);
		return "planning.usersdeliverymap";
	}
	
	@RequestMapping(value="/sellerreceivedorders")
	public String sellerReceivedOrders(Model model) throws BusinessException {

		UserDetailsImpl udi = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		long id = udi.getId();
		Seller seller = (Seller) service.findUserById(id);
		
		// Trovo i carrelli pagati e li aggiungo al model
		Collection<CartLine> cartLines = cartService.findSellerReceivedOrders(seller); 
		
		model.addAttribute("cartLines", cartLines);
		return "sellers.sellerreceivedorders";
	}
	
	@RequestMapping(value="/admin/sellerspickupmap")
	public String sellersPickUpMap(Model model) throws BusinessException {

		Collection<CartLine> cartLinesToDeliver = cartService.findCartLinesToDeliver();
		
		model.addAttribute("cartLinesToDeliver", cartLinesToDeliver);
		return "planning.sellerspickupmap";
	}
	
}
