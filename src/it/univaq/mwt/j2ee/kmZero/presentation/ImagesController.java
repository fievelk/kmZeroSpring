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
		
	/*ADD IMAGES*/
	/*i metodi start sono parametrizzati per tutti i modelli che utilizzano le immagini (Product, Seller, SellerContent nel nostro caso)*/
	/*viene invocato via ajax per caricare la jsp dentro la finestra modale (che è unica) */
	/*prima di ritornare vengono impostati i parametri da sotituire compresa la action del form presente nella jsp*/
	
	@RequestMapping(value="/{ownerKind}/{ownerId}/addImages_start",method = RequestMethod.POST)
	public String productAddImagesStart(@PathVariable("ownerKind")String ownerKind,@PathVariable("ownerId")Long ownerId, Model model) throws BusinessException {
		model.addAttribute("ownerId", ownerId);
		model.addAttribute("action", "/"+ownerKind+"/addImages");
		return "image.addform";
	}
	
	/*questi 2 metodi che seguono vengono sempre chiamati via ajax (image.js) quando il form viene submittato(corrispondono alla action impostata nel metodo start). Ne sono uno per modello perchè abbiamo bisogno di generare thumbnails di dimensioni diverse (si protrebbe parametrizzare anche questo)*/
	@RequestMapping(value="/product/addImages", method = RequestMethod.POST)
	public @ResponseBody ResponseImages productAddImages(@ModelAttribute("fileUpload") MultipartBean fileUpload,@ModelAttribute("ownerId") Long ownerId) throws BusinessException, IOException {
		if(validator.validateProdImage(ownerId)){
			Collection<MultipartFile> files = fileUpload.getFiles();
			Collection<Image> images = km0ImageUtility.generateImages(files,410,410);
			imageService.setProductImages(ownerId,images);
			return reloadProductImages(ownerId);	
		}else{
			return responseError();
		}
	}
		
	@RequestMapping(value="/seller/addImages", method = RequestMethod.POST)
	public @ResponseBody ResponseImages sellerAddImages(@ModelAttribute("fileUpload") MultipartBean fileUpload,@ModelAttribute("ownerId") Long ownerId) throws BusinessException, IOException {
		if(validator.validateSellerImage(ownerId)){
			Collection<MultipartFile> files = fileUpload.getFiles();
			Collection<Image> images = km0ImageUtility.generateImages(files,640,269);
			imageService.setSellerImages(ownerId,images);
			return reloadSellerImages(ownerId);	
		}else {
			return responseError();
		}
	}
	
	@RequestMapping(value="/sellercontent/addImages", method = RequestMethod.POST)
	public @ResponseBody ResponseImages sellerContentAddImages(@ModelAttribute("fileUpload") MultipartBean fileUpload,@ModelAttribute("ownerId") Long ownerId) throws BusinessException, IOException {
		if(validator.validateSellerContentImage(ownerId)){
			Collection<MultipartFile> files = fileUpload.getFiles();
			List<Image> li = new ArrayList<Image>(km0ImageUtility.generateImages(files,350,350));
			imageService.setSellerContentImage(ownerId,li.get(0));
			return reloadSellerContentImages(ownerId);
		}else {
			return responseError();
		}
	}
	
	
	/*UPDATE IMAGE*/
	@RequestMapping(value="/{ownerKind}/{ownerId}/image/{imageId}/updateImage_start",method = RequestMethod.POST)
	public String updateImageStart(@PathVariable("ownerKind")String ownerKind,@PathVariable("ownerId")Long ownerId,@PathVariable("imageId")Long imageId, Model model) throws BusinessException {
		Image image = imageService.getImage(imageId);
		model.addAttribute("image", image);
		model.addAttribute("ownerId", ownerId);
		model.addAttribute("ownerKind", ownerKind);
		model.addAttribute("action", "/"+ownerKind+"/updateImage");
		return "image.updateform";
	}
	
	@RequestMapping(value ="/product/updateImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages productUpdateImage(@ModelAttribute Image image,@ModelAttribute("ownerId") Long ownerId)throws BusinessException {
		if(validator.validateProdImage(ownerId)){
			imageService.updateProductImage(image,ownerId);
			return reloadProductImages(ownerId);
		}else{
			return responseError();
		}
	}
	
	@RequestMapping(value ="/seller/updateImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages sellerUpdateImage(@ModelAttribute Image image,@ModelAttribute("ownerId") Long ownerId)throws BusinessException {
		if(validator.validateSellerImage(ownerId)){
			imageService.updateSellerImage(image,ownerId);
			return reloadSellerImages(ownerId);
		}else{
			return responseError();
		}
	}
	
	@RequestMapping(value ="/sellercontent/updateImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages sellerContentUpdateImage(@ModelAttribute Image image,@ModelAttribute("ownerId") Long ownerId)throws BusinessException {
		if(validator.validateSellerContentImage(ownerId)){
			image.setAltName(image.getAltName().toLowerCase().replace(" ","-"));
			imageService.updateSellerContentImage(image,ownerId);
			return reloadSellerContentImages(ownerId);
		}else{
			return responseError();
		}
	}
	
	/*DELETE IMAGE*/
	@RequestMapping(value="/{ownerKind}/{ownerId}/image/{imageId}/deleteImage_start",method = RequestMethod.POST)
	public String deleteImageStart(@PathVariable("ownerKind")String ownerKind,@PathVariable("ownerId")Long ownerId,@PathVariable("imageId")Long imageId, Model model) throws BusinessException {
		Image image = imageService.getImage(imageId);
		model.addAttribute("image", image);
		model.addAttribute("ownerId", ownerId);
		model.addAttribute("ownerKind", ownerKind);
		model.addAttribute("action", "/"+ownerKind+"/deleteImage");
		return "image.deleteform";
	}
	
	@RequestMapping(value ="/product/deleteImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages productDeleteImage(@ModelAttribute("id") Long imageId,@ModelAttribute("ownerId") Long ownerId)throws BusinessException {
		if(validator.validateProdImage(ownerId)){
			imageService.deleteProductImage(imageId,ownerId);	
			return reloadProductImages(ownerId);	
		}else{
			return responseError();
		}
	}
	
	@RequestMapping(value ="/seller/deleteImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages sellerDeleteImage(@ModelAttribute("id") Long imageId,@ModelAttribute("ownerId") Long ownerId)throws BusinessException {
		if(validator.validateSellerImage(ownerId)){
			imageService.deleteSellerImage(imageId,ownerId);	
			return reloadSellerImages(ownerId);	
		}else{
			return responseError();
		}
	}
	
	@RequestMapping(value ="/sellercontent/deleteImage", method = RequestMethod.POST)
	@ResponseBody
    public ResponseImages sellerContentDeleteImage(@ModelAttribute("id") Long imageId,@ModelAttribute("ownerId") Long ownerId)throws BusinessException {
		if(validator.validateSellerContentImage(ownerId)){
			imageService.deleteSellerContentImage(ownerId);	
			return reloadSellerContentImages(ownerId);
		}else{
			return responseError();
		}
	}

	@RequestMapping(value = {"/product/image/{id}/*","/product/image/{id}","/seller/image/{id}/*","/seller/image/{id}","/sellercontent/image/{id}/*","/sellercontent/image/{id}"})
	@ResponseBody
    public byte[] getImage(@PathVariable("id")Long id)throws BusinessException {
		Image image = imageService.getImage(id);
		return image.getImageData();
	}
	
	/*Metodi per ricaricare le immagini ogni volta che si effettua una CRUD*/
	/*Necessario differenziare ResponseImages in product e seller e sellercontent per poter passare i giusti parametri al javascript (images.js) function ProcessJson*/
	public ResponseImages reloadProductImages(long prodId) throws BusinessException{
		Collection<Image> ri = imageService.getProductImages(prodId);
		List<Image> l = new ArrayList<Image>(ri);
		return new ResponseImages(l, prodId,"product");
	}
	
	public ResponseImages reloadSellerImages(long userId) throws BusinessException{
		Collection<Image> ri = imageService.getSellerImages(userId);
		List<Image> l = new ArrayList<Image>(ri);
		return new ResponseImages(l, userId,"seller");
	}
	
	public ResponseImages reloadSellerContentImages(long sellercontentId) throws BusinessException{
		Image i = imageService.getSellerContentImages(sellercontentId);
		List<Image> ri = new ArrayList<Image>();
		//se non viene effettuato questo controllo (i == null), nell'oggetto json viene ritornato un array con un unico elemento null e questo fa fallire image.js che deve ricostruire la lista delle immagini. Se l'array delle immagini ÔøΩ [ ] OK, se ÔøΩ [null] fallisce quindi ÔøΩ importante fare il controllo e ritornare [ ] 
		if(i != null) ri.add(i);
		return new ResponseImages(ri, sellercontentId,"sellercontent");
	}
	
	public ResponseImages responseError() throws BusinessException{
		ResponseImages r = new ResponseImages();
		r.setTrueData(false);
		return r;
	}

}
