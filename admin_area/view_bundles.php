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
<th>shipment ID</th>
<th>District</th>
<th>Province</th>
<th>Distance</th>
<th> Delete</th>
<th> Edit</th>



</tr>

</thead>

<tbody>

<?php

$i = 0;

$get_pro = "select * from drop_location ";

$run_pro = mysqli_query($con,$get_pro);

while($row_pro=mysqli_fetch_array($run_pro)){

$disc_id = $row_pro['locationid'];

$loc_name = $row_pro['locationName'];

$loc_province = $row_pro['location_province'];

$loc_distance = $row_pro['distance_KM'];

$i++;

?>

<tr>

<td><?php echo $i; ?></td>

<td><?php echo $loc_name; ?></td>

<td> <?php echo $loc_province; ?></td>

<td><?php echo $loc_distance; ?> </td>

<td>

<a href="index.php?delete_bundle=<?php echo $disc_id ; ?>">

<i class="fa fa-trash-o"> </i> Delete

</a>

</td>

<td>

<a href="index.php?edit_bundle=<?php echo $disc_id ; ?>">

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