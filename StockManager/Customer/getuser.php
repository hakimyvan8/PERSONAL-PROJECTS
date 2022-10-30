<?php
require_once 'db_functions.php';
$db = new DB_Functions();



$response = array();
if(isset($_POST['phone']))
{
    $phone = $_POST['phone'];

    $user = $db->getUserInformation($phone,$id,$firstname,$lastname,$permit);
    if($user)
    {
        $response["phone"] =$user["phone"];
        $response["id"] =$user["id"];
        $response["firstname"] =$user["lastname"];
        $response["lastname"] =$user["lastname"];
        $response["permit"] =$user["permit"];
        echo json_encode($response);
    }
    else {
        $response["error_msg"] = "Unknown Error";
        echo json_encode($response);
    }
}
?>