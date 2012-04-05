package com.bookreader.views.main;

import android.content.Context;
import android.util.AttributeSet;

import com.android.opengl.BaseGLSurfaceView;

public class MainView extends BaseGLSurfaceView {
    MainScene myScene = null;

    public MainView(Context context) {
        this(context, null);
    }

    public MainView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public BaseGLSurfaceView initView() {
        myScene = new MainScene(this);
        mRenderer.setDrawable(myScene);
        return this;
    }

}
