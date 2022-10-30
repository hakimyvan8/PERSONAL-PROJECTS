package com.igor.mamba;

import android.app.Application;

import com.stripe.android.PaymentConfiguration;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PaymentConfiguration.init(
                getApplicationContext(),
                "pk_test_51KahTdF8eTxMSBjWPQOW8VLAHKK85V0vwiep8fvPsvXXrBbTbSkJWIAh96AKriGjlRNi1mlKmJEPk9j24FLZgzmA00fxvGFdHB"
        );
    }
}