<?php

session_start();

include("includes/db.php");
include("securityFinance.php");
?>

<?php

$admin_session = $_SESSION['admin_email'];

$get_admin = "select * from admins  where admin_email='$admin_session'";

$run_admin = mysqli_query($con,$get_admin);

$row_admin = mysqli_fetch_array($run_admin);

$admin_id = $row_admin['admin_id'];

$admin_name = $row_admin['admin_name'];

$admin_email = $row_admin['admin_email'];

$admin_image = $row_admin['admin_image'];

$admin_job = $row_admin['admin_job'];

$admin_contact = $row_admin['admin_contact'];

$admin_about = $row_admin['admin_about'];


$get_products = "select * from product";
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


<!DOCTYPE html>
<html>

<head>

<title>Mamba Admin Panel</title>

<link href="css/bootstrap.min.css" rel="stylesheet">

<link href="css/style.css" rel="stylesheet">

<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" >
<link rel="shortcut icon" href="//cdn.shopify.com/s/files/1/2484/9148/files/SDQSDSQ_32x32.png?v=1511436147" type="image/png">

</head>

<body>

<div id="wrapper"><!-- wrapper Starts -->

<?php include("includes/sidebar Finance.php");  ?>

<div id="page-wrapper"><!-- page-wrapper Starts -->

<div class="container-fluid"><!-- container-fluid Starts -->

<?php

if(isset($_GET['dashboardFINANCE'])){

include("dashboardFINANCE.php");

}


if(isset($_GET['view_orders'])){

include("view_orders.php");

}


if(isset($_GET['view_approved_orders'])){

    include("view_approved_orders.php");

}

if(isset($_GET['view_assigned_orders'])){

    include("view_assigned_orders.php");

}


if(isset($_GET['view_cancelled_orders'])){

    include("view_cancelled_orders.php");

}



if(isset($_GET['order_delete'])){

include("order_delete.php");

}


if(isset($_GET['view_payments'])){

include("view_payments.php");

}

if(isset($_GET['payment_delete'])){

include("payment_delete.php");

}

if(isset($_GET['insert_bundle'])){

    include("insert_bundle.php");
    
    }

    if(isset($_GET['view_bundles'])){

        include("view_bundles.php");
        
        }

if(isset($_GET['edit_contact_us'])){

include("edit_contact_us.php");

}

if(isset($_GET['edit_order'])){

    include("edit_order.php");

}


if(isset($_GET['edit_assigned_order'])){

    include("edit_assigned_order.php");

}


if(isset($_GET['edit_cancelled_order'])){

    include("edit_cancelled_order.php");

}



if(isset($_GET['edit_completed_order'])){

    include("edit_completed_order.php");

}


if(isset($_GET['insert_enquiry'])){

include("insert_enquiry.php");

}


if(isset($_GET['view_enquiry'])){

include("view_enquiry.php");

}

if(isset($_GET['delete_enquiry'])){

include("delete_enquiry.php");

}

if(isset($_GET['edit_enquiry'])){

include("edit_enquiry.php");

}


if(isset($_GET['edit_about_us'])){

include("edit_about_us.php");

}

?>

</div><!-- container-fluid Ends -->

</div><!-- page-wrapper Ends -->

</div><!-- wrapper Ends -->

<script src="js/jquery.min.js"></script>

<script src="js/bootstrap.min.js"></script>


</body>


</html>

<?php ?>