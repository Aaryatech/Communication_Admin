package com.ats.communication_admin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.ats.communication_admin.bean.ComplaintData;
import com.ats.communication_admin.bean.ComplaintDetail;
import com.ats.communication_admin.bean.FeedbackData;
import com.ats.communication_admin.bean.FeedbackDetail;
import com.ats.communication_admin.bean.Message;
import com.ats.communication_admin.bean.NotificationData;
import com.ats.communication_admin.bean.SchedulerList;
import com.ats.communication_admin.bean.SuggestionData;
import com.ats.communication_admin.bean.SuggestionDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MAXADMIN on 29/1/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "CommunicationApp";

    private static final String TABLE_MESSAGE = "message";
    private static final String TABLE_NOTICE = "notice";
    private static final String TABLE_NOTIFICATION = "notification";
    private static final String TABLE_SUGGESTION = "suggestion";
    private static final String TABLE_SUGGESTION_DETAILS = "suggestionDetails";
    private static final String TABLE_COMPLAINT = "complaint";
    private static final String TABLE_COMPLAINT_DETAILS = "complaintDetails";
    private static final String TABLE_FEEDBACK = "feedback";
    private static final String TABLE_FEEDBACK_DETAILS = "feedbackDetails";


    private static final String M_ID = "mId";
    private static final String M_FROM_DATE = "mFromDate";
    private static final String M_TO_DATE = "mToDate";
    private static final String M_IMAGE = "mImage";
    private static final String M_HEADER = "mHeader";
    private static final String M_DETAILS = "mDetails";
    private static final String M_IS_ACTIVE = "mIsActive";
    private static final String M_DEL_STATUS = "mDelStatus";
    private static final String M_READ = "mRead";


    private static final String N_ID = "nId";
    private static final String N_DATE = "nDate";
    private static final String N_TO_DATE = "nToDate";
    private static final String N_OCC_NAME = "nOccName";
    private static final String N_Message = "nMessage";
    private static final String N_FROM_DT = "nFromDT";
    private static final String N_TO_DT = "nToDT";
    private static final String N_IS_ACTIVE = "nIsActive";
    private static final String N_DEL_STATUS = "nDelStatus";
    private static final String N_READ = "nRead";


    private static final String NF_ID = "nfId";
    private static final String NF_SUBJECT = "nfSubject";
    private static final String NF_USER_ID = "nfUserId";
    private static final String NF_USER_NAME = "nfUserName";
    private static final String NF_DESC = "nfDesc";
    private static final String NF_PHOTO = "nfPhoto";
    private static final String NF_DATE = "nfDate";
    private static final String NF_TIME = "nfTime";
    private static final String NF_IS_CLOSED = "nfIsClosed";
    private static final String NF_READ = "nfRead";

    private static final String SG_ID = "sgId";
    private static final String SG_TITLE = "sgTitle";
    private static final String SG_PHOTO = "sgPhoto";
    private static final String SG_DESC = "sgDesc";
    private static final String SG_DATE = "sgDate";
    private static final String SG_TIME = "sgTime";
    private static final String SG_FR_ID = "sgFrId";
    private static final String SG_FR_NAME = "sgFrName";
    private static final String SG_IS_CLOSED = "sgIsClosed";
    private static final String SG_READ = "sgRead";

    private static final String SD_ID = "sdId";
    private static final String SD_SG_ID = "sgId";
    private static final String SD_MESSAGE = "sdMessage";
    private static final String SD_IS_ADMIN = "sdIsAdmin";
    private static final String SD_FR_ID = "sdFrId";
    private static final String SD_FR_NAME = "sdFrName";
    private static final String SD_PHOTO = "sdPHOTO";
    private static final String SD_DATE = "sdDate";
    private static final String SD_TIME = "sdTime";
    private static final String SD_READ = "sdRead";

    private static final String C_ID = "cId";
    private static final String C_TITLE = "cTitle";
    private static final String C_DESC = "cDesc";
    private static final String C_PHOTO1 = "cPhoto1";
    private static final String C_PHOTO2 = "cPhoto2";
    private static final String C_FR_ID = "cFrId";
    private static final String C_FR_NAME = "cFrName";
    private static final String C_CUST_NAME = "cCustName";
    private static final String C_MOBILE = "cMobile";
    private static final String C_DATE = "cDate";
    private static final String C_TIME = "cTime";
    private static final String C_IS_CLOSED = "cIsClosed";
    private static final String C_READ = "cRead";

    private static final String CD_ID = "cdId";
    private static final String CD_C_ID = "cdCId";
    private static final String CD_MESSAGE = "cdMessage";
    private static final String CD_PHOTO = "cdPhoto";
    private static final String CD_DATE = "cdDate";
    private static final String CD_TIME = "cdTime";
    private static final String CD_IS_ADMIN = "cdIsAdmin";
    private static final String CD_FR_ID = "cdFrId";
    private static final String CD_FR_NAME = "cdFrName";
    private static final String CD_READ = "cdRead";

    private static final String F_ID = "fId";
    private static final String F_TITLE = "fTitle";
    private static final String F_USER_ID = "fUserId";
    private static final String F_USER_NAME = "fUserName";
    private static final String F_PHOTO = "fPhoto";
    private static final String F_DESC = "fDesc";
    private static final String F_DATE = "fDate";
    private static final String F_TIME = "fTime";
    private static final String F_IS_CLOSED = "fIsClosed";
    private static final String F_READ = "fRead";

    private static final String FD_ID = "fdId";
    private static final String FD_F_ID = "fdFId";
    private static final String FD_MESSAGE = "fdMessage";
    private static final String FD_IS_ADMIN = "fdIsAdmin";
    private static final String FD_FR_ID = "fdFrID";
    private static final String FD_FR_NAME = "fdFrName";
    private static final String FD_PHOTO = "fdPhoto";
    private static final String FD_DATE = "fdDate";
    private static final String FD_TIME = "fdTime";
    private static final String FD_READ = "fdRead";


    String CREATE_TABLE_MESSAGE = "CREATE TABLE "
            + TABLE_MESSAGE + "("
            + M_ID + " INTEGER PRIMARY KEY, "
            + M_FROM_DATE + " TEXT, "
            + M_TO_DATE + " TEXT, "
            + M_IMAGE + " TEXT, "
            + M_HEADER + " TEXT, "
            + M_DETAILS + " TEXT, "
            + M_IS_ACTIVE + " INTEGER, "
            + M_DEL_STATUS + " INTEGER, "
            + M_READ + " INTEGER)";

    String CREATE_TABLE_NOTICE = "CREATE TABLE "
            + TABLE_NOTICE + "("
            + N_ID + " INTEGER PRIMARY KEY, "
            + N_DATE + " TEXT, "
            + N_TO_DATE + " TEXT, "
            + N_OCC_NAME + " TEXT, "
            + N_Message + " TEXT, "
            + N_FROM_DT + " INTEGER, "
            + N_TO_DT + " INTEGER, "
            + N_IS_ACTIVE + " INTEGER, "
            + N_DEL_STATUS + " INTEGER, "
            + N_READ + " INTEGER)";

    String CREATE_TABLE_NOTIFICATION = "CREATE TABLE "
            + TABLE_NOTIFICATION + "("
            + NF_ID + " INTEGER PRIMARY KEY, "
            + NF_SUBJECT + " TEXT, "
            + NF_USER_ID + " INTEGER, "
            + NF_USER_NAME + " TEXT, "
            + NF_DESC + " TEXT, "
            + NF_PHOTO + " TEXT, "
            + NF_DATE + " TEXT, "
            + NF_TIME + " TEXT, "
            + NF_IS_CLOSED + " INTEGER, "
            + NF_READ + " INTEGER)";

    String CREATE_TABLE_SUGGESTION = "CREATE TABLE "
            + TABLE_SUGGESTION + "("
            + SG_ID + " INTEGER PRIMARY KEY, "
            + SG_TITLE + " TEXT, "
            + SG_PHOTO + " TEXT, "
            + SG_DESC + " TEXT, "
            + SG_DATE + " TEXT, "
            + SG_TIME + " TEXT, "
            + SG_FR_ID + " INTEGER, "
            + SG_FR_NAME + " TEXT, "
            + SG_IS_CLOSED + " INTEGER, "
            + SG_READ + " INTEGER)";

    String CREATE_TABLE_SUGGESTION_DETAILS = "CREATE TABLE "
            + TABLE_SUGGESTION_DETAILS + "("
            + SD_ID + " INTEGER PRIMARY KEY, "
            + SD_SG_ID + " INTEGER, "
            + SD_MESSAGE + " TEXT, "
            + SD_IS_ADMIN + " TEXT, "
            + SD_FR_ID + " INTEGER, "
            + SD_FR_NAME + " TEXT, "
            + SD_PHOTO + " TEXT, "
            + SD_DATE + " TEXT, "
            + SD_TIME + " TEXT, "
            + SD_READ + " INTEGER)";

    String CREATE_TABLE_COMPLAINT = "CREATE TABLE "
            + TABLE_COMPLAINT + " ("
            + C_ID + " INTEGER PRIMARY KEY, "
            + C_TITLE + " TEXT, "
            + C_DESC + " TEXT, "
            + C_PHOTO1 + " TEXT, "
            + C_PHOTO2 + " TEXT, "
            + C_FR_ID + " INTEGER, "
            + C_FR_NAME + " TEXT, "
            + C_CUST_NAME + " TEXT, "
            + C_MOBILE + " TEXT, "
            + C_DATE + " TEXT, "
            + C_TIME + " TEXT, "
            + C_IS_CLOSED + " INTEGER, "
            + C_READ + " INTEGER)";

    String CREATE_TABLE_COMPLAINT_DETAIL = "CREATE TABLE "
            + TABLE_COMPLAINT_DETAILS + " ("
            + CD_ID + " INTEGER PRIMARY KEY, "
            + CD_C_ID + " INTEGER, "
            + CD_MESSAGE + " TEXT, "
            + CD_PHOTO + " TEXT, "
            + CD_DATE + " TEXT, "
            + CD_TIME + " TEXT, "
            + CD_IS_ADMIN + " INTEGER, "
            + CD_FR_ID + " INTEGER, "
            + CD_FR_NAME + " TEXT, "
            + CD_READ + " INTEGER)";

    String CREATE_TABLE_FEEDBACK = "CREATE TABLE "
            + TABLE_FEEDBACK + " ("
            + F_ID + " INTEGER PRIMARY KEY, "
            + F_TITLE + " TEXT, "
            + F_USER_ID + " INTEGER, "
            + F_USER_NAME + " TEXT, "
            + F_PHOTO + " TEXT, "
            + F_DESC + " TEXT, "
            + F_DATE + " TEXT, "
            + F_TIME + " TEXT, "
            + F_IS_CLOSED + " INTEGER, "
            + F_READ + " INTEGER)";


    String CREATE_TABLE_FEEDBACK_DETAIL = "CREATE TABLE "
            + TABLE_FEEDBACK_DETAILS + " ("
            + FD_ID + " INTEGER PRIMARY KEY, "
            + FD_F_ID + " INTEGER, "
            + FD_MESSAGE + " TEXT, "
            + FD_IS_ADMIN + " INTEGER, "
            + FD_FR_ID + " INTEGER, "
            + FD_FR_NAME + " TEXT, "
            + FD_PHOTO + " TEXT, "
            + FD_DATE + " TEXT, "
            + FD_TIME + " TEXT, "
            + FD_READ + " INTEGER)";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.e("NOTICE TABLE : ", "----" + CREATE_TABLE_NOTICE);
        db.execSQL(CREATE_TABLE_MESSAGE);
        db.execSQL(CREATE_TABLE_NOTICE);
        db.execSQL(CREATE_TABLE_NOTIFICATION);
        db.execSQL(CREATE_TABLE_SUGGESTION);
        db.execSQL(CREATE_TABLE_SUGGESTION_DETAILS);
        db.execSQL(CREATE_TABLE_COMPLAINT);
        db.execSQL(CREATE_TABLE_COMPLAINT_DETAIL);
        db.execSQL(CREATE_TABLE_FEEDBACK);
        db.execSQL(CREATE_TABLE_FEEDBACK_DETAIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUGGESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUGGESTION_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLAINT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLAINT_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK_DETAILS);
        onCreate(db);
    }


    public void removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MESSAGE, null, null);
        db.delete(TABLE_NOTICE, null, null);
        db.delete(TABLE_NOTIFICATION, null, null);
        db.delete(TABLE_SUGGESTION, null, null);
        db.delete(TABLE_SUGGESTION_DETAILS, null, null);
        db.delete(TABLE_COMPLAINT, null, null);
        db.delete(TABLE_COMPLAINT_DETAILS, null, null);
        db.delete(TABLE_FEEDBACK, null, null);
        db.delete(TABLE_FEEDBACK_DETAILS, null, null);
        db.close();
    }

    public void removeAllNotices() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTICE, null, null);
        db.close();
    }

    public void removeAllMessages() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MESSAGE, null, null);
        db.close();
    }

    //----------------------------------MESSAGE------------------------------------

    public void addMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(M_ID, message.getMsgId());
        values.put(M_FROM_DATE, message.getMsgFrdt());
        values.put(M_TO_DATE, message.getMsgTodt());
        values.put(M_IMAGE, message.getMsgImage());
        values.put(M_HEADER, message.getMsgHeader());
        values.put(M_DETAILS, message.getMsgDetails());
        values.put(M_IS_ACTIVE, message.getIsActive());
        values.put(M_DEL_STATUS, message.getDelStatus());
        values.put(M_READ, 0);

        db.insert(TABLE_MESSAGE, null, values);
        db.close();
    }

    public ArrayList<Message> getAllMessages() {
        ArrayList<Message> messageList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String todaysDate = sdf.format(date.getTime());
        //Log.e("TODAYS DATE : ", "-------------" + todaysDate);

        //String query = "SELECT * FROM " + TABLE_MESSAGE + " WHERE " + M_TO_DATE + "<'" + todaysDate + "'";
        String query = "SELECT * FROM " + TABLE_MESSAGE + " ORDER BY " + M_ID + " DESC";

        //Log.e("QUERY : ", "-------------" + query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Message message = new Message();
                message.setMsgId(cursor.getInt(0));
                message.setMsgFrdt(cursor.getString(1));
                message.setMsgTodt(cursor.getString(2));
                message.setMsgImage(cursor.getString(3));
                message.setMsgHeader(cursor.getString(4));
                message.setMsgDetails(cursor.getString(5));
                message.setIsActive(cursor.getInt(6));
                message.setDelStatus(cursor.getInt(7));
                message.setRead(cursor.getInt(8));
                messageList.add(message);
            } while (cursor.moveToNext());
        }
        return messageList;
    }

    public Message getMessage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MESSAGE, new String[]{M_ID,
                        M_FROM_DATE, M_TO_DATE, M_IMAGE, M_HEADER, M_DETAILS, M_IS_ACTIVE, M_DEL_STATUS, M_READ}, M_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Message msg = new Message();
        if (cursor != null && cursor.moveToFirst()) {
            msg = new Message(Integer.parseInt(cursor.getString(0)));
            cursor.close();
        }
        return msg;
    }

    public int updateMessageById(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(M_ID, message.getMsgId());
        values.put(M_FROM_DATE, message.getMsgFrdt());
        values.put(M_TO_DATE, message.getMsgTodt());
        values.put(M_IMAGE, message.getMsgImage());
        values.put(M_HEADER, message.getMsgHeader());
        values.put(M_DETAILS, message.getMsgDetails());
        values.put(M_IS_ACTIVE, message.getIsActive());
        values.put(M_DEL_STATUS, message.getDelStatus());
        values.put(M_READ, 0);

        // updating row
        return db.update(TABLE_MESSAGE, values, "mId=" + message.getMsgId(), null);

    }

    public int updateMessageRead() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(M_READ, 1);

        // updating row
        return db.update(TABLE_MESSAGE, values, "mRead=0", null);

    }

    public int getMessageUnreadCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_MESSAGE + " WHERE " + M_READ + "=0", null);
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }


    //---------------------------NOTICE---------------------------------------

    public void addNotices(SchedulerList notice) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(N_ID, notice.getSchId());
        values.put(N_DATE, notice.getSchDate());
        values.put(N_TO_DATE, notice.getSchTodate());
        values.put(N_OCC_NAME, notice.getSchOccasionname());
        values.put(N_Message, notice.getSchMessage());
        values.put(N_FROM_DT, notice.getSchFrdttime());
        values.put(N_TO_DT, notice.getSchTodttime());
        values.put(N_IS_ACTIVE, notice.getIsActive());
        values.put(N_DEL_STATUS, notice.getDelStatus());
        values.put(N_READ, 0);

        db.insert(TABLE_NOTICE, null, values);
        db.close();
    }


    public ArrayList<SchedulerList> getAllSqliteNotices() {
        ArrayList<SchedulerList> noticeList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String todaysDate = sdf.format(date.getTime());
        //Log.e("TODAYS DATE : ", "-------------" + todaysDate);

        String query = "SELECT * FROM " + TABLE_NOTICE + " ORDER BY " + N_ID + " DESC";
        //String query = "SELECT * FROM " + TABLE_NOTICE + " WHERE " + N_TO_DATE + "<" + todaysDate;
        //Log.e("QUERY : ", "------------------------------------" + query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                SchedulerList notice = new SchedulerList();
                notice.setSchId(cursor.getInt(0));
                notice.setSchDate(cursor.getString(1));
                notice.setSchTodate(cursor.getString(2));
                notice.setSchOccasionname(cursor.getString(3));
                notice.setSchMessage(cursor.getString(4));
                notice.setSchFrdttime(cursor.getDouble(5));
                notice.setSchTodttime(cursor.getDouble(6));
                notice.setIsActive(cursor.getInt(7));
                notice.setDelStatus(cursor.getInt(8));
                notice.setRead(cursor.getInt(9));

                noticeList.add(notice);
            } while (cursor.moveToNext());
        }


        //Log.e("NOTICE", "********************************" + noticeList);
        return noticeList;
    }

    public SchedulerList getNotice(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTICE, new String[]{N_ID}, N_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        SchedulerList notice = new SchedulerList();
        if (cursor != null && cursor.moveToFirst()) {
            notice = new SchedulerList(Integer.parseInt(cursor.getString(0)));
            cursor.close();
        }
        return notice;
    }

    public int updateNoticeById(SchedulerList notice) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(N_ID, notice.getSchId());
        values.put(N_DATE, notice.getSchDate());
        values.put(N_TO_DATE, notice.getSchTodate());
        values.put(N_OCC_NAME, notice.getSchOccasionname());
        values.put(N_Message, notice.getSchMessage());
        values.put(N_FROM_DT, notice.getSchFrdttime());
        values.put(N_TO_DT, notice.getSchTodttime());
        values.put(N_IS_ACTIVE, notice.getIsActive());
        values.put(N_DEL_STATUS, notice.getDelStatus());
        values.put(N_READ, 0);

        // updating row
        return db.update(TABLE_NOTICE, values, "nId=" + notice.getSchId(), null);

    }

    public int updateNoticeRead() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(N_READ, 1);

        // updating row
        return db.update(TABLE_NOTICE, values, "nRead=0", null);

    }

    public int getNoticeUnreadCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NOTICE + " WHERE " + N_READ + "=0", null);
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    //---------------------------NOTIFICATION---------------------------------------

    public void addNotifications(NotificationData data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NF_ID, data.getNotificationId());
        values.put(NF_SUBJECT, data.getSubject());
        values.put(NF_USER_ID, data.getUserId());
        values.put(NF_USER_NAME, data.getUserName());
        values.put(NF_DESC, data.getDescription());
        values.put(NF_PHOTO, data.getPhoto());
        values.put(NF_DATE, data.getDate());
        values.put(NF_TIME, data.getTime());
        values.put(NF_IS_CLOSED, data.getIsClosed());
        values.put(NF_READ, 0);

        db.insert(TABLE_NOTIFICATION, null, values);
        db.close();
    }

    public ArrayList<NotificationData> getAllSqliteNotifications() {
        ArrayList<NotificationData> notificationList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NOTIFICATION + " ORDER BY " + NF_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                NotificationData notice = new NotificationData();
                notice.setNotificationId(cursor.getInt(0));
                notice.setSubject(cursor.getString(1));
                notice.setUserId(cursor.getInt(2));
                notice.setUserName(cursor.getString(3));
                notice.setDescription(cursor.getString(4));
                notice.setPhoto(cursor.getString(5));
                notice.setDate(cursor.getString(6));
                notice.setTime(cursor.getString(7));
                notice.setIsClosed(cursor.getInt(8));
                notice.setRead(cursor.getInt(9));

                notificationList.add(notice);
            } while (cursor.moveToNext());
        }
        return notificationList;
    }

    public NotificationData getNotification(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTIFICATION, new String[]{NF_ID}, NF_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        NotificationData notification = new NotificationData();
        if (cursor != null && cursor.moveToFirst()) {
            notification = new NotificationData(Integer.parseInt(cursor.getString(0)));
            cursor.close();
        }
        return notification;
    }

    public int updateNotificationRead() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NF_READ, 1);

        // updating row
        return db.update(TABLE_NOTIFICATION, values, "nfRead=0", null);

    }

    public int getNotificationUnreadCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NOTIFICATION + " WHERE " + NF_READ + "=0", null);
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getNotificationLastId() {
        int lastId = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT MAX(" + NF_ID + ") FROM " + TABLE_NOTIFICATION, null);
        if (cursor != null && cursor.moveToFirst()) {
            lastId = cursor.getInt(0);
            cursor.close();
        }
        return lastId;
    }

    //----------------------------------SUGGESTION------------------------------------

    public void addSuggestion(SuggestionData suggestion) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SG_ID, suggestion.getSuggestionId());
        values.put(SG_TITLE, suggestion.getTitle());
        values.put(SG_PHOTO, suggestion.getPhoto());
        values.put(SG_DESC, suggestion.getDescription());
        values.put(SG_DATE, suggestion.getDate());
        values.put(SG_TIME, suggestion.getTime());
        values.put(SG_FR_ID, suggestion.getFrId());
        values.put(SG_FR_NAME, suggestion.getFrName());
        values.put(SG_IS_CLOSED, suggestion.getIsClosed());
        values.put(SG_READ, 0);

        db.insert(TABLE_SUGGESTION, null, values);
        db.close();
    }

    public ArrayList<SuggestionData> getAllSQLiteSuggestions() {
        ArrayList<SuggestionData> suggestionList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_SUGGESTION + " ORDER BY " + SG_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                SuggestionData suggestion = new SuggestionData();
                suggestion.setSuggestionId(cursor.getInt(0));
                suggestion.setTitle(cursor.getString(1));
                suggestion.setPhoto(cursor.getString(2));
                suggestion.setDescription(cursor.getString(3));
                suggestion.setDate(cursor.getString(4));
                suggestion.setTime(cursor.getString(5));
                suggestion.setFrId(cursor.getInt(6));
                suggestion.setFrName(cursor.getString(7));
                suggestion.setIsClosed(cursor.getInt(8));
                suggestion.setRead(cursor.getInt(9));

                suggestionList.add(suggestion);
            } while (cursor.moveToNext());
        }
        return suggestionList;
    }

    public SuggestionData getSuggestion(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SUGGESTION, new String[]{SG_ID, SG_TITLE, SG_PHOTO, SG_DESC, SG_DATE, SG_TIME, SG_FR_ID, SG_FR_NAME, SG_IS_CLOSED, SG_READ}, SG_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        SuggestionData sugg = new SuggestionData();
        if (cursor != null && cursor.moveToFirst()) {
            sugg = new SuggestionData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9));
            cursor.close();
        }
        return sugg;
    }

    public int updateSuggestion(SuggestionData suggestionData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SG_TITLE, suggestionData.getTitle());
        values.put(SG_PHOTO, suggestionData.getPhoto());
        values.put(SG_DESC, suggestionData.getDescription());
        values.put(SG_DATE, suggestionData.getDate());
        values.put(SG_TIME, suggestionData.getTime());
        values.put(SG_FR_ID, suggestionData.getFrId());
        values.put(SG_FR_NAME, suggestionData.getFrName());
        values.put(SG_IS_CLOSED, suggestionData.getIsClosed());
        values.put(SG_READ, suggestionData.getRead());

        // updating row
        return db.update(TABLE_SUGGESTION, values, SG_ID + " = ?",
                new String[]{String.valueOf(suggestionData.getSuggestionId())});
    }

    public int updateSuggestionRead() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SG_READ, 1);

        // updating row
        return db.update(TABLE_SUGGESTION, values, "sgRead=0", null);
    }

    public int getSuggestionUnreadCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_SUGGESTION + " WHERE " + SG_READ + "=0", null);
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getSuggestionLastId() {
        int lastId = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT MAX(" + SG_ID + ") FROM " + TABLE_SUGGESTION, null);
        if (cursor != null && cursor.moveToFirst()) {
            lastId = cursor.getInt(0);
            cursor.close();
        }
        return lastId;
    }

    public void deleteAllSuggestion() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_SUGGESTION);
        //Log.e("DELETED : ", "-----------------------SUGGESTION");
    }

    //----------------------------------SUGGESTION DETAILS------------------------------------


    public void addSuggestionDetails(SuggestionDetail suggestion) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SD_ID, suggestion.getSuggestionDetailId());
        values.put(SD_SG_ID, suggestion.getSuggestionId());
        values.put(SD_MESSAGE, suggestion.getMessage());
        values.put(SD_IS_ADMIN, suggestion.getIsAdmin());
        values.put(SD_FR_ID, suggestion.getFrId());
        values.put(SD_FR_NAME, suggestion.getFrName());
        values.put(SD_PHOTO, suggestion.getPhoto());
        values.put(SD_DATE, suggestion.getDate());
        values.put(SD_TIME, suggestion.getTime());
        values.put(SD_READ, 0);

        db.insert(TABLE_SUGGESTION_DETAILS, null, values);
        db.close();
    }

    public ArrayList<SuggestionDetail> getAllSQLiteSuggestionDetails(int sgId) {
        ArrayList<SuggestionDetail> suggestionDetailList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_SUGGESTION_DETAILS + " WHERE " + SD_SG_ID + "=" + sgId;

        //Log.e("QUERY : ", "-------" + query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                SuggestionDetail suggestion = new SuggestionDetail();
                suggestion.setSuggestionDetailId(cursor.getInt(0));
                suggestion.setSuggestionId(cursor.getInt(1));
                suggestion.setMessage(cursor.getString(2));
                suggestion.setIsAdmin(cursor.getInt(3));
                suggestion.setFrId(cursor.getInt(4));
                suggestion.setFrName(cursor.getString(5));
                suggestion.setPhoto(cursor.getString(6));
                suggestion.setDate(cursor.getString(7));
                suggestion.setTime(cursor.getString(8));
                suggestion.setRead(cursor.getInt(9));

                suggestionDetailList.add(suggestion);
            } while (cursor.moveToNext());
        }
        return suggestionDetailList;
    }

    public SuggestionDetail getSuggestionDetail(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SUGGESTION_DETAILS, new String[]{SD_ID}, SD_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        SuggestionDetail suggDet = new SuggestionDetail();
        if (cursor != null && cursor.moveToFirst()) {
            suggDet = new SuggestionDetail(Integer.parseInt(cursor.getString(0)));
            cursor.close();
        }
        return suggDet;
    }

    public int updateSuggestionDetailRead() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SD_READ, 1);

        // updating row
        return db.update(TABLE_SUGGESTION_DETAILS, values, "sdRead=0", null);
    }

    public int updateSuggestionDetailRead(int suggestionId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SD_READ, 1);

        // updating row
        return db.update(TABLE_SUGGESTION_DETAILS, values, "sdRead=0 AND " + SD_SG_ID + "=" + suggestionId, null);
    }

    public int getSuggestionDetailUnreadCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_SUGGESTION_DETAILS + " WHERE " + SD_READ + "=0", null);
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getSuggestionDetailUnreadCount(int suggestionId) {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_SUGGESTION_DETAILS + " WHERE " + SD_READ + "=0 AND " + SD_SG_ID + "=" + suggestionId, null);
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getSuggestionDetailLastId() {
        int lastId = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT MAX(" + SD_ID + ") FROM " + TABLE_SUGGESTION_DETAILS, null);
        if (cursor != null && cursor.moveToFirst()) {
            lastId = cursor.getInt(0);
            cursor.close();
        }
        return lastId;
    }


    //----------------------------------COMPLAINT------------------------------------

    public void addComplaint(ComplaintData complaint) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(C_ID, complaint.getComplaintId());
        values.put(C_TITLE, complaint.getTitle());
        values.put(C_DESC, complaint.getDescription());
        values.put(C_PHOTO1, complaint.getPhoto1());
        values.put(C_PHOTO2, complaint.getPhoto2());
        values.put(C_FR_ID, complaint.getFrId());
        values.put(C_FR_NAME, complaint.getFrName());
        values.put(C_CUST_NAME, complaint.getCustomerName());
        values.put(C_MOBILE, complaint.getMobileNumber());
        values.put(C_DATE, complaint.getDate());
        values.put(C_TIME, complaint.getTime());
        values.put(C_IS_CLOSED, complaint.getIsClosed());
        values.put(C_READ, 0);

        db.insert(TABLE_COMPLAINT, null, values);
        db.close();
    }

    public ArrayList<ComplaintData> getAllSQLiteComplaints() {
        ArrayList<ComplaintData> complaintList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_COMPLAINT + " ORDER BY " + C_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ComplaintData complaint = new ComplaintData();
                complaint.setComplaintId(cursor.getInt(0));
                complaint.setTitle(cursor.getString(1));
                complaint.setDescription(cursor.getString(2));
                complaint.setPhoto1(cursor.getString(3));
                complaint.setPhoto2(cursor.getString(4));
                complaint.setFrId(cursor.getInt(5));
                complaint.setFrName(cursor.getString(6));
                complaint.setCustomerName(cursor.getString(7));
                complaint.setMobileNumber(cursor.getString(8));
                complaint.setDate(cursor.getString(9));
                complaint.setTime(cursor.getString(10));
                complaint.setIsClosed(cursor.getInt(11));
                complaint.setRead(cursor.getInt(12));

                complaintList.add(complaint);
            } while (cursor.moveToNext());
        }
        return complaintList;
    }


    public int updateComplaintRead() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(C_READ, 1);

        // updating row
        return db.update(TABLE_COMPLAINT, values, "cRead=0", null);
    }

    public int getComplaintUnreadCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_COMPLAINT + " WHERE " + C_READ + "=0", null);
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getComplaintLastId() {
        int lastId = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT MAX(" + C_ID + ") FROM " + TABLE_COMPLAINT, null);
        if (cursor != null && cursor.moveToFirst()) {
            lastId = cursor.getInt(0);
            cursor.close();
        }
        return lastId;
    }


    public ComplaintData getComplaint(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COMPLAINT, new String[]{C_ID, C_TITLE, C_DESC, C_PHOTO1, C_PHOTO2, C_FR_ID, C_FR_NAME, C_CUST_NAME, C_MOBILE, C_DATE, C_TIME, C_IS_CLOSED, C_READ}, C_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        ComplaintData complaint = new ComplaintData();
        if (cursor != null && cursor.moveToFirst()) {
            complaint = new ComplaintData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12));
            cursor.close();
        }
        return complaint;
    }

    public void deleteAllComplaint() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_COMPLAINT);
        //Log.e("DELETED : ", "-----------------------COMPLAINT");
    }

    //----------------------------------COMPLAINT DETAILS------------------------------------


    public void addComplaintDetails(ComplaintDetail complaint) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CD_ID, complaint.getCompDetailId());
        values.put(CD_C_ID, complaint.getComplaintId());
        values.put(CD_MESSAGE, complaint.getMessage());
        values.put(CD_PHOTO, complaint.getPhoto());
        values.put(CD_DATE, complaint.getDate());
        values.put(CD_TIME, complaint.getTime());
        values.put(CD_IS_ADMIN, complaint.getIsAdmin());
        values.put(CD_FR_ID, complaint.getFrId());
        values.put(CD_FR_NAME, complaint.getFrName());
        values.put(CD_READ, 0);

        Log.e("Complaint DETAIL INSERT", " : " + values);

        db.insert(TABLE_COMPLAINT_DETAILS, null, values);
        db.close();
    }

    public ArrayList<ComplaintDetail> getAllSQLiteComplaintDetails(int cId) {
        ArrayList<ComplaintDetail> complaintDetailList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_COMPLAINT_DETAILS + " WHERE " + CD_C_ID + "=" + cId;

        //Log.e("QUERY : ", "-------" + query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ComplaintDetail complaint = new ComplaintDetail();
                complaint.setCompDetailId(cursor.getInt(0));
                complaint.setComplaintId(cursor.getInt(1));
                complaint.setMessage(cursor.getString(2));
                complaint.setPhoto(cursor.getString(3));
                complaint.setDate(cursor.getString(4));
                complaint.setTime(cursor.getString(5));
                complaint.setIsAdmin(cursor.getInt(6));
                complaint.setFrId(cursor.getInt(7));
                complaint.setFrName(cursor.getString(8));
                complaint.setRead(cursor.getInt(9));

                complaintDetailList.add(complaint);
            } while (cursor.moveToNext());
        }
        return complaintDetailList;
    }


    public int updateComplaintDetailRead() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CD_READ, 1);

        // updating row
        return db.update(TABLE_COMPLAINT_DETAILS, values, "cdRead=0", null);
    }

    public int updateComplaintDetailRead(int complaintId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CD_READ, 1);

        // updating row
        return db.update(TABLE_COMPLAINT_DETAILS, values, "cdRead=0 AND " + CD_C_ID + "=" + complaintId, null);
    }

    public int getComplaintDetailUnreadCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_COMPLAINT_DETAILS + " WHERE " + CD_READ + "=0", null);
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getComplaintDetailUnreadCount(int complaintId) {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_COMPLAINT_DETAILS + " WHERE " + CD_READ + "=0 AND " + CD_C_ID + "=" + complaintId, null);
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getComplaintDetailLastId() {
        int lastId = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT MAX(" + CD_ID + ") FROM " + TABLE_COMPLAINT_DETAILS, null);
        if (cursor != null && cursor.moveToFirst()) {
            lastId = cursor.getInt(0);
            cursor.close();
        }
        return lastId;
    }


    //----------------------------------FEEDBACK------------------------------------


    public void addFeedBack(FeedbackData feedback) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(F_ID, feedback.getFeedbackId());
        values.put(F_TITLE, feedback.getTitle());
        values.put(F_USER_ID, feedback.getUserId());
        values.put(F_USER_NAME, feedback.getUserName());
        values.put(F_PHOTO, feedback.getPhoto());
        values.put(F_DESC, feedback.getDescription());
        values.put(F_DATE, feedback.getDate());
        values.put(F_TIME, feedback.getTime());
        values.put(F_IS_CLOSED, feedback.getIsClosed());
        values.put(F_READ, 0);

        db.insert(TABLE_FEEDBACK, null, values);
        db.close();
    }

    public ArrayList<FeedbackData> getAllSQLiteFeedback() {
        ArrayList<FeedbackData> feedbackList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_FEEDBACK + " ORDER BY " + F_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                FeedbackData feedback = new FeedbackData();
                feedback.setFeedbackId(cursor.getInt(0));
                feedback.setTitle(cursor.getString(1));
                feedback.setUserId(cursor.getInt(2));
                feedback.setUserName(cursor.getString(3));
                feedback.setPhoto(cursor.getString(4));
                feedback.setDescription(cursor.getString(5));
                feedback.setDate(cursor.getString(6));
                feedback.setTime(cursor.getString(7));
                feedback.setIsClosed(cursor.getInt(8));
                feedback.setRead(cursor.getInt(9));

                feedbackList.add(feedback);
            } while (cursor.moveToNext());
        }
        return feedbackList;
    }


    public int updateFeedbackRead() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(F_READ, 1);

        // updating row
        return db.update(TABLE_FEEDBACK, values, "fRead=0", null);
    }

    public int getFeedbackUnreadCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_FEEDBACK + " WHERE " + F_READ + "=0", null);
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getFeedbackLastId() {
        int lastId = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT MAX(" + F_ID + ") FROM " + TABLE_FEEDBACK, null);
        if (cursor != null && cursor.moveToFirst()) {
            lastId = cursor.getInt(0);
            cursor.close();
        }
        return lastId;
    }

    public FeedbackData getFeedback(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FEEDBACK, new String[]{F_ID, F_TITLE, F_USER_ID, F_USER_NAME, F_PHOTO, F_DESC, F_DATE, F_TIME, F_IS_CLOSED, F_READ}, F_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        FeedbackData feedback = new FeedbackData();
        if (cursor != null && cursor.moveToFirst()) {
            feedback = new FeedbackData(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9));
            cursor.close();
        }
        return feedback;
    }

    public void deleteAllFeedback() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_FEEDBACK);
        //Log.e("DELETED : ", "-----------------------FEEDBACK");
    }


    //----------------------------------FEEDBACK DETAILS------------------------------------

    public void addFeedbackDetails(FeedbackDetail feedback) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FD_ID, feedback.getFeedbackDetailId());
        values.put(FD_F_ID, feedback.getFeedbackId());
        values.put(FD_MESSAGE, feedback.getMessage());
        values.put(FD_IS_ADMIN, feedback.getIsAdmin());
        values.put(FD_FR_ID, feedback.getFrId());
        values.put(FD_FR_NAME, feedback.getFrName());
        values.put(FD_PHOTO, feedback.getPhoto());
        values.put(FD_DATE, feedback.getDate());
        values.put(FD_TIME, feedback.getTime());
        values.put(FD_READ, 0);

        db.insert(TABLE_FEEDBACK_DETAILS, null, values);
        db.close();
    }

    public ArrayList<FeedbackDetail> getAllSQLiteFeedbackDetails(int fId) {
        ArrayList<FeedbackDetail> feedbackDetailList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_FEEDBACK_DETAILS + " WHERE " + FD_F_ID + "=" + fId;

        //Log.e("QUERY : ", "-------" + query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                FeedbackDetail feedback = new FeedbackDetail();
                feedback.setFeedbackDetailId(cursor.getInt(0));
                feedback.setFeedbackId(cursor.getInt(1));
                feedback.setMessage(cursor.getString(2));
                feedback.setIsAdmin(cursor.getInt(3));
                feedback.setFrId(cursor.getInt(4));
                feedback.setFrName(cursor.getString(5));
                feedback.setPhoto(cursor.getString(6));
                feedback.setDate(cursor.getString(7));
                feedback.setTime(cursor.getString(8));
                feedback.setRead(cursor.getInt(9));

                feedbackDetailList.add(feedback);
            } while (cursor.moveToNext());
        }
        Log.e("SQLITE", "*************************" + feedbackDetailList);
        return feedbackDetailList;
    }


    public int updateFeedbackDetailRead() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FD_READ, 1);

        // updating row
        return db.update(TABLE_FEEDBACK_DETAILS, values, "fdRead=0", null);
    }

    public int updateFeedbackDetailRead(int feedbackId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FD_READ, 1);

        // updating row
        return db.update(TABLE_FEEDBACK_DETAILS, values, "fdRead=0 AND " + FD_F_ID + "=" + feedbackId, null);
    }

    public int getFeedbackDetailUnreadCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_FEEDBACK_DETAILS + " WHERE " + FD_READ + "=0", null);
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        Log.e("FEEDBACK DETAIL", "   UNREAD COUNT-----------------------" + count);
        return count;
    }

    public int getFeedbackDetailUnreadCount(int feedbackId) {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_FEEDBACK_DETAILS + " WHERE " + FD_READ + "=0 AND " + FD_F_ID + "=" + feedbackId, null);
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getFeedbackDetailLastId() {
        int lastId = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT MAX(" + FD_ID + ") FROM " + TABLE_FEEDBACK_DETAILS, null);
        if (cursor != null && cursor.moveToFirst()) {
            lastId = cursor.getInt(0);
            cursor.close();
        }
        return lastId;
    }


}
