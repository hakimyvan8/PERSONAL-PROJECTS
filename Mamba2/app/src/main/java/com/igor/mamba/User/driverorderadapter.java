package com.igor.mamba.User;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.igor.mamba.Home;
import com.igor.mamba.R;
import com.igor.mamba.RequestHandle;
import com.igor.mamba.SharedPrefManager;
import com.igor.mamba.SharedPrefManagerDriver;
import com.igor.mamba.URLs;
import com.igor.mamba.ordermodel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class driverorderadapter extends RecyclerView.Adapter<driverorderadapter.orderHolder> {


    Dialog dialog;

    ArrayList<ordermodel> DriverorderList;
    private Context context;
    View view;
    public driverorderadapter(Context mCtx, ArrayList<ordermodel> orderList){
        this.DriverorderList = orderList;
        this.context = mCtx;
    }
    public driverorderadapter.orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.row_ordereditem, null);
        return new driverorderadapter.orderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull driverorderadapter.orderHolder holder, int position) {

             ordermodel om = DriverorderList.get(position);

             holder.rel.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     dialog = new Dialog(context);


                     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                     Window window = dialog.getWindow();
                     WindowManager.LayoutParams wlp = window.getAttributes();
                     wlp.gravity = Gravity.CENTER;

                     LayoutInflater layoutInflater = LayoutInflater.from(context);;
                     View root = layoutInflater.inflate(R.layout.dialog_droppoint4, null);
                     dialog.setContentView(root);

                     dialog.setCancelable(true);
                     dialog.show();
                     window.setAttributes(wlp);
                        TextView tv = root.findViewById(R.id.textView26);
                     String ordernumber = om.getOrdernumber();
                        tv.setText("Accept order number"+ordernumber+" for delivery");
                          root.findViewById(R.id.button11).setOnClickListener(new View.OnClickListener() {


                              @Override
                              public void onClick(View view) {



                                  StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.accept, new Response.Listener<String>() {
                                      @Override
                                      public void onResponse(String response) {

                                          try {
                                              JSONObject obj = new JSONObject(response);
                                              tv.setText(obj.getString("message"));





                                          } catch (JSONException e) {
                                              e.printStackTrace();
                                          }

                                      }
                                  }, new Response.ErrorListener() {
                                      @Override
                                      public void onErrorResponse(VolleyError error) {

                                          tv.setText(error.getMessage());

                                      }
                                  }){
                                      protected Map<String, String> getParams() throws AuthFailureError {
                                          Map<String, String> params = new HashMap<>();
                                          params.put("orderid", ordernumber);
                                          params.put("driverid", SharedPrefManagerDriver.getInstance(context).getDriver().getDriverId());
                                          return params;
                                      }
                                  };

                                  RequestHandle.getInstance(context).addToRequestQueue(stringRequest);
                                  /////run stringrequest here update order to 3 for picklup aproval and 4 for delivered
                              }
                          });

                     root.findViewById(R.id.button13).setOnClickListener(new View.OnClickListener() {


                         @Override
                         public void onClick(View view) {



                             StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.confirm, new Response.Listener<String>() {
                                 @Override
                                 public void onResponse(String response) {

                                     try {
                                         JSONObject obj = new JSONObject(response);
                                         tv.setText(obj.getString("message"));





                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     }

                                 }
                             }, new Response.ErrorListener() {
                                 @Override
                                 public void onErrorResponse(VolleyError error) {

                                     tv.setText(error.getMessage());

                                 }
                             }){
                                 protected Map<String, String> getParams() throws AuthFailureError {
                                     Map<String, String> params = new HashMap<>();
                                     params.put("orderid", ordernumber);
                                     params.put("driverid", SharedPrefManagerDriver.getInstance(context).getDriver().getDriverId());
                                     return params;
                                 }
                             };

                             RequestHandle.getInstance(context).addToRequestQueue(stringRequest);
                             /////run stringrequest here update order to 3 for picklup aproval and 4 for delivered
                         }
                     });


                 }
             });
             holder.ordernumber.setText(om.getOrdernumber());
             holder.orderlocation.setText(om.getLocation());
             holder.phonenumber.setText(om.getPhonenumber());
             holder.ordername.setText(om.getOrdername());
    }



   ;








    @Override
    public int getItemCount() {
        return DriverorderList.size();
    }

    public class orderHolder extends RecyclerView.ViewHolder {

        RelativeLayout rel;
        TextView ordernumber,orderlocation,ordername,status,phonenumber;//orderIdTver,statusTv,shopNameTv,amountTv,dateTv;

        public orderHolder(@NonNull View itemView) {
            super(itemView);

            rel       =  itemView.findViewById(R.id.rel);
            ordernumber = itemView.findViewById(R.id.orderIdTver);
            orderlocation = itemView.findViewById(R.id.dateTv);
            phonenumber = itemView.findViewById(R.id.amountTv);
            ordername = itemView.findViewById(R.id.shopNameTv);
             status = itemView.findViewById(R.id.statusTv);
        }
    }
}
