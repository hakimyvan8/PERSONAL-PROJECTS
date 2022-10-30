<?php
require_once 'includes/db.php';

$result=array();
$user_id =$_POST["userid"];

$response['error'] = false;
$response['message'] = 'processing data';


$cart = $con->prepare("SELECT p_id, totalCpst, p_qty, p_title, p_NetPrice FROM cart where user_id='$user_id'");
$cart ->execute();
$cart-> bind_result($p_id,$totalCpst, $p_qty, $p_title, $p_NetPrice);
$products = array();
;
while($cart->fetch()){

    $temp = array();


    $_POST["userid"] = $user_id;
    $temp['product_id'] = $p_id;
    $temp['product_title'] = $p_title;
    $temp['product_totalcost'] = $totalCpst;
    $temp['product_quantity'] = $p_qty;
    $temp['product_netprice'] = $p_NetPrice;


    array_push($products,$temp);


}
$sum=$con->prepare("SELECT SUM(totalCpst) FROM cart where user_id=?;");
$sum->bind_param("s",$user_id);
$sum->execute();
$sum->bind_result($sumcount);
$sum->fetch();
$cart->close();

$responses["products"] = $products;
$responses["count"] = $sumcount;
echo json_encode($responses);


?>