




<?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();
$orderid=  $_POST["orderid"];
$id  = $_POST["driverid"];

$get = $conn->prepare("SELECT Tracking_id,OrderNumber,customer_id,location FROM order_customer_tracking WHERE OrderNumber=$orderid");
$get->execute();
$get->bind_result($Tracking_id,$OrderNumber,$customer_id,$location);
$get->store_result();
$get->fetch();

 $get->close();
$run_product = $conn->prepare("INSERT INTO order_customer_tracking(Tracking_id,status,OrderNumber,timeUpdate,customer_id,notificationdelivery,location,drverid) VALUES ('$Tracking_id',4,$OrderNumber,now(),$customer_id,'notdelivered','$location',$id)");
$run_product->execute();

 if($run_product->execute()){
     $update =$conn->prepare("UPDATE orders set status = 4 ");
     $update->execute();
 }

echo json_encode(array( "status" => "true","message" => "Delivery Completed") );
?>