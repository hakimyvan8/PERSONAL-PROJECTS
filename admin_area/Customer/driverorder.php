<?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();
$orderid=$_POST["id"];

$querry = mysqli_query($conn,"SELECT order_customer_tracking.OrderNumber,order_customer_tracking.location,order_customer_tracking.status,users.firstName,users.lastName,users.mobile from order_customer_tracking inner join users on order_customer_tracking.customer_id = users.id where order_customer_tracking.drverid = $orderid  GROUP BY OrderNumber");

while ($row = mysqli_fetch_assoc($querry)) {
$emparray[] = $row;
}
echo json_encode(array( "status" => "true","message" => "Data fetched successfully!","data" => $emparray) );
?>

