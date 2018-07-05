<?php session_start();

$_SESSION["adults"] = $_POST["adults"];
$_SESSION["children"] = $_POST["children"];
$_SESSION["animals"] = $_POST["animals"];
$_SESSION["temperature"] = $_POST["temperature"];
$_SESSION["preference"] = $_POST["preference"];
$_SESSION["activities"] = $_POST["activities"];
$_SESSION["budget"] = $_POST["budget"];
$_SESSION["departure"] = $_POST["departure"];
$_SESSION["return"] = $_POST["return"];

?>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="./css/style.css">
  <link rel="stylesheet" href="./css/form-basic.css">
</head>
<body>
  <div class="form-basic">
    <h1 style="color: rgb(83,153,245);", align="center">emTours - Your booking portal!</h1>
</div>
<div class="form-basic">
  <h2> Please Verify your Information </h2>
<div class="view-row">
  <span> Name </span>
  <span class="view-field"><?php echo $_SESSION["fname"];?></span>
  <span> Last Name </span>
  <span class="view-field"><?php echo $_SESSION["lname"];?></span>
</div>
<div class="view-row">
  <span> Gender </span>
  <span class="view-field"><?php echo $_SESSION["gender"];?></span>
</div>

<div class="view-row">
  <span> Home Adress </span>
  <span class="view-field"><?php echo $_SESSION["adress"];?></span>
</div>
<div class="view-row">
  <span> ZIP Code </span>
  <span class="view-field"><?php echo $_SESSION["zip"];?></span>
</div>
<div class="view-row">
  <span> City </span>
  <span class="view-field"><?php echo $_SESSION["city"];?></span>
</div>
<div class="view-row">
  <span> Country </span>
  <span class="view-field"><?php echo $_SESSION["country"];?></span>
</div>
<div class="view-row">
  <span> Birthday </span>
  <span class="view-field"><?php echo $_SESSION["bday"];?></span>
</div>
<div class="view-row">
  <span> E-Mail Adress </span>
  <span class="view-field"><?php echo $_SESSION["email"];?></span>
</div>
<br><br>
<div class="view-row">
  <span> Number of Adults </span>
  <span class="view-field"><?php echo $_SESSION["adults"];?></span>
  <span> Number of Children </span>
  <span class="view-field"><?php echo $_SESSION["children"];?></span>
</div>
<div class="view-row">
  <span> Number of Animals </span>
  <span class="view-field"><?php echo $_SESSION["animals"];?></span>
</div>
<div class="view-row">
  <span> Climate Preference </span>
  <span class="view-field"><?php echo ucfirst($_SESSION["temperature"]);?></span>
</div>
<div class="view-row">
  <span> Experience Preference </span>
  <span class="view-field"><?php echo ucfirst($_SESSION["preference"]);?></span>
</div>
<div class="view-row">
  <span> Number of Activities </span>
  <span class="view-field"><?php echo $_SESSION["activities"];?></span>
</div>
<div class="view-row">
  <span> Budget </span>
  <span class="view-field"><?php echo ucfirst($_SESSION["budget"]);?></span>
</div>
<div class="view-row">
  <span> Departure Date </span>
  <span class="view-field"><?php echo $_SESSION["departure"];?></span>
  <span> Return Date </span>
  <span class="view-field"><?php echo $_SESSION["return"];?></span>
</div>
<br><br>
<form id="form3">
  <input type="button" value="Back" onclick="history.back()">
  <input type="button" value="Submit Request">
</form>



</div>


</body>
</html>
