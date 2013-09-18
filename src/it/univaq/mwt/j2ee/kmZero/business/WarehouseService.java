package it.univaq.mwt.j2ee.kmZero.business;

import it.univaq.mwt.j2ee.kmZero.business.model.Warehouse;

public interface WarehouseService {
	
	void updateWarehouse(Warehouse warehouse) throws BusinessException;

	Warehouse findWarehouseById(long id) throws BusinessException;
	
	Warehouse findWarehouse() throws BusinessException;

	String findWarehouseAddress() throws BusinessException;

}
