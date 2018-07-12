package org.camunda.bpm.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Activity class which is saved in the database. 
 * One activity always belongs to one recommendation.
 */
@Entity
public class Activity {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer activityId;
	
	@ManyToOne
	@JoinColumn(name="recommendationId")
	private Recommendation recommendation;
	private String name;
	private String location;
	private float price;
	private String description;
	private Date date;
	private String activityType;
	private String localproviderName;
	private String localproviderPhoneNumber;
	private Integer localproviderId;
	private Integer id;
	
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
	public String getLocalproviderName() {
		return localproviderName;
	}
	public void setLocalproviderName(String provider) {
		this.localproviderName = provider;
	}
	
	public Recommendation getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(Recommendation recommendation) {
		this.recommendation = recommendation;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getLocalproviderPhoneNumber() {
		return localproviderPhoneNumber;
	}
	public void setLocalproviderPhoneNumber(String localproviderPhoneNumber) {
		this.localproviderPhoneNumber = localproviderPhoneNumber;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer id) {
		this.activityId = id;
	}
	public Integer getLocalproviderId() {
		return localproviderId;
	}
	public void setLocalproviderId(Integer localproviderId) {
		this.localproviderId = localproviderId;
	}
}
