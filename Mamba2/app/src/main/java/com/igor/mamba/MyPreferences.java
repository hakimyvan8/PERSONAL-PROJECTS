package com.igor.mamba;

import android.content.Context;
import android.content.SharedPreferences;

import static java.lang.String.valueOf;

public class MyPreferences {
    private static MyPreferences myPreferences;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private MyPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(Config.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static MyPreferences getPreferences(Context context) {
        if (myPreferences == null) myPreferences = new MyPreferences(context);
        return myPreferences;
    }

    public void shippingfee(int shippingfee){
        editor.putInt(valueOf(Config.shippingfee), shippingfee);
        editor.apply();
    }
    public void totalkgs(int totalkgs){
        editor.putInt(valueOf(Config.totalkgs), totalkgs);
        editor.apply();
    }
    public float gettotalkgs(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getInt(valueOf(Config.totalkgs), 0);
    }
    public float get_shippingfee(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getInt(valueOf(Config.shippingfee), 0);
    }

    public void totalprice(int totalprice){
        editor.putInt(valueOf(Config.totalprice), totalprice);
        editor.apply();
    }

    public int getTotalprice(){
        return sharedPreferences.getInt(valueOf(Config.totalprice), 0); //if user's age not found then it'll return -1
    }
    public void totalpricecomputed(int computed){
        editor.putInt(valueOf(Config.totalpricecomputed), computed);
        editor.apply();
    }

    public int getTotalpricecomputed(){
        return sharedPreferences.getInt(valueOf(Config.totalpricecomputed), 0); //if user's age not found then it'll return -1
    }

    public void totalpriceformatted(String totalprice){
        editor.putString(Config.totalpriceformatted, totalprice);
        editor.apply();
    }

    public String getTotalpriceformatted(){
        return sharedPreferences.getString(Config.totalpriceformatted, ""); //if user's age not found then it'll return -1
    }

}