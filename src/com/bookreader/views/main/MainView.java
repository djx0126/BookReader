package com.bookreader.views.main;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.android.object.drawable.BaseDrawableObject;
import com.android.opengl.BaseGLSurfaceView;
import com.bookreader.config.Settings;

public class MainView extends BaseGLSurfaceView {
    BaseDrawableObject mWall = null;
    BaseDrawableObject mFPS = null;
    BaseDrawableObject currentPage = null;
    BaseDrawableObject nextPage = null;
    BaseDrawableObject prePage = null;

    public MainView(Context context) {
        this(context, null);
    }

    public MainView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public BaseGLSurfaceView initView() {
        Log.d("[MainView]", "initView");

        mFPS = new FPSObj(this);
        layerMgr.insertDrawable(mFPS);

        currentPage = new PageObj(this, viewWidth, viewHeight);
        layerMgr.insertDrawable(currentPage);

        return this;
    }

    @Override
    public void afterRendererCreated() {
        super.afterRendererCreated();
        this.setBkgColor(Settings.bkgColorA, Settings.bkgColorR, Settings.bkgColorG, Settings.bkgColorB);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            if (x > 0 && x < viewWidth && y > 0 && y < viewHeight) {
                Log.d("[MainView]", "x=" + String.valueOf(x) + ", y=" + String.valueOf(y));
                if (x > viewWidth / 2) {
                    nextPage();
                }
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private void nextPage() {
        layerMgr.removeDrawable(currentPage);
        BaseDrawableObject nextPage = new PageObj(this, viewWidth, viewHeight);
        layerMgr.insertDrawable(nextPage);
        currentPage = nextPage;
        Settings.OFFSET = Settings.NEXTPAGEOFFSET;
    }

    private void prePage() {

    }
}
