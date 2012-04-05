package com.android.object.drawable;

import javax.microedition.khronos.opengles.GL10;

import com.android.opengl.BaseGLSurfaceView;

public abstract class BaseDrawableObject implements IDrawable, Comparable<BaseDrawableObject> {
    public int posX;
    public int posY;
    public float width;
    public float height;
    public boolean initiated = false;
    public boolean active = true;
    public BaseGLSurfaceView myView;
    public GL10 gl;

    public BaseDrawableObject(BaseGLSurfaceView pView) {
        this.myView = pView;

    }

    public final void draw() {
        if (!initiated && myView.mRenderer.isInitiated) {
            initDrawable(myView.mRenderer.gl);
        }
        if (initiated && active) {
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
            initiated = true;
        }
    }

    protected abstract void initDrawable();

    public boolean isInitiated() {
        return initiated;
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
