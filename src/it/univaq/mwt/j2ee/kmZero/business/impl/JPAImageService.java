package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.model.Image;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.SellerContent;

@Service
public class JPAImageService implements ImageService{

	@PersistenceContext
	private EntityManager em;

	@Override
	public Image getImage(Long id) throws BusinessException {
		Image img = em.find(Image.class, id);
		return img;
	}
	
	@Override
	public Collection<Image> getProductImages(Long productId) throws BusinessException {      	
		Product p = em.find(Product.class, productId);
		return p.getImages();
	}
	
	@Override  
	@Transactional
	public void setProductImages(Long productId, Collection<Image> ci) throws BusinessException {
		Product p = em.find(Product.class, productId);
		p.addImages(ci);
	}
	
	@Override
	@Transactional
	public void updateProductImage(Image image, Long productId) throws BusinessException {
		Image i = em.find(Image.class,image.getId());
		i.setAltName(image.getAltName());
		i.setPosition(image.getPosition());
		Product p = em.find(Product.class, productId);
		//il metodo setImages effettua l'ordinamento
		p.setImages(p.getImages());
	}
	
	@Override
	@Transactional
	public void deleteProductImage(Long imageId, Long productId) throws BusinessException {
		Image i = em.find(Image.class, imageId);
		Product p = em.find(Product.class, productId);
		p.getImages().remove(i);
		
	}

	
	@Override
	public Collection<Image> getSellerImages(Long sellerId) throws BusinessException {
		Seller s = em.find(Seller.class, sellerId);
		return s.getImages();
	}
	
	@Override
	@Transactional
	public void setSellerImages(Long sellerId, Collection<Image> ci) throws BusinessException {
		Seller s = em.find(Seller.class, sellerId);
		s.addImages(ci);
		
	}
	
	@Override
	@Transactional
	public void updateSellerImage(Image image, Long sellerId) throws BusinessException {
		Image i = em.find(Image.class,image.getId());
		i.setAltName(image.getAltName());
		i.setPosition(image.getPosition());
		Seller s = em.find(Seller.class, sellerId);
		s.setImages(s.getImages());
	}

	@Override
	@Transactional
	public void deleteSellerImage(Long imageId, Long sellerId)	throws BusinessException {
		Image i = em.find(Image.class, imageId);
		Seller s = em.find(Seller.class, sellerId);
		s.getImages().remove(i);
	}

	@Override
	public Image getSellerContentImages(Long sellercontentId) {
		SellerContent sc = em.find(SellerContent.class, sellercontentId);
		return sc.getImage();
	}
	
	@Override
	@Transactional
	public void setSellerContentImage(Long sellercontentId, Image image) throws BusinessException {
		SellerContent sc = em.find(SellerContent.class, sellercontentId);
		sc.setImage(image);
	}
	
	@Override
	@Transactional
	public void updateSellerContentImage(Image image, Long sellercontentId) {
		SellerContent sc = em.find(SellerContent.class,sellercontentId);
		sc.getImage().setAltName(image.getAltName());
	}

	@Override
	@Transactional
	public void deleteSellerContentImage(Long sellercontentId) {
		SellerContent sc = em.find(SellerContent.class,sellercontentId);
		sc.setImage(null);
	}
	
}
