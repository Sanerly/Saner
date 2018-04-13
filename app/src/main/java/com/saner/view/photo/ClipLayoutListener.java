package com.saner.view.photo;

import android.widget.ImageView;

/**
 * Created by sunset on 2018/4/12.
 */

public interface ClipLayoutListener {
    void Imageloader(ImageView view, String url);

    void BottomChildClick(String text, int position);
}
