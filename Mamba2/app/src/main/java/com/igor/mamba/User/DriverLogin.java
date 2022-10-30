package com.igor.mamba.User;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.igor.mamba.Driverhome;
import com.igor.mamba.Home;
import com.igor.mamba.R;
import com.igor.mamba.SharedPrefManager;
import com.igor.mamba.SharedPrefManagerDriver;
import com.igor.mamba.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DriverLogin extends AppCompatActivity implements View.OnClickListener{
    private EditText phonenumberText,PasswordEditText;
    Dialog dialog;
    TextView errortext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        errortext   = findViewById(R.id.error);
        phonenumberText = findViewById(R.id.phone);
        PasswordEditText = findViewById(R.id.Password);
        findViewById(R.id.adduserdriver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logindriver();
            }
        });
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.adduserdriver)
        {


        }
    }

    private void logindriver() {


       String phonenumber = phonenumberText.getText().toString().trim();
       String Password  = PasswordEditText.getText().toString().trim();
        class Loginuser extends AsyncTask<Void, Void, String> {



            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("phone", phonenumber);
                params.put("password", Password);


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOGINDRIVER, params);
            }

            public void showDialogue( String condition ){


                dialog = new Dialog(DriverLogin.this);
                if(condition == "show")
                {


                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.CENTER;

                    LayoutInflater layoutInflater = DriverLogin.this.getLayoutInflater();
                    View root = layoutInflater.inflate(R.layout.dialog_droppoint3, null);
                    dialog.setContentView(root);

                    dialog.setCancelable(false);
                    dialog.show();
                    window.setAttributes(wlp);


                }

                else if (condition == "dismiss")
                {
                    dialog.dismiss();

                    return ;
                }

            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                showDialogue("show");



            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                },2000);




                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        Driver user = new Driver(

                                userJson.getString("uuid"),
                                userJson.getString("firstName"),
                                userJson.getString("lastName"),
                                userJson.getString("email"),
                                userJson.getString("phone"),
                                userJson.getString("permit"));

                        //storing the user in shared preferences
                        SharedPrefManagerDriver.getInstance(getApplicationContext()).DriverLogin(user);
                        dialog.dismiss();
                        //starting the profile activity

                        startActivity(new Intent(getApplicationContext(), Driverhome.class));
                        DriverLogin.this.finish();
                    } else {
                        errortext.setVisibility(View.VISIBLE);
                        errortext.setText(obj.getString("message"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        Loginuser ru = new Loginuser();
        ru.execute();
    }
    }
