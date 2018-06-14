<?php
	
	//Passwortabfrage
	$password = $_POST['password'];
	
	if($password != 'dudelsack' && $password != 'razzmatazz'){
		header('Location: ../team.html');
		exit();
	}
?>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>Enactus Münster | Bewerbungstool</title>
    <link rel="stylesheet" href="../css/style.css">
	<link rel="stylesheet" href="../css/list-basic.css">
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
	<section id="smallshowcase">
      <div class="container">
        <h1>Bewerbungen</h1>
      </div>
    </section>
	  
	<div class="table-users">
   		<table cellspacing="0">

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
			
    $result = mysql_query("SELECT * FROM bewerbung_bewerber");
	$format = 'Y-m-d H:i:s';

	while($row = mysql_fetch_array($result)){
		echo '<tr>';
		echo '<td><a href="applicant.php?id=' . $row['id'] . '"><img class="userphoto" src="./photos/' . $row['photo'] . '" alt="" /></a></td>';
		echo '<td>' . $row['firstname'] . ' ' . $row['lastname'] . '</td>';
		$date = DateTime::createFromFormat($format, $row['submissiontime']);
		$newdate = $date->format('d.m. H:i');
		echo '<td>' . $newdate . '</td>';
		echo '</tr>';
	}
			
	if($password == 'razzmatazz'){
		echo '<tr>';
		echo '<td><a href="csvexport.php"><img class="excelexp" src="../img/excel.png" alt="" /></a></td>';
		echo '<td>Als CSV herunterladen</td>';
		echo '</tr>';
	}		
?>
   		</table>
	</div>  
	</main>
	</div>
    <footer class="footer">
      <p>Enactus Münster e.V., Copyright &copy; 2018</p>
    </footer>
  </body>
</html>
