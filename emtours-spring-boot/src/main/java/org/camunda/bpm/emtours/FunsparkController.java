package org.camunda.bpm.emtours;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.entities.Activity;
import org.camunda.bpm.entities.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FunsparkController implements ExecutionListener{
	
	@Autowired
	private ProcessEngine camunda;
	private String executionId;
	
	@Autowired(required = true)
	public ActivityRepository activityRepository;
	
	@Autowired(required = true)
	public RecommendationRepository recoRepository;
	
	@RequestMapping(value="/testSend", method=RequestMethod.POST)
	public ResponseEntity<String> testPOST(@RequestBody String cust) {
		System.out.println("received Post request");
		if(cust != null) {
			System.out.println("Name: "+cust);
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	 
	@RequestMapping(value="/recommendationFeedback", method=RequestMethod.POST)
	public String receiveFeedback(String feedback) {
		System.out.println("received Feedback: "+feedback);
		if(feedback.equals("yes")) {
			camunda.getRuntimeService().setVariable(executionId, "feedback", true);
		} else {
			camunda.getRuntimeService().setVariable(executionId, "feedback", false);
		}
		camunda.getRuntimeService().messageEventReceived("feedback", executionId);
		
		return "";
	}
	
	
	@RequestMapping(value="/activityRecommendations", method=RequestMethod.POST)
	public String receiveActivityRecos(Collection<Activity> activityRecos) {
		System.out.println("received Activity Recommendations: "+ activityRecos);
		for (Iterator<Activity> iterator = activityRecos.iterator(); iterator.hasNext();) {
			Activity a = iterator.next();
			a = activityRepository.save(a);
		}
		camunda.getRuntimeService().messageEventReceived("activityRecos", executionId);
		
		return "";
	}
	
	@RequestMapping(value="/bookingUnavailable", method=RequestMethod.POST)
	public String receiveUnavailability(String unavailable) {
		System.out.println("received unavailibility notification");
		
		camunda.getRuntimeService().messageEventReceived("unavailable", executionId);
		
		return "";
	}
	
	@RequestMapping(value="/booking&bill", method=RequestMethod.POST)
	public String receiveBookingAndBill(Collection<Activity> activityBooking, double costs) {
		System.out.println("received booking confirmation and bill from FunSpark");
		
		int recommendationId = (Integer)camunda.getRuntimeService().getVariable(executionId, "recommendationId");
		Optional<Recommendation> recommendationo = recoRepository.findById(recommendationId);
		Recommendation recommendation = recommendationo.get();
		recommendation.setActivityCost(costs);
		recommendation = recoRepository.save(recommendation);
		
		camunda.getRuntimeService().messageEventReceived("bookingandbill", executionId);
		
		return "";
	}
	

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("activated Listener!!!!!!!!!!!!!!!!!!");
		this.executionId = execution.getId();
		
	}

}
