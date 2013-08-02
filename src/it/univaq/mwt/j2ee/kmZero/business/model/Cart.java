package it.univaq.mwt.j2ee.kmZero.business.model;

import it.univaq.mwt.j2ee.kmZero.common.DateJsonSerializer;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="carts")
public class Cart {
	
	@Id
	@Column(name="cart_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="created", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;// Momento di immissione del primo prodotto nel carrello
	
	@Column(name="dispatched", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dispatched; // Momento della consegna
	
	@Column(name="paid", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date paid;
	
	@Column(name="address", nullable=true)
	private String address;
	
	@Column(name="name", nullable=true)
	private String name;
	
	@Column(name="surname", nullable=true)
	private String surname;
	
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "cartline_fk")
	private Collection<CartLine> cartLines;

	public Cart() {
		super();
	}

	
	public Cart(long id, Date created, Date dispatched, Date paid,
			Collection<CartLine> cartLines, String address, String name,
			String surname) {
		super();
		this.id = id;
		this.created = created;
		this.dispatched = dispatched;
		this.paid = paid;
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

}