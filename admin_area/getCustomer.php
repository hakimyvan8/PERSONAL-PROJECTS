<?php
require_once 'includes/db.php';

$stmt = $con->prepare($sql = "SELECT unique_id,phone,fname,lname FROM usermember;");


$stmt ->execute();
$stmt -> bind_result($userID,$phone,$firstname, $lastname);

$usermember = array();

while($stmt ->fetch()){

    $temp = array();

    $temp['unique_id'] = $userID;
    $temp['phone'] = $phone;
    $temp['fname'] = $firstname;
    $temp['lname'] = $lastname;

    array_push($usermember,$temp);


}
$response['User'] = $usermember;
echo json_encode($response);
?>