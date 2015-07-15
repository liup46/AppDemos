package com.lp.FlipDeleteListItem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lp.myapp.R;

/**
 * Created by pengliu2 on 6/23/15.
 */
public class MyListView extends ListView implements GestureDetector.OnGestureListener, View.OnTouchListener {

    GestureDetector mGDetector;

    boolean mIsDeleteShown;

    View mDeleteBtn;

    ViewGroup mSelectListItem;

    OnDeleteListener mOnDeleteListener;

    private int mSelectIndex;

    public interface OnDeleteListener {

        void onDelete(int index);
    }

    public MyListView(Context context) {
        this(context, null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGDetector = new GestureDetector(this.getContext(), this);
        setOnTouchListener(this);
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        mGDetector = new GestureDetector(this.getContext(), this);
//        setOnTouchListener(this);
//
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    public void setOnDeleteListener(OnDeleteListener deleteListener) {
        mOnDeleteListener = deleteListener;
    }

    private void hideDelete() {
        mDeleteBtn.setVisibility(GONE);
//      mSelectListItem.removeView(mDeleteBtn);
//      mDeleteBtn = null;
        mIsDeleteShown = false;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mIsDeleteShown) {
            hideDelete();
            invalidateViews();
            return false;
        }
        return mGDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mSelectIndex = pointToPosition((int) e.getX(), (int) e.getY());

        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        mSelectListItem = (ViewGroup) getChildAt(mSelectIndex - getFirstVisiblePosition());
        mDeleteBtn = mSelectListItem.findViewById(R.id.deletebtn);
        if (!mIsDeleteShown && Math.abs(velocityX) > Math.abs(velocityY) && velocityX < 0) {
//            mDeleteBtn = LayoutInflater.from(getContext()).inflate(R.layout.delete_button, null);
            mDeleteBtn.setVisibility(VISIBLE);
            mDeleteBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideDelete();
                    mOnDeleteListener.onDelete(mSelectIndex);
                }
            });


//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            params.addRule(RelativeLayout.CENTER_VERTICAL);
//            mSelectListItem.addView(mDeleteBtn, params);
            mIsDeleteShown = true;
        } else if (mIsDeleteShown && Math.abs(velocityX) > Math.abs(velocityY) && velocityX > 0) {
            hideDelete();
        }

        return false;
    }
}
