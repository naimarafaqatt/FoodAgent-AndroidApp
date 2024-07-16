package com.example.foodagentcustomer.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foodagentcustomer.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView productImage;
    public TextView nameTv,categoryTv,priceTv;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        productImage = itemView.findViewById(R.id.productImage);
        nameTv = itemView.findViewById(R.id.nameTv);
        categoryTv = itemView.findViewById(R.id.categroyTv);
        priceTv = itemView.findViewById(R.id.priceTv);
    }
}
