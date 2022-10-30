<?php
date_default_timezone_set('Asia/Kolkata');
class DbOperation
{
    private  $conn;

    //Constructor
    function __construct()
    {
        require_once dirname(__FILE__) . '/Config.php';
        require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }

    //Function to create a new user
    public function createUser($name, $password,$type)
    {
        if (!$this->isUserExists($name,$password)) {
            $stmt = $this->conn->prepare("INSERT INTO users(name, password, type) values(?, ?, ?)");
            $stmt->bind_param("sss", $name, $password,$type);
            $result = $stmt->execute();
            $stmt->close();
            if ($result) {
                return USER_CREATED_SUCCESSFULLY;
            } else {
                return USER_CREATE_FAILED;
            }
        } else {
            return USER_ALREADY_EXISTED;
        }
    }
public function CheckUser($email,$password,$type)
    {
      
            $stmt = $this->conn->prepare("SELECT * FROM users where BINARY email=? AND BINARY password=? AND type=?");
            $stmt->bind_param("sss",$email,$password,$type);
            $result = $stmt->execute();
            $stmt->close();
            if ($result) {
                return USER_CREATED_SUCCESSFULLY;
            } else {
                return USER_CREATE_FAILED;
            }
        } 
    
    //Function to get the user with email
    public function getUser($name,$password,$type)
    {
        $stmt = $this->conn->prepare("SELECT * FROM users WHERE  name=? AND  password=? AND type=?");
        $stmt->bind_param("sss", $name,$password,$type);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        return $user;
    }


	public function getAllStudents()
    {
        $stmt = $this->conn->prepare("select u.id,u.name ,i.photo from users u,images i where u.type='S' and i.u_id = u.id");
        $stmt->execute();
        $user = $stmt->get_result();
        return $user;
    }
	public function getAllLecturer()
    {
        $stmt = $this->conn->prepare("select u.id,u.name ,i.photo from users u,images i where u.type='L' and i.u_id = u.id");
        $stmt->execute();
        $user = $stmt->get_result();
        return $user;
    }

    //Function to check whether user exist or not
    private function isUserExists($name,$password)
    {
        $stmt = $this->conn->prepare("SELECT id FROM users WHERE name=? and password = ?");
        $stmt->bind_param("ss", $name,$password);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }

    //Function to store gcm registration token in database
    public function storeGCMToken($id, $token)
    {
        $stmt = $this->conn->prepare("UPDATE users SET gcmtoken =? WHERE id=?");
        $stmt->bind_param("si", $token, $id);
        if ($stmt->execute())
            return true;
        return false;
    }

    //Function to get the registration token from the database
    //The id is of the person who is sending the message
    //So we are excluding his registration token as sender doesnt require notification
    public function getRegistrationTokens($id,$to_id){
        $stmt = $this->conn->prepare("SELECT gcmtoken FROM users WHERE id=?");
        $stmt->bind_param("i",$to_id);
        $stmt->execute();
        $result = $stmt->get_result();
        $tokens = array();
        while($row = $result->fetch_assoc()){
            array_push($tokens,$row['gcmtoken']);
        }
        return $tokens;
    }

    //Function to add message to the database
    public function addMessage($id,$message,$to_id){
        $stmt = $this->conn->prepare("INSERT INTO messages (message,from_id,to_id) VALUES (?,?,?)");
        $stmt->bind_param("sii",$message,$id,$to_id);
        if($stmt->execute())
            return true;
        return false;
    }

    //Function to get messages from the database 
    public function getMessages($from_id,$to_id){
        $stmt = $this->conn->prepare("SELECT a.id, a.message, a.sentat, a.from_id, b.name,
		a.to_id FROM messages a, users b WHERE ((a.from_id = ? AND a.to_id = ?) OR
		(a.from_id = ? AND a.to_id = ?)) AND (b.id = a.from_id) ORDER BY a.id ASC;");
		 $stmt->bind_param("iiii", $from_id, $to_id, $to_id, $from_id);
	     $stmt->execute();
         $result = $stmt->get_result();
        return $result;
    }

public function getMessagesDoc($from_id,$to_id){
	
       $stmt = $this->conn->prepare("SELECT a.id, a.message, a.sentat, a.users_id, b.name,
		a.to_id FROM messages a, users b WHERE ((a.users_id = ? AND a.to_id = ?) OR
		(a.users_id = ? AND a.to_id = ?)) AND (b.id = a.users_id) ORDER BY a.id ASC;");
		$stmt->bind_param("iiii", $from_id, $to_id, $to_id, $from_id);
	     $stmt->execute();
         $result = $stmt->get_result();
        return $result;
    }
  public function createDoctor($dr_name, $dr_email)
    {
        if (!$this->isDocExists($dr_email)) {
            $stmt = $this->conn->prepare("INSERT INTO doctors(dr_name,dr_email) values(?, ?)");
            $stmt->bind_param("ss", $dr_name, $dr_email);
            $result = $stmt->execute();
            $stmt->close();
            if ($result) {
                return USER_CREATED_SUCCESSFULLY;
            } else {
                return USER_CREATE_FAILED;
            }
        } else {
            return USER_ALREADY_EXISTED;
        }
    }

    //Function to get the user with email
    public function getDoctor($dr_email)
    {
        $stmt = $this->conn->prepare("SELECT * FROM doctors WHERE dr_email=?");
        $stmt->bind_param("s",$dr_email);
        $stmt->execute();
        $doctor = $stmt->get_result()->fetch_assoc();
        return $doctor;
    }

    //Function to check whether user exist or not
   
	

    //Function to store gcm registration token in database
    public function storeDocGCMToken($dr_id, $token)
    {
        $stmt = $this->conn->prepare("UPDATE users SET gcmtoken =? WHERE id=?");
        $stmt->bind_param("si", $token, $dr_id);
        if ($stmt->execute())
            return true;
        return false;
    }

    //Function to get the registration token from the database
    //The id is of the person who is sending the message
    //So we are excluding his registration token as sender doesnt require notification
    public function getDocRegistrationTokens($id){
        $stmt = $this->conn->prepare("SELECT dr_gcm_token FROM doctors WHERE NOT dr_id = ?;");
        $stmt->bind_param("i",$id);
        $stmt->execute();
        $result = $stmt->get_result();
        $tokens = array();
        while($row = $result->fetch_assoc()){
            array_push($tokens,$row['dr_gcm_token']);
        }
        return $tokens;
    }


}