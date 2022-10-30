<?php



if(!isset($_SESSION['admin_email'])){

echo "<script>window.open('login.php','_self')</script>";

}

else {

?>

<?php

if(isset($_GET['customer_approve'])){

$approve_id = $_GET['customer_approve'];

$update_customer = "update users set status='approved'where id='$approve_id'";

$run_update = mysqli_query($con,$update_customer);


if($run_update){

echo "<script>alert('Customer Has Been Approved')</script>";

echo "<script>window.open('index.php?view_customers','_self')</script>";


}

}


?>




<?php } ?>