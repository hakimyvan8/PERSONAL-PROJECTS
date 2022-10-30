package com.igor.mamba;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.igor.mamba.User.RequestHandler;
import com.igor.mamba.User.signup;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

public class ProductDetails extends AppCompatActivity {
    public static int CLEAR_CART=0;
    private TextView ptitle,pPrice,finalprice,quantityTv,prodDesc,limitMsg,qty1,qty2;
    private Product productDetails;
    private ImageView Pimageprod,plusBtn,minusBtn;
    productAdapter adapter;
    private Button proceedTocart;
    private Product product;
    private double cost = 0;
    private double finalCost = 0;
    private  int quantity = 0;

    private Context context;
    ArrayList<Product> models ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        ptitle = findViewById(R.id.title);
        pPrice = findViewById(R.id.price);
        finalprice = findViewById(R.id.finalprice);
        quantityTv = findViewById(R.id.quantityTv);
        qty1 = findViewById(R.id.qty1);
        qty2 = findViewById(R.id.qty2);
        Pimageprod = findViewById(R.id.imageprod);
        minusBtn = findViewById(R.id.decrementBtn);
        prodDesc = findViewById(R.id.prodDesc);
        plusBtn = findViewById(R.id.incrementBtn);
        proceedTocart = findViewById(R.id.proceedTocart);

        final String timestamp = ""+System.currentTimeMillis();




        getProduct();

    }

    private void getProduct()
    {

        product = (Product) getIntent().getSerializableExtra("object");

        //loading the image
        Glide.with(this)
                .load("http://"+URLs.IP+"/admin_area/product_images/"+product.getProduct_img1())
                .fitCenter()
                .into(Pimageprod);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\'');
        symbols.setDecimalSeparator(',');
       symbols.setCurrency(Currency.getInstance(new Locale("es", "ES")));
        DecimalFormat decimalFormat = new DecimalFormat("R₣ #,###.00", symbols);

        ptitle.setText(product.getProduct_title());
        prodDesc.setText(product.getProduct_desc());
        pPrice.setText(decimalFormat.format(Double.valueOf(product.getProduct_price())));
        finalprice.setText(decimalFormat.format(Double.valueOf(product.getProduct_price())));
        qty1.setText(product.getUnitsStored()+"/");
        qty2.setText(product.getRemainingUnits());

        String price = product.getProduct_price();
        cost = Double.parseDouble(price.replaceAll("RwF",""));
        finalCost = Double.parseDouble(price.replaceAll("RWF",""));
        quantity = 1;

        int Rem = Integer.parseInt(product.getUnitsStored());
        quantityTv.setText(String.valueOf(quantity));


        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity>=Rem){

                }
                else {
                    finalCost = finalCost + cost;
                    quantity++;
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setGroupingSeparator('\'');
                    symbols.setDecimalSeparator(',');
                   symbols.setCurrency(Currency.getInstance(new Locale("es", "ES")));
                    DecimalFormat decimalFormat = new DecimalFormat("R₣ #,###.00", symbols);
                    finalprice.setText(decimalFormat.format(Double.valueOf(finalCost)));
                    quantityTv.setText(""+quantity);

                }
            }
        });

        proceedTocart.setOnClickListener(view->{

            product.getProduct_id();
            class RegisterUser extends AsyncTask<Void, Void, String> {


                final String timestamp = ""+System.currentTimeMillis();
                @Override
                protected String doInBackground(Void... voids) {
                    //creating request handler object
                    RequestHandler requestHandler = new RequestHandler();

                    //creating request parameters
                    HashMap<String, String> params = new HashMap<>();

                    params.put("productid", String.valueOf(product.getProduct_id()));
                    params.put("product_title",String.valueOf(product.getProduct_title()));
                    params.put("product_price",String.valueOf(product.getProduct_price()));
                    params.put("quantity", String.valueOf(quantityTv.getText()));
                    params.put("itemNo",""+timestamp);
                    params.put("userid",SharedPrefManager.getInstance(getApplicationContext()).getUser().getUUID());

                    return requestHandler.sendPostRequest(URLs.URL_ADDTOCART, params);

                }

                public boolean showDialogue( boolean  condition ){


                    return false;
                }
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    //displaying the progress bar while user registers on the server

                    showDialogue(true);

                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //hiding the progressbar after completion
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    },2000);

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(s);

                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            finishAndRemoveTask();


                            //  startActivity(new Intent(getApplicationContext(), Home.class));
                        } else {
                            //       Toast.makeText(getApplicationContext(), "User Already registered", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //executing the async task
            RegisterUser ru = new RegisterUser();
            ru.execute();

        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quantity>1) {
                    finalCost = finalCost - cost;
                    quantity--;
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setGroupingSeparator('\'');
                    symbols.setDecimalSeparator(',');
                   symbols.setCurrency(Currency.getInstance(new Locale("es", "ES")));
                    DecimalFormat decimalFormat = new DecimalFormat("R₣ #,###.00", symbols);
                    finalprice.setText(decimalFormat.format(Double.valueOf(finalCost)));
                    quantityTv.setText("" + quantity);

                }
            }
        });

    }
}
