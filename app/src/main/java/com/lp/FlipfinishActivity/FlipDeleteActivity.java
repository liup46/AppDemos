package com.lp.FlipfinishActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.lp.myapp.R;

public class FlipDeleteActivity extends ActionBarActivity {


    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_delete1);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FlipDeleteActivity.this, FlipDelete2.class));
            }
        });

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (Math.abs(velocityX) < 100) {
                    return true;
                }

                //flip down
                if (e2.getRawY() - e1.getRawY() > 200) {
                    return true;
                }
                //flip up
                if (e1.getRawY() - e2.getRawY() > 200) {
                    return true;
                }
                if (e2.getX() - e1.getX() > 0 && e1.getX() >= 0 && e1.getX() < 100) {

                    if (Math.abs(e2.getX() - e1.getX()) > Math.abs(e2.getY() - e1.getY()) && Math.abs(velocityX) > 1000) {
                        overridePendingTransition(R.anim.base_flip_right_in, R.anim.base_flip_right_out);
                        finish();
                        onBackPressed();
                    }
                }


                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.base_flip_right_out);
    }
}
