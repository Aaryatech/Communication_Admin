package com.ats.communication_admin.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ats.communication_admin.R;
import com.ats.communication_admin.activity.AddFeedbackActivity;
import com.ats.communication_admin.adapter.ComplaintAdapter;
import com.ats.communication_admin.adapter.FeedbackAdapter;
import com.ats.communication_admin.adapter.SuggestionAdapter;
import com.ats.communication_admin.bean.ComplaintData;
import com.ats.communication_admin.bean.FeedbackData;
import com.ats.communication_admin.db.DatabaseHandler;
import com.ats.communication_admin.interfaces.LaunchProductInterface;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LaunchProductFragment extends Fragment implements LaunchProductInterface, View.OnClickListener {

    private RecyclerView rvFeedback;
    DatabaseHandler db;
    FeedbackAdapter adapter;
    private FloatingActionButton fab;

    ArrayList<FeedbackData> feedbackDataArray;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private BroadcastReceiver mBroadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_launch_product, container, false);

        rvFeedback = view.findViewById(R.id.rvFeedback);
        fab = view.findViewById(R.id.fabAddFeedback);

        fab.setOnClickListener(this);

        db = new DatabaseHandler(getActivity());

        feedbackDataArray = db.getAllSQLiteFeedback();

        adapter = new FeedbackAdapter(feedbackDataArray, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvFeedback.setLayoutManager(mLayoutManager);
        rvFeedback.setItemAnimator(new DefaultItemAnimator());
        rvFeedback.setAdapter(adapter);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("FEEDBACK_ADMIN")) {
                    handlePushNotification(intent);
                }
            }
        };

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("REFRESH_DATA")) {
                    handlePushNotification1(intent);
                }
            }
        };

        return view;
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mRegistrationBroadcastReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    private void handlePushNotification(Intent intent) {

        Log.e("handlePushNotification", "------------------------------------**********");
        Gson gson = new Gson();
        FeedbackData feedbackData = gson.fromJson(intent.getStringExtra("message"), FeedbackData.class);

        String frName = intent.getStringExtra("frName");
        feedbackData.setUserName(frName);

        String dateStr = feedbackData.getDate();
        try {
            long dateLong = Long.parseLong(dateStr);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            feedbackData.setDate(sdf.format(dateLong));

        } catch (Exception e) {
        }

        Log.e("Msg ", "------------------- " + feedbackData);
        if (feedbackData != null) {
            feedbackDataArray.add(0, feedbackData);
            adapter.notifyDataSetChanged();
        }
    }

    private void handlePushNotification1(Intent intent) {
        Log.e("handlePushNotification1", "------------------------------------**********");
        adapter.notifyDataSetChanged();
    }


    @Override
    public void fragmentGetVisible() {
        db.updateFeedbackRead();

        feedbackDataArray.clear();
        feedbackDataArray = db.getAllSQLiteFeedback();

        adapter = new FeedbackAdapter(feedbackDataArray, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvFeedback.setLayoutManager(mLayoutManager);
        rvFeedback.setItemAnimator(new DefaultItemAnimator());
        rvFeedback.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fabAddFeedback) {
            startActivity(new Intent(getContext(), AddFeedbackActivity.class));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        db.updateFeedbackRead();

       // feedbackDataArray.clear();
        feedbackDataArray = db.getAllSQLiteFeedback();
        adapter.notifyDataSetChanged();
//        adapter = new FeedbackAdapter(feedbackDataArray, getContext());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        rvFeedback.setLayoutManager(mLayoutManager);
//        rvFeedback.setItemAnimator(new DefaultItemAnimator());
//        rvFeedback.setAdapter(adapter);

        // registering the receiver for new notification
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("FEEDBACK_ADMIN"));

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadcastReceiver,
                new IntentFilter("REFRESH_DATA"));
    }
}
