package com.example.chatforum.Adapters;


import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatforum.Models.AllMethods;
import com.example.chatforum.Models.Message;
import com.example.chatforum.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MessageAdapter extends RecyclerView. Adapter<MessageAdapter.MessageAdapterViewHolder>{


    Context context;
    List<Message> messages;
    DatabaseReference messagedb;

    public MessageAdapter(Context context,List<Message>messages,DatabaseReference messagedb)
    {
        this.context = context;
        this.messagedb = messagedb;
        this.messages=messages;
    }

    @NonNull
    @Override
    public MessageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message,parent,false);
        return  new MessageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapterViewHolder holder, int position) {
        Message message = messages.get(position);

        if(message.getMessage().equals(AllMethods.name))
        {
            holder.tvTitle.setText("You: "+message.getMessage());
            holder.tvTitle.setGravity(Gravity.START);
            holder.l1.setBackgroundColor(Color.parseColor("#EF9E73"));
        }
        else
        {
            holder.tvTitle.setText(message.getName()+":"+message.getMessage());
            holder.itDelete.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageAdapterViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvTitle;
        ImageButton itDelete;
        LinearLayout l1;

        public MessageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            itDelete = (ImageButton)itemView.findViewById(R.id.itDelete);
            l1 = (LinearLayout) itemView.findViewById(R.id.l1Message) ;

            itDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    messagedb.child(messages.get(getAdapterPosition()).getKey()).removeValue();
                }
            });

        }
    }
}