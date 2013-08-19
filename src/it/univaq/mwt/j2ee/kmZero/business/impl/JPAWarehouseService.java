package it.univaq.mwt.j2ee.kmZero.business.impl;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.model.Warehouse;
import it.univaq.mwt.j2ee.kmZero.business.service.WarehouseService;

public class JPAWarehouseService implements WarehouseService {
	
	@PersistenceUnit
	private EntityManagerFactory emf;

	@Override
	public void updateWarehouse(Warehouse warehouse) throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        em.merge(warehouse);
        
        tx.commit();
        em.close();
		
	}

	@Override
	public Warehouse findWarehouseById(long id) throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Warehouse warehouse = em.find(Warehouse.class, id);	
		
		tx.commit();
		em.close();
		return warehouse;

	}

	@Override
	public String findWarehouseAddress() throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		TypedQuery<Warehouse> query = em.createQuery("SELECT w FROM Warehouse w", Warehouse.class);
		Warehouse warehouse = query.getSingleResult();
		String address = warehouse.getAddress();
		
		tx.commit();
		em.close();
		return address;
	}

	@Override
	public Warehouse findWarehouse() throws BusinessException {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		TypedQuery<Warehouse> query = em.createQuery("SELECT w FROM Warehouse w", Warehouse.class);
		Warehouse warehouse = query.getSingleResult();
		
		tx.commit();
		em.close();
		return warehouse;

	}

}
