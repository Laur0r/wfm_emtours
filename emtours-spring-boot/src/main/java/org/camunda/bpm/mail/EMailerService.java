package org.camunda.bpm.mail;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.camunda.bpm.emtours.ActivityRepository;
import org.camunda.bpm.emtours.CustomerRepository;
import org.camunda.bpm.emtours.CustomerRequestRepository;
import org.camunda.bpm.emtours.RecommendationRepository;
import org.camunda.bpm.entities.Activity;
import org.camunda.bpm.entities.Customer;
import org.camunda.bpm.entities.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EMailerService {

	@Autowired(required = true)
	public CustomerRepository custRepository;

	@Autowired(required = true)
	public CustomerRequestRepository custRequestRepository;

	@Autowired(required = true)
	public RecommendationRepository recommendationRepository;

	@Autowired(required = true)
	public ActivityRepository activityRepository;

	private JavaMailSender emailSender;

	public EMailerService(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	public void sendInvalidMessage(int customerId, String subject) {

		Optional<Customer> customer = custRepository.findById(customerId);

		String eMail = customer.get().getEmail();
		if (isValidEmailAddress(eMail)) {

			String name = customer.get().getName();
			String gender = customer.get().getGender();

			String salutation = "Ms.";
			if (gender.equals("male")) {
				salutation = "Mr.";
			}

			String msg = String.format(INVALID_MSG, salutation, name);

			sendMessage(eMail, subject, msg);
		} else {
			System.out.println("E-Mail not send: invalid Mail Adress!");
		}
	}

	public void sendConfirmationMessage(int customerId, int recommendationId, String subject) {
		Optional<Customer> customer = custRepository.findById(customerId);

		String eMail = customer.get().getEmail();
		if (isValidEmailAddress(eMail)) {

			String name = customer.get().getName();
			String gender = customer.get().getGender();

			String salutation = "Ms.";
			if (gender.equals("male")) {
				salutation = "Mr.";
			}

			Optional<Recommendation> recommendation = recommendationRepository.findById(recommendationId);
			String destination = recommendation.get().getDestination();
			Date startDate = recommendation.get().getArrival();
			Date endDate = recommendation.get().getDeparture();
			Date currentDate = new Date();
			String flight = recommendation.get().getFlight();
			String hotel = recommendation.get().getHotel();
			Double price = recommendation.get().getCost();
			int numberPeople = recommendation.get().getNumberPeople();
			 Collection<Activity> activities = recommendation.get().getActivities();

			String msg = String.format(CONFIRMATION_MSG, salutation, name, destination, startDate, endDate, flight,
					hotel, price, numberPeople, activities, currentDate);

			sendMessage(eMail, subject, msg);
			
		} else {
			System.out.println("E-Mail not send: invalid Mail Adress!");
		}

	}

	private void sendMessage(String eMail, String subject, String msg) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(eMail);
		message.setSubject(subject);
		message.setText(msg);
		emailSender.send(message);
		System.out.println("E-Mail send");
	}

	public static String INVALID_MSG = "Dear %s %s, " + "\nyour travel request can not be processed! "
			+ "\nThe data you have entered is invalid." + "\n" + "\nWe are looking forward to your next request!" + "\n"
			+ "\nYours sincerely," + "\nemTours TravelAgency";

	public static String CONFIRMATION_MSG = "Dear %s %s, "
			+ "\npack your bags! - your booking at emTours is confirmed: You are travelling to %s."
			+ "\nThe facts of your travel are as follows:" + "\n" + "\nFrom: %s To: %s" + "\nFlight: %s"
			+ "\nAccomodation: %s" + "\nPrice: %s" + "\nNumber of Travellers: %s"
			+ "\nYour journey includes the following activities:" + "\n%s" + "\n"
			+ "\nYou can find information on payment and emTours account data in the attached documents. Please settle the invoice within 14 days from today: %s."
			+ "\n" + "\nThank you for travelling with emTours - your #1 travel agency in Münster!" + "\n"
			+ "\nYours sincerely," + "\nemTours TravelAgency";

}
