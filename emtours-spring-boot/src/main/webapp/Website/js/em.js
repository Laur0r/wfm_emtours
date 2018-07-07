$(window).on('load', function () {
	$(".se-pre-con").fadeOut("slow");;
});
$(document).ready(function(){
  $('.slider').slider('init', {'height' : 500, 'indicators' : false});
  $('select').material_select();
  $('.stepper').activateStepper();
  $('.datepicker').pickadate({
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
			register();
		} else {
			console.log("Empty or invalid input fields")
		}
	});
});
  
function submit() {

  var user = {
    "user-name": document.getElementById('first_name').value + " " + document.getElementById('last_name').value,
    "user-mail" : document.getElementById('email').value
  }
  var json_user = JSON.stringify(user);
  console.log("Post Request:" + json_user)
  $.ajax({
    url: 'https://',
    data: json_user,
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

