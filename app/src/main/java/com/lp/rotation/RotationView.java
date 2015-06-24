package com.lp.rotation;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lp.myapp.R;

/**
 * Created by Administrator on 15-6-13.
 */
public class RotationView extends BaseGuideView {

    View mView;
    FrameLayout mLayout;
    TextView mTextView;
    Drawable mDrawable;

    private static final String TAG = "RotationView.Class";


    public RotationView(Context context) {
        super(context);
    }

    public RotationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RotationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        mView = layoutInflater.inflate(R.layout.rotatinoview, this, true);
        mLayout = (FrameLayout) mView.findViewById(R.id.guid_layout);
        mTextView = (TextView) mView.findViewById(R.id.guid_textview);
        mDrawable = context.getResources().getDrawable(R.drawable.ong);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Log.i(TAG, "onMeasure enter...");
        Log.i(TAG, "width = " + getWidth() + " height = " + getHeight());
        Log.i(TAG, "MeasuredWidth = " + getMeasuredWidth() + " MeasuredHeight = " + getMeasuredHeight());

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.i(TAG, "00000000000 width = " + getWidth() + " height = " + getHeight());
        Log.i(TAG, "00000000000 MeasuredWidth = " + getMeasuredWidth() + " MeasuredHeight = " + getMeasuredHeight());
        Log.i(TAG, "onMeasure exit...");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        Log.i(TAG, "width = " + getWidth() + " height = " + getHeight());

        int transX = 0;
        int transY = 0;

        if (mOrientation == 0) {
            mLayout.setRotation(0);
            transX += 0;
            transY += 0;
        } else if (mOrientation == 90) {
            mLayout.setRotation(270);
            transX += -DisplayUtil.dip2px(getContext(), 25);
            transY += b - DisplayUtil.dip2px(getContext(), 200 - 25);

        } else if (mOrientation == 180) {
            mLayout.setRotation(180);
            transX += DisplayUtil.dip2px(getContext(), 160);
            transY += b - DisplayUtil.dip2px(getContext(), 150);

        } else if (mOrientation == 270) {
            mLayout.setRotation(90);
            transX += -DisplayUtil.dip2px(getContext(), 25) + DisplayUtil.dip2px(getContext(), 210);
            transY += DisplayUtil.dip2px(getContext(), 25);

        }

//		mLayout.setX(transX);
//		mLayout.setY(transY);

        mLayout.setTranslationX(transX);
        mLayout.setTranslationY(transY);

        LayoutParams params = (LayoutParams) mLayout.getLayoutParams();
        params.leftMargin = 100;
        params.topMargin = 100;
        mLayout.setLayoutParams(params);
        super.onLayout(changed, l, t, r, b);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i(TAG, "width = " + getWidth() + " height = " + getHeight());
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw enter...");
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.i(TAG, "dispatchDraw enter...");
        super.dispatchDraw(canvas);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Log.i(TAG, "drawChild enter...");
        int w = getWidth();
        int h = getHeight();

        Point centerPoint = new Point(w / 2, h / 2);
        canvas.save();
        mDrawable.setBounds(centerPoint.x - 150, centerPoint.y - 150, centerPoint.x + 150, centerPoint.y + 150);
        mDrawable.draw(canvas);
        canvas.restore();
        return super.drawChild(canvas, child, drawingTime);
    }
}
