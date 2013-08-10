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
	public void updateImage(Image image) throws BusinessException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Image i = em.find(Image.class,image.getId());
		i.setAltName(image.getAltName());
		i.setPosition(image.getPosition());
		tx.commit();
		em.close();	
	}
	
	
	@Override
	public List<Image> getProductImages(Long id) throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		System.out.println("ProdottoID:"+id);
		Product p = em.find(Product.class, id);
		System.out.println("Prodotto:"+p.getName());
		tx.commit();
		return p.getImages();
	}
	
	@Override
	public void setProductImages(Long id, List<Image> ci) throws BusinessException {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		Product p = em.find(Product.class, id);
		//riprendo la collezione di immagini giˆ associate all'oggetto e...
		List<Image> c = p.getImages();
		//...aggiungo la nuova collezione (le fondo assieme)
		c.addAll(ci);
		p.setImages(c);
		//em.merge(p);
		
		tx.commit();
		em.close();
	}
	
	@Override
	public void deleteProductImage(Long id, Long product_id) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
	
		tx.begin();
		Image i = em.find(Image.class, id);
		Product p = em.find(Product.class, product_id);
		em.merge(p);
		p.getImages().remove(i);
		tx.commit();	
		em.close();
		
	}

	
	@Override
	public List<Image> getSellerImages(Long id) throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		Seller s = em.find(Seller.class, id);
		return s.getImages();
	}
	
	@Override
	public void setSellerImages(Long id, List<Image> ci) throws BusinessException {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		Seller s = em.find(Seller.class, id);
		//riprendo la collezione di immagini giˆ associate all'oggetto e...
		List<Image> c = s.getImages();
		//...aggiungo la nuova collezione (le fondo assieme)
		c.addAll(ci);
		s.setImages(c);
		em.merge(s);
		
		tx.commit();
		em.close();
	}


	@Override
	public void deleteSellerImage(Long image_id, Long owner_id)	throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
	
		tx.begin();
		Image i = em.find(Image.class, image_id);
		Seller s = em.find(Seller.class, owner_id);
		em.merge(s);
		s.getImages().remove(i);
		tx.commit();	
		em.close();	
	}
	
	@Override
	public void setSellerContentImage(Long id, Image i) throws BusinessException {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		SellerContent sc = em.find(SellerContent.class, id);
		sc.setImage(i);
		
		
		tx.commit();
		em.close();
	}


	@Override
	public Image getSellerContentImages(long sellercontentId) {
		EntityManager em = this.emf.createEntityManager();
		SellerContent sc = em.find(SellerContent.class, sellercontentId);
		return sc.getImage();
	}


	@Override
	public void deleteSellerContentImage(Long image_id, Long owner_id) {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
	
		tx.begin();
		Image i = em.find(Image.class, image_id);
		SellerContent sc = em.find(SellerContent.class,owner_id);
		sc.setImage(null);
		em.merge(sc);
		tx.commit();	
		em.close();	
		
	}
	

	
}
