package com.android.opengl;

import javax.microedition.khronos.opengles.GL10;

import com.android.object.drawable.IDrawable;

public abstract class BaseLayer implements Comparable<BaseLayer> {
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

}
