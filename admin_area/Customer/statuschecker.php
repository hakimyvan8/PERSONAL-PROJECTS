
<?php

require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();

if(isTheseParametersAvailable(array('number'))){

//getting values
    $phone = $_POST['number'];

    $response = array();
//creating the query
    $stmt = $conn->prepare("SELECT  status FROM usermember WHERE phone= ? AND status='approved'");
    $stmt->bind_param("s",$phone);

    $stmt->execute();

    $stmt->store_result();

//if the user exist with given credentials


    if($stmt->num_rows > 0){

        $stmt->bind_result($status);
        $stmt->fetch();

        $response['error'] = "false";
        $response['response'] = "none";

    }else{
//if the user not found

        $response['error'] = true;


        $response['response'] ='Your Account has been Blocked, Please Contact Admin at Admin@mambaAgri.com or Contact +254(0)705183146. ';
        echo json_encode($response);
    }
    echo json_encode($response);
}

function isTheseParametersAvailable($params){

    foreach($params as $param){
        if(!isset($_POST[$param])){
            return false;
        }
    }
    return true;
}
?>