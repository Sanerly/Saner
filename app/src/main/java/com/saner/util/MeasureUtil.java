package com.saner.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * ��湤����
 * 
 * @author Aige
 * @since 2014/11/19
 */
public final class MeasureUtil {
	/**
	 * ��ȡ��Ļ�ߴ�
	 * 
	 * @param activity
	 *            Activity
	 * @return ��Ļ�ߴ�����ֵ���±�Ϊ0��ֵΪ���±�Ϊ1��ֵΪ��
	 */
	public static int[] getScreenSize(Context activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
		if (wm != null) {
			wm.getDefaultDisplay().getMetrics(metrics);
		}
//
//		DisplayMetrics metrics = new DisplayMetrics();
//		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return new int[] { metrics.widthPixels, metrics.heightPixels };
	}
}
