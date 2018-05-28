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
import com.ats.communication_admin.activity.AddComplaintActivity;
import com.ats.communication_admin.adapter.ComplaintAdapter;
import com.ats.communication_admin.adapter.SuggestionAdapter;
import com.ats.communication_admin.bean.ComplaintData;
import com.ats.communication_admin.bean.SuggestionData;
import com.ats.communication_admin.db.DatabaseHandler;
import com.ats.communication_admin.interfaces.ComplaintInterface;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ComplaintFragment extends Fragment implements ComplaintInterface, View.OnClickListener {

    private RecyclerView rvComplaint;
    DatabaseHandler db;
    FloatingActionButton fabAdd;


    ComplaintAdapter adapter;

    ArrayList<ComplaintData> complaintDataArrayList;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaint, container, false);

        rvComplaint = view.findViewById(R.id.rvComplaint);
        fabAdd = view.findViewById(R.id.fabAddComplaint);
        fabAdd.setOnClickListener(this);

        db = new DatabaseHandler(getActivity());

        complaintDataArrayList = db.getAllSQLiteComplaints();

        adapter = new ComplaintAdapter(complaintDataArrayList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvComplaint.setLayoutManager(mLayoutManager);
        rvComplaint.setItemAnimator(new DefaultItemAnimator());
        rvComplaint.setAdapter(adapter);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("COMPLAINT_ADMIN")) {
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
    public void onResume() {
        super.onResume();

        db.updateComplaintRead();

      //  complaintDataArrayList.clear();
        complaintDataArrayList = db.getAllSQLiteComplaints();
        adapter.notifyDataSetChanged();
//        adapter = new ComplaintAdapter(complaintDataArrayList, getContext());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        rvComplaint.setLayoutManager(mLayoutManager);
//        rvComplaint.setItemAnimator(new DefaultItemAnimator());
//        rvComplaint.setAdapter(adapter);


        // registering the receiver for new notification
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("COMPLAINT_ADMIN"));

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadcastReceiver,
                new IntentFilter("REFRESH_DATA"));

    }


    private void handlePushNotification(Intent intent) {

        Log.e("handlePushNotification", "------------------------------------**********");
        Gson gson = new Gson();
        ComplaintData complaintData = gson.fromJson(intent.getStringExtra("message"), ComplaintData.class);

        String frName=intent.getStringExtra("frName");
        complaintData.setFrName(frName);

        String dateStr = complaintData.getDate();
        try {
            long dateLong = Long.parseLong(dateStr);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            complaintData.setDate(sdf.format(dateLong));

        } catch (Exception e) {
        }

        Log.e("Msg ", "------------------- " + complaintData);
        if (complaintData != null) {
            complaintDataArrayList.add(0, complaintData);
            adapter.notifyDataSetChanged();
        }
    }

    private void handlePushNotification1(Intent intent) {
        Log.e("handlePushNotification1", "------------------------------------**********");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void fragmentGetVisible() {
        db.updateComplaintRead();

        complaintDataArrayList.clear();
        complaintDataArrayList = db.getAllSQLiteComplaints();
        adapter = new ComplaintAdapter(complaintDataArrayList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvComplaint.setLayoutManager(mLayoutManager);
        rvComplaint.setItemAnimator(new DefaultItemAnimator());
        rvComplaint.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fabAddComplaint) {
            startActivity(new Intent(getContext(), AddComplaintActivity.class));
        }
    }

}
