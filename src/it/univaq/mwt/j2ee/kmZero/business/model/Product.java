package it.univaq.mwt.j2ee.kmZero.business.model;

import it.univaq.mwt.j2ee.kmZero.common.Comparators;
import it.univaq.mwt.j2ee.kmZero.common.DateJsonSerializer;
import it.univaq.mwt.j2ee.kmZero.common.PriceJsonSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="products")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "products_seq")
	@SequenceGenerator(name = "products_seq", allocationSize=1)
	private long id;

	@Column(name="name", nullable=true)
	private String name;

	@Column(name="description",length = 2000)
	private String description;

	@Column(name="price")	
	private BigDecimal price;
	
	@Column(name="date_in")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateIn;

	@Column(name="date_out")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOut;

	@Column(name="active")
	private boolean active;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "categories_id")
	@JsonManagedReference("product-categories")
	private Category category;

	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "product_id")
	@OrderBy("position ASC")
	private Collection<Image> images = new ArrayList<Image>();


	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="rating_id")
	@JsonManagedReference("product-rating")
	private Rating rating;
	
	@OneToMany(mappedBy="product")
	@JsonManagedReference("product-feedback")
	private Collection<Feedback> feedbacks = new HashSet<Feedback>();
	
	@Column(name="stock")
	private int stock;
	
	@ManyToOne
	@JoinColumn(name="measures_id")
	private Measure measure;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="sellers_users_id")
	@JsonManagedReference("products-seller")
	private Seller seller;
	
	@Column(name="pos")
	private int position;

	public Product() {
		super();
	}

	public Product(long id, String name, String description, BigDecimal price,
			Date dateIn, Date dateOut, boolean active, Category category,
			List<Image> images, Rating rating, Collection<Feedback> feedbacks,
			int stock, Measure measure, Seller seller, int position) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.dateIn = dateIn;
		this.dateOut = dateOut;
		this.active = active;
		this.category = category;
		this.images = images;
		this.rating = rating;
		this.feedbacks = feedbacks;
		this.stock = stock;
		this.measure = measure;
		this.seller = seller;
		this.position = position;
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

	// L'annotazione @JsonSerialize serve per visualizzare correttamente le date in DataTables
	@JsonSerialize(using=DateJsonSerializer.class)
	public Date getDateIn() {
		return dateIn;
	}
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}

	@JsonSerialize(using=DateJsonSerializer.class)
	public Date getDateOut() {
		return dateOut;
	}
	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	@JsonSerialize(using=PriceJsonSerializer.class)
	public BigDecimal getPrice() {
		return price;
	}
	
	@JsonSerialize(using=PriceJsonSerializer.class)
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Collection<Image> getImages() {
		return images;
	}

	public void setImages(Collection<Image> images) {
		Comparators comparator = new Comparators();
		List<Image> imagesList = new ArrayList<Image>(images);
		Collections.sort(imagesList,comparator.getImagePositionComparator());
		this.images = imagesList;
	}

	public void addImage(Image image){
		List<Image> imagesList = new ArrayList<Image>(images);
		int position = 0;
		if(!imagesList.isEmpty()){
			Image lastImage = imagesList.get(images.size()-1);
			position = lastImage.getPosition();
		}
		image.setPosition(position+1);
		images.add(image);			
	}
	
	public void addImages(Collection<Image> images){
		for(Iterator<Image> i = images.iterator(); i.hasNext();){
			addImage(i.next());
		}
	}


	public Rating getRating() {
		return rating;
	}


	public void setRating(Rating rating) {
		this.rating = rating;
	}


	public Collection<Feedback> getFeedbacks() {
		return feedbacks;
	}


	public void setFeedbacks(Collection<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

}
