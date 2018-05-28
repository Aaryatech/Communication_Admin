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
import com.ats.communication_admin.adapter.FeedbackDetailAdapter;
import com.ats.communication_admin.bean.FeedbackData;
import com.ats.communication_admin.bean.FeedbackDetail;
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

public class FeedbackDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvFeedbackDetail;
    DatabaseHandler db;
    FeedbackDetailAdapter adapter;

    private LinearLayout llSend;
    private EditText edMessage;

    ImageView ivHeaderImage;
    TextView tvText, tvFr;

    int feedbackId = 0;
    FeedbackData data = new FeedbackData();

    int userId, refresh;
    String userName = "";

    ArrayList<FeedbackDetail> feedbackDetailArrayList;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_detail);

        rvFeedbackDetail = findViewById(R.id.rvFeedbackChat);
        edMessage = findViewById(R.id.edFeedbackDetail_Chat);
        llSend = findViewById(R.id.llFeedbackDetail_Send);
        ivHeaderImage = findViewById(R.id.ivFeedbackDetail_header);
        tvText = findViewById(R.id.tvFeedbackDetail_Text);
        tvFr = findViewById(R.id.tvFeedbackDetail_Fr);

        llSend.setOnClickListener(this);

        db = new DatabaseHandler(this);


        Toolbar toolbar = findViewById(R.id.toolbar_FeedbackDetail);
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
            feedbackId = mIntent.getIntExtra("feedbackId", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (feedbackId > 0) {
            db.updateFeedbackDetailRead(feedbackId);
            data = db.getFeedback(feedbackId);
        }

        Log.e("FEEDBACK DATA", "------------" + data);


        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar_FeedbackDetail);
        collapsingToolbar.setTitle(data.getTitle());
        collapsingToolbar.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));

        tvText.setText("" + data.getDescription());
        tvFr.setText("" + data.getUserName());

        final String image = Constants.FEEDBACK_IMAGE_URL + data.getPhoto();
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
                Intent intent = new Intent(FeedbackDetailActivity.this, ViewImageActivity.class);
                intent.putExtra("image", image);
                startActivity(intent);
            }
        });


        feedbackDetailArrayList = db.getAllSQLiteFeedbackDetails(feedbackId);

        adapter = new FeedbackDetailAdapter(feedbackDetailArrayList, this);
        rvFeedbackDetail.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvFeedbackDetail.setLayoutManager(mLayoutManager);
        rvFeedbackDetail.setItemAnimator(new DefaultItemAnimator());
        rvFeedbackDetail.setAdapter(adapter);
        if (adapter.getItemCount() > 0)
            rvFeedbackDetail.scrollToPosition(adapter.getItemCount() - 1);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("FEEDBACK_DETAIL_ADMIN")) {
                    handlePushNotification(intent);
                }
            }
        };

      /*  if (refresh == 1) {
            adapter.notifyDataSetChanged();
            if (adapter.getItemCount() > 0)
                rvFeedbackDetail.scrollToPosition(adapter.getItemCount() - 1);
        }*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        db.updateFeedbackDetailRead(feedbackId);
    }

    @Override
    protected void onResume() {
        super.onResume();

        feedbackDetailArrayList.clear();
        feedbackDetailArrayList = db.getAllSQLiteFeedbackDetails(feedbackId);

        adapter = new FeedbackDetailAdapter(feedbackDetailArrayList, this);
        rvFeedbackDetail.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvFeedbackDetail.setLayoutManager(mLayoutManager);
        rvFeedbackDetail.setItemAnimator(new DefaultItemAnimator());
        rvFeedbackDetail.setAdapter(adapter);

        if (adapter.getItemCount() > 0)
            rvFeedbackDetail.scrollToPosition(adapter.getItemCount() - 1);

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("FEEDBACK_DETAIL_ADMIN"));

        db.updateFeedbackDetailRead(feedbackId);

    }

    private void handlePushNotification(Intent intent) {

        Log.e("handlePushNotification", "------------------------------------**********");
        Gson gson = new Gson();
        FeedbackDetail feedbackDetail = gson.fromJson(intent.getStringExtra("message"), FeedbackDetail.class);

        String frName = intent.getStringExtra("frName");
        feedbackDetail.setFrName(frName);

        int fId = intent.getIntExtra("feedbackId", 0);
        int refreshId = intent.getIntExtra("refresh", 0);
        Log.e("Msg " + feedbackDetail, "Feedback id " + fId + "  Refresh : " + refreshId);
        if (feedbackDetail != null && fId != 0 && refreshId == 1) {

            if (feedbackId == fId) {
                feedbackDetailArrayList.add(feedbackDetail);
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() > 1) {
                    rvFeedbackDetail.scrollToPosition(adapter.getItemCount() - 1);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.llFeedbackDetail_Send) {

            String msg = edMessage.getText().toString();
            if (msg.isEmpty()) {
            } else {

                FeedbackDetail feedbackDetail = new FeedbackDetail(0, feedbackId, msg, 1, userId, userName, "", "2018-01-01", "00:00:00", 1);
                FeedbackDetail feedbackDetailDB = new FeedbackDetail(0, feedbackId, msg, 1, userId, userName, "", "2018-01-01", "00:00:00", 1);


                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
                String date = sdf.format(Calendar.getInstance().getTimeInMillis());
                String time = sdfTime.format(Calendar.getInstance().getTimeInMillis());

                Log.e("Feedback DETAIL", " LAST ID : " + db.getFeedbackDetailLastId());
                int lastId = db.getFeedbackDetailLastId() + 1;
                feedbackDetail.setTime(time);
                feedbackDetail.setDate(date);
                feedbackDetail.setFeedbackDetailId(lastId);

                db.addFeedbackDetails(feedbackDetail);
                edMessage.setText("");

                feedbackDetailArrayList.add(feedbackDetail);
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() > 0)
                    rvFeedbackDetail.scrollToPosition(adapter.getItemCount() - 1);


                saveFeedbackDetail(feedbackDetailDB);

            }
        }
    }

    public void saveFeedbackDetail(final FeedbackDetail feedbackDetail) {
        if (Constants.isOnline(this)) {

            Call<Info> infoCall = Constants.myInterface.saveFeedbackDetail(feedbackDetail);
            infoCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {
                            Info data = response.body();
                            if (data.getError()) {
                                Log.e("Feedback Detail : ", " ERROR : " + data.getMessage());
                            } else {
                                Log.e("Feedback Detail : ", " SUCCESS");
                                //edMessage.setText("");

                            }
                        } else {
                            Log.e("Feedback Detail : ", " NULL");
                        }
                    } catch (Exception e) {
                        Log.e("Feedback Detail : ", " Exception : " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    Log.e("Feedback Detail : ", " onFailure : " + t.getMessage());
                    t.printStackTrace();
                }
            });
        }
    }
}
