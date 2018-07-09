package org.camunda.bpm.services;

import java.util.Optional;

import org.camunda.bpm.emtours.CustomerRequestRepository;
import org.camunda.bpm.emtours.RecommendationRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.entities.CustomerRequest;
import org.camunda.bpm.entities.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Save recommendation in the database
 */
@Component
public class SaveRecommendation implements JavaDelegate {

	@Autowired(required = true)
	 public CustomerRequestRepository requestrepository;
	 
	 @Autowired(required = true)
	 public RecommendationRepository recomrepository;
	 
	public void execute(DelegateExecution execution) throws Exception {

		int requestId = (Integer) execution.getVariable("requestId");
		Optional<CustomerRequest> custrequesto = requestrepository.findById(requestId);
		CustomerRequest custrequest = custrequesto.get();
		
		String destination = (String) execution.getVariable("destination");
		String hotel = (String) execution.getVariable("hotel");
		String flight = (String) execution.getVariable("flight");
		double cost = (Double) execution.getVariable("cost");	
		
	    Recommendation recommendation = new Recommendation();
	    recommendation.setDestination(destination);
	    recommendation.setHotel(hotel);
	    recommendation.setFlight(flight);
	    recommendation.setArrival(custrequest.getArrival());
	    recommendation.setDeparture(custrequest.getDeparture());
	    recommendation.setNumberPeople(custrequest.getNumberPeople());
	    recommendation.setRequest(custrequest);
	    recommendation.setCost(cost);
	    recommendation = recomrepository.save(recommendation);
	    System.out.println("recommendation was saved: "+recommendation.getId());
	    execution.setVariable("recommendationId", recommendation.getId());
	
	}
}