package org.camunda.bpm.sendMessages.Customer;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.mail.EMailerService;
import org.springframework.stereotype.Component;

/**
 * Send the customer a mail containing the recommendation offer
 */
@Component
public class RecommendationsMessageDelegate implements JavaDelegate {
	
	private EMailerService eMailerService;
	
	public RecommendationsMessageDelegate(EMailerService eMailerService) {
		this.eMailerService = eMailerService;
	}

	public void execute(DelegateExecution execution) throws Exception {

		try {
		Integer customerId = (Integer) execution.getVariable("customerId");
		Integer recommendationId = (Integer) execution.getVariable("recommendationId");
	
		String mailSubject = "Travel recommendation";
		
		eMailerService.sendComplexMessage(customerId, recommendationId, mailSubject, execution.getId());
		System.out.println("E-Mail:TravelRecommendation sent");
		}catch (Exception ex) {
			System.out.println("Error in sending email (TravelRecommendation): "+ex);
		}
	}
}
