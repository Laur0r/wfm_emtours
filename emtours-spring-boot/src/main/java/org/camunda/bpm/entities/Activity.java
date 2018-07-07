package org.camunda.bpm.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Activity {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name="recommendationId")
	private Recommendation recommendation;
	private String name;
	private String location;
	private float price;
	private String description;
	private Date date;
	private String provider;
	
public Activity() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public Recommendation getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(Recommendation recommendation) {
		this.recommendation = recommendation;
	}
}
