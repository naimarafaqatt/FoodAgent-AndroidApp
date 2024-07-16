package com.example.foodagentcustomer.adapters;

import android.widget.Filter;


import com.example.foodagentcustomer.models.ProductsModel;

import java.util.ArrayList;

public class FilterProduct extends Filter{

    ProductAdapter adapter;
    ArrayList<ProductsModel> productList;


    public FilterProduct(ProductAdapter adapter, ArrayList<ProductsModel> productList) {
        this.adapter = adapter;
        this.productList = productList;

    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        // validate data for search query
        if (constraint != null && constraint.length()>0){
            //change to upper case to make case insensitive

            constraint = constraint.toString().toUpperCase();

            // store our filterd result

            ArrayList<ProductsModel> filterModel = new ArrayList<>();

            for (int i = 0; i<productList.size();i++){
                // check search by title and
                if (productList.get(i).getProductName().toUpperCase().contains(constraint)){
                    // add filter data to list
                    filterModel.add(productList.get(i));
                }
            }

            results.count = filterModel.size();
            results.values = filterModel;
        }else{
            results.count = productList.size();
            results.values = productList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.productList = (ArrayList<ProductsModel>) filterResults.values;
        // refresh adapter
        adapter.notifyDataSetChanged();
    }
}
