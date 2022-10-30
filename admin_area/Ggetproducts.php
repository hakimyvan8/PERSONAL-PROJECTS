<?php
require_once 'includes/db.php';
require_once 'Customer/db_connect.php';

$db = new DB_Connect();
$conn = $db->connect();

$products = array();
$stmt = $conn->prepare("SELECT id,title,product_img1,price,summary,quantity FROM product;");


$stmt ->execute();
$stmt -> bind_result($p_id,$p_title,$p_img,$p_price,$p_desc,$pro_units);

$products = array();


while($stmt ->fetch()){

    $temp = array();
    $temp['id'] = $p_id;
    $temp['title'] = $p_title;
    $temp['product_img1'] = $p_img;
    $temp['price'] = $p_price;
    $temp['summary']=$p_desc;
    $temp['quantity'] = $pro_units;
    array_push($products,$temp);
}
$response['products'] = $products;
echo json_encode($response);
?>