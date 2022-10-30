<?php
require 'includes/db.php';
include_once 'vendor\autoload.php';
use PHPMailer\PHPMailer\PHPMailer; 
use PHPMailer\PHPMailer\Exception;

 
 
$response = array();
    $emailId = $_POST['email'];

    $result = mysqli_query($con,"SELECT * FROM users WHERE email='" . $emailId . "'");

    $row= mysqli_fetch_array($result);

  if($row)
  {
    
     $token = md5($emailId).rand(10,9999);

     $expFormat = mktime(
     date("H"), date("i"), date("s"), date("m") ,date("d")+1, date("Y")
     );

    $expDate = date("Y-m-d H:i:s",$expFormat);

    $update = mysqli_query($con,"UPDATE users set  reset_link_token='" . $token . "' ,exp_date='" . $expDate . "' WHERE email='" . $emailId . "'");

    $link = '<div><a href=http://localhost/admin_area/resetlink.php?key='.$emailId.'&token='.$token.'>click here to reset your password</a></div>';

    if($update)
    {
      
      $response = array();
      $response['error'] = false;
      $response['delivery'] = $link;
     $response['message'] = "Password reset link sent to email valid for 24hrs";
      echo json_encode($response);
    }
  
  }else{
    $response = array();
    $response['error'] = true;
    $response['message'] = "Invalid Email Address";
   
    echo json_encode($response);
  
  }
?>