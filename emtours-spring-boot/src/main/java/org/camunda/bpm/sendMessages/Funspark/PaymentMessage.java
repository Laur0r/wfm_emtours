package org.camunda.bpm.sendMessages.Funspark;

/**
 * Payment message which is sent to Funspark
 */
public class PaymentMessage {
	
	private String executionId;

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

}
