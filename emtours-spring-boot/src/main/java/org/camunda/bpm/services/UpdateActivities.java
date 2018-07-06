package org.camunda.bpm.services;

import org.camunda.bpm.emtours.ActivityRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import aj.org.objectweb.asm.TypeReference;
import antlr.collections.List;

public class UpdateActivities implements JavaDelegate {
	
	 @Autowired(required = true)
	 public ActivityRepository actrepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String activityJson = (String) execution.getVariable("activityBooking");
		ObjectMapper mapper = new ObjectMapper();
		List<ActivityDate> activityBooking = mapper.readValue(activityJson, new TypeReference<List<ActivityDate>>(){});
		
	}

}
