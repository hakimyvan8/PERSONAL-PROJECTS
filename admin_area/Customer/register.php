 <?php

require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();

    $firstname = $_POST['firstname'];
    $lastname = $_POST['lastname'];
    $email = $_POST['email'];
    $phone = $_POST['phone'];
    $permit = $_POST['permit'];
    $password = md5($_POST['password']);

    $stmt = $conn->prepare("SELECT mobile FROM users WHERE  mobile = ? OR email = ?");
    $stmt->bind_param("ss", $phone, $email);
    $stmt->execute();
    $stmt->store_result();
$response = array();
    if ($stmt->num_rows > 0)
    {
        $response['error'] = true;
        $response['message'] = 'User already registered';
        echo json_encode($response);
        $stmt->close();
    }
    else {
        $uuid = uniqid('', true);
        $stmt = $conn->prepare("INSERT INTO users (unique_id,firstName,lastName,email,passwordHash,mobile,permit,status,registeredAt) VALUES ( ?, ?, ?, ?, ?, ?, ?, 'pending', NOW())");
        $stmt->bind_param("sssssss", $uuid, $firstname, $lastname, $email, $password, $phone, $permit);

        if ($stmt->execute())
        {
    
            $stmt->close();

            $response['error'] = false;
            $response['message'] = 'User registered successfully';
            echo json_encode($response);

        }
    }
echo json_encode($response);


?>

