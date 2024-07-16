package com.example.foodagentcustomer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodagentcustomer.R;
import com.example.foodagentcustomer.models.OnClickListnere;
import com.example.foodagentcustomer.models.ProductsModel;
import com.example.foodagentcustomer.viewHolder.ProductViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> implements Filterable {

    public ArrayList<ProductsModel> productList,filterList;
    private Context context;
    FilterProduct filter;
    OnClickListnere listnere;

    public ProductAdapter(ArrayList<ProductsModel> productList, Context context) {

        this.productList = productList;
        this.filterList = filterList;
        this.context = context;


    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_row,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        final ProductsModel productModel = productList.get(position);

        String name = productModel.getProductName();
        String image = productModel.getProductImage();
        String category = productModel.getProductCategory();
        String price = productModel.getProductPrice();
        final String uid = productModel.getProductId();

        Picasso.get().load(image).error(R.drawable.ic_cart).into(holder.productImage);
        holder.nameTv.setText(name);
        holder.categoryTv.setText(category);
        holder.priceTv.setText("Rs: " +price);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listnere.clickListner(productModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new FilterProduct(this,filterList);
        }
        return filter;
    }

    public void setClickListner(OnClickListnere listner){
        this.listnere = listner;
    }
}
