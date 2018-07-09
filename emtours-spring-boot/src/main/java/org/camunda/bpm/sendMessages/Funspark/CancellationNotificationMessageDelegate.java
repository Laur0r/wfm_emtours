package org.camunda.bpm.sendMessages.Funspark;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@PropertySource({"classpath:application.properties"})
public class CancellationNotificationMessageDelegate implements JavaDelegate {

	@Value("${funspark.url}")
	private String funsparkUrl;
	
	public void execute(DelegateExecution execution) throws Exception {
		try {
			
			FunsparkNotification postElement = new FunsparkNotification();
			postElement.setRecommendationId((int) execution.getVariable("recommendationId"));
			postElement.setBookingNotification((false));
			postElement.setCancellationNotification((true));
			postElement.setRefinementNotification((false));
			postElement.setExecutionId((String) execution.getVariable("funsparkExecutionId"));
			doPost(postElement);
		} catch(NoSuchElementException e) {
		}	
	
	}


	private String doPost(FunsparkNotification string) {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<FunsparkNotification> request = new HttpEntity<>(string, headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(funsparkUrl +"/recommendationFeedback");
		builder.queryParam("name", string);
		
	    ResponseEntity<FunsparkNotification> response = new RestTemplate().postForEntity(builder.build().encode().toUri(), request, FunsparkNotification.class);
	    HttpStatus statusCode = response.getStatusCode();
	    return statusCode.toString();
	}
}
