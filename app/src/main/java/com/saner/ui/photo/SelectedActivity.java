package com.saner.ui.photo;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.saner.R;
import com.saner.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class SelectedActivity extends AppCompatActivity implements SelectedAdapter.OnItemClickListener, View.OnClickListener {
    //列表每行显示的列数
    final int mColumns = 3;
    //最多可选择的图片计数
    final int SELECTED_COUNT = 6;

    private RecyclerView mRecycler;
    private List<PhotoModel> mDatas;
    private SelectedAdapter mAdapter;
    private ImageView mImageBack;
    private TextView mTextTitle;
    private Button mButComplete;

    private int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);
        initView();
        initRecycler();
        initData();
    }

    private void initView() {
        mRecycler = findViewById(R.id.selected_recycler);
        mImageBack = findViewById(R.id.selected_back);
        mTextTitle = findViewById(R.id.selected_title);
        mButComplete = findViewById(R.id.selected_complete);
        mImageBack.setOnClickListener(this);
        mButComplete.setOnClickListener(this);
    }


    private void initRecycler() {

        mDatas = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(this, mColumns);
        mRecycler.setLayoutManager(layoutManager);
        DefaultItemAnimator itemAnim = new DefaultItemAnimator();
        itemAnim.setSupportsChangeAnimations(false);
        mRecycler.setItemAnimator(itemAnim);
        mAdapter = new SelectedAdapter(mDatas, mColumns);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }


    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                Cursor cursor = getContentResolver().query(uri, null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        PhotoModel photoModel = new PhotoModel();
                        String url = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//                        LogUtil.logd("图片路径********" + url);
                        photoModel.setUrl(url);
                        photoModel.setSelected(false);
                        mDatas.add(photoModel);
                    }
                    cursor.close();
                }
            }
        }).start();
        handler.sendMessage(new Message());
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            LogUtil.logd("handler");
            mAdapter.notifyDataSetChanged();
        }
    };


    @Override
    public void onSelected(PhotoModel data, int pos) {

        if (data.isSelected()) {
            data.setSelected(false);
        } else {
            if (mCount < SELECTED_COUNT) {
                data.setSelected(true);
            } else {
                String messageFormat = "最多只能选择%s张图片";
                toast(String.format(messageFormat, SELECTED_COUNT));
            }
        }
        mCount = getSelectedCount();

        mAdapter.notifyItemChanged(pos);
        mButComplete.setEnabled(isEnabled());
        LogUtil.logd("count = " + mCount);
        setCompleteNum(mCount);
    }


    private void setCompleteNum(int count) {
        String str;
        if (count < 1) {
            str = "完成";
        } else {
            str = "(" + count + ")完成";
        }
        mButComplete.setText(str);
    }


    public void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowPhoto(PhotoModel data, int pos) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selected_back:
                finish();
                break;
            case R.id.selected_complete:

                break;
        }
    }


    /**
     * 检查当前是否有选择中的图片
     * @return
     */
    /**
     * 检查当前是否有选择中的图片
     *
     * @return
     */
    private boolean isEnabled() {
        for (PhotoModel data : mDatas) {
            if (data.isSelected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 计算当前选择的数量是否小于设定的数量
     *
     * @return
     */
    private int getSelectedCount() {
        int count = 0;
        for (PhotoModel data : mDatas) {
            if (data.isSelected()) {
                LogUtil.logd("url = " + data.getUrl());
                count++;
            }
        }
        return count;
    }

}
