package com.igor.mamba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.igor.mamba.User.Supplier;

public class HomeSupplier extends AppCompatActivity {

    ImageView logout,homeView,forumView,settingView;
    TextView usernameView,sup_portal;
    Button newsupply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_supplier);

        newsupply = findViewById(R.id.newsupply);
        usernameView = findViewById(R.id.usernameView);
        logout = findViewById(R.id.logout);
        homeView = findViewById(R.id.homeView);
        sup_portal = findViewById(R.id.sup_portal);






        newsupply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SupFormActivity.class));
            }
        });
    }
}