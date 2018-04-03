package com.saner.ui.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.saner.R;

public class ClipImageActivity extends AppCompatActivity {

    public static void start(Context context) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(activity, ClipImageActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_photo);
    }
}
