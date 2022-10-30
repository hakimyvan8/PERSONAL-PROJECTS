<?php

// This example sets up an endpoint using the Slim framework.
// Watch this video to get started: https://youtu.be/sGcNPFX1Ph4.
$app->post('/payment-sheet', function (Request $request, Response $response) {
// Set your secret key. Remember to switch to your live secret key in production.
// See your keys here: https://dashboard.stripe.com/apikeys
    \stripe\stripe::setApiKey('sk_test_51KahTdF8eTxMSBjWTrRGjXYXLRV6rNR5FO7PVLaBrUchvXjujzwNeKAXXMhuvIFyVRrcCnyv5n2tLqgBWiwDhMJC00tdTP48d5');
    // Use an existing Customer ID if this is a returning customer.
    $customer = \Stripe\Customer::create();
    $ephemeralKey = \Stripe\EphemeralKey::create([
        'customer' => $customer->id],[
        'stripe_version' => '2020-08-27'
  ]);
  $paymentIntent = \Stripe\PaymentIntent::create([
      'amount' => 1099,
      'currency' => 'eur',
      'customer' => $customer->id,
      'automatic_payment_methods' => [
          'enabled' => 'true',
      ]
      ]);

  return $response->withJson([
      'paymentIntent' => $paymentIntent->client_secret,
      'ephemeralKey' => $ephemeralKey->secret,
      'customer' => $customer->id,
      'publishableKey' => 'pk_test_51KahTdF8eTxMSBjWRKneh0FIVrS49eX1OZvQllKOHEL3lexxZ6obiA2K4eSKevMEUnPWQWEluQzBXk3q7AjK478H00chOEN5lZ'
  ])->withStatus(200);
});
