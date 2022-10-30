<?php
require 'includes/db.php';
require 'vendor/autoload.php';
// This is your test secret API key.
\Stripe\Stripe::setApiKey("sk_test_51KahTdF8eTxMSBjWTrRGjXYXLRV6rNR5FO7PVLaBrUchvXjujzwNeKAXXMhuvIFyVRrcCnyv5n2tLqgBWiwDhMJC00tdTP48d5");
\Stripe\Stripe::setMaxNetworkRetries(2);

require_once "includes/db.php";
$price = (Double)$_POST["totalpriceserver"];
$shippingfee = (Double)$_POST["shippingfee"];
$userid = $_POST["userid"];

$customer = \Stripe\Customer::create();
$ephemeralKey = \Stripe\EphemeralKey::create([
    'customer' => $customer->id],[
        "stripe_version" => "2020-08-27"]
);
      
   $stripe = new \Stripe\StripeClient('sk_test_51KahTdF8eTxMSBjWTrRGjXYXLRV6rNR5FO7PVLaBrUchvXjujzwNeKAXXMhuvIFyVRrcCnyv5n2tLqgBWiwDhMJC00tdTP48d5');
try {
    $stripe->paymentMethods->create([
        'type' => 'card',
        'card' => [
          'number' => '4242424242424242',
          'exp_month' => 10,
          'exp_year' => 2023,
          'cvc' => '314',
        ],
      ]);

    $paymentIntent = \Stripe\PaymentIntent::create([
        'payment_method_types'=>['card'],
        'amount' => $price,
        'currency' => 'rwf',
        'customer' => $customer->id,
        'setup_future_usage' => 'off_session',
        'payment_method_types' => ['card'],
      
    ]);

    $output = [
        'paymentid'=>$paymentIntent->id,
        'price' =>$price,
        'customer' => $customer->id,
        'clientSecret' => $paymentIntent->client_secret,
        'publishableKey' => "pk_test_51KahTdF8eTxMSBjWRKneh0FIVrS49eX1OZvQllKOHEL3lexxZ6obiA2K4eSKevMEUnPWQWEluQzBXk3q7AjK478H00chOEN5lZ"
];


    echo json_encode($output);
} catch (Error $e) {
    http_response_code(500);
    echo json_encode(['error' => $e->getMessage()]);
}