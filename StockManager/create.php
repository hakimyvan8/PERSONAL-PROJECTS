<?php

require 'includes/db.php';
require 'vendor/autoload.php';


\Stripe\Stripe::setApiKey('sk_test_51KahTdF8eTxMSBjWUPl7gphhj9Po4Q7VfKNe5AhfDnghG2HcCgTsTab7zzlbg04c7e1nDThjlM1kKQB6D6S5CUhC00o6T7mrPt');
\Stripe\Stripe::setMaxNetworkRetries(2);

require_once "includes/db.php";
$price = $_POST["totalpriceserver"];
$userid = $_POST["userid"];
$customer = \Stripe\Customer::create();
$ephemeralKey = \Stripe\EphemeralKey::create([
    'customer' => $customer->id
]);
$tid = $_POST["customer"];


$stmt = $con->prepare("SELECT p_id,p_qty FROM cart");
$stmt ->execute();


function calculateOrderAmount(array $items): int {
  
    return 1000;
}

header('Content-Type: application/json');

try {
   
    $jsonStr = file_get_contents('php://input');
    $jsonObj = json_decode($jsonStr);

    $paymentIntent = \Stripe\PaymentIntent::create([
        'amount' =>$price,
        'currency' => 'rwf',
        'customer' => $customer->id,
        'automatic_payment_methods' => [
            'enabled' => true,
        ],
    ]);

    $output = [
        
        'price' =>$price,
        'clientSecret' => $paymentIntent->client_secret,
        'emphemeralKey'=>$ephemeralKey->secreat,
        'publishableKey' => "pk_test_51KahTdF8eTxMSBjWPQOW8VLAHKK85V0vwiep8fvPsvXXrBbTbSkJWIAh96AKriGjlRNi1mlKmJEPk9j24FLZgzmA00fxvGFdHB",
    ];

    echo json_encode($output);
} catch (Error $e) {
    http_response_code(500);
    echo json_encode(['error' => $e->getMessage()]);
}