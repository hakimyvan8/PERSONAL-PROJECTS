<?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();
$orderid=$_POST["orderid"];

$querry = mysqli_query($conn,"select order_item.orderId,order_item.price,order_item.quantity,order_item.content,order_item.createdAt,product.product_img1,product.title from order_item inner JOIN product on order_item.productId = product.id where order_item.orderId=$orderid;");

while ($row = mysqli_fetch_assoc($querry)) {
$emparray[] = $row;
}
echo json_encode(array( "status" => "true","message" => "Data fetched successfully!","data" => $emparray) );
?>

