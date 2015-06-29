package com.lp.slideviewpagebasic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lp.common.logger.Log;
import com.lp.myapp.R;

/**
 * Created by pengliu2 on 6/29/15.
 */
public class SlidingTabsBasicFragment extends Fragment {

    static final String LOG_TAG = "SlidingTabsBasicFragment";

    private SlidingTabLayout mSlideTabLayout;

    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slidebasic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mViewPager = (ViewPager) view.findViewById(R.id.slidebasic_viewpage);
        mViewPager.setAdapter(new SamplePagerAdapter());
        mSlideTabLayout = (SlidingTabLayout) view.findViewById(R.id.slidebasic_tabs);
        mSlideTabLayout.setViewPager(mViewPager);


    }

    private class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Item" + (position + 1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.slidebasic_pageitem, container, false);
            container.addView(view);
            TextView title = (TextView) view.findViewById(R.id.item_title);
            title.setText(String.valueOf(position + 1));

            Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Log.i(LOG_TAG, "destroyItem() [position: " + position + "]");
        }
    }
}
