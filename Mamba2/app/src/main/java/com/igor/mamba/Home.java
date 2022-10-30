

package com.igor.mamba;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.igor.mamba.User.User;
import com.igor.mamba.User.signup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;


public class Home extends AppCompatActivity implements Runnable{

    private EditText searchText;
    private static final String URL_PRODUCTS = "http://"+URLs.IP+"/admin_area/Ggetproducts.php";
    ImageButton filterProductBtn;
    ImageView profileView;

    String cart ;
    public static int CLEAR_CART=0;
    FloatingActionButton card_btn;
    TextView usernameView,about,cartCountTv,textView1;
    CartListAdapter cartAdapter;
    ArrayList<Product> productList;
    List<ProductsCategory> categoryList;

    productAdapter adapter;

    ImageView logoutView,settingsView,homeView;
    //the recyclerView
    RecyclerView recyclerView, recyclerCatView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        usernameView = findViewById(R.id.usernameView);
        filterProductBtn = findViewById(R.id.filterProductBtn);
        settingsView = findViewById(R.id.settingsView);
        homeView = findViewById(R.id.homeView);
        textView1 = findViewById(R.id.textView1);
        profileView = findViewById(R.id.profileView);
        logoutView = findViewById(R.id.logout);
        card_btn = findViewById(R.id.card_btn);
        cartCountTv = findViewById(R.id.cartCountTv);
        about = findViewById(R.id.about);
        searchText = findViewById(R.id.searchText);

        User user = SharedPrefManager.getInstance(this).getUser();

        usernameView.setText(user.getFirstName());

        findViewById(R.id.imageView6).setOnClickListener(view ->{


            startActivity(new Intent(getApplicationContext(),editProfileActivity.class));
        });






       run();
        //initializing the productList
        productList = new ArrayList<>();


