package it.univaq.mwt.j2ee.kmZero.business.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.model.Image;
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

	
}
