package com.lp.common.logger;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by pengliu2 on 6/26/15.
 */
public class LogFragment extends Fragment {

    ScrollView mScrollView;
    LogView mLogView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflaterViews();
        mLogView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);

            }
        });

        return result;
    }

    private View inflaterViews() {

        mScrollView = new ScrollView(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mScrollView.setLayoutParams(params);

        mLogView = new LogView(getActivity());

        ViewGroup.LayoutParams logParams = new ViewGroup.LayoutParams(params);
        logParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mLogView.setLayoutParams(logParams);
        mLogView.setClickable(true);
        mLogView.setFocusable(true);
        mLogView.setTypeface(Typeface.MONOSPACE);

        int paddingDips = 16;
        float scale = getResources().getDisplayMetrics().density;
        int paddingPixels = (int) (paddingDips * scale + 0.5);
        mLogView.setPadding(paddingPixels, paddingPixels, paddingPixels, paddingPixels);
        mLogView.setCompoundDrawablePadding(paddingPixels);
        mLogView.setGravity(Gravity.BOTTOM);
        mLogView.setTextAppearance(getActivity(), android.R.style.TextAppearance_Holo_Medium);
        mScrollView.addView(mLogView);
        return mScrollView;

    }

    public LogView getLogView() {
        return mLogView;
    }
}

