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
import com.ats.communication_admin.adapter.MessageAdapter;
import com.ats.communication_admin.adapter.SuggestionAdapter;
import com.ats.communication_admin.bean.Message;
import com.ats.communication_admin.bean.SuggestionData;
import com.ats.communication_admin.db.DatabaseHandler;
import com.ats.communication_admin.interfaces.MessagesInterface;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessagesFragment extends Fragment implements MessagesInterface {

    private RecyclerView rvMessage;
    MessageAdapter adapter;

    DatabaseHandler db;

    ArrayList<Message> messageArray;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        rvMessage = view.findViewById(R.id.rvMessage);

        db = new DatabaseHandler(getActivity());


        messageArray = db.getAllMessages();

        adapter = new MessageAdapter(messageArray, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvMessage.setLayoutManager(mLayoutManager);
        rvMessage.setItemAnimator(new DefaultItemAnimator());
        rvMessage.setAdapter(adapter);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("MESSAGE_ADMIN")) {
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
        Log.e("SUGGESTION", "  ON PAUSE");

        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mRegistrationBroadcastReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("SUGG DET", "  ON RESUME");

        db.updateSuggestionRead();

        messageArray.clear();
        messageArray = db.getAllMessages();

        adapter = new MessageAdapter(messageArray, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvMessage.setLayoutManager(mLayoutManager);
        rvMessage.setItemAnimator(new DefaultItemAnimator());
        rvMessage.setAdapter(adapter);


        // registering the receiver for new notification
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("MESSAGE_ADMIN"));

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadcastReceiver,
                new IntentFilter("REFRESH_DATA"));


    }


    private void handlePushNotification(Intent intent) {

        Log.e("handlePushNotification", "------------------------------------**********");
        Gson gson = new Gson();
        com.ats.communication_admin.bean.Message messageData = gson.fromJson(intent.getStringExtra("message"), com.ats.communication_admin.bean.Message.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date parseDate = sdf.parse(messageData.getMsgFrdt());
            messageData.setMsgFrdt(sdf2.format(parseDate));

            Date parseDateTo = sdf.parse(messageData.getMsgTodt());
            messageData.setMsgTodt(sdf2.format(parseDateTo));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Message dbMessage = db.getMessage(messageData.getMsgId());
        if (dbMessage.getMsgId() == 0) {
            Log.e("Msg " + messageData, "------------------- ");
            if (messageData != null) {

                messageArray.add(0, messageData);
                adapter.notifyDataSetChanged();
            }
        } else {
            for (int i = 0; i < messageArray.size(); i++) {
                if (dbMessage.getMsgId() == messageArray.get(i).getMsgId()) {
                    messageArray.set(i, messageData);
                    db.updateMessageById(messageData);
                }
            }

            adapter.notifyDataSetChanged();
        }

       /* Log.e("Msg " + messageData, "------------------- ");
        if (messageData != null) {

            messageArray.add(0, messageData);
            adapter.notifyDataSetChanged();
        }*/
    }

    private void handlePushNotification1(Intent intent) {
        Log.e("handlePushNotification1", "------------------------------------**********");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void fragmentGetVisible() {

        db.updateMessageRead();

        ArrayList<Message> messageArray = db.getAllMessages();

        messageArray.clear();
        messageArray = db.getAllMessages();

        adapter = new MessageAdapter(messageArray, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvMessage.setLayoutManager(mLayoutManager);
        rvMessage.setItemAnimator(new DefaultItemAnimator());
        rvMessage.setAdapter(adapter);
    }
}
