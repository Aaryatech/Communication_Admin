package com.ats.communication_admin.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.communication_admin.BuildConfig;
import com.ats.communication_admin.R;
import com.ats.communication_admin.bean.ComplaintData;
import com.ats.communication_admin.bean.Franchisee;
import com.ats.communication_admin.bean.Info;
import com.ats.communication_admin.bean.NotificationData;
import com.ats.communication_admin.bean.User;
import com.ats.communication_admin.common.CommonDialog;
import com.ats.communication_admin.constants.Constants;
import com.ats.communication_admin.db.DatabaseHandler;
import com.ats.communication_admin.util.PermissionUtil;
import com.ats.communication_admin.util.RealPathUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddNotificationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edTitle, edDesc;
    private Button btnSubmit;
    private ImageView ivCamera, ivImage;
    private TextView tvImageName;

    DatabaseHandler db;

    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "COMM_AD");
    File f;

    Bitmap myBitmap = null;
    public static String path, imagePath;
    int userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        edTitle = findViewById(R.id.edAddNotification_Title);
        edDesc = findViewById(R.id.edAddNotification_Desc);
        ivCamera = findViewById(R.id.ivAddNotification_Camera);
        ivImage = findViewById(R.id.ivAddNotification_Image);
        btnSubmit = findViewById(R.id.btnAddNotification_Submit);
        tvImageName = findViewById(R.id.tvAddNotification_ImageName);
        ivCamera.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);


        if (PermissionUtil.checkAndRequestPermissions(this)) {

        }

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE);
        Gson gson = new Gson();
        String json2 = pref.getString("User", "");
        User userBean = gson.fromJson(json2, User.class);
        try {
            if (userBean != null) {
                userId = userBean.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        db = new DatabaseHandler(this);

        createFolder();


    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnAddNotification_Submit) {
            String title = edTitle.getText().toString();
            String desc = edDesc.getText().toString();
            String image = "";

            if (title.isEmpty()) {
                edTitle.setError("Required");
                edTitle.requestFocus();
            } else if (desc.isEmpty()) {
                edDesc.setError("Required");
                edDesc.requestFocus();
            } else {

                if (imagePath == null) {
                    imagePath = "";
                }

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                String date=sdf.format(new Date().getTime());

                NotificationData data = new NotificationData(0, title, userId, "", desc, image, date, "00:00:00", 0, 1);

                if (imagePath.isEmpty()) {
                    Log.e("ImagePath : ", "------- Empty");
                    addNewNotification(data);

                } else {

                    File imgFile = new File(imagePath);
                    int pos = imgFile.getName().lastIndexOf(".");
                    String ext = imgFile.getName().substring(pos + 1);
                    image = userId + "_" + System.currentTimeMillis() + "." + ext;

                    data.setPhoto(image);
                    sendImage(image, "nf", data);
                }

            }
        } else if (view.getId() == R.id.ivAddNotification_Camera) {
            showCameraDialog();
        }

    }

    public void showCameraDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle("Choose");
        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent pictureActionIntent = null;
                pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent, 101);
            }
        });
        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        f = new File(folder + File.separator, "Camera.jpg");

                        String authorities = BuildConfig.APPLICATION_ID + ".provider";
                        Uri imageUri = FileProvider.getUriForFile(getApplicationContext(), authorities, f);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, 102);


                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        f = new File(folder + File.separator, "Camera.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, 102);

                    }
                } catch (Exception e) {
                    ////Log.e("select camera : ", " Exception : " + e.getMessage());
                }
            }
        });
        builder.show();
    }

    public void createFolder() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }


    //--------------------------IMAGE-----------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String realPath;
        Bitmap bitmap = null;

        if (resultCode == RESULT_OK && requestCode == 102) {
            try {
                String path = f.getAbsolutePath();
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ivImage.setImageBitmap(myBitmap);

                    myBitmap = shrinkBitmap(imgFile.getAbsolutePath(), 720, 720);

                    try {
                        FileOutputStream out = new FileOutputStream(path);
                        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                        Log.e("Image Saved  ", "---------------");

                    } catch (Exception e) {
                        Log.e("Exception : ", "--------" + e.getMessage());
                        e.printStackTrace();
                    }
                }
                imagePath = f.getAbsolutePath();
                tvImageName.setText("" + f.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == 101) {
            try {
                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                Uri uriFromPath = Uri.fromFile(new File(realPath));
                myBitmap = getBitmapFromCameraData(data, this);

                ivImage.setImageBitmap(myBitmap);
                imagePath = uriFromPath.getPath();
                tvImageName.setText(uriFromPath.getPath());

                try {

                    FileOutputStream out = new FileOutputStream(uriFromPath.getPath());
                    myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                    //Log.e("Image Saved  ", "---------------");

                } catch (Exception e) {
                    // Log.e("Exception : ", "--------" + e.getMessage());
                    e.printStackTrace();
                }



            } catch (Exception e) {
                e.printStackTrace();
                // Log.e("Image Compress : ", "-----Exception : ------" + e.getMessage());
            }
        }
    }


    public static Bitmap getBitmapFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

        String picturePath = cursor.getString(columnIndex);
        path = picturePath;
        cursor.close();

        Bitmap bitm = shrinkBitmap(picturePath, 720, 720);
       // Log.e("Image Size : ---- ", " " + bitm.getByteCount());

        return bitm;
        // return BitmapFactory.decodeFile(picturePath, options);
    }

    public static Bitmap shrinkBitmap(String file, int width, int height) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }

    private void sendImage(String filename, String type, final NotificationData bean) {
        final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
        commonDialog.show();

        File imgFile = new File(imagePath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image"), imgFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imgFile.getName(), requestFile);

        RequestBody imgName = RequestBody.create(MediaType.parse("text/plain"), filename);
        RequestBody imgType = RequestBody.create(MediaType.parse("text/plain"), type);


        Call<JSONObject> call = Constants.myInterface.imageUpload(body, imgName, imgType);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                commonDialog.dismiss();
                addNewNotification(bean);
                imagePath = "";
                Log.e("Response : ", "--" + response.body());
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e("Error : ", "--" + t.getMessage());
                commonDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(AddNotificationActivity.this, "Unable To Process", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void addNewNotification(final NotificationData notificationData) {
        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> infoCall = Constants.myInterface.saveNotification(notificationData);
            infoCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {
                            commonDialog.dismiss();
                            Info data = response.body();
                            if (data.getError()) {
                                Toast.makeText(AddNotificationActivity.this, "Unable To Save! Please Try Again", Toast.LENGTH_SHORT).show();
                                Log.e("NotificationData  : ", " ERROR : " + data.getMessage());
                            } else {
                                Toast.makeText(AddNotificationActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                Log.e("NotificationData  : ", " SUCCESS");

                                db.deleteAllNotifications();
                                getAllNotification(db.getNotificationLastId());


                            }
                        } else {
                            commonDialog.dismiss();
                            Toast.makeText(AddNotificationActivity.this, "Unable To Save! Please Try Again", Toast.LENGTH_SHORT).show();
                            Log.e("NotificationData  : ", " NULL");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Toast.makeText(AddNotificationActivity.this, "Unable To Save! Please Try Again", Toast.LENGTH_SHORT).show();
                        Log.e("NotificationData  : ", " Exception : " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Toast.makeText(AddNotificationActivity.this, "Unable To Save! Please Try Again", Toast.LENGTH_SHORT).show();
                    Log.e("NotificationData  : ", " onFailure : " + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void getAllNotification(int nId) {

        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<NotificationData>> listCall = Constants.myInterface.getAllNotificationById(nId);
            listCall.enqueue(new Callback<ArrayList<NotificationData>>() {
                @Override
                public void onResponse(Call<ArrayList<NotificationData>> call, Response<ArrayList<NotificationData>> response) {
                    try {
                        if (response.body() != null) {
                            ArrayList<NotificationData> data = response.body();
                            commonDialog.dismiss();
                            Log.e("HOME : ", "INSERTING Complaint---------------------------");
                            DatabaseHandler db = new DatabaseHandler(AddNotificationActivity.this);

                            for (int i = 0; i < data.size(); i++) {
                                db.addNotifications(data.get(i));

                            }

                            Intent pushNotificationIntent = new Intent();
                            pushNotificationIntent.setAction("REFRESH_DATA");
                            LocalBroadcastManager.getInstance(AddNotificationActivity.this).sendBroadcast(pushNotificationIntent);

                            finish();

                        } else {
                            commonDialog.dismiss();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<NotificationData>> call, Throwable t) {
                    commonDialog.dismiss();
                    t.printStackTrace();
                }
            });
        }
    }
}
