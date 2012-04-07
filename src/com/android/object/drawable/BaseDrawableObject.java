package com.android.object.drawable;

import javax.microedition.khronos.opengles.GL10;

import com.android.opengl.BaseGLSurfaceView;
import com.android.opengl.texture.BaseTextureHolder;

public abstract class BaseDrawableObject implements IDrawable, Comparable<BaseDrawableObject> {
    public final static int STATE_NOT_ACTIVE = 0;
    public final static int STATE_ACTIVE = 1;
    private int state = STATE_NOT_ACTIVE;

    protected BaseTextureHolder texture = null;

    public int posX;
    public int posY;
    public float width;
    public float height;

    protected BaseGLSurfaceView mView = null;

    public BaseDrawableObject(BaseGLSurfaceView pView) {
        this.mView = pView;
        this.width = 1.0f;
        this.height = 1.0f;
    }

    public BaseDrawableObject(BaseGLSurfaceView pView, float pWidth, float pHeight) {
        this.mView = pView;
        this.width = pWidth;
        this.height = pHeight;
    }

    public BaseDrawableObject(BaseGLSurfaceView pView, float pSize) {
        this.mView = pView;
        this.width = pSize;
        this.height = pSize;
    }

    public final void setWidth(float pWidth) {
        this.width = pWidth;
    }

    public final void setHeight(float pWidth) {
        this.width = pWidth;
    }

    public final void setPos(int pX, int pY) {
        this.posX = pX;
        this.posY = pY;
    }

    public final void draw(GL10 gl) {
        if (isActived()) {
            onDraw(gl);
        }
    }

    /**
     * draw the object with texture binded. the base is (0,0) at left bottom.
     * 
     * @param gl
     */
    protected void onDraw(GL10 gl) {
        if (texture != null) {
            texture.draw(gl, posX, posY, width, height);
        }
    }

    public void putTexture(BaseTextureHolder pTexture) {
        this.texture = pTexture;
    }

    /**
     * this function will be run right after the Renderer created. at this time the renderer is created successfully.
     * 
     * @return
     */
    public void initDrawable(GL10 gl) {
        // Log.d("[BaseDrawableObject]", "initDrawable");
        activate();
    }

    public final void activate() {
        state = STATE_ACTIVE;
    }

    public final void deactive() {
        if (isActived()) {
            state = STATE_NOT_ACTIVE;
        }
    }

    public final boolean isActived() {
        return state == STATE_ACTIVE;
    }

    public final int compareTo(BaseDrawableObject objB) {
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
