package com.igor.mamba;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.cartHolder> {

    ArrayList<ModelCart> cartList;
    private Context context;

    View view;
    public CartListAdapter(Context mCtx, ArrayList<ModelCart> cartList){
        this.cartList = cartList;
        this.context = mCtx;
    }
    @NonNull
    @Override
    public CartListAdapter.cartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
         view = inflater.inflate(R.layout.row_cartitem,null);
        return new CartListAdapter.cartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.cartHolder holder, int position) {
        final ModelCart modelCart = cartList.get(position);

        holder.ProductNameTv.setText(modelCart.getP_title());
        holder.itemQuantityPrice.setText(modelCart.getTotalCpst());
        holder.itemQuantityTv.setText(modelCart.getP_qty());
        holder.ProductPriceTv.setText("("+modelCart.getP_NetPrice()+" / bag)");
        Glide.with(context)
                .load("http://"+URLs.IP+"/admin_area/product_images/"+modelCart.getProduct_img1())
                .fitCenter()
                .into(holder.productimg);
        holder.itemRemove.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {


               deleteFromCart(String.valueOf(modelCart.getP_id()));
                SharedPreferences mPreferences;
                mPreferences = PreferenceManager.getDefaultSharedPreferences(context);

                SharedPreferences.Editor mEditor;
                mEditor = mPreferences.edit();

                mEditor.putString("totalpriceserver", "0");
                mEditor.putString("totalprice", "0");
                mEditor.putString("totalkgs", "0");
                mEditor.commit();
            }
        });
    }
    private void deleteFromCart(String id)
    {
        final String theID = id;

        String url_delete_cart="http://"+URLs.IP+"/admin_area/url_delete_cart.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_delete_cart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject = new JSONObject(response);

                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                }catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "Exception GGGG", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Failed to Data > "+error,Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String , String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();

                params.put("pid", theID);

                return params;
            }
        };
        RequestHandle.getInstance(context).addToRequestQueue(stringRequest);

        selfIntent();
    }

    private void selfIntent()
    {

        Intent selfIntent1 = new Intent(context, CartActivity.class);
        selfIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(selfIntent1);
    }

    @Override
    public int getItemCount() {
       return cartList.size();
    }

    public class cartHolder extends RecyclerView.ViewHolder {
        TextView ProductNameTv,itemQuantityTv,itemQuantityPrice,ProductPriceTv;
        ImageView itemRemove,productimg;

        public cartHolder(@NonNull View itemView) {
            super(itemView);
            //init ui views
            ProductNameTv = itemView.findViewById(R.id.ProductNameTv);
            itemQuantityTv = itemView.findViewById(R.id.itemQuantityTv);
            itemQuantityPrice = itemView.findViewById(R.id.itemQuantityPrice);
            ProductPriceTv = itemView.findViewById(R.id.ProductPriceTv);
            itemRemove = itemView.findViewById(R.id.itemRemove);
            productimg = itemView.findViewById(R.id.productimage);
        }
    }
}
