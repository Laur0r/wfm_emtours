package org.camunda.bpm.sendMessages.Funspark;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.camunda.bpm.emtours.CustomerRequestRepository;
import org.camunda.bpm.emtours.RecommendationRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.entities.CustomerRequest;
import org.camunda.bpm.entities.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Send Funspark the updated customer information
 */
@Component
@PropertySource({"classpath:application.properties"})
public class RequestFurtherInfoMessageDelegate implements JavaDelegate {

	@Autowired(required = true)
	public RecommendationRepository recommendationrepository;
	
	@Autowired(required = true)
	public CustomerRequestRepository requestrepository;
	
	@Value("${funspark.url}")
	private String funsparkUrl;
	
	public void execute(DelegateExecution execution) throws Exception {

		try {
			int requestId = (Integer) execution.getVariable("requestId");
			Optional<CustomerRequest> custrequesto = requestrepository.findById(requestId);
			CustomerRequest custrequest = custrequesto.get();
			
			int recommendationId = (Integer) execution.getVariable("recommendationId");
			Recommendation recommendation = recommendationrepository.findById(recommendationId).get();
			
			FunsparkRecommendation postElement = new FunsparkRecommendation();
			postElement.setRecommendationId((Integer) execution.getVariable("recommendationId"));
			postElement.setExecutionId((String) execution.getVariable("funsparkExecutionId"));
			postElement.setCustomer(custrequest.getCustomer());
			postElement.setDestination(recommendation.getDestination());
			postElement.setStart(recommendation.getArrival());
			postElement.setEnd(recommendation.getDeparture());
			postElement.setNumberActivities(custrequest.getNumberActivities());
			postElement.setNumberPeople(custrequest.getNumberPeople());
			postElement.setExperienceType(custrequest.getExperienceType());
			doPost(postElement);
		} catch(NoSuchElementException e) {
		}	
	}
	
	  
	private String doPost(FunsparkRecommendation string) {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<FunsparkRecommendation> request = new HttpEntity<>(string, headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(funsparkUrl +"/furtherInformation");
		builder.queryParam("name", string);
		
	    ResponseEntity<FunsparkRecommendation> response = new RestTemplate().postForEntity(builder.build().encode().toUri(), request, FunsparkRecommendation.class);
	    HttpStatus statusCode = response.getStatusCode();
	    return statusCode.toString();
	
	}
}