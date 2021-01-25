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
import android.widget.Toast;


import com.ats.communication_admin.R;
import com.ats.communication_admin.activity.AddNotificationActivity;
import com.ats.communication_admin.activity.InnerTabActivity;
import com.ats.communication_admin.activity.TabActivity;
import com.ats.communication_admin.adapter.FeedbackAdapter;
import com.ats.communication_admin.adapter.NotificationAdapter;
import com.ats.communication_admin.bean.ComplaintData;
import com.ats.communication_admin.bean.FeedbackData;
import com.ats.communication_admin.bean.NotificationData;
import com.ats.communication_admin.db.DatabaseHandler;
import com.ats.communication_admin.interfaces.NotificationsInterface;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.ats.communication_admin.activity.TabActivity.dbstatic;

public class NotificationsFragment extends Fragment implements NotificationsInterface, View.OnClickListener {

    private  RecyclerView rvNotification;
    private  NotificationAdapter adapter;
    private FloatingActionButton fab;

    DatabaseHandler db;

      ArrayList<NotificationData> notificationArray;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        rvNotification = view.findViewById(R.id.rvNotification);
        fab = view.findViewById(R.id.fabAddNotification);
        fab.setOnClickListener(this);

        db = new DatabaseHandler(getActivity());


        notificationArray = db.getAllSqliteNotifications();

        adapter = new NotificationAdapter(notificationArray, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvNotification.setLayoutManager(mLayoutManager);
        rvNotification.setItemAnimator(new DefaultItemAnimator());
        rvNotification.setAdapter(adapter);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("NOTIFICATION_ADMIN")) {
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

    @Override
    public void fragmentGetVisible() {

        Log.e("NOTIFY","----------- VISIBLE");

        //DatabaseHandler db = new DatabaseHandler(new TabActivity());
        dbstatic.updateNotificationRead();

        notificationArray.clear();
        notificationArray = db.getAllSqliteNotifications();

        adapter = new NotificationAdapter(notificationArray, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvNotification.setLayoutManager(mLayoutManager);
        rvNotification.setItemAnimator(new DefaultItemAnimator());
        rvNotification.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fabAddNotification) {
            startActivity(new Intent(getContext(), AddNotificationActivity.class));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

//        db.updateNotificationRead();
//
//        notificationArray = db.getAllSqliteNotifications();
//        adapter = new NotificationAdapter(notificationArray, getContext());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        rvNotification.setLayoutManager(mLayoutManager);
//        rvNotification.setItemAnimator(new DefaultItemAnimator());
//        rvNotification.setAdapter(adapter);


        // registering the receiver for new notification
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("NOTIFICATION_ADMIN"));

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadcastReceiver,
                new IntentFilter("REFRESH_DATA"));

    }

    private void handlePushNotification(Intent intent) {

        Log.e("handlePushNotification", "------------------------------------**********");
        Gson gson = new Gson();
        NotificationData notificationData = gson.fromJson(intent.getStringExtra("message"), NotificationData.class);

        String dateStr = notificationData.getDate();
        try {
            long dateLong = Long.parseLong(dateStr);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            notificationData.setDate(sdf.format(dateLong));

        } catch (Exception e) {
        }

        Log.e("Msg ", "------------------- " + notificationData);
        if (notificationData != null) {
            notificationArray.add(0, notificationData);
            adapter.notifyDataSetChanged();
        }
    }

    private void handlePushNotification1(Intent intent) {
        Log.e("handlePushNotification1", "------------------------------------**********");
        adapter.notifyDataSetChanged();
    }
}
