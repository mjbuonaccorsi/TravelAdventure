<?php

if (!isset($_POST['submit'])) {
	header("Location: ../signup.php");
	exit();
} else {
		include_once 'repo.inc.php';
		$first = mysqli_real_escape_string($conn, $_POST['first']);
		$last = mysqli_real_escape_string($conn, $_POST['last']);
		$email = mysqli_real_escape_string($conn, $_POST['email']);
		$password = mysqli_real_escape_string($conn, $_POST['password']);
		$pwdhint = mysqli_real_escape_string($conn, $_POST['pwdhint']);
	
		//Error handlers
		//Check for empty fields
		if (empty($first) || empty($last) || empty($email) || empty($password) || empty($pwdhint)){
			header("Location: ../signup.php?signup-cd=empty");
			exit();
			
		} else {
			// check if Input characters are valid.
			if (!preg_match("/^[a-zA-Z]*$", $first) || !preg_match("/^[a-zA-Z]*$", $last)) {
				header("Location: ../signup.php?signup-cd=invalid");
				exit();
				
			} else {
				//Check for valid email
				if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
				header("Location: ../signup.php?signup-cd=email");
				exit();
				} else {
					$sql="";
				}
			}
		}
		
		exit();
}

