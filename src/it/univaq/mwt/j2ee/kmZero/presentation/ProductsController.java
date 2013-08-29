package it.univaq.mwt.j2ee.kmZero.presentation;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.RequestGridProducts;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.service.ImageService;
import it.univaq.mwt.j2ee.kmZero.business.service.ProductService;
import it.univaq.mwt.j2ee.kmZero.business.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import it.univaq.mwt.j2ee.kmZero.business.model.Category;
import it.univaq.mwt.j2ee.kmZero.business.model.Image;
import it.univaq.mwt.j2ee.kmZero.business.model.Measure;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;

import it.univaq.mwt.j2ee.kmZero.common.spring.security.LoggedUser;



@Controller
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ImageService imageService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private LoggedUser loggedUser;
	
	@Autowired
	private ProductValidator validator;
	
	//FRONTEND
	
	@RequestMapping("")
	public String views(Model model) throws BusinessException{
		List<Category> categoryTree = productService.findAllRootCategories();
		model.addAttribute("categoryTree", categoryTree);
		List<Seller> sellers = userService.getAllSellers();
		model.addAttribute("sellers", sellers);
		List<Product> products = productService.getFavouriteProducts();
		model.addAttribute("products", products);
		return "products.views";
	}
	
	@RequestMapping("/viewProducts")
	@ResponseBody
	public ResponseGrid<Product> viewProducts(@ModelAttribute RequestGridProducts requestGridProducts) throws BusinessException {
		ResponseGrid<Product> result = productService.viewProducts(requestGridProducts);
		return result;
	}
	

	@RequestMapping("/viewsforsellers")
	public String viewsForSellers() {
		return "products.viewsforsellers";
	}
	
	
	@RequestMapping("/viewProductsBySellerIdPaginated")
	@ResponseBody
	public ResponseGrid<Product> viewProductsBySellerIdPaginated(@ModelAttribute RequestGrid requestGrid) throws BusinessException{
		long userId = loggedUser.getUserDetails().getId();
		ResponseGrid<Product> result = productService.viewProductsBySellerIdPaginated(requestGrid,userId);
		return result;
	}	
	
	@RequestMapping("/create_start")
	public String createStart(Model model) throws BusinessException {
		model.addAttribute("product", new Product());
		List<Category> categories = productService.findAllCategories();
		model.addAttribute("categories", categories);
		if(loggedUser.isAdmin()){
			List<Seller> sellers = userService.getAllSellers();
			model.addAttribute("sellers", sellers);
		}
		return "products.createform";
	}
	
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String create(@ModelAttribute Product product, BindingResult bindingResult) throws BusinessException {
		validator.validate(product, bindingResult);
		if (bindingResult.hasErrors()){
			return  loggedUser.isAdmin() ? "products.createformbyadmin" : "products.createform";
		}
		long sellerid = loggedUser.isAdmin() ? product.getSeller().getId() : loggedUser.getUserDetails().getId();
		productService.createProduct(product,sellerid);
		return "redirect:/products/viewsforsellers.do";
	}
	
	
	@RequestMapping("/update_start")
	public String updateStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		Product product = productService.findProductById(id);
		List<Category> categories = productService.findAllCategories();
		model.addAttribute("categories", categories);
		model.addAttribute("product", product);
		model.addAttribute("id", id);
		if(loggedUser.isAdmin()){
			List<Seller> sellers = userService.getAllSellers();
			model.addAttribute("sellers", sellers);
		}
		return "products.updateform";	
	}
	
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update(@ModelAttribute Product product, BindingResult bindingResult) throws BusinessException {
		validator.validate(product, bindingResult);
		if (bindingResult.hasErrors()){
			return  loggedUser.isAdmin() ? "products.updateformbyadmin" : "products.updateform";
		}
		long sellerid = loggedUser.isAdmin() ? product.getSeller().getId() : loggedUser.getUserDetails().getId();
		Collection<Image> images = imageService.getProductImages(product.getId());
		productService.updateProduct(product,images,sellerid);
		return "redirect:/products/viewsforsellers.do";
	}	
	
	
	@RequestMapping(value="/delete_start")
	public String deleteStart(@RequestParam("id") Long id, Model model) throws BusinessException {	
		Product product = productService.findProductById(id);
		model.addAttribute("product", product);
		return "products.deleteform";
	}
	
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public String delete(@ModelAttribute Product product, BindingResult bindingResult) throws BusinessException {
		long sellerid = loggedUser.getUserDetails().getId();
		productService.deleteProduct(product,sellerid);
		return "redirect:/products/viewsforsellers.do";
	}	
	
	@RequestMapping(value="/{prod_id}/*")
	public String viewProduct(@PathVariable("prod_id")Long prod_id, Model model) throws BusinessException {
		Product p = productService.findProductById(prod_id);
		model.addAttribute("product", p);
		List<Product> lp = productService.getSameCategoryProducts(prod_id);
		model.addAttribute("sameCategoryProducts", lp);
		return "products.product";
	}
	
	// CATEGORIES
	
	@RequestMapping("/viewCategories")
	public String viewCategories(Model model) throws BusinessException {
		List<Category> categories = productService.findAllCategories();
		model.addAttribute("categories", categories);
		return "categories.views";
	}
	
	@RequestMapping("/createCategory_start")
	public String createCategoryStart(Model model) throws BusinessException {
		List<Category> categories = productService.findAllCategories();
		model.addAttribute("categories", categories);
		model.addAttribute("category", new Category());
		return "categories.createform";
	}
	
	@RequestMapping(value="/createCategory", method=RequestMethod.POST)
	public String create(@ModelAttribute Category category, BindingResult bindingResult) throws BusinessException {
		productService.createCategory(category);
		
		return "redirect:/products/viewCategories";
	}
	
	@RequestMapping("/updateCategory_start")
	public String updateCategoryStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		Category category = productService.findCategoryById(id);
		model.addAttribute("category", category);
		model.addAttribute("id", id);
		List<Category> categories = productService.findAllCategories();
		//per non permettere di associare una categoria a se stessa non la facciamo visualizzare rimuovendola dalla lista
		Category toRemove = null;
		for(Iterator<Category> i = categories.iterator(); i.hasNext();){
			Category c = i.next();
			if(c.getId() == (category.getId())) toRemove = c; 
		}
		categories.remove(toRemove);
		model.addAttribute("categories", categories);
		return "categories.updateform";
	}
	
	
	@RequestMapping(value="/updateCategory", method = RequestMethod.POST)
	public String update(@ModelAttribute Category category, BindingResult bindingResult) throws BusinessException {
		productService.updateCategory(category);
		return "redirect:/products/viewCategories";
	}	
	
	
	@RequestMapping(value="/deleteCategory_start")
	public String deleteCategoryStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		List<Category> categories = productService.findAllCategories();
		model.addAttribute("categories", categories);
		Category category = productService.findCategoryById(id);
		model.addAttribute("category", category);
		return "categories.deleteform";
	}
	
	
	@RequestMapping(value="/deleteCategory", method = RequestMethod.POST)
	public String deleteCategory(@ModelAttribute Category category, BindingResult bindingResult) throws BusinessException {
		productService.deleteCategory(category.getId());
		return "redirect:/products/viewCategories";
	}	
	

