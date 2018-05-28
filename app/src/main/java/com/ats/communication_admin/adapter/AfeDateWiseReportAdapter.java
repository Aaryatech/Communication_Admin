package com.ats.communication_admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.communication_admin.R;
import com.ats.communication_admin.activity.AfeDetailReportActivity;
import com.ats.communication_admin.bean.AfeDateWiseReport;

import java.util.ArrayList;

/**
 * Created by MAXADMIN on 19/3/2018.
 */

public class AfeDateWiseReportAdapter extends RecyclerView.Adapter<AfeDateWiseReportAdapter.MyViewHolder> {

    private ArrayList<AfeDateWiseReport> reportList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView frName, route, person, date, score;
        public LinearLayout llBack;

        public MyViewHolder(View view) {
            super(view);
            frName = view.findViewById(R.id.tvCustomAfeReport_FrName);
            route = view.findViewById(R.id.tvCustomAfeReport_Route);
            person = view.findViewById(R.id.tvCustomAfeReport_Person);
            date = view.findViewById(R.id.tvCustomAfeReport_Date);
            score = view.findViewById(R.id.tvCustomAfeReport_Score);
            llBack = view.findViewById(R.id.llCustomAfeReport);
        }
    }

    public AfeDateWiseReportAdapter(ArrayList<AfeDateWiseReport> reportList, Context context) {
        this.reportList = reportList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_afe_datewise_report, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final AfeDateWiseReport report = reportList.get(position);

        holder.frName.setText(report.getFrName());
        holder.route.setText(report.getRouteName());
        holder.person.setText(report.getVisitPerson());
        holder.date.setText(report.getScoreHeaderDate());
        holder.score.setText("" + report.getTotalScore());

        holder.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AfeDetailReportActivity.class);
                intent.putExtra("HeaderId", report.getAfeScoreHeaderId());
                intent.putExtra("Title", report.getFrName());
                intent.putExtra("Date", report.getScoreHeaderDate());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

}
