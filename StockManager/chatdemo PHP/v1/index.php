<?php
require_once '../include/DbOperation.php';
require_once '../libs/gcm/gcm.php';
require '.././libs/Slim/Slim.php';

\Slim\Slim::registerAutoloader();

$app = new \Slim\Slim();

// User id from db - Global Variable
$user_id = NULL;
$problem_id = NULL;
$health_concern_id = NULL;

$app->post('/getallstudents', function () use ($app){
	
    $db = new DbOperation();
    $messages = $db->getAllStudents();
    $response = array();
    $response['error']=false;
    $response['allstudents'] = array();
    while($row = mysqli_fetch_array($messages)){
        $temp = array();
        $temp['id']=$row['id'];
        $temp['name']=$row['name'];
        $temp['photo']=$row['photo'];
    
        array_push($response['allstudents'],$temp);
    }
    echoResponse(200,$response);
});
$app->post('/getalllecturer', function () use ($app){
	
    $db = new DbOperation();
    $messages = $db->getAllLecturer();
    $response = array();
    $response['error']=false;
    $response['alllecturer'] = array();
    while($row = mysqli_fetch_array($messages)){
        $temp = array();
        $temp['id']=$row['id'];
        $temp['name']=$row['name'];
        $temp['photo']=$row['photo'];
    
        array_push($response['alllecturer'],$temp);
    }
    echoResponse(200,$response);
});

$app->post('/register', function () use ($app) {
    //Verifying parameters
    verifyRequiredParams(array('password', 'name','type'));

    //Response array
    $response = array();

    //Getting request parameters
    
    $name = $app->request->post('name');
$password = $app->request->post('password');
	$type= $app->request->post('type');

    //Vaidating email
   // validateEmail($email);

    //Creating a db object
    $db = new DbOperation();

    //INserting user to database
    $res = $db->createUser($name,$password,$type);

    //If user created
    //Adding the user detail to response
    if ($res == USER_CREATED_SUCCESSFULLY) {
        $response["error"] = false;

        $user = $db->getUser($name,$password,$type);

        $response['id'] = $user['id'];
        $response['name'] = $user['name'];
     
	
        echoResponse(200, $response);

        //If user creating failes adding error to response
    } else if ($res == USER_CREATE_FAILED) {
        $response["error"] = true;
        $response["message"] = "Oops! An error occurred while registereing";
        echoResponse(200, $response);

        //If user already exist
        //adding the user data to response
    }  else if ($res == USER_ALREADY_EXISTED) {
        $response["error"] = false;
        $user = $db->getUser($email);

        $response['id'] = $user['id'];
        $response['name'] = $user['name'];
        

        echoResponse(200, $response);
    } 
});




//This is used to send message on the chat room
$app->post('/send', function () use ($app) {

    //Verifying required parameters
    verifyRequiredParams(array('id', 'message','to_id'));

    //Getting request parameters
    $id = $app->request()->post('id');
    $message = $app->request()->post('message');
    $name = $app->request()->post('name');
	$to_id = $app->request()->post('to_id');

    //Creating a gcm object
    $gcm = new GCM();

    //Creating db object
    $db = new DbOperation();

    //Creating response array
    $response = array();

    //Creating an array containing message data
    $pushdata = array();
    //Adding title which would be the username
    $pushdata['title'] = $name;
    //Adding the message to be sent
    $pushdata['message'] = $message;
    //Adding user id to identify the user who sent the message
    $pushdata['id']=$id;

    //If message is successfully added to database
    if ($db->addMessage($id, $message,$to_id)) {
        //Sending push notification with gcm object
        $gcm->sendMessage($db->getRegistrationTokens($id,$to_id), $pushdata);
        $response['error'] = false;
    } else {
        $response['error'] = true;
    }
    echoResponse(200, $response);
});

/*
 * URL: /storegcmtoken/:id
 * Method: PUT
 * Parameters: token
 * */

//This will store the gcm token to the database
$app->put('/storegcmtoken/:id', function ($id) use ($app) {
    verifyRequiredParams(array('token'));
    $token = $app->request()->put('token');
    $db = new DbOperation();
    $response = array();
    if ($db->storeGCMToken($id, $token)) {
        $response['error'] = false;
        $response['message'] = "token stored";
    } else {
        $response['error'] = true;
        $response['message'] = "Could not store token";
    }
    echoResponse(200, $response);
});

