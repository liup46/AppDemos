package com.lp.girlsecret;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.lp.myapp.R;

/**
 * Created by lp on 2015/7/15.
 */
public class RemoveActivity extends Activity {

    int position;

    private MediaPlayer mediaPlayer1;
    private MediaPlayer mediaPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getIntent().getIntExtra("position", -1);
        setContentView(new MyView(this));

        initPlay();
    }

    void initPlay() {
        mediaPlayer1 = MediaPlayer.create(this, R.raw.blond5);
        mediaPlayer1.setLooping(false);

        mediaPlayer2 = MediaPlayer.create(this, R.raw.higher13);
        mediaPlayer2.setLooping(false);
    }

    void playMedia1() {
        mediaPlayer1.start();
    }

    void playMedia2() {
        mediaPlayer2.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer1.stop();
        mediaPlayer2.stop();
    }

    class MyView extends View {
        int mWidth;
        int mHeight;
        Bitmap beforeBitmap;
        private Bitmap mBitmap;

        Paint mPaint;
        Canvas mCanvas;

        private int x;
        private int y;

        public MyView(Context context) {
            super(context);
            setFocusable(true);
            mPaint = new Paint();
            mCanvas = new Canvas();
            setWindow();
            setBackground();

            try {
                int drawId = R.drawable.class.getDeclaredField("g" + (RemoveActivity.this.position + 1) + "_up").getInt(this);
                beforeBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), drawId), mWidth, mHeight, true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            setBeforeImage(beforeBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            canvas.drawBitmap(mBitmap, 0, 0, null);
            mCanvas.drawCircle(x, y, 80, mPaint);
            super.onDraw(canvas);
        }

        void setWindow() {
            Point point = new Point();
            getWindowManager().getDefaultDisplay().getSize(point);
            mWidth = point.x;
            mHeight = point.y;

        }

        void setBackground() {
            try {
                int drawableId = R.drawable.class.getDeclaredField("g" + (RemoveActivity.this.position + 1) + "_back").getInt(this);
                this.setBackgroundResource(drawableId);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        void setBeforeImage(Bitmap bitmapTem) {
            mPaint.setAlpha(0);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            mPaint.setAntiAlias(true);

            mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            mCanvas.setBitmap(mBitmap);
            mCanvas.drawBitmap(bitmapTem, 0, 0, null);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            x = (int) event.getX();
            y = (int) event.getY();


            if (y > mHeight / 3 && y < mHeight * 2 / 3 && x > mWidth / 3 && x < mWidth * 2 / 3) {
                playMedia1();
            } else if (y > mHeight * 2 / 3 && y < mHeight - 100 && x > mWidth / 3 && x < mWidth * 2 / 3) {
                playMedia2();
            }
            this.invalidate();

            return true;
        }
    }

}
