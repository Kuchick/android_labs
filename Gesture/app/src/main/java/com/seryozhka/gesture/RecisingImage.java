package com.seryozhka.gesture;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

/**
 * Created by seryozhka on 28.11.16.
 */
public class RecisingImage extends ImageView implements ScaleGestureDetector.OnScaleGestureListener {

    private ScaleGestureDetector mScaleDetector;
    private float mScaleValue = 1.0f;


    public RecisingImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.MATRIX);
        mScaleDetector = new ScaleGestureDetector(context, this);
        Log.d("SERZH", "In Recizing Image constructor");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = mScaleDetector.onTouchEvent(event);

        Log.d("SERZH", "onTouchEvent, boolean = " + String.valueOf(handled));
        if (!handled){
            handled = super.onTouchEvent(event);
        }
        return handled;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        Log.d("SERZH", "onScale");
        //return false;
        mScaleValue *= detector.getScaleFactor();

        Matrix m = new Matrix(getImageMatrix());
        m.setScale(mScaleValue, mScaleValue);
        setImageMatrix(m);

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        Log.d("SERZH", "onScaleBegin");
        //return false;
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Log.d("SERZH", "onScaleEnd");

    }
}
