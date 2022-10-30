<?php



if(!isset($_SESSION['admin_email'])){

echo "<script>window.open('login.php','_self')</script>";

}

else {

?>


<div class="row"><!--  1 row Starts -->

<div class="col-lg-12" ><!-- col-lg-12 Starts -->

<ol class="breadcrumb" ><!-- breadcrumb Starts -->

<li class="active" style="font-size: large">

 <b>View Shipment Details </b>

</li>

</ol><!-- breadcrumb Ends -->

</div><!-- col-lg-12 Ends -->

</div><!--  1 row Ends -->

<div class="row" ><!-- 2 row Starts -->

<div class="col-lg-12" ><!-- col-lg-12 Starts -->

<div class="panel panel-default" ><!-- panel panel-default Starts -->

<div class="panel-heading" ><!-- panel-heading Starts -->

<h3 class="panel-title" ><!-- panel-title Starts -->

<b>  Shipment Details </b>

</h3><!-- panel-title Ends -->


</div><!-- panel-heading Ends -->

<div class="panel-body" ><!-- panel-body Starts -->

<div class="table-responsive" ><!-- table-responsive Starts -->

<table class="table table-bordered table-hover table-striped" ><!-- table table-bordered table-hover table-striped Starts -->

<thead>

<tr>
<th>Driver ID</th>
<th>Full Name</th>
<th>Phone</th>
<th>Number Plate</th>
<th>Driver License</th>
<th>Driver Email </th> 
<th> Delete</th>
<th> Edit</th>



</tr>

</thead>

<tbody>

<?php

$i = 0;

$get_store = "select * from driver ";

$run_store = mysqli_query($con,$get_store);

while($row_store=mysqli_fetch_array($run_store)){

$driverId = $row_store['driverID'];

$FullName = $row_store['FullName'];

$phone = $row_store['phone'];

$NumberPlate = $row_store['NumberPlate'];
    
$DriverLicense = $row_store['DriverLicense'];

$admin_email = $row_store['admin_email'];


$i++;

?>

<tr>

<td><?php echo $i; ?></td>

<td><?php echo $FullName; ?></td>

<td> <?php echo $phone; ?></td>

<td><?php echo $NumberPlate; ?> </td>
    
<td><?php echo $DriverLicense; ?> </td>

<td><?php echo $admin_email; ?> </td>

<td>

<a href="index.php?delete_store=<?php echo $driverId ; ?>">

<i class="fa fa-trash-o"> </i> Delete

</a>

</td>

<td>

<a href="index.php?edit_store=<?php echo $driverId ; ?>">

<i class="fa fa-pencil"> </i> Edit

</a>

</td>

</tr>

<?php } ?>


</tbody>


</table><!-- table table-bordered table-hover table-striped Ends -->

</div><!-- table-responsive Ends -->

</div><!-- panel-body Ends -->

</div><!-- panel panel-default Ends -->

</div><!-- col-lg-12 Ends -->

</div><!-- 2 row Ends -->




<?php } ?>