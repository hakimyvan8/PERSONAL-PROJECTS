<?php
session_start();

require_once ('includes/db.php'); 

$result=array();
$user_id = $_POST["userid"];
$response['error'] = false;
$response['message'] = 'processing data';

$cart = $con->prepare("SELECT id,status,createdAt,grandTotal,paymentsecreat FROM orders WHERE userId = $user_id ");
$cart ->execute();
$cart->bind_result($orderid,$status,$date,$price,$paymentsecreat);
$products = array();



while($cart->fetch()){

    $temp = array();

    $temp['orderid'] = $orderid;
    $temp['status'] = $status;
    $temp['date'] = $date;
    $temp['price']  = $price;
    $temp['transcode'] = $paymentsecreat;
    
    array_push($products,$temp);
}


$responses["products"] = $products;
echo json_encode($responses);

?>