package org.camunda.bpm.sendMessages.Funspark;

/**
 * Class which contains the notification information for funspark 
 * which is then converted to json
 */
public class FunsparkNotification {
	
	private int recommendationId;
	private String executionId;
	private boolean bookingNotification;
	private boolean cancellationNotification;
	private boolean refinementNotification;
	
	
	public boolean isRefinementNotification() {
		return refinementNotification;
	}
	public void setRefinementNotification(boolean refinementNotification) {
		this.refinementNotification = refinementNotification;
	}
	public boolean isCancellationNotification() {
		return cancellationNotification;
	}
	public void setCancellationNotification(boolean cancellationNotification) {
		this.cancellationNotification = cancellationNotification;
	}
	public boolean isBookingNotification() {
		return bookingNotification;
	}
	public void setBookingNotification(boolean bookingNotification) {
		this.bookingNotification = bookingNotification;
	}
	public int getRecommendationId() {
		return recommendationId;
	}
	public void setRecommendationId(int recommendationId) {
		this.recommendationId = recommendationId;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

}
