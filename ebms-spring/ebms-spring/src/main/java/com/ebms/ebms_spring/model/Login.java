package com.ebms.ebms_spring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "login", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public class Login {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String status;
	
	@Column(nullable = false)
	private String userType;
	
	@ManyToOne
	@JoinColumn(name = "customerId", referencedColumnName = "id", nullable = true)
	private Customer customer;
	
	

	public Login() {
		super();
	}



	public Login(String email, String password, String status, String userType, Customer customer) {
		super();
		this.email = email;
		this.password = password;
		this.status = status;
		this.userType = userType;
		this.customer = customer;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
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



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getUserType() {
		return userType;
	}



	public void setUserType(String userType) {
		this.userType = userType;
	}



	public Customer getCustomer() {
		return customer;
	}



	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	

}
