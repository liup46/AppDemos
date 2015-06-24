package com.lp.rotation;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by Administrator on 15-6-13.
 */
public class DisplayUtil {

    private static final String TAG = "DisplayUtil.Class";

    /**
     * dip to px
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dip2px(Context context, float dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);

    }

    /**
     * px to dip
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2dp(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


    public static Point getSreenMetrics(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int w_screen = metrics.widthPixels;
        int h_screen = metrics.heightPixels;
        Log.i(TAG, "Screen---Width = " + w_screen + " Height = " + h_screen + " densityDpi = " + metrics.densityDpi);

        return new Point(w_screen, h_screen);
    }

    public static float getScreenRate(Context context) {
        Point P = getSreenMetrics(context);
        return (P.y / P.x);
    }

}
