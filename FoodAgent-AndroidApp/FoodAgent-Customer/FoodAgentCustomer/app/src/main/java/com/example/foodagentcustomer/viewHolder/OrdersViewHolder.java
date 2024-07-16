package com.example.foodagentcustomer.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodagentcustomer.R;

public class OrdersViewHolder extends RecyclerView.ViewHolder {

    public TextView customerName,customerAddress,customerNumber,dateTime,totalPrice,statusTv;
    public ImageView btnMore;
    public OrdersViewHolder(@NonNull View itemView) {
        super(itemView);

        customerName = itemView.findViewById(R.id.customerName);
        customerAddress = itemView.findViewById(R.id.customerAddress);
        customerNumber = itemView.findViewById(R.id.customerNumber);
        dateTime = itemView.findViewById(R.id.dateTime);
        totalPrice = itemView.findViewById(R.id.totalPrice);
        statusTv = itemView.findViewById(R.id.statusTv);
        btnMore = itemView.findViewById(R.id.btnMore);
    }
}
