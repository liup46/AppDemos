package com.lp.rotation;

import android.app.Activity;
import android.view.Surface;

import static android.view.OrientationEventListener.ORIENTATION_UNKNOWN;

/**
 * Created by Administrator on 15-6-14.
 */
public class RotationUtil {
    public static final int ORIENTATION_HYSTERESIS = 5;

    public static int roundOrientation(int orientation, int history) {
        boolean change = false;
        if (history == ORIENTATION_UNKNOWN) {
            change = true;
        } else {
            int dist = Math.abs(orientation - history);
            dist = Math.min(dist, 360 - dist);
            change = (dist >= 45 + history);
        }
        if (change) {
            return ((orientation + 45) / 90 * 90) / 360;

        }
        return history;

    }

    public static int getDisplayRotation(final Activity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();

        switch (rotation) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
        return 0;

    }

}
