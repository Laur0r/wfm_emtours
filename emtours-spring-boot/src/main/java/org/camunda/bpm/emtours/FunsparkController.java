package org.camunda.bpm.emtours;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunsparkController {
	
	 @RequestMapping(value="/testSend", method=RequestMethod.POST)
	  public ResponseEntity<String> testPOST(@RequestBody String cust) {
		  System.out.println("received Post request");
		  if(cust != null) {
			  System.out.println("Name: "+cust);
		  }
		  return ResponseEntity.status(HttpStatus.CREATED).build();
	  }

}
