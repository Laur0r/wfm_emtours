$(window).on('load', function () {
	$(".se-pre-con").fadeOut("slow");;
});
$(document).ready(function(){
  $('.modal').modal();
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
    if (first_name.value != null && first_name.value != undefined && first_name.value != "" && first_name.validity.valid != false
    && last_name.value != undefined && last_name.value != null && last_name.value != "" && last_name.validity.valid != false
		&& email.value != undefined && email.value != null && email.value != "" && email.validity.valid != false) {
			submit();
		} else {
			console.log("Empty or invalid input fields")
		}
	});
});
  
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
    "departure": document.getElementById('return_date').value,  //YYYY-MM-dd,
    "budget": document.querySelector('input[name = "budget"]:checked').value,
    "climate": document.querySelector('input[name = "climate"]:checked').value,
    "numberPeople": document.getElementById('num_adults').value,
    "numberActivites": document.getElementById('num_activities').value,
    "experienceType": document.querySelector('input[name = "activity"]:checked').value
  }
  var json_customer_information = JSON.stringify(customer_information);
  console.log("Post Request:" + json_customer_information)
  $.ajax({
    url: 'http://localhost:8080/basicCustomerInformation',
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
	
};

