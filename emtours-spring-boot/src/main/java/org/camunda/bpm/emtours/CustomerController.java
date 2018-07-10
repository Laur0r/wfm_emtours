package org.camunda.bpm.emtours;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.entities.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Rest Controller which receives all Customer data and processes it
 */
@Component
@RestController
public class CustomerController {
  
  @Autowired
  private ProcessEngine camunda;
  @Autowired(required = true)
  public CustomerRequestRepository repository;

  /**
   * Initial Input of basic Customer Information which starts the process
   * @param json Json with the information
   */
  @CrossOrigin(origins="*")
  @RequestMapping(value="/basicCustomerInformation", method=RequestMethod.POST, consumes="application/json")
  public void incomingCustomerInformation(@RequestBody String json) throws IOException, ParseException {
	  ObjectMapper mapper = new ObjectMapper();
	  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	  JsonNode node = mapper.readTree(json);
	  JsonNode nameNode = node.at("/name");
	  String name = nameNode.asText();
	  JsonNode genderNode = node.at("/gender");
	  String gender = genderNode.asText();
	  JsonNode emailNode = node.at("/email");
	  String email = emailNode.asText();
	  JsonNode addressNode = node.at("/address");
	  String address = addressNode.asText();
	  JsonNode zipNode = node.at("/zip");
	  String zip = zipNode.asText();
	  JsonNode budgetNode = node.at("/budget");
	  String budget = budgetNode.asText();
	  JsonNode cityNode = node.at("/city");
	  String city = cityNode.asText();
	  JsonNode countryNode = node.at("/country");
	  String country = countryNode.asText();
	  JsonNode birthdayNode = node.at("/birthday");
	  Date birthday = dateFormat.parse(birthdayNode.asText());
	  String birthdayString = dateFormat.format(birthday);
	  JsonNode arrivalNode = node.at("/arrival");
	  Date arrival = dateFormat.parse(arrivalNode.asText());
	  String arrivalString = dateFormat.format(arrival);
	  JsonNode departureNode = node.at("/departure");
	  Date departure = dateFormat.parse(departureNode.asText());
	  String departureString = dateFormat.format(departure);
	  JsonNode climateNode = node.at("/climate");
	  String climate = climateNode.asText();
	  JsonNode peopleNode = node.at("/numberPeople");
	  int numberPeople = peopleNode.asInt();
	  JsonNode activitiesNode = node.at("/numberActivities");
	  int numberActivities = activitiesNode.asInt();
	  JsonNode experienceNode = node.at("/experienceType");
	  String experienceType = experienceNode.asText();
	  
	  camunda.getRuntimeService().startProcessInstanceByKey(//
		        "sid-emtours", //
		        Variables //
		          .putValue("name", name)
		          .putValue("gender", gender)
                  .putValue("address", address)
                  .putValue("zip", zip)
                  .putValue("city", city)
                  .putValue("country", country)
                  .putValue("birthday", birthday)
                  .putValue("birthdayString", birthdayString)
		          .putValue("email", email)
	  			  .putValue("arrival", arrival)
	  			  .putValue("arrivalString", arrivalString)
	  			  .putValue("departure", departure)
	  			  .putValue("departureString", departureString)
	  			  .putValue("budget", budget)
	  			  .putValue("climate", climate)
	  			  .putValue("numberPeople", numberPeople)
	  			  .putValue("numberActivities", numberActivities)
	  			  .putValue("experienceType", experienceType));
  }
  
  /**
   * Receives further Customer Information
   * @param json
   */
  @CrossOrigin(origins="*")
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
			camunda.getRuntimeService().createMessageCorrelation("furtherCustomerInformation")
			.processInstanceId(executionId).correlate();
		} catch(Exception e) {
			System.out.println("receive further Customer failed");
		}
		
		
		return "";
  }
  
  /**
   * Receives the customer feedback on the recommendation
   * @param json
   */
  @CrossOrigin(origins="*")
  @RequestMapping(value="/customerRecommendationFeedback", method=RequestMethod.POST, consumes="application/json")
	public String receiveRecommendationFeedback(@RequestBody String json) throws IOException {
	  ObjectMapper mapper = new ObjectMapper();
	  JsonNode node = mapper.readTree(json);
	  JsonNode executionNode = node.at("/executionId");
	  String executionId = executionNode.asText();
	  JsonNode responseNode = node.at("/customerResponse");
	  String customerResponse = responseNode.asText();
	  if(customerResponse.equals("confirm")) {
		  camunda.getRuntimeService().createMessageCorrelation("bookingRequest")
			.processInstanceId(executionId).correlate();
	  } else if(customerResponse.equals("cancel")) {
		  camunda.getRuntimeService().createMessageCorrelation("bookingCancellation")
			.processInstanceId(executionId).correlate();
	  }	else if(customerResponse.equals("refine")){
		  camunda.getRuntimeService().createMessageCorrelation("customerRecommendationFeedback")
			.processInstanceId(executionId).correlate();
	  }
	  
		return "";
	}
}
