package com.saner.ui.photo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saner.R;

/**
 * Created by sunset on 2018/3/21.
 */

public class ShowPhotoActivity extends AppCompatActivity {

    private ImageView image;
    private TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        init();
    }

    private void init() {
        image = findViewById(R.id.imageView);
        text = findViewById(R.id.textView);

    }


    public void onStartSelector(View view) {
//        LogUtil.logd("点击事件");
//        start(SelectedActivity.class);
        SelectedConf mConf = new SelectedConf.Builder()
                .setMaxCount(12)
                .setMultiSelected(true)
                .setColumns(4)
                .build();
        SelectedActivity.start(this, mConf);
    }


    public void start(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
