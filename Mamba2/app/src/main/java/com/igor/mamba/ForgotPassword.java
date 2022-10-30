package com.igor.mamba;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.igor.mamba.User.RequestHandler;
import com.igor.mamba.User.User;
import com.igor.mamba.User.login;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.android.volley.Request.*;

public class ForgotPassword extends AppCompatActivity {

    EditText email;
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email = findViewById(R.id.phone);

        error = findViewById(R.id.error);

        findViewById(R.id.adduser).setOnClickListener(view -> {

            sendreset(email.getText().toString(),URLs.URL_RESSETPASS);
         }
         );
    }

    public void  sendreset(String email, String link) {

        class reset extends AsyncTask<Void, Void, String> {
            Dialog dialog;
            public void showDialogue( String condition ){


                dialog = new Dialog(ForgotPassword.this);
                if(condition == "show")
                {


                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.CENTER;

                    LayoutInflater layoutInflater = ForgotPassword.this.getLayoutInflater();
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
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_RESSETPASS, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showDialogue("show");
            }

            @SuppressLint("ResourceAsColor")
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        error.setText(obj.getString("message"));
                        error.setBackgroundColor(R.color.colorAccent);
                        error.setTextColor(Color.WHITE);
                        error.setVisibility(View.VISIBLE);
                        Properties props = new Properties();

                        Thread thread = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                try  {
                                    props.put("mail.smtp.host", "free.mboxhosting.com");
                                    props.put("mail.smtp.socketFactory.port", "465");
                                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                                    props.put("mail.smtp.auth", "true");
                                    props.put("mail.smtp.port", "465");
                                    Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                                        protected PasswordAuthentication getPasswordAuthentication() {
                                            return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
                                        }
                                    });
                                    try {
                                        MimeMessage mm = new MimeMessage(session);
                                        mm.setFrom(new InternetAddress(Config.EMAIL));
                                        mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                                        mm.setSubject("MAMBA PASSWORD RESET");
                                        mm.setContent(obj.getString("delivery"),
                                                "text/html");
                                        Transport.send(mm);
                                    } catch (MessagingException e) {
                                        e.printStackTrace();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        thread.start();



                    } else {

                               error.setText(obj.getString("message"));
                        error.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                },2000);
            }
        }

        //executing the async task
        reset ru = new reset();
        ru.execute();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void sendMail()
    {


    }

}