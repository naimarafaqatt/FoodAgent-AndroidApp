package com.example.foodagentcustomer.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodagentcustomer.R;
import com.example.foodagentcustomer.activites.ChatActivity;
import com.example.foodagentcustomer.models.ModelChat;
import com.example.foodagentcustomer.models.SellerModel;
import com.example.foodagentcustomer.models.Users;
import com.example.foodagentcustomer.viewHolder.ChatListViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListViewHolder> {


    List<Users> usersList;
    Context context;
    String myId,theLastMessage;

    public ChatListAdapter(ArrayList<Users> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
        myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_chat_list,parent,false);
        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {

        String uName = usersList.get(position).getName();
        String uid = usersList.get(position).getUid();
        String dp = usersList.get(position).getImage();

        holder.userName.setText(uName);
        if (dp.equals("")){
            Picasso.get().load(R.drawable.icon_face).into(holder.dp);
        }
        else{
            Picasso.get().load(dp).into(holder.dp);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersList.clear();
                Intent it = new Intent(context, ChatActivity.class);
                it.putExtra("hisId",uid);
                context.startActivity(it);
            }
        });

        setLastMessage(holder.lastMessage,uid);

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    private void setLastMessage(TextView lastMessage, String uid) {
        theLastMessage = "default";
        FirebaseDatabase.getInstance().getReference("Chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        ModelChat chat = ds.getValue(ModelChat.class);

                        if (chat.getReciver().equals(myId) && chat.getSender().equals(uid) ||
                                chat.getReciver().equals(uid) && chat.getSender().equals(myId)){
                            if (chat.getType().equals("text")){
                                theLastMessage = chat.getMessage();
                            }

                        }
                    }

                    if (theLastMessage.equals("default")){
                        lastMessage.setText("Start Conversation");
                    }
                    else {
                        lastMessage.setText(theLastMessage);
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
