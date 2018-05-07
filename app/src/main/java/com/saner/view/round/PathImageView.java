package com.saner.view.round;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;



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
    public Builder getBuilder(RectF rectF) {
        Path path = new Path();
        path.addOval(rectF,  Path.Direction.CW);
        return Builder.newInstance().setPath(path).setColor(Color.parseColor("#FFE394")).setStrokeWidth(20);
    }


}
