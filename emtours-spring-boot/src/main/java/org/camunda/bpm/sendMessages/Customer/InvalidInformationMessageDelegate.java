package org.camunda.bpm.sendMessages.Customer;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.mail.EMailerService;
import org.springframework.stereotype.Component;

/**
 * Send the customer a mail that the information provided are not valid
 */
@Component
public class InvalidInformationMessageDelegate implements JavaDelegate {

	private EMailerService eMailerService;
	
	public InvalidInformationMessageDelegate(EMailerService eMailerService) {
		this.eMailerService = eMailerService;
	}
	
	public void execute(DelegateExecution execution) throws Exception {

		try {
			
			String customerMail = (String) execution.getVariable("email");
			String customerName = (String) execution.getVariable("name");
			String customerGender = (String) execution.getVariable("gender");
			String mailSubject = "Invalid Information";
			
			eMailerService.sendInvalidMessage(customerMail, customerName, customerGender, mailSubject);
			System.out.println("E-Mail:InvalidInformation sent");
		}catch (Exception ex) {
			System.out.println("Error in sending email (InvalidInformation): "+ex);
		}
	}
}