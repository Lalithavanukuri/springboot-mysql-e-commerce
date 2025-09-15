package com.springspartans.shopkart.model;

import java.sql.Timestamp;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;

@Entity
@Table(name="Orders")
public class Order
{
	public enum OrderStatus 
	{
	    Pending, Shipped, Delivered, Cancelled
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="cust_id", nullable=false, foreignKey= @ForeignKey(name="FR_Customer"))
	private Customer customer;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="prod_id", nullable=false, foreignKey=@ForeignKey(name="FR_Product"))
	private Product product;
	
	@Column(nullable=false)
	private int quantity;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp order_date=Timestamp.from(Instant.now());
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT NULL")
	private Timestamp delivered_date;
	
	@Column(columnDefinition = "ENUM('Pending', 'Shipped', 'Delivered', 'Cancelled') DEFAULT 'Pending'")
	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.Pending;
	
	@Column(nullable=false)
	private double total_amount;

	public Order() {
	}

	public Order(int id, Customer customer, Product product, int quantity, Timestamp order_date,
			Timestamp delivered_date, OrderStatus status, double total_amount) {
		this.id = id;
		this.customer = customer;
		this.product = product;
		this.quantity = quantity;
		this.order_date = order_date;
		this.delivered_date = delivered_date;
		this.status = status;
		this.total_amount = total_amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Timestamp getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Timestamp order_date) {
		this.order_date = order_date;
	}

	public Timestamp getDelivered_date() {
		return delivered_date;
	}

	public void setDelivered_date(Timestamp delivered_date) {
		this.delivered_date = delivered_date;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer + ", product=" + product + ", quantity=" + quantity
				+ ", order_date=" + order_date + ", delivered_date=" + delivered_date + ", status=" + status
				+ ", total_amount=" + total_amount + "]";
	}
	
}
