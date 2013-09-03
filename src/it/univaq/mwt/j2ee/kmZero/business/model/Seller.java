package it.univaq.mwt.j2ee.kmZero.business.model;

import it.univaq.mwt.j2ee.kmZero.common.Comparators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
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

@Entity
@Table(name="sellers")
@DiscriminatorValue(value="S")
@PrimaryKeyJoinColumn(name="id")
public class Seller extends User implements Serializable{

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
	
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "seller_id")
	@OrderBy("position ASC")
	private Collection<Image> images = new ArrayList<Image>();
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="seller",orphanRemoval=true)
	@JsonBackReference("contents-seller")
	private Collection<SellerContent> contents = new ArrayList<SellerContent>();

	@OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL,mappedBy="seller")
	@JsonBackReference("products-seller")
	@OrderBy("position ASC")
	private Collection<Product> products = new ArrayList<Product>();

	private static final long serialVersionUID = 1L;
	
	public Seller() {
		super();
	}

	
	// Costruttore per il Test
	public Seller(String name, String surname, String email, Password password, Date created, 
			Date dateOfBirth, String address, String p_iva, String cod_fisc, String company,String url, String phone, boolean enable) {
		super(name, surname, email, password, created, dateOfBirth, address);
		this.p_iva = p_iva;
		this.cod_fisc = cod_fisc;
		this.company = company;
		this.url = url;
		this.phone = phone;
		this.enable = enable;
	}
	
	// Costruttore per il Test
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

	public Collection<SellerContent> getContents() {
		return contents;
	}

	public void setContents(Collection<SellerContent> contents) {
		this.contents = contents;
	}
	
	public void addContent(SellerContent content){
		this.contents.add(content);
	}

	public Collection<Image> getImages() {
		return images;
	}

	public void setImages(Collection<Image> images) {
		Comparators comparator = new Comparators();
		List<Image> imagesList = new ArrayList<Image>(images);
		Collections.sort(imagesList,comparator.getImagePositionComparator());
		this.images = imagesList;
	}

	public void addImage(Image image){
		List<Image> imagesList = new ArrayList<Image>(images);
		int position = 0;
		if(!imagesList.isEmpty()){
			Image lastImage = imagesList.get(this.images.size()-1);
			position = lastImage.getPosition();
		}
		image.setPosition(position+1);
		images.add(image);	
	}
	
	public void addImages(Collection<Image> images){
		for(Iterator<Image> i = images.iterator(); i.hasNext();){
			this.addImage(i.next());
		}
	}

	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		if(products != null){
			Comparators comparator = new Comparators();
			List<Product> productsList = new ArrayList<Product>(products);
			Collections.sort(productsList,comparator.getProductPositionComparator());
			this.products = productsList;
		}else this.products = null;
	}
	
	public void addProduct(Product product){
		
		if(!products.contains(product)){
			List<Product> productsList = new ArrayList<Product>(products);
			int position = 0;
			if(!productsList.isEmpty()){
				Product lastProduct = productsList.get(products.size()-1);
				position = lastProduct.getPosition();
			}
			product.setPosition(position+1);
			products.add(product);	
		}	
	}
	
	//utilizzato quando viene modificato il seller di un product
	public void deleteProduct(Product product){
		if(products.contains(product)){
			product.setSeller(null);
			products.remove(product);
		}
	}
	
	//utilizzato alla cancellazione del seller (per rimuovere tutti i product associati -> lato owner)
	public void deleteAllProducts(){
		for(Iterator<Product> i = products.iterator();i.hasNext();){
			i.next().setSeller(null);
		}
	}
}
