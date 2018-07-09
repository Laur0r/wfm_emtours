package org.camunda.bpm.sendMessages.Funspark;

import java.util.Date;

import org.camunda.bpm.entities.Customer;

/**
 * Information which are sended to Funspark on which they are 
 * choosing their activities
 */
public class FunsparkRecommendation {

	private int recommendationId;
	private String executionId;
	private Customer customer;
	private Date start;
	private Date end;
	private String destination;
	private int numberPeople;
	private String experienceType;
	private int numberActivities;
	
	public int getRecommendationId() {
		return recommendationId;
	}
	public void setRecommendationId(int recommendationId) {
		this.recommendationId = recommendationId;
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
}
