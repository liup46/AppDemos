package com.lp.slideviewpagebasic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by pengliu2 on 6/29/15.
 */
public class SlidingTabStrip extends LinearLayout {

    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 2;
    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFF33B5E5;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 8;

    private static final int DEFAULT_DIVIDER_THICKNESS_DIPS = 1;
    private static final byte DEFAULT_DIVIDER_COLOR_ALPHA = 0x20;
    private static final float DEFAULT_DIVIDER_HEIGHT = 0.5f;

    private final int mDefaultBottomBorderColor;

    private final Paint mBottomBorderPaint;
    private final Paint mSelectedIndicatorPaint;

    private final int mBottomBorderThickness;
    private final int mSelectedIndicatorThickness;

    private final Paint mDividerPaint;
    private final float mDividerHeight;
    private final SimpleTabColorizer mDefaultTabColorizer;
    private int mSelectedPositon;
    private float mSelectedOffset;
    private SlidingTabLayout.TabColorizer mCustomTabColorizer;

    public SlidingTabStrip(Context context) {
        this(context, null);
    }

    public SlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

        final float density = getResources().getDisplayMetrics().density;

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.colorForeground, outValue, true);
        final int themeForegroundColor = outValue.data;
        mDefaultBottomBorderColor = setColorAlpha(themeForegroundColor, DEFAULT_BOTTOM_BORDER_COLOR_ALPHA);

        mDefaultTabColorizer = new SimpleTabColorizer();
        mDefaultTabColorizer.setIndicatorColors(DEFAULT_SELECTED_INDICATOR_COLOR);
        mDefaultTabColorizer.setDividerColors(setColorAlpha(themeForegroundColor, DEFAULT_DIVIDER_COLOR_ALPHA));

        mBottomBorderPaint = new Paint();

        mBottomBorderThickness = (int) (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density);
        mBottomBorderPaint.setColor(mDefaultBottomBorderColor);

        mSelectedIndicatorThickness = (int) (SELECTED_INDICATOR_THICKNESS_DIPS * density);
        mSelectedIndicatorPaint = new Paint();

        mDividerHeight = DEFAULT_DIVIDER_HEIGHT;
        mDividerPaint = new Paint();
        mDividerPaint.setStrokeWidth((int) (DEFAULT_DIVIDER_THICKNESS_DIPS * density));

    }

    private static int setColorAlpha(int color, byte alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    private static int blendColors(int color1, int color2, float radio) {
        final float inverseRation = 1f - radio;
        float r = Color.red(color1) * radio + Color.red(color2) * inverseRation;
        float g = Color.green(color1) * radio + Color.green(color2) * inverseRation;
        float b = Color.blue(color1) * radio + Color.blue(color2) * inverseRation;
        return Color.rgb((int) r, (int) g, (int) b);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int height = getHeight();
        final int childCount = getChildCount();
        final int dividerHeightPx = (int) (Math.min(Math.max(0f, mDividerHeight), 1f) * height);
        final SlidingTabLayout.TabColorizer tabColorizer = mCustomTabColorizer != null ? mCustomTabColorizer : mDefaultTabColorizer;
        if (childCount > 0) {
            View selectedTitle = getChildAt(mSelectedPositon);
            int left = selectedTitle.getLeft();
            int right = selectedTitle.getRight();
            int color = tabColorizer.getIndicatorColor(mSelectedPositon);

            if (mSelectedOffset > 0f && mSelectedPositon < (getChildCount() - 1)) {
                int nextColor = tabColorizer.getIndicatorColor(mSelectedPositon + 1);
                if (color != nextColor) {
                    color = blendColors(nextColor, color, mSelectedOffset);
                }

                View nextTitle = getChildAt(mSelectedPositon + 1);
                left = (int) (nextTitle.getLeft() * mSelectedOffset + (1.0f - mSelectedOffset) * left);
                right = (int) (mSelectedOffset * nextTitle.getRight() + (1.0f - mSelectedOffset) * right);

            }
            mSelectedIndicatorPaint.setColor(color);
            canvas.drawRect(left, height - mSelectedIndicatorThickness, right, height, mSelectedIndicatorPaint);

        }
        canvas.drawRect(0, height - mBottomBorderThickness, getWidth(), height, mBottomBorderPaint);

        int separatorTop = (height - dividerHeightPx) / 2;
        for (int i = 0; i < getChildCount() - 1; i++) {
            View child = getChildAt(i);
            canvas.drawRect(child.getRight(), separatorTop, child.getRight(), separatorTop + dividerHeightPx, mDividerPaint);
        }

    }

    void setCustomTabColorizer(SlidingTabLayout.TabColorizer customTabColorizer) {
        mCustomTabColorizer = customTabColorizer;
        invalidate();

    }

    void setSelectedIndicatorColors(int... colors) {
        mCustomTabColorizer = null;
        mDefaultTabColorizer.setIndicatorColors(colors);
        invalidate();

    }

    void setDividerColors(int... colors) {
        mCustomTabColorizer = null;
        mDefaultTabColorizer.setDividerColors(colors);
        invalidate();
    }

    void onViewPaperPageChanged(int position, float positionOffset) {
        mSelectedPositon = position;
        mSelectedOffset = positionOffset;
        invalidate();
    }

    private static class SimpleTabColorizer implements SlidingTabLayout.TabColorizer {

        private int[] mIndicatorColors;
        private int[] mDividerColors;

        @Override
        public int getIndicatorColor(int position) {
            return mIndicatorColors[position % mIndicatorColors.length];
        }

        @Override
        public int getDividerColor(int position) {
            return mDividerColors[position % mDividerColors.length];
        }

        void setIndicatorColors(int... colors) {
            mIndicatorColors = colors;
        }

        void setDividerColors(int... colors) {
            mDividerColors = colors;
        }
    }

}
