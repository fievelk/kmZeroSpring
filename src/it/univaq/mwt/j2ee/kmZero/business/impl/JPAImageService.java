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
		Query query = em.createQuery("UPDATE Image SET altName= :altName, position= :position WHERE id= :id");
		query.setParameter("altName", image.getAltName());
		query.setParameter("position", image.getPosition());
		query.setParameter("id", image.getId());
		query.executeUpdate();
		tx.commit();
		em.close();
		
	}
	
	@Override
	public boolean deleteProductImage(Long id, Long product_id) throws BusinessException {
		
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		boolean val=false;
		try {		
			tx.begin();
			Image i = em.find(Image.class, id);
			Product p = em.find(Product.class, product_id);
			em.merge(p);
			p.getImages().remove(i);
			tx.commit();
			val=true;
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		em.close();
		return val;
	}
	
	@Override
	public List<Image> getProductImages(Long id) throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Product p = em.find(Product.class, id);
		tx.commit();
		return p.getImages();
	}
	

	
	@Override
	public List<Image> getSellerImages(Long id) throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		Seller s = em.find(Seller.class, id);
		return s.getImages();
	}

	//questo metodo recupera solo id e nome dell'immagine ma non funziona la query
	//Ritorna solo l'id e il nome dell'imagine senza i dati blob
	/*
	@Override
	public Collection<Image> getProductImagesIdName(Long id) throws BusinessException {
		System.out.println("id prodotto:"+id);
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		TypedQuery<Image> query = em.createQuery("SELECT NEW it.univaq.mwt.j2ee.kmZero.business.model.Image(i.id,i.name) FROM Image i JOIN Product p WHERE p.id= :id", Image.class);
		query.setParameter("id", id);
		tx.begin();	
		List<Image> images = (List<Image>)query.getResultList();
		tx.commit();
		em.close();
		return images;	
	}*/
	
}
