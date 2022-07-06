package com.xadmin.usermanagement.bean;

public class User {
	private int id;
	private String name;
	private String email;
	private String password;
	private String billingAddress;
	private int admin;
	
	public User(int id, String name, String email, String password, String billingAddress, int admin) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.billingAddress = billingAddress;
		this.admin = admin;
	}

	public User(int id, String name, String email, String password, String billingAddress) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.billingAddress = billingAddress;
	}

	public User(String name, String email, String password, String billingAddress) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.billingAddress = billingAddress;
	}
	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
