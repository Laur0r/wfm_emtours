package org.camunda.bpm.sendMessages.Customer;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.mail.EMailerService;
import org.springframework.stereotype.Component;

/**
 * Send the customer a mail to inform them that the activities are no longer available
 */
@Component
public class UnavailabilityMessageDelegate implements JavaDelegate {

	private EMailerService eMailerService;
	
	public UnavailabilityMessageDelegate(EMailerService eMailerService) {
		this.eMailerService = eMailerService;
	}

	public void execute(DelegateExecution execution) throws Exception {
		try {
			
			Integer customerId = (Integer) execution.getVariable("customerId");
			String mailSubject = "No offer available";
			
			eMailerService.sendSimpleMessage(customerId, mailSubject, execution.getId());
		}catch (Exception ex) {
			System.out.println("Error in sending email (Unavailability): "+ex);
		}
		
	}
}
