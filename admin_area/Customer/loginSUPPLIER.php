<?php

require_once 'db_connect.php';
$db = new DB_Connect();
$con = $db->connect();

if(isTheseParametersAvailable(array('phone', 'password'))){

//getting values
    $phone = $_POST['phone'];
    $password = $_POST['password'];
    $response = array();
//creating the query
    $stmt = $con->prepare("SELECT  supplier_id, supplierName, supplierEmail, supplierState, supplierDistrict, supplierSector, supplierBusiness, supplierPhone FROM supplier WHERE supplierPhone = ? AND supplierPass = ?");
    $stmt->bind_param("ss",$phone, $password);

    $stmt->execute();

    $stmt->store_result();

//if the user exist with given credentials


    if($stmt->num_rows > 0){

        $stmt->bind_result($uuid,$supplier_name, $supplier_email, $supplier_state, $supplier_district, $supplier_sector, $supplier_Business, $supplier_phone);
        $stmt->fetch();
        $user = array(

            'uuid'=>$uuid,
            'supplier_name'=>$supplier_name,
            'supplier_email'=>$supplier_email,
            'supplier_state'=>$supplier_state,
            'supplier_district'=>$supplier_district,
            'supplier_sector'=>$supplier_sector,
            'supplier_Business' =>$supplier_Business,
            'supplier_phone' =>$phone
        );

        $response['error'] = false;
        $response['message'] = 'Login successfull';
        $response['supplier'] = $user;
        echo json_encode($response);
    }else{
//if the user not found
        $response['error'] = true;
        $response['message'] = 'Invalid phone or password';
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