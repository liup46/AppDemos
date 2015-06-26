package com.lp.common.logger;

/**
 * Created by pengliu2 on 6/26/15.
 */
public interface LogNode {

    public void println(int priority, String tag, String msg, Throwable tr);
}
