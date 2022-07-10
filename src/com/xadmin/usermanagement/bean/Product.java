package com.xadmin.usermanagement.bean;

import java.util.Date;


public class Product {
	private int productId;
	private String name;
	private String description;
	private double price;
	private int stock;
	public Product(String name, String description, double price, int stock) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
	}
	public Product(int productId, String name, String description, double price, int stock) {
		super();
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
