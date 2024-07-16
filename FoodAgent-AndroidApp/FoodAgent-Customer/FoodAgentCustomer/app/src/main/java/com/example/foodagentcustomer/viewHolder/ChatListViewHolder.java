package com.example.foodagentcustomer.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodagentcustomer.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListViewHolder extends RecyclerView.ViewHolder {

   public CircleImageView dp;
    public TextView userName,lastMessage;

    public ChatListViewHolder(@NonNull View itemView) {
        super(itemView);

        dp = itemView.findViewById(R.id.chatlistDp);
        userName = itemView.findViewById(R.id.chatListUser_name);
        lastMessage = itemView.findViewById(R.id.chatListLastMessage);
    }
}
