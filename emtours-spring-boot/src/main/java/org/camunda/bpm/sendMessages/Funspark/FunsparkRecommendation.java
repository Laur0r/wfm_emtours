package org.camunda.bpm.sendMessages.Funspark;

import java.util.Date;

public class FunsparkRecommendation {

	private int customerId;
	private Date start;
	private Date end;
	private String destination;
	private int numberPeople;
	private String experienceType;
	private int numberActivities;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
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
	public String getExperienceType() {
		return experienceType;
	}
	public void setExperienceType(String experienceType) {
		this.experienceType = experienceType;
	}
	public int getNumberActivities() {
		return numberActivities;
	}
	public void setNumberActivities(int numberActivities) {
		this.numberActivities = numberActivities;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
}
