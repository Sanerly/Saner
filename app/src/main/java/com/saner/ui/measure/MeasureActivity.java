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
        mList.add("的大小并");
        mList.add("Group");
        mList.add("留下了大量的空白区域");
        mList.add("Group");
        mList.add("留下了大量");
        mList.add("窗体的");
        mList.add("量的空白");
        mList.add("留下了大量的空白区域");

        mList.add("的大小并不是填满");
        mList.add("文件中的最顶层");
        mList.add("留下了大量的空白区域");
        mList.add("View(Group)");
        mList.add("是填满父窗体的");
        mList.add("View");
        mList.add("Group");
        mList.add("的大小并不是填满");
        mList.add("文件中的最顶层");
        mList.add("留下了大量的空白区域");
        mList.add("View(Group)");
        mList.add("是填满父窗体的");
        mList.add("View");
        mList.add("Group");

        mList.add("手机屏幕不能透明");
        mList.add("由于我们的");
        mList.add("你会发现一个问题");
        mList.add("文件");
        mList.add("layout？");
        mList.add("仔细观察上面的");
        mList.add("仔细观察");
        mList.add("所以这些空白区");
        mList.add("文件中的最顶层");
        mList.add("那么应该显示什么呢？");
        mList.add("我在 layout");
        mList.add("你会发现一个问题");
        mList.add("文件");
        mList.add("layout？");
        mList.add("仔细观察上面的");
        mList.add("仔细观察");
        mList.add("手机屏幕不能透明");
        mList.add("由于我们的");
        mList.add("你会发现一个问题");
        mList.add("文件");
        mList.add("layout？");
        mList.add("仔细观察上面的");
        mList.add("仔细观察");
        mList.add("所以这些空白区");
        mList.add("文件中的最顶层");
        mList.add("那么应该显示什么呢？");
        mList.add("我在 layout");
        mList.add("你会发现一个问题");
        tablabellayout.setAdapter(new TabLabelAdapter(mList,this));
    }

}
