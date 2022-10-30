package com.igor.mamba.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.igor.mamba.Driverhome;
import com.igor.mamba.R;
import com.igor.mamba.SharedPrefManagerDriver;
import com.igor.mamba.finance;
import com.igor.mamba.stock;
import com.igor.mamba.supplier;

public class selectlogintype extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectlogintype);

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(),login.class));
                selectlogintype.this.finish();
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SharedPrefManagerDriver.getInstance(getApplicationContext()).isLoggedIn()) {
                    startActivity(new Intent(getApplicationContext(), Driverhome.class));
                }
                else{

                    startActivity(new Intent(getApplicationContext(), DriverLogin.class));
                }
            }
        });



        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(),stock.class));
                selectlogintype.this.finish();
            }
        });



        findViewById(R.id.button10).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(),finance.class));
                selectlogintype.this.finish();
            }
        });




        findViewById(R.id.button11).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(), supplier.class));
                selectlogintype.this.finish();
            }
        });

    }
}