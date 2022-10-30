package com.igor.mamba;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.igor.mamba.User.Driver;
import com.igor.mamba.User.Order;
import com.igor.mamba.User.OrderDetailsAdapter;
import com.igor.mamba.User.User;
import com.igor.mamba.User.driverorderadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Driverhome extends AppCompatActivity {
    FragmentPagerAdapter adapterViewPager;

    driverorderadapter cartAdapter;
    TextView usernameView,phone;
    RecyclerView recyclerORDER;
    private Driver driver;
    ArrayList<ordermodel> orderList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverhome);

        usernameView = findViewById(R.id.usernameView);
        phone = findViewById(R.id.phone);
        recyclerORDER = findViewById(R.id.itemsRverse);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerORDER.setLayoutManager(linearLayoutManager);
        recyclerORDER.setHasFixedSize(false);
        loadData();

        Driver driver = SharedPrefManagerDriver.getInstance(this).getDriver();

        usernameView.setText(driver.getDriverFullname());
        phone.setText(driver.getDriverEmail());

    }

    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.driver, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {

                //  Toast.makeText(getApplicationContext(), "Show something", Toast.LENGTH_SHORT).show();
                try {
                     JSONObject jsonObject = new JSONObject(response);
                   JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject orderi = jsonArray.getJSONObject(i);



                        ordermodel order = new ordermodel();

                       order.setOrdernumber(orderi.getString("OrderNumber"));
                       order.setLocation(orderi.getString("location"));
                       order.setOrdername(orderi.getString("firstName")+"  "+orderi.getString("lastName"));
                       order.setPhonenumber(orderi.getString("mobile"));
                       order.setStatus(orderi.getString("status"));
                        orderList.add(order);
                    }
                    cartAdapter = new driverorderadapter( Driverhome.this,orderList);
                    recyclerORDER.setAdapter(cartAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", SharedPrefManagerDriver.getInstance(getApplicationContext()).getDriver().getDriverId());

                return params;
            }
        };
        RequestHandle.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}