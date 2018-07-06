$(document).ready(function(){
  $('.slider').slider('init', {'height' : 500, 'indicators' : false});
  $('select').material_select();
});
  
  $(window).on('load', function () {
	$(".se-pre-con").fadeOut("slow");;
});

$('.datepicker').pickadate({
  selectMonths: true, // Creates a dropdown to control month
  selectYears: 200 // Creates a dropdown of 15 years to control year
});

$(document).ready(function() {
  $('.content-link').click(function(e) {
    e.preventDefault();

    $('html, body').animate({
      scrollTop: $('#customer_information').offset().top
    }, 800);
  });
});