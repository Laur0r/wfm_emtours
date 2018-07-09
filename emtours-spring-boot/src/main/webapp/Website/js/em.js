$(window).on('load', function () {
	$(".se-pre-con").fadeOut("slow");;
});
$(document).ready(function(){
  $('.modal').modal();
  $('.tooltipped').tooltip();
  $('.slider').slider('init', {'height' : 500, 'indicators' : false});
  $('select').material_select();
  $('.stepper').activateStepper();
  $('.datepicker').pickadate({
    format: 'yyyy-mm-dd',
    selectMonths: true,
    selectYears: 200
  });
  $('.content-link').click(function(e) {
    e.preventDefault();
    $('html, body').animate({
      scrollTop: $('#customer_information').offset().top
    }, 800);
  });
  $("#btn_submit").click(function() {
    var first_name = document.getElementById('first_name');
    var last_name = document.getElementById('last_name');
		var email = document.getElementById('email');
    var gender = document.getElementById('gender');
    var adress = document.getElementById('adress');
    var zip_code = document.getElementById('zip_code');
    var city = document.getElementById('city');
    var country = document.getElementById('country');
    var birthday = document.getElementById('birthday');
    var departure_date = document.getElementById('departure_date');
    var return_date = document.getElementById('return_date');
    var budget = document.getElementById('budget');
    var climate = document.getElementById('climate');
    var num_adults = document.getElementById('num_adults');
    var num_activities = document.getElementById('num_activities');
    var activity = document.getElementById('activity');
    if (first_name.value != null && first_name.value != undefined && first_name.value != "" && first_name.validity.valid != false
    && last_name.value != undefined && last_name.value != null && last_name.value != "" && last_name.validity.valid != false
		&& email.value != undefined && email.value != null && email.value != "" && email.validity.valid != false
    && gender.value != undefined && gender.value != null && gender.value != "" && gender.validity.valid != false
    && adress.value != undefined && adress.value != null && adress.value != "" && adress.validity.valid != false
    && zip_code.value != undefined && zip_code.value != null && zip_code.value != "" && zip_code.validity.valid != false
    && city.value != undefined && city.value != null && city.value != "" && city.validity.valid != false
    && country.value != undefined && country.value != null && country.value != "" && country.validity.valid != false
    && birthday.value != undefined && birthday.value != null && birthday.value != "" && birthday.validity.valid != false
    && departure_date.value != undefined && departure_date.value != null && departure_date.value != "" && departure_date.validity.valid != false
    && return_date.value != undefined && return_date.value != null && return_date.value != "" && return_date.validity.valid != false && return_date.value > departure_date.value
    && budget.value != undefined && budget.value != null && budget.value != "" && budget.validity.valid != false
    && climate.value != undefined && climate.value != null && climate.value != "" && climate.validity.valid != false
    && num_adults.value != undefined && num_adults.value != null && num_adults.value != "" && num_adults.validity.valid != false)
     {
			submit();
		} else {
      console.log("Empty or invalid input fields")
      document.getElementById("errormessage").innerHTML = "Please fill out every fields with valid values.";
      $('#fail').modal('open');
		}
	});
});


// Function for submitting first customer information
function submit() {

  var customer_information = {
    "name": document.getElementById('first_name').value + " " + document.getElementById('last_name').value,
    "email" : document.getElementById('email').value,
    "gender": document.getElementById('gender').value,
    "address": document.getElementById('address').value,
    "zip": document.getElementById('zip_code').value,
    "city": document.getElementById('city').value,
    "country":  document.getElementById('country').value,
    "birthday": document.getElementById('birthday').value,
    "arrival": document.getElementById('departure_date').value,
    "departure": document.getElementById('return_date').value,
    "budget": document.getElementById('budget').value,
    "climate": document.getElementById('climate').value,
    "numberPeople": document.getElementById('num_adults').value,
    "numberActivities": document.getElementById('num_activities').value,
    "experienceType": document.getElementById('activity').value
  }
  var json_customer_information = JSON.stringify(customer_information);
  console.log("Post Request:" + json_customer_information)
  $.ajax({
    url: 'http://localhost:8080/basicCustomerInformation',
    data: json_customer_information,
    headers: {
      "content-type" : "application/json"
    },
    type: 'POST',
    async: true,
    processData: false,
    success: function(data) {
      console.log("success");
      $('#success').modal('open');
      console.log(data);
    },
    error: function (jqxhr) {
      if(jqxhr.responseText != undefined && jqxhr.responseText != null) {
        document.getElementById("errormessage").innerHTML = jqxhr.responseText;
      } else {
        document.getElementById("errormessage").innerHTML = "Please try again in a few seconds.";
      }
      $('#fail').modal('open');
      if (jqxhr !== undefined) {
        console.log(jqxhr);
      }
    }
  })

};

// Function for sending Response for Recommendations
function sendResponse() {
  var urlParams = new URLSearchParams(window.location.search);
  console.log(urlParams.has('executionId'));
  console.log(urlParams.get('executionId'));

  if (feedback != undefined && feedback != null && feedback.value != "") {
      var customer_information = {
          "executionId": executionId,
          "customerResponse": feedback.value
      }
      var json_customer_information = JSON.stringify(customer_information);
      console.log("Post Request:" + json_customer_information);
      $.ajax({
          url: 'http://localhost:8080/customerRecommendationFeedback',
          data: json_customer_information,
          headers: {
              "content-type" : "application/json"
          },
          crossOrigin: false,
          type: 'POST',
          async: true,
          processData: false,
          success: function(data) {
              console.log("success");
              $('#success').modal('open');
              console.log(data);
          },
          error: function (jqxhr) {
              if(jqxhr.responseText != undefined && jqxhr.responseText != null) {
                  document.getElementById("errormessage").innerHTML = jqxhr.responseText;
              } else {
                  document.getElementById("errormessage").innerHTML = "Please try again in a few seconds.";
              }
              $('#fail').modal('open');
              if (jqxhr !== undefined) {
                  console.log(jqxhr);
              }
          }
      })
  } else {
      console.log("Empty or invalid input fields");
      document.getElementById("errormessage").innerHTML = "Please fill select your response.";
      $('#fail').modal('open');
  }
};
