package com.igor.mamba;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.kittinunf.fuel.Fuel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BottomSheetDialogue extends BottomSheetDialogFragment {
    private ArrayList<locationModel> locationmodelarraylist;
    Context context;
    private Spinner spinner;
    private String locationid;

    TextInputEditText deliveryaddress,recievername,tagname;

    private ArrayList<String> names = new ArrayList<String>();
    private String URLstring = "http://"+URLs.IP+"/admin_area/Customer/populate.php";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.bottom_sheet_layout,container, false);

        deliveryaddress = v.findViewById(R.id.deliveryaddresses);
        recievername    = v.findViewById(R.id.recievername);
        tagname         = v.findViewById(R.id.tagname);
        spinner = v.findViewById(R.id.algo_button);
        MaterialButton course_button = v.findViewById(R.id.course_button);
        retrieveJSON();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                locationid = locationmodelarraylist.get(i).getLocationid();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        course_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest sr = new StringRequest(Request.Method.POST, URLs.postdata, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if(!(obj.getBoolean("error"))){


                                Toast.makeText(context,"updated",Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }) {
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("locationid", locationid);
                        params.put("userid", SharedPrefManager.getInstance(context).getUser().getUUID());

                        params.put("deliveryaddress",deliveryaddress.getText().toString());
                        params.put("tagname",tagname.getText().toString());
                        params.put("recievername",recievername.getText().toString());
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("Content-Type","application/x-www-form-urlencoded");
                        return params;
                    }
                };

              RequestQueue requestQueue =  Volley.newRequestQueue(context);
              requestQueue.add(sr);


            }
        });
        return v;
    }

    public BottomSheetDialogue(Context context){

        this.context = context;

    }

    private void retrieveJSON() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

             ///          Log.d("strrrrr", ">>" + response);

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
                                    names.add(locationmodelarraylist.get(i).getLocationName());

                                }



                                   ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item,names);
                                  spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                  spinner.setAdapter(spinnerArrayAdapter);



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
                      //  Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(stringRequest);


    }
}
