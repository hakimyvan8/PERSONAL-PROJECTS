<?php
require_once 'includes/db.php';

$stmt = $con->prepare( "SELECT p_cat_id,p_cat_title,catproduct_desc,p_cat_image FROM product_categories;");


$stmt ->execute();
$stmt -> bind_result($product_catid,$product_cattitle, $cat_details,$product_catimg);

$product_categories = array();

while($stmt ->fetch()){

    $temp = array();

    $temp['p_cat_id'] = $product_catid;
    $temp['p_cat_title'] = $product_cattitle;
    $temp['catproduct_desc'] = $cat_details;
    $temp['product_img1'] = $product_catimg;

    array_push($product_categories,$temp);


}
$response['product_categories'] = $product_categories;
echo json_encode($response);
?>