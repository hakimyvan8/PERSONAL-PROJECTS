package com.igor.mamba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.internal.TextWatcherAdapter;
import com.igor.mamba.User.RequestHandler;
import com.igor.mamba.User.User;
import com.igor.mamba.User.login;
import com.maxpilotto.creditcardview.CreditCardView;
import com.maxpilotto.creditcardview.models.Brand;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;


import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CheckoutActivity extends AppCompatActivity implements Runnable {
    private static final int CARD_NUMBER_TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
    private static final int CARD_NUMBER_TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
    private static final int CARD_NUMBER_DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
    private static final int CARD_NUMBER_DIVIDER_POSITION = CARD_NUMBER_DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
    private static final char CARD_NUMBER_DIVIDER = '-';

    private static final int CARD_DATE_TOTAL_SYMBOLS = 5; // size of pattern MM/YY
    private static final int CARD_DATE_TOTAL_DIGITS = 4; // max numbers of digits in pattern: MM + YY
    private static final int CARD_DATE_DIVIDER_MODULO = 3; // means divider position is every 3rd symbol beginning with 1
    private static final int CARD_DATE_DIVIDER_POSITION = CARD_DATE_DIVIDER_MODULO - 1; // means divider position is every 2nd symbol beginning with 0
    private static final char CARD_DATE_DIVIDER = '/';

    private static final int CARD_CVC_TOTAL_SYMBOLS = 3;
    EditText FirstName,LastName,Phone,address,PaymentPhone;
    TextView totalprice,back;
    Button chekout;
    CreditCardView creditCardView;
    private static final String TAG = "CheckoutActivity";
    private String name,name2,shippingfee;
    private Button payButton;
    PaymentSheet.CustomerConfiguration customerConfig;
    private String paymentIntentClientSecret ="pk_test_51KahTdF8eTxMSBjWRKneh0FIVrS49eX1OZvQllKOHEL3lexxZ6obiA2K4eSKevMEUnPWQWEluQzBXk3q7AjK478H00chOEN5lZ";
    private PaymentSheet paymentSheet;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    locationlistadapter locationlistadapter;
    RecyclerView recyclerCat;
    ArrayList<ModelDefaultLocation> locationlist;
    MyPreferences myPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        locationlist = new ArrayList<>();
        recyclerCat = findViewById(R.id.prefered);
        recyclerCat.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerCat.setHasFixedSize(false);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

       Bundle intent = getIntent().getExtras();

        mEditor = mPreferences.edit();

        //TextView totalprice = findViewById(R.id.totalprice);

       // creditCardView = findViewById(R.id.card);
       mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = mPreferences.edit();

         myPreferences = MyPreferences.getPreferences(getApplicationContext());




            name2 = "";

           shippingfee = "";

       //  Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
//        totalprice.setText(name2);

        payButton = findViewById(R.id.pay_button);
        payButton.setOnClickListener(this::onPayClicked);
        payButton.setEnabled(true);

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        getdefaultlocations();
  run();

    }
    public void getdefaultlocations(){
        StringRequest srequest = new StringRequest(com.android.volley.Request.Method.POST, URLs.getdefaultlocations, new com.android.volley.Response.Listener<String>() {
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
        }, new com.android.volley.Response.ErrorListener() {
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

    private void showAlert(String title, @Nullable String message) {
        runOnUiThread(() -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Ok", null)
                    .create();
            dialog.show();
        });
    }


    private void fetchPaymentIntent() {


      /////test  final String shoppingCartContent = "{\"items\": [ {\"id\":12\"\"}]}";
       Toast.makeText(getApplicationContext(),String.valueOf(myPreferences.getTotalpricecomputed()),Toast.LENGTH_SHORT).show();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("totalpriceserver", name)
                .addFormDataPart("shippingfee", shippingfee)
                .addFormDataPart("userid", SharedPrefManager.getInstance(getApplicationContext()).getUser().getUUID())
                .build();

        Request request = new Request.Builder()
                .url("http://"+URLs.IP+"/admin_area/create.php")
                .post(requestBody)
                .build();

        new OkHttpClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(
                            @NonNull Call call,
                            @NonNull Response response
                    ) throws IOException {
                        if (!response.isSuccessful()) {



                        } else {


                            final JSONObject responseJson = parseResponse(response.body());

                            paymentIntentClientSecret = responseJson.optString("clientSecret");
                            PaymentConfiguration.init(getApplicationContext(), responseJson.optString("publishableKey"));
                            runOnUiThread(() -> payButton.setEnabled(true));
                            Log.i(TAG, "Retrieved PaymentIntent");


                            mEditor.putString("paymentintent",responseJson.optString("paymentid"));
                            mEditor.commit();

                        }
                    }
                });
    }

    private JSONObject parseResponse(ResponseBody responseBody) {
        if (responseBody != null) {
            try {
                return new JSONObject(responseBody.string());
            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error parsing response", e);
            }
        }

        return new JSONObject();
    }

    private void onPayClicked(View view) {

        fetchPaymentIntent();
        PaymentSheet.Configuration configuration = new PaymentSheet.Configuration("Example, Inc.");

        paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration);
    }

    private void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            String url_delete_cart="http://"+URLs.IP+"/admin_area/orders_cart.php";
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url_delete_cart, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try{
                        JSONObject jsonObject = new JSONObject(response);

                    }catch (JSONException e) {
                        e.printStackTrace();
                        //Toast.makeText(context, "Exception GGGG", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                   }
            }){
                protected Map<String , String> getParams() throws AuthFailureError {
                    Map<String , String> params = new HashMap<>();

                    params.put("pid", mPreferences.getString("paymentintent",""));
                    params.put("totalpriceserver",name);
                    params.put("shippingfee",shippingfee);
                    params.put("userid",SharedPrefManager.getInstance(getApplicationContext()).getUser().getUUID());
                    params.put("location",mPreferences.getString("location",""));
                    params.put("location_province",mPreferences.getString("location_province",""));
                    params.put("fname",SharedPrefManager.getInstance(getApplicationContext()).getUser().getFirstName());
                    params.put("lname",SharedPrefManager.getInstance(getApplicationContext()).getUser().getLastName());
                    params.put("email",SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail());
                    params.put("number",SharedPrefManager.getInstance(getApplicationContext()).getUser().getPhone());
                    return params;
                }
            };
            RequestHandle.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Payment Completed").setContentText("Payment of"+name2+"Succesfully Processed").setConfirmText("close").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {@Override
            public void onClick(SweetAlertDialog sDialog) {
                // Showing simple toast message to user



                startActivity(new Intent(getApplicationContext(),Home.class));
            }
            }).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Log.i(TAG, "Payment canceled!");
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Throwable error = ((PaymentSheetResult.Failed) paymentSheetResult).getError();
             showAlert("Payment failed", error.getLocalizedMessage());
        }
    }

    @Override
    public void run() {
        new Thread( new Runnable()

        { @Override


        public void run() {


            while(true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }






                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        TextView textView = findViewById(R.id.shipping);
                        TextView textView24 = findViewById(R.id.textView24);
                       // Toast.makeText(getApplicationContext(), String.valueOf(myPreferences.getTotalpricecomputed()), Toast.LENGTH_SHORT).show();
     if(myPreferences.getTotalpricecomputed()  == 0) {
         DecimalFormatSymbols symbols = new DecimalFormatSymbols();
         symbols.setGroupingSeparator('\'');
         symbols.setDecimalSeparator(',');
         symbols.setCurrency(Currency.getInstance(new Locale("es", "ES")));

         DecimalFormat decimalFormat = new DecimalFormat("R₣ #,###.00", symbols);

         int total = (int) getShippingCharges(Float.parseFloat(mPreferences.getString("totalkgs", "")), Float.parseFloat(mPreferences.getString("distance", "")));
       ///  Toast.makeText(getApplicationContext(), String.valueOf(mPreferences.getString("totalpriceserver", "")), Toast.LENGTH_SHORT).show();

         int totalplusshipping = total + Integer.parseInt(mPreferences.getString("totalpriceserver", ""));
         name = String.valueOf(totalplusshipping);
         textView.setText(decimalFormat.format(totalplusshipping));
         textView24.setText(decimalFormat.format(total));
         myPreferences.totalpricecomputed((int) 8);
     }
                    }
                });
            }


        } } ).start();
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
}
