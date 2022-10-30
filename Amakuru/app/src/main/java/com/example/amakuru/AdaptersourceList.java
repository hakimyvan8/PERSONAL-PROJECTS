package com.example.amakuru;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptersourceList extends RecyclerView.Adapter<AdaptersourceList.HolderSourceList> implements Filterable {

    private Context context;
    public ArrayList<ModelSourceList> sourceLists, filterList;
    private  Filtersourcelist filter;

   //constructor
    public AdaptersourceList(Context context, ArrayList<ModelSourceList> sourceLists) {
        this.context = context;
        this.sourceLists = sourceLists;
        this.filterList = sourceLists;
    }

    @NonNull
    @Override
    public HolderSourceList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_source_list.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_source_list,parent,false);
        return new HolderSourceList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderSourceList holder, int position) {
        //get data
        ModelSourceList model = sourceLists.get(position);
        String id = model.getId();
        String name = model.getName();
        String description = model.getDescription();
        String country = model.getCountry();
        String category = model.getCategory();
        String language = model.getLanguage();

        //set data to  ui views

        holder.nameTv.setText(name);
        holder.descriptionTv.setText(description);
        holder.categoryTv.setText("Category:"+category);
        holder.countryTv.setText("Country:"+country);
        holder.languageTv.setText("Language:"+language);

        //handles clicks
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return sourceLists.size();//return size of list
    }

    @Override
    public Filter getFilter() {
        if (filter==null){
            filter = new Filtersourcelist(this,filterList);
        }
        return filter ;
    }


    //viewHolderClass
    class HolderSourceList extends RecyclerView.ViewHolder{
        //UI views from row_source_list.xml
        TextView nameTv,descriptionTv,countryTv,categoryTv,languageTv;

        public HolderSourceList(@NonNull View itemView) {
            super(itemView);
            //init UI views
            nameTv = itemView.findViewById(R.id.nameTv);
           descriptionTv = itemView.findViewById(R.id.descriptionTv);
           countryTv = itemView.findViewById(R.id.countryTv);
           categoryTv = itemView.findViewById(R.id.categoryTv);
           languageTv = itemView.findViewById(R.id.languageTv);
        }
    }
}
