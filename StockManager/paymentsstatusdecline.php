
<?php
require_once 'includes/db.php';
$q = $_REQUEST["q"];



$update_product = "update orders set status=2 where id = $q";

$run_product = mysqli_query($con,$update_product);

if($run_product){
    $random = substr(base64_encode(sha1(mt_rand())), 4, 10);
    $notification = $con->prepare("select userId,province,city from orders where id =$q");
    $notification->execute();
  
    $notification->bind_result($id,$province,$city);
  
    $notification->fetch();
    $location = $province."-".$city;
     $notification->close();
     
 
     if($id != null){
     $update = $con->prepare("INSERT INTO order_customer_tracking (Tracking_id,status,OrderNumber,timeUpdate,customer_id,notificationdelivery,location) VALUES ('$random',5,$q,now(),$id,'notdelivered','$location')");
      if($update->execute())
      {
        $update_product = "update order_item set content='order declined' where orderId = $q";

        $run_product = mysqli_query($con,$update_product);

      }
     echo "declined status sent to user";


}
}
?> 