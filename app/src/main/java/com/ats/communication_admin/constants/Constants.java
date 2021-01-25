package com.ats.communication_admin.constants;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ats.communication_admin.interfaces.InterfaceApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MAXADMIN on 29/1/2018.
 */

public class Constants {

    public static final String MY_PREF = "ADMIN_PREF";

    public static final String FR_IMAGE_URL = "http://107.180.95.11:8080/uploads/FR/";
    public static final String MESSAGE_IMAGE_URL = "http://107.180.95.11:8080/uploads/MSG/";
    public static final String NOTIFICATION_IMAGE_URL = "http://107.180.95.11:8080/uploads/NOTIFICATION/";
    public static final String SUGGESTION_IMAGE_URL = "http://107.180.95.11:8080/uploads/SUGGESTION/";
    public static final String COMPLAINT_IMAGE_URL = "http://107.180.95.11:8080/uploads/COMPLAINT/";
    public static final String FEEDBACK_IMAGE_URL = "http://107.180.95.11:8080/uploads/FEEDBACK/";
    public static final String ALBUM_IMAGE_URL = "http://107.180.95.11:8080/uploads/ALBUM/";


    //public static final String BASE_URL = "http://192.168.2.3:8096/";
    public static final String BASE_URL = "http://107.180.95.11:8080/webapi/";

    public static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();

                    Response response = chain.proceed(request);

                    return response;
                }
            })
            .readTimeout(10000, TimeUnit.SECONDS)
            .connectTimeout(10000, TimeUnit.SECONDS)
            .writeTimeout(10000, TimeUnit.SECONDS)
            .build();


    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build();

    public static InterfaceApi myInterface = retrofit.create(InterfaceApi.class);

    public static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            // Toast.makeText(context, "No Internet Connection ! ", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
