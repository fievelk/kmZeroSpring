package it.univaq.mwt.j2ee.kmZero.business.model;

public class Image {
	
	private long oid;
	private String name;
	private String path;
	private String mimetype;
	
	
	public Image() {
		super();
	}

	public Image(long oid, String name, String path, String mimetype) {
		super();
		this.oid = oid;
		this.name = name;
		this.path = path;
		this.mimetype = mimetype;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	
	

}
