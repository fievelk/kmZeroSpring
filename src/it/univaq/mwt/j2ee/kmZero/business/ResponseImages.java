package it.univaq.mwt.j2ee.kmZero.business;

import it.univaq.mwt.j2ee.kmZero.business.model.Image;

import java.io.Serializable;
import java.util.List;

public class ResponseImages implements Serializable{
	
	private List<Image> images;
	private long owner_id;
	private String owner_kind;
	private boolean trueData = true;
	
	public ResponseImages() {
	}
	
	public ResponseImages(List<Image> images, long owner_id, String owner_kind) {
		super();
		this.images = images;
		this.owner_id = owner_id;
		this.owner_kind = owner_kind;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	public long getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(long owner_id) {
		this.owner_id = owner_id;
	}
	public String getOwner_kind() {
		return owner_kind;
	}
	public void setOwner_kind(String owner_kind) {
		this.owner_kind = owner_kind;
	}

	public boolean isTrueData() {
		return trueData;
	}

	public void setTrueData(boolean trueData) {
		this.trueData = trueData;
	}
	
	

}
