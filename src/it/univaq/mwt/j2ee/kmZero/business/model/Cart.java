package it.univaq.mwt.j2ee.kmZero.business.model;

import java.util.Collection;
import java.util.Date;

public class Cart {
	
	private long oid;
	private Date created; // Momento di immissione del primo prodotto nel carrello
	private Date dispatched; // Momento della consegna
	private Date paid;
	private Collection<CartLine> cartLines;
	private String address;
	private String name;
	private String surname;

	public Cart() {
		super();
	}

	
	public Cart(long oid, Date created, Date dispatched, Date paid,
			Collection<CartLine> cartLines, String address, String name,
			String surname) {
		super();
		this.oid = oid;
		this.created = created;
		this.dispatched = dispatched;
		this.paid = paid;
		this.cartLines = cartLines;
		this.address = address;
		this.name = name;
		this.surname = surname;
	}


	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getDispatched() {
		return dispatched;
	}
	public void setDispatched(Date dispatched) {
		this.dispatched = dispatched;
	}
	public Date getPaid() {
		return paid;
	}
	public void setPaid(Date paid) {
		this.paid = paid;
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
	

}
