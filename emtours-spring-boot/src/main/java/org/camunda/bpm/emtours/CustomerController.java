package org.camunda.bpm.emtours;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
}
