package com.lp.common.logger;

/**
 * Created by pengliu2 on 6/26/15.
 */
public class MessageLogOnlyFilter implements LogNode {

    LogNode mNext;

    public MessageLogOnlyFilter(LogNode node) {
        this.mNext = mNext;
    }


    public MessageLogOnlyFilter() {
    }

    public LogNode getNext() {
        return mNext;
    }

    public void setNext(LogNode mNext) {
        this.mNext = mNext;
    }

    @Override
    public void println(int priority, String tag, String msg, Throwable tr) {
        if (mNext != null) {
            mNext.println(priority, tag, msg, tr);
        }
    }
}
