package it.univaq.mwt.j2ee.kmZero.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResponseCarts<CartLine> implements Serializable{
	
	private long id;
	private Collection<CartLine> cartlines = new ArrayList<CartLine>();

	public ResponseCarts(long id, Collection<CartLine> cartlines) {
		super();
		this.id = id;
		this.cartlines = cartlines;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public Collection<CartLine> getCartlines() {
		return cartlines;
	}

	public void setCartlines(Collection<CartLine> cartlines) {
		this.cartlines = cartlines;
	}

}
