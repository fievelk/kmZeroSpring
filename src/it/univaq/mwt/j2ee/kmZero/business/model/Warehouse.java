package it.univaq.mwt.j2ee.kmZero.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="warehouses")
public class Warehouse {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "warehouses_seq")
	@SequenceGenerator(name = "warehouses_seq", allocationSize=1)
	private long id;

	@Column(name="name", nullable=false)
	private String name;

	@Column(name="address")
	private String address;
	
	
	public Warehouse() {
		super();
	}


	public Warehouse(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
