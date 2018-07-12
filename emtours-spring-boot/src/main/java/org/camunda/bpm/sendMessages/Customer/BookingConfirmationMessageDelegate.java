package org.camunda.bpm.sendMessages.Customer;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.mail.EMailerService;
import org.springframework.stereotype.Component;

/**
 * Send the customer a mail to confirm the booking 
 */
@Component
public class BookingConfirmationMessageDelegate implements JavaDelegate {

	private EMailerService eMailerService;
	
	public BookingConfirmationMessageDelegate(EMailerService eMailerService) {
		this.eMailerService = eMailerService;
	}
	
	public void execute(DelegateExecution execution) throws Exception {
		
		try {
			
			Integer customerId = (Integer) execution.getVariable("customerId");
			Integer recommendationId = (Integer) execution.getVariable("recommendationId");
			
			String mailSubject = "Booking confirmation";
			
			eMailerService.sendComplexMessage(customerId, recommendationId, mailSubject, execution.getId());
		}catch (Exception ex) {
			System.out.println("Error in sending email (BookingConfirmation): "+ex);
		}		
	} 
}