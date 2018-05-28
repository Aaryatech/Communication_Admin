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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ats.communication_admin.R;
import com.ats.communication_admin.adapter.QuestionsListAdapter;
import com.ats.communication_admin.bean.AfeDateWiseReport;
import com.ats.communication_admin.bean.AfeDetail;
import com.ats.communication_admin.bean.AfeHeader;
import com.ats.communication_admin.bean.AfeQuestion;
import com.ats.communication_admin.bean.AfeQuestionListData;
import com.ats.communication_admin.bean.User;
import com.ats.communication_admin.common.CommonDialog;
import com.ats.communication_admin.constants.Constants;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.communication_admin.activity.HomeActivity.mapRemark;
import static com.ats.communication_admin.adapter.QuestionsListAdapter.map;

public class QuestionsActivity extends AppCompatActivity {

    private RecyclerView rvQuestions;
    private Button btnSubmit;

    int userId, frId, routeId;
    String userName, todaysDate,frName;

    QuestionsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        rvQuestions = findViewById(R.id.rvQuestionsList);
        btnSubmit = findViewById(R.id.btnQuestion_Submit);

        frId = getIntent().getExtras().getInt("FrId");
        routeId = getIntent().getExtras().getInt("RouteId");
        frName= getIntent().getExtras().getString("FrName");
        setTitle(""+frName);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        todaysDate = sdf.format(calendar.getTimeInMillis());

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE);
        Gson gson = new Gson();
        String json2 = pref.getString("User", "");
        User userBean = gson.fromJson(json2, User.class);
        Log.e("User Bean : ", "---------------" + userBean);
        try {
            if (userBean != null) {
                userId = userBean.getId();
                userName = userBean.getUsername();
            }
        } catch (Exception e) {
        }


        getAllQuestions();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter.notifyDataSetChanged();
               // rvQuestions.getLayoutManager().scrollToPosition(0);

                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsActivity.this, R.style.AlertDialogTheme);
                builder.setTitle("Confirm");
                builder.setMessage("Do You Want To Submit?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ArrayList<AfeDetail> detailArray = new ArrayList<>();
                        Set<Integer> keys = map.keySet();
                        ArrayList<Integer> queIdArray = new ArrayList<>();
                        queIdArray.addAll(keys);

                        int totalScore = 0;
                        for (int i = 0; i < queIdArray.size(); i++) {

                            String remark = mapRemark.get(queIdArray.get(i));
                            int result = 0;
                            if (map.get(queIdArray.get(i)) == true) {
                                result = 1;
                                totalScore = totalScore + 1;
                            } else {
                                result = 0;
                            }
                            AfeDetail detail = new AfeDetail(0, 0, queIdArray.get(i), result, 0, remark, 0, 0, "");
                            detailArray.add(detail);
                        }

                        AfeHeader header = new AfeHeader(0, frId, todaysDate, userId, userName, routeId, totalScore, 1, 0, 0, 0, "", "", detailArray);
                        saveScore(header);

                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


    }


    public void getAllQuestions() {
        if (Constants.isOnline(this)) {

            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<AfeQuestionListData> questionListDataCall = Constants.myInterface.getAllQuestions(0);
            questionListDataCall.enqueue(new Callback<AfeQuestionListData>() {
                @Override
                public void onResponse(Call<AfeQuestionListData> call, Response<AfeQuestionListData> response) {
                    try {
                        if (response.body() != null) {
                            AfeQuestionListData data = response.body();
                            if (data.getInfo().getError()) {
                                commonDialog.dismiss();
                            } else {
                                commonDialog.dismiss();
                                Log.e("getAllQuestions : ", "-----------------------" + data);

                                for (int i = 0; i < data.getAfeQuestion().size(); i++) {
                                    map.put(data.getAfeQuestion().get(i).getQueId(), false);
                                    mapRemark.put(data.getAfeQuestion().get(i).getQueId(), "");
                                }

                                adapter = new QuestionsListAdapter((ArrayList<AfeQuestion>) data.getAfeQuestion(), QuestionsActivity.this);

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(QuestionsActivity.this);
                                rvQuestions.setLayoutManager(mLayoutManager);
                                rvQuestions.setItemAnimator(new DefaultItemAnimator());
                                rvQuestions.setAdapter(adapter);

                            }
                        } else {
                            commonDialog.dismiss();
                            Log.e("getAllQuestions : ", " NULL");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("getAllQuestions : ", " Exception : " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<AfeQuestionListData> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("getAllQuestions : ", " onFailure : " + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveScore(AfeHeader afeHeader) {
        if (Constants.isOnline(this)) {

            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<AfeHeader> headerCall = Constants.myInterface.saveScore(afeHeader);
            headerCall.enqueue(new Callback<AfeHeader>() {
                @Override
                public void onResponse(Call<AfeHeader> call, Response<AfeHeader> response) {
                    try {
                        if (response.body() != null) {
                            mapRemark.clear();
                            AfeHeader data = response.body();
                            Toast.makeText(QuestionsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            commonDialog.dismiss();
                            Intent intent = new Intent(QuestionsActivity.this, AfeDateWiseReportActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(QuestionsActivity.this, "Unable To Save", Toast.LENGTH_SHORT).show();
                            commonDialog.dismiss();
                            Log.e("saveScore : ", " NULL");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Toast.makeText(QuestionsActivity.this, "Unable To Save", Toast.LENGTH_SHORT).show();
                        Log.e("saveScore : ", " Exception : " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<AfeHeader> call, Throwable t) {
                    commonDialog.dismiss();
                    Toast.makeText(QuestionsActivity.this, "Unable To Save", Toast.LENGTH_SHORT).show();
                    Log.e("saveScore : ", " onFailure : " + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }
}
