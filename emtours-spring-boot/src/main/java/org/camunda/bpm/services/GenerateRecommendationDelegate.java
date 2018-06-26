package org.camunda.bpm.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import org.camunda.bpm.emtours.CustomerRepository;
import org.camunda.bpm.emtours.CustomerRequestRepository;
import org.camunda.bpm.emtours.LoggerDelegate;
import org.camunda.bpm.emtours.RecommendationRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.entities.Customer;
import org.camunda.bpm.entities.CustomerRequest;
import org.camunda.bpm.entities.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;

@Component
public class GenerateRecommendationDelegate implements JavaDelegate {

	 private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	 @Autowired
	 public CustomerRepository repository; 
	 public Random randomGenerator;
	 
	 @Autowired(required = true)
	 public CustomerRequestRepository requestrepository;
	 
	 @Autowired(required = true)
	 public RecommendationRepository recomrepository;
	 
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		Iterable<Customer> cust = repository.findAll();
		Iterator<Customer> it = cust.iterator();
		while(it.hasNext()) {
			Customer c = it.next();
			LOGGER.info(c.getName());
		}

		int requestId = (Integer) execution.getVariable("requestId");
		Optional<CustomerRequest> custrequesto = requestrepository.findById(requestId);
		CustomerRequest custrequest = custrequesto.get();
		String budget = (String) execution.getVariable("budget");
		Double randomNum = ThreadLocalRandom.current().nextDouble(0.0, 51.0);
		Double cost = 0.0;
		if (budget == "low") {
			cost = cost + randomNum;
		} else if(budget == "middle") {
			cost = cost + randomNum + 150;
		} else if(budget == "high") {
			cost = cost + randomNum + 300;
		}
		List<Map<String, Object>> dmnresult = (List<Map<String, Object>>) execution.getVariable("dmn_result");
		int index = randomGenerator.nextInt(dmnresult.size());
		Map<String, Object> accommodation = dmnresult.get(index);
	    String hotel = (String) accommodation.get((Object)"hotel");
	    String location = (String) accommodation.get((Object)"location");
	    Integer hotelcost = (Integer) accommodation.get((Object)"cost");
	    cost = cost + hotelcost*custrequest.getNumberPeople();
	    Recommendation recommendation = new Recommendation();
	    recommendation.setDestination(location);
	    recommendation.setHotel(hotel);
	    recommendation.setArrival(custrequest.getArrival());
	    recommendation.setDeparture(custrequest.getDeparture());
	    recommendation.setNumberPeople(custrequest.getNumberPeople());
	    recommendation.setRequest(custrequest);
	    recommendation = recomrepository.save(recommendation);
	    execution.setVariable("recommendationId", recommendation.getId());
	    
	    LOGGER.info("dishDecisionResult: "+ dmnresult.toString());

	}

}
