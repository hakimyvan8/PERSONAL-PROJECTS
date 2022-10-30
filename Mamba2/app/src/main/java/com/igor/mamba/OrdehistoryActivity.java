package com.igor.mamba;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class OrdehistoryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    TimeLineModel timeLineAdapter;
    RecyclerView recyclerCat;
    ArrayList<timeModel> timelinelist;
    private  LinearLayoutManager mLayoutManager;
    int drawables[] = {R.drawable.accepted,R.drawable.driver,R.drawable.enroute,R.drawable.delivered};

    public OrdehistoryActivity(){

        timelinelist = new ArrayList<>();


    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordehistory);
        Bundle bundle = getIntent().getExtras();
        String value = bundle.getString("orderid");
        String value2= bundle.getString("orderstatus");
        String value3= bundle.getString("date");
        String value4 = bundle.getString("trackingnumber");
        String value5 = bundle.getString("location");
        String value6 = bundle.getString("driver");

        TextView deliveryadress = findViewById(R.id.deliveryAdress);

        timelinelist = new ArrayList<>();
        TextView title = findViewById(R.id.title);
        recyclerCat = findViewById(R.id.orders);


        deliveryadress.setText(value5);

        if(TextUtils.equals(value2,"1")) {

             timelinelist.add(new timeModel(Icon.createWithResource(getApplicationContext(),drawables[0]), "Order Accepted, Timeline will be updated untill delivery", value3, OrderStatus.ACTIVE));

            timelinelist.add(new timeModel(Icon.createWithResource(getApplicationContext(),drawables[1]), "Assigning Driver", value3, OrderStatus.INACTIVE));

        }

        if(TextUtils.equals(value2,"2")) {

            timelinelist.add(new timeModel(Icon.createWithResource(getApplicationContext(),drawables[0]), "Order Accepted, Timeline will be updated untill delivery", value3, OrderStatus.INACTIVE));

            timelinelist.add(new timeModel(Icon.createWithResource(getApplicationContext(),drawables[1]), "Driver Assigned", value3, OrderStatus.ACTIVE));
            timelinelist.add(new timeModel(Icon.createWithResource(getApplicationContext(),drawables[2]), "Order Enroute", value3, OrderStatus.INACTIVE));
            timelinelist.add(new timeModel(Icon.createWithResource(getApplicationContext(),drawables[3]), "Order Arrived at Droppoint", value3, OrderStatus.INACTIVE));
        }
        if(TextUtils.equals(value2,"3")) {

            timelinelist.add(new timeModel(Icon.createWithResource(getApplicationContext(),drawables[0]), "Order Accepted, Timeline will be updated untill delivery", value3, OrderStatus.INACTIVE));

            timelinelist.add(new timeModel(Icon.createWithResource(getApplicationContext(),drawables[1]), "Driver Assigned", value3, OrderStatus.INACTIVE));
            timelinelist.add(new timeModel(Icon.createWithResource(getApplicationContext(),drawables[2]), "Order Enroute", value3, OrderStatus.ACTIVE));
            timelinelist.add(new timeModel(Icon.createWithResource(getApplicationContext(),drawables[3]), "Order Arrived at Droppoint", value3, OrderStatus.INACTIVE));
        }
        if(TextUtils.equals(value2,"4")) {

            timelinelist.add(new timeModel(Icon.createWithResource(getApplicationContext(),drawables[0]), "Order Accepted, Timeline will be updated untill delivery", value3, OrderStatus.INACTIVE));

            timelinelist.add(new timeModel(Icon.createWithResource(getApplicationContext(),drawables[1]), "Driver Assigned", value3, OrderStatus.INACTIVE));
            timelinelist.add(new timeModel(Icon.createWithResource(getApplicationContext(),drawables[2]), "Order Enroute", value3, OrderStatus.INACTIVE));
            timelinelist.add(new timeModel(Icon.createWithResource(getApplicationContext(),drawables[3]), "Order Arrived at Droppoint", value3, OrderStatus.ACTIVE));
        }


        timeLineAdapter= new TimeLineModel(timelinelist, OrdehistoryActivity.this);
        recyclerCat.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerCat.setAdapter(timeLineAdapter);


         title.setText("Track your order #"+value);
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),OrderDetailsActivity.class));
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
