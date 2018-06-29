package org.camunda.bpm.emtours;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunsparkController implements ExecutionListener{
	
	@Autowired
	private ProcessEngine camunda;
	private String executionId;
	
	@RequestMapping(value="/testSend", method=RequestMethod.POST)
	public ResponseEntity<String> testPOST(@RequestBody String cust) {
		System.out.println("received Post request");
		if(cust != null) {
			System.out.println("Name: "+cust);
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	 
	@RequestMapping(value="/recommendationFeedback", method=RequestMethod.POST)
	public String receiveFeedback(String feedback) {
		System.out.println("received Feedback");
		if(feedback == "true") {
			camunda.getRuntimeService().setVariable(executionId, "feedback", true);
		} else {
			camunda.getRuntimeService().setVariable(executionId, "feedback", false);
		}
		camunda.getRuntimeService().createMessageCorrelation("feedback")
		  .processInstanceId(executionId)
		  .correlate();
		
		return "";
	}

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("activated Listener!!!!!!!!!!!!!!!!!!");
		this.executionId = execution.getId();
		
	}

}
