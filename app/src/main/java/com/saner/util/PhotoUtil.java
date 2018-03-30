package com.saner.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.saner.R;

/**
 * Created by sunset on 2018/3/21.
 */

public class PhotoUtil {

    public static void load(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .error(R.mipmap.ic_default_image)
                .placeholder(R.mipmap.ic_default_image)
                .into(view);
    }


    public static void showImageLayoutMeasure(View view, int columns) {
        WindowManager wm = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = dm.widthPixels / columns;
        params.height = dm.widthPixels / columns;
        view.setLayoutParams(params);
    }
}
