package com.olx.transaction.service.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {
	
	@Id
	private Long id;
	
	private String name;
	
	private Double price;
	
	private Boolean arhived;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getArhived() {
		return arhived;
	}

	public void setArhived(Boolean arhived) {
		this.arhived = arhived;
	}
}