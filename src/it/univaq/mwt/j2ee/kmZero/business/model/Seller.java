package it.univaq.mwt.j2ee.kmZero.business.model;

import it.univaq.mwt.j2ee.kmZero.common.Comparators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="sellers")
@DiscriminatorValue(value="S")
@PrimaryKeyJoinColumn(name="user_id")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
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
	@Column(name="enable")
	private boolean enable;
	
	@OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "seller_fk")
	@OrderBy("position ASC")
	@JsonIgnore
	private List<Image> images;
	
	@OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL,mappedBy="seller",orphanRemoval = true)
	private Collection<SellerContent> contents = new ArrayList<SellerContent>();

	private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="seller",orphanRemoval=true)
	@JsonBackReference("products-seller")
	private List<Product> products;

	public Seller() {
		super();
	}

	// Construttore con solo l'id
	public Seller(long user_id){
		super (user_id);
	}

	public Seller(String name, String surname, String email, Password password, Date created, 
			Date date_of_birth, String address, String p_iva, String cod_fisc, String company,String url, String phone, boolean enable) {
		super(name, surname, email, password, created, date_of_birth, address);
		this.p_iva = p_iva;
		this.cod_fisc = cod_fisc;
		this.company = company;
		this.url = url;
		this.phone = phone;
		this.enable = false;
	}

	// Costruttore da utilizzare quando il venditore si registra da zero.
	public Seller(long id, String name, String surname, String email, Password password, Date created, 
			Date date_of_birth, String address, String p_iva, String cod_fisc, String company,String url, String phone, boolean enable) {
		super(id, name, surname, email, password, created, date_of_birth, address);
		this.p_iva = p_iva;
		this.cod_fisc = cod_fisc;
		this.company = company;
		this.url = url;
		this.phone = phone;
		this.enable = false;
	}

	// Costruttore da utilizzare quando un utente fa l'upgrade a venditore
	public Seller(String p_iva, String cod_fisc, String company, String url, String phone) {
		this.p_iva = p_iva;
		this.cod_fisc = cod_fisc;
		this.company = company;
		this.url = url;
		this.phone = phone;
		this.enable = false;
	}

	// Costruttore da utilizzare per visualizzare un venditore all'interno di una Datatables (Admin)
	public Seller(long id, String name, String surname, String p_iva, String company, String phone){
		super(id, name, surname);
		this.p_iva = p_iva;
		this.company = company;
		this.phone = phone;
	}

	// Costruttore da utilizzare al momento della cancellazione e delle modifica di un venditore
	public Seller(long id, String name, String surname, String email, Date date_of_birth,
			String address, String p_iva, String cod_fisc, String company, String url, String phone){
		super(id, name, surname, email, date_of_birth, address);
		this.p_iva = p_iva;
		this.cod_fisc = cod_fisc;
		this.company = company;
		this.url = url;
		this.phone = phone;
	}

	// Costruttore da utilizzare al momento della modifica di un venditore da parte di quest'ultimo
		public Seller(long id, String name, String surname, String email, Date date_of_birth,
				String address, String url, String phone){
			super(id, name, surname, email, date_of_birth, address);
			this.url = url;
			this.phone = phone;
		}

	// Costruttore con Id User e nome della Company utilizzato per la visualizzazione dei prodotti di un venditore e
	// per far visualizzare ad un utente la lista dei venditori.
	 public Seller(long id, String company) {
		super(id);
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

	public boolean getEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public List<Image> getImages() {
		Comparators c = new Comparators();
		Collections.sort(images,c.getImagePositionComparator());
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public Collection<SellerContent> getContents() {
		return contents;
	}

	public void setContents(Collection<SellerContent> contents) {
		this.contents = contents;
	}

	public List<Product> getProducts() {
		Comparators c = new Comparators();
		Collections.sort(products,c.getProductPositionComparator());
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void setProduct(Product product){
		this.products.add(product);
	}
	
	public void deleteProduct(Product product){
		this.products.remove(product);
	}

}