        //this method will fetch and parse Json
        //to display it in recyclerview


        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                try{
                    adapter.getFilter().filter(charSequence);

                }catch(Exception e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        homeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });



        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), aboutActivity.class));
                Home.this.finish();

            }
        });

        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OrderDetailsActivity.class));
                Home.this.finish();
            }
        });

        card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                Home.this.finish();
            }
        });
        settingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), editProfileActivity.class);
                intent.putExtra("cart",cart);
                startActivity(intent);
                Home.this.finish();

            }
        });

        filterProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setTitle("Choose Category:")
                        .setItems(Constants.productCategories1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //get selected item
                                String selected = Constants.productCategories1[which];
                                textView1.setText(selected);
                                if (selected.equals("All"))
                                {
                                    //load all
                                    loadProducts();
                                }
                                else
                                {
                                    //load filtered
                                    adapter.getFilter().filter(selected);
                                }
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }

    private void logout()
    {
        SharedPrefManager.getInstance(getApplicationContext()).logout();
    }
    private void loadDatacount() {

        String URL_getCART="http://"+URLs.IP+"/admin_area/getcount.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_getCART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONObject jsonObject = new JSONObject(response);
                   TextView text = findViewById(R.id.cartCountTv);
                   text.setText(jsonObject.getString("count"));
                   cart = jsonObject.getString("count");


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid",SharedPrefManager.getInstance(getApplicationContext()).getUser().getUUID());
                return params;
            }
        };
        RequestHandle.getInstance(Home.this).addToRequestQueue(stringRequest);
    }

    private void loadProducts() {
        recyclerView = findViewById(R.id.itemRv);
           GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2, androidx.recyclerview.widget.GridLayoutManager.VERTICAL,false) ;
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_PRODUCTS, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("products");


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);



                                int product_id = Integer.parseInt(employee.getString("id"));
                                String title = employee.getString("title");
                                String image = employee.getString("product_img1");
                                String price = employee.getString("price");
                                String unitStored = employee.getString("quantity");
                                String product_desc = employee.getString("summary");


                               String url = "http://"+URLs.IP+"/admin_area/product_images/"+image;

                                Product product = new Product();
                                product.setProduct_title(title);
                                product.setProduct_price(price);
                                product.setProduct_img1(image);
                                product.setUnitsStored(unitStored);
                                product.setProduct_desc(product_desc);
                                product.setProduct_id(product_id);
                                product.setQuant(0);
                                productList.add(product);

                            }
                            adapter = new productAdapter(Home.this,productList);
                            recyclerView.setAdapter(adapter);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue mQueue = Volley.newRequestQueue(this);
        mQueue.add(request);
    }

    @Override
    public void run() {

            loadProducts();


        new Thread( new Runnable()

        { @Override


        public void run() {


                    while(true) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        loadDatacount();

                        getApprovalNotification();



                    }


        } } ).start();


    }

    private void getApprovalNotification() {
//



        String URL_getCART="http://"+URLs.IP+"/StockManager/checkorderstatus.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_getCART, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {

                ArrayList<timeModel> timelinelist = new ArrayList<>();

                OrdehistoryActivity ordehistoryActivity = new OrdehistoryActivity();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject userJson = jsonObject.getJSONObject("order");
                     Date date = new Date();
                    int dateformat =Integer.parseInt(new SimpleDateFormat("hhmmss", Locale.US).format(date));
                    for(int i = 0; i < userJson.length(); i++) {
                  //  Toast.makeText(getApplicationContext(),userJson.getString("status") ,Toast.LENGTH_LONG).show();
                        if(TextUtils.equals(userJson.getString("status") , "5")){

                            new NotificationHandler(getApplicationContext()).showNotificationMessage(String.valueOf(dateformat),
                                    "Order # " + userJson.getString("OrderNumber") + " Declined at " + userJson.getString("timeUpdate"),
                                    "Your Order has been cancelled by the Admin", userJson.getString("OrderNumber"), userJson.getString("status"), userJson.getString("timeUpdate"),userJson.getString("Trackingid"),userJson.getString("location"),userJson.getString("driver"));

                        }
                       else if(TextUtils.equals(userJson.getString("status"),"1")) {
                            new NotificationHandler(getApplicationContext()).showNotificationMessage(String.valueOf(dateformat),
                                    "Order # " + userJson.getString("OrderNumber") + "  Approved at " + userJson.getString("timeUpdate"),
                                    "Your tracking number is " + userJson.getString("Trackingid"), userJson.getString("OrderNumber"), userJson.getString("status"), userJson.getString("timeUpdate"),userJson.getString("Trackingid"),userJson.getString("location"),userJson.getString("driver"));
                               }
                        else if(TextUtils.equals(userJson.getString("status"),"2")) {
                            new NotificationHandler(getApplicationContext()).showNotificationMessage(String.valueOf(dateformat),
                                    "Order # " + userJson.getString("OrderNumber") + "  Approved at " + userJson.getString("timeUpdate"),
                                    "Driver Has been Assigned " + userJson.getString("Trackingid"), userJson.getString("OrderNumber"), userJson.getString("status"), userJson.getString("timeUpdate"),userJson.getString("Trackingid"),userJson.getString("location"),userJson.getString("driver"));

                        }
                        else if(TextUtils.equals(userJson.getString("status"),"3")) {
                            new NotificationHandler(getApplicationContext()).showNotificationMessage(String.valueOf(dateformat),
                                    "Order # " + userJson.getString("OrderNumber") + "  Approved at " + userJson.getString("timeUpdate"),
                                    "Driver Has Accepted your order " + userJson.getString("Trackingid"), userJson.getString("OrderNumber"), userJson.getString("status"), userJson.getString("timeUpdate"),userJson.getString("Trackingid"),userJson.getString("location"),userJson.getString("driver"));
                                  }
                        else if(TextUtils.equals(userJson.getString("status"),"4")) {
                            new NotificationHandler(getApplicationContext()).showNotificationMessage(String.valueOf(dateformat),
                                    "Order # " + userJson.getString("OrderNumber") + "  Approved at " + userJson.getString("timeUpdate"),
                                    "Delivery Completed " + userJson.getString("Trackingid"), userJson.getString("OrderNumber"), userJson.getString("status"), userJson.getString("timeUpdate"),userJson.getString("Trackingid"),userJson.getString("location"),userJson.getString("driver"));

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid",SharedPrefManager.getInstance(getApplicationContext()).getUser().getUUID());
                return params;
            }
        };
        RequestHandle.getInstance(Home.this).addToRequestQueue(stringRequest);
    }
}

