package com.lp.rotation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lp.myapp.R;


public class RotationActivity extends ActionBarActivity implements Rotatable {

    ViewGroup mGuildLayout;
    Button mButton;
    BaseGuideView mBaseGuideView = null;
    OrientationEventListener mOrientationListener;
    int mOrientation = 0;
    int mOrientationCompensation = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation);
        initUI();

        mOrientationListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {

                if (orientation == ORIENTATION_UNKNOWN) {
                    return;
                }

                mOrientation = RotationUtil.roundOrientation(orientation, mOrientation);
                int orientationCompensation = (mOrientation + RotationUtil.getDisplayRotation(RotationActivity.this)) / 360;
                if (mOrientationCompensation != orientationCompensation) {
                    mOrientationCompensation = orientationCompensation;
                    Log.i("YanZi", "mOrientationCompensation = " + mOrientationCompensation);
                    int mTempOrientation = (mOrientationCompensation == -1 ? 0 :
                            mOrientationCompensation);
                    setOrientation(mTempOrientation, false);
                }
            }
        };
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showGuideView();
            }
        });

    }

    private void initUI() {
        mGuildLayout = (ViewGroup) findViewById(R.id.guide_layout);
        mButton = (Button) findViewById(R.id.btn_show_guide);

    }

    private void showGuideView() {
        mButton.setEnabled(false);
        mGuildLayout.removeAllViews();
        mGuildLayout.setBackgroundColor(Color.GRAY);
        if (mBaseGuideView == null) {
            mBaseGuideView = new RotationView(getApplicationContext());
            mBaseGuideView.setGuideViewCallBack(new BaseGuideView.GuidViewCallBack() {

                @Override
                public void onGuildViewClick() {
                    hideGuideView();
                }
            });
            mGuildLayout.addView(mBaseGuideView);
        }
    }

    private void hideGuideView() {
        mButton.setEnabled(true);
        mGuildLayout.setBackgroundColor(Color.TRANSPARENT);
        if (mBaseGuideView != null) {
            mGuildLayout.removeView(mBaseGuideView);
            mBaseGuideView = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOrientationListener.enable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mOrientationListener.disable();
    }

    @Override
    public void setOrientation(int orientation, boolean isAnimator) {
        // TODO Auto-generated method stub
        if (mBaseGuideView != null) {
            mBaseGuideView.setOrientation(mOrientation, false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
