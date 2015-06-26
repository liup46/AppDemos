package com.lp.common.logger;

import android.util.Log;

/**
 * Created by pengliu2 on 6/26/15.
 */
public class LogWrapper implements LogNode {

    private LogNode mNext;

    public LogNode getNext() {
        return mNext;
    }

    public void setNext(LogNode logNode) {
        mNext = logNode;
    }


    @Override
    public void println(int priority, String tag, String msg, Throwable tr) {
        String useMsg = msg;
        if (useMsg == null) {
            useMsg = "";
        }
        if (tr != null) {
            msg += "\n" + Log.getStackTraceString(tr);
        }
        Log.println(priority, tag, useMsg);

        if (mNext != null) {
            mNext.println(priority, tag, msg, tr);
        }
    }
}
