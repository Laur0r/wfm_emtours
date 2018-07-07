$(window).on('load', function () {
	$(".se-pre-con").fadeOut("slow");;
});

$(document).ready(function(){
    $('.modal').modal();
    $('select').material_select();
    $("#btn_send").click(function() {
        sendResponse();
    });
    $("#btn_sendAddInformation").click(function() {
        sendAddInformation();
    })
});

function sendResponse() {
    var urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('executionId')) {
        var executionId = urlParams.get('executionId');
    }

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
                document.getElementById("errorheader").innerHTML = "Something went wrong."
                $('#fail').modal('open');   
                if (jqxhr !== undefined) {
                    console.log(jqxhr);
                }
            }
        })
    } else {
        console.log("Empty or invalid input fields");
        document.getElementById("errorheader").innerHTML = "Missing Fields!";
        document.getElementById("errormessage").innerHTML = "Please fill select your response.";
        $('#fail').modal('open'); 
    }
};

function sendAddInformation() {
    var urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('executionId')) {
        var executionId = urlParams.get('executionId');
    }
    if (experience != undefined && experience != null && experience.value != ""
        && activities != undefined && activities != null && activities.value != "") {
        var additional_information = {
            "executionId": executionId,
            "numberActivities": document.getElementById('activities').value,
            "experienceType": document.getElementById('experience').value
        }
        var json_additional_information = JSON.stringify(additional_information);
        console.log("Post Request:" + additional_information)
        $.ajax({
            url: 'http://localhost:8080/furtherCustomerInformation',
            data: json_additional_information,
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
                document.getElementById("errorheader").innerHTML = "Something went wrong."
                $('#fail').modal('open');   
                if (jqxhr !== undefined) {
                    console.log(jqxhr);
                }
            }
        })   
    } else {
        console.log("Empty or invalid input fields");
        document.getElementById("errorheader").innerHTML = "Missing Fields!";
        document.getElementById("errormessage").innerHTML = "Please fill provide us with more information.";
        $('#fail').modal('open');
    }
   
}
