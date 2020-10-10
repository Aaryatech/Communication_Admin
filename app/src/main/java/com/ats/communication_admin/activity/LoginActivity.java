package com.ats.communication_admin.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.communication_admin.R;
import com.ats.communication_admin.bean.AdminData;
import com.ats.communication_admin.bean.Info;
import com.ats.communication_admin.bean.LoginData;
import com.ats.communication_admin.common.CommonDialog;
import com.ats.communication_admin.constants.Constants;
import com.ats.communication_admin.fcm.SharedPrefManager;
import com.ats.communication_admin.util.PermissionUtil;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edUsername, edPassword;
    private TextView tvForgotPassword;
    private LinearLayout llLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.edLogin_Username);
        edPassword = findViewById(R.id.edLogin_Password);
        tvForgotPassword = findViewById(R.id.tvLogin_ForgotPass);
        llLogin = findViewById(R.id.llLogin);
        llLogin.setOnClickListener(this);

        if (PermissionUtil.checkAndRequestPermissions(this)) {

        }


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.llLogin) {

            String username = edUsername.getText().toString();
            String password = edPassword.getText().toString();
            if (username.isEmpty()) {
                edUsername.setError("Required");
                edUsername.requestFocus();
            } else if (password.isEmpty()) {
                edPassword.setError("Required");
                edPassword.requestFocus();
            } else {
                doLogin(username, password);
            }
        }
    }


    public void doLogin(String username, String pass) {
        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(LoginActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<AdminData> adminDataCall = Constants.myInterface.getLogin(username, pass);
            adminDataCall.enqueue(new Callback<AdminData>() {
                @Override
                public void onResponse(Call<AdminData> call, Response<AdminData> response) {
                    try {
                        if (response.body() != null) {
                            AdminData data = response.body();
                            if (data.getErrorMessage().getError()) {
                                commonDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "" + data.getErrorMessage().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("Login : ", " onError : " + data.getErrorMessage().getMessage());
                            } else {
                                commonDialog.dismiss();

                                Log.e("Login : ", " DATA : " + data);

                                SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(data.getUser());
                                editor.putString("User", json);
                                editor.apply();
                                editor.apply();

                                String token = SharedPrefManager.getmInstance(LoginActivity.this).getDeviceToken();
                                Log.e("Token : ", "---------" + token);
                                updateUserToken(data.getUser().getId(), token);


                            }
                        } else {
                            commonDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Unable To Login", Toast.LENGTH_SHORT).show();
                            Log.e("Login : ", " NULL");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Unable To Login", Toast.LENGTH_SHORT).show();
                        Log.e("Login : ", " Exception : " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<AdminData> call, Throwable t) {
                    commonDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Unable To Login", Toast.LENGTH_SHORT).show();
                    Log.e("Login : ", " onFailure : " + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }


    public void updateUserToken(int userId, String token) {

        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(LoginActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> infoCall = Constants.myInterface.updateFCMToken(1, userId, token);
            infoCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    Log.e("Response : ", "--------------------" + response.body());
                    commonDialog.dismiss();
                    //Intent intent = new Intent(LoginActivity.this, AFEIVisitActivity.class);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    Log.e("Failure : ", "---------------------" + t.getMessage());
                    t.printStackTrace();
                //    Intent intent = new Intent(LoginActivity.this, AFEIVisitActivity.class);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                }
            });

        } else {
           // Intent intent = new Intent(LoginActivity.this, AFEIVisitActivity.class);
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }

}
