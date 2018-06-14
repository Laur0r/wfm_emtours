<?php

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

	$id = $_GET['id'];

	$result = mysql_query('SELECT * FROM bewerbung_bewerber WHERE id="' . $id . '"');
	if (!$result) {
    	echo 'Konnte Abfrage nicht ausführen: ' . mysql_error();
    	exit;
	}
	
	$row = mysql_fetch_row($result);

	$format = 'Y-m-d';
	$date = DateTime::createFromFormat($format, $row[3]);

	$lastname = $row[1];
	$firstname = $row[2];
	$birthday = $date->format('d.m.Y');
	$email = $row[4];
	$phone = $row[5];
	$course = $row[6];
	$semester = $row[7];
	$photo = $row[8];
	$experience = $row[9];
	$reason = $row[10];
	$engagement = $row[11];
	$time = $row[12];
	$project = $row[13];
	$otherproject = $row[14];
	$motivating = $row[15];
	$invention = $row[16];
	$contact = $row[17];
?>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>Enactus Münster | Bewerbungstool</title>
	<link rel="stylesheet" href="../css/profile-basic.css">
	<link rel="stylesheet" href="../css/style.css">
	<link rel="icon" href="../img/favicon.png">
  </head>
  <body>
	  <div class="site">
	<header>
      <div class="container">
        <div id="brandlogo">
			<a href="../index.html"><img src="../img/logo_enactus.png" width=150px alt=""></a>
		  </div>
      </div>
    </header>
	<main>  
	<section id="smallshowcase">
      <div class="container">
      </div>
    </section>
	
	<div class="profilecontainer">
		<div class="profilemain">
			<div>
			  <div class="left">
				<div>
					<a href=<?php echo '"./photos/' . $photo . '"' ?>><img class="photo" alt="" src=<?php echo '"./photos/' . $photo . '"' ?>  /></a>
				</div>
				<h4 class="name"><?php echo $firstname . " " . $lastname ?></h4>
				<p class="info">Geboren am <?php echo $birthday ?></p>
				<p class="info"><?php echo $email ?></p>
				<p class="info">Handy: <?php echo $phone ?></p>
				<div class="stats">
					<div>
						<p class="number-stat"><?php echo $course ?></p>
						<p class="desc-stat"><?php echo $semester ?>. Semester</p>
					</div>
				</div>
				  <p class="quest">Welche Erfahrungen und Stärken zeichnen dich anderen gegenüber aus? (Auslandserfahrung, Engagement, Qualifikationen, Praktika, ...)</p>
				<p class="desc"><?php echo $experience ?></p>
				  <p class="quest">Warum möchtest du Mitglied bei Enactus Münster werden?</p>
				<p class="desc"><?php echo $reason ?></p>
				  <p class="quest">Engagierst du dich momentan bei anderen studentischen / gemeinnützigen Organisationen oder hast du dies in der Vergangenheit einmal gemacht?</p>
				<p class="desc"><?php echo $engagement ?></p>
				  <p class="quest">Wie viel Zeit kannst du pro Woche in die Team- und Projektarbeit investieren?</p>
				<p class="desc"><?php echo $time ?></p>
				  <p class="quest">Welches Projekt / Ressort hat dein Interesse geweckt und warum denkst du, dass du dafür das perfekte Mitglied bist?</p>
				<p class="desc"><?php echo $project ?></p>
				  <p class="quest">Welche anderen Projekte / Ressorts würden dich noch interessieren?</p>
				<p class="desc"><?php echo $otherproject ?></p>
				  <p class="quest">Wie kannst du Leute begeistern?</p>
				<p class="desc"><?php echo $motivating ?></p>
				  <p class="quest">Wenn du etwas erfinden könntest, was es noch nicht gibt, was wäre das?</p>
				<p class="desc"><?php echo $invention ?></p>
				  <p class="quest">Wie hast du von uns erfahren?</p>
				<p class="desclast"><?php echo $contact ?></p>
			 </div>
		  </div>
		</div>
	</div>
	</main>
	</div>
	<footer class="footer">
      <p>Enactus Münster e.V., Copyright &copy; 2018</p>
    </footer>
  </body>
</html>
