package it.univaq.mwt.j2ee.kmZero.presentation;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.RequestGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseGrid;
import it.univaq.mwt.j2ee.kmZero.business.ResponseImages;
import it.univaq.mwt.j2ee.kmZero.business.service.ImageService;
import it.univaq.mwt.j2ee.kmZero.business.service.ProductService;
import it.univaq.mwt.j2ee.kmZero.business.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import it.univaq.mwt.j2ee.kmZero.business.model.Image;


import it.univaq.mwt.j2ee.kmZero.common.MultipartBean;
import it.univaq.mwt.j2ee.kmZero.common.km0ImageUtility;

@Controller
public class ImagesController {
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService  userService;

	@RequestMapping(value="/products/{prod_id}/addImages_start",method = RequestMethod.POST)
	public String addImageStart(@PathVariable("prod_id")Long prod_id, Model model) throws BusinessException {
		model.addAttribute("prod_id", prod_id);
		return "products.image.addform";
	}
	
	@RequestMapping(value="/products/image/addImages", method = RequestMethod.POST)
	public @ResponseBody ResponseImages addImages(@ModelAttribute("fileUpload") MultipartBean fileUpload,@ModelAttribute("prod_id") Long prod_id) throws BusinessException, IOException {
		
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
		productService.setProductImages(prod_id,ci);
		return reloadImages(prod_id);	
	}
	
	@RequestMapping(value="/products/{prod_id}/image/{image_id}/updateImage_start",method = RequestMethod.POST)
	public String updateImageStart(@PathVariable("prod_id")Long prod_id,@PathVariable("image_id")Long image_id, Model model) throws BusinessException {
		Image image = imageService.getImage(image_id);
		model.addAttribute("image", image);
		model.addAttribute("prod_id", prod_id);
		return "products.image.updateform";
	}
	
	@RequestMapping(value ="/products/image/updateImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages updateImage(@ModelAttribute Image image,@ModelAttribute("prod_id") Long prod_id)throws BusinessException {
		imageService.updateImage(image);
		return reloadImages(prod_id);	
	}
	
	@RequestMapping(value="/products/{prod_id}/image/{image_id}/deleteImage_start",method = RequestMethod.POST)
	public String deleteImageStart(@PathVariable("prod_id")Long prod_id,@PathVariable("image_id")Long image_id, Model model) throws BusinessException {
		Image image = imageService.getImage(image_id);
		model.addAttribute("image", image);
		model.addAttribute("prod_id", prod_id);
		return "products.image.deleteform";
	}
	
	@RequestMapping(value ="/products/image/deleteImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages deleteImage(@ModelAttribute("id") Long image_id,@ModelAttribute("prod_id") Long prod_id)throws BusinessException {
		imageService.deleteProductImage(image_id,prod_id);	
		return reloadImages(prod_id);	
	}
	
	public ResponseImages reloadImages(long prod_id) throws BusinessException{
		List<Image> ri = imageService.getProductImages(prod_id);
		for(Iterator i = ri.iterator(); i.hasNext();){
			Image img = (Image) i.next();
			System.out.println("IMAGES CONTROLLER IMAGE POSITION:"+img.getPosition());
		}
		return new ResponseImages(ri, prod_id);
	}

	//img src
	@RequestMapping(value = {"/products/image/{id}/*","/products/image/{id}","/sellers/image/{id}/*","/sellers/image/{id}"})
	@ResponseBody
    public byte[] getImage(@PathVariable("id")Long id)throws BusinessException {
		Image image = imageService.getImage(id);
		return image.getImageData();
	}
	

}
