package org.camunda.bpm.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.camunda.bpm.emtours.CustomerRepository;
import org.camunda.bpm.emtours.CustomerRequestRepository;
import org.camunda.bpm.emtours.RecommendationRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.entities.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Generates or completes the recommendation like calculating the prize
 */
@Component
public class GenerateRecommendationDelegate implements JavaDelegate {

	 @Autowired
	 public CustomerRepository repository; 
	 
	 @Autowired(required = true)
	 public CustomerRequestRepository requestrepository;
	 
	 @Autowired(required = true)
	 public RecommendationRepository recomrepository;
	 
	public void execute(DelegateExecution execution) throws Exception {

		int requestId = (Integer) execution.getVariable("requestId");
		Optional<CustomerRequest> custrequesto = requestrepository.findById(requestId);
		CustomerRequest custrequest = custrequesto.get();
		String budget = (String) execution.getVariable("budget");
		double randomNum = ThreadLocalRandom.current().nextDouble(0.0, 51.0);
		double cost = 0.0;
		if (budget.equals("low")) {
			cost = cost + randomNum;
		} else if(budget.equals("middle")) {
			cost = cost + randomNum + 150;
		} else if(budget.equals("high")) {
			cost = cost + randomNum + 300;
		}
		List<Map<String, Object>> dmnresult = (List<Map<String, Object>>) execution.getVariable("dmn_result");
		int index = ThreadLocalRandom.current().nextInt(dmnresult.size());
		Map<String, Object> accommodation = dmnresult.get(index);
	    String hotel = (String) accommodation.get((Object)"hotel");
	    String destination = (String) accommodation.get((Object)"location");
	    String flight = (String) accommodation.get((Object)"flight");
	    
	    Date arrival = custrequest.getArrival();
	    Date departure = custrequest.getDeparture();
	    long difference = departure.getTime() - arrival.getTime();
	    long days = TimeUnit.MILLISECONDS.toDays(difference);
	    
	    Integer hotelcost = (Integer) accommodation.get((Object)"price");
	    cost = cost + hotelcost*custrequest.getNumberPeople()*days;
	    System.out.println(cost);
	    cost = cost*100;
	    cost = Math.round(cost);
	    cost = cost/100;
	    System.out.println(cost);
	    
	    execution.setVariable("cost", cost);
	    execution.setVariable("destination", destination);
	    execution.setVariable("hotel", hotel);
	    execution.setVariable("flight", flight);
	    
	    System.out.println("DMN-Result: "+dmnresult);

	}

}
