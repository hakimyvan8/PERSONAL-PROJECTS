package com.example.amakuru;

import android.widget.Filter;

import java.util.ArrayList;

public class Filtersourcelist extends Filter {

    private AdaptersourceList adapter;
    private ArrayList<ModelSourceList> filterlist;

    //constructor

    public Filtersourcelist(AdaptersourceList adapter, ArrayList<ModelSourceList> filterlist) {
        this.adapter = adapter;
        this.filterlist = filterlist;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint)
    {
        FilterResults results = new FilterResults();
        //checkConstraint Validity
        if (constraint !=null && constraint.length()>0)
        {
            //change to upper case to make it not case sensitive
            constraint = constraint.toString().toUpperCase();
            //store our filtered records
            ArrayList<ModelSourceList> filteredModels = new ArrayList<>();
            for (int i=0; i<filterlist.size(); i++){

                if (filterlist.get(i).getName().toUpperCase().contains(constraint)){
                    filteredModels.add(filterlist.get(i));

                }
            }

            results.count = filteredModels.size();
            results.values = filteredModels;
        }
        else {
            results.count = filterlist.size();
            results.values = filterlist;
        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.sourceLists = (ArrayList<ModelSourceList>) results.values;
        //refresh list
        adapter.notifyDataSetChanged();

    }
}
