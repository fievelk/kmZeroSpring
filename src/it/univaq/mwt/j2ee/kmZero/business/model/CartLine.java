package it.univaq.mwt.j2ee.kmZero.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="cartlines")
public class CartLine {

	@Id
	@Column(name="cartline_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="lineTotal")
	private float lineTotal;
	
	@Column(name="comment")
	private String comment;
	
	@Column(name="rating")
	private int rating;
	
	@OneToOne
	@JoinColumn(name="product_fk")
	private Product product;
	
	public CartLine() {
		super();
	}
	
	public CartLine(int quantity, float lineTotal, String comment) {
		super();
		this.quantity = quantity;
		this.lineTotal = lineTotal;
		this.comment = comment;
	}

	public CartLine(int quantity, float lineTotal, String comment, int rating,
			Product product) {
		super();
		this.quantity = quantity;
		this.lineTotal = lineTotal;
		this.comment = comment;
		this.rating = rating;
		this.product = product;
	}

	public CartLine(long id, int quantity, float lineTotal, String comment,
			int rating, Product product) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.lineTotal = lineTotal;
		this.comment = comment;
		this.rating = rating;
		this.product = product;
	}

	public long getOid() {
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
	public float getLineTotal() {
		return lineTotal;
	}
	public void setLineTotal(float lineTotal) {
		this.lineTotal = lineTotal;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
