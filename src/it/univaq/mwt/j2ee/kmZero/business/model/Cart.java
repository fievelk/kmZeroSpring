package it.univaq.mwt.j2ee.kmZero.business.model;

import it.univaq.mwt.j2ee.kmZero.common.DateJsonSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="carts")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Cart implements Serializable{

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "carts_seq")
	@SequenceGenerator(name = "carts_seq", allocationSize=1, initialValue=2)
	private long id;
	
	@Column(name="transaction_id")
	private String transactionId;
	
	@Column(name="created", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;// Momento di immissione del primo prodotto nel carrello
	
	@Column(name="dispatched", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dispatched; // Momento della consegna
	
	@Column(name="paid", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date paid;
	
	@Column(name="delivery_date", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveryDate;
	
	@Column(name="address", nullable=true)
	private String address;
	
	@Column(name="name", nullable=true)
	private String name;
	
	@Column(name="surname", nullable=true)
	private String surname;
	
	@OneToMany(mappedBy="cart",fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonManagedReference("cart-cartlines")
	private Collection<CartLine> cartLines = new ArrayList<CartLine>();
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	private static final long serialVersionUID = 1L;

	public Cart() {
		super();
	}

	
	public Cart(long id, String transactionId, Date created, Date dispatched, Date paid,
			Date deliveryDate,Collection<CartLine> cartLines, String address, String name,
			String surname) {
		super();
		this.id = id;
		this.transactionId = transactionId;
		this.created = created;
		this.dispatched = dispatched;
		this.paid = paid;
		this.deliveryDate = deliveryDate;
		this.cartLines = cartLines;
		this.address = address;
		this.name = name;
		this.surname = surname;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@JsonSerialize(using=DateJsonSerializer.class)
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	@JsonSerialize(using=DateJsonSerializer.class)
	public Date getDispatched() {
		return dispatched;
	}
	public void setDispatched(Date dispatched) {
		this.dispatched = dispatched;
	}
	
	@JsonSerialize(using=DateJsonSerializer.class)
	public Date getPaid() {
		return paid;
	}
	public void setPaid(Date paid) {
		this.paid = paid;
	}
	@JsonSerialize(using=DateJsonSerializer.class)
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public Collection<CartLine> getCartLines() {
		return cartLines;
	}

	public void setCartLines(Collection<CartLine> cartLines) {
		this.cartLines = cartLines;
	}
	
	public void addCartLines(CartLine cartLine) {
		this.cartLines.add(cartLine);
	}
	
	public void delCartLines(CartLine cartLine) { // DELete a cartLine
		this.cartLines.remove(cartLine);
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	
	
}
