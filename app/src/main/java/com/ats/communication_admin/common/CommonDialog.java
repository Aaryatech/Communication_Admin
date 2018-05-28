package com.ats.communication_admin.common;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by eis-01 on 4/4/17.
 */

public class CommonDialog {

    Context context;
    String Title;
    String Msg;
    ProgressDialog progressDialog;

    public CommonDialog(Context context, String title, String msg) {
        this.context = context;
        Title = title;
        Msg = msg;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
    }


    public void show() {
        try {

            progressDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismiss() {

        try {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
