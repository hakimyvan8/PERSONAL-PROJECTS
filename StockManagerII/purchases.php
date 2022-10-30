<?php



if(!isset($_SESSION['admin_email'])){

echo "<script>window.open('login.php','_self')</script>";

}

else {

?>

<div class="row" ><!-- 1 row Starts -->

<div class="col-lg-12" ><!-- col-lg-12 Starts -->

<ol class="breadcrumb" ><!-- breadcrumb Starts -->

<li class="active" >

<i class="fa fa-dashboard" ></i> Dashboard / View Suppliers

</li>

</ol><!-- breadcrumb Ends -->


</div><!-- col-lg-12 Ends -->

</div><!-- 1 row Ends -->


<div class="row" ><!-- 2 row Starts -->

<div class="col-lg-12" ><!-- col-lg-12 Starts -->

<div class="panel panel-default" ><!-- panel panel-default Starts -->

<div class="panel-heading" ><!-- panel-heading Starts -->

<h3 class="panel-title" ><!-- panel-title Starts -->

<i class="fa fa-money fa-fw" ></i> View Supplier

</h3><!-- panel-title Ends -->


</div><!-- panel-heading Ends -->

<div class="panel-body" ><!-- panel-body Starts -->

<div class="table-responsive" ><!-- table-responsive Starts -->

<table class="table table-bordered table-hover table-striped" ><!-- table table-bordered table-hover table-striped Starts -->

<thead><!-- thead Starts -->

<tr>

    <th>Supplier ID:</th>

<th>TaxNos:</th>

<th>Supplier Email:</th>

    <th>Supplier Password:</th>

<th>Supplier State:</th>

    <th>Supplier District:</th>

<th>Supplier Business:</th>

<th>Supplier Phone:</th>

<th>Delete</th>

</tr>

</thead><!-- thead Ends -->

<tbody><!-- tbody Starts -->

<?php

$get_supplier = "select * from supplier";

$run_supplier = mysqli_query($con,$get_supplier);

while($row_admin = mysqli_fetch_array($run_supplier)){

$supplier_id = $row_admin['supplier_id'];

$TaxNo = $row_admin['TaxNo'];

$supplier_email = $row_admin['supplierEmail'];

$supplier_pass = $row_admin['supplierPass'];

$supplier_state = $row_admin['supplierState'];

$supplier_district = $row_admin['supplierDistrict'];

$supplier_Business = $row_admin['supplierBusiness'];

$supplier_phone = $row_admin['supplierPhone'];





?>

<tr>
    <td><?php echo $supplier_id; ?></td>

<td><?php echo $TaxNo; ?></td>

<td><?php echo $supplier_email; ?></td>

    <td><?php echo $supplier_pass; ?></td>

<td><?php echo $supplier_state; ?></td>

<td><?php echo $supplier_district; ?></td>

    <td><?php echo $supplier_Business; ?></td>

    <td><?php echo $supplier_phone; ?></td>

<td>

<a href="index.php?supplier_delete=<?php echo $supplier_id; ?>" >

<i class="fa fa-trash-o" ></i> Delete

</a>

</td>


</tr>


<?php } ?>

</tbody><!-- tbody Ends -->



</table><!-- table table-bordered table-hover table-striped Ends -->

</div><!-- table-responsive Ends -->


</div><!-- panel-body Ends -->


</div><!-- panel panel-default Ends -->


</div><!-- col-lg-12 Ends -->



</div><!-- 2 row Ends -->





<?php }  ?>