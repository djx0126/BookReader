package com.android.opengl;

import com.android.object.drawable.IDrawable;

public abstract class BaseLayer implements Comparable<BaseLayer> {
    protected int priority = 0;
    protected BaseGLSurfaceView myView;

    public BaseLayer(BaseGLSurfaceView pView) {
        myView = pView;

    }

    public abstract void draw();

    public abstract void insertDrawable(IDrawable drawableObj);

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
