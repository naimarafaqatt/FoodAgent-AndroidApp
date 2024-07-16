package com.example.foodagentcustomer.adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodagentcustomer.R;
import com.example.foodagentcustomer.models.OrderModel;
import com.example.foodagentcustomer.viewHolder.OrdersViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrdersViewHolder> {


    ArrayList<OrderModel> orderList = new ArrayList<>();
    Context context;
    String myNumber;

    public OrderAdapter(Context context, ArrayList<OrderModel> orderList, String myNumber) {
        this.context = context;
        this.orderList = orderList;
        this.myNumber = myNumber;
    }
    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_orders,parent,false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {

        OrderModel orderModel = orderList.get(position);

        holder.customerName.setText(orderModel.getCustomerName());
        holder.customerNumber.setText("Phone No.: " + orderModel.getCustomerNumber());
        holder.totalPrice.setText("Total Price: "+orderModel.getTotalPrice());
        holder.customerAddress.setText("Address: "+orderModel.getCustomerAddress());
        holder.dateTime.setText("Date Time: " + orderModel.getDate() + " , " + orderModel.getTime());
        holder.statusTv.setText("Status: " + orderModel.getStatus());
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                showPopUpMenu(orderModel,holder.btnMore);
            }
        });

    }



    @Override
    public int getItemCount() {
        return orderList.size();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPopUpMenu(OrderModel orderModel, ImageView btnMore) {
        PopupMenu popupMenu = new PopupMenu(context,btnMore, Gravity.END);

        popupMenu.getMenu().add(Menu.NONE,0,0,"Delete");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == 0){

                    deleteOrder(orderModel);
                }

                return false;
            }
        });
        popupMenu.show();
    }

    private void deleteOrder(OrderModel orderModel) {

        Log.e("TAG", "deleteOrder: " + orderModel.getOrderId() );
        FirebaseDatabase.getInstance().getReference("Orders")
                .child(orderModel.getOrderId())
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context, "Deleted....", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG", "onFailure: " + e.getMessage());
            }
        });
    }

}
