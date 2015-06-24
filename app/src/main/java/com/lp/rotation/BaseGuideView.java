package com.lp.rotation;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 15-6-12.
 */
public abstract class BaseGuideView extends RelativeLayout implements Rotatable, View.OnClickListener {

    protected int mOrientation = 0;

    private GuidViewCallBack mGuideViewCallback;

    public interface GuidViewCallBack {
        void onGuildViewClick();
    }


    public BaseGuideView(Context context) {
//        super(context);
        super(context, null);
        setOnClickListener(this);
        initView(context);
    }


    public BaseGuideView(Context context, AttributeSet attrs) {
//        super(context, attrs);attrs
        super(context, attrs, 0);
        setOnClickListener(this);
        initView(context);
    }

    public BaseGuideView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
        super(context, attrs, defStyleAttr, 0);
        setOnClickListener(this);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseGuideView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOnClickListener(this);
        initView(context);
    }


    @Override
    public void setOrientation(int orientation, boolean isAnimator) {
        mOrientation = orientation;
        requestLayout();

    }

    public void setGuideViewCallBack(GuidViewCallBack callBack) {
        mGuideViewCallback = callBack;
    }

    protected abstract void initView(Context context);

//    private void initUI(Context context){
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        ViwlayoutInflater.inflate(R.layout.rotatinoview,this);
//
//
//    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;//super.onInterceptTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        mGuideViewCallback.onGuildViewClick();
    }
}
