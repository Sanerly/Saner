package com.saner.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by sunset on 2018/3/21.
 */

public class Systemconfig {

    public static void start(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
}
