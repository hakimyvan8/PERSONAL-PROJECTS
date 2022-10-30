
<?php
require_once 'includes/db.php';


$q = $_REQUEST["q"];
$orderid = $_REQUEST["orderid"];
$update_product = $con->prepare("select driverID from driver where phone like '%$q'");

if($update_product->execute())
{
$update_product->bind_result( $id);
$update_product->fetch();
echo $q."".$id;

$update_product->free_result();

if($id != null){

 // $con->close();/////get this code/////copyfromhere
 $get = $con->prepare("SELECT Tracking_id,OrderNumber,customer_id,location FROM order_customer_tracking WHERE OrderNumber=$orderid");
 $get->execute();
 $get->bind_result($Tracking_id,$OrderNumber,$customer_id,$location);
 $get->store_result();
 $get->fetch();

  $get->close();
$run_product = $con->prepare("INSERT INTO order_customer_tracking(Tracking_id,status,OrderNumber,timeUpdate,customer_id,notificationdelivery,location,drverid) VALUES ('$Tracking_id',2,$OrderNumber,now(),$customer_id,'notdelivered','$location',$id)");
$run_product->execute();



}

}

?> 