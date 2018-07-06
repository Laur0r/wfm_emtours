package org.camunda.bpm.emtours;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.entities.Customer;
import org.camunda.bpm.entities.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RestController
public class CustomerController {
  
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
  
  @RequestMapping(value="/testCustomerInformation", method=RequestMethod.POST, consumes="application/json")
  public void incomingInformationPOST(@RequestBody String json) throws IOException {
	  ObjectMapper mapper = new ObjectMapper();
	  JsonNode node = mapper.readTree(json);
	  JsonNode coordinatesNode = node.at("/executionId");
	  JsonNode coordinatesNode2 = node.at("/cust");
	  String cust = coordinatesNode.asText();
			  //mapper.writerWithDefaultPrettyPrinter()
      //.writeValueAsString(coordinatesNode);
	  String executionId = mapper.writerWithDefaultPrettyPrinter()
		      .writeValueAsString(coordinatesNode2);
	  System.out.println("hallo");
	  System.out.println(cust);
	  System.out.println(executionId);
	  /*camunda.getRuntimeService().startProcessInstanceByKey(//
		        "SampleProcess", //
		        Variables //
		          .putValue("customer", customerJson));*/
  }
  
  @RequestMapping(value="/furtherCustomerInformation", method=RequestMethod.POST, consumes="application/json")
	public String receiveFurtherInformation(@RequestBody String json) throws IOException {
	  ObjectMapper mapper = new ObjectMapper();
	  JsonNode node = mapper.readTree(json);
	  JsonNode numberNode = node.at("/numberActivities");
	  JsonNode experienceNode = node.at("/experienceType");
	  JsonNode executionNode = node.at("/executionId");
	  Integer numberActivities = Integer.parseInt(numberNode.asText());
	  String experienceType = experienceNode.asText();
	  String executionId = executionNode.asText();
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
  
  @RequestMapping(value="/CustomerBookingRequest", method=RequestMethod.POST, consumes="application/json")
	public String receiveBookingRequest(@RequestBody String json) throws IOException {
	  ObjectMapper mapper = new ObjectMapper();
	  JsonNode node = mapper.readTree(json);
	  JsonNode executionNode = node.at("/executionId");
	  String executionId = executionNode.asText();
		camunda.getRuntimeService().messageEventReceived("bookingRequest", executionId);
		return "";
	}
  
  @RequestMapping(value="/CustomerBookingCancellation", method=RequestMethod.POST, consumes="application/json")
	public String receiveCancellation(@RequestBody String json) throws IOException {
	  ObjectMapper mapper = new ObjectMapper();
	  JsonNode node = mapper.readTree(json);
	  JsonNode executionNode = node.at("/executionId");
	  String executionId = executionNode.asText();
	  camunda.getRuntimeService().messageEventReceived("bookingCancellation", executionId);
		return "";
	}
  
  @RequestMapping(value="/CustomerRecommendationFeedback", method=RequestMethod.POST, consumes="application/json")
	public String receiveRecommendationFeedback(@RequestBody String json) throws IOException {
	  ObjectMapper mapper = new ObjectMapper();
	  JsonNode node = mapper.readTree(json);
	  JsonNode executionNode = node.at("/executionId");
	  String executionId = executionNode.asText();
	  camunda.getRuntimeService().messageEventReceived("customerRecommendationFeedback", executionId);
		return "";
	}
  
  @RequestMapping(value="/CustomerPayment", method=RequestMethod.POST, consumes="application/json")
	public String receivePayment(@RequestBody String json) throws IOException {
	  ObjectMapper mapper = new ObjectMapper();
	  JsonNode node = mapper.readTree(json);
	  JsonNode executionNode = node.at("/executionId");
	  String executionId = executionNode.asText();
	  camunda.getRuntimeService().messageEventReceived("customerPayment", executionId);
		return "";
	}
  
  @RequestMapping(value="/CustomerIncorrectPayment", method=RequestMethod.POST, consumes="application/json")
	public String receiveIncorrectPayment(@RequestBody String json) throws IOException {
	  ObjectMapper mapper = new ObjectMapper();
	  JsonNode node = mapper.readTree(json);
	  JsonNode executionNode = node.at("/executionId");
	  String executionId = executionNode.asText();
	  camunda.getRuntimeService().messageEventReceived("customerIncorrectPayment", executionId);
		return "";
	}
}
