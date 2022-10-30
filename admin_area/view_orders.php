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

<i class="fa fa-dashboard"></i> Dashboard / View Orders

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
    
    <th>Order Status:</th>

</tr>

</thead><!-- thead Ends -->


<tbody><!-- tbody Starts -->

<?php

$i = 0;

$get_orders = "SELECT order_item.quantity,orders.userId,orders.status,order_item.price,product.title, orders.id,orders.shipping,orders.grandtotal,
orders.paymentsecreat,orders.province,orders.firstName,orders.lastName,orders.mobile,orders.email,orders.createdAt
 FROM order_item INNER JOIN orders on order_item.orderId = orders.id 
 LEFT JOIN product on order_item.productId = product.id ";

$run_orders = mysqli_query($con,$get_orders);

while ($row_order = mysqli_fetch_array($run_orders)) {

    $uuid = $row_order['userId'];

    $order_no = $row_order['id'];

    $name = $row_order['firstName'];

    $Invoice = $row_order['id'];

    $state = $row_order['province'];

    $order_status = $row_order['status'];

$i++;

?>

<tr>

<td><?php echo $i; ?></td>

    <td><?php echo $order_no; ?></td>

<td bgcolor="yellow" ><?php echo $Invoice; ?></td>

    <td><?php echo $name; ?></td>

    <td><?php echo $state; ?></td>

  

<td>
<?php

if($order_status== 0){

echo $order_status='pending';

}
else{

echo $order_status='Complete';

}


?>
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