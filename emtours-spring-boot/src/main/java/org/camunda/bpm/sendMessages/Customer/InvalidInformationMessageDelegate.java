package org.camunda.bpm.sendMessages.Customer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.emtours.Customer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class InvalidInformationMessageDelegate implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		Customer cust = new Customer();
		cust.setName("Laura");
		cust.setEmail("bla@test.de");
		String test = doPost(cust);
		System.out.println("result: "+test);
	
	}
	
	  
	private String doPost(Customer string) {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<Customer> request = new HttpEntity<>(string, headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/testSend");
		builder.queryParam("name", string);
		
	    ResponseEntity<Customer> response = new RestTemplate().postForEntity(builder.build().encode().toUri(), request, Customer.class);
	    HttpStatus statusCode = response.getStatusCode();
	    System.out.println(statusCode.toString());
	    return statusCode.toString();
	}
}