package com.android.opengl;

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

    // public int leftPad = 50;
    // public int rightPad = 50;
    // public int bottomPad = 100;
    // public int topPad = 50;
    // public BaseGLSurfaceView nextView;

    public BaseGLSurfaceView(Context context) {
        this(context, null);
    }

    public BaseGLSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.myContext = context;

        setDefaultViewSize();

        requestFocus();
        setFocusableInTouchMode(true);
        Log.d("BaseGLSurfaceView", "created:" + Thread.currentThread().toString() + "/"
                + Thread.currentThread().getId());
    }

    /**
     * To set the logical size of the view
     * 
     * @param pWidth
     *            int the width for the view
     * @param pHeight
     *            int the height for the view
     * 
     *            this function should be called before the renderer is created,
     *            which means it should be the 1st thing to do after the view is
     *            created. if not set manually, default value--screen pixel size
     *            will be used
     */
    // public BaseGLSurfaceView setLogicWidthHeight(int pWidth, int pHeight) {
    // // setDefaultSize();
    // if (pWidth > 0) {
    // logicWidth = pWidth;
    // }
    // if (pHeight > 0) {
    // logicHeight = pHeight;
    // }
    //
    // Log.d("logicWidth", String.valueOf(logicWidth));
    // Log.d("logicHeight", String.valueOf(logicHeight));
    // return this;
    // }

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
