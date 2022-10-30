<?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();

$firstname = $_POST['firstname'];
$lastname = $_POST['lastname'];
$phone = $_POST['phone'];
$state = $_POST['state'];
$district = $_POST['district'];
$quantity =(float) $_POST['quantity'];
$product_title =(string) $_POST['product_title'];
$product_price=(int) $_POST['product_price'];
$id = $_POST['userid'];
$cart_id = (int)$_POST['cart_id'];
$unique_id = (int)$_POST['unique_id'];


$stmt = $conn->prepare("select * from pending_orders where cart_id =$cart_id");
$stmt->bind_param("s", $cart_id);
if( $stmt->execute()){

    $stmt->store_result();
    if($stmt->num_rows == null) {


        $orderid =0;
        $addtocart = $conn->prepare("insert into pending_orders(order_id,customer_id,qty,unique_orderid,firstname,lastname,phone,state,district,product_title,initialCost,cartid) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        $addtocart->bind_param("sssssssssssss",$orderid,$id,$price,$unique_id,$firstname,$lastname,$phone,$state,$district,$product_title,$product_price,$cart_id);
        if($addtocart->execute())

        {
            $response['error'] = "false";
            $response['message'] = "order added";
        }


    }

    else

    {
        $response['error'] = "true";
        $response['message'] = "Failed to Order";

    }

}
echo json_encode($response);
?>
