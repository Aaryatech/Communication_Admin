package com.ats.communication_admin.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.ats.communication_admin.R;
import com.ats.communication_admin.activity.ComplaintDetailActivity;
import com.ats.communication_admin.activity.FeedbackDetailActivity;
import com.ats.communication_admin.activity.HomeActivity;
import com.ats.communication_admin.activity.InnerTabActivity;
import com.ats.communication_admin.activity.SuggestionDetailActivity;
import com.ats.communication_admin.activity.TabActivity;
import com.ats.communication_admin.bean.ComplaintData;
import com.ats.communication_admin.bean.ComplaintDetail;
import com.ats.communication_admin.bean.FeedbackData;
import com.ats.communication_admin.bean.FeedbackDetail;
import com.ats.communication_admin.bean.MessageData;
import com.ats.communication_admin.bean.NoticeData;
import com.ats.communication_admin.bean.NotificationData;
import com.ats.communication_admin.bean.SchedulerList;
import com.ats.communication_admin.bean.SuggestionData;
import com.ats.communication_admin.bean.SuggestionDetail;
import com.ats.communication_admin.bean.User;
import com.ats.communication_admin.constants.Constants;
import com.ats.communication_admin.db.DatabaseHandler;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0 || !remoteMessage.getNotification().equals(null)) {

            try {
                JSONObject json = new JSONObject(remoteMessage.getData());
                // JSONObject json1 = new JSONObject(remoteMessage.getNotification().toString());

                Log.e("JSON DATA", "-----------------------------" + json);
                // Log.e("JSON NOTIFICATION", "-----------------------------" + json1);

                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "-----------------------------Exception: " + e.getMessage());
                e.printStackTrace();
            }

            super.onMessageReceived(remoteMessage);

           /* NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher).setDefaults(Notification.DEFAULT_ALL)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("body"));
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
*/
        } else {
            Log.e("FIREBASE", "----------------------------------");
        }
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
    }

    private void sendPushNotification(JSONObject json) {

        Log.e(TAG, "--------------------------------JSON String" + json.toString());
        try {

            String tempTitle = json.getString("title");

            String[] tempArray = tempTitle.split("#");

            String title = "";
            String frName = "";
            Log.e("LENGTH : -----------","--------------"+tempArray.length);
            if (tempArray.length > 1) {
                title = tempArray[0];
                frName = tempArray[1];
            } else {
                title = tempTitle;
            }

            // String title = json.getString("title");
            String message = json.getString("body");
            String imageUrl = "";
            String tag = json.getString("tag");

            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            DatabaseHandler db = new DatabaseHandler(this);


            if (tag.equalsIgnoreCase("nf")) {

                Gson gson = new Gson();
                NotificationData notificationData = gson.fromJson(message, NotificationData.class);
                notificationData.setUserName(frName);

                Log.e("NotificationData:", "----------------------" + notificationData);


                if (mNotificationManager.isAppIsInBackground(getApplicationContext())) {
                    Log.e("APP BACKGROUND", "--------------------------------------------------");
                    db.addNotifications(notificationData);

                    Intent resultIntent = new Intent(getApplicationContext(), TabActivity.class);
                    resultIntent.putExtra("tabId", 2);

                    mNotificationManager.showSmallNotification(title, notificationData.getSubject(), resultIntent);

                } else {
                    Log.e("APP RUNNING", "--------------------------------------------------");
                    Intent pushNotification = new Intent();
                    pushNotification.setAction("NOTIFICATION_ADMIN");
                    pushNotification.putExtra("message", message);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                    MyNotificationManager notificationUtils = new MyNotificationManager(getApplicationContext());
                    notificationUtils.playNotificationSound();

                    db.addNotifications(notificationData);
                }
            }

            if (tag.equalsIgnoreCase("notice")) {

                Gson gson = new Gson();
                com.ats.communication_admin.bean.Message messageData = gson.fromJson(message, com.ats.communication_admin.bean.Message.class);
                Log.e("MessageData  : ", "----------------------" + messageData);

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

                if (mNotificationManager.isAppIsInBackground(getApplicationContext())) {
                    Log.e("APP BACKGROUND", "--------------------------------------------------");
                    com.ats.communication_admin.bean.Message dbMesage = db.getMessage(messageData.getMsgId());
                    if (dbMesage.getMsgId() == 0) {
                        db.addMessage(messageData);
                    } else {
                        db.updateMessageById(messageData);
                    }

                    Intent resultIntent = new Intent(getApplicationContext(), TabActivity.class);
                    resultIntent.putExtra("tabId", 1);

                    mNotificationManager.showSmallNotification(title, messageData.getMsgHeader(), resultIntent);

                } else {
                    Log.e("APP RUNNING", "--------------------------------------------------");
                    Intent pushNotification = new Intent();
                    pushNotification.setAction("MESSAGE_ADMIN");
                    pushNotification.putExtra("message", message);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                    MyNotificationManager notificationUtils = new MyNotificationManager(getApplicationContext());
                    notificationUtils.playNotificationSound();

                    com.ats.communication_admin.bean.Message dbMesage = db.getMessage(messageData.getMsgId());
                    if (dbMesage.getMsgId() == 0) {
                        db.addMessage(messageData);
                    } else {
                        db.updateMessageById(messageData);
                    }
                }
            }

            if (tag.equalsIgnoreCase("news")) {

                Gson gson = new Gson();
                SchedulerList noticeData = gson.fromJson(message, SchedulerList.class);
                Log.e("noticeData  : ", "----------------------" + noticeData);

                if (mNotificationManager.isAppIsInBackground(getApplicationContext())) {
                    Log.e("APP BACKGROUND", "--------------------------------------------------");
                    SchedulerList dbNews = db.getNotice(noticeData.getSchId());
                    if (dbNews.getSchId() == 0) {
                        db.addNotices(noticeData);
                    } else {
                        db.updateNoticeById(noticeData);
                    }

                    Intent resultIntent = new Intent(getApplicationContext(), TabActivity.class);
                    resultIntent.putExtra("tabId", 0);

                    mNotificationManager.showSmallNotification(title, noticeData.getSchMessage(), resultIntent);

                } else {
                    Log.e("APP RUNNING", "--------------------------------------------------");
                    Intent pushNotification = new Intent();
                    pushNotification.setAction("NOTICE_ADMIN");
                    pushNotification.putExtra("message", message);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                    MyNotificationManager notificationUtils = new MyNotificationManager(getApplicationContext());
                    notificationUtils.playNotificationSound();

                    SchedulerList dbNews = db.getNotice(noticeData.getSchId());
                    if (dbNews.getSchId() == 0) {
                        db.addNotices(noticeData);
                    } else {
                        db.updateNoticeById(noticeData);
                    }
                }
            }

            if (tag.equalsIgnoreCase("sd")) {

                Gson gson = new Gson();
                SuggestionDetail suggestionDetail = gson.fromJson(message, SuggestionDetail.class);
                suggestionDetail.setFrName(frName);
                Log.e("SUGGESTION DETAIL : ", "----------------------" + suggestionDetail);

                if (mNotificationManager.isAppIsInBackground(getApplicationContext())) {
                    Log.e("APP BACKGROUND", "--------------------------------------------------");
                    db.addSuggestionDetails(suggestionDetail);

                    Intent resultIntent = new Intent(getApplicationContext(), SuggestionDetailActivity.class);
                    resultIntent.putExtra("suggestionId", suggestionDetail.getSuggestionId());
                    resultIntent.putExtra("refresh", 1);

                    mNotificationManager.showSmallNotification(title, suggestionDetail.getMessage(), resultIntent);

                } else {
                    Log.e("APP RUNNING", "--------------------------------------------------");
                    Intent pushNotification = new Intent();
                    pushNotification.setAction("SUGGESTION_DETAIL_ADMIN");
                    pushNotification.putExtra("frName", frName);
                    pushNotification.putExtra("message", message);
                    pushNotification.putExtra("suggestionId", suggestionDetail.getSuggestionId());
                    pushNotification.putExtra("refresh", 1);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                    MyNotificationManager notificationUtils = new MyNotificationManager(getApplicationContext());
                    notificationUtils.playNotificationSound();

                    db.addSuggestionDetails(suggestionDetail);
                }
            } else if (tag.equalsIgnoreCase("cd")) {

                Gson gson = new Gson();
                ComplaintDetail complaintDetail = gson.fromJson(message, ComplaintDetail.class);
                complaintDetail.setFrName(frName);
                Log.e("COMPLAINT DETAIL : ", "----------------------" + complaintDetail);

                if (mNotificationManager.isAppIsInBackground(getApplicationContext())) {
                    Log.e("APP BACKGROUND", "--------------------------------------------------");
                    db.addComplaintDetails(complaintDetail);

                    Intent resultIntent = new Intent(getApplicationContext(), ComplaintDetailActivity.class);
                    resultIntent.putExtra("complaintId", complaintDetail.getComplaintId());
                    resultIntent.putExtra("refresh", 1);

                    mNotificationManager.showSmallNotification(title, complaintDetail.getMessage(), resultIntent);

                } else {
                    Log.e("APP RUNNING", "--------------------------------------------------");
                    Intent pushNotification = new Intent();
                    pushNotification.setAction("COMPLAINT_DETAIL_ADMIN");
                    pushNotification.putExtra("frName", frName);
                    pushNotification.putExtra("message", message);
                    pushNotification.putExtra("complaintId", complaintDetail.getComplaintId());
                    pushNotification.putExtra("refresh", 1);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                    MyNotificationManager notificationUtils = new MyNotificationManager(getApplicationContext());
                    notificationUtils.playNotificationSound();

                    db.addComplaintDetails(complaintDetail);
                }
            } else if (tag.equalsIgnoreCase("fd")) {

                Gson gson = new Gson();
                FeedbackDetail feedbackDetail = gson.fromJson(message, FeedbackDetail.class);
                feedbackDetail.setFrName(frName);
                Log.e("FEEDBACK DETAIL : ", "----------------------" + feedbackDetail);

                if (mNotificationManager.isAppIsInBackground(getApplicationContext())) {
                    Log.e("APP BACKGROUND", "--------------------------------------------------");
                    db.addFeedbackDetails(feedbackDetail);

                    Intent resultIntent = new Intent(getApplicationContext(), FeedbackDetailActivity.class);
                    resultIntent.putExtra("feedbackId", feedbackDetail.getFeedbackId());
                    resultIntent.putExtra("refresh", 1);

                    mNotificationManager.showSmallNotification(title, feedbackDetail.getMessage(), resultIntent);

                } else {
                    Log.e("APP RUNNING", "--------------------------------------------------");
                    Intent pushNotification = new Intent();
                    pushNotification.setAction("FEEDBACK_DETAIL_ADMIN");
                    pushNotification.putExtra("frName", frName);
                    pushNotification.putExtra("message", message);
                    pushNotification.putExtra("feedbackId", feedbackDetail.getFeedbackId());
                    pushNotification.putExtra("refresh", 1);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                    MyNotificationManager notificationUtils = new MyNotificationManager(getApplicationContext());
                    notificationUtils.playNotificationSound();

                    db.addFeedbackDetails(feedbackDetail);
                }
            } else if (tag.equalsIgnoreCase("s")) {

                Gson gson = new Gson();
                SuggestionData suggestion = gson.fromJson(message, SuggestionData.class);
                suggestion.setFrName(frName);

                String dateStr = suggestion.getDate();
                try {
                    long dateLong = Long.parseLong(dateStr);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    suggestion.setDate(sdf.format(dateLong));

                } catch (Exception e) {
                }


                Log.e("SUGGESTION : ", "----------------------" + suggestion);

                if (mNotificationManager.isAppIsInBackground(getApplicationContext())) {
                    Log.e("APP BACKGROUND", "--------------------------------------------------");
                    db.addSuggestion(suggestion);

                    Intent resultIntent = new Intent(getApplicationContext(), InnerTabActivity.class);
                    resultIntent.putExtra("tabId", 0);

                    mNotificationManager.showSmallNotification(title, suggestion.getTitle(), resultIntent);

                } else {
                    Log.e("APP RUNNING", "--------------------------------------------------");
                    Intent pushNotification = new Intent();
                    pushNotification.setAction("SUGGESTION_ADMIN");
                    pushNotification.putExtra("frName", frName);
                    pushNotification.putExtra("message", message);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                    MyNotificationManager notificationUtils = new MyNotificationManager(getApplicationContext());
                    notificationUtils.playNotificationSound();

                    db.addSuggestion(suggestion);
                }
            } else if (tag.equalsIgnoreCase("c")) {

                Gson gson = new Gson();
                ComplaintData complaint = gson.fromJson(message, ComplaintData.class);
                complaint.setFrName(frName);

                String dateStr = complaint.getDate();
                try {
                    long dateLong = Long.parseLong(dateStr);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    complaint.setDate(sdf.format(dateLong));

                } catch (Exception e) {
                }


                Log.e("COMPLAINT : ", "----------------------" + complaint);

                if (mNotificationManager.isAppIsInBackground(getApplicationContext())) {
                    Log.e("APP BACKGROUND", "--------------------------------------------------");
                    db.addComplaint(complaint);

                    Intent resultIntent = new Intent(getApplicationContext(), InnerTabActivity.class);
                    resultIntent.putExtra("tabId", 1);

                    mNotificationManager.showSmallNotification(title, complaint.getTitle(), resultIntent);

                } else {
                    Log.e("APP RUNNING", "--------------------------------------------------");
                    Intent pushNotification = new Intent();
                    pushNotification.setAction("COMPLAINT_ADMIN");
                    pushNotification.putExtra("frName", frName);
                    pushNotification.putExtra("message", message);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                    MyNotificationManager notificationUtils = new MyNotificationManager(getApplicationContext());
                    notificationUtils.playNotificationSound();

                    db.addComplaint(complaint);
                }
            } else if (tag.equalsIgnoreCase("f")) {

                Gson gson = new Gson();
                FeedbackData feedbackData = gson.fromJson(message, FeedbackData.class);
                feedbackData.setUserName(frName);
                Log.e("FEEDBACK  : ", "----------------------" + feedbackData);


                if (mNotificationManager.isAppIsInBackground(getApplicationContext())) {
                    Log.e("APP BACKGROUND", "--------------------------------------------------");
                    db.addFeedBack(feedbackData);

                    Intent resultIntent = new Intent(getApplicationContext(), InnerTabActivity.class);
                    resultIntent.putExtra("tabId", 2);

                    mNotificationManager.showSmallNotification(title, feedbackData.getTitle(), resultIntent);

                } else {
                    Log.e("APP RUNNING", "--------------------------------------------------");
                    Intent pushNotification = new Intent();
                    pushNotification.setAction("FEEDBACK_ADMIN");
                    pushNotification.putExtra("frName", frName);
                    pushNotification.putExtra("message", message);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                    MyNotificationManager notificationUtils = new MyNotificationManager(getApplicationContext());
                    notificationUtils.playNotificationSound();

                    db.addFeedBack(feedbackData);
                }
            }


            Intent pushNotificationIntent = new Intent();
            pushNotificationIntent.setAction("REFRESH_DATA");
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotificationIntent);


        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: -----------" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "Exception: ------------" + e.getMessage());
            e.printStackTrace();
        }

    }


}
