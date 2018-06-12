package org.wwu.bpm.wfm.processEmtours.entity;

import java.util.ArrayList;
import java.util.Date;

public class Recommendation {
	
	private String location;
	private Date startDate;
	private Date endDate;
	private String accommodation;
	private String transport;
	private double cost;
	private int numberCust;
	private ArrayList<Activity> activities;
	private Boolean isValid;
	
	public Recommendation() {
		this.setIsValid(false);
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAccommodation() {
		return accommodation;
	}
	public void setAccommodation(String accommodation) {
		this.accommodation = accommodation;
	}
	public String getFlight() {
		return transport;
	}
	public void setFlight(String flight) {
		this.transport = flight;
	}
	public int getNumberCust() {
		return numberCust;
	}
	public void setNumberCust(int numberCust) {
		this.numberCust = numberCust;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public ArrayList<Activity> getActivities() {
		return activities;
	}
	public void setActivities(ArrayList<Activity> activities) {
		this.activities = activities;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
}
