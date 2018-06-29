package org.camunda.bpm.sendMessages.Funspark;

public class RequestBookingNotification {
	private int recommendationId;
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

}
