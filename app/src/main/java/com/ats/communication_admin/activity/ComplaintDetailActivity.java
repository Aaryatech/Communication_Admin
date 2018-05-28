package com.ats.communication_admin.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ats.communication_admin.R;
import com.ats.communication_admin.adapter.ComplaintDetailAdapter;
import com.ats.communication_admin.bean.ComplaintData;
import com.ats.communication_admin.bean.ComplaintDetail;
import com.ats.communication_admin.bean.Franchisee;
import com.ats.communication_admin.bean.Info;
import com.ats.communication_admin.bean.User;
import com.ats.communication_admin.constants.Constants;
import com.ats.communication_admin.db.DatabaseHandler;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvComplaintDetail;
    DatabaseHandler db;

    ComplaintDetailAdapter adapter;

    private LinearLayout llSend;
    private EditText edMessage;

    ImageView ivHeaderImage;
    TextView tvText, tvFr;

    int complaintId = 0;
    ComplaintData data = new ComplaintData();

    int userId, refresh;
    String userName="";

    ArrayList<ComplaintDetail> complaintDetailArrayList;

    private BroadcastReceiver mRegistrationBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_detail);

        rvComplaintDetail = findViewById(R.id.rvComplaintChat);

        edMessage = findViewById(R.id.edComplaintDetail_Chat);
        llSend = findViewById(R.id.llComplaintDetail_Send);
        ivHeaderImage = findViewById(R.id.ivComplaintDetail_header);
        tvText = findViewById(R.id.tvComplaintDetail_Text);
        tvFr = findViewById(R.id.tvComplaintDetail_Fr);

        llSend.setOnClickListener(this);

        db = new DatabaseHandler(this);


        Toolbar toolbar = findViewById(R.id.toolbar_ComplaintDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE);
        Gson gson = new Gson();
        String json2 = pref.getString("User", "");
        User userBean = gson.fromJson(json2, User.class);
        try {
            if (userBean != null) {
                userId = userBean.getId();
                userName = userBean.getUsername();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Intent mIntent = getIntent();
            complaintId = mIntent.getIntExtra("complaintId", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("Complaint Id", "----------------" + complaintId);

        if (complaintId > 0) {
            db.updateComplaintDetailRead(complaintId);
            data = db.getComplaint(complaintId);
        }


        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar_ComplaintDetail);
        collapsingToolbar.setTitle(data.getTitle());
        collapsingToolbar.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));

        tvText.setText("" + data.getDescription());
        tvFr.setText("" + data.getFrName());

        final String image = Constants.COMPLAINT_IMAGE_URL + data.getPhoto1();
        try {
            Picasso.with(this)
                    .load(image)
                    .placeholder(android.R.color.transparent)
                    .error(android.R.color.transparent)
                    .into(ivHeaderImage);
        } catch (Exception e) {
        }

        ivHeaderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComplaintDetailActivity.this, ViewImageActivity.class);
                intent.putExtra("image", image);
                startActivity(intent);
            }
        });

        complaintDetailArrayList = db.getAllSQLiteComplaintDetails(complaintId);

        adapter = new ComplaintDetailAdapter(complaintDetailArrayList, this);
        rvComplaintDetail.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvComplaintDetail.setLayoutManager(mLayoutManager);
        rvComplaintDetail.setItemAnimator(new DefaultItemAnimator());
        rvComplaintDetail.setAdapter(adapter);
        if (adapter.getItemCount() > 0)
            rvComplaintDetail.scrollToPosition(adapter.getItemCount() - 1);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("COMPLAINT_DETAIL_ADMIN")) {
                    handlePushNotification(intent);
                }
            }
        };

        if (refresh == 1) {
            adapter.notifyDataSetChanged();
            if (adapter.getItemCount() > 0)
                rvComplaintDetail.scrollToPosition(adapter.getItemCount() - 1);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        db.updateComplaintDetailRead(complaintId);
    }

    @Override
    protected void onResume() {
        super.onResume();

        complaintDetailArrayList.clear();
        complaintDetailArrayList = db.getAllSQLiteComplaintDetails(complaintId);

        adapter = new ComplaintDetailAdapter(complaintDetailArrayList, this);
        rvComplaintDetail.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvComplaintDetail.setLayoutManager(mLayoutManager);
        rvComplaintDetail.setItemAnimator(new DefaultItemAnimator());
        rvComplaintDetail.setAdapter(adapter);
        if (adapter.getItemCount() > 0)
            rvComplaintDetail.scrollToPosition(adapter.getItemCount() - 1);

        // registering the receiver for new notification
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("COMPLAINT_DETAIL_ADMIN"));

        db.updateComplaintDetailRead(complaintId);

    }

    private void handlePushNotification(Intent intent) {

        Log.e("handlePushNotification", "------------------------------------**********");
        Gson gson = new Gson();
        ComplaintDetail complaintDetail = gson.fromJson(intent.getStringExtra("message"), ComplaintDetail.class);

        String frName = intent.getStringExtra("frName");
        complaintDetail.setFrName(frName);
        Log.e("ComplaintDetail : ","----------------"+complaintDetail);

        int cId = intent.getIntExtra("complaintId", 0);
        int refreshId = intent.getIntExtra("refresh", 0);
        Log.e("Msg " + complaintDetail, "Complaint id " + cId + "  Refresh : " + refreshId);
        if (complaintDetail != null && cId != 0 && refreshId == 1) {

            if (complaintId == cId) {
                complaintDetailArrayList.add(complaintDetail);
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() > 1) {
                    rvComplaintDetail.scrollToPosition(adapter.getItemCount() - 1);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.llComplaintDetail_Send) {

            String msg = edMessage.getText().toString();
            if (msg.isEmpty()) {
            } else {

                ComplaintDetail complaintDetail = new ComplaintDetail(0, complaintId, msg, "", "2018-1-1", "00:00:00", 1, userId, userName, 1);
                ComplaintDetail complaintDetailDB = new ComplaintDetail(0, complaintId, msg, "", "2018-1-1", "00:00:00", 1, userId, userName, 1);


                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
                String date = sdf.format(Calendar.getInstance().getTimeInMillis());
                String time = sdfTime.format(Calendar.getInstance().getTimeInMillis());

                Log.e("COMPLAINT DETAIL", " LAST ID : " + db.getComplaintDetailLastId());
                int lastId = db.getComplaintDetailLastId() + 1;
                complaintDetail.setTime(time);
                complaintDetail.setDate(date);
                complaintDetail.setCompDetailId(lastId);

                db.addComplaintDetails(complaintDetail);
                edMessage.setText("");

                complaintDetailArrayList.add(complaintDetail);
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() > 0)
                    rvComplaintDetail.scrollToPosition(adapter.getItemCount() - 1);


                saveComplaintDetail(complaintDetailDB);

            }

        }

    }


    public void saveComplaintDetail(final ComplaintDetail complaintDetail) {
        if (Constants.isOnline(this)) {

            Call<Info> infoCall = Constants.myInterface.saveComplaintDetail(complaintDetail);
            infoCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {
                            Info data = response.body();
                            if (data.getError()) {
                                Log.e("Complaint Detail : ", " ERROR : " + data.getMessage());
                            } else {
                                Log.e("Complaint Detail : ", " SUCCESS");
                                //edMessage.setText("");

                            }
                        } else {
                            Log.e("Complaint Detail : ", " NULL");
                        }
                    } catch (Exception e) {
                        Log.e("Complaint Detail : ", " Exception : " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    Log.e("Complaint Detail : ", " onFailure : " + t.getMessage());
                    t.printStackTrace();
                }
            });
        }
    }
}
