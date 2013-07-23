package it.univaq.mwt.j2ee.kmZero.presentation;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.service.ImageService;
import it.univaq.mwt.j2ee.kmZero.business.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;
import it.univaq.mwt.j2ee.kmZero.business.model.Category;
import it.univaq.mwt.j2ee.kmZero.business.model.Image;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.common.DateEditor;
import it.univaq.mwt.j2ee.kmZero.common.MultipartBean;
import it.univaq.mwt.j2ee.kmZero.common.km0ImageUtility;


@Controller
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductService service;
	
	@Autowired
	private ImageService imageService;

	
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
	
	@RequestMapping(value="/testCreateProduct")
	public String createProduct(Model model) throws BusinessException {

		//Eliminare, è solo per il test
		Product product = new Product();
		
		service.createProduct(product);	
		model.addAttribute("test", 654);
		return "common.test";
	}

	
	@RequestMapping("/views.do")
	public String views() {
		return "products.views";
	}
	
	
	@RequestMapping("/viewProducts")
	@ResponseBody
	public ResponseGrid<Product> viewProducts(@ModelAttribute RequestGrid requestGrid) throws BusinessException {
		ResponseGrid<Product> result = service.viewProducts(requestGrid);
		return result;
	}
	
	
	@RequestMapping("/viewsforsellers.do")
	public String viewsForSellers() {
		return "products.viewsforsellers";
	}
	
	
	@RequestMapping("/viewProductsBySellerIdPaginated")
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
		return "products.createform";
	}
	
	
	@RequestMapping(value="/create.do", method=RequestMethod.POST)
	public String create(@ModelAttribute Product product, BindingResult bindingResult) throws BusinessException {
		service.createProduct(product);
		return "redirect:/products/viewsforsellers.do";
	}
	
	
	@RequestMapping("/update_start.do")
	public String updateStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		Product product = service.findProductById(id);
		model.addAttribute("product", product);
		model.addAttribute("id", id);
		return "products.updateform";
	}
	
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	public String update(@ModelAttribute Product product, BindingResult bindingResult) throws BusinessException {
		service.updateProduct(product);
		return "redirect:/products/viewsforsellers.do";
	}	
	
	
	@RequestMapping(value="/delete_start.do")
	public String deleteStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		
		Product product = service.findProductById(id);
		model.addAttribute("product", product);
		return "products.deleteform";
	}
	
	
	@RequestMapping(value="/delete.do", method = RequestMethod.POST)
	public String delete(@ModelAttribute Product product, BindingResult bindingResult) throws BusinessException {
		service.deleteProduct(product);
		return "redirect:/products/viewsforsellers.do";
	}	
	
	@RequestMapping(value="/addImages", method = RequestMethod.POST)
	public @ResponseBody Collection<Image> addImages(@ModelAttribute("fileUpload") MultipartBean fileUpload,@ModelAttribute("prod_id") Long id) throws BusinessException, IOException {
		
			Collection<Image> ci = new HashSet<Image>();
			List<MultipartFile> l = fileUpload.getFiles();
	
			for (Iterator<MultipartFile> i = l.iterator(); i.hasNext();){
				MultipartFile mpf = (MultipartFile)i.next();
				//File cannot be null 
				if(!mpf.isEmpty()){
					byte [] scaledimg = km0ImageUtility.getScaledImage(220, 410, mpf.getBytes(), mpf.getContentType());
					Image img = new Image(mpf.getOriginalFilename(), scaledimg);
					ci.add(img);
				}
	        }
			service.setProductImages(id,ci);
		
		return service.getProductImagesIdName(id);
	}
	
	@RequestMapping(value ="/image/{id}")
	@ResponseBody
    public byte[] getImage(@PathVariable("id")Long id)throws BusinessException {
		Image image = imageService.getImage(id);
		return image.getImageData();
	}
	
	@RequestMapping(value ="/{prod_id}/image/{id}/delete", method = RequestMethod.POST)
	@ResponseBody
    public boolean deleteImage(@PathVariable("id")Long id,@PathVariable("prod_id")Long p_id)throws BusinessException {
		return service.deleteImage(id,p_id);	
	}

	
	@ModelAttribute
	public void findAllCategories(Model model) throws BusinessException {
		List<Category> categories = service.findAllCategories();
		model.addAttribute("categories", categories);
	}
	

}
