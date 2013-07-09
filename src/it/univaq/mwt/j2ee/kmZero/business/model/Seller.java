package it.univaq.mwt.j2ee.kmZero.business.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="sellers")
@DiscriminatorValue(value="S")
@PrimaryKeyJoinColumn(name="user_id")
public class Seller extends User {

	@Column(name="p_iva")
	private String p_iva;
	@Column(name="cod_fisc")
	private String cod_fisc;
	@Column(name="company")
	private String company;
	@Column(name="url", nullable=true)
	private String url;
	@Column(name="phone")
	private String phone;
	@Column(name="enable", length=1)
	private int enable;
	
	private static final long serialVersionUID = 1L;
	
	public Seller() {
		super();
	}
	
	// Construttore con solo l'id
	public Seller(long user_oid){
		super (user_oid);
	}

	// Costruttore da utilizzare quando il venditore si registra da zero.
	public Seller(long oid, String name, String surname, String email, String password, Date created, Date date_of_birth,
			String address, String p_iva, String cod_fisc, String company, String url, String phone, int enable) {
		super(oid, name, surname, email, password, created, date_of_birth, address, null);
		this.p_iva = p_iva;
		this.cod_fisc = cod_fisc;
		this.company = company;
		this.url = url;
		this.phone = phone;
		this.enable = 0;
	}

	// Costruttore da utilizzare quando un utente fa l'upgrade a venditore
	public Seller(String p_iva, String cod_fisc, String company, String url, String phone) {
		this.p_iva = p_iva;
		this.cod_fisc = cod_fisc;
		this.company = company;
		this.url = url;
		this.phone = phone;
		this.enable = 0;
	}
	
	// Costruttore da utilizzare per visualizzare un venditore all'interno di una Datatables (Admin)
	public Seller(long oid, String name, String surname, String p_iva, String company, String phone){
		super(oid, name, surname);
		this.p_iva = p_iva;
		this.company = company;
		this.phone = phone;
	}
	
	// Costruttore da utilizzare al momento della cancellazione e delle modifica di un venditore
	public Seller(long oid, String name, String surname, String email, Date date_of_birth,
			String address, String p_iva, String cod_fisc, String company, String url, String phone){
		super(oid, name, surname, email, date_of_birth, address, null);
		this.p_iva = p_iva;
		this.cod_fisc = cod_fisc;
		this.company = company;
		this.url = url;
		this.phone = phone;
	}
	
	// Costruttore da utilizzare al momento della modifica di un venditore da parte di quest'ultimo
		public Seller(long oid, String name, String surname, String email, Date date_of_birth,
				String address, String url, String phone){
			super(oid, name, surname, email, date_of_birth, address, null);
			this.url = url;
			this.phone = phone;
		}
	
	// Costruttore con Id User e nome della Company utilizzato per la visualizzazione dei prodotti di un venditore e
	// per far visualizzare ad un utente la lista dei venditori.
	 public Seller(long oid, String company) {
		super(oid);
		this.company = company;
	 }

	public String getP_iva() {
		return p_iva;
	}

	public void setP_iva(String p_iva) {
		this.p_iva = p_iva;
	}

	public String getCod_fisc() {
		return cod_fisc;
	}

	public void setCod_fisc(String cod_fisc) {
		this.cod_fisc = cod_fisc;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}
	
}
