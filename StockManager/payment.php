<?php

// This example sets up an endpoint using the Slim framework.
// Watch this video to get started: https://youtu.be/sGcNPFX1Ph4.
$app->post('/payment-sheet', function (Request $request, Response $response) {
// Set your secret key. Remember to switch to your live secret key in production.
// See your keys here: https://dashboard.stripe.com/apikeys
    \stripe\stripe::setApiKey('sk_test_51KahTdF8eTxMSBjWUPl7gphhj9Po4Q7VfKNe5AhfDnghG2HcCgTsTab7zzlbg04c7e1nDThjlM1kKQB6D6S5CUhC00o6T7mrPt');
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
      'publishableKey' => 'pk_test_51KahTdF8eTxMSBjWPQOW8VLAHKK85V0vwiep8fvPsvXXrBbTbSkJWIAh96AKriGjlRNi1mlKmJEPk9j24FLZgzmA00fxvGFdHB'
  ])->withStatus(200);
});
