package com.ats.communication_admin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.communication_admin.R;
import com.ats.communication_admin.adapter.AfeDateWiseReportAdapter;
import com.ats.communication_admin.adapter.AfeDetailReportAdapter;
import com.ats.communication_admin.bean.AfeDateWiseReport;
import com.ats.communication_admin.bean.AfeDetailDisplay;
import com.ats.communication_admin.common.CommonDialog;
import com.ats.communication_admin.constants.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AfeDetailReportActivity extends AppCompatActivity {

    private RecyclerView rvReport;
    private TextView tvDate;
    int headerId;
    String title,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afe_detail_report);

        rvReport = findViewById(R.id.rvDetailReport);
        tvDate=findViewById(R.id.tvDetailReport_Date);

        headerId = getIntent().getExtras().getInt("HeaderId");
        title=getIntent().getExtras().getString("Title");
        date=getIntent().getExtras().getString("Date");
        setTitle(title+"\n"+date);
        tvDate.setText(""+date);

        getDetailReport(headerId);

    }

    public void getDetailReport(int headerId) {
        if (Constants.isOnline(this)) {

            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<AfeDetailDisplay>> arrayListCall = Constants.myInterface.getAfeDetailReport(headerId, 0);
            arrayListCall.enqueue(new Callback<ArrayList<AfeDetailDisplay>>() {
                @Override
                public void onResponse(Call<ArrayList<AfeDetailDisplay>> call, Response<ArrayList<AfeDetailDisplay>> response) {
                    try {
                        if (response.body() != null) {
                            ArrayList<AfeDetailDisplay> data = response.body();
                            Log.e("getDetailReport", "------------------------------" + data);
                            AfeDetailReportAdapter adapter = new AfeDetailReportAdapter(data, AfeDetailReportActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AfeDetailReportActivity.this);
                            rvReport.setLayoutManager(mLayoutManager);
                            rvReport.setItemAnimator(new DefaultItemAnimator());
                            rvReport.setAdapter(adapter);
                            commonDialog.dismiss();
                        } else {
                            commonDialog.dismiss();
                            Log.e("getDetailReport : ", " NULL");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("getDetailReport : ", " Exception : " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<AfeDetailDisplay>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("getDetailReport : ", " onFailure : " + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

}
