package it.univaq.mwt.j2ee.kmZero.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ResponseCarts<CartLine> implements Serializable{
	
	private long id;
	private int exist;
	private Collection<CartLine> cartlines = new ArrayList<CartLine>();
	
	public ResponseCarts(long id, int exist, Collection<CartLine> cartlines) {
		super();
		this.id = id;
		this.exist = exist;
		this.cartlines = cartlines;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getExist() {
		return exist;
	}

	public void setExist(int exist) {
		this.exist = exist;
	}

	public Collection<CartLine> getCartlines() {
		return cartlines;
	}

	public void setCartlines(Collection<CartLine> cartlines) {
		this.cartlines = cartlines;
	}

}
