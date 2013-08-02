package it.univaq.mwt.j2ee.kmZero.business.service;

import java.util.Collection;
import java.util.List;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.model.Image;

public interface ImageService {
	
	public Image getImage(Long id) throws BusinessException;

	public void updateImage(Image image) throws BusinessException;

	public List<Image> getProductImages(Long id) throws BusinessException;
	
	void setProductImages(Long id, List<Image> ci) throws BusinessException;
	
	public void deleteProductImage(Long id, Long product_id) throws BusinessException;
	
	public List<Image> getSellerImages(Long id) throws BusinessException;

	void setSellerImages(Long id, List<Image> ci) throws BusinessException;

	public void deleteSellerImage(Long image_id, Long owner_id) throws BusinessException;

}
