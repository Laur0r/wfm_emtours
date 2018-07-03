package org.camunda.bpm.sendMessages.Customer;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.mail.EMailerService;
import org.springframework.stereotype.Component;

@Component
public class RequestFurtherInfosMessageDelegate implements JavaDelegate {

	private EMailerService eMailerService;
	
	public RequestFurtherInfosMessageDelegate(EMailerService eMailerService) {
		this.eMailerService = eMailerService;
	}
	
	public void execute(DelegateExecution execution) throws Exception {

		try {
			
			Integer customerId = (Integer) execution.getVariable("customerId");
			String mailSubject = "Request for further information";
			
			eMailerService.sendSimpleMessage(customerId, mailSubject);
			System.out.println("E-Mail:RequestInformation sent");
		}catch (Exception ex) {
			System.out.println("Error in sending email (RequestInformation): "+ex);
		}
	
	}
}
