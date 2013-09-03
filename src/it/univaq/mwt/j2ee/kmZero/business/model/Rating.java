package it.univaq.mwt.j2ee.kmZero.business.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="ratings")
public class Rating implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="rating_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "ratings_seq")
	@SequenceGenerator(name = "ratings_seq", allocationSize=1)
	private long id;
	
	@Column(name="rating",scale=1)
	private float rating;
	
	@Column(name="absoluterating")
	private int absoluteRating;
	
	@Column(name="ratingvotes")
	private int ratingVotes;
	
	@OneToOne(mappedBy="rating",cascade=CascadeType.MERGE)
	@JsonBackReference("product-rating")
	private Product product;


	public Rating() {
		super();
	}

	public Rating(long id, float rating, int absoluteRating, int ratingVotes,
			Product product) {
		super();
		this.id = id;
		this.rating = rating;
		this.absoluteRating = absoluteRating;
		this.ratingVotes = ratingVotes;
		this.product = product;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public float getRating() {
		return rating;
	}


	public void setRating(float rating) {
		this.rating = rating;
	}


	public int getAbsoluteRating() {
		return absoluteRating;
	}


	public void setAbsoluteRating(int absoluteRating) {
		this.absoluteRating = absoluteRating;
	}


	public int getRatingVotes() {
		return ratingVotes;
	}


	public void setRatingVotes(int ratingVotes) {
		this.ratingVotes = ratingVotes;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}

	
}
