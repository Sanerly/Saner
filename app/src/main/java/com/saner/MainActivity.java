package com.saner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.saner.util.LogUtil;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRoundAvatar(View view){
//        LogUtil.logd("点击事件");
        start(RoundAvatarActivity.class);
    }




    public void start(Class<?> cls){
        Intent intent=new Intent(this,cls);
        startActivity(intent);
    }
}
