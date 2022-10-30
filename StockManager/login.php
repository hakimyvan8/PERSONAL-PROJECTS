<?php

session_start();

include("includes/db.php");

?>
    <!DOCTYPE HTML>
    <html>

    <head>

        <title>Admin Login</title>

        <link rel="stylesheet" href="css/bootstrap.min.css" >

        <link rel="stylesheet" href="css/login.css" >

    </head>

    <body>

    <div class="container" ><!-- container Starts -->

        <form class="form-login" action="" method="post" ><!-- form-login Starts -->

            <h2 class="form-login-heading" >Mamba Finance Manager Login</h2>

            <input type="text" class="form-control" name="admin_email" placeholder="Email Address" required >

            <input type="password" class="form-control" name="admin_pass" placeholder="Password" required >

            <button class="btn btn-lg btn-primary btn-block" type="submit" name="admin_login" >

                Log in

            </button>


        </form><!-- form-login Ends -->

    </div><!-- container Ends -->



    </body>

    </html>

<?php

if(isset($_POST['admin_login'])){

    $admin_email = mysqli_real_escape_string($con,$_POST['admin_email']);

    $admin_pass = mysqli_real_escape_string($con,$_POST['admin_pass']);

    $get_admin = "select * from admins where admin_email='$admin_email' AND admin_pass='$admin_pass'";

    $run_admin = mysqli_query($con,$get_admin);

    $usertypes = mysqli_fetch_array($run_admin);

     if($usertypes['admin_job']=="Finance"){
        $_SESSION['admin_email']=$admin_email;
        echo "<script>alert('You are Logged in into admin panel')</script>";

        echo "<script>window.open('indexFINANCE.php?dashboardFINANCE','_self')</script>";
    }
    else {

        echo "<script>alert('Email or Password is Wrong')</script>";

    }

}

?>