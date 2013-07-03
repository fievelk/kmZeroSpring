package it.univaq.mwt.j2ee.kmZero.business.model;

import java.util.Calendar;

public class User {

	private long oid;
	private String name;
	private String surname;
	private String email;
	private String password;
	private Calendar created;
	private Calendar date_of_birth;
	private Calendar last_access;
	private String address;
	

	public User() {
		
	}

	// Costruttore dello User con solo l'id
	public User(long oid){
		this.oid = oid;
	}
	
	public User(String name, String surname, String email, String password,
			Calendar created, Calendar date_of_birth, String address) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.created = created;
		this.date_of_birth = date_of_birth;
		this.address = address;
	}

	public User(long oid, String name, String surname, String email, String password,
			Calendar created, Calendar date_of_birth, String address) {
		super();
		this.oid = oid;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.created = created;
		this.date_of_birth = date_of_birth;
		this.address = address;
	}
	
	/* Costruttore per visualizzare la lista degli utenti senza il campo password */
	public User(long oid, String name, String surname, String email,
			Calendar created, Calendar date_of_birth, Calendar last_access, String address) {
		super();
		this.oid = oid;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.created = created;
		this.date_of_birth = date_of_birth;
		this.last_access = last_access;
		this.address = address;
	}
	
	/* Costruttore per aggiornare il profilo utente */
	public User(long oid, String name, String surname, String email, Calendar date_of_birth, String address) {
		super();
		this.oid = oid;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.date_of_birth = date_of_birth;
		this.address = address;
	}
	
	/* Costruttore che serve al Seller quando verrà visualizzata la lista tramite Datatables */
	public User(long oid, String name, String surname){
		this.oid = oid;
		this.name = name;
		this.surname = surname;
	}
	
	// Costruttore per il cambio password
	public User(long oid, String password){
		this.oid = oid;
		this.password = password;
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Calendar getCreated() {
		return created;
	}
	public void setCreated(Calendar created) {
		this.created = created;
	}
	public Calendar getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(Calendar date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	public Calendar getLast_access() {
		return last_access;
	}
	public void setLast_access(Calendar last_access) {
		this.last_access = last_access;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
