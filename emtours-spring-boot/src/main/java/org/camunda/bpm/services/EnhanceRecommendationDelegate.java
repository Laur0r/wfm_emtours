package org.camunda.bpm.services;

import org.camunda.bpm.emtours.ActivityRepository;
import org.camunda.bpm.emtours.RecommendationRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class EnhanceRecommendationDelegate implements JavaDelegate {

	@Autowired(required = true)
	public ActivityRepository actrepository;

	@Autowired(required = true)
	public RecommendationRepository recorepository;
		 
	@Override
	public void execute(DelegateExecution execution) throws Exception {
			
	}
}

