<?php
require_once 'includes/db.php';


$q =  $_REQUEST["q"];



$update_product = "update orders set status=1 where id = $q";

$run_product = mysqli_query($con,$update_product);

if($run_product){
     $random = substr(base64_encode(sha1(mt_rand())), 4, 10);
     $notification = $con->prepare("select userId,province,city from orders where id =$q");
     $notification->execute();
     $notification->bind_result($id,$province,$city);
     $notification->fetch();
     $location = $province."-".$city;
      $notification->close();
      echo "Payment Aproval Notification Sent To user";
  
      if($id != null){
        $update = $con->prepare("INSERT INTO order_customer_tracking (Tracking_id,status,OrderNumber,timeUpdate,customer_id,notificationdelivery,location,drverid) VALUES ('$random',1,$q,now(),$id,'notdelivered','$location',5)");
     
    if($update->execute())
       {

     }



     }
    }
    

?> 