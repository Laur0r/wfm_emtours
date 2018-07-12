package org.camunda.bpm.services;

import java.util.List;
import java.util.Optional;

import org.camunda.bpm.emtours.ActivityRepository;
import org.camunda.bpm.emtours.RecommendationRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.entities.Activity;
import org.camunda.bpm.entities.Recommendation;
import org.camunda.bpm.sendMessages.Funspark.ActivityDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Update the activities by adding the date
 */
@Component
public class UpdateActivity implements JavaDelegate {

	@Autowired(required = true)
	public ActivityRepository actrepository;

	@Autowired(required = true)
	public RecommendationRepository recorepository;
		 
	@Override
	public void execute(DelegateExecution execution) throws Exception {
			
		String activityJson = (String) execution.getVariable("activityBooking");
		ObjectMapper mapper = new ObjectMapper();
		List<ActivityDate> activityBooking = mapper.readValue(activityJson, new TypeReference<List<ActivityDate>>(){});
			
		int recommendationId = (Integer) execution.getVariable("recommendationId");
		Optional<Recommendation> reco = recorepository.findById(recommendationId);
		Recommendation recommendation = reco.get();
		List<Activity> oldrecos = (List<Activity>)recommendation.getActivities();
		
		for(ActivityDate booked: activityBooking) {
			for(Activity old: oldrecos) {
				if (booked.getActivity().getName().equals(old.getName())) {
					old.setDate(booked.getBookingDate());
					actrepository.save(old);
				}
			}
		}	
	}
}

