<?php
if(isset($_POST['password']) && $_POST['reset_link_token'] && $_POST['email'])
{
    require 'includes/db.php';
  
  $emailId = $_POST['email'];

  $token = $_POST['reset_link_token'];
  
  $password = md5($_POST['password']);

  $query = mysqli_query($con,"SELECT * FROM `users` WHERE `reset_link_token`='".$token."' and `email`='".$emailId."'");

   $row = mysqli_num_rows($query);

   if($row){

       mysqli_query($con,"UPDATE users set  passwordHash='" . $password . "', reset_link_token='" . NULL . "' ,exp_date='" . NULL . "' WHERE email='" . $emailId . "'");

       echo '<p>Congratulations! Your password has been updated successfully.</p>';
   }else{
      echo "<p>Something went wrong. Please try again</p>";
   }
}
?>