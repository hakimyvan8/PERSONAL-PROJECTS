<?php
require_once 'includes/db.php';

$user = $_POST["pid"];


$count = $con->prepare("DELETE FROM cart_item WHERE productId=$user");
if($count->execute())

{

    $responses["message"] = "deleted";
    echo json_encode($responses);

}

