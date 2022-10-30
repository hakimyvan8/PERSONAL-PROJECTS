package com.igor.mamba.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.igor.mamba.ForgotPassword;
import com.igor.mamba.Home;
import com.igor.mamba.HomeSupplier;
import com.igor.mamba.R;
import com.igor.mamba.SharedPrefManager;
import com.igor.mamba.URLs;
import com.igor.mamba.delivery;
import com.igor.mamba.finance;
import com.igor.mamba.stock;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class login extends AppCompatActivity implements View.OnClickListener{
    private String phonenumber,Password;
    private EditText phonenumberText,PasswordEditText;
    Dialog dialog;
    TextView errortext,supplierPortal,driverPortal,FinancePortal,storePortal;
    SharedPrefManager sharedPrefManager;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.signup).setOnClickListener(this);
        findViewById(R.id.adduser).setOnClickListener(this);
        phonenumberText = findViewById(R.id.phone);
        PasswordEditText = findViewById(R.id.Password);

        errortext   = findViewById(R.id.error);


        findViewById(R.id.forgotpassword2).setOnClickListener(view -> {

            startActivity(new Intent(login.this, ForgotPassword.class));
        });
        findViewById(R.id.signa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(login.this,selectlogintype.class));

            }
        });




            errortext.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (errortext.getRight() - errortext.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                            errortext.setVisibility(View.INVISIBLE);
                            return true;
                        }
                    } else {
                        errortext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

                    }
                    return false;
                }
            });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        return;
    }

    public void login()
    {
        phonenumber = phonenumberText.getText().toString().trim();
        Password  = PasswordEditText.getText().toString().trim();
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
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
            }

            public void showDialogue( String condition ){


                 dialog = new Dialog(login.this);
                if(condition == "show")
                {


                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.CENTER;

                    LayoutInflater layoutInflater = login.this.getLayoutInflater();
                    View root = layoutInflater.inflate(R.layout.dialog_droppoint3, null);
                    dialog.setContentView(root);

                    dialog.setCancelable(true);
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
                        User user = new User(

                                userJson.getString("uuid"),
                                userJson.getString("firstName"),
                                userJson.getString("lastName"),
                                userJson.getString("email"),
                                userJson.getString("phone"),
                                userJson.getString("permit"),
                               "");

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        dialog.dismiss();
                        //starting the profile activity

                      startActivity(new Intent(getApplicationContext(), Home.class));
                      login.this.finish();
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




    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.signup)
        {

            startActivity(new Intent(getApplicationContext(),signup.class));
      }

        if(view.getId() == R.id.adduser)
        {

            if(!(TextUtils.isEmpty(phonenumberText.getText().toString()) && TextUtils.isEmpty(PasswordEditText.getText().toString())))
            {

                login();
            }

            else
            {

                errortext.setText("Fields can not be empty!");
                errortext.setVisibility(View.VISIBLE);

            }
        }
    }
}