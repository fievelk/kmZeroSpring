package it.univaq.mwt.j2ee.kmZero.business.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="roles")
public class Role implements java.io.Serializable {
	

	@Id 
	@Column(name="role_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	

	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@ManyToMany(mappedBy="roles")
	@JsonBackReference
	private Set<User> users = new HashSet<User>();
	
	private static final long serialVersionUID = 1L;
	
	public Role() {
		super();
	}
	
	public Role(int id){
		this.id = id;
	}

	public Role(int id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}



}