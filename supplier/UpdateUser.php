<?php
      require_once 'includes/db.php';

        $uuid = $_POST['uuid'];
        $firstname = $_POST['firstname'];
        $lastname = $_POST['lastname'];
        $email = $_POST['email'];
        $phone = $_POST['phone'];
        $permit = $_POST['permit'];


        $sql = "UPDATE `usermember` SET `fname`='$firstname',`lname`='$lastname',`email`='$email',`phone`='$phone',`permit`='$permit' WHERE unique_id = '$uuid' ";
        $result = mysqli_query($con, $sql);
        $res = array();
        
        if($result){
            
            $res['success'] = "1";
            $res['message'] = "success";
            
            
        }else{
            $res['success'] = "0";
            $res['message'] = "error";
        }
        
        echo json_encode($res);
    
        mysqli_close($con);

?>