package it.univaq.mwt.j2ee.kmZero.business;

public class RequestGridProducts extends RequestGrid{

	private Long categoryId = null;

	public RequestGridProducts() {
		super();
	}

	public RequestGridProducts(Long categoryId) {
		super();
		this.setCategoryId(categoryId);
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	
}
