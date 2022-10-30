package com.igor.mamba;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.igor.mamba.User.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.R.layout.simple_spinner_item;

public class CartActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener, Runnable {


    private static final String URL_getCART = "http://"+URLs.IP+"/admin_area/getcart.php";
    private String URLstring = "http://"+URLs.IP+"/admin_area/Customer/populate.php";
    private ArrayList<locationModel> locationmodelarraylist;
    private ArrayList<String> names = new ArrayList<String>();

    TextView Firstname, Lastname, phone, bstate, district, sector;
    private String firstname, lastname, phoneNo;
    private static String shipping;
    private static String shipID;
    TextView TotalPriceRWF, back;
    CartListAdapter cartAdapter;
    JSONArray jsonArray = null;
    RecyclerView recyclerCat;
    Float totalpricepaid;
    ArrayList<ModelCart> cartList;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    JSONObject jsonObject ;
    private Spinner spinner;
    RelativeLayout relativeLayout;

    public CartActivity() {

    }

    public static String getShipping() {
        return shipping;
    }

    public static void setShipping(String shipping) {
        CartActivity.shipping = shipping;
    }



    public static String getShipID() {
        return shipID;
    }

    public static void setShipID(String shipID) {
        CartActivity.shipID = shipID;
    }



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_cart);

        retrieveJSON();

loadCartList();


        //spinner = findViewById(R.id.stateEt);
//        spinner.setOnItemSelectedListener(this);
        recyclerCat = findViewById(R.id.cartlV);
        back = findViewById(R.id.back);
    //    TotalPriceRWF = findViewById(R.id.TotalPriceRWF);
     //   Firstname = findViewById(R.id.Firstname);
    //    Lastname = findViewById(R.id.Lastname);
    //    phone = findViewById(R.id.phone);
        bstate = findViewById(R.id.state);
    ///    district = findViewById(R.id.district);
  //      sector = findViewById(R.id.sector);
        User user = SharedPrefManager.getInstance(this).getUser();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        Firstname.setText(user.getFirstName());
   //     Lastname.setText(user.getLastName());
   //     phone.setText(user.getPhone());


        relativeLayout = findViewById(R.id.relative);
