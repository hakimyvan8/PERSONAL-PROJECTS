<?php
require_once 'includes/db.php';
$result=array();
$user_id = '5';

$response['error'] = false;
$response['message'] = 'processing data';

$cart = $con->prepare("SELECT orders.id,orders.status,orders.province,orders.city,orders.firstName,orders.lastName,orders.mobile,orders.email,orders.createdAt
         FROM orders WHERE orders.userId = $user_id");
$cart ->execute();
$cart-> bind_result($orderid,$status,$province,$city,$firstname,$lastname,$mobile,$email,$createAt);
$products = array();
while($cart->fetch()){

    $temp = array();


    $_POST["userid"] = $user_id;
    $temp['orderid'] = $orderid;
    $temp['status'] = $status;
    $temp['province'] = $province;
    $temp['city'] = $city;
    $temp['firstname'] = $firstname;
    $temp['lastname'] = $lastname;
    $temp['mobile'] = $mobile;
    $temp['email'] = $email;
    $temp['createAt'] = $createAt;
    array_push($products,$temp);
}


$responses["products"] = $products;
echo json_encode($responses);
