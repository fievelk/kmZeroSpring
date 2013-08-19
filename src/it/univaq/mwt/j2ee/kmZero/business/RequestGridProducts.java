package it.univaq.mwt.j2ee.kmZero.business;

public class RequestGridProducts extends RequestGrid{

	private Long categoryId = null;
	private Long sellerId = null;

	public RequestGridProducts() {
		super();
	}

	public RequestGridProducts(Long categoryId,Long sellerId) {
		super();
		this.setCategoryId(categoryId);
		this.setCategoryId(sellerId);
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	
	
}
