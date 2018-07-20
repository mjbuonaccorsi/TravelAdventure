<?php

	if (!isset($_SESSION['userid'])) {
    echo '<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">';
    echo '<h5 class="my-0 mr-md-auto font-weight-normal">Travel Adventure</h5>';
   echo '  <div class="nav-login">';
   echo '  <form>';
   echo '<input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus autocomplete="off">';
    echo '<input type="password" id="inputPassword" class="form-control" placeholder="Password" required autocomplete="off">';
   
     echo '  <a class="btn btn-outline-primary" href="#">Log In</a>';
    echo '  <a class="p-2 text-dark" href="signup.php">Sign Up</a>';
   echo '  </form>';
    echo '  </div>';
   echo '</div>';

	} else {
    echo '<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">';
    echo '<h5 class="my-0 mr-md-auto font-weight-normal">Travel Adventure</h5>';
    echo '  <nav class="my-2 my-md-0 mr-md-3">';
    echo '    <a class="p-2 text-dark" href="#">New Adventures</a>';
    echo '    <a class="p-2 text-dark" href="#">Enterprise</a>';
    echo '    <a class="p-2 text-dark" href="#">Support</a>';
    echo '    <a class="p-2 text-dark" href="#">Pricing</a>';
    echo '  </nav>';
    echo '  <a class="btn btn-outline-primary" href="#">Log Out</a>';
    echo '  <a class="p-2 text-dark" href="#">Profile</a>';
    echo '</div>';
	}

