package com.ats.communication_admin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ats.communication_admin.R;
import com.ats.communication_admin.adapter.FranchiseListAdapter;
import com.ats.communication_admin.adapter.MessageAdapter;
import com.ats.communication_admin.bean.FranchiseData;
import com.ats.communication_admin.bean.FranchiseeList;
import com.ats.communication_admin.bean.MessageData;
import com.ats.communication_admin.common.CommonDialog;
import com.ats.communication_admin.constants.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AFEIVisitActivity extends AppCompatActivity {

    RecyclerView rvFranchise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afeivisit);
        setTitle("AFE Visit");

        rvFranchise = findViewById(R.id.rvFranchise);

        getAllFranchise();
    }


    public void getAllFranchise() {
        if (Constants.isOnline(this)) {

            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<FranchiseData> franchiseDataCall = Constants.myInterface.getAllFranchise();
            franchiseDataCall.enqueue(new Callback<FranchiseData>() {
                @Override
                public void onResponse(Call<FranchiseData> call, Response<FranchiseData> response) {
                    try {
                        if (response.body() != null) {
                            FranchiseData data = response.body();
                            if (data.getErrorMessage().getError()) {
                                commonDialog.dismiss();
                                Toast.makeText(AFEIVisitActivity.this, "No Franchise Found", Toast.LENGTH_SHORT).show();
                            } else {
                                commonDialog.dismiss();
                                Log.e("getAllFranchise : ", "-----------------------" + data);

                                ArrayList<FranchiseeList> franchiseeLists = new ArrayList<>();
                                for (int i = 0; i < data.getFranchiseeList().size(); i++) {
                                    franchiseeLists.add(data.getFranchiseeList().get(i));
                                }

                                FranchiseListAdapter adapter = new FranchiseListAdapter(franchiseeLists, AFEIVisitActivity.this);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AFEIVisitActivity.this);
                                rvFranchise.setLayoutManager(mLayoutManager);
                                rvFranchise.setItemAnimator(new DefaultItemAnimator());
                                rvFranchise.setAdapter(adapter);

                            }
                        } else {
                            commonDialog.dismiss();
                            Toast.makeText(AFEIVisitActivity.this, "No Franchise Found", Toast.LENGTH_SHORT).show();
                            Log.e("getAllFranchise : ", " NULL");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Toast.makeText(AFEIVisitActivity.this, "No Franchise Found", Toast.LENGTH_SHORT).show();
                        Log.e("getAllFranchise : ", " Exception : " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<FranchiseData> call, Throwable t) {
                    commonDialog.dismiss();
                    Toast.makeText(AFEIVisitActivity.this, "No Franchise Found", Toast.LENGTH_SHORT).show();
                    Log.e("getAllFranchise : ", " onFailure : " + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reports_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_all_reports) {
            Intent intent = new Intent(AFEIVisitActivity.this, AfeDateWiseReportActivity.class);
            intent.putExtra("FrId", 0);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