//        firstname = Firstname.getText().toString();
     //   lastname = Lastname.getText().toString();
  //      phoneNo = phone.getText().toString();

        //for order id and order time
        final String timestamp = ""+System.currentTimeMillis();
        final String Invoice = timestamp + phoneNo;

        cartList = new ArrayList<>();


        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //mPreferences = getSharedPreferences("tabian.com.sharedpreferencestest", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();


        findViewById(R.id.confirmOrder).setOnClickListener(View -> {
            //Toast.makeText(CartActivity.this, "Order placed successfully", Toast.LENGTH_LONG).show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ADDTOORDER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                  Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                  intent.putExtra("totalprice",totalpricepaid);
                  startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CartActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("userid",SharedPrefManager.getInstance(getApplicationContext()).getUser().getUUID());

                    return params;

                }
            };
            Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Home.class));
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerCat.setLayoutManager(linearLayoutManager);
        recyclerCat.setHasFixedSize(true);

    }





    private void loadCartList() {

        loadData();
    }
    private void retrieveJSON() {



        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);

                        try {

                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("status").equals("true")){

                                locationmodelarraylist = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    locationModel playerModel = new locationModel();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setLocationid(dataobj.getString("locationid"));
                                    playerModel.setLocationName(dataobj.getString("locationName"));
                                    playerModel.setLocationPrince(dataobj.getString("location_province"));

                                    playerModel.setDistanceKm(Float.parseFloat(dataobj.getString("distance_KM")));

                                    locationmodelarraylist.add(playerModel);

                                }

                                for (int i = 0; i < locationmodelarraylist.size(); i++){
                                    names.add(locationmodelarraylist.get(i).getLocationName().toString()+" Drop Point /" + locationmodelarraylist.get(i).getLocationPrince().toString());

                                }
                          //      ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(CartActivity.this, simple_spinner_item,names);
                           //     spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                   //     spinner.setAdapter(spinnerArrayAdapter);




                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_getCART, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                cartList.clear();
                //  Toast.makeText(getApplicationContext(), "Show something", Toast.LENGTH_SHORT).show();
                try {
                    jsonObject = new JSONObject(response);
                    jsonArray = jsonObject.getJSONArray("products");


                    if(jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject carti = jsonArray.getJSONObject(i);


                            //  Toast.makeText(getApplicationContext(),String.valueOf(jsonArray.length()),Toast.LENGTH_LONG).show();
                            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                            symbols.setGroupingSeparator('\'');
                            symbols.setDecimalSeparator(',');
                           symbols.setCurrency(Currency.getInstance(new Locale("es", "ES")));

                            DecimalFormat decimalFormat = new DecimalFormat("R₣ #,###.00", symbols);
                            String totalprice = decimalFormat.format(Integer.valueOf(jsonObject.getString("count")));
                            mEditor.putString("totalpriceserver", jsonObject.getString("count"));
                            mEditor.putString("totalprice", totalprice);
                            mEditor.putString("totalkgs", jsonObject.getString("totalkgs"));
                            mEditor.commit();




                           MyPreferences myPreferences = MyPreferences.getPreferences(getApplicationContext());

                           myPreferences.totalprice(Integer.parseInt(jsonObject.getString("count")));
                           myPreferences.totalkgs(Integer.parseInt(jsonObject.getString("totalkgs")));

                           
                        //   Toast.makeText(getApplicationContext(),String.valueOf(myPreferences.getTotalprice()),Toast.LENGTH_SHORT).show();
                            SharedPrefManager.getInstance(getApplicationContext()).getUser().setTotalprice(totalprice);
                            int product_id = Integer.parseInt(carti.getString("product_id"));
                            String productTprice = decimalFormat.format(Integer.valueOf(carti.getString("product_totalcost")));
                            String quantity = carti.getString("product_quantity");
                            String image = carti.getString("product_img1");
                            String productTitle = carti.getString("product_title");
                            String productNprice = decimalFormat.format(Integer.valueOf(carti.getString("product_netprice")));
                            TextView textView = findViewById(R.id.TotalPriceRWF);
                            textView.setText("Sub-Total / " + totalprice);


                            totalpricepaid = Float.parseFloat( jsonObject.getString("count"));
                            ModelCart modelCart = new ModelCart();
                            modelCart.setP_id(product_id);
                            modelCart.setTotalCpst(productTprice);
                            modelCart.setP_qty(quantity);
                            modelCart.setP_title(productTitle);
                            modelCart.setP_NetPrice(productNprice);
                            modelCart.setProduct_img1(image);



                            cartList.add(modelCart);
                            mEditor.putString("user_cartid", carti.getString("cart_Id"));
                            mEditor.commit();
                            //   Toast.makeText(getApplicationContext(),mPreferences.getString("user_cartid",""),Toast.LENGTH_LONG).show();
                        }
                        cartAdapter = new CartListAdapter(CartActivity.this, cartList);
                        recyclerCat.setAdapter(cartAdapter);

                    }
                    else{


                        findViewById(R.id.cartlV).setVisibility(View.GONE);
                        TextView tv = new TextView(getApplicationContext());
                        tv.setText("YOUR CART IS EMPTY");
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                        params.leftMargin = 107;
                      relativeLayout.addView(tv, params);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CartActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid", SharedPrefManager.getInstance(getApplicationContext()).getUser().getUUID());

                return params;
            }
        };
        RequestHandle.getInstance(CartActivity.this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //  Toast.makeText(getApplicationContext(),String.valueOf(locationmodelarraylist.get(i).getDistanceKm()), Toast.LENGTH_LONG).show();


        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\'');
        symbols.setDecimalSeparator(',');
        float totalkgs = 0;
        double roundOff = 0.0;


        totalkgs = Float.valueOf(mPreferences.getString("totalkgs", ""));
        mEditor.putString("totalprices", "0");
        mEditor.commit();

        float distance = locationmodelarraylist.get(i).getDistanceKm();
        String shipyId = locationmodelarraylist.get(i).getLocationid();

        double courierCost=getShippingCharges(totalkgs,distance);
        setShipping(String.valueOf(courierCost));
        setShipID(String.valueOf(shipyId));
        roundOff = Math.round(courierCost * 100.0) / 100.0;
        DecimalFormat decimalFormat = new DecimalFormat("", symbols);
        String totalprice = decimalFormat.format(roundOff);

        double Tota  = Double.parseDouble(mPreferences.getString("totalpriceserver","") ) + roundOff;
        TextView tota = findViewById(R.id.TotalPriceRWF);
        tota.setText("Total Price /" + decimalFormat.format(Tota));
       // TextView deli = findViewById(R.id.dPrice);
      //  deli.setText( totalprice);


        int total = (int) Tota;
        mEditor.putString("totalprices", String.valueOf(total));
        mEditor.putString("totalprice", decimalFormat.format(Tota));
        mEditor.putString("totalkgs", "0");
        mEditor.putString("location", locationmodelarraylist.get(i).getLocationName());
        mEditor.putString("location_province", locationmodelarraylist.get(i).getLocationPrince());
        mEditor.putString("shipingfee", String.valueOf(courierCost));
        mEditor.commit();


    }
    public static synchronized void saveToFileSystem(Context context, Object object, String binFileName) {
        try {
            String tempPath = context.getFilesDir() + "/" + binFileName + ".bin";
            File file = new File(tempPath);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(object);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public double getShippingCharges(float weight, float miles)
    {

        double charges;
        if (weight <= 20)
        { charges = (40.10 * Math.ceil((miles/1.6) ));
        }
        else if ((weight > 20) && (weight <= 50))
        {
            charges = (60.20 * Math.ceil((miles/1.6)  ));
        }
        else if ((weight > 50) && (weight <=80))
        {
            charges = (100.70 * Math.ceil((miles/1.6) ));
        }
        else
        {
            charges = (150.80 * Math.ceil((miles/1.6) ));
        }
        return charges;

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void run() {



    }
}
