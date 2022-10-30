<?php
require_once 'includes/db.php';


$products = array();
$stmt = $con->prepare("SELECT product_id,product_title,product_img1,product_price,product_desc,unitsStored,RemainingUnits,status FROM products;");


$stmt ->execute();
$stmt -> bind_result($p_id,$p_title,$p_img,$p_price,$p_desc,$pro_units,$pro_RemUnits,$status);

$products = array();


while($stmt ->fetch()){

    $temp = array();


    $temp['product_id'] = $p_id;
    $temp['product_title'] = $p_title;
    $temp['product_img1'] = $p_img;
    $temp['product_price'] = $p_price;
    $temp['product_desc']=$p_desc;
    $temp['unitsStored'] = $pro_units;
    $temp['RemainingUnits'] = $pro_RemUnits;
    $temp['status'] = $status;

    array_push($products,$temp);


}
$response['products'] = $products;
echo json_encode($response);
?>