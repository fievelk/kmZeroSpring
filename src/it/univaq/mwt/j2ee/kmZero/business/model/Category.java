package it.univaq.mwt.j2ee.kmZero.business.model;

public class Category {
	
	private long oid;
	private String name;
	private long parent_oid;
	

	public Category() {
		super();
	}
	
	public Category(long oid) {
		super();
		this.oid = oid;
	}
	
	public Category(long oid, String name) {
		super();
		this.oid = oid;
		this.name = name;
	}

	public Category(long oid, String name, long parent_oid) {
		super();
		this.oid = oid;
		this.name = name;
		this.parent_oid = parent_oid;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public long getParent_oid() {
		return parent_oid;
	}

	public void setParent_oid(long parent_oid) {
		this.parent_oid = parent_oid;
	}
	
}
