package com.lp.FancyCoverFlow;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by lp on 2015/7/15.
 */
public abstract class FancyCoverFlowAdapter extends BaseAdapter {


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FancyCoverView coverFlow = (FancyCoverView) parent;
        View wrappedView = null;
        FancyCoverFlowItemWrapper coverItem;

        if (convertView != null) {
            coverItem = (FancyCoverFlowItemWrapper) convertView;
            wrappedView = coverItem.getChildAt(0);
            coverItem.removeAllViews();

        } else {
            coverItem = new FancyCoverFlowItemWrapper(parent.getContext());
        }

        wrappedView = this.getCoverFlowItem(position, wrappedView, parent);

        if (wrappedView == null) {
            throw new NullPointerException("getCoverFlowItem() was expected to return a view, but null was returned.");
        }

        final boolean isReflectionEnabled = coverFlow.isReflectionEnabled();

        if (isReflectionEnabled) {
            coverItem.setReflectionGap(coverFlow.getReflectionGap());
            coverItem.setReflectionRatio(coverFlow.getReflectionRatio());

        }

        coverItem.addView(wrappedView);
        coverItem.setLayoutParams(wrappedView.getLayoutParams());

        return coverItem;
    }

    public abstract View getCoverFlowItem(int position, View reusableView, ViewGroup parent);
}
