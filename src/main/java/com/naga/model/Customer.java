package com.naga.model;

public class Customer
{
	int cus_id;
	String name;
	
	public Customer(int cus_id, String name) {
		super();
		this.cus_id = cus_id;
		this.name = name;
	}
	public Customer() {
		super();
	}
	
	public int getCus_id() {
		return cus_id;
	}
	public void setCus_id(int cus_id) {
		this.cus_id = cus_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
