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

    $stmt2 = $conn->prepare("INSERT INTO cart (userid, createdAt) SELECT '$id', 'now()' FROM DUAL WHERE NOT EXISTS (SELECT * FROM cart WHERE userId=$id)");
    $stmt2->execute();
    $stmt = $conn->prepare("select * from cart_item where productId =$product_id");
     if( $stmt->execute()){

         $stmt->store_result();
         if($stmt->num_rows == null) {


             $cartid =35;
             $price = get_price($product_id,$conn,$quantity);
             $getcartid = $conn->prepare("select id from cart where userId =$id");
             $getcartid->execute();
             $getcartid->bind_result($crtid);
             $getcartid->fetch();
             $getcartid->close();
             $addtocart = $conn->prepare("insert into cart_item(cartId,productId,price,active,quantity) values (?,?,?,1,?)");
             $addtocart->bind_param("ssss",$crtid,$product_id,$price,$quantity);
             if($addtocart->execute())

             {
                 $response['error'] = "false";
                 $response['message'] = "added to cart";
             }


         }

         else

         {
           $stmt = $conn->prepare("select quantity from product where id =$product_id");
             
           $cartid =0;
             $price = get_price($product_id,$conn,$quantity);
             $addtocart = $conn->prepare( "UPDATE cart_item SET price=$price WHERE id=$product_id");
             if($addtocart->execute())


             $response['error'] = "false";
             $response['message'] = "cart updated";

         }

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