// MEASURES
	
	@RequestMapping("/viewMeasures")
	public String viewMeasures() {
		return "measures.views";
	}
	
	@RequestMapping("/createMeasure_start")
	public String createMeasureStart(Model model) throws BusinessException {
		model.addAttribute("measure", new Measure());
		return "measures.createform";
	}

	@RequestMapping(value="/createMeasure", method=RequestMethod.POST)
	public String create(@ModelAttribute Measure measure, BindingResult bindingResult) throws BusinessException {
		productService.createMeasure(measure);
		return "redirect:/products/viewMeasures";
	}
	
	@RequestMapping("/updateMeasure_start")
	public String updateMeasureStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		Measure measure = productService.findMeasureById(id);
		model.addAttribute("measure", measure);
		model.addAttribute("id", id);
		return "measures.updateform";
	}
	
	@RequestMapping(value="/updateMeasure", method=RequestMethod.POST)
	public String update(@ModelAttribute Measure measure, BindingResult bindingResult) throws BusinessException {
		productService.updateMeasure(measure);
		return "redirect:/products/viewMeasures";
	}	

	
	@RequestMapping(value="/deleteMeasure_start")
	public String deleteMeasureStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		
		Measure measure = productService.findMeasureById(id);
		model.addAttribute("measure", measure);
		return "measures.deleteform";
	}
	
	
	@RequestMapping(value="/deleteMeasure", method = RequestMethod.POST)
	public String deleteMeasure(@ModelAttribute Measure measure, BindingResult bindingResult) throws BusinessException {
		productService.deleteMeasure(measure);
		return "redirect:/products/viewMeasures";
	}	
	
// Model Attributes	
	

	@ModelAttribute
	public void findAllCategories(Model model) throws BusinessException {
		List<Category> categories = productService.findAllCategories();
		model.addAttribute("categories", categories);
	}
	
	@ModelAttribute
	public void findAllMeasures(Model model) throws BusinessException {
		List<Measure> measures = productService.findAllMeasures();
		model.addAttribute("measures", measures);
	}

	
}
