 <?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();

$userid =  $_POST['userid'];
$querry = mysqli_query($conn,"INSERT INTO orders(userId,sessionId,total,createdAt)
SELECT iduser,iduser,price,createdAt
FROM cart_item WHERE iduser =$userid " );

$querry = mysqli_query($conn,"INSERT INTO order_item(productId,price,quantity,createdAt)
SELECT productId,price,quantity,createdAt
FROM cart_item WHERE iduser =$userid " );


echo json_encode($response);
?>