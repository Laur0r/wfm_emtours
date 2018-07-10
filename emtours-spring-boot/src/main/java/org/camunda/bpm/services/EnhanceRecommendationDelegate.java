package org.camunda.bpm.services;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.camunda.bpm.emtours.ActivityRepository;
import org.camunda.bpm.emtours.RecommendationRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.entities.Activity;
import org.camunda.bpm.entities.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



@Component
public class EnhanceRecommendationDelegate implements JavaDelegate {
	
	@Autowired(required = true)
	public ActivityRepository actrepository;

	@Autowired(required = true)
	public RecommendationRepository recorepository;
		 
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String activityJson = (String) execution.getVariable("activityRecommendation");
		ObjectMapper mapper = new ObjectMapper();
		List<Activity> activityBooking = mapper.readValue(activityJson, new TypeReference<List<Activity>>(){});
		
		int recommendationId = (Integer) execution.getVariable("recommendationId");
		Optional<Recommendation> recommendationo = recorepository.findById(recommendationId);
		Recommendation recommendation = recommendationo.get();
		for (Iterator<Activity> iterator = activityBooking.iterator(); iterator.hasNext();) {
			Activity a = iterator.next();
			a.setRecommendation(recommendation);
			a = actrepository.save(a);
		}
			
	}
}

