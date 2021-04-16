package com.olx.transaction.service.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private User owner;
	
	@ManyToOne
	private User buyer;
	
	@OneToOne
	private Product product;
	
	private Long createdAt;
	
	private Boolean closed;
	
	public Transaction() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}
}
