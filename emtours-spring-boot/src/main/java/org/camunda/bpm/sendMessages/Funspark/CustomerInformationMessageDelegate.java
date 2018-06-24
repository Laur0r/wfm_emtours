package org.camunda.bpm.sendMessages.Funspark;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.camunda.bpm.entities.Customer;
import org.camunda.bpm.entities.Recommendation;
import org.camunda.bpm.emtours.CustomerRepository;
import org.camunda.bpm.emtours.CustomerRequest;
import org.camunda.bpm.emtours.CustomerRequestRepository;
import org.camunda.bpm.emtours.RecommendationRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class CustomerInformationMessageDelegate implements JavaDelegate {

	@Autowired(required = true)
	public RecommendationRepository recommendationrepository;
	
	@Autowired(required = true)
	public CustomerRequestRepository requestrepository;
	
	public void execute(DelegateExecution execution) throws Exception {
		try {
			int requestId = (int) execution.getVariable("requestId");
			CustomerRequest custrequest = requestrepository.findById(requestId).get();
			
			int recommendationId = (int) execution.getVariable("recommendationId");
			Recommendation recommendation = recommendationrepository.findById(recommendationId).get();
			
			FunsparkRecommendation postElement = new FunsparkRecommendation();
			postElement.setCustomerId((int) execution.getVariable("customerId"));
			postElement.setDestination(recommendation.getDestination());
			postElement.setStart(recommendation.getStart());
			postElement.setEnd(recommendation.getEnd());
			postElement.setNumberActivities(custrequest.getNumberActivities());
			postElement.setNumberPeople(custrequest.getNumberPeople());
			postElement.setExperienceType(custrequest.getExperienceType());
			String test = doPost(postElement);
			System.out.println("result: "+test);
		} catch(NoSuchElementException e) {
		}	
	}
	
	  
	private String doPost(FunsparkRecommendation string) {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<FunsparkRecommendation> request = new HttpEntity<>(string, headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/testSend");
		builder.queryParam("name", string);
		
	    ResponseEntity<FunsparkRecommendation> response = new RestTemplate().postForEntity(builder.build().encode().toUri(), request, FunsparkRecommendation.class);
	    HttpStatus statusCode = response.getStatusCode();
	    System.out.println(statusCode.toString());
	    return statusCode.toString();
	}
}
