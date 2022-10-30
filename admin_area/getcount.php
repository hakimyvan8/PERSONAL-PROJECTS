<?php
require_once 'includes/db.php';
require_once 'Customer/db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();

    $user = $_POST["userid"];

    $getcart = $con->prepare("select id from cart where userId = 1");
    $getcart->execute();
    $getcart->bind_result($cartid);
    $getcart->fetch();
    $getcart->close();
    $count = $conn->prepare("select count(*)from cart_item where cartId=? and active =1");
    $count->bind_param('s',$cartid);

    $count ->execute();
    $count ->bind_result($counter);

    $count->fetch();



$responses["count"] = $counter;
echo json_encode($responses);
















