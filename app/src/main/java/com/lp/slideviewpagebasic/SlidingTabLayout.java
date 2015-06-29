package com.lp.slideviewpagebasic;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

/**
 * Created by pengliu2 on 6/29/15.
 */
public class SlidingTabLayout extends HorizontalScrollView {

    private static final int TITLE_OFFSET_DIPS = 24;
    private static final int TAB_VIEW_PADDING_DIPS = 16;
    private static final int TAB_VIEW_TEXT_SIZE_SP = 12;
    private int mTitleOffsert;
    private int mTabViewLayoutId;
    private int mTabViewTextViewId;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private SlidingTabStrip mTabStrip;

    public SlidingTabLayout(Context context) {
        this(context, null);
    }


    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setHorizontalScrollBarEnabled(false);
        setFillViewport(true);

        mTitleOffsert = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

        mTabStrip = new SlidingTabStrip(context);

        addView(mTabStrip, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//        setScrollbarFadingEnabled();
//        setVerticalScrollBarEnabled();
    }

    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        mTabStrip.setCustomTabColorizer(tabColorizer);
    }

    public void setSelectedIndicatorColors(int... colors) {
        mTabStrip.setSelectedIndicatorColors(colors);
    }

    public void setDividerColors(int... colors) {
        mTabStrip.setDividerColors(colors);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

    public void setCustomTabView(int layoutResId, int textViewId) {
        mTabViewLayoutId = layoutResId;
        mTabViewTextViewId = textViewId;
    }

    public void setViewPager(ViewPager viewPager) {
        mTabStrip.removeAllViews();
        mViewPager = viewPager;
        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();
        }
    }

    private void populateTabStrip() {
        final PagerAdapter pagerAdapter = mViewPager.getAdapter();
        final View.OnClickListener tabClickListener = new TabClickListener();

        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            View tabView = null;
            TextView tabTitleView = null;

            if (mTabViewLayoutId != 0) {
                tabView = LayoutInflater.from(getContext()).inflate(mTabViewLayoutId, mTabStrip, false);
                tabTitleView = (TextView) tabTitleView.findViewById(mTabViewTextViewId);

            }
            if (tabView == null) {
                tabView = createDefaultTabView(getContext());
            }
            if (tabTitleView == null && TextView.class.isInstance(tabView)) {

                tabTitleView = (TextView) tabView;
            }
            tabTitleView.setText(pagerAdapter.getPageTitle(i));
            tabTitleView.setOnClickListener(tabClickListener);
            mTabStrip.addView(tabView);

        }
    }

    private View createDefaultTabView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP);
        textView.setTypeface(Typeface.DEFAULT_BOLD);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            textView.setBackgroundResource(outValue.resourceId);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            textView.setAllCaps(true);
        }

        int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
        textView.setPadding(padding, padding, padding, padding);
        return textView;

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (mViewPager != null) {
            scrollToTab(mViewPager.getCurrentItem(), 0);
        }
    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = mTabStrip.getChildCount();

        if (tabIndex < 0 || tabStripChildCount == 0 || tabIndex >= tabStripChildCount) {
            return;
        }
        View tabView = mTabStrip.getChildAt(tabIndex);
        if (tabView != null) {
            int targetScrollX = tabView.getLeft() + positionOffset;

            if (tabIndex > 0 || positionOffset > 0) {
                targetScrollX -= mTitleOffsert;
            }

            scrollTo(targetScrollX, 0);
        }

    }


    public interface TabColorizer {
        int getIndicatorColor(int position);

        int getDividerColor(int position);
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int stripChildCount = mTabStrip.getChildCount();

            if (stripChildCount == 0 || position < 0 || position >= stripChildCount) {
                return;

            }
            mTabStrip.onViewPaperPageChanged(position, positionOffset);

            View selectedChild = mTabStrip.getChildAt(position);
            int extraOffset = (selectedChild != null) ? (int) (selectedChild.getWidth() * positionOffset) : 0;
            scrollToTab(position, extraOffset);
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);

            }
        }

        @Override
        public void onPageSelected(int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStrip.onViewPaperPageChanged(position, 0f);
                scrollToTab(position, 0);

            }

            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageSelected(position);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

            mScrollState = state;
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageScrollStateChanged(state);
            }


        }
    }

    private class TabClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                if (v == mTabStrip.getChildAt(i)) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    }

}
