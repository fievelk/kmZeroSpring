package it.univaq.mwt.j2ee.kmZero.business.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="categories")
public class Category implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "categories_seq")
	@Column(name="id")
	@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
	@SequenceGenerator(name = "categories_seq", allocationSize=1)
	private long id;
	
	private String name;
	
	@OneToOne
	@JoinColumn(name = "parent_id")
	@JsonBackReference
	private Category parent;

	@OneToMany(mappedBy="parent",cascade=CascadeType.PERSIST)
	@JsonManagedReference
	private List<Category> childs = new ArrayList<Category>();
	
	@OneToMany(mappedBy="category")
	@JsonBackReference("product-categories")
	private List<Product> products = new ArrayList<Product>();
	
	private static final long serialVersionUID = 1L;

	public Category() {
		super();
	}
	
	public Category(long id) {
		super();
		this.id = id;
	}
	
	public Category(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Category(long id, String name, Category parent) {
		super();
		this.id = id;
		this.name = name;
		this.setParent(parent);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Category> getChilds() {
		return childs;
	}

	public void setChilds(List<Category> childs) {
		this.childs = childs;
	}
	
	public void addChild(Category child){
		this.childs.add(child);
	}
	
	public void removeChild(Category child){
		this.childs.remove(child);
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
