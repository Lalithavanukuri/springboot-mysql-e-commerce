package com.springspartans.shopkart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Admin")
public class Admin
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false, length=50)
	private String username;
	
	@Column(nullable=false, length=72)
	private String password;

	@Column(nullable=false, length=50, unique=true)
	private String email;
	
	@Column(nullable=false, length=255)
	private String security_key;
	
	public Admin() {
		
	}
	public Admin(int id, String username, String password, String email, String security_key) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.security_key = security_key;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSecurity_key() {
		return security_key;
	}

	public void setSecurity_key(String security_key) {
		this.security_key = security_key;
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", security_key=" + security_key + "]";
	}
	
	
}