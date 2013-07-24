package it.univaq.mwt.j2ee.kmZero.business.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="image")
public class Image implements Serializable{
	

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String name;
	@Basic(fetch=FetchType.LAZY)//sembra non avere effetto...
	@Lob
	private byte [] imageData;
	
	private static final long serialVersionUID = 1L;
	
	public Image() {
		super();
	}

	public Image(String name, byte[] imageData) {
		this.name = name;
		this.imageData = imageData;
	}


	public Image(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Image(long id, String name, byte[] imageData) {
		this(name,imageData);
		this.id = id;
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

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	

}
