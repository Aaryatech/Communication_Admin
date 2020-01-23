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
import com.ats.communication_admin.adapter.SuggestionDetailAdapter;
import com.ats.communication_admin.bean.Info;
import com.ats.communication_admin.bean.SuggestionData;
import com.ats.communication_admin.bean.SuggestionDetail;
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

public class SuggestionDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvSuggestionDetail;
        DatabaseHandler db;
    SuggestionDetailAdapter adapter;

    private LinearLayout llSend;
    private EditText edMessage;

    ImageView ivHeaderImage;
    TextView tvText, tvFr;

    int suggestionId = 0;
    SuggestionData data = new SuggestionData();

    int userId;
    String userName = "";

    ArrayList<SuggestionDetail> suggestionDetailArrayList;

    private BroadcastReceiver mRegistrationBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_detail);

        rvSuggestionDetail = findViewById(R.id.rvSuggestionChat);
        edMessage = findViewById(R.id.edSuggestionDetail_Chat);
        llSend = findViewById(R.id.llSuggestionDetail_Send);
        ivHeaderImage = findViewById(R.id.ivSuggestionDetail_header);
        tvText = findViewById(R.id.tvSuggestionDetail_Text);
        tvFr = findViewById(R.id.tvSuggestionDetail_Fr);

        llSend.setOnClickListener(this);

        db = new DatabaseHandler(this);


        Toolbar toolbar = findViewById(R.id.toolbar_suggestionDetail);
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
            suggestionId = mIntent.getIntExtra("suggestionId", 0);
            // refresh = mIntent.getIntExtra("refresh", 0);
            db.updateSuggestionDetailRead(suggestionId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (suggestionId > 0) {
            db.updateSuggestionDetailRead(suggestionId);
            data = db.getSuggestion(suggestionId);
        }


        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(data.getTitle());
        collapsingToolbar.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));

        tvText.setText("" + data.getDescription());
        tvFr.setText("" + data.getFrName());

        final String image = Constants.SUGGESTION_IMAGE_URL + data.getPhoto();
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
                Intent intent = new Intent(SuggestionDetailActivity.this, ViewImageActivity.class);
                intent.putExtra("image", image);
                startActivity(intent);
            }
        });


        suggestionDetailArrayList = db.getAllSQLiteSuggestionDetails(suggestionId);

        adapter = new SuggestionDetailAdapter(suggestionDetailArrayList, this);
        rvSuggestionDetail.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvSuggestionDetail.setLayoutManager(mLayoutManager);
        rvSuggestionDetail.setItemAnimator(new DefaultItemAnimator());
        rvSuggestionDetail.setAdapter(adapter);

        if (adapter.getItemCount() > 0)
            rvSuggestionDetail.scrollToPosition(adapter.getItemCount() - 1);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("SUGGESTION_DETAIL_ADMIN")) {
                    handlePushNotification(intent);
                }
            }
        };

      /*  if (refresh == 1) {
            adapter.notifyDataSetChanged();
            if (adapter.getItemCount() > 0)
                rvSuggestionDetail.scrollToPosition(adapter.getItemCount() - 1);
        }*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("SUGG DET", "  ON PAUSE");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        db.updateSuggestionDetailRead(suggestionId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("SUGG DET", "  ON RESUME");

        suggestionDetailArrayList.clear();
        suggestionDetailArrayList = db.getAllSQLiteSuggestionDetails(suggestionId);
        adapter = new SuggestionDetailAdapter(suggestionDetailArrayList, this);
        rvSuggestionDetail.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvSuggestionDetail.setLayoutManager(mLayoutManager);
        rvSuggestionDetail.setItemAnimator(new DefaultItemAnimator());
        rvSuggestionDetail.setAdapter(adapter);

        if (adapter.getItemCount() > 0)
            rvSuggestionDetail.scrollToPosition(adapter.getItemCount() - 1);


        // registering the receiver for new notification
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("SUGGESTION_DETAIL_ADMIN"));

        db.updateSuggestionDetailRead(suggestionId);

    }


    private void handlePushNotification(Intent intent) {

        Log.e("handlePushNotification", "------------------------------------**********");
        Gson gson = new Gson();
        SuggestionDetail suggestionDetail = gson.fromJson(intent.getStringExtra("message"), SuggestionDetail.class);

        String frName = intent.getStringExtra("frName");
        suggestionDetail.setFrName(frName);

        int sugId = intent.getIntExtra("suggestionId", 0);
        // int refreshId = intent.getIntExtra("refresh", 0);
        Log.e("Msg " + suggestionDetail, "Suggestion id " + sugId);
        if (suggestionDetail != null && sugId != 0) {

            suggestionDetailArrayList.add(suggestionDetail);
            adapter.notifyDataSetChanged();
            if (adapter.getItemCount() > 1) {
                rvSuggestionDetail.scrollToPosition(adapter.getItemCount() - 1);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.llSuggestionDetail_Send) {

            String msg = edMessage.getText().toString();
            if (msg.isEmpty()) {
            } else {

                SuggestionDetail suggestionDetail = new SuggestionDetail(0, suggestionId, msg, 1, userId, userName, "", "2018-1-1", "00:00:00", 1);
                SuggestionDetail suggestionDetailDB = new SuggestionDetail(0, suggestionId, msg, 1, userId, userName, "", "2018-1-1", "00:00:00", 1);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
                String date = sdf.format(Calendar.getInstance().getTimeInMillis());
                String time = sdfTime.format(Calendar.getInstance().getTimeInMillis());

                Log.e("SUGG DETAIL", " LAST ID : " + db.getSuggestionDetailLastId());
                int lastId = db.getSuggestionDetailLastId() + 1;
                suggestionDetail.setTime(time);
                suggestionDetail.setDate(date);
                suggestionDetail.setSuggestionDetailId(lastId);

                db.addSuggestionDetails(suggestionDetail);
                edMessage.setText("");

                suggestionDetailArrayList.add(suggestionDetail);
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() > 0)
                    rvSuggestionDetail.scrollToPosition(adapter.getItemCount() - 1);


                saveSuggestionDetail(suggestionDetailDB);

            }


        }
    }


    public void saveSuggestionDetail(final SuggestionDetail suggestionDetail) {
        if (Constants.isOnline(this)) {

            Call<Info> infoCall = Constants.myInterface.saveSuggestionDetail(suggestionDetail);
            infoCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {
                            Info data = response.body();
                            if (data.getError()) {
                                Log.e("Suggestion Detail : ", " ERROR : " + data.getMessage());
                            } else {
                                Log.e("Suggestion Detail : ", " SUCCESS");
                                //edMessage.setText("");
                            }
                        } else {
                            Log.e("Suggestion Detail : ", " NULL");
                        }
                    } catch (Exception e) {
                        Log.e("Suggestion Detail : ", " Exception : " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    Log.e("Suggestion Detail : ", " onFailure : " + t.getMessage());
                    t.printStackTrace();
                }
            });
        }
    }


}
