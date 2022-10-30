<?php


if(!isset($_SESSION['admin_email'])){

echo "<script>window.open('login.php','_self')</script>";

}

else {


?>

<div class="row"><!-- 1 row Starts -->

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<ol class="breadcrumb"><!-- breadcrumb Starts  --->

<li class="active">

 View Orders

</li>

    <div class="container py-5">

        <button onclick="window.print()" name="btn-btn-primary" class="btn">Print Out</button>

    </div>

</ol><!-- breadcrumb Ends  --->

</div><!-- col-lg-12 Ends -->

</div><!-- 1 row Ends -->


<div class="row"><!-- 2 row Starts -->

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<div class="panel panel-default"><!-- panel panel-default Starts -->

<div class="panel-heading"><!-- panel-heading Starts -->

<h3 class="panel-title"><!-- panel-title Starts -->

<i class="fa fa-money fa-fw"></i> View Orders

</h3><!-- panel-title Ends -->

</div><!-- panel-heading Ends -->

<div class="panel-body"><!-- panel-body Starts -->

<div class="table-responsive"><!-- table-responsive Starts -->

<table class="table table-bordered table-hover table-striped"><!-- table table-bordered table-hover table-striped Starts -->

<thead><!-- thead Starts -->

<tr>

    <th>No:</th>
    <th>Order No:</th>
    <th>Invoice:</th>
    <th>Name:</th>
    <th>State</th>
    <th>District</th>
    <th>Sector</th>
    <th>Order Status:</th>
    <th>Delete</th>
    <th>View Order Details</th>

</tr>

</thead><!-- thead Ends -->


<tbody><!-- tbody Starts -->

<?php

$i = 0;

$get_orders = "select * from orderdetails  order by 1 DESC LIMIT 0,5";

$run_orders = mysqli_query($con,$get_orders);

while ($row_order = mysqli_fetch_array($run_orders)) {

    $customerid = $row_order['customer_id'];

    $order_no = $row_order['orderNo'];

    $name = $row_order['firstname'];

    $Invoice = $row_order['invoiceNo'];

    $state = $row_order['state'];

    $district = $row_order['district'];

    $sector = $row_order['sector'];

    $order_status = $row_order['orderstatus'];

$i++;

?>

<tr>

<td><?php echo $i; ?></td>

    <td><?php echo $order_no; ?></td>

<td bgcolor="yellow" ><?php echo $Invoice; ?></td>

<td><?php echo $name; ?></td>

<td><?php echo $state; ?></td>

<td><?php echo $district; ?></td>

    <td><?php echo $sector; ?></td>

    <td bgcolor="#d3d3d3">
<?php

if($order_status=='pending'){

echo $order_status='pending';

}
else{

echo $order_status='Complete';

}


?>
</td>

<td>

<a href="indexFINANCE.php?order_delete=<?php echo $customerid; ?>" >

<i class="fa fa-trash-o" ></i> Delete

</a>

</td>


    <td>
        <a href="indexFINANCE.php?edit_completed_order=<?php echo $customerid; ?>">

            <i class="fa fa-eye"> </i> View


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


<?php } ?>