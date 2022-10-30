<?php

        require_once 'db_connect.php';
        $db = new DB_Connect();
        $conn = $db->connect();

if(isTheseParametersAvailable(array('phone', 'password'))){

//getting values
    $phone = $_POST['phone'];
    $password = md5($_POST['password']);
    $response = array();
//creating the query
    $stmt = $conn->prepare("SELECT  id,firstName, lastName, email, mobile, permit,status FROM users WHERE mobile = ? AND passwordHash = ?");
    $stmt->bind_param("ss",$phone, $password);

    $stmt->execute();

    $stmt->store_result();

//if the user exist with given credentials


    if($stmt->num_rows > 0){

        $stmt->bind_result($id, $name, $lname, $email,$phone, $permit,$status);
        $stmt->fetch();
        if($status == "pending"){

            $response['error'] = true;
            $response['message'] = 'user verification pending';
            echo json_encode($response);
            return;}
        $user = array(

            'uuid'=>(string)$id,
            'firstName'=>$name,
            'lastName'=>$lname,
            'email'=>$email,
            'phone'=>$phone,
            'permit'=>$permit,
              
        );

        $response['error'] = false;
        $response['message'] = 'Login successfull';
        $response['user'] = $user;
        echo json_encode($response);
    }else{
//if the user not found
        $response['error'] = true;
        $response['message'] = 'Invalid username or password';
        echo json_encode($response);
    }
}
echo json_encode($response);
function isTheseParametersAvailable($params){

    foreach($params as $param){
        if(!isset($_POST[$param])){
            return false;
        }
    }
    return true;
}
?>