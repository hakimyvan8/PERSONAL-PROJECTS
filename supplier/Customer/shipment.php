<?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();


   $quantity =(float) $_POST['quantity'];
   $product_title =(string) $_POST['product_title'];
   $product_price=(int) $_POST['product_price'];
   $id = $_POST['userid'];
   $product_id = (int)$_POST['productid'];


   if($product_id != null && $id != null)

   {

     $stmt = $conn->prepare("select * from cart where p_id =$product_id");
      if( $stmt->execute()){

          $stmt->store_result();
          if($stmt->num_rows == null) {


              $cartid =0;
              $price = get_price($product_id,$conn,$quantity);
              $addtocart = $conn->prepare("insert into cart(cart_id,user_id,p_id,totalCpst,p_qty,p_title,p_NetPrice) values (?,?,?,?,?,?,?)");
              $addtocart->bind_param("sssssss",$cartid,$id,$product_id,$price,$quantity,$product_title,$product_price);
              if($addtocart->execute())

              {
                  $response['error'] = "false";
                  $response['message'] = "added to cart";
              }


          }

          else

          {
              $cartid =0;
              $price = get_price($product_id,$conn,$quantity);
              $addtocart = $conn->prepare( "UPDATE cart SET totalCpst=$price, p_qty=$quantity WHERE p_id=$product_id");
              if($addtocart->execute())


              $response['error'] = "false";
              $response['message'] = "cart updated";

          }

     }

   }

function get_price($product_id ,$conn,$quantity){



       $getprice = $conn->prepare("select product_price from products where product_id =$product_id");

       $getprice->execute();
    $price =0;
    $getprice->bind_result($price);
       $getprice->fetch();


       $actual_price = $price * $quantity;
       return (float)$actual_price;
}



echo json_encode($response);
?>
