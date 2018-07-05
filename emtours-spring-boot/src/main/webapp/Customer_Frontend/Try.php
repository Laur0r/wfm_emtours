<?php session_start();?>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="./css/style.css">
  <link rel="stylesheet" href="./css/form-basic.css">
</head>
<body>
  <?php

    $_SESSION["fname"] = $_POST["fname"];
    $_SESSION["lname"] = $_POST["lname"];
    $_SESSION["adress"] = $_POST["adress"];
    $_SESSION["zip"] = $_POST["zip"];
    $_SESSION["city"] = $_POST["city"];
    $_SESSION["country"] = $_POST["country"];
    $_SESSION["bday"] = $_POST["bday"];
    $_SESSION["email"] = $_POST["email"];
  ?>
  <div class="form-basic">
    <h1 style="color: rgb(83,153,245);", align="center">emTours - Your booking portal!</h1>
    <p style="color: #555;", align="center">Find your perfect destination! Help us get to know you and fill out the following information
    </p>
    <div id="Chill-out" class="tabcontent">

      <h3>I want chilling!</h3>

      <p>Sun, sea, golden sand! Give your mind some break!</p>

      <img src="Meer1.jpg" alt="Meer" height="100" width="150">

    </div>
    <div id="Adventure" class="tabcontent">

      <h3>I want adventure!</h3>

      <p>Liberate your inner Indiana Jones and start the discovery of your lifetime!</p>
      <img src="Adventure.jpeg" alt="Adventure" height="100" width="150">

    </div>
    <div id="Romance" class="tabcontent">

      <h3>I want romance!</h3>

      <p>Fancy hotels, an ocean of flowers and candlelight awaits you.</p>
      <img src="Romance.jpeg" alt="Romance" height="100" width="150">
    </div>
    <div id="Action" class="tabcontent">

      <h3>I want action!</h3>

      <p>No matter how fast, or deep, or high. We get you there!</p>
<img src="Action.jpeg" alt="Action" height="100" width="150">
    </div>
    <div id="Family" class="tabcontent">

      <h3>I want family!</h3>

      <p>Spend some quality time with your loved ones</p>
<img src="Family.jpeg" alt="Family" height="100" width="150">
    </div>
    <button class="tablink" onclick="openCity('Chill-out', this, '#188DF7')" id="defaultOpen">Chill-out</button>
    <button class="tablink" onclick="openCity('Adventure', this, '#cc6b00')">Adventure</button>

    <button class="tablink" onclick="openCity('Romance', this, '#bd0000')">Romance</button>

    <button class="tablink" onclick="openCity('Action', this, '#217521')">Action</button>

    <button class="tablink" onclick="openCity('Family', this, '#ff5c00')">Family</button>
    <br><br><br><br><br><br>
    <form id="form2", method="post" action="submit2.php">
    <h2> Who is travelling? </h2>
    <div class = "form-row-multiple">
      <label>
        <span>Number of Adults</span>
        <input type = "number" name="adults" required min="1" max ="4" value="1">
        <span>Number of Children</span>
        <input type = "number" name="children" required min="0" max = "4"  value="0">
      </label>
    </div>
    <div class="form-row-multiple">
      <label>
        <div class="tooltip">
        <span>Number of Animals</span><span class="tooltiptext"> Only cats and dogs allowed </span>
      </div>
        <input type = "number" name="animals" required min="0" max = "3" value="0">
      </label>
    </div>
    <br><br>
    <h2> What are your climate preferences? </h2>
    <div class = "form-row">
      <input type = "radio" name="temperature" value="cold"><span>I want it cold</span>
      <input type = "radio" name = "temperature" value="moderate" checked><span>I want it moderate</span>
      <input type="radio" name="temperature" value="temperature"><span>I want it hot</span>
    </div>
    <br><br>

      <h2> What do you want to experience? </h2>
        <div class="form-row">
          <input type="radio" name="preference" value="chilling" checked><span> Chilling </span>
          <input type="radio" name="preference" value="adventure"><span> Adventure</span>
          <input type="radio" name="preference" value="romance"><span> Romance </span>
          <input type="radio" name="preference" value="action"><span> Action </span>
          <input type="radio" name="preference" value="family"><span> Family </span>
        </div>
        <br><br>
        <h2> How many activities do you want to do? </h2>

        <div class="form-row-multiple">
          <label>
            <span> Number of activities </span>
            <input type="number" min="1" max="100" name="activities" required value="1">
            <label>Activities are provided by our trusted partner FunSpark</label>
          </label>
        </div>
        <br><br>
  <h2> My budget for this travel is ... </h2>
  <div class="form-row">
    <input type="radio" name="budget" value="low" checked><span> smaller than 500 € p.P. </span>
    <input type="radio" name="budget" value="medium"><span> between 500 and 2.000 € p.P. </span>
    <input type="radio" name="budget" value="high"><span> greater than 2.000 € p.P. </span>
  </div>

  <h2> When do you want to travel? </h2>
  <div class="form-row-multiple">
    <label>
      <span> Departure Date </span>
      <input type="date" name="departure"required>
      <span> Return Date </span>
      <input type="date" name="return"required>
    </label>
  </div>
  <br><br>
  <input type="button" value="Back" onclick="history.back()">
  <input type="submit" value="Next"/>
</form>
</div>
  <script>function openCity(cityName,elmnt,color) {    var i, tabcontent, tablinks;    tabcontent = document.getElementsByClassName("tabcontent");    for (i = 0; i < tabcontent.length; i++) {        tabcontent[i].style.display = "none";    }    tablinks = document.getElementsByClassName("tablink");    for (i = 0; i < tablinks.length; i++) {        tablinks[i].style.backgroundColor = "";    }    document.getElementById(cityName).style.display = "block";    elmnt.style.backgroundColor = color;}// Get the element with id="defaultOpen" and click on itdocument.getElementById("defaultOpen").click();
</script>
</body>
</html>
