package it.univaq.mwt.j2ee.kmZero.business.model;

import it.univaq.mwt.j2ee.kmZero.common.PriceJsonSerializer;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="cartlines")
public class CartLine implements Serializable{

	@Id
	@Column(name="cartline_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="lineTotal")
	private BigDecimal lineTotal;
	
	@Column(name="review", nullable=true)
	private String review;
	
	@Column(name="rating", nullable=true)
	private int rating;
	
	@OneToOne
	@JoinColumn(name="product_fk")
	private Product product;
	
	private static final long serialVersionUID = 1L;
	
	public CartLine() {
		super();
	}
	
	public CartLine(int quantity, BigDecimal lineTotal, String review) {
		super();
		this.quantity = quantity;
		this.lineTotal = lineTotal;
		this.review = review;
	}

	public CartLine(int quantity, BigDecimal lineTotal, String review, int rating,
			Product product) {
		super();
		this.quantity = quantity;
		this.lineTotal = lineTotal;
		this.review = review;
		this.rating = rating;
		this.product = product;
	}

	public CartLine(long id, int quantity, BigDecimal lineTotal, String review,
			int rating, Product product) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.lineTotal = lineTotal;
		this.review = review;
		this.rating = rating;
		this.product = product;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@JsonSerialize(using=PriceJsonSerializer.class)
	public BigDecimal getLineTotal() {
		return lineTotal;
	}
	@JsonSerialize(using=PriceJsonSerializer.class)
	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
