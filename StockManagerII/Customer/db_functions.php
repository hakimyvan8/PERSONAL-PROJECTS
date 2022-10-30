<?php
class DB_Functions {
    private $conn;

    //constructor
    function __construct()
    {
        require_once 'db_connect.php';
        $db = new DB_Connect();
        $this->conn = $db->connect();
    }
    //destructor
    function __destruct()
    {
        //TODO:implement __destruct()method
    }
    //store new user
    //return user details
    public function storeUser($name,$email,$password,$permit,$phone)
    {
        $uuid = uniqid('',true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"];
        $salt = $hash["salt"];

        $stmt = $this->conn-> prepare("INSERT INTO usermember(unique_id,name,email,encrypted_password,permit,phone,salt,create_at,status) VALUES (?,?,?,?,?,?,?,NOW(),'pending'");
        $stmt->bind_param("sssssss",$uuid,$name,$email,$encrypted_password,$permit,$phone,$salt);
        $result = $stmt->execute();
        $stmt->close();

        //Check for successful store
        if($result)
        {
            $stmt = $this->conn->prepare("SELECT * FROM usermember WHERE phone = ?");
            $stmt->bind_param("s",$phone);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $user;
        }else{
            return false;
        }

    }

    //Get user by phone and password
    public function getUserByPhoneAndPassword($phone,$password)
    {
        $stmt = $this->conn->prepare("SELECT * FROM usermember WHERE phone=?");
        $stmt->bind_param("s",$phone);

        if($stmt->execute())
        {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            //verifying user password
            $salt = $user['salt'];
            $encrypted_password = $user['encrypted_password'];
            $hash = $this->checkhashSSHA($salt,$password);
            //check for password equality
            if($encrypted_password == $hash)
            {
                return $user;
            }
        }
        else {
            return NULL;
        }
    }

    //Check user is existed or not
    public function isUserExisted($phone)
    {
        $stmt = $this->conn->prepare("SELECT phone from usermember WHERE phone=?");
        $stmt->bind_param("s",$phone);
        $stmt->execute();
        $stmt->store_result();

        if($stmt->num_rows() > 0)
        {
            $stmt->close();
            return true;
        }
        else {
            $stmt->close();
            return false;
        }
    }


    //Check user is existed or not
    public function getUserInformation($phone)
    {
        $stmt = $this->conn->prepare("SELECT * from usermember WHERE phone=?");
        $stmt->bind_param("s",$phone);

        if($stmt->execute())
        {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user;
        }
        else
            return NULL;
    }

    //Encrypting password

    public function hashSSHA($password)
    {
        $salt = sha1(rand());
        $salt = substr($salt,0,10);
        $encrypted = base64_encode(sha1($password . $salt,true).$salt);
        $hash =  array("salt"=>$salt,"encrypted"=>$encrypted);

        return $hash;
    }

    //Decrypting password
    public function checkhashSSHA($salt,$password)
    {
        $hash = base64_encode(sha1($password . $salt,true).$salt);
        return $hash;
    }
}
?>