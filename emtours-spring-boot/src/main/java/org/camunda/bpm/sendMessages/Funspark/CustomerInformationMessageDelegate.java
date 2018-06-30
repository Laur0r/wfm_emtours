package org.camunda.bpm.sendMessages.Funspark;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.camunda.bpm.entities.Recommendation;
import org.camunda.bpm.entities.CustomerRequest;
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
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CustomerInformationMessageDelegate implements JavaDelegate {

	@Autowired(required = true)
	public RecommendationRepository recommendationrepository;
	
	@Autowired(required = true)
	public CustomerRequestRepository requestrepository;
	
	public void execute(DelegateExecution execution) throws Exception {
		try {
			int requestId = (Integer) execution.getVariable("requestId");
			Optional<CustomerRequest> custrequesto = requestrepository.findById(requestId);
			CustomerRequest custrequest = custrequesto.get();
			
			int recommendationId = (Integer) execution.getVariable("recommendationId");
			Recommendation recommendation = recommendationrepository.findById(recommendationId).get();
			
			FunsparkRecommendation postElement = new FunsparkRecommendation();
			postElement.setRecommendationId((Integer) execution.getVariable("recommendationId"));
			postElement.setDestination(recommendation.getDestination());
			postElement.setStart(recommendation.getArrival());
			postElement.setEnd(recommendation.getDeparture());
			postElement.setNumberActivities(custrequest.getNumberActivities());
			postElement.setNumberPeople(custrequest.getNumberPeople());
			postElement.setExperienceType(custrequest.getExperienceType());
			doPost(postElement);
			System.out.println("feedback!!! "+(Boolean)execution.getVariable("feedback"));
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
	    return statusCode.toString();
	}
}
