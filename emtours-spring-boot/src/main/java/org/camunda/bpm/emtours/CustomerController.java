package org.camunda.bpm.emtours;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
}
