package org.camunda.bpm.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Recommendation class which is saved in the database.
 * One recommendation always belongs to one customer request
 */
@Entity
public class Recommendation {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer recommendationId;
	
	@ManyToOne
	private CustomerRequest request;
	
	private Date arrival;
	private Date departure;
	private String destination;
	private String hotel;
	private String flight;
	private int numberPeople;
	
	@OneToMany(mappedBy="recommendation")
	private Collection<Activity> activities;
	
	private double cost;
	private double activityCost;
	
	public Recommendation() {
		
	}
	public Date getArrival() {
		return arrival;
	}
	public void setArrival(Date arrival) {
		this.arrival = arrival;
	}
	public Date getDeparture() {
		return departure;
	}
	public void setDeparture(Date departure) {
		this.departure = departure;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getNumberPeople() {
		return numberPeople;
	}
	public void setNumberPeople(int numberPeople) {
		this.numberPeople = numberPeople;
	}
	public Collection<Activity> getActivities() {
		return activities;
	}
	public void setActivities(Collection<Activity> activities) {
		this.activities = activities;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getHotel() {
		return hotel;
	}
	public void setHotel(String hotel) {
		this.hotel = hotel;
	}
	public String getFlight() {
		return flight;
	}
	public void setFlight(String flight) {
		this.flight = flight;
	}
	public void setRequest(CustomerRequest request) {
		this.request = request;
	}
	public CustomerRequest getRequest() {
		return request;
	}
	public Integer getId() {
		return recommendationId;
	}
	public double getActivityCost() {
		return activityCost;
	}
	public void setActivityCost(double activityCost) {
		this.activityCost = activityCost;
	}

}
