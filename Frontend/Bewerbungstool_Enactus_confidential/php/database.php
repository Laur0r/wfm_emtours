<?php
	
	//Deadline prüfen
	$deadline=mktime(23,59,0,04,19,2018);
	$message='Deine Bewerbung konnte nicht abgeschickt werden, da die Bewerbungsfrist abgelaufen ist. <br> Versuche es gerne beim nächsten Mal wieder!';
    
	if(!date(time())>$deadline){
    	$message='Deine Bewerbung wurde erfolgreich abgeschickt. <br> Vielen Dank für dein Interesse an Enactus Münster!';
		
		//Zugangsdaten zur Datenbank
		define('DB_HOST', 'mysql5.simeconomy.de');
		define('DB_NAME', 'db340951_1');
		define('DB_USER', 'db340951_1');
		define('DB_PASSWORD', 'Enactus2018!');

		//Verbindung zur Datenbank prüfen
		$link = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);

		if(!$link){
			die('Failed to connect: '. mysql_error());
		}

		$db_selected = mysql_select_db(DB_NAME, $link);

		if(!$db_selected){
			die('Cannot use ' . DB_NAME . ': ' . mysql_error());
		}

		//Werte aus dem Formular holen
		$firstname = $_POST['firstname'];
		$lastname = $_POST['lastname'];
		$birthday = $_POST['birthday'];
		$email = $_POST['email'];
		$phone = $_POST['phone'];
		$course = $_POST['course'];
		$semester = $_POST['semester'];
		$experience = $_POST['experience'];
		$reason = $_POST['reason'];
		$engagement = $_POST['engagement'];
		$time = $_POST['time'];
		$project = $_POST['project'];
		$otherproject = $_POST['otherproject'];
		$motivating = $_POST['motivating'];
		$invention = $_POST['invention'];
		$contact = $_POST['contact'];

		//Bild hochladen
		$photo = $firstname. $lastname. $_FILES["photo"]['name'];	
		$file = $_FILES['photo']['tmp_name'];
		list($x, $y) = getimagesize($file);
		// horizontal rectangle
		if ($x > $y) {
			$square = $y;              // $square: square side length
			$offsetX = ($x - $y) / 2;  // x offset based on the rectangle
			$offsetY = 0;              // y offset based on the rectangle
		}
		// vertical rectangle
		elseif ($y > $x) {
			$square = $x;
			$offsetX = 0;
			$offsetY = ($y - $x) / 2;
		}
		// it's already a square
		else {
			$square = $x;
			$offsetX = $offsetY = 0;
		}

		$tn = imagecreatetruecolor($square, $square);
		$image = imagecreatefromjpeg($file) ; 
		imagecopyresampled($tn, $image, 0, 0, $offsetX, $offsetY, $square, $square, $square, $square);

		if(!imagejpeg($tn, 'photos/'. $photo, 100)) {
			die('Could not upload file: ' . mysql_error());
		}


		//In Datenbank eintragen
		$sql = "INSERT INTO bewerbung_bewerber (firstname, lastname, birthday, email, phone, course, semester, photo, experience, reason, engagement, time, project, otherproject, motivating, invention, contact, submissiontime) VALUES ('$firstname', '$lastname', '$birthday', '$email', '$phone', '$course', '$semester', '$photo', '$experience', '$reason', '$engagement', '$time', '$project', '$otherproject', '$motivating', '$invention', '$contact', now())";

		if(!mysql_query($sql)){
			die('Error: ' . mysql_error());
		}
    }
?>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>Enactus Münster | Bewerbungstool</title>
    <link rel="stylesheet" href="../css/style.css">
	<link rel="stylesheet" href="../css/form-basic.css">
	<link rel="icon" href="../img/favicon.png">
  </head>
  <body>
    <div class="site">
	  <header>
      <div class="container">
        <div id="brandlogo">
			<a href="../index.html"><img src="../img/logo_enactus.png" width=150px></a>
		  </div>
      </div>
    </header>
	<main>
      <div class="main-content">
          <form class="form-basic" method="post" action="">
            <div class="form-title-row">
                <h1>Bewerbungsformular</h1>
            </div>
            <span><?php echo $message ?></span>
	    </form>
      </div>
		</main>
	  </div>
    <footer class="footer">
      <p>Enactus Münster e.V., Copyright &copy; 2018</p>
    </footer>
  </body>
</html>
