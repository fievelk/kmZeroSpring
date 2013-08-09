package it.univaq.mwt.j2ee.kmZero.business.model;

import it.univaq.mwt.j2ee.kmZero.common.DateJsonSerializer;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="products")
public class Product {

	@Id
	@Column(name="product_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="description")
	private String description;

	@Column(name="price")	
	private float price;
	
	@Column(name="date_in")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_in;
	
	@Column(name="date_out")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_out;
	
	@Column(name="active")
	private boolean active;
	
	@ManyToOne
	@JoinColumn(name = "categories_id")
	private Category category;

	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "product_fk")
	@OrderBy("position ASC")
	private List<Image> images;
	
	@Column(name="rating")
	private float rating;

	@Column(name="stock")
	private int stock;
	
	@ManyToOne
	@JoinColumn(name="measures_id")
	private Measure measure;
	
	@ManyToOne
	@JoinColumn(name="sellers_users_id")
	private Seller seller;

	public Product() {
		super();
	}

	public Product(long id, String name, String description, float price,
			Date date_in, Date date_out, Category category,
			List<Image> images, Seller seller) {
		super();
		this.id = id;
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
	public Product(long id, String name, String description, float price,
			Category category, Seller seller, Date date_in, Date date_out) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.seller = seller;
		this.date_in = date_in;
		this.date_out = date_out;
	}	
	
	/* Costruttore per l'inserimento senza immagini e date */
	public Product(long id, String name, String description, float price,
			Category category, Seller seller) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.seller = seller;
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
	
	// L'annotazione @JsonSerialize serve per visualizzare correttamente le date in DataTables
	@JsonSerialize(using=DateJsonSerializer.class)
	public Date getDate_in() {
		return date_in;
	}
	public void setDate_in(Date date_in) {
		this.date_in = date_in;
	}
	
	@JsonSerialize(using=DateJsonSerializer.class)
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
	
/*	public Collection<Image> getImages() {
		return images;
	}
	public void setImages(Collection<Image> images) {
		this.images = images;
	}*/

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public Seller getSeller() {
		return seller;
	}
	
	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Measure getMeasure() {
		return measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}

	
	
}
