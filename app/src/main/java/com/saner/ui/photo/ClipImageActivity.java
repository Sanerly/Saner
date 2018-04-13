package com.saner.ui.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.saner.R;
import com.saner.util.LogUtil;
import com.saner.util.SelPhotoUtil;
import com.saner.view.photo.ClipLayout;
import com.saner.view.photo.ClipLayoutListener;

public class ClipImageActivity extends AppCompatActivity implements ClipLayoutListener {
    private static String IMAGE_PATH = "image_path";

    public static void start(Context context, String imagePath) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(activity, ClipImageActivity.class);
        intent.putExtra(IMAGE_PATH, imagePath);
        activity.startActivity(intent);
    }

    ClipLayout clipLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_photo);
        clipLayout = findViewById(R.id.clip_layout);
        String path = getIntent().getStringExtra(IMAGE_PATH);
        clipLayout.setClipLayoutListener(this);
        clipLayout.setImageUrl(path);
//        clipLayout.setImageResource(R.drawable.icon_photo);
        clipLayout.setClipSize(0.8f);
    }

    @Override
    public void Imageloader(ImageView view, String url) {
        LogUtil.logd("------url-----" + url);
        SelPhotoUtil.load(view, url);
    }

    @Override
    public void BottomChildClick(String text, int position) {
        switch (position) {
            case 0:
                finish();
                break;
            case 1:
                clipLayout.onStartClip();
                finish();
                break;
        }

    }
}
