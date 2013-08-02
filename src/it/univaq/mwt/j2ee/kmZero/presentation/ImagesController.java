package it.univaq.mwt.j2ee.kmZero.presentation;

import java.io.IOException;
import java.util.ArrayList;
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

	/*ADD IMAGES*/
	/*i metodi start sono parametrizzati per tutti i modelli che utilizzano le immagini (product e seller nel nostro caso)*/
	/*viene invocato via ajax per caricare la jsp dentro la finestra modale (che è unica) */
	/*prima di ritornare vengono impostarti i parametri da sotituire compresa la action del form presente nella jsp*/
	@RequestMapping(value="/{owner_kind}/{owner_id}/addImages_start",method = RequestMethod.POST)
	public String productAddImagesStart(@PathVariable("owner_kind")String owner_kind,@PathVariable("owner_id")Long owner_id, Model model) throws BusinessException {
		model.addAttribute("owner_id", owner_id);
		model.addAttribute("action", "/"+owner_kind+"/image/addImages");
		return "image.addform";
	}
	
	/*questi 2 metodi che seguono vengono sempre chiamati via ajax (image.js) quando il form viene submittato(corrispondono alla action impostata nel metodo precedente). Ne sono uno per modello perchè abbiamo bisogno di generare thumbnails di dimensioni diverse (si protrebbe parametrizzare anche questo)*/
	@RequestMapping(value="/prod/image/addImages", method = RequestMethod.POST)
	public @ResponseBody ResponseImages productAddImages(@ModelAttribute("fileUpload") MultipartBean fileUpload,@ModelAttribute("owner_id") Long owner_id) throws BusinessException, IOException {
		
		List<MultipartFile> files = fileUpload.getFiles();
		List<Image> images = km0ImageUtility.generateImages(files,220,410);
		imageService.setProductImages(owner_id,images);
		return reloadProductImages(owner_id);	
	}
		
	@RequestMapping(value="/selr/image/addImages", method = RequestMethod.POST)
	public @ResponseBody ResponseImages sellerAddImages(@ModelAttribute("fileUpload") MultipartBean fileUpload,@ModelAttribute("owner_id") Long owner_id) throws BusinessException, IOException {
		
		List<MultipartFile> files = fileUpload.getFiles();
		List<Image> images = km0ImageUtility.generateImages(files,640,269);
		imageService.setSellerImages(owner_id,images);
		return reloadSellerImages(owner_id);	
	}
	
	
	/*UPDATE IMAGE*/
	@RequestMapping(value="/{owner_kind}/{owner_id}/image/{image_id}/updateImage_start",method = RequestMethod.POST)
	public String updateImageStart(@PathVariable("owner_kind")String owner_kind,@PathVariable("owner_id")Long owner_id,@PathVariable("image_id")Long image_id, Model model) throws BusinessException {
		Image image = imageService.getImage(image_id);
		model.addAttribute("image", image);
		model.addAttribute("owner_id", owner_id);
		model.addAttribute("owner_kind", owner_kind);
		model.addAttribute("action", "/"+owner_kind+"/image/updateImage");
		return "image.updateform";
	}
	
	@RequestMapping(value ="/prod/image/updateImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages productUpdateImage(@ModelAttribute Image image,@ModelAttribute("owner_id") Long owner_id)throws BusinessException {
		imageService.updateImage(image);
		return reloadProductImages(owner_id);	
	}
	
	@RequestMapping(value ="/selr/image/updateImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages sellerUpdateImage(@ModelAttribute Image image,@ModelAttribute("owner_id") Long owner_id)throws BusinessException {
		imageService.updateImage(image);
		return reloadSellerImages(owner_id);	
	}
	
	/*DELETE IMAGE*/
	@RequestMapping(value="/{owner_kind}/{owner_id}/image/{image_id}/deleteImage_start",method = RequestMethod.POST)
	public String deleteImageStart(@PathVariable("owner_kind")String owner_kind,@PathVariable("owner_id")Long owner_id,@PathVariable("image_id")Long image_id, Model model) throws BusinessException {
		Image image = imageService.getImage(image_id);
		model.addAttribute("image", image);
		model.addAttribute("owner_id", owner_id);
		model.addAttribute("owner_kind", owner_kind);
		model.addAttribute("action", "/"+owner_kind+"/image/deleteImage");
		return "image.deleteform";
	}
	
	@RequestMapping(value ="/prod/image/deleteImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages productDeleteImage(@ModelAttribute("id") Long image_id,@ModelAttribute("owner_id") Long owner_id)throws BusinessException {
		imageService.deleteProductImage(image_id,owner_id);	
		return reloadProductImages(owner_id);	
	}
	
	@RequestMapping(value ="/selr/image/deleteImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages sellerDeleteImage(@ModelAttribute("id") Long image_id,@ModelAttribute("owner_id") Long owner_id)throws BusinessException {
		imageService.deleteSellerImage(image_id,owner_id);	
		return reloadSellerImages(owner_id);	
	}
	
	/*Metodi per ricaricare le immagini ogni volta che si effettua una CRUD*/
	/*Necessario differenziare ResponseImages in prod e selr per poter passare i giusti parametri al javascript (images.js) function ProcessJson*/
	public ResponseImages reloadProductImages(long prod_id) throws BusinessException{
		List<Image> ri = imageService.getProductImages(prod_id);
		return new ResponseImages(ri, prod_id,"prod");
	}
	
	public ResponseImages reloadSellerImages(long prod_id) throws BusinessException{
		List<Image> ri = imageService.getSellerImages(prod_id);
		return new ResponseImages(ri, prod_id,"selr");
	}

	//<img src="">
	@RequestMapping(value = {"/prod/image/{id}/*","/prod/image/{id}","/selr/image/{id}/*","/selr/image/{id}"})
	@ResponseBody
    public byte[] getImage(@PathVariable("id")Long id)throws BusinessException {
		Image image = imageService.getImage(id);
		return image.getImageData();
	}
	

}
