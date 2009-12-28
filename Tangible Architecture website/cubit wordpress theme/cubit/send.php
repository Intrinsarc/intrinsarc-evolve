<?php 

if(isset($_POST['Send'])){
	
		$the_name = $_POST['yourname'];
		$email = $_POST['email'];
		$website = $_POST['website'];
 		$to      =  $_POST['myemail'];
		$subject = "New Message from " . $_POST['myblogname'];
		$header  = 'MIME-Version: 1.0' . "\r\n";
		$header .= 'Content-type: text/html; charset=utf-8' . "\r\n";
		$header .= 'From:'. $_POST['email']  . " \r\n";
	
		$message1 = nl2br($_POST['message']);
		$message = "New message from  $the_name <br/>
		Mail: $email<br />
		Website: $website <br /><br />
		Message: $message1 <br />";
		
		mail($to,
		$subject,
		$message,
		$header); 
		
		echo"<p><h3>Your message has been sent!</h3> Thank you!</p>";
}
	?>