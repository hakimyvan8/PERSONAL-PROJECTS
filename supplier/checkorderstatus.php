<?php
require_once 'includes/db.php';

$id = $_POST["userid"];

$data = $con->prepare(" SELECT * FROM order_customer_tracking WHERE customer_id = $id and notificationdelivery ='notdelivered'");
$data->execute();
$data->bind_result($Tracking_id,$status,$OrderNumber,$timeUpdate,$customerid,$notificationdelivery,$number,$local,$driver);
$data->store_result();

while($data->fetch() > 0){

   
    $order = array(

        'Trackingid'=>$Tracking_id,
        'status'=>$status,
        'OrderNumber'=>$OrderNumber,
        'timeUpdate'=>$timeUpdate,
        'customerid'=>$customerid,
        'notificationdeliver'=>$notificationdelivery,
        'location'=>$local,
        'driver'=>$driver
         );
         $eitnotifyring = $con->prepare("UPDATE order_customer_tracking SET notificationdelivery = 'delivered' ");
         $eitnotifyring->execute();
        
    $response['error'] = false;
    $response['message'] = 'retrieved';
    $response['order'] = $order;
    echo json_encode($response);

    
}




?>