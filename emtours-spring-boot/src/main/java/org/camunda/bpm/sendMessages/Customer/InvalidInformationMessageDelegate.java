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
			eMailerService.sendSimpleMessage("book.emtours@gmail.com","Test","Dies ist ein Test");
			System.out.println("E-Mail sent");
		}catch (Exception ex) {
			System.out.println("Error in sending email: "+ex);
		}
	
	}
}