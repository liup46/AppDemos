package com.lp.FancyCoverFlow;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lp on 2015/7/15.
 */
public class FancyCoverFlowItemWrapper extends ViewGroup {
    Paint mPaint;
    ColorMatrix mColorMatrix;
    float mSaturation;
    float mOriginalScaledownFactor;
    boolean mIsReflectionEnabled;
    float mImageReflectionRatio;

    private float imageReflectionRatio;
    int mReflectionGap;

    private Bitmap wrappedViewBitmap;

    private Canvas wrappedViewDrawingCanvas;


    public FancyCoverFlowItemWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FancyCoverFlowItemWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FancyCoverFlowItemWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public FancyCoverFlowItemWrapper(Context context) {
        super(context);
        init();
    }

    void init() {
        mPaint = new Paint();
        mColorMatrix = new ColorMatrix();
        this.setSaturation(1);
    }

    void setReflectionEnabled(boolean hasReflection) {
        if (hasReflection != this.mIsReflectionEnabled) {
            this.mIsReflectionEnabled = hasReflection;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                // Turn off hardware acceleration if necessary (reflections won't support it).
                this.setLayerType(hasReflection ? View.LAYER_TYPE_SOFTWARE : View.LAYER_TYPE_HARDWARE, null);
            }

            this.remeasureChildren();
        }
    }

    void setReflectionRatio(float imageReflectionRatio) {
        if (imageReflectionRatio != this.imageReflectionRatio) {
            this.imageReflectionRatio = imageReflectionRatio;
            this.remeasureChildren();
        }
    }

    void setReflectionGap(int reflectionGap) {
        if (reflectionGap != this.mReflectionGap) {
            this.mReflectionGap = reflectionGap;
            this.remeasureChildren();
        }
    }


    public void setSaturation(float saturation) {
        if (saturation != this.mSaturation) {
            mSaturation = saturation;
            mColorMatrix.setSaturation(mSaturation);
            mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.remeasureChildren();

        if (this.mIsReflectionEnabled) {
            setMeasuredDimension((int) mOriginalScaledownFactor * getMeasuredWidth(), (int) (mOriginalScaledownFactor * getMeasuredHeight()));
        }
    }

    void remeasureChildren() {
        View child = this.getChildAt(0);
        if (child != null) {
            final int originalChildHeight = this.getMeasuredHeight();
            this.mOriginalScaledownFactor = mIsReflectionEnabled ? (originalChildHeight * (1 - mImageReflectionRatio) - mReflectionGap) / originalChildHeight : 1.0f;
            final int childHeight = (int) (this.mOriginalScaledownFactor * originalChildHeight);
            final int childWidth = (int) (this.mOriginalScaledownFactor * getMeasuredHeight());

            int heightSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST);
            int widthSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST);
            child.measure(heightSpec, widthSpec);

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int measureWidth = getMeasuredWidth();
            int measureHeight = getMeasuredHeight();

            if (this.wrappedViewBitmap == null || wrappedViewBitmap.getHeight() != measureHeight || wrappedViewBitmap.getWidth() != measureWidth) {
                this.wrappedViewBitmap = Bitmap.createBitmap(measureWidth, measureHeight, Bitmap.Config.ARGB_8888);
                wrappedViewDrawingCanvas = new Canvas(wrappedViewBitmap);
            }
            View child = getChildAt(0);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            int childLeft = (measureWidth - childWidth) / 2;
            int childRigth = measureWidth - childLeft;
            child.layout(childLeft, 0, childRigth, childHeight);

        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        View child = getChildAt(0);
        if (child != null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                if (child.isDirty()) {
                    child.draw(this.wrappedViewDrawingCanvas);

                    if (mIsReflectionEnabled) {
                        this.createReflectedImages();
                    }
                }
            } else {
                child.draw(this.wrappedViewDrawingCanvas);

            }
        }
        canvas.drawBitmap(this.wrappedViewBitmap, (this.getWidth() - child.getWidth()) / 2, 0, mPaint);

    }

    void createReflectedImages() {
        int width = wrappedViewBitmap.getWidth();
        int height = wrappedViewBitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(1, -1);

        int scaledDownHeight = (int) (height * mOriginalScaledownFactor);
        int invertedHeight = height - scaledDownHeight - mReflectionGap;
        int invertedBitmapTop = scaledDownHeight - mReflectionGap;
        Bitmap invertedBitmap = Bitmap.createBitmap(wrappedViewBitmap, 0, invertedBitmapTop, width, invertedHeight, matrix, true);
        wrappedViewDrawingCanvas.drawBitmap(invertedBitmap, 0, scaledDownHeight + mReflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, height * imageReflectionRatio + mReflectionGap, 0, height, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        wrappedViewDrawingCanvas.drawRect(0, height * (1 - imageReflectionRatio), width, height, paint);
    }

}
