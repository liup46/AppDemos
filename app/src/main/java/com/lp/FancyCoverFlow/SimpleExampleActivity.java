package com.lp.FancyCoverFlow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lp.myapp.R;

/**
 * Created by lp on 2015/7/16.
 */
public class SimpleExampleActivity extends Activity {

    private FancyCoverView fancyCoverFlow;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fancy_simple);

        this.fancyCoverFlow = (FancyCoverView) this.findViewById(R.id.fancyCoverFlow);

        this.fancyCoverFlow.setAdapter(new FancyCoverFlowSampleAdapter());
        this.fancyCoverFlow.setUnselectedAlpha(1.0f);
        this.fancyCoverFlow.setUnselectedSaturation(0.0f);
        this.fancyCoverFlow.setUnselectedScale(0.5f);
        this.fancyCoverFlow.setSpacing(50);
        this.fancyCoverFlow.setMaxRotation(0);
        this.fancyCoverFlow.setScaleDownGravity(0.2f);
        this.fancyCoverFlow.setActionDistance(FancyCoverView.ACTION_DISTANCE_AUTO);
    }

    public static class FancyCoverFlowSampleAdapter extends FancyCoverFlowAdapter {
        private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6,};


        @Override
        public View getCoverFlowItem(int position, View reusableView, ViewGroup parent) {
            ImageView imageView;
            if (reusableView != null) {
                imageView = (ImageView) reusableView;

            } else {
                imageView = new ImageView(parent.getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setLayoutParams(new FancyCoverView.LayoutParams(300, 400));

            }

            imageView.setImageResource(getItem(position));
            return imageView;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Integer getItem(int position) {
            return images[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

}
