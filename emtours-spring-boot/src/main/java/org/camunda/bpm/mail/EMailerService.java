package org.camunda.bpm.mail;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
import org.springframework.mail.javamail.MimeMessageHelper;

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

	public void sendInvalidMessage(String email, String name, String gender, String subject) {

		if (isValidEmailAddress(email)) {

			String salutation = "Ms.";
			if (gender.equals("male")) {
				salutation = "Mr.";
			}

			String msg = String.format(INVALID_MSG, salutation, sanitizeSpecialCharacters(name));

			sendMessage(email, subject, msg);
		} else {
			System.out.println("E-Mail not send: invalid Mail Adress!");
		}
	}

	public void sendSimpleMessage(int customerId, String subject, String executionId) {

		Optional<Customer> customer = custRepository.findById(customerId);

		String eMail = customer.get().getEmail();
		if (isValidEmailAddress(eMail)) {

			String name = customer.get().getName();
			String gender = customer.get().getGender();

			String salutation = "Ms.";
			if (gender.equals("male")) {
				salutation = "Mr.";
			}

			String msg = "";
			if (subject.equals("Invalid Information")) {
				msg = String.format(INVALID_MSG, salutation, sanitizeSpecialCharacters(name));
			} else if (subject.equals("Request for further information")) {
				msg = String.format(FURTHERINFO_MSG, salutation, sanitizeSpecialCharacters(name), executionId);
			} else if (subject.equals("No offer available")) {
				msg = String.format(UNAVAILABLE_MSG, salutation, sanitizeSpecialCharacters(name));
			}

			sendMessage(eMail, subject, msg);
		} else {
			System.out.println("E-Mail not send: invalid Mail Adress!");
		}
	}

	public void sendComplexMessage(int customerId, int recommendationId, String subject, String executionId) {
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

			String msg = "";
			if (subject.equals("Booking confirmation")) {
				String activitiesFormat = formatActivitiesWithDate(activities);
				msg = String.format(CONFIRMATION_MSG, salutation, sanitizeSpecialCharacters(name), sanitizeSpecialCharacters(destination), startDate, endDate, 
						sanitizeSpecialCharacters(flight), sanitizeSpecialCharacters(hotel),price, numberPeople, activitiesFormat, currentDate);
			} else if (subject.equals("Travel recommendation")) {
				String activitiesFormat = formatActivities(activities);
				msg = String.format(RECOMMENDATION_MSG, salutation, sanitizeSpecialCharacters(name), startDate, endDate, sanitizeSpecialCharacters(flight),
						sanitizeSpecialCharacters(destination), sanitizeSpecialCharacters(hotel), price, numberPeople, activitiesFormat, executionId, currentDate);
			}

			sendMessage(eMail, subject, msg);

		} else {
			System.out.println("E-Mail not send: invalid Mail Adress!");
		}

	}
	
	private String sanitizeSpecialCharacters(String var) {
		System.out.println(var);
		String result = var;
		if(var.contains("ü")) {
			System.out.println("contains ü");
			result = result.replace("ü", "&#x00FC;");
		}
		if(var.contains("ä")) {
			System.out.println("contains ä");
			result = result.replace("ä", "&#x00E4;");
		}
		if(var.contains("ö")) {
			System.out.println("contains ö");
			result = result.replace("ö", "&#x00F6;");
		}
		return result;
	}

	private void sendMessage(String eMail, String subject, String msg) {

		try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			String htmlMsg = buildHTML(msg);
			mimeMessage.setContent(htmlMsg, "text/html");
			helper.setTo(eMail);
			helper.setSubject(subject);
			helper.setFrom("book.emtours@gmail.com");
			emailSender.send(mimeMessage);
			
//			SimpleMailMessage message = new SimpleMailMessage();
//			message.setTo(eMail);
//			message.setSubject(subject);
//			message.setText(msg);
//			emailSender.send(message);
			
			System.out.println("E-Mail send");
		} catch (MessagingException e) {
			
		}
	}
	

	public String formatActivities(Collection<Activity> activities) {
		List<Activity> as = (List<Activity>) activities;
		String result = "";
		for(int i=0; i<activities.size(); i++) {
			result = result + "<br/>Name: "+as.get(i).getName()+"<br/>Description: "+as.get(i).getDescription() + 
					"<br/>Location: "+as.get(i).getLocation() + "<br/>Provider: "+as.get(i).getProvider() + 
					"<br/>Price: "+as.get(i).getPrice() + "<br/>";
		}
		return result;
	}
	
	public String formatActivitiesWithDate(Collection<Activity> activities) {
		List<Activity> as = (List<Activity>) activities;
		String result = "";
		for(int i=0; i<as.size(); i++) {
			result = result + "<br/>Name: "+as.get(i).getName()+"<br/>Description: "+as.get(i).getDescription() + 
					"<br/>Location: "+as.get(i).getLocation() + "<br/>Provider: "+as.get(i).getProvider() + 
					"<br/>Price: "+as.get(i).getPrice() + "<br/>Date: "+as.get(i).getDate() + "<br/>";
		}
		return result;
	}
	

	public static String INVALID_MSG = "<b>Dear %s %s, </b><br/>" + "<br/>your travel request can not be processed! "
			+ "<br/>The data you have entered is invalid." + "<br/>" + "<br>We are looking forward to your next request!" + "<br/>"
			+ "<br/>Yours sincerely," + "<br/><br/><b>emTours TravelAgency</b>";

	public static String CONFIRMATION_MSG = "<b>Dear %s %s, </b><br/>"
			+ "<br/>pack your bags! - your booking at emTours is confirmed: You are travelling to %s."
			+ "<br/>The facts of your travel are as follows:" + "<br/>" + "<br/>From: %s To: %s" + "<br/>Flight: %s"
			+ "<br/>Accomodation: %s" + "<br/>Price: %s" + "<br/>Number of Travellers: %s"
			+ "<br/>Your journey includes the following activities:" + "<br/>%s" + "<br/>"
			+ "<br/>You can find information on payment and emTours account data in the attached documents. Please settle the invoice within 14 days from today: %s."
			+ "<br/>" + "<br/>Thank you for travelling with emTours - your #1 travel agency in M&#x00FC;nster!" + "<br/>"
			+ "<br/>Yours sincerely," + "<br/><b>emTours TravelAgency</b>";

	public static String RECOMMENDATION_MSG = "<b>Dear %s %s,  </b><br/>"
			+ "<br/>thank you for your travel request! We are happy to inform you that we were able to provide you with an unbeatable, exclusive travel package!"
			+ "<br/>The dates of our offer are as follows:" + "<br/>" + "<br/>From: %s To: %s" + "<br/>Flight: %s"
			+ "<br/>Destination: %s" + "<br/>Accomodation: %s" + "<br/>Price: %s" + "<br/>Number of Travellers: %s"
			+ "<br/>Your journey will include the following activities:" + "<br/>%s" + "<br/>"
			+ "<br/>You can reply on this recommendation <a href=\"http://localhost:8081/feedback.html?executionId=%s\">here</a>"
			+ "<br/>"
			+ "<br/>We are looking forward to your reply!" + "<br/>" + "<br/>Yours sincerely," + "<br/><b>emTours TravelAgency</b>";

	public static String FURTHERINFO_MSG = "<b>Dear %s %s, </b><br/>"
			+ "<br/>to generate a proper travel recommendation, we need to get to know you and your attitudes better!<br/>"
			+ "<br/>Would you like to take part in activities during your journey" + "<br/>"
			+ "<br/>Please choose your preferred type of activities and a number of how many activities you plan to experience <a href=\"http://localhost:8081/additionalInformation.html?executionId=%s\">here</a>"
			+ "<br/>"
			+ "<br/>Yours sincerely," + "<br/><b>emTours TravelAgency</b>";

	public static String UNAVAILABLE_MSG = "<b>Dear %s %s, </b><br/>"
			+ "<br/>we are sorry to inform you that your requested booking can not be completed: Our partner informed us, that at least one of the proposed activities is already booked out."
			+ "<br/>We hope that you are still interested in travelling with us! - You will receive a new offer soon!"
			+ "<br/>Yours sincerely," + "<br/><b>emTours TravelAgency</b>";

	private String buildHTML(String msg) {
		String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">                                           "+
				"<html xmlns=\"http://www.w3.org/1999/xhtml\">                                                                                                                           "+
				"  <head>                                                                                                                                                                "+
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />                                                                                           "+
				"    <title>[em] Tours</title>                                                                                                                                           "+
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>                                                                                         "+
				"  </head>                                                                                                                                                               "+
				"  <body style=\"margin: 0; padding: 10px 0px 10px 0px;\">                                                                                                               "+
				"    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse; border: 1px solid #cccccc;\">             "+
				"      <tr>                                                                                                                                                              "+
				"        <td align=\"center\" bgcolor=\"#F08080\" style=\"color: white; font-family: Arial, sans-serif; font-size: 24px; line-height: 20px; padding: 40px 0 30px 30px;\">"+
				"                                                                                                                                                                        "+
				"          <img src=\"https://image.ibb.co/h1izmo/logo.png\" alt=\"[em] Tours\" width=\"150px\" style=\"display: block;\" />                                                                  "+
				"          <br/>                                                                                                                                                         "+
				"        </td>                                                                                                                                                           "+
				"                                                                                                                                                                        "+
				"      </tr>                                                                                                                                                             "+
				"      <tr>                                                                                                                                                              "+
				"        <td bgcolor=\"white\" style=\"padding: 40px 30px 40px 30px;\">                                                                                                  "+
				"          <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">                                                                                       "+
				"            <tr>                                                                                                                                                        "+
				"              <td style=\"color: #212121; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px; padding: 20px 0 30px 0;\">                                "+	msg +                                                                                                                                                         
				"              </td>                                                                                                                                                     "+
				"            </tr>                                                                                                                                                       "+
				"          </table>                                                                                                                                                      "+
				"        </td>                                                                                                                                                           "+
				"      </tr>                                                                                                                                                             "+
				"      <tr>                                                                                                                                                              "+
				"        <td bgcolor=\"#F08080\" style=\"padding: 30px 30px 30px 30px;\">                                                                                                "+
				"          <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">                                                                                       "+
				"            <tr>                                                                                                                                                        "+
				"              <td align=\"right\" width=\"75%\" style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 12px; \">                                            "+
				"                <b>&copy; [em] Tours</b>                                                                                                                                "+
				"              </td>                                                                                                                                                     "+
				"            </tr>                                                                                                                                                       "+
				"          </table>                                                                                                                                                      "+
				"        </td>                                                                                                                                                           "+
				"      </tr>                                                                                                                                                             "+
				"    </table>                                                                                                                                                            "+
				"  </body>                                                                                                                                                               "+
				"</html>                                                                                                                                                                 ";

		return html;
	}                                                                                                                                                          
}
