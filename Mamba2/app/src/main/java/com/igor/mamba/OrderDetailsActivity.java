package com.igor.mamba;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.igor.mamba.User.Order;
import com.igor.mamba.User.OrderDetailsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.layout.simple_spinner_item;

public class OrderDetailsActivity extends AppCompatActivity {
    private String URLstring = "http://" + URLs.IP + "/admin_area/orderhistory.php";
    OrderDetailsAdapter cartAdapter;
    JSONArray jsonArray = null;
    RecyclerView recyclerORDER;
    private ModelCart modelCart;
    ArrayList<Order> orderList;
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        recyclerORDER = findViewById(R.id.itemsRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerORDER.setLayoutManager(linearLayoutManager);
        recyclerORDER.setHasFixedSize(false);

        orderList = new ArrayList<>();
        loadData();
        View view = getLayoutInflater().inflate(R.layout.row_ordereditem, null, true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(getApplicationContext(),Home.class));
        overridePendingTransition(R.anim.modal_out,R.anim.modal_out);
    }

    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLstring, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                orderList.clear();
                //  Toast.makeText(getApplicationContext(), "Show something", Toast.LENGTH_SHORT).show();
                try {
                    jsonObject = new JSONObject(response);
                    jsonArray = jsonObject.getJSONArray("products");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject orderi = jsonArray.getJSONObject(i);

                        int id = Integer.parseInt(orderi.getString("orderid"));
                        int grandtotal = Integer.parseInt(orderi.getString("price"));
                        int Status = Integer.parseInt(orderi.getString("status"));
                        String createdAt = orderi.getString("date");

                        Order order = new Order();

                        order.setOrderid(id);
                        order.setGrandtotal(grandtotal);
                        order.setStatus(Status);
                        order.setCreateAt(createdAt);

                        order.setFirstname(orderi.getString("transcode"));
                        orderList.add(order);
                    }
                    cartAdapter = new OrderDetailsAdapter( OrderDetailsActivity.this,orderList);
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
                params.put("userid", SharedPrefManager.getInstance(getApplicationContext()).getUser().getUUID());

                return params;
            }
        };
        RequestHandle.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}