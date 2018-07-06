package org.camunda.bpm.emtours;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.entities.Activity;
import org.camunda.bpm.entities.Recommendation;
import org.camunda.bpm.sendMessages.Funspark.ActivityDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;


@RestController
public class FunsparkController {
	
	@Autowired
	private ProcessEngine camunda;
	
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
	 
	@RequestMapping(value="/recommendationFeedback", method=RequestMethod.POST, consumes="application/json")
	public String receiveFeedback(@RequestBody String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(json);
		JsonNode executionNode = node.at("/executionId");
		String executionId = executionNode.asText();
		JsonNode funsparkNode = node.at("/funsparkExecutionId");
		String funsparkExecutionId = funsparkNode.asText();
		JsonNode feedbackNode = node.at("/feedback");
		String feedback = feedbackNode.asText();
		if(feedback.equals("yes")) {
			camunda.getRuntimeService().setVariable(executionId, "feedback", true);
		} else {
			camunda.getRuntimeService().setVariable(executionId, "feedback", false);
		}
		camunda.getRuntimeService().setVariable(executionId, "funsparkExecutionId", funsparkExecutionId);
		camunda.getRuntimeService().messageEventReceived("feedback", executionId);
		
		return "";
	}
	
	
	@RequestMapping(value="/activityRecommendations", method=RequestMethod.POST, consumes="application/json")
	public String receiveActivityRecos(@RequestBody String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(json);
		JsonNode executionNode = node.at("/executionId");
		String executionId = executionNode.asText();
		JsonNode activitiesNode = node.at("/activities");
		ObjectReader reader = mapper.readerFor(new TypeReference<List<Activity>>() {
		});
		Collection<Activity> activityRecos = reader.readValue(activitiesNode);
		for (Iterator<Activity> iterator = activityRecos.iterator(); iterator.hasNext();) {
			Activity a = iterator.next();
			a = activityRepository.save(a);
		}
		camunda.getRuntimeService().messageEventReceived("activityRecos", executionId);
		
		return "";
	}
	
	@RequestMapping(value="/bookingUnavailable", method=RequestMethod.POST, consumes="application/json")
	public String receiveUnavailability(@RequestBody String json) throws IOException {
		System.out.println("received unavailibility notification");
		ObjectMapper mapper = new ObjectMapper();
		  JsonNode node = mapper.readTree(json);
		  JsonNode executionNode = node.at("/executionId");
		  String executionId = executionNode.asText();
		camunda.getRuntimeService().messageEventReceived("unavailable", executionId);
		
		return "";
	}
	
	@RequestMapping(value="/bookingAndBill", method=RequestMethod.POST)
	public String receiveBookingAndBill(@RequestBody String json) throws IOException {
		System.out.println("received booking confirmation and bill from FunSpark");
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(json);
		JsonNode executionNode = node.at("/executionId");
		String executionId = executionNode.asText();
		JsonNode costNode = node.at("/costs");
		Double costs = costNode.asDouble();
		JsonNode activitiesNode = node.at("/activities");
		ObjectReader reader = mapper.readerFor(new TypeReference<List<ActivityDate>>() {
		});
		Collection<ActivityDate> activityBooking = reader.readValue(activitiesNode);
		
		//TODO activities date update
		String activityJson = mapper.writeValueAsString(activityBooking);
		camunda.getRuntimeService().setVariable(executionId, "activityBooking", activityJson);
		
		int recommendationId = (Integer)camunda.getRuntimeService().getVariable(executionId, "recommendationId");
		Optional<Recommendation> recommendationo = recoRepository.findById(recommendationId);
		Recommendation recommendation = recommendationo.get();
		recommendation.setActivityCost(costs);
		recommendation = recoRepository.save(recommendation);
		
		camunda.getRuntimeService().messageEventReceived("bookingandbill", executionId);
		
		return "";
	}
}
