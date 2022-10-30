package com.igor.mamba;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.igor.mamba.User.login;
import com.igor.mamba.User.selectlogintype;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Canvas canvas;
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        Glide.with(getApplicationContext())
                .asGif()
                .load(R.drawable.unnamed)
                .placeholder(R.drawable.unnamed).into(imageView);
        new Handler().postDelayed(new Runnable() {
            public void run() {

              Thread thread;
                thread = new Thread(new Runnable() {
                      @Override
                      public void run() {
                          int code = 0;
                          try {
                              URL url = null;

                              url = new URL(URLs.URL_LOGIN);

                              HttpURLConnection connection = null;
                              connection = (HttpURLConnection) url.openConnection();

                              code = connection.getResponseCode();
                              Log.d("code", String.valueOf(code));
                          } catch (IOException e) {
                              e.printStackTrace();
                          }


                          if(code == 200) {
                              SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());
                              if(sharedPrefManager.getUser().getUUID() != null){
                                  startActivity(new Intent(getApplicationContext(), Home.class));
                                  MainActivity.this.finish();
                              }
                              else{

                                  startActivity(new Intent(getApplicationContext(), selectlogintype.class));
                                  MainActivity.this.finish();


                              }
                          } else {


                              startActivity(new Intent(getApplicationContext(),nonetwork.class));

                          }
                      }
                  });
                thread.start();


            }
        }, 2000);
    }

}