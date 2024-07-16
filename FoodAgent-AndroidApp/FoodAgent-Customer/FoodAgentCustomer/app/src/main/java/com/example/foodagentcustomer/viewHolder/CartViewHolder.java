package com.example.foodagentcustomer.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodagentcustomer.R;

public class CartViewHolder extends RecyclerView.ViewHolder {
    public TextView productName,productTotalPrice,productSize,productQuantity;
    public ImageView productImage;
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        productImage = itemView.findViewById(R.id.productImage);
        productName = itemView.findViewById(R.id.productName);
        productTotalPrice = itemView.findViewById(R.id.totalPrice);
        productQuantity = itemView.findViewById(R.id.productQuantity);
        productSize = itemView.findViewById(R.id.productSize);
    }
}
