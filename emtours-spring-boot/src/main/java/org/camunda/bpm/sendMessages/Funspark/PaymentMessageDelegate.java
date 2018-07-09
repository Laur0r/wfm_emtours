package org.camunda.bpm.sendMessages.Funspark;

import java.util.Arrays;

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
public class PaymentMessageDelegate implements JavaDelegate {

	@Value("${funspark.url}")
	private String funsparkUrl;
	
	public void execute(DelegateExecution execution) throws Exception {

		String executionId = (String) execution.getVariable("funsparkExecutionId");
		doPost(executionId);
	
	}
	private String doPost(String string) {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> request = new HttpEntity<>(string, headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(funsparkUrl +"/receivePayment");
		builder.queryParam("name", string);
		
	    ResponseEntity<String> response = new RestTemplate().postForEntity(builder.build().encode().toUri(), request, String.class);
	    HttpStatus statusCode = response.getStatusCode();
	    return statusCode.toString();
	
	}
}
