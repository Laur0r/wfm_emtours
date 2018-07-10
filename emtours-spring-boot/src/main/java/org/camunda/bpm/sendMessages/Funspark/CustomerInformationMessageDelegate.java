package org.camunda.bpm.sendMessages.Funspark;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.camunda.bpm.emtours.CustomerRepository;
import org.camunda.bpm.emtours.CustomerRequestRepository;
import org.camunda.bpm.emtours.RecommendationRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.entities.Customer;
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
 * Send Funspark the initial customer information
 */
@Component
@PropertySource({"classpath:application.properties"})
public class CustomerInformationMessageDelegate implements JavaDelegate {

	@Autowired(required = true)
	public RecommendationRepository recommendationrepository;
	
	@Autowired(required = true)
	public CustomerRequestRepository requestrepository;
	
	@Autowired(required = true)
	public CustomerRepository repository;
	
	@Value("${funspark.url}")
	private String funsparkUrl;

	
	public void execute(DelegateExecution execution) throws Exception {
		try {
			// TODO updated object
			int custId = (Integer) execution.getVariable("customerId");
			Optional<Customer> custo = repository.findById(custId);
			Customer cust = custo.get();
			
			int requestId = (Integer) execution.getVariable("requestId");
			Optional<CustomerRequest> custrequesto = requestrepository.findById(requestId);
			CustomerRequest custrequest = custrequesto.get();
			
			System.out.println("recommendationId in CustomerInfSend: "+execution.getVariable("recommendationId"));
			int recommendationId = (Integer) execution.getVariable("recommendationId");
			Recommendation recommendation = recommendationrepository.findById(recommendationId).get();
			
			FunsparkRecommendation postElement = new FunsparkRecommendation();
			postElement.setRecommendationId((Integer) execution.getVariable("recommendationId"));
			postElement.setExecutionId(execution.getId());
			postElement.setCustomer(cust);
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
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(funsparkUrl +"/orderRecommendations");
//		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/testSend");
		builder.queryParam("name", string);
		
	    ResponseEntity<String> response = new RestTemplate().postForEntity(builder.build().encode().toUri(), request, String.class);
	    HttpStatus statusCode = response.getStatusCode();
	    return statusCode.toString();
	}	
}
