package com.ats.communication_admin.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
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
import com.ats.communication_admin.bean.Info;
import com.ats.communication_admin.bean.MessageData;
import com.ats.communication_admin.bean.User;
import com.ats.communication_admin.common.CommonDialog;
import com.ats.communication_admin.constants.Constants;
import com.ats.communication_admin.util.PermissionUtil;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AFEIVisitActivity extends AppCompatActivity {

    RecyclerView rvFranchise;
    int userId;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afeivisit);
        setTitle("AFE Visit");

        rvFranchise = findViewById(R.id.rvFranchise);

        if (PermissionUtil.checkAndRequestPermissions(this)) {

        }


        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE);
        Gson gson = new Gson();
        String json2 = pref.getString("User", "");
        User userBean = gson.fromJson(json2, User.class);
        Log.e("User Bean : ", "---------------" + userBean);
        try {
            if (userBean != null) {
                userId = userBean.getId();
                userName = userBean.getUsername();


            } else {
                startActivity(new Intent(AFEIVisitActivity.this, LoginActivity.class));
                finish();
            }
        } catch (Exception e) {
            Log.e("HomeActivity : ", " Exception : " + e.getMessage());
            e.printStackTrace();
        }

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
        }else  if (id == R.id.menu_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AFEIVisitActivity.this, R.style.AlertDialogTheme);
            builder.setTitle("Logout");
            builder.setMessage("Are You Sure You Want To Logout?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    updateUserToken(userId, "");
                    SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE);
                    final SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.commit();

                    Intent intent = new Intent(AFEIVisitActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    finish();
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(AFEIVisitActivity.this, R.style.AlertDialogTheme);
        builder.setTitle("Exit Application?");
        builder.setMessage("");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void updateUserToken(int userId, String token) {

        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(AFEIVisitActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> infoCall = Constants.myInterface.updateFCMToken(1, userId, token);
            infoCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    Log.e("Response : ", "--------------------" + response.body());
                    commonDialog.dismiss();
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("Failure : ", "---------------------" + t.getMessage());
                    t.printStackTrace();
                }
            });

        }

    }


}
