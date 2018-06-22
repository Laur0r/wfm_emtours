package org.camunda.bpm.sendMessages.Customer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
		String test = doPost("Testing!!!!!!!");
		System.out.println("result: "+test);
	
	}
	
	  
	private String doPost(String string) {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	      
	      //System.out.println(req_payload.toString());
	      //System.out.println(headers.toString());

	      HttpEntity<String> request = new HttpEntity<>(string, headers);
	      UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/testSend");
	      builder.queryParam("name", string);
	     
	      //String url = "http://localhost:8080/testSend";

	      String result = "";
	      ResponseEntity<?> response = new RestTemplate().postForEntity(builder.build().encode().toUri(), request, String.class);
	      HttpStatus statusCode = response.getStatusCode();
	      System.out.println(statusCode.toString());
	      if (statusCode == HttpStatus.ACCEPTED) {
	          result = (String) response.getBody();
	      }
	      return result;
		}
}