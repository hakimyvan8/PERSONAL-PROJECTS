<?php
require_once 'includes/db.php';

$user = $_POST["pid"];


$count = $con->prepare("DELETE FROM cart WHERE p_id=?");
$count->bind_param('s',$user);

$count ->execute();





$responses["response"] = "deleted";
echo json_encode($responses);