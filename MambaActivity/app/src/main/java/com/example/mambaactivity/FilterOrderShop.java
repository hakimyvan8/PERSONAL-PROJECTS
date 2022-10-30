package com.example.mambaactivity;

import android.widget.Filter;

import com.example.mambaactivity.adapters.AdapterOrderShop;
import com.example.mambaactivity.adapters.AdapterProductSeller;
import com.example.mambaactivity.models.ModelOrderShop;
import com.example.mambaactivity.models.ModelProduct;

import java.util.ArrayList;

public class FilterOrderShop extends Filter {

    private AdapterOrderShop adapter;
    private ArrayList<ModelOrderShop> filterlist;

    public FilterOrderShop(AdapterOrderShop adapter, ArrayList<ModelOrderShop> filterlist) {
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
            ArrayList<ModelOrderShop> filteredModels = new ArrayList<>();
            for (int i =0; i<filterlist.size(); i++){
                //check,search by title and category
                if (filterlist.get(i).getOrderStatus().toUpperCase().contains(constraint))
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
        adapter.orderShopArrayList = (ArrayList<ModelOrderShop>) results.values;
        //refresh adapter
        adapter.notifyDataSetChanged();
    }
}
