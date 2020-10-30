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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ats.communication_admin.R;
import com.ats.communication_admin.activity.AddSuggestionActivity;
import com.ats.communication_admin.adapter.SuggestionAdapter;
import com.ats.communication_admin.bean.SuggestionData;
import com.ats.communication_admin.db.DatabaseHandler;
import com.ats.communication_admin.interfaces.SuggestionInterface;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SuggestionFragment extends Fragment implements SuggestionInterface, View.OnClickListener {

    private RecyclerView rvSuggestion;
    DatabaseHandler db;
    SuggestionAdapter adapter;
    private FloatingActionButton fabSearch;
    private EditText edSearch;
    private LinearLayout llSearch;

    ArrayList<SuggestionData> suggestionDataArray;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private BroadcastReceiver mBroadcastReceiver;

    int scrollDist = 0;
    boolean isVisible = true;
    static final float MINIMUM = 25;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion, container, false);

        rvSuggestion = view.findViewById(R.id.rvSuggestion);
        fabSearch = view.findViewById(R.id.fabSearch);
        edSearch = view.findViewById(R.id.edSearch);
        llSearch = view.findViewById(R.id.llSearch);
        fabSearch.setOnClickListener(this);

        db = new DatabaseHandler(getActivity());

        suggestionDataArray = db.getAllSQLiteSuggestions();
        adapter = new SuggestionAdapter(suggestionDataArray, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvSuggestion.setLayoutManager(mLayoutManager);
        rvSuggestion.setItemAnimator(new DefaultItemAnimator());
        rvSuggestion.setAdapter(adapter);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("SUGGESTION_ADMIN")) {
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

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

        suggestionDataArray = db.getAllSQLiteSuggestions();
        adapter.notifyDataSetChanged();
//        adapter = new SuggestionAdapter(suggestionDataArray, getContext());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        rvSuggestion.setLayoutManager(mLayoutManager);
//        rvSuggestion.setItemAnimator(new DefaultItemAnimator());
//        rvSuggestion.setAdapter(adapter);

        // registering the receiver for new notification
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("SUGGESTION_ADMIN"));

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadcastReceiver,
                new IntentFilter("REFRESH_DATA"));

    }


    private void handlePushNotification(Intent intent) {

        Log.e("handlePushNotification", "------------------------------------**********");
        Gson gson = new Gson();
        SuggestionData suggestionData = gson.fromJson(intent.getStringExtra("message"), SuggestionData.class);

        String frName = intent.getStringExtra("frName");
        suggestionData.setFrName(frName);

        // int refreshId = intent.getIntExtra("refresh", 0);
        Log.e("Msg " + suggestionData, "------------------- ");
        if (suggestionData != null) {

            suggestionDataArray.add(0, suggestionData);
            adapter.notifyDataSetChanged();
        }
    }

    private void handlePushNotification1(Intent intent) {
        Log.e("handlePushNotification1", "------------------------------------**********");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void fragmentGetVisible() {
        db.updateSuggestionRead();

        Log.e("SUGGESTION FRG", "---------------VISIBLE--------------");

        suggestionDataArray.clear();
        suggestionDataArray = db.getAllSQLiteSuggestions();
        adapter = new SuggestionAdapter(suggestionDataArray, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvSuggestion.setLayoutManager(mLayoutManager);
        rvSuggestion.setItemAnimator(new DefaultItemAnimator());
        rvSuggestion.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fabSearch) {
            //startActivity(new Intent(getContext(), AddSuggestionActivity.class));

            if (llSearch.getVisibility()==View.VISIBLE){
                llSearch.setVisibility(View.GONE);
            }else if (llSearch.getVisibility()==View.GONE){
                llSearch.setVisibility(View.VISIBLE);
            }

        }
    }

}
