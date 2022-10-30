<?php


if(!isset($_SESSION['admin_email'])){

echo "<script>window.open('login.php','_self')</script>";

}

else {

?>

<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
<script>tinymce.init({ selector:'textarea' });</script>
  
<?php

if(isset($_GET['edit_store'])){

$edit_id = $_GET['edit_store'];

$get_store = "select * from driver where driverID='$edit_id'";

$run_store = mysqli_query($con,$get_store);

$row_store = mysqli_fetch_array($run_store);

$driverID = $row_store['driverID'];

$FullName = $row_store['FullName'];

$phone = $row_store['phone'];

$NumberPlate = $row_store['NumberPlate'];

$DriverLicense = $row_store['DriverLicense'];

$admin_email = $row_store['admin_email'];

}

?>  

<div class="row" ><!-- 1 row Starts -->

<div class="col-lg-12" ><!-- col-lg-12 Starts --> 

<ol class="breadcrumb"><!-- breadcrumb Starts -->

<li class="active">

<i class="fa fa-dashboard" ></i> Dashboard / Edit Driver

</li>

</ol><!-- breadcrumb Ends -->

</div><!-- col-lg-12 Ends --> 

</div><!-- 1 row Ends -->

<div class="row"><!-- 2 row Starts -->

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<div class="panel panel-default"><!-- panel panel-default Starts -->

<div class="panel-heading"><!-- panel-heading Starts -->

<h3 class="panel-title">

<i class="fa fa-money fa-fw"></i> Edit Driver

</h3>

</div><!-- panel-heading Ends -->

<div class="panel-body"><!-- panel-body Starts -->

<form class="form-horizontal" action="" method="post" enctype="multipart/form-data"><!-- form-horizontal Starts -->

<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label"> Driver Fullname : </label>

<div class="col-md-6">

<input type="text" name="FullName" class="form-control" value="<?php echo $FullName; ?>">

</div>

</div><!-- form-group Ends -->




<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label"> Phone : </label>

<div class="col-md-6">

<input type="text" name="phone" class="form-control" value="<?php echo $phone; ?>">

</div>

</div><!-- form-group Ends -->

    


<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label"> Number Plate : </label>

<div class="col-md-6">

<input type="text" name="NumberPlate" class="form-control" value="<?php echo $NumberPlate; ?>">

</div>

</div><!-- form-group Ends -->



<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label"> Driver License : </label>

<div class="col-md-6">

<input type="text" name="DriverLicense" class="form-control" value="<?php echo $DriverLicense; ?>">

</div>

</div><!-- form-group Ends -->
    
    


<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label"> Driver Email : </label>
s
<div class="col-md-6">

<input type="text" name="admin_email" class="form-control" value="<?php echo $admin_email; ?>">

</div>

</div><!-- form-group Ends -->
    
    
    
<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label"> </label>

<div class="col-md-6">

<input type="submit" name="update" value="Update store" class="btn btn-primary form-control">

</div>

</div><!-- form-group Ends -->


</form><!-- form-horizontal Ends -->

</div><!-- panel-body Ends -->

</div><!-- panel panel-default Ends -->

</div><!-- col-lg-12 Ends -->

</div><!-- 2 row Ends -->

<?php

if(isset($_POST['update'])){

$FullName = $_POST['FullName'];

$phone = $_POST['phone'];

$NumberPlate = $_POST['NumberPlate'];

$DriverLicense = $_POST['DriverLicense'];

$admin_email = $_POST['admin_email'];


$update_store = "update driver set FullName='$FullName',phone='$phone',NumberPlate='$NumberPlate',DriverLicense='$DriverLicense',admin_email='$admin_email' where driverID='$driverID'";

$run_store = mysqli_query($con,$update_store);

if($run_store){

echo "<script>alert('One Driver Column Has Been Updated')</script>";

echo "<script>window.open('index.php?view_store','_self')</script>";

}

}

?>

<?php } ?>