package com.ats.communication_admin.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ats.communication_admin.R;
import com.ats.communication_admin.bean.SchedulerList;

import java.util.ArrayList;

/**
 * Created by MAXADMIN on 30/1/2018.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolder> {

    private ArrayList<SchedulerList> noticeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc, date;
        public LinearLayout llBack;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tvNoticeItem_Title);
            desc = view.findViewById(R.id.tvNoticeItem_Desc);
            date = view.findViewById(R.id.tvNoticeItem_Date);
            llBack = view.findViewById(R.id.llNoticeItem);
        }
    }

    public NoticeAdapter(ArrayList<SchedulerList> noticeList) {
        this.noticeList = noticeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_notice_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SchedulerList notice = noticeList.get(position);
        holder.title.setText(notice.getSchOccasionname());
        holder.desc.setText(notice.getSchMessage());
        holder.date.setText(notice.getSchDate());

        if (position % 2 == 0) {
            holder.llBack.setBackgroundColor(Color.parseColor("#F5F1EE"));
        } else {
            holder.llBack.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

}
