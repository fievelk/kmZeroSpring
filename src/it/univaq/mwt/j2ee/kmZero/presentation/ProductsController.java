package it.univaq.mwt.j2ee.kmZero.presentation;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.univaq.mwt.j2ee.kmZero.business.model.Category;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;


@Controller
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductService service;
	
	@RequestMapping("/views.do")
	public String views() {
		return "products.views";
	}
	
	
	@RequestMapping("/viewsforsellers.do")
	public String viewsForSellers() {

		return "products.viewsforsellers";
	}
	
	
	@RequestMapping("/viewProductsBySellerIdPaginated.do")
	@ResponseBody
	public ResponseGrid<Product> viewProductsBySellerIdPaginated(@ModelAttribute RequestGrid requestGrid) throws BusinessException{

		ResponseGrid<Product> result = service.viewProductsBySellerIdPaginated(requestGrid);
		return result;
	}
	
	
	@RequestMapping("/create_start.do")
	public String createStart(Model model) throws BusinessException {
		model.addAttribute("product", new Product());
		//Parte commentata: si è aggiunto @ModelAttribute
/*		List<Category> categories = service.findAllCategories();
		model.addAttribute("categories", categories);*/
		return "products.createproduct";
	}
	
	
	@RequestMapping(value="/create.do", method=RequestMethod.POST)
	public String create(@ModelAttribute Product product, BindingResult bindingResult) throws BusinessException {
		service.createProduct(product);
		return "redirect:/products/viewsforsellers.do";
	}
	
	@RequestMapping("/update_start.do")
	public String updateStart(@RequestParam("oid") Long id, Date date_out, Model model) throws BusinessException {
		Product product = service.findProductById(id);
		model.addAttribute("product", product);
		return "products.updateform";
	}
	
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	public String update(@ModelAttribute Product product, BindingResult bindingResult) throws BusinessException {
		service.updateProduct(product);
		return "redirect:/products/viewsforsellers.do";
	}	
	
	
	@ModelAttribute
	public void findAllCategories(Model model) throws BusinessException {
		List<Category> categories = service.findAllCategories();
		model.addAttribute("categories", categories);
	}
}