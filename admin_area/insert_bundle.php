<?php

if(!isset($_SESSION['admin_email'])){

echo "<script>window.open('login.php','_self')</script>";

}

else {

?>
<!DOCTYPE html>

<html>

<head>

<title> Insert Bundle </title>


<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
  <script>tinymce.init({ selector:'#product_desc,#product_features' });</script>

</head>

<body>

<div class="row"><!-- row Starts -->

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<ol class="breadcrumb"><!-- breadcrumb Starts -->

<li class="active">

<i class="fa fa-dashboard"> </i> Dashboard / Insert Bundle

</li>

</ol><!-- breadcrumb Ends -->

</div><!-- col-lg-12 Ends -->

</div><!-- row Ends -->


<div class="row"><!-- 2 row Starts --> 

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<div class="panel panel-default"><!-- panel panel-default Starts -->

<div class="panel-heading"><!-- panel-heading Starts -->

<h3 class="panel-title">

<i class="fa fa-money fa-fw"></i> Insert Bundle

</h3>



</div><!-- panel-heading Ends -->

<div class="panel-body"><!-- panel-body Starts -->

<form class="form-horizontal" method="post" enctype="multipart/form-data"><!-- form-horizontal Starts -->

<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > State </label>

<div class="col-md-6" >

<input type="text" name="state" class="form-control" required >

</div>

</div><!-- form-group Ends -->


<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > District </label>

<div class="col-md-6" >

<input type="text" name="district" class="form-control" required >

</div>

</div><!-- form-group Ends -->


<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Distance </label>

<div class="col-md-6" >

<input type="text" name="distance" class="form-control" required >

</div>

</div><!-- form-group Ends -->



<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > National ID </label>

<div class="col-md-6" >

<input type="text" name="NationalId" class="form-control" required >

</div>

</div><!-- form-group Ends -->


    <div class="form-group" ><!-- form-group Starts -->

        <label class="col-md-3 control-label" ></label>

        <div class="col-md-6" >

            <input type="submit" name="submit" value="Insert Bundle" class="btn btn-primary form-control" >

        </div>

    </div><!-- form-group Ends -->


</form><!-- form-horizontal Ends -->

</div><!-- panel-body Ends -->

</div><!-- panel panel-default Ends -->

</div><!-- col-lg-12 Ends -->

</div><!-- 2 row Ends --> 




</body>

</html>

<?php

if(isset($_POST['submit'])){
$district = $_POST['district'];
$state = $_POST['state'];
$distance = $_POST['distance'];

$insert_location= "insert into drop_location (locationName,location_province,distance_KM) values ('$district','$state','$distance')";

$run_location = mysqli_query($con,$insert_location);

if($run_location){

echo "<script>alert('Shipment Cost has been inserted successfully')</script>";
    echo "<script>window.open('index.php?view_bundles','_self')</script>";
}

}

?>

<?php } ?>
