package com.igor.mamba.User;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.igor.mamba.R;
import com.igor.mamba.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class signup extends AppCompatActivity implements View.OnClickListener {
  Dialog dialog;
   EditText      firstnametxt,lastnametxt,Emailtxt,phone_numbrtxt,middlename,Business_permittxt,passwordtxt,bstate,bdistrict,bsector,Retype_passwordtxt;
  private String firstname,lastname,Email,phone_numbr,middlenamestring,Business_permit,district,sector,password,Retype_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        findViewById(R.id.backhome).setOnClickListener(this);
        findViewById(R.id.adduser).setOnClickListener(this);
        firstnametxt = findViewById(R.id.firstname);
        lastnametxt  = findViewById(R.id.lastname);
        Emailtxt     = findViewById(R.id.email);
        phone_numbrtxt  = findViewById(R.id.phonenumber);
        Business_permittxt = findViewById(R.id.permit);
        passwordtxt      = findViewById(R.id.Password);
        Retype_passwordtxt =findViewById(R.id.retypepassword);



    }

    public void signup()
    {

        firstname = firstnametxt.getText().toString();
        lastname =  lastnametxt.getText().toString();
        Email =   Emailtxt.getText().toString();
        Business_permit = Business_permittxt.getText().toString();
        phone_numbr = phone_numbrtxt.getText().toString();


        password   = passwordtxt.getText().toString();

        if (TextUtils.isEmpty(firstnametxt.getText().toString())) {
            firstnametxt.setError("Please enter FirstName");
           firstnametxt.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastnametxt.getText().toString())) {
            lastnametxt.setError("Please enter LastName");
            lastnametxt.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Emailtxt.getText().toString())) {
            Emailtxt.setError("Please enter your email");
            Emailtxt.requestFocus();
            return;
        }

        if (Business_permit.length() != 9) {
            Business_permittxt.setError("Please enter your Permit");
            Business_permittxt.requestFocus();
            return;
        }

            if(phone_numbr.length() != 10) {
                phone_numbrtxt.setError("Phone Number not Valid");
                phone_numbrtxt.requestFocus();
                return;
            }


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Emailtxt.getText().toString()).matches()) {
            Emailtxt.setError("Enter a valid email");
            Emailtxt.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(passwordtxt.getText().toString())) {
            passwordtxt.setError("Enter a password");
            passwordtxt.requestFocus();
            return;
        }


        class RegisterUser extends AsyncTask<Void, Void, String> {



            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("permit", Business_permit);
                params.put("email", Email);
                params.put("phone", phone_numbr);
                params.put("password", password);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }

            public boolean showDialogue( boolean  condition ){

                dialog = new Dialog(signup.this);
                if(condition)
                {
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                  //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.CENTER;

                    LayoutInflater layoutInflater = signup.this.getLayoutInflater();
                    View root = layoutInflater.inflate(R.layout.dialog_droppoint3, null);
                    dialog.setContentView(root);

                    dialog.setCancelable(false);
                    dialog.show();
                    window.setAttributes(wlp);

                    return  true;
                }

                else
                {
                    dialog.dismiss();

                }

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
                        dialog.dismiss();
                    }
                },2000);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),login.class));
                        signup.this.finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "User Already registered", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }


    public void onBackPressed() {
        super.onBackPressed();

        return;
    }
    public void onClick(View view) {

        if(view.getId() == R.id.backhome)
        {

            startActivity(new Intent(getApplicationContext(),login.class));

        }
        if(view.getId() == R.id.adduser)
        {

            signup();

        }


    }
}