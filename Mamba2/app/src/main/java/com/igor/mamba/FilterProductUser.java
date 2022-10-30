package com.igor.mamba;

import android.widget.Filter;

import java.util.ArrayList;

public class FilterProductUser extends Filter {

    private productAdapter adapter;
    private ArrayList<Product>  filterList;

    public FilterProductUser(productAdapter adapter, ArrayList<Product> filterList){
        this.adapter = adapter;
        this.filterList = filterList;
    }
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if(constraint !=null && constraint.length() >0)
        {

            constraint = constraint.toString().toUpperCase();

            ArrayList<Product> filteredModels = new ArrayList();
            for(int i=0; i<filterList.size(); i++){
                if(filterList.get(i).getProduct_title().toUpperCase().contains(constraint))
                {
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;
        }
        else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.productList =(ArrayList<Product>) results.values;

        adapter.notifyDataSetChanged();

    }
}
