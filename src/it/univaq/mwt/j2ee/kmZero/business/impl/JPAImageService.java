package it.univaq.mwt.j2ee.kmZero.business.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.model.Image;
import it.univaq.mwt.j2ee.kmZero.business.model.Product;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.SellerContent;
import it.univaq.mwt.j2ee.kmZero.business.service.ImageService;

public class JPAImageService implements ImageService{

	@PersistenceUnit
	private EntityManagerFactory emf;

	@Override
	public Image getImage(Long id) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		Image img = em.find(Image.class, id);
		em.close();	
		return img;
	}
	
	
	@Override
	public Collection<Image> getProductImages(Long productId) throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Product p = em.find(Product.class, productId);
		tx.commit();
		return p.getImages();
	}
	
	
	public void setProductImages(Long productId, Collection<Image> ci) throws BusinessException {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Product p = em.find(Product.class, productId);
		p.addImages(ci);
		tx.commit();
		em.close();
	}
	
	@Override
	public void updateProductImage(Image image, Long productId) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Image i = em.find(Image.class,image.getId());
		i.setAltName(image.getAltName());
		i.setPosition(image.getPosition());
		Product p = em.find(Product.class, productId);
		//il metodo setImages effettua l'ordinamento
		p.setImages(p.getImages());
		tx.commit();
		em.close();	
	}
	
	@Override
	public void deleteProductImage(Long imageId, Long productId) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Image i = em.find(Image.class, imageId);
		Product p = em.find(Product.class, productId);
		p.getImages().remove(i);
		tx.commit();	
		em.close();
		
	}

	
	@Override
	public Collection<Image> getSellerImages(Long sellerId) throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		Seller s = em.find(Seller.class, sellerId);
		return s.getImages();
	}
	
	@Override
	public void setSellerImages(Long sellerId, Collection<Image> ci) throws BusinessException {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Seller s = em.find(Seller.class, sellerId);
		s.addImages(ci);
		tx.commit();
		em.close();
	}
	
	@Override
	public void updateSellerImage(Image image, Long sellerId) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Image i = em.find(Image.class,image.getId());
		i.setAltName(image.getAltName());
		i.setPosition(image.getPosition());
		Seller s = em.find(Seller.class, sellerId);
		s.setImages(s.getImages());
		tx.commit();
		em.close();	
	}


	@Override
	public void deleteSellerImage(Long imageId, Long sellerId)	throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
	
		tx.begin();
		Image i = em.find(Image.class, imageId);
		Seller s = em.find(Seller.class, sellerId);
		s.getImages().remove(i);
		tx.commit();	
		em.close();	
	}

	@Override
	public Image getSellerContentImages(Long sellercontentId) {
		EntityManager em = this.emf.createEntityManager();
		SellerContent sc = em.find(SellerContent.class, sellercontentId);
		return sc.getImage();
	}
	
	@Override
	public void setSellerContentImage(Long sellercontentId, Image image) throws BusinessException {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		SellerContent sc = em.find(SellerContent.class, sellercontentId);
		sc.setImage(image);
		tx.commit();
		em.close();
	}
	
	@Override
	public void updateSellerContentImage(Image image, Long sellercontentId) {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		SellerContent sc = em.find(SellerContent.class,sellercontentId);
		Image i = em.find(Image.class,image.getId());
		i.setAltName(image.getAltName());		
		tx.commit();
		em.close();

	}

	@Override
	public void deleteSellerContentImage(Long imageId, Long sellercontentId) {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Image i = em.find(Image.class, imageId);
		SellerContent sc = em.find(SellerContent.class,sellercontentId);
		sc.setImage(null);
		em.merge(sc);
		tx.commit();	
		em.close();	
		
	}




	



	

	
}
