
<?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();


$locationid = $_POST["locationid"];
$userid     = $_POST["userid"];
$deliveryaddress = $_POST["deliveryaddress"];
$tagname = $_POST["tagname"];
$receivername =$_POST["recievername"];


$querry = $conn->prepare("INSERT INTO defaultadress(locationid,userid,adresstype,recievername,dropadress,isactive) VALUES ($locationid,$userid,'$tagname','$receivername','$deliveryaddress',0)");
if($querry->execute())
{
    $response['error'] = false;
    echo json_encode($response);

}
else{
    $response['error'] = true;
    echo json_encode($response);


}


?>

