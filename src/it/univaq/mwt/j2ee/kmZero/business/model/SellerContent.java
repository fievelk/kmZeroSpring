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

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="sellercontents")
public class SellerContent implements Serializable{

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seller_contents_seq")
	@SequenceGenerator(name = "seller_contents_seq", allocationSize=1)
	private long id;
	@Column(name="title")
	private String title;
	@Column(name="description",length = 2000)
	private String description;
	
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "image_id")
	private Image image;
	
	@ManyToOne
	@JoinColumn(name="sellers_users_id")
	@JsonManagedReference("contents-seller")
	private Seller seller;
	
	private static final long serialVersionUID = 1L;
	

	public SellerContent() {
		super();
	}


	public SellerContent(long id, String title, String description) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

}
