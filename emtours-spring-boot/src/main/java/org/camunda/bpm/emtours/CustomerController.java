package org.camunda.bpm.emtours;

import java.util.Date;
import java.util.Optional;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.entities.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class CustomerController implements ExecutionListener{
  
  @Autowired
  private ProcessEngine camunda;
  @Autowired(required = true)
  public CustomerRequestRepository repository;

  @RequestMapping(value="/basicCustomerInformation", method=RequestMethod.POST)
  public void incomingInformationPOST(String name, String gender, String address, String zip, String city, 
		  String country, Date birthday,  String email, Date arrival, Date departure, String budget, String climate,
		  Integer numberPeople, Integer numberActivities, String experienceType) {
	  camunda.getRuntimeService().startProcessInstanceByKey(//
		        "SampleProcess", //
		        Variables //
		          .putValue("name", name)
		          .putValue("gender", gender)
                  .putValue("address", address)
                  .putValue("zip", zip)
                  .putValue("city", city)
                  .putValue("country", country)
                  .putValue("birthday", birthday)
		          .putValue("email", email)
	  			  .putValue("arrival", arrival)
	  			  .putValue("departure", departure)
	  			  .putValue("budget", budget)
	  			  .putValue("climate", climate)
	  			  .putValue("numberPeople", numberPeople)
	  			  .putValue("numberActivities", numberActivities)
	  			  .putValue("experienceType", experienceType));
  }
  
  @RequestMapping(value="/furtherCustomerInformation", method=RequestMethod.POST)
	public String receiveFurtherInformation(Integer numberActivities, String experienceType, String executionId) {
		try {
			Integer requestId = (Integer) camunda.getRuntimeService().getVariable(executionId, "requestId");
			Optional<CustomerRequest> customero = repository.findById(requestId);
			CustomerRequest request = customero.get();
			request.setNumberActivities(numberActivities);
			request.setExperienceType(experienceType);
			repository.save(request);
			camunda.getRuntimeService().messageEventReceived("furtherCustomerInformation", executionId);
		} catch(Exception e) {
			System.out.println("receive further Customer failed");
		}
		
		
		return "";
  }
  
  @RequestMapping(value="/CustomerBookingRequest", method=RequestMethod.POST)
	public String receiveBookingRequest(String Bookingrequest, String executionId) {
		camunda.getRuntimeService().messageEventReceived("bookingRequest", executionId);
		return "";
	}
  
  @RequestMapping(value="/CustomerBookingCancellation", method=RequestMethod.POST)
	public String receiveCancellation(String Bookingcancellation, String executionId) {
		camunda.getRuntimeService().messageEventReceived("bookingCancellation", executionId);
		return "";
	}
  
  @RequestMapping(value="/CustomerRecommendationFeedback", method=RequestMethod.POST)
	public String receiveRecommendationFeedback(String Bookingrequest, String executionId) {
		camunda.getRuntimeService().messageEventReceived("customerRecommendationFeedback", executionId);
		return "";
	}
  
  @RequestMapping(value="/CustomerPayment", method=RequestMethod.POST)
	public String receivePayment(String payment, String executionId) {
		camunda.getRuntimeService().messageEventReceived("customerPayment", executionId);
		return "";
	}
  
  @RequestMapping(value="/CustomerIncorrectPayment", method=RequestMethod.POST)
	public String receiveIncorrectPayment(String payment, String executionId) {
		camunda.getRuntimeService().messageEventReceived("customerIncorrectPayment", executionId);
		return "";
	}
  
  @Override
  public void notify(DelegateExecution execution) throws Exception {
	System.out.println("activated Listener!!!!!!!!!!!!!!!!!!");
		
  }
}
