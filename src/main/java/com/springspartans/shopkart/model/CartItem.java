package com.springspartans.shopkart.model;

import java.sql.Timestamp;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_item")
public class CartItem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int slno;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cust_id", nullable = false, foreignKey = @ForeignKey(name = "FK_Customer"))
    private Customer customer;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prod_id", nullable = false, foreignKey = @ForeignKey(name = "FK_Product"))
    private Product product;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp addedDate = Timestamp.from(Instant.now());
	
	public CartItem() {
	}

	public CartItem(int slno, Customer customer, Product product, int quantity, Timestamp orderDate) {
		this.slno = slno;
		this.customer = customer;
		this.product = product;
		this.quantity = quantity;
		this.addedDate = orderDate;
	}

	public int getSlno() {
		return slno;
	}

	public void setSlno(int slno) {
		this.slno = slno;
	}
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Timestamp getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Timestamp addedDate) {
		this.addedDate = addedDate;
	}

	@Override
	public String toString() {
		return "CartItem [slno=" + slno + ", customer=" + customer + ", product=" + product + ", quantity=" + quantity
				+ ", addedDate=" + addedDate + "]";
	}
		
}
