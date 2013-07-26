package it.univaq.mwt.j2ee.kmZero.business.model;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Password implements java.io.Serializable{

	@Transient
	private String db_password;
	@Transient
	private String old_password;
	private String password;
	@Transient
	private String confirm_password;
	
	private static final long serialVersionUID = 1L;
	
	public Password(){
		
	}

	public Password(String db_password, String old_password, String password, String confirm_password) {
		super();
		this.db_password = db_password;
		this.old_password = old_password;
		this.password = password;
		this.confirm_password = confirm_password;
	}

	public String getDb_password() {
		return db_password;
	}

	public void setDb_password(String db_password) {
		this.db_password = db_password;
	}
	
	public String getOld_password() {
		return old_password;
	}

	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm_password() {
		return confirm_password;
	}

	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}

}
