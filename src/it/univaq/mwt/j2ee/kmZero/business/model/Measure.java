package it.univaq.mwt.j2ee.kmZero.business.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="measures")
public class Measure implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "measures_seq")
	@Column(name="measure_id")
	@SequenceGenerator(name = "measures_seq", allocationSize=1)
	private long id;
	
	private String name;

	private String shortName;

	public Measure() {
		super();
	}
	
	public Measure(String name) {
		super();
		this.name = name;
	}

	public Measure(long id, String name, String shortName) {
		super();
		this.id = id;
		this.name = name;
		this.shortName = shortName;
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	

}
