package com.android.object.drawable;

import javax.microedition.khronos.opengles.GL10;

import com.android.opengl.BaseGLSurfaceView;

public abstract class BaseLayer implements IDrawable, Comparable<BaseLayer> {
    public final static int STATE_NOT_INIT = 0;
    // public final static int STATE_INIT_NOT_ACTIVE = 1;
    public final static int STATE_ACTIVE = 2;
    private int state = STATE_NOT_INIT;
    protected int priority = 0;
    protected BaseGLSurfaceView mView;

    public BaseLayer(BaseGLSurfaceView pView) {
        mView = pView;

    }

    public abstract void draw(GL10 gl);

    public abstract void insertDrawable(IDrawable drawableObj);

    public abstract void removeDrawable(IDrawable drawableObj);

    public abstract void clearDrawable();

    public int compareTo(BaseLayer layerB) {
        if (this.priority > layerB.priority) {
            return 1;
        } else if (this.priority < layerB.priority) {
            return -1;
        }
        return 0;
    }

    public void activate() {
        state = STATE_ACTIVE;
    }

    public void deactive() {
        if (isActived()) {
            state = STATE_NOT_INIT;
        }
    }

    public boolean isActived() {
        return state == STATE_ACTIVE;
    }

    public void initDrawable(GL10 gl) {
        // Log.d("[BaseLayer]", "initDrawable");
        doInitDrawable(gl);
        activate();
    }

    protected abstract void doInitDrawable(GL10 gl);
}
