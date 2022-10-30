package com.igor.mamba;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.igor.mamba.User.Order;
import com.igor.mamba.User.OrderDetailsAdapter;
import com.igor.mamba.User.User;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class editProfileActivity extends AppCompatActivity implements Runnable {

    private static final int CAMERA_PIC_REQUEST = 1337 ;
    ImageView back;
    ////////////////////////////////////////////location adapters
   
    locationlistadapter locationlistadapter;
    RecyclerView recyclerCat;
    ArrayList<ModelDefaultLocation> locationlist;

    /////////////////////////
    private Boolean runing;
    TextView completed;
    TextView declined;
    Button update,languageBtn;
    SharedPrefManager sharedPreferences;
    TextView userFirstUpdate,userLastCheck,phoneUpdate,emailUpdate,permitUpdate;
    String firstname,lastname,Email,phone,permit;
    OrderDetailsAdapter cartAdapter;
    JSONArray jsonArray = null;
    RecyclerView recyclerORDER;
    private ModelCart modelCart;
    ArrayList<Order> orderList;
    JSONObject jsonObject;
   private  Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        locationlist = new ArrayList<>();
        recyclerCat = findViewById(R.id.addressss);
        recyclerCat.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerCat.setHasFixedSize(false);
        run();
        getdefaultlocations();
        findViewById(R.id.viewall).setOnClickListener(view->{

            startActivity(new Intent(getApplicationContext(),OrderDetailsActivity.class));
        });


        findViewById(R.id.takepic).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });


    findViewById(R.id.buttonrefresh).setOnClickListener(view->{

        getdefaultlocations();
        locationlist = new ArrayList<>();

    });



        findViewById(R.id.back).setOnClickListener(view->{

            startActivity(new Intent(getApplicationContext(),Home.class));
            editProfileActivity.this.finish();
        });

        User user = sharedPreferences.getInstance(this).getUser();
        back = (ImageView)findViewById(R.id.back);
        recyclerORDER = findViewById(R.id.itemsRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerORDER.setLayoutManager(linearLayoutManager);
        recyclerORDER.setHasFixedSize(false);

        orderList = new ArrayList<>();
        loadData();
        userLastCheck = findViewById(R.id.userFirstUpdate);

        phoneUpdate = findViewById(R.id.phoneUpdate);
        emailUpdate = findViewById(R.id.emailUpdate);

        findViewById(R.id.address).setOnClickListener(view -> {


            BottomSheetDialogue bottomSheet = new BottomSheetDialogue(getApplicationContext());
            bottomSheet.show(getSupportFragmentManager(),
                    "ModalBottomSheet");
        });

        findViewById(R.id.logout).setOnClickListener(view ->{

            SharedPrefManager.getInstance(getApplicationContext()).logout();
            SharedPrefManagerDriver.getInstance(getApplicationContext()).logout();
        });
        //userFirstUpdate.setText(user.getFirstName());
        userLastCheck.setText(user.getLastName() + " " + user.getFirstName());
        emailUpdate.setText(user.getEmail());
        phoneUpdate.setText(user.getPhone());

        Intent intent = getIntent();
        TextView textView = findViewById(R.id.countercart);
        textView.setText("("+intent.getStringExtra("cart")+")");
       // permitUpdate.setText(user.getPermit());



    }
    private String URLstring = "http://" + URLs.IP + "/admin_area/orderhistory.php";
    int temp = 0;
    int finalres =0;
        int comp= 0;
        int dec = 0;




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            CircleImageView imageview = findViewById(R.id.camera); //sets imageview as the bitmap
            imageview.setImageBitmap(image);
        }
    }
    private void loadData() {
        TextView rec = findViewById(R.id.recent);
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

                        rec.setText("Recent Orders" +" ("+jsonArray.length()+")");
                    }
                    cartAdapter = new OrderDetailsAdapter( editProfileActivity.this,orderList);
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



    private void saveData() {
        final String user_id = sharedPreferences.getInstance(getApplicationContext()).getUser().getUUID();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "http://10.0.2.2/admin_area/UpdateUser.php/";

        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")){
                        Toast.makeText(getApplicationContext(),"Data Changed",Toast.LENGTH_LONG).show();
                        finishAndRemoveTask();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Updating, check Internet Connection", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("uuid", user_id);
                param.put("firstname", firstname);
                param.put("lastname", lastname);
                param.put("email", Email);
                param.put("phone", phone);
                param.put("permit", permit);

                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        RequestQueue mQueue = Volley.newRequestQueue(this);
        mQueue.add(sr);
        }

    @Override
    public void run() {

         new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){

                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();


    }

    public void getdefaultlocations(){
        StringRequest srequest = new StringRequest(Request.Method.POST, URLs.getdefaultlocations, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);

                    if(obj.optString("status").equals("true")) {



                        JSONArray dataArray = obj.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);

                            ModelDefaultLocation playerModel = new ModelDefaultLocation();
                            playerModel.setDroplocationname(dataobj.getString("locationName"));
                            playerModel.setDroplocationdistance(dataobj.getInt("distance_KM"));
                            playerModel.setRecievername(dataobj.getString("recievername"));
                            playerModel.setDropadress(dataobj.getString("dropadress"));
                            playerModel.setAddresstype(dataobj.getString("adresstype"));
                            playerModel.setActive(dataobj.getInt("isactive"));
                            locationlist.add(playerModel);

                        }
                        locationlistadapter = new locationlistadapter(getApplicationContext(), locationlist);
                        recyclerCat.setAdapter(locationlistadapter);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("userid", SharedPrefManager.getInstance(getApplicationContext()).getUser().getUUID());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        RequestQueue requestQueue =  Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(srequest);
    }


}