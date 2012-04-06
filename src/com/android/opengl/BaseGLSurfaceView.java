package com.android.opengl;

import com.android.object.drawable.BaseLayer;
import com.android.object.drawable.PriorityLayer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;

public class BaseGLSurfaceView extends GLSurfaceView {
    public Context myContext;
    public BaseRenderer mRenderer;
    public boolean viewCreated = false;
    public int viewWidth = 0;
    public int viewHeight = 0;
    private final BaseLayer drawableLayer;

    public BaseGLSurfaceView(Context context) {
        this(context, null);
    }

    public BaseGLSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.myContext = context;
        drawableLayer = new PriorityLayer(this);
        setDefaultViewSize();

        requestFocus();
        setFocusableInTouchMode(true);
        Log.d("BaseGLSurfaceView", "created:" + Thread.currentThread().toString() + "/"
                + Thread.currentThread().getId());
    }

    private void setDefaultViewSize() {
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        viewWidth = dm.widthPixels;
        viewHeight = dm.heightPixels;
    }

    /**
     * A new BaseRenderer should be created and returned. by default, the
     * initViewAsyncHandler will be set as the CreatedHook to the renderer
     * created.
     * 
     * @return the this view.
     */
    public BaseGLSurfaceView createDefaultRenderer() {

        createRenderer(new BaseRenderer(this));
        return this;
    }

    protected BaseGLSurfaceView createRenderer(BaseRenderer pRenderer) {
        mRenderer = pRenderer;
        if (mRenderer != null) {
            setRenderer(mRenderer);

        }
        this.initView();
        return this;

    }

    /**
     * this function will be run right after initRenderer. at this time the
     * renderer may not be created successfully which means all functions depend
     * on gl should not be call in this function.
     * 
     * any drawable objects on this view should be created here and use this
     * function to the top level drawable.
     * mRenderer.setDrawable(myLoadingScene);
     * 
     * @return
     */
    protected BaseGLSurfaceView initView() {
        Log.d("BaseGLSurfaceView", "initView");
        return this;
    }

}
