package com.example.mambaactivity.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mambaactivity.R;
import com.example.mambaactivity.adapters.AdapterOrderUser;
import com.example.mambaactivity.adapters.AdapterShop;
import com.example.mambaactivity.models.ModelOrderUser;
import com.example.mambaactivity.models.ModelShop;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MainUserActivity extends AppCompatActivity {
    private TextView nameTv,emailTv,phoneTv,tabShopsTv,tabOrdersTv;
    private RelativeLayout shopsRL,ordersRL;
    private ImageButton logoutBtn,editProfileBtn,settingsTv;
    private ImageView profileIv;
    private RecyclerView shopsRv,ordersRv;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private ArrayList<ModelShop> shopsList;
    private AdapterShop adapterShop;

    private ArrayList<ModelOrderUser> ordersList;
    private AdapterOrderUser adapterOrderUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        nameTv = findViewById(R.id.nameTv);
        emailTv = findViewById(R.id.emailTv);
        phoneTv = findViewById(R.id.phoneTv);
        tabShopsTv = findViewById(R.id.tabShopsTv);
        tabOrdersTv = findViewById(R.id.tabOrdersTv);
        logoutBtn = findViewById(R.id.logoutBtn);
        editProfileBtn = findViewById(R.id.editProfileBtn);
        profileIv = findViewById(R.id.profileIv);
        shopsRL = findViewById(R.id.shopsRL);
        ordersRL = findViewById(R.id.ordersRL);
        shopsRv = findViewById(R.id.shopsRv);
        ordersRv = findViewById(R.id.ordersRv);
        settingsTv = findViewById(R.id.settingsTv);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        //at start show shops ui
        showShopsUI();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //make offline
                //sign out
                //go to login activity
                makemeOffline();
            }
        });
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //open edit profile activity
                startActivity(new Intent(MainUserActivity.this, profileEditUserActivity.class));

            }
        });
        settingsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open add product activity
                startActivity(new Intent(MainUserActivity.this, SettingsActivity.class));
            }
        });
        tabShopsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               //show shops
               showShopsUI();
            }
        });
        tabOrdersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //show orders
                showOrdersUI();
            }
        });
    }

    private void showShopsUI()
    {
        //show shops ui, hide orders ui
        shopsRL.setVisibility(View.VISIBLE);
        ordersRL.setVisibility(View.GONE);

        tabShopsTv.setTextColor(getResources().getColor(R.color.colorAccent));
        tabShopsTv.setBackgroundResource(R.drawable.shape_rect05);

        tabOrdersTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        tabOrdersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }
    private void showOrdersUI()
    {
        //show orders ui, hide shops ui
        shopsRL.setVisibility(View.GONE);
        ordersRL.setVisibility(View.VISIBLE);

        tabOrdersTv.setTextColor(getResources().getColor(R.color.colorAccent));
        tabOrdersTv.setBackgroundResource(R.drawable.shape_rect05);

        tabShopsTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        tabShopsTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void makemeOffline()
    {
        //after logging in, make user online
        progressDialog.setMessage("Logging Out...");

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("online","false");

        //update value to db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        //update successfully
                        firebaseAuth.signOut();
                        checkUser();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        //failed updating
                        progressDialog.dismiss();
                        Toast.makeText(MainUserActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkUser()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null)
        {
            startActivity(new Intent(MainUserActivity.this, LoginActivity.class));
        }
        else
        {
            loadmyInfo();
        }
    }

    private void loadmyInfo()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        for (DataSnapshot ds: snapshot.getChildren())
                        {
                            //get user data
                            String name = ""+ds.child("name").getValue();
                            String email = ""+ds.child("email").getValue();
                            String phone = ""+ds.child("phone").getValue();
                            String profileImage = ""+ds.child("profileImage").getValue();
                            String accountType = ""+ds.child("accountType").getValue();
                            String city = ""+ds.child("city").getValue();

                            //set user data
                            nameTv.setText(name);
                            emailTv.setText(email);
                            phoneTv.setText(phone);
                            try {
                                Picasso.get().load(profileImage).placeholder(R.drawable.ic_person).into(profileIv);
                            }
                            catch (Exception e)
                            {
                                profileIv.setImageResource(R.drawable.ic_person);
                            }
                            //load only those shops that are in the vicinity
                            loadShops(city);
                            loadOrders();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadOrders()
    {
        //init order list
        ordersList = new ArrayList<>();

        //get orders
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                ordersList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    String uid = ""+ds.getRef().getKey();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Orders");
                    ref.orderByChild("orderBy").equalTo(firebaseAuth.getUid())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot)
                                {
                                    if (snapshot.exists()){
                                        for (DataSnapshot ds: snapshot.getChildren()){
                                            ModelOrderUser modelOrderUser = ds.getValue(ModelOrderUser.class);


                                            //add to list
                                            ordersList.add(modelOrderUser);
                                        }
                                        //setup adapter
                                        adapterOrderUser = new AdapterOrderUser(MainUserActivity.this,ordersList);

                                        //set to recyclerview
                                        ordersRv.setAdapter(adapterOrderUser);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadShops(final String mycity)
    {
        //init list
        shopsList = new ArrayList<>();
       DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
       ref.orderByChild("accountType").equalTo("Seller")
               .addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       //clear list before adding
                       shopsList.clear();
                       for (DataSnapshot ds: snapshot.getChildren()){
                         ModelShop modelShop = ds.getValue(ModelShop.class);

                         String shopCity = ""+ds.child("city").getValue();


                         //show only user city shops
                           if (shopCity.equals(mycity)){
                               shopsList.add(modelShop);
                           }

                           //if you want to display all shops,skip the iff statement and add this
                           //shopsList.add(modelShop);
                       }
                       //setup adapter
                       adapterShop = new AdapterShop(MainUserActivity.this,shopsList);
                       //set adapter to recyclerView
                       shopsRv.setAdapter(adapterShop);

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
    }
}