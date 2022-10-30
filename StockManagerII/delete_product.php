<?php
 
/*
 * Following code will delete a product from table
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['cart_id'])) {
   
    $cartid = $_POST['cart_id'];
 
    // include db connect class
    require_once 'includes/db.php';

 
    // mysql update row with matched pid
    $result = mysqli_query($con,"DELETE FROM cart WHERE cart_id = $cartid");
 
    // check if row deleted or not
    if (mysqli_affected_rows($con) > 0) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Product successfully deleted";
 
        // echoing JSON response
        echo json_encode($response);
    }
}
?>