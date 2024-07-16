package com.example.foodagentcustomer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodagentcustomer.R;
import com.example.foodagentcustomer.models.ModelChat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.chatViewHolder> {

    private static int MESSAGE_TYPE_RIGHT = 0;
    private static int MESSAGE_TYPE_LEFT = 1;
    private Context context;
    private String ImageUrl;
    private List<ModelChat> chatList;
    private FirebaseAuth mAuth;
    private String myId;

    public ChatAdapter(Context context, String imageUrl, List<ModelChat> chatList) {
        this.context = context;
        ImageUrl = imageUrl;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public chatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        // get id of current logged in user
        mAuth = FirebaseAuth.getInstance();
        myId = mAuth.getCurrentUser().getUid();

        // inflating layout
        if (i == MESSAGE_TYPE_RIGHT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_right,parent,false);
            return new chatViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_left,parent,false);
            return new chatViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull chatViewHolder holder, int position) {
           String message = chatList.get(position).getMessage();
           String timeStamp = chatList.get(position).getTimeDate();
            String type = chatList.get(position).getType();
           // convert timeStamp into time and date format
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(Long.parseLong(timeStamp));
            String timeDate = DateFormat.format("dd/MM/yyyy hh:mm aa",cal).toString();

                holder.messageTv.setVisibility(View.VISIBLE);
                holder.messageTv.setText(message);

                holder.timeTv.setText(timeDate);

            try{
                Picasso.get().load(ImageUrl).into(holder.profileIv);
            }catch (Exception e){
                Picasso.get().load(R.drawable.ic_cart).into(holder.profileIv);
            }

            // set click listner and show dialouge when user click on message

            holder.messageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete");
                    builder.setMessage("Are you sure?");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                DeleteMessage(position);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog  alret = builder.create();
                    alret.show();
                    alret.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundColor(Color.BLUE);
                    alret.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLUE);

                }
            });


            // set isSeen
           if (position == chatList.size() - 1){
               if (chatList.get(position).isSeen()){
                   holder.isSeenTv.setVisibility(View.VISIBLE);
                   holder.isSeenTv.setText("Seen");
               }
               else {
                   holder.isSeenTv.setVisibility(View.VISIBLE);
                   holder.isSeenTv.setText("Delivered");
               }
           }
           else {
               holder.isSeenTv.setVisibility(View.GONE);
           }

    }


    @Override
    public int getItemCount() {
        return chatList.size();
    }

    private void DeleteMessage(int position) {
        // get time stamp of clicked message
        // compare the time stamp with messsages time stamp
        // where both values are same delete message

        String timeStamp = chatList.get(position).getTimeDate();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Chats");
        Query query = ref.orderByChild("timeDate").equalTo(timeStamp);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    // user can only delete his messages
                    // compare the sender id with current user id
                    // if match then delete message
                    if (ds.child("sender").getValue().equals(myId)){

                        // Either you can remove message or set message value message was deleted what ever you want
                        // 1) delete the message
                          if (chatList.get(position).getMessage().equals("This message was deleted.")){
                              ds.getRef().removeValue();
                          }
                          else{
                              // 2)set value of message. this message was deleted...
                              HashMap<String,Object> map = new HashMap<>();
                              map.put("message","This message was deleted.");
                              ds.getRef().updateChildren(map);
                              Toast.makeText(context, "message deleted..", Toast.LENGTH_SHORT).show();

                          }

                    }
                    else{
                        Toast.makeText(context, "you can delete only your messages", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemViewType(int position) {

        if (chatList.get(position).getSender().equals(myId)){
            return MESSAGE_TYPE_RIGHT;
        }
        else {
            return MESSAGE_TYPE_LEFT;
        }
    }

    // view holder class
    class chatViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profileIv;
        TextView messageTv,timeTv,isSeenTv;
        LinearLayout messageLayout;
        public chatViewHolder(@NonNull View itemView) {
            super(itemView);

            profileIv = itemView.findViewById(R.id.profileIV);
            messageTv = itemView.findViewById(R.id.messageTv);
            timeTv = itemView.findViewById(R.id.dateTimeTv);
            isSeenTv = itemView.findViewById(R.id.isSeenTv);
            messageLayout = itemView.findViewById(R.id.messageLayout);
        }
    }
}
