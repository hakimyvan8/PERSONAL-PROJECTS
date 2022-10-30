<?php
require_once 'includes/db.php';
$result=array();
$user_id = $_POST["userid"];

$response['error'] = false;
$response['message'] = 'processing data';

$tempkgs = 0;
$final = 0;
$getcart = $con->prepare("select id from cart where userId = $user_id");
$getcart->execute();
$getcart->bind_result($cartid);
$getcart->fetch();
$getcart->close();
$cart = $con->prepare("SELECT cart_item.cartId,cart_item.productId, cart_item.price, cart_item.quantity, product.metaTitle,product.price,product.product_img1 FROM cart_item INNER JOIN product ON cart_item.productId = product.id where cart_item.cartId=$cartid and active = 1 ");
$cart ->execute();
$cart-> bind_result($cartid,$p_id,$totalCpst, $p_qty, $p_title,$netprice,$product_img1);
$products = array();
while($cart->fetch()){

    $temp = array();


    $_POST["userid"] = $user_id;
    $temp['cart_Id'] = $cartid;
    $temp['product_id'] = $p_id;
    $temp['product_title'] = $p_title;
    $temp['product_totalcost'] = $totalCpst;
    $temp['product_quantity'] = $p_qty;
    $temp['product_netprice'] = $netprice;
    $temp['product_img1'] = $product_img1;
    array_push($products,$temp);
    
    $myArr[] = (int)filter_var($p_title, FILTER_SANITIZE_NUMBER_INT) * $p_qty;

}



////////////////////////////calculating the sum
$sum=$con->prepare("SELECT SUM(price) FROM cart_item where cartId=?");
$sum->bind_param("s",$cartid);
$sum->execute();
$sum->bind_result($sumcount);
$sum->fetch();
$cart->close();
$responses["totalkgs"] = array_sum($myArr);
$responses["products"] = $products;
$responses["count"] = $sumcount;
echo json_encode($responses);


?>