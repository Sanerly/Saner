package com.saner.util;

import android.app.Activity;
import android.util.DisplayMetrics;

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
	public static int[] getScreenSize(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return new int[] { metrics.widthPixels, metrics.heightPixels };
	}
}
