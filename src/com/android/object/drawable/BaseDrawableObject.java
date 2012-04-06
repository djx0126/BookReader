package com.android.object.drawable;

import javax.microedition.khronos.opengles.GL10;

import com.android.opengl.BaseRenderer;

public abstract class BaseDrawableObject implements IDrawable, Comparable<BaseDrawableObject> {
    public final static int STATE_NOT_INIT = 0;
    public final static int STATE_INIT = 1;
    public final static int STATE_ACTIVE = 2;
    private int state = STATE_NOT_INIT;

    public int posX;
    public int posY;
    public float width;
    public float height;

    // public BaseGLSurfaceView myView;
    protected final BaseRenderer mRenderer;
    public GL10 gl;

    public BaseDrawableObject(BaseRenderer pRenderer) {
        this.mRenderer = pRenderer;
    }

    public void setWidth(float pWidth) {
        this.width = pWidth;
    }

    public void setHeight(float pWidth) {
        this.width = pWidth;
    }

    public void setPos(int pX, int pY) {
        this.posX = pX;
        this.posY = pY;
    }

    public final void draw() {
        if (!isInitiated() && mRenderer.isInitiated) {
            initDrawable(mRenderer.gl);
        }
        if (isActived()) {
            onDraw();
        }
    }

    protected abstract void onDraw();

    /**
     * this function will be run right after the Renderer created. at this time
     * the renderer is created successfully.
     * 
     * @return
     */
    public final void initDrawable(GL10 gl) {
        if (gl != null) {
            this.gl = gl;
            initDrawable();
            state = STATE_ACTIVE;
        }
    }

    protected abstract void initDrawable();

    public boolean isInitiated() {
        return state == STATE_INIT || state == STATE_ACTIVE;
    }

    public void activate() {
        if (isInitiated()) {
            state = STATE_ACTIVE;
        } else {
            initDrawable(this.gl);
        }
    }

    public void deactive() {
        if (isActived()) {
            state = STATE_INIT;
        }
    }

    public boolean isActived() {
        return state == STATE_ACTIVE;
    }

    public int compareTo(BaseDrawableObject objB) {
        // upper right is smaller than lower left
        if (this.posX > objB.posX && this.posY > objB.posY) {
            return -1;
        }
        if (this.posX < objB.posX && this.posY < objB.posY) {
            return 1;
        }
        if (objB.posX == this.posX) {
            if (this.posY > objB.posY) {
                return -1;
            } else if (this.posY < objB.posY) {
                return 1;
            } else {
                return 0;
            }
        }
        if (objB.posY == this.posY) {
            if (this.posX > objB.posX) {
                return -1;
            } else if (this.posX < objB.posX) {
                return 1;
            } else {
                return 0;
            }
        }
        return 0;
    }
}
