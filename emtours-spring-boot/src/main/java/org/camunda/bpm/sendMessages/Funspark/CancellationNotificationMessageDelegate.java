package org.camunda.bpm.sendMessages.Funspark;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class CancellationNotificationMessageDelegate implements JavaDelegate {

	
	
	public void execute(DelegateExecution execution) throws Exception {
		try {
			
			CancellationNotification postElement = new CancellationNotification();
			postElement.setRecommendationId((int) execution.getVariable("recommendationId"));
			postElement.setBookingNotification((false));
			postElement.setCancellationNotification((true));
			postElement.setRefinementNotification((false));
			String test = doPost(postElement);
		} catch(NoSuchElementException e) {
		}	
	
	}


	private String doPost(CancellationNotification string) {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<CancellationNotification> request = new HttpEntity<>(string, headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/testSend");
		builder.queryParam("name", string);
		
	    ResponseEntity<CancellationNotification> response = new RestTemplate().postForEntity(builder.build().encode().toUri(), request, CancellationNotification.class);
	    HttpStatus statusCode = response.getStatusCode();
	    return statusCode.toString();
	}
}
