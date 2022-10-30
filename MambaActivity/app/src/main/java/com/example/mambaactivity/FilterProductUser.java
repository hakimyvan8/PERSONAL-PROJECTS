package com.example.mambaactivity;

import android.widget.Filter;

import com.example.mambaactivity.adapters.AdapterProductSeller;
import com.example.mambaactivity.adapters.AdapterProductUser;
import com.example.mambaactivity.models.ModelProduct;

import java.util.ArrayList;

public class FilterProductUser extends Filter {

    private AdapterProductUser adapter;
    private ArrayList<ModelProduct> filterlist;

    public FilterProductUser(AdapterProductUser adapter, ArrayList<ModelProduct> filterlist) {
        this.adapter = adapter;
        this.filterlist = filterlist;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //validate data for search query
        if (constraint != null && constraint.length() >0)
        {
            //search filed not empty, searching something,perform search


            //change to upper case, to make case insensitive
            constraint = constraint.toString().toUpperCase();
            //store our filtered list
            ArrayList<ModelProduct> filteredModels = new ArrayList<>();
            for (int i =0; i<filterlist.size(); i++){
                //check,search by title and category
                if (filterlist.get(i).getProductTitle().toUpperCase().contains(constraint)||
                filterlist.get(i).getProductCategory().toUpperCase().contains(constraint))
                {
                    //add filtered data to list
                    filteredModels.add(filterlist.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;
        }
        else
        {
            //search filed empty, not searching, return original/all/complete list

            results.count = filterlist.size();
            results.values = filterlist;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results)
    {
        adapter.productsList = (ArrayList<ModelProduct>) results.values;
        //refresh adapter
        adapter.notifyDataSetChanged();
    }
}
