<?php


if(!isset($_SESSION['admin_email'])){

echo "<script>window.open('login.php','_self')</script>";

}

else {

?>

<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
  <script>tinymce.init({ selector:'textarea' });</script>

<div class="row" ><!-- 1 row Starts -->

<div class="col-lg-12" ><!-- col-lg-12 Starts --> 

<ol class="breadcrumb"><!-- breadcrumb Starts -->

<li class="active">

<i class="fa fa-dashboard" ></i> Dashboard / Insert Driver

</li>

</ol><!-- breadcrumb Ends -->

</div><!-- col-lg-12 Ends --> 

</div><!-- 1 row Ends -->

<div class="row"><!-- 2 row Starts -->

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<div class="panel panel-default"><!-- panel panel-default Starts -->

<div class="panel-heading"><!-- panel-heading Starts -->

<h3 class="panel-title">

<i class="fa fa-money fa-fw"></i> Insert Driver

</h3>

</div><!-- panel-heading Ends -->

<div class="panel-body"><!-- panel-body Starts -->

<form class="form-horizontal" action="" method="post" enctype="multipart/form-data"><!-- form-horizontal Starts -->

<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label"> Driver Full Name : </label>

<div class="col-md-6">

<input type="text" name="FullName" class="form-control">

</div>

</div><!-- form-group Ends -->

    
<div class="panel-body"><!-- panel-body Starts -->

<form class="form-horizontal" action="" method="post" enctype="multipart/form-data"><!-- form-horizontal Starts -->

<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label"> Driver Phone Number : </label>

<div class="col-md-6">

<input type="text" name="phone" class="form-control">

</div>

</div><!-- form-group Ends -->

    
    
<div class="panel-body"><!-- panel-body Starts -->

<form class="form-horizontal" action="" method="post" enctype="multipart/form-data"><!-- form-horizontal Starts -->

<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label"> Driver Password : </label>

<div class="col-md-6">

<input type="text" name="password" class="form-control">

</div>

</div><!-- form-group Ends -->
    
    

<div class="panel-body"><!-- panel-body Starts -->

<form class="form-horizontal" action="" method="post" enctype="multipart/form-data"><!-- form-horizontal Starts -->

<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label"> Driver Email : </label>

<div class="col-md-6">

<input type="text" name="DriverEmail" class="form-control">

</div>

</div><!-- form-group Ends -->

    
<div class="panel-body"><!-- panel-body Starts -->

<form class="form-horizontal" action="" method="post" enctype="multipart/form-data"><!-- form-horizontal Starts -->

<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label"> Driver Plate Number : </label>

<div class="col-md-6">

<input type="text" name="NumberPlate" class="form-control">

</div>

</div><!-- form-group Ends -->
    
    
    
<div class="panel-body"><!-- panel-body Starts -->

<form class="form-horizontal" action="" method="post" enctype="multipart/form-data"><!-- form-horizontal Starts -->

<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label"> Driver License Number : </label>

<div class="col-md-6">

<input type="text" name="DriverLicense" class="form-control">

</div>

</div><!-- form-group Ends -->
    
    

    

<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label"> </label>

<div class="col-md-6">

<input type="submit" name="submit" value="Insert Driver" class="btn btn-primary form-control">

</div>

</div><!-- form-group Ends -->


</form><!-- form-horizontal Ends -->

</div><!-- panel-body Ends -->


<?php
if(isset($_POST['submit'])){
$FullName = $_POST['FullName'];
$phone = $_POST['phone'];
$password = $_POST['password'];
$NumberPlate = $_POST['NumberPlate'];
$DriverEmail = $_POST['DriverEmail'];
$DriverLicense = $_POST['DriverLicense'];

$insert_driver= "insert into driver (FullName,phone,NumberPlate,DriverLicense,password,admin_email) values ('$FullName','$phone','$NumberPlate','$DriverLicense','$password','$DriverEmail')";

$run_driver = mysqli_query($con,$insert_driver);

if($run_driver){

echo "<script>alert('Driver has been inserted successfully')</script>";
    echo "<script>window.open('index.php?view_store','_self')</script>";
}

}

?>

<?php } ?>