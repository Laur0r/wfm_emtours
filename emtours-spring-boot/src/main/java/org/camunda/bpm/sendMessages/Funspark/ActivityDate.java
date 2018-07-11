package org.camunda.bpm.sendMessages.Funspark;

import java.util.Date;

import org.camunda.bpm.entities.Activity;

/**
 * Class which maps a date to each activity
 */
public class ActivityDate {

	private Integer id;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
