package org.camunda.bpm.sendMessages.Customer;

import org.camunda.bpm.emtours.CustomerRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.mail.EMailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingConfirmationMessageDelegate implements JavaDelegate {

	@Autowired(required = true)
	public CustomerRepository custRepository;

	private EMailerService eMailerService;
	
	public BookingConfirmationMessageDelegate(EMailerService eMailerService) {
		this.eMailerService = eMailerService;
	}
	
	public void execute(DelegateExecution execution) throws Exception {
		
		try {
			
			Integer customerId = (Integer) execution.getVariable("customerId");
			Integer recommendationId = (Integer) execution.getVariable("recommendationId");
			
			String mailSubject = "Booking confirmation";
			
			eMailerService.sendConfirmationMessage(customerId, recommendationId, mailSubject);
			System.out.println("E-Mail:BookingConfirmation sent");
		}catch (Exception ex) {
			System.out.println("Error in sending email (BookingConfirmation): "+ex);
		}
		
	} 
}