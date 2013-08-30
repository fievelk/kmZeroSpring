package it.univaq.mwt.j2ee.kmZero.business.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="feedbacks")
public class Feedback implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="feedback_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "feedbacks_seq")
	@SequenceGenerator(name = "feedbacks_seq")
	private long id;
	
	@Column(name="feedback_content",length = 1000)
	private String feedbackContent;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JsonBackReference("product-feedback")
	@JoinColumn(name="product_id")
	private Product product;
	
	@OneToOne(mappedBy="feedback",cascade=CascadeType.MERGE)
	@JsonBackReference("cartLine-feedback")
	private CartLine cartLine;

	public Feedback() {
		super();
	}


	public Feedback(long id, String feedbackContent, Product product,
			CartLine cartLine) {
		super();
		this.id = id;
		this.feedbackContent = feedbackContent;
		this.product = product;
		this.cartLine = cartLine;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFeedbackContent() {
		return feedbackContent;
	}

	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}


	public CartLine getCartLine() {
		return cartLine;
	}


	public void setCartLine(CartLine cartLine) {
		this.cartLine = cartLine;
	}
	
	
}
