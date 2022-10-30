<?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();
$userid= $_POST["userid"];

$querry = mysqli_query($conn,"select drop_location.locationName,drop_location.distance_KM,adresstype,recievername,dropadress,isactive FROM defaultadress INNER JOIN drop_location on defaultadress.locationid = drop_location.locationid WHERE userid =$userid;");

while ($row = mysqli_fetch_assoc($querry)) {
$emparray[] = $row;
}
echo json_encode(array( "status" => "true","message" => "Data fetched successfully!","data" => $emparray) );
?>