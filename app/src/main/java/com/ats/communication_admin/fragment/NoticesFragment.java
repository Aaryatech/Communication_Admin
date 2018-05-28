package com.ats.communication_admin.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
import com.ats.communication_admin.adapter.NoticeAdapter;
import com.ats.communication_admin.adapter.SuggestionAdapter;
import com.ats.communication_admin.bean.SchedulerList;
import com.ats.communication_admin.bean.SuggestionData;
import com.ats.communication_admin.db.DatabaseHandler;
import com.ats.communication_admin.interfaces.NoticesInterface;
import com.google.gson.Gson;

import java.util.ArrayList;

public class NoticesFragment extends Fragment implements NoticesInterface {

    private RecyclerView rvNotices;
    DatabaseHandler db;
    NoticeAdapter adapter;

    ArrayList<SchedulerList> noticesArray;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private BroadcastReceiver mBroadcastReceiver;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notices, container, false);

        rvNotices = view.findViewById(R.id.rvNotices);

        db = new DatabaseHandler(getActivity());


        noticesArray = db.getAllSqliteNotices();

        adapter = new NoticeAdapter(noticesArray);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvNotices.setLayoutManager(mLayoutManager);
        rvNotices.setItemAnimator(new DefaultItemAnimator());
        rvNotices.setAdapter(adapter);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("NOTICE_ADMIN")) {
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
        Log.e("NOTICE", "  ON PAUSE");

        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mRegistrationBroadcastReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("NOTICE", "  ON RESUME");

        db.updateSuggestionRead();

        noticesArray.clear();
        noticesArray = db.getAllSqliteNotices();

        adapter = new NoticeAdapter(noticesArray);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvNotices.setLayoutManager(mLayoutManager);
        rvNotices.setItemAnimator(new DefaultItemAnimator());
        rvNotices.setAdapter(adapter);


        // registering the receiver for new notification
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("NOTICE_ADMIN"));

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadcastReceiver,
                new IntentFilter("REFRESH_DATA"));


    }


    private void handlePushNotification(Intent intent) {

        Log.e("handlePushNotification", "------------------------------------**********");
        Gson gson = new Gson();
        SchedulerList noticeData = gson.fromJson(intent.getStringExtra("message"), SchedulerList.class);

        SchedulerList dbNotice = db.getNotice(noticeData.getSchId());
        if (dbNotice.getSchId() == 0) {
            Log.e("Msg " + noticeData, "------------------- ");
            if (noticeData != null) {

                noticesArray.add(0, noticeData);
                adapter.notifyDataSetChanged();
            }
        } else {
            for (int i = 0; i < noticesArray.size(); i++) {
                if (dbNotice.getSchId() == noticesArray.get(i).getSchId()) {
                    noticesArray.set(i, noticeData);
                    db.updateNoticeById(noticeData);
                }
            }

            adapter.notifyDataSetChanged();
        }

       /* Log.e("Msg " + noticeData, "------------------- ");
        if (noticeData != null) {

            noticesArray.add(0, noticeData);
            adapter.notifyDataSetChanged();
        }*/
    }

    private void handlePushNotification1(Intent intent) {
        Log.e("handlePushNotification1", "------------------------------------**********");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void fragmentGetVisible() {
        db.updateNoticeRead();

        noticesArray.clear();
        noticesArray = db.getAllSqliteNotices();

        adapter = new NoticeAdapter(noticesArray);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvNotices.setLayoutManager(mLayoutManager);
        rvNotices.setItemAnimator(new DefaultItemAnimator());
        rvNotices.setAdapter(adapter);
    }
}
