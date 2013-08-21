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
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;


import it.univaq.mwt.j2ee.kmZero.common.MultipartBean;
import it.univaq.mwt.j2ee.kmZero.common.km0ImageUtility;
import it.univaq.mwt.j2ee.kmZero.common.spring.security.LoggedUser;
import it.univaq.mwt.j2ee.kmZero.common.spring.validation.ValidationUtility;

@Controller
public class ImagesController {
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private ImageValidator validator;
	
	@Autowired
	private UserService  userService;
	
	/*ADD IMAGES*/
	/*i metodi start sono parametrizzati per tutti i modelli che utilizzano le immagini (product e seller nel nostro caso)*/
	/*viene invocato via ajax per caricare la jsp dentro la finestra modale (che � unica) */
	/*prima di ritornare vengono impostarti i parametri da sotituire compresa la action del form presente nella jsp*/
	
	@RequestMapping(value="/{owner_kind}/{owner_id}/addImages_start",method = RequestMethod.POST)
	public String productAddImagesStart(@PathVariable("owner_kind")String owner_kind,@PathVariable("owner_id")Long owner_id, Model model) throws BusinessException {
		model.addAttribute("owner_id", owner_id);
		model.addAttribute("action", "/"+owner_kind+"/image/addImages");
		return "image.addform";
	}
	
	/*questi 2 metodi che seguono vengono sempre chiamati via ajax (image.js) quando il form viene submittato(corrispondono alla action impostata nel metodo precedente). Ne sono uno per modello perch� abbiamo bisogno di generare thumbnails di dimensioni diverse (si protrebbe parametrizzare anche questo)*/
	@RequestMapping(value="/prod/image/addImages", method = RequestMethod.POST)
	public @ResponseBody ResponseImages productAddImages(@ModelAttribute("fileUpload") MultipartBean fileUpload,@ModelAttribute("owner_id") Long owner_id) throws BusinessException, IOException {
		if(validator.validateProdImage(owner_id)){
			List<MultipartFile> files = fileUpload.getFiles();
			List<Image> images = km0ImageUtility.generateImages(files,410,410);
			imageService.setProductImages(owner_id,images);
			return reloadProductImages(owner_id);	
		}else{
			return responseError();
		}
	}
		
	@RequestMapping(value="/selr/image/addImages", method = RequestMethod.POST)
	public @ResponseBody ResponseImages sellerAddImages(@ModelAttribute("fileUpload") MultipartBean fileUpload,@ModelAttribute("owner_id") Long owner_id) throws BusinessException, IOException {
		if(validator.validateSellerImage(owner_id)){
			List<MultipartFile> files = fileUpload.getFiles();
			List<Image> images = km0ImageUtility.generateImages(files,640,269);
			imageService.setSellerImages(owner_id,images);
			return reloadSellerImages(owner_id);	
		}else {
			return responseError();
		}
	}
	
	@RequestMapping(value="/selr_content/image/addImages", method = RequestMethod.POST)
	public @ResponseBody ResponseImages sellerContentAddImages(@ModelAttribute("fileUpload") MultipartBean fileUpload,@ModelAttribute("owner_id") Long owner_id) throws BusinessException, IOException {
		if(validator.validateSellerContentImage(owner_id)){
			List<MultipartFile> files = fileUpload.getFiles();
			List<Image> images = km0ImageUtility.generateImages(files,350,350);
			imageService.setSellerContentImage(owner_id, images.get(0));
			return reloadSellerContentImages(owner_id);
		}else {
			return responseError();
		}
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
		if(validator.validateProdImage(owner_id)){
			imageService.updateImage(image);
			return reloadProductImages(owner_id);
		}else{
			return responseError();
		}
	}
	
	@RequestMapping(value ="/selr/image/updateImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages sellerUpdateImage(@ModelAttribute Image image,@ModelAttribute("owner_id") Long owner_id)throws BusinessException {
		if(validator.validateSellerImage(owner_id)){
			imageService.updateImage(image);
			return reloadSellerImages(owner_id);
		}else{
			return responseError();
		}
	}
	
	@RequestMapping(value ="/selr_content/image/updateImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages sellerContentUpdateImage(@ModelAttribute Image image,@ModelAttribute("owner_id") Long owner_id)throws BusinessException {
		if(validator.validateSellerContentImage(owner_id)){
			image.setAltName(image.getAltName().toLowerCase().replace(" ","-"));
			imageService.updateImage(image);
			return reloadSellerContentImages(owner_id);
		}else{
			return responseError();
		}
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
		if(validator.validateProdImage(owner_id)){
			imageService.deleteProductImage(image_id,owner_id);	
			return reloadProductImages(owner_id);	
		}else{
			return responseError();
		}
	}
	
	@RequestMapping(value ="/selr/image/deleteImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages sellerDeleteImage(@ModelAttribute("id") Long image_id,@ModelAttribute("owner_id") Long owner_id)throws BusinessException {
		if(validator.validateSellerImage(owner_id)){
			imageService.deleteSellerImage(image_id,owner_id);	
			return reloadSellerImages(owner_id);	
		}else{
			return responseError();
		}
	}
	
	@RequestMapping(value ="/selr_content/image/deleteImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages sellerContentDeleteImage(@ModelAttribute("id") Long image_id,@ModelAttribute("owner_id") Long owner_id)throws BusinessException {
		if(validator.validateSellerContentImage(owner_id)){
			imageService.deleteSellerContentImage(image_id,owner_id);	
			return reloadSellerContentImages(owner_id);
		}else{
			return responseError();
		}
	}

	//<img src="">
	@RequestMapping(value = {"/prod/image/{id}/*","/prod/image/{id}","/selr/image/{id}/*","/selr/image/{id}","/selr_content/image/{id}/*","/selr_content/image/{id}"})
	@ResponseBody
    public byte[] getImage(@PathVariable("id")Long id)throws BusinessException {
		Image image = imageService.getImage(id);
		return image.getImageData();
	}
	
	/*Metodi per ricaricare le immagini ogni volta che si effettua una CRUD*/
	/*Necessario differenziare ResponseImages in prod e selr e selr_content per poter passare i giusti parametri al javascript (images.js) function ProcessJson*/
	public ResponseImages reloadProductImages(long prodId) throws BusinessException{
		List<Image> ri = imageService.getProductImages(prodId);
		return new ResponseImages(ri, prodId,"prod");
	}
	
	public ResponseImages reloadSellerImages(long userId) throws BusinessException{
		List<Image> ri = new ArrayList<Image>(); 
		ri = imageService.getSellerImages(userId);
		return new ResponseImages(ri, userId,"selr");
	}
	
	public ResponseImages reloadSellerContentImages(long sellercontentId) throws BusinessException{
		Image i = imageService.getSellerContentImages(sellercontentId);
		List<Image> ri = new ArrayList<Image>();
		//se non viene effettuato questo controllo (i == null), nell'oggetto json viene ritornato un array con un unico elemento null e questo fa fallire image.js che deve ricostruire la lista delle immagini. Se l'array delle immagini � [ ] OK, se � [null] fallisce quindi � importante fare il controllo e ritornare [ ] 
		if(i != null) ri.add(i);
		return new ResponseImages(ri, sellercontentId,"selr_content");
	}
	
	public ResponseImages responseError() throws BusinessException{
		ResponseImages r = new ResponseImages();
		r.setTrueData(false);
		return r;
	}
	


}
