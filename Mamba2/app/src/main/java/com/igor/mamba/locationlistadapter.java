package com.igor.mamba;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class locationlistadapter  extends RecyclerView.Adapter<locationlistadapter.locationholder>{

    View view;
    ArrayList<ModelDefaultLocation> locationModels ;
    private Context context;

    public locationlistadapter(Context mCtx, ArrayList<ModelDefaultLocation> locationModels){
        this.locationModels = locationModels;
        this.context = mCtx;
    }
    @NonNull
    @Override

    public locationlistadapter.locationholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_addresses,null);
        return new locationlistadapter.locationholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull locationholder holder, int position) {
        final ModelDefaultLocation ModelDefaultLocations = locationModels.get(position);

            holder.con.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("Range")
                @Override
                public void onClick(View view) {
                    SharedPreferences  mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = mPreferences.edit();
                    holder.con.setBackgroundColor(Color.parseColor("#FFA500"));
                   MyPreferences myPreferences = MyPreferences.getPreferences(context);
                   myPreferences.shippingfee(ModelDefaultLocations.getDroplocationdistance());
                   myPreferences.totalpricecomputed(0);
                   editor.putString("distance", String.valueOf(ModelDefaultLocations.getDroplocationdistance()));
                   editor.apply();
                }
            });
            holder.dropaddress.setText(ModelDefaultLocations.getDropadress());
            holder.recievername.setText(ModelDefaultLocations.getRecievername());
            holder.homeadress.setText(ModelDefaultLocations.getDroplocationname());
            holder.locationname.setText(ModelDefaultLocations.getDroplocationname()+ " "+ModelDefaultLocations.getAddresstype());



    }
    private void showAlert(String title, @Nullable String message) {




    }



    @Override
    public int getItemCount() {
        return locationModels.size();
    }

    public class locationholder extends RecyclerView.ViewHolder {

        ConstraintLayout con;
       TextView imageButton;
        TextView homeadress,locationname,recievername,dropaddress;

        public locationholder(@NonNull View itemView) {
            super(itemView);
              con      = view.findViewById(R.id.con);

            homeadress = view.findViewById(R.id.textView9);
            locationname = view.findViewById(R.id.textView15);
            recievername = view.findViewById(R.id.textView16);
            dropaddress = view.findViewById(R.id.textView19);


        }
    }
}
