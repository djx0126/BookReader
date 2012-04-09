package com.android.object.drawable;

import javax.microedition.khronos.opengles.GL10;

import com.android.opengl.BaseGLSurfaceView;

public abstract class BaseLayer extends AbstractDrawable implements Comparable<BaseLayer> {
    // public final static int STATE_NOT_INIT = 0;
    // public final static int STATE_ACTIVE = 2;
    // private int state = STATE_NOT_INIT;
    protected int priority = 0;
    protected BaseGLSurfaceView mView;

    public BaseLayer(BaseGLSurfaceView pView) {
        mView = pView;

    }

    @Override
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

    @Override
    public void deactiveDrawable(GL10 gl) {
        if (isActived()) {
            deactive();
        }
    }

    @Override
    public void activeDrawable(GL10 gl) {
        // Log.d("[BaseLayer]", "initDrawable");
        doInitDrawable(gl);
        activate();
    }

    protected abstract void doInitDrawable(GL10 gl);
}
