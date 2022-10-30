<?php
require_once 'includes/db.php';


    $user = $_POST["userid"];


    $count = $con->prepare("select count(*)from cart where user_id=?");
    $count->bind_param('s',$user);

    $count ->execute();
    $count ->bind_result($counter);

    $count->fetch();



$responses["count"] = $counter;
echo json_encode($responses);
















