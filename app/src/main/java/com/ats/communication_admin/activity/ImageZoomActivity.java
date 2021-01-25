package com.ats.communication_admin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ats.communication_admin.R;

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

public class ImageZoomActivity extends AppCompatActivity {
    private ZoomageView zoomageView;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);

        zoomageView = findViewById(R.id.myZoomageView);

        try {

            image = getIntent().getExtras().getString("image");
            Log.e("IMAGE PATH : ", " " + image);

            //  Picasso.with(this).load(image).placeholder(ImageZoomActivity.this.getResources().getDrawable(R.drawable.logo)).resize(800,800).into(zoomageView);
            try {
                Picasso.with(this)
                        .load(image)
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                        .into(zoomageView);
            } catch (Exception e) {
            }



        } catch (Exception e) {
        }
    }
}
