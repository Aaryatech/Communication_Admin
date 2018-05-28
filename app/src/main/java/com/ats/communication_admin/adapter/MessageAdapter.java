package com.ats.communication_admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ats.communication_admin.R;
import com.ats.communication_admin.activity.ViewImageActivity;
import com.ats.communication_admin.bean.Message;
import com.ats.communication_admin.constants.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MAXADMIN on 30/1/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private ArrayList<Message> messageList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc, date;
        public ImageView ivImage;
        public LinearLayout llBack;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tvMessageItem_Title);
            desc = view.findViewById(R.id.tvMessageItem_Desc);
            date = view.findViewById(R.id.tvMessageItem_Date);
            llBack = view.findViewById(R.id.llMessageItem);
            ivImage = view.findViewById(R.id.ivMessageItem_Image);
        }
    }

    public MessageAdapter(ArrayList<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_message_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Message msg = messageList.get(position);
        holder.title.setText(msg.getMsgHeader());
        holder.desc.setText(msg.getMsgDetails());
        holder.date.setText(msg.getMsgFrdt());

        final String image = Constants.MESSAGE_IMAGE_URL + msg.getMsgImage();
        try {
            Picasso.with(context)
                    .load(image)
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .into(holder.ivImage);
        } catch (Exception e) {
        }

        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewImageActivity.class);
                intent.putExtra("image", image);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

}
