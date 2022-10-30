<?php

include("securityAdmin.php");
?>


<?php

$admin_session = $_SESSION['supplierEmail'];

$get_admin = "select * from supplier where supplierEmail='$admin_session'";

$run_admin = mysqli_query($con,$get_admin);

$row_admin = mysqli_fetch_array($run_admin);

$supplier_id = $row_admin['supId'];

$admin_name = $row_admin['supplierBusiness'];

$admin_email = $row_admin['supplierEmail'];

$admin_contact = $row_admin['supplierPhone'];


$get_products = "select * from supplierlisting where supplierId='$supplier_id' ";
$run_products = mysqli_query($con,$get_products);
$count_products = mysqli_num_rows($run_products);

$get_customers = "select * from users where status='pending'";
$run_customers = mysqli_query($con,$get_customers);
$count_customers = mysqli_num_rows($run_customers);

$get_p_categories = "select * from category";
$run_p_categories = mysqli_query($con,$get_p_categories);
$count_p_categories = mysqli_num_rows($run_p_categories);


$get_pending_orders = "select * from orders";
$run_pending_orders = mysqli_query($con,$get_pending_orders);
$count_pending_orders = mysqli_num_rows($run_pending_orders);

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
                                <th>Order No</th>
                                <th> Name</th>
                                <th> Phone</th>
                                <th>Created At</th>
                                <th>Status</th>
                                <th>Delete</th>
                                <th>View Order Details</th>
</tr>

</thead><!-- thead Ends -->


<tbody><!-- tbody Starts -->

<?php

$i = 0;

$get_orders = "select * from supplierorder where supplierId='$supplier_id' and status = 'pending' ";

$run_orders = mysqli_query($con,$get_orders);

while ($row_order = mysqli_fetch_array($run_orders)) {


    $order_no = $row_order['orderid'];

    $Name = $row_order['recipientName'];

    $phone = $row_order['recipientPhone'];

    $createdAt= $row_order['date'];
    
    $status= $row_order['status'];


$i++;

?>

<tr>

<td><?php echo $i; ?></td>
                                 

                                    <td><?php echo $order_no; ?></td>
                                    <td><?php echo $Name; ?></td>
                                    <td><?php echo $phone; ?></td>
                                    <td><?php echo $createdAt; ?></td>
                                    <td><?php echo $status; ?></td>
    

<a href="index.php?order_delete=<?php echo $order_no; ?>" >

<td>
<i class="fa fa-trash-o" ></i> Delete

</a>

</td>


    <td>
        <a href="index.php?edit_order=<?php echo $order_no; ?>">

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


<?php ?>