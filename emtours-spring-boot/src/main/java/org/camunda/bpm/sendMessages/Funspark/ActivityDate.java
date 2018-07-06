package org.camunda.bpm.sendMessages.Funspark;

import java.util.Date;

import org.camunda.bpm.entities.Activity;

public class ActivityDate {

	private Activity activity;
	private Date bookingDate;
	
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
}
