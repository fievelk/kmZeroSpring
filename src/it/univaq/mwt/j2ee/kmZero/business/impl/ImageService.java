package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.Collection;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.model.Image;

public interface ImageService {
	
	public Image getImage(Long id) throws BusinessException;
	
	public Collection<Image> getProductImages(Long productId) throws BusinessException;
	
	void setProductImages(Long productId, Collection<Image> ci) throws BusinessException;
	
	void updateProductImage(Image image, Long productId) throws BusinessException;
	
	public void deleteProductImage(Long imageId, Long productId) throws BusinessException;
	
	public Collection<Image> getSellerImages(Long sellerId) throws BusinessException;

	void setSellerImages(Long id, Collection<Image> ci) throws BusinessException;
	
	void updateSellerImage(Image image, Long sellerId) throws BusinessException;

	public void deleteSellerImage(Long imageId, Long sellerId) throws BusinessException;

	public Image getSellerContentImages(Long sellercontentId);
	
	void setSellerContentImage(Long sellercontentId, Image image) throws BusinessException;
	
	public void updateSellerContentImage(Image image, Long sellercontentId);

	public void deleteSellerContentImage(Long sellercontentId);

}
