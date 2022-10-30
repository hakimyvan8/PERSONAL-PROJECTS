package com.example.amakuru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private EditText searchEt;
    private ImageButton filterBtn;
    private RecyclerView sourceRv;

    private ArrayList<ModelSourceList> sourceLists;
    private AdaptersourceList adaptersourceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init ui views
        progressBar = findViewById(R.id.progressBar);
        searchEt = findViewById(R.id.searchEt);
       filterBtn = findViewById(R.id.filterBtn);
       sourceRv = findViewById(R.id.sourceRv);

       loadSources();
       //search
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //called as and when user type/remove letter
                try {
                    adaptersourceList.getFilter().filter(s);
                }
                catch (Exception e){

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadSources()
    {
        //init List
        sourceLists = new ArrayList<>();
        sourceLists.clear();

        progressBar.setVisibility(View.VISIBLE);
        String url="https://newsapi.org/v2/sources?apiKey="+Constants.API_KEY;
        //Request data
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                //response is gotten as string
                try {
                    //convert string to JSON object
                    JSONObject jsonObject = new JSONObject(response);
                    //get sources array from that object
                    JSONArray jsonArray = jsonObject.getJSONArray("sources");
                    //get all data from that array using loop
                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String name = jsonObject1.getString("name");
                        String description = jsonObject1.getString("description");
                        String url = jsonObject1.getString("url");
                        String category = jsonObject1.getString("category");
                        String country = jsonObject1.getString("country");
                        String language = jsonObject1.getString("language");

                        //set data to mode
                        ModelSourceList model = new ModelSourceList(""+id,
                                ""+name,
                                ""+description,
                                ""+url,
                                ""+category,
                                ""+country,
                                ""+language);//add model to the list

                        sourceLists.add(model);
                    }
                    progressBar.setVisibility(View.GONE);
                    //setup adapter
                    adaptersourceList = new AdaptersourceList(MainActivity.this,sourceLists);
                    //setAdaptertoRecyclerView
                    sourceRv.setAdapter(adaptersourceList);
                }
                catch (Exception e)
                {
                    //exception while loading json data
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error while requesting response
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        //ADD REQUEST TO QUEUE
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}