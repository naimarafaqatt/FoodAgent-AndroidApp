package com.example.foodagentcustomer.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodagentcustomer.R;

public class ShopsViewHolder extends RecyclerView.ViewHolder {

    public ImageView shopImage;
    public TextView nameTv,descriptionTv,phonetv;

    public ShopsViewHolder(@NonNull View itemView) {
        super(itemView);

        shopImage = itemView.findViewById(R.id.shopImage);
        nameTv = itemView.findViewById(R.id.nameTv);
        descriptionTv = itemView.findViewById(R.id.descriptionTv);

        phonetv = itemView.findViewById(R.id.contactTv);
    }
}
