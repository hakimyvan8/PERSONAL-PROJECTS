 <?php

require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();

    $firstname = $_POST['firstname'];
    $lastname = $_POST['lastname'];
    $email = $_POST['email'];
    $phone = $_POST['phone'];
    $permit = $_POST['permit'];
    $state = $_POST['state'];
    $district = $_POST['district'];
    $sector = $_POST['sector'];
    $password = md5($_POST['password']);

    $stmt = $conn->prepare("SELECT phone FROM usermember WHERE  phone = ? OR email = ?");
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
        $stmt = $conn->prepare("INSERT INTO usermember (unique_id,fname,lname, email, encrypted_password, permit,phone,state,district,sector,status,create_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'pending', NOW())");
        $stmt->bind_param("ssssssssss", $uuid, $firstname, $lastname, $email, $password, $permit, $phone,$state,$district,$sector);

        if ($stmt->execute())
        {
            $stmt = $conn->prepare("SELECT fname, lname, email, permit,phone,state,district,sector FROM usermember WHERE phone = ?");
            $stmt->bind_param("s", $phone);
            $stmt->execute();
            $stmt->bind_result($fname, $lname, $email, $permit, $phone,$state,$district,$sector);
            $stmt->fetch();

            $user = array(

                'uuid' => $uuid,
                'firstname' => $fname,
                'lastname' => $lname,
                'permit' => $permit,
                'email' => $email,
                'phone' => $phone,
                'state' => $state,
               'district' => $district,
                'sector' => $sector
            );

            $stmt->close();

            $response['error'] = false;
            $response['message'] = 'User registered successfully';
            $response['user'] = $user;
            echo json_encode($response);

        }
    }
echo json_encode($response);


?>

