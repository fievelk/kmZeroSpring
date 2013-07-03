package it.univaq.mwt.j2ee.kmZero.business.model;

public class CartLine {

	private long oid;
	private Product product;
	private int quantity;
	private float lineTotal;
	private String comment;
	private int rating;
	
	
	public CartLine() {
		super();
	}

	public CartLine(long oid, Product product, int quantity, float lineTotal,
			String comment, int rating) {
		super();
		this.oid = oid;
		this.product = product;
		this.lineTotal = lineTotal;
		this.comment = null;
	}
	
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
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
	
	
	
}
