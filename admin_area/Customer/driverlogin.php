<?php

        require_once 'db_connect.php';
        $db = new DB_Connect();
        $conn = $db->connect();

if(isTheseParametersAvailable(array('phone', 'password'))){

//getting values
    $phone = $_POST['phone'];
    $password = $_POST['password'];
    $response = array();
//creating the query
    $stmt = $conn->prepare("SELECT  driverID,FullName, phone, DriverLicense, imagelocation,admin_email FROM driver WHERE phone = ? AND password = ?");
    $stmt->bind_param("ss",$phone, $password);

    $stmt->execute();

    $stmt->store_result();

//if the user exist with given credentials


    if($stmt->num_rows > 0){

        $stmt->bind_result($driverID, $FullName, $phone,$DriverLicense, $imagelocation,$admin_email);
        $stmt->fetch();
 
        $user = array(

            'uuid'=>(string)$driverID,
            'firstName'=>$FullName,
            'lastName'=>$phone,
            'email'=>$DriverLicense,
            'phone'=>$imagelocation,
            'permit'=>$admin_email,
              
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