/*
 * URL: /storeDocgcmtoken/:id
 * Method: PUT
 * Parameters: token
 * */

//This will store the gcm token to the database
$app->put('/storelcgcmtoken/:id', function ($id) use ($app) {
    verifyRequiredParams(array('token'));
    $token = $app->request()->put('token');
    $db = new DbOperation();
    $response = array();
    if ($db->storeDocGCMToken($id, $token)) {
        $response['error'] = false;
        $response['message'] = "token stored";
    } else {
        $response['error'] = true;
        $response['message'] = "Could not store token";
    }
    echoResponse(200, $response);
});

/*
 * URL: /demographics
 * Method: POST
 * */


//This will fetch all the demographics info available on the database to display on the thread

/*
 * URL: /messages
 * Method: POST
 * */


//This will fetch all the messages available on the database to display on the thread
$app->post('/messages', function () use ($app){
	 verifyRequiredParams(array('from_id','to_id'));
	 $from_id = $app->request->post('from_id');
	$to_id= $app->request->post('to_id');
    $db = new DbOperation();
    $messages = $db->getMessages($from_id,$to_id);
    $response = array();
    $response['error']=false;
    $response['messages'] = array();
    while($row = mysqli_fetch_array($messages)){
        $temp = array();
        $temp['id']=$row['id'];
        $temp['message']=$row['message'];
        $temp['userid']=$row['from_id'];
        $temp['sentat']=$row['sentat'];
        $temp['name']=$row['name'];
        array_push($response['messages'],$temp);
    }
    echoResponse(200,$response);
});

/*
 * URL: /getallusers
 * Method: GET
 * */

//This will fetch all the users available on the database to display on the thread
$app->post('/getallusers', function () use ($app){
    $db = new DbOperation();
    $doctor= $db->getAllUsers();
	$response["users"] = array();
	 while ($row = mysqli_fetch_array($doctor)) {
        // temp user array
        $product = array();
        $product["id"] = $row["id"];
        $product["name"] = $row["name"];

        $product["email"] = $row["email"];
       
        $product["gcmtoken"] = $row["gcmtoken"];
        //$product["patient_email"] = $row["patient_email"];
       
 
        // push single product into final response array
        array_push($response["users"], $product);
    }
    echoResponse(200,$response);
       // $response['id'] = $doctor['id'];
        //$response['name'] = $doctor['name'];
        //$response['email'] = $doctor['email'];

       // echoResponse(200, $response);
    
   
});



//Function to validate email
function validateEmail($email)
{
      $app = \Slim\Slim::getInstance();
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $response["error"] = true;
        $response["message"] = 'Email address is not valid';
        echoResponse(400, $response);
        $app->stop();
    }
}


//Function to display the response in browser
function echoResponse($status_code, $response)
{
    $app = \Slim\Slim::getInstance();
    // Http response code
    $app->status($status_code);
    // setting response content type to json
    $app->contentType('application/json');
    echo json_encode($response);
}


//Function to verify required parameters
function verifyRequiredParams($required_fields)
{
    $error = false;
    $error_fields = "";
    $request_params = $_REQUEST;
    // Handling PUT request params
    if ($_SERVER['REQUEST_METHOD'] == 'PUT') {
        $app = \Slim\Slim::getInstance();
        parse_str($app->request()->getBody(), $request_params);
    }
    foreach ($required_fields as $field) {
        if (!isset($request_params[$field]) || strlen(trim($request_params[$field])) <= 0) {
            $error = true;
            $error_fields .= $field . ', ';
        }
    }

    if ($error) {
        // Required field(s) are missing or empty
        // echo error json and stop the app
        $response = array();
        $app = \Slim\Slim::getInstance();
        $response["error"] = true;
        $response["message"] = 'Required field(s) ' . substr($error_fields, 0, -2) . ' is missing or empty';
        echoResponse(400, $response);
        $app->stop();
    }
}



function authenticate(\Slim\Route $route)
{
    //Implement authentication if needed 
}


$app->run();