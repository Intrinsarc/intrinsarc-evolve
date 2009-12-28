<?php

$value = $_POST['value'];
$check_for = $_POST['check_for'];


if(strpos($check_for, "email") !== false){
	if (checkmail($value)){
	echo"true";
	}
}


if(strpos($check_for, "empty") !== false){
	if ($value != ""){
	echo"true";
	}
}



function checkmail($mailadresse){
	$email_flag=preg_match("!^\w[\w|\.|\-]+@\w[\w|\.|\-]+\.[a-zA-Z]{2,4}$!",$mailadresse);
	return $email_flag;
}

?>