package com.lp.FancyCoverFlow;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.SpinnerAdapter;

/**
 * Created by lp on 2015/7/15.
 */
public class FancyCoverView extends Gallery {

    public static final int ACTION_DISTANCE_AUTO = Integer.MAX_VALUE;
    public static final float SCALEDOWN_GRAVITY_CENTER = 0.5f;
    public static final float SCALEDOWN_GRAVITY_TOP = 0.0f;
    public static final float SCALEDOWN_GRAVITY_BOTTOM = 1.0f;

    private float reflectionRatio = 0.4f;

    private int reflectionGap = 20;

    private boolean reflectionEnabled = false;


    private int actionDistance;

    float mUnselectedAlpha;

    private float unselectedSaturation;

    private float unselectedScale;

    private int maxRotation = 75;

    private float scaleDownGravity = SCALEDOWN_GRAVITY_CENTER;

    private Camera transformationCamera;

    public FancyCoverView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FancyCoverView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FancyCoverView(Context context) {
        super(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FancyCoverView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    void ini() {
        this.transformationCamera = new Camera();
        this.setSpacing(0);
    }


    public float getReflectionRatio() {
        return reflectionRatio;
    }

    public void setReflectionRatio(float reflectionRatio) {
        if (reflectionRatio <= 0 || reflectionRatio > 0.5f) {
            throw new IllegalArgumentException("reflectionRatio may only be in the interval (0, 0.5]");
        }

        this.reflectionRatio = reflectionRatio;

        if (this.getAdapter() != null) {
            ((FancyCoverFlowAdapter) this.getAdapter()).notifyDataSetChanged();
        }
    }

    public int getReflectionGap() {
        return reflectionGap;
    }

    public void setReflectionGap(int reflectionGap) {
        this.reflectionGap = reflectionGap;

        if (this.getAdapter() != null) {
            ((FancyCoverFlowAdapter) this.getAdapter()).notifyDataSetChanged();
        }
    }

    public boolean isReflectionEnabled() {
        return reflectionEnabled;
    }

    public void setReflectionEnabled(boolean reflectionEnabled) {
        this.reflectionEnabled = reflectionEnabled;

        if (this.getAdapter() != null) {
            ((FancyCoverFlowAdapter) this.getAdapter()).notifyDataSetChanged();
        }
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        if (!(adapter instanceof FancyCoverFlowAdapter)) {
            throw new ClassCastException(FancyCoverView.class.getSimpleName() + ":not a FancyCoverFlowAdapter adapter");

        }
        super.setAdapter(adapter);
    }

    public int getMaxRotation() {
        return maxRotation;
    }

    public void setMaxRotation(int maxRotation) {
        this.maxRotation = maxRotation;
    }

    public float getUnselectedAlpha() {
        return this.mUnselectedAlpha;
    }

    public float getUnselectedScale() {
        return unselectedScale;
    }

    public void setUnselectedScale(float unselectedScale) {
        this.unselectedScale = unselectedScale;
    }

    public float getScaleDownGravity() {
        return scaleDownGravity;
    }


    public void setScaleDownGravity(float scaleDownGravity) {
        this.scaleDownGravity = scaleDownGravity;
    }

    public int getActionDistance() {
        return actionDistance;
    }

    public void setActionDistance(int actionDistance) {
        this.actionDistance = actionDistance;
    }

    public float getUnselectedSaturation() {
        return unselectedSaturation;
    }

    @Override
    public void setUnselectedAlpha(float unselectedAlpha) {
        super.setUnselectedAlpha(unselectedAlpha);
        mUnselectedAlpha = unselectedAlpha;
    }

    public void setUnselectedSaturation(float unselectedSaturation) {
        this.unselectedSaturation = unselectedSaturation;
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        FancyCoverFlowItemWrapper item = (FancyCoverFlowItemWrapper) child;
        if (Build.VERSION.SDK_INT >= 16) {
            item.invalidate();
        }

        final int coverFlowWidth = this.getWidth();
        final int coverFlowCenter = coverFlowWidth / 2;
        final int childWidth = item.getWidth();
        final int childHeight = item.getHeight();
        final int childCenter = item.getLeft() + childWidth / 2;
        final int actionDistance = (this.actionDistance == ACTION_DISTANCE_AUTO) ? (int) ((coverFlowWidth + childWidth) / 2.0f) : this.actionDistance;
        final float effectsAmount = Math.min(1.0f, Math.max(-1.0f, (1.0f / actionDistance) * (childCenter - coverFlowCenter)));

        t.clear();
        t.setTransformationType(Transformation.TYPE_BOTH);

        if (this.mUnselectedAlpha != 1) {
            final float alphaAmount = (this.mUnselectedAlpha - 1) * Math.abs(effectsAmount) + 1;
            t.setAlpha(alphaAmount);
        }

        if (this.unselectedSaturation != 1) {
            // Pass over saturation to the wrapper.
            final float saturationAmount = (this.unselectedSaturation - 1) * Math.abs(effectsAmount) + 1;
            item.setSaturation(saturationAmount);
        }

        final Matrix imageMatrix = t.getMatrix();
        if (this.maxRotation != 0) {
            final int rotationAngle = (int) (-effectsAmount * this.maxRotation);
            this.transformationCamera.save();
            transformationCamera.rotateY(rotationAngle);
            transformationCamera.getMatrix(imageMatrix);
            transformationCamera.restore();
        }
        if (this.unselectedScale != 1) {
            final float zoomAmount = (this.unselectedScale - 1) * Math.abs(effectsAmount) + 1;
            final float translateX = childWidth / 2.0f;
            final float translateY = childHeight * this.scaleDownGravity;
            imageMatrix.preTranslate(-translateX, -translateY);
            imageMatrix.postScale(zoomAmount, zoomAmount);
            imageMatrix.postTranslate(translateX, translateX);
        }
        return true;
    }

    public static class LayoutParams extends Gallery.LayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
