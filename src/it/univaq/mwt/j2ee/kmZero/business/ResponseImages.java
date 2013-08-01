package it.univaq.mwt.j2ee.kmZero.business;

import it.univaq.mwt.j2ee.kmZero.business.model.Image;

import java.util.List;

public class ResponseImages {
	
	private List<Image> images;
	private long id_product;
	
	public ResponseImages(List<Image> images, long id_product) {
		super();
		this.images = images;
		this.id_product = id_product;
	}
	
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	public long getId_product() {
		return id_product;
	}
	public void setId_product(long id_product) {
		this.id_product = id_product;
	}
	

}
