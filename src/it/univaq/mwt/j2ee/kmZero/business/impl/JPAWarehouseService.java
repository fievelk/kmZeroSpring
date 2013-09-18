package it.univaq.mwt.j2ee.kmZero.business.impl;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.WarehouseService;
import it.univaq.mwt.j2ee.kmZero.business.model.Warehouse;

@Service
public class JPAWarehouseService implements WarehouseService {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void updateWarehouse(Warehouse warehouse) throws BusinessException {
        
        em.merge(warehouse);
		
	}

	@Override
	public Warehouse findWarehouseById(long id) throws BusinessException {

		Warehouse warehouse = em.find(Warehouse.class, id);	
		
		return warehouse;

	}

	@Override
	public String findWarehouseAddress() throws BusinessException {
		
		TypedQuery<Warehouse> query = em.createQuery("SELECT w FROM Warehouse w", Warehouse.class);
		Warehouse warehouse = query.getSingleResult();
		String address = warehouse.getAddress();
		
		return address;
	}

	@Override
	public Warehouse findWarehouse() throws BusinessException {
		
		TypedQuery<Warehouse> query = em.createQuery("SELECT w FROM Warehouse w", Warehouse.class);
		Warehouse warehouse = query.getSingleResult();
		
		return warehouse;

	}

}
