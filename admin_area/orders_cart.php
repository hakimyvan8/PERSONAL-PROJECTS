<?php
require_once "includes/db.php";

$price =(float)$_POST["totalpriceserver"];
$shippingfee = (float)$_POST["shippingfee"];
$userid =$_POST["userid"];
$pid    = $_POST["pid"];
$location =$_POST["location"];
$location_province =$_POST["location_province"];
$fname     = $_POST["fname"];
$lname     = $_POST["lname"];
$email     = $_POST["email"];
$number    = $_POST["number"];


$stmt = $con->prepare("insert into orders(userid,paymentsecreat,
shipping,grandTotal,createdAt,firstName,lastName,mobile,email,city,province,country) 
values ($userid,'$pid',$shippingfee,$price,now(),'$fname','$lname','$number','$email','$location_province','$location','Rwanda')");
$stmt->execute();


$stmt = $con->prepare("SELECT id from cart where userId=$userid");
$stmt->execute();
$stmt->bind_result($cartid);
$stmt->fetch();
$stmt->close();
//echo"working +$cartid";

$stmtsubtract = mysqli_query($con,"SELECT productId,quantity from cart_item where cartId=$cartid");
while($row = mysqli_fetch_assoc($stmtsubtract))
{
  $productid = $row['productId'];
  $productquantity = $row['quantity'];
  $getQuantity = $con->prepare("SELECT quantity from product where id = $productid");
  $getQuantity->execute();
  $getQuantity->bind_result($quantitygotten);
  $getQuantity->fetch();
  $getQuantity->close();
  $sub = $con->prepare("UPDATE product SET quantity= $quantitygotten - $productquantity Where id=$productid");
  $sub->execute();
  
}

$stmtupdateorderlist = $con->prepare("select id from orders where paymentsecreat='$pid'");
$stmtupdateorderlist->execute();
$stmtupdateorderlist->bind_result($order_id);
$stmtupdateorderlist->fetch();
$stmtupdateorderlist->close();

$stmt = $con->prepare("select id from cart where userId=$userid");
$stmt->execute();
$stmt->bind_result($cartId);
$stmt->fetch();
$stmt->close();
/////////////////////////////////////////////////////////

$stmtsubtract = mysqli_query($con,"select productId,price,quantity from cart_item where cartId=$cartId");
while($row = mysqli_fetch_assoc($stmtsubtract))
{
  $productid = $row['productId'];
  $quantity = $row['quantity'];
  $productquantity = $row['price'];
  $sub = $con->prepare("insert into order_item(productId,orderId,price,quantity,createdAt) values ($productid,$order_id,$productquantity,$quantity,now())");
  $sub->execute();
  
}
$up = mysqli_query($con,"UPDATE cart_item SET active = 0 WHERE cartId = $cartId ");
$up = mysqli_query($con,"DELETE FROM cart_item WHERE active = 0");
$sub = $con->prepare("insert into transaction(userId,orderId,code,mode,createdAt) values ($userid,$order_id,'$pid','visa/stripe',now())");
$sub->execute();
/////////////////////////////////////////////////////

$responses["message"] = "done!";
echo json_encode($responses);
?>