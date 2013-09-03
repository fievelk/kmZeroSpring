package it.univaq.mwt.j2ee.kmZero.business.model;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Password implements java.io.Serializable{

	@Transient
	private String dbPassword;
	@Transient
	private String oldPassword;
	private String password;
	@Transient
	private String confirmPassword;
	
	private static final long serialVersionUID = 1L;
	
	public Password(){
		
	}

	public Password(String dbPassword, String oldPassword, String password, String confirmPassword) {
		super();
		this.dbPassword = dbPassword;
		this.oldPassword = oldPassword;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
