package com.saner.view.round;

import android.content.Context;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by sunset on 2018/4/18.
 */

public class PathImageView extends BasePathImageView {
    public PathImageView(Context context) {
        super(context);
    }

    public PathImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Path getPath(RectF rectF) {
        Path path = new Path();
        path.addOval(rectF,  Path.Direction.CW);
        return path;
    }
}
