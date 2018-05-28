package com.ats.communication_admin.interfaces;


import com.ats.communication_admin.bean.AdminData;
import com.ats.communication_admin.bean.AfeDateWiseReport;
import com.ats.communication_admin.bean.AfeDetailDisplay;
import com.ats.communication_admin.bean.AfeHeader;
import com.ats.communication_admin.bean.AfeQuestionListData;
import com.ats.communication_admin.bean.ComplaintData;
import com.ats.communication_admin.bean.ComplaintDetail;
import com.ats.communication_admin.bean.ErrorMessage;
import com.ats.communication_admin.bean.FeedbackData;
import com.ats.communication_admin.bean.FeedbackDetail;
import com.ats.communication_admin.bean.FranchiseByRoute;
import com.ats.communication_admin.bean.FranchiseData;
import com.ats.communication_admin.bean.Info;
import com.ats.communication_admin.bean.LoginData;
import com.ats.communication_admin.bean.MessageData;
import com.ats.communication_admin.bean.NoticeData;
import com.ats.communication_admin.bean.NotificationData;
import com.ats.communication_admin.bean.RouteListData;
import com.ats.communication_admin.bean.SuggestionData;
import com.ats.communication_admin.bean.SuggestionDetail;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by MAXADMIN on 29/1/2018.
 */

public interface InterfaceApi {

    @GET("login")
    Call<AdminData> getLogin(@Query("username") String username, @Query("password") String password);

    @GET("showFrontEndMessage")
    Call<MessageData> getAllMessages();

    @GET("showLatestNews")
    Call<NoticeData> getAllNotices();

    @GET("communication/getAllNotification")
    Call<ArrayList<NotificationData>> getAllNotification();

    @POST("communication/getAllNotificationById")
    Call<ArrayList<NotificationData>> getAllNotificationById(@Query("notificationId") int notificationId);

    @POST("communication/getAllSuggHeaders")
    Call<ArrayList<SuggestionData>> getAllSuggestion(@Query("suggestionId") int suggestionId);

    @POST("communication/getAllSuggDetails")
    Call<ArrayList<SuggestionDetail>> getAllSuggestionDetails(@Query("suggestionDetailId") int suggestionDetailId);

    @POST("communication/saveSuggestionDetail")
    Call<Info> saveSuggestionDetail(@Body SuggestionDetail suggestionDetail);

    @POST("communication/updateUserToken")
    Call<Info> updateFCMToken(@Query("isAdmin") int isAdmin, @Query("userId") int userId, @Query("token") String token);

    @POST("communication/getAllComplHeaders")
    Call<ArrayList<ComplaintData>> getAllComplaint(@Query("complaintId") int complaintId);

    @POST("communication/getAllComplDetails")
    Call<ArrayList<ComplaintDetail>> getAllComplaintDetail(@Query("compDetailId") int compDetailId);

    @POST("communication/getAllFeedbackById")
    Call<ArrayList<FeedbackData>> getAllFeedback(@Query("feedbackId") int feedbackId);

    @POST("communication/getAllFeedbackDetailsByFrId")
    Call<ArrayList<FeedbackDetail>> getAllFeedbackDetail1(@Query("frId") int frId, @Query("feedbackDetailId") int feedbackDetailId);

    @POST("communication/getAllFeedbackDetailById")
    Call<ArrayList<FeedbackDetail>> getAllFeedbackDetail(@Query("feedbackDetailId") int feedbackDetailId);


    @POST("communication/saveSuggestion")
    Call<Info> saveSuggestion(@Body SuggestionData suggestionData);

    @POST("communication/deleteSuggestion")
    Call<Info> deleteSuggestion(@Query("suggestionId") int suggestionId);

    @POST("communication/saveComplaint")
    Call<Info> saveComplaint(@Body ComplaintData complaintData);

    @POST("communication/deleteComplaint")
    Call<Info> deleteComplaint(@Query("complaintId") int complaintId);

    @POST("communication/saveComplaintDetail")
    Call<Info> saveComplaintDetail(@Body ComplaintDetail complaintDetail);

    @POST("communication/saveFeedbackDetail")
    Call<Info> saveFeedbackDetail(@Body FeedbackDetail feedbackDetail);

    @POST("communication/saveFeedback")
    Call<Info> saveFeedback(@Body FeedbackData feedbackData);

    @Multipart
    @POST("photoUpload")
    Call<JSONObject> imageUpload(@Part MultipartBody.Part file, @Part("imageName") RequestBody name, @Part("type") RequestBody type);

    @POST("communication/saveNotification")
    Call<Info> saveNotification(@Body NotificationData notificationData);

    @GET("showRouteList")
    Call<RouteListData> getAllRouteList();

    @POST("traymgt/getFranchiseInRoute")
    Call<ArrayList<FranchiseByRoute>> getAllFranchiseByRoute(@Query("routeId") int routeId, @Query("tranId") int tranId);

    @POST("communication/sendNotificationToFr")
    Call<ErrorMessage> sendNotificationToFr(@Query("frIds") ArrayList<Integer> frIds, @Query("title") String title, @Query("message") String message);


    //-----AFE VISIT--------------------------

    @GET("getAllFranchisee")
    Call<FranchiseData> getAllFranchise();

    @POST("getAfeQuestionList")
    Call<AfeQuestionListData> getAllQuestions(@Query("delStatus") int delStatus);

    @POST("postAfeScore")
    Call<AfeHeader> saveScore(@Body AfeHeader afeHeader);

    @POST("getAfeScoreHeaderList")
    Call<ArrayList<AfeDateWiseReport>> getAfeDateWiseReport(@Query("fromDate") String fromDate, @Query("toDate") String toDate);

    @POST("getAfeScoreDetail")
    Call<ArrayList<AfeDetailDisplay>> getAfeDetailReport(@Query("scoreHeaderId") int scoreHeaderId, @Query("delStatus") int delStatus);

    @POST("getAfeScoreHeaderByFrId")
    Call<ArrayList<AfeDateWiseReport>> getAfeFranchiseWiseReport(@Query("frId") int frId);

}
