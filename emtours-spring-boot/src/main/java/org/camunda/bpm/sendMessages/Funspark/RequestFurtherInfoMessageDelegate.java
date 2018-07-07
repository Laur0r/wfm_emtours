package org.camunda.bpm.sendMessages.Funspark;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.camunda.bpm.emtours.CustomerRequestRepository;
import org.camunda.bpm.emtours.RecommendationRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.entities.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class RequestFurtherInfoMessageDelegate implements JavaDelegate {

	@Autowired(required = true)
	public RecommendationRepository recommendationrepository;
	
	@Autowired(required = true)
	public CustomerRequestRepository requestrepository;
	
	public void execute(DelegateExecution execution) throws Exception {

		try {
			int requestId = (Integer) execution.getVariable("requestId");
			Optional<CustomerRequest> custrequesto = requestrepository.findById(requestId);
			CustomerRequest custrequest = custrequesto.get();
			
			RequestFurtherInfo postElement = new RequestFurtherInfo();
			postElement.setExecutionId((String) execution.getVariable("funsparkExecutionId"));
			postElement.setNumberActivities(custrequest.getNumberActivities());
			postElement.setExperienceType(custrequest.getExperienceType());
			doPost(postElement);
		} catch(NoSuchElementException e) {
		}	
	}
	
	  
	private String doPost(RequestFurtherInfo string) {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<RequestFurtherInfo> request = new HttpEntity<>(string, headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/testSend");
		builder.queryParam("name", string);
		
	    ResponseEntity<RequestFurtherInfo> response = new RestTemplate().postForEntity(builder.build().encode().toUri(), request, RequestFurtherInfo.class);
	    HttpStatus statusCode = response.getStatusCode();
	    return statusCode.toString();
	
	}
}