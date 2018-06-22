package org.camunda.bpm.emtours;

import java.util.Date;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
  
  @Autowired
  private ProcessEngine camunda;

  @RequestMapping(value="/incomingInformation", method=RequestMethod.POST)
  public void incomingInformationPOST(String name, String address, String zip, String city, String country, Date birthday,  String email ) {
	  camunda.getRuntimeService().startProcessInstanceByKey(//
		        "SampleProcess", //
		        Variables //
		          .putValue("name", name)
                  .putValue("address", address)
                  .putValue("zip", zip)
                  .putValue("city", city)
                  .putValue("country", country)
                  .putValue("birthday", birthday)
		          .putValue("email", email));

  }
  
  @RequestMapping(value="/testSend", method=RequestMethod.POST)
  public ResponseEntity<String> testPOST(@RequestBody String cust) {
	  System.out.println("received Post request");
	  if(cust != null) {
		  System.out.println("Name: "+cust);
	  }
	  return ResponseEntity.status(HttpStatus.CREATED).build();
  }
  
 /* private String doPOST(String[] array, String name) {
	  MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
      Map map = new HashMap<String, String>();
      map.put("Content-Type", "application/json");

      headers.setAll(map);

      Map req_payload = new HashMap();
      req_payload.put("name", name);

      HttpEntity<?> request = new HttpEntity<>(req_payload, headers);
      String url = "http://localhost:8080/testSend";

      String result = "";
      ResponseEntity<?> response = new RestTemplate().postForEntity(url, request, String.class);
      HttpStatus statusCode = response.getStatusCode();
      if (statusCode == HttpStatus.ACCEPTED) {
          result = (String) response.getBody();
      }
      return result;
	}*/
}
