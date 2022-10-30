package com.igor.mamba;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.igor.mamba.User.Order;
import com.igor.mamba.User.OrderDetailsAdapter;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsPurchased extends AppCompatActivity {
    RecyclerView recyclerView;
    List<purchaseditemsmodel> purchaseditemsmodel;
    private static String JSON_URL = URLs.PURCHASEDITEMS;
    itemspurchasedadapter adapter;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_purchased);
        recyclerView = findViewById(R.id.songsList);
        purchaseditemsmodel = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
       recyclerView.setHasFixedSize(false);
         bundle = getIntent().getExtras();

         findViewById(R.id.button6).setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View view) {
                 //startActivity(new Intent(getApplicationContext(),g))
             }
         });
        if(bundle != null) {

            TextView textView = findViewById(R.id.textView20);
            textView.setText("Order number "+bundle.getString("id"));
        }



      loadData();
    }
    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PURCHASEDITEMS, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                TextView status = findViewById(R.id.status);
                //  Toast.makeText(getApplicationContext(), "Show something", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                   JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject orderi = jsonArray.getJSONObject(i);


                        purchaseditemsmodel purchaseditemsmodelpm = new purchaseditemsmodel();
                        purchaseditemsmodelpm.setStatus(orderi.getString("content"));
                        purchaseditemsmodelpm.setTitle(orderi.getString("title"));
                        purchaseditemsmodelpm.setPrice((float) orderi.getDouble("price"));
                        purchaseditemsmodelpm.setQuantity(orderi.getInt("quantity"));
                        purchaseditemsmodelpm.setProductimage(orderi.getString("product_img1"));
                        status.setText(orderi.getString("content"));
                        purchaseditemsmodel.add(purchaseditemsmodelpm);
                    }
                     adapter = new itemspurchasedadapter( getApplicationContext(),purchaseditemsmodel);
                    recyclerView.setAdapter(adapter);

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
                params.put("orderid", bundle.getString("id"));

                return params;
            }
        };
        RequestHandle.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}