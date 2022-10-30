<?php
  require_once 'db_connect.php';
  $db = new DB_Connect();
  $conn = $db->connect();


   $quantity =(float) $_POST['quantity'];
   $product_title =(string) $_POST['product_title'];
   $product_price=(int) $_POST['product_price'];
   $id = $_POST['userid'];
   $product_id = (int)$_POST['productid'];


 

     $stmt = $conn->prepare("select * from cart_item where id =$product_id");
      if( $stmt->execute()){

          $stmt->store_result();
          if($stmt->num_rows == null) {


              $cartid =0;
              $price = get_price($product_id,$conn,$quantity);
          


          }

          else

          {
           


              $response['error'] = "false";
              $response['message'] = "cart updated";

          }

     }

   
function get_price($product_id ,$conn,$quantity){



       $getprice = $conn->prepare("select price from product where id =$product_id");

       $getprice->execute();
    $price =0;
    $getprice->bind_result($price);
       $getprice->fetch();


       $actual_price = $price * $quantity;
       return (float)$actual_price;
}



echo json_encode($response);
?>
