package com.igor.mamba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class nonetwork extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonetwork);

        ImageView imageView = findViewById(R.id.imageView8);
        Glide.with(getApplicationContext())
                .asGif()
                .load(R.drawable.no)
                .placeholder(R.drawable.no).into(imageView);
    }


}