package org.camunda.bpm.sendMessages.Customer;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.mail.EMailerService;
import org.springframework.stereotype.Component;


@Component
public class InvalidInformationMessageDelegate implements JavaDelegate {

	private EMailerService eMailerService;
	
	public InvalidInformationMessageDelegate(EMailerService eMailerService) {
		this.eMailerService = eMailerService;
	}
	
	public void execute(DelegateExecution execution) throws Exception {

		try {
			
			Integer customerId = (Integer) execution.getVariable("customerId");
			String mailSubject = "Invalid Information";
			
			eMailerService.sendSimpleMessage(customerId, mailSubject);
			System.out.println("E-Mail:InvalidInformation sent");
		}catch (Exception ex) {
			System.out.println("Error in sending email (InvalidInformation): "+ex);
		}
	}
}