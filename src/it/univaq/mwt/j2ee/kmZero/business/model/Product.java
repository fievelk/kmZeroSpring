package it.univaq.mwt.j2ee.kmZero.business.model;

import java.util.Collection;
import java.util.Date;

public class Product {

	private long oid;
	private String name;
	private String description;
	private float price;
	private Date date_in;
	private Date date_out;
	private boolean active;
	private Category category;
	private Collection<Image> images;
	private float rating;
	private Seller seller;

	public Product() {
		super();
	}

	public Product(long oid, String name, String description, float price,
			Date date_in, Date date_out, Category category,
			Collection<Image> images, Seller seller) {
		super();
		this.oid = oid;
		this.name = name;
		this.description = description;
		this.price = price;
		this.date_in = date_in;
		this.date_out = date_out;
		this.category = category;
		this.images = images;
		this.seller = seller;
	}
	
	/* Costruttore per l'inserimento senza immagini */
	public Product(long oid, String name, String description, float price,
			Category category, Seller seller, Date date_in, Date date_out) {
		super();
		this.oid = oid;
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.seller = seller;
		this.date_in = date_in;
		this.date_out = date_out;
	}	
	
	/* Costruttore per l'inserimento senza immagini e date */
	public Product(long oid, String name, String description, float price,
			Category category, Seller seller) {
		super();
		this.oid = oid;
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.seller = seller;
	}
	
	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Date getDate_in() {
		return date_in;
	}
	public void setDate_in(Date date_in) {
		this.date_in = date_in;
	}
	public Date getDate_out() {
		return date_out;
	}
	public void setDate_out(Date date_out) {
		this.date_out = date_out;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Collection<Image> getImages() {
		return images;
	}
	public void setImages(Collection<Image> images) {
		this.images = images;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
	
	public Seller getSeller() {
		return seller;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
