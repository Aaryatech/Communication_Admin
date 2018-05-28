package com.ats.communication_admin.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.communication_admin.R;
import com.ats.communication_admin.adapter.AfeDateWiseReportAdapter;
import com.ats.communication_admin.adapter.QuestionsListAdapter;
import com.ats.communication_admin.bean.AfeDateWiseReport;
import com.ats.communication_admin.bean.AfeQuestion;
import com.ats.communication_admin.bean.AfeQuestionListData;
import com.ats.communication_admin.common.CommonDialog;
import com.ats.communication_admin.constants.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.communication_admin.adapter.QuestionsListAdapter.map;

public class AfeDateWiseReportActivity extends AppCompatActivity {

    private RecyclerView rvReport;
    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;
    int frId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afe_date_wise_report);
        setTitle("AFE Visit Reports");

        rvReport = findViewById(R.id.rvReportList);

        frId = getIntent().getExtras().getInt("FrId");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar currDate = Calendar.getInstance();
        String toDate = sdf.format(currDate.getTimeInMillis());

        currDate.add(Calendar.DATE, -1);
        String fromDate = sdf.format(currDate.getTimeInMillis());

        if (frId != 0) {
            getFrReport(frId);
        } else {
            getReport(fromDate, toDate);
        }

    }

    public void getReport(String fromDate, String toDate) {
        if (Constants.isOnline(this)) {

            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<AfeDateWiseReport>> arrayListCall = Constants.myInterface.getAfeDateWiseReport(fromDate, toDate);
            arrayListCall.enqueue(new Callback<ArrayList<AfeDateWiseReport>>() {
                @Override
                public void onResponse(Call<ArrayList<AfeDateWiseReport>> call, Response<ArrayList<AfeDateWiseReport>> response) {
                    try {
                        if (response.body() != null) {
                            ArrayList<AfeDateWiseReport> data = response.body();
                            Log.e("getReport", "------------------------------" + data);
                            AfeDateWiseReportAdapter adapter = new AfeDateWiseReportAdapter(data, AfeDateWiseReportActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AfeDateWiseReportActivity.this);
                            rvReport.setLayoutManager(mLayoutManager);
                            rvReport.setItemAnimator(new DefaultItemAnimator());
                            rvReport.setAdapter(adapter);
                            commonDialog.dismiss();
                        } else {
                            commonDialog.dismiss();
                            Log.e("getReport : ", " NULL");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("getReport : ", " Exception : " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<AfeDateWiseReport>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("getReport : ", " onFailure : " + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    public void getFrReport(int frId) {
        if (Constants.isOnline(this)) {

            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<AfeDateWiseReport>> arrayListCall = Constants.myInterface.getAfeFranchiseWiseReport(frId);
            arrayListCall.enqueue(new Callback<ArrayList<AfeDateWiseReport>>() {
                @Override
                public void onResponse(Call<ArrayList<AfeDateWiseReport>> call, Response<ArrayList<AfeDateWiseReport>> response) {
                    try {
                        if (response.body() != null) {
                            ArrayList<AfeDateWiseReport> data = response.body();
                            Log.e("getReport", "------------------------------" + data);
                            AfeDateWiseReportAdapter adapter = new AfeDateWiseReportAdapter(data, AfeDateWiseReportActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AfeDateWiseReportActivity.this);
                            rvReport.setLayoutManager(mLayoutManager);
                            rvReport.setItemAnimator(new DefaultItemAnimator());
                            rvReport.setAdapter(adapter);
                            commonDialog.dismiss();
                        } else {
                            commonDialog.dismiss();
                            Log.e("getReport : ", " NULL");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("getReport : ", " Exception : " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<AfeDateWiseReport>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("getReport : ", " onFailure : " + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.date_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_date_filter) {
            new showDateDialog(this).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public class showDateDialog extends Dialog {

        EditText edFromDate, edToDate;
        TextView tvFromDate, tvToDate;

        public showDateDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.custom_date_picker_dialog_layout);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.dimAmount = 0.75f;
            wlp.gravity = Gravity.TOP | Gravity.RIGHT;
            wlp.x = 100;
            wlp.y = 100;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            edFromDate = findViewById(R.id.edDatePicker_FromDate);
            edToDate = findViewById(R.id.edDatePicker_ToDate);
            TextView tvDialogSearch = findViewById(R.id.tvDatePicker_Search);
            TextView tvDialogCancel = findViewById(R.id.tvDatePicker_Cancel);
            tvFromDate = findViewById(R.id.tvDatePicker_FromDate);
            tvToDate = findViewById(R.id.tvDatePicker_ToDate);

            edFromDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int yr, mn, dy;
                    if (fromDateMillis > 0) {
                        Calendar purchaseCal = Calendar.getInstance();
                        purchaseCal.setTimeInMillis(fromDateMillis);
                        yr = purchaseCal.get(Calendar.YEAR);
                        mn = purchaseCal.get(Calendar.MONTH);
                        dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                    } else {
                        Calendar purchaseCal = Calendar.getInstance();
                        yr = purchaseCal.get(Calendar.YEAR);
                        mn = purchaseCal.get(Calendar.MONTH);
                        dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                    }
                    DatePickerDialog dialog = new DatePickerDialog(AfeDateWiseReportActivity.this, fromDateListener, yr, mn, dy);
                    dialog.show();
                }
            });

            edToDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int yr, mn, dy;
                    if (toDateMillis > 0) {
                        Calendar purchaseCal = Calendar.getInstance();
                        purchaseCal.setTimeInMillis(toDateMillis);
                        yr = purchaseCal.get(Calendar.YEAR);
                        mn = purchaseCal.get(Calendar.MONTH);
                        dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                    } else {
                        Calendar purchaseCal = Calendar.getInstance();
                        yr = purchaseCal.get(Calendar.YEAR);
                        mn = purchaseCal.get(Calendar.MONTH);
                        dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                    }
                    DatePickerDialog dialog = new DatePickerDialog(AfeDateWiseReportActivity.this, toDateListener, yr, mn, dy);
                    dialog.show();
                }
            });


            tvDialogSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edFromDate.getText().toString().isEmpty()) {
                        edFromDate.setError("Select From Date");
                        edFromDate.requestFocus();
                    } else if (edToDate.getText().toString().isEmpty()) {
                        edToDate.setError("Select To Date");
                        edToDate.requestFocus();
                    } else {
                        dismiss();

                        String fromDate = tvFromDate.getText().toString();
                        String toDate = tvToDate.getText().toString();

                        getReport(fromDate, toDate);
                    }
                }
            });

            tvDialogCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

        }

        DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yyyy = year;
                mm = month + 1;
                dd = dayOfMonth;
                edFromDate.setText(dd + "-" + mm + "-" + yyyy);
                tvFromDate.setText(yyyy + "-" + mm + "-" + dd);

                Calendar calendar = Calendar.getInstance();
                calendar.set(yyyy, mm - 1, dd);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.HOUR, 0);
                fromDateMillis = calendar.getTimeInMillis();
            }
        };

        DatePickerDialog.OnDateSetListener toDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yyyy = year;
                mm = month + 1;
                dd = dayOfMonth;
                edToDate.setText(dd + "-" + mm + "-" + yyyy);
                tvToDate.setText(yyyy + "-" + mm + "-" + dd);

                Calendar calendar = Calendar.getInstance();
                calendar.set(yyyy, mm - 1, dd);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.HOUR, 0);
                toDateMillis = calendar.getTimeInMillis();
            }
        };
    }

}
