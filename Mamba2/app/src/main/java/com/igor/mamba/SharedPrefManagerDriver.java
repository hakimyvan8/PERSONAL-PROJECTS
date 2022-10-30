package com.igor.mamba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.igor.mamba.User.Driver;
import com.igor.mamba.User.DriverLogin;
import com.igor.mamba.User.LoginSupplier;
import com.igor.mamba.User.Supplier;
import com.igor.mamba.User.login;
import com.igor.mamba.User.selectlogintype;


public class SharedPrefManagerDriver {


    private static final String SHARED_PREF_NAME = "Mambadriver";
    private static final String DriverId = "DriverId";
    private static final String DriverFullname = "DriverFullname";
    private static final String DriverPhoneNumber = "DriverPhoneNumber";
    private static final String DriverLincence = "DriverLincence";
    private static final String DriverImage= "DriverImage";
    private static final String DriverEmail= "DriverEmail";



    private static SharedPrefManagerDriver mInstance;
    private static Context mCtx;

    private SharedPrefManagerDriver(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerDriver getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerDriver(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void DriverLogin(Driver supplier) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(DriverId, supplier.getDriverId());
        editor.putString(DriverFullname, supplier.getDriverFullname());
        editor.putString(DriverPhoneNumber, supplier.getDriverEmail());
        editor.putString(DriverLincence, supplier.getDriverLincence());
        editor.putString(DriverImage, supplier.getDriverLincence());
        editor.putString(DriverEmail, supplier.getDriverEmail());

        editor.apply();
    }
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DriverPhoneNumber, null) != null;
    }


    public Driver getDriver() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Driver(

                sharedPreferences.getString(DriverId,null),
                sharedPreferences.getString(DriverFullname, null),
                sharedPreferences.getString(DriverPhoneNumber, null),
                sharedPreferences.getString(DriverLincence, null),
                sharedPreferences.getString(DriverImage, null),
                sharedPreferences.getString(DriverEmail, null)

        );
    }


    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(mCtx, selectlogintype.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);

    }
}