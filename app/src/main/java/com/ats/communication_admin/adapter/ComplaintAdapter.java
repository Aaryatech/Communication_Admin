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
import com.ats.communication_admin.activity.ComplaintDetailActivity;
import com.ats.communication_admin.activity.ViewImageActivity;
import com.ats.communication_admin.bean.ComplaintData;
import com.ats.communication_admin.constants.Constants;
import com.ats.communication_admin.db.DatabaseHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MAXADMIN on 1/2/2018.
 */

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.MyViewHolder> {

    private ArrayList<ComplaintData> complaintList;
    private Context context;
    DatabaseHandler db;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc, date, count, franchise, customer;
        public LinearLayout llBack;
        public ImageView ivImage;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tvComplaintItem_Title);
            desc = view.findViewById(R.id.tvComplaintItem_Desc);
            date = view.findViewById(R.id.tvComplaintItem_Date);
            count = view.findViewById(R.id.tvComplaintItem_Count);
            franchise = view.findViewById(R.id.tvComplaintItem_Fr);
            customer = view.findViewById(R.id.tvComplaintItem_Customer);
            llBack = view.findViewById(R.id.llComplaintItem);
            ivImage = view.findViewById(R.id.ivComplaintItem_Image);
        }
    }

    public ComplaintAdapter(ArrayList<ComplaintData> complaintList, Context context) {
        this.complaintList = complaintList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_complaint_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ComplaintData complaint = complaintList.get(position);
        holder.title.setText(complaint.getTitle());
        holder.desc.setText(complaint.getDescription());
        holder.date.setText(complaint.getDate());
        holder.franchise.setText(complaint.getFrName());

        db = new DatabaseHandler(context);

        if (complaint.getCustomerName().equalsIgnoreCase("NA") || complaint.getCustomerName().isEmpty()) {
            holder.customer.setVisibility(View.GONE);
        } else {
            holder.customer.setVisibility(View.VISIBLE);
            holder.customer.setText("(Customer : " + complaint.getCustomerName() + "-" + complaint.getMobileNumber() + ")");
        }

        final String image = Constants.COMPLAINT_IMAGE_URL + complaint.getPhoto1();
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

        holder.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ComplaintDetailActivity.class);
                intent.putExtra("complaintId", complaint.getComplaintId());
                intent.putExtra("refresh", 0);
                context.startActivity(intent);
            }
        });

        if (db.getComplaintDetailUnreadCount(complaint.getComplaintId()) > 0) {
            holder.count.setVisibility(View.VISIBLE);
            holder.count.setText("" + db.getComplaintDetailUnreadCount(complaint.getComplaintId()));
        } else {
            holder.count.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

}
