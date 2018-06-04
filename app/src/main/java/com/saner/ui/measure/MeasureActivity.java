package com.saner.ui.measure;

import android.app.Activity;
import android.os.Bundle;

import com.saner.R;
import com.saner.view.measure.TabLabelLayout;

import java.util.ArrayList;
import java.util.List;

public class MeasureActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        TabLabelLayout tablabellayout = findViewById(R.id.tablabellayout);

        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mList.add("Group");
            mList.add("的大小并");
            mList.add("留下了大量的空白区域");
            mList.add("Group");
            mList.add("留下了大量");
            mList.add("窗体的");
            mList.add("量的空白");
            mList.add("留下了");
        }

        tablabellayout.setAdapter(new TabLabelAdapter(mList,this));
    }

}
