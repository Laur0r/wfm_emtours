package org.wwu.bpm.wfm.processEmtours.entity;

import java.util.ArrayList;
import java.util.Date;

public class Request {
	private int requestId;
	private ArrayList<String> interests;
	private int budget;
	private int numberAdults;
	private int numberChildren;
	private Date startDate;
	private Date endDate;
	private String paymentMethod;
	private Boolean isValid;
	
	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public ArrayList<String> getInterests() {
		return interests;
	}

	public void setInterests(ArrayList<String> interests) {
		this.interests = interests;
	}

	public int getNumberChildren() {
		return numberChildren;
	}

	public void setNumberChildren(int numberChildren) {
		this.numberChildren = numberChildren;
	}

	public int getNumberAdults() {
		return numberAdults;
	}

	public void setNumberAdults(int numberAdults) {
		this.numberAdults = numberAdults;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
}
