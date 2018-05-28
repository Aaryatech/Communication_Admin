package com.ats.communication_admin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ats.communication_admin.R;
import com.ats.communication_admin.bean.AfeDetailDisplay;

import java.util.ArrayList;

/**
 * Created by MAXADMIN on 20/3/2018.
 */

public class AfeDetailReportAdapter extends RecyclerView.Adapter<AfeDetailReportAdapter.MyViewHolder> {

    private ArrayList<AfeDetailDisplay> detailDisplays;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public CheckBox cbCheck;
        public EditText edRemark;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvCustomQuestion_Name);
            edRemark = view.findViewById(R.id.edCustomQuestion_Remark);
            cbCheck = view.findViewById(R.id.cbCustomQuestion_Check);
        }
    }


    public AfeDetailReportAdapter(ArrayList<AfeDetailDisplay> detailDisplays, Context context) {
        this.detailDisplays = detailDisplays;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_question_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final AfeDetailDisplay report = detailDisplays.get(position);

        holder.edRemark.setFocusable(false);
        holder.cbCheck.setClickable(false);
        holder.edRemark.setBackground(null);

        holder.name.setText(report.getQuestion());
        if (report.getQuePoint() == 1) {
            holder.cbCheck.setChecked(true);
        } else {
            holder.cbCheck.setChecked(false);
        }

        if (report.getRemark().isEmpty() || report.getRemark() == null) {
            holder.edRemark.setVisibility(View.GONE);
        } else {
            holder.edRemark.setVisibility(View.VISIBLE);
            holder.edRemark.setText("" + report.getRemark());
        }

    }

    @Override
    public int getItemCount() {
        return detailDisplays.size();
    }

}
