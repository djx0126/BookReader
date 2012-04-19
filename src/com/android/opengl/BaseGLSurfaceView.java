package com.android.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.android.object.drawable.BaseLayer;
import com.android.object.drawable.LinkedListLayer;

public class BaseGLSurfaceView extends GLSurfaceView {
    public Context mContext;
    public BaseRenderer mRenderer;
    private boolean viewInitialized = false;
    public int viewWidth = 0;
    public int viewHeight = 0;
    protected final BaseLayer layerMgr;

    public BaseGLSurfaceView(Context pContext) {
        this(pContext, null);
    }

    public BaseGLSurfaceView(Context pContext, AttributeSet pAttributeSet) {
        super(pContext, pAttributeSet);
        this.mContext = pContext;
        layerMgr = new LinkedListLayer(this);
        setDefaultViewSize();

        requestFocus();
        setFocusableInTouchMode(true);
        Log.d("BaseGLSurfaceView", "created:" + Thread.currentThread().toString() + "/" + Thread.currentThread().getId());
    }

    private void setDefaultViewSize() {
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        viewWidth = dm.widthPixels;
        viewHeight = dm.heightPixels;
    }

    /**
     * A new BaseRenderer should be created and returned. by default, the initViewAsyncHandler will be set as the CreatedHook to the renderer created.
     * 
     * @return the this view.
     */
    public final BaseGLSurfaceView createDefaultRenderer() {

        createRenderer(new BaseRenderer(this));
        return this;
    }

    protected BaseGLSurfaceView createRenderer(BaseRenderer pRenderer) {
        mRenderer = pRenderer;
        if (mRenderer != null) {
            setRenderer(mRenderer);
        }
        // this.initView();
        mRenderer.setDrawable(layerMgr);
        return this;

    }

    /**
     * this function will be run right after initRenderer. at this time the renderer may not be created successfully which means all functions depend
     * on gl should not be call in this function.
     * 
     * any drawable objects on this view should be created here and use this function to the top level drawable.
     * mRenderer.setDrawable(myLoadingScene);
     * 
     * @return
     */
    protected BaseGLSurfaceView initView() {
        Log.d("BaseGLSurfaceView", "initView");
        return this;
    }

    /**
     * this function will be called automatically when the renderer is changed. in this time, the renderer is successfully created.
     */
    protected synchronized final void afterRendererCreated() {
        viewInitialized = true;
        initView();
    }

    public synchronized final boolean initialized() {
        boolean result = false;
        if (mRenderer != null) {
            result = viewInitialized;
        }
        return result;
    }

    public void setBkgColor(int a, int r, int g, int b) {
        if (mRenderer != null) {
            mRenderer.setClearColor(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

}
