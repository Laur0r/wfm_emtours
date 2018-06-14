package org.camunda.bpm.getstarted.loanapproval;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class GenerateRecommendationDelegate implements JavaDelegate {

	 private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	 
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("!!!!!!!!!!!Process went through");

	}

}
