package com.bookreader.views.main;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.android.object.drawable.BaseDrawableObject;
import com.android.opengl.BaseGLSurfaceView;
import com.bookreader.config.Settings;

public class MainView extends BaseGLSurfaceView {
    BaseDrawableObject mWall = null;
    BaseDrawableObject mFPS = null;
    BaseDrawableObject mPage = null;

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

        mPage = new PageObj(this, viewWidth, viewHeight);
        layerMgr.insertDrawable(mPage);

        return this;
    }

    @Override
    public void afterRendererCreated() {
        super.afterRendererCreated();
        this.setBkgColor(Settings.bkgColorA, Settings.bkgColorR, Settings.bkgColorG, Settings.bkgColorB);
    }
}
