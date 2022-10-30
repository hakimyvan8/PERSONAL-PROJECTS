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
                                <th>User ID:</th>
                                <th>Order No</th>
                                <th>Shipping Cost</th>
                                <th>Total</th>
                                <th>Created At</th>
                                <th>Payment</th>
                                <th>Delete</th>
                                <th>View Order Details</th>
</tr>

</thead><!-- thead Ends -->


<tbody><!-- tbody Starts -->

<?php

$i = 0;

$get_orders = "select * from orders where status = 0 ";

$run_orders = mysqli_query($con,$get_orders);

while ($row_order = mysqli_fetch_array($run_orders)) {


    $uuid = $row_order['userId'];

    $order_no = $row_order['id'];

    $shipping = $row_order['shipping'];

    $Total = $row_order['grandTotal'];

    $createdAt= $row_order['createdAt'];

    $payment = $row_order['paymentsecreat'];

$i++;

?>

<tr>

<td><?php echo $i; ?></td>
                                    <td>
                                        <?php echo $uuid; ?>
                                    </td>

                                    <td><?php echo $order_no; ?></td>
                                    <td><?php echo $shipping; ?></td>
                                    <td><?php echo $Total; ?></td>
                                    <td><?php echo $createdAt; ?></td>
                                    <td><?php echo $payment; ?></td>

<a href="indexFINANCE.php?order_delete=<?php echo $order_no; ?>" >

<td>
<i class="fa fa-trash-o" ></i> Delete

</a>

</td>


    <td>
        <a href="indexFINANCE.php?edit_order=<?php echo $order_no; ?>">

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