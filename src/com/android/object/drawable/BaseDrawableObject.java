package com.android.object.drawable;

import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.android.opengl.BaseGLSurfaceView;
import com.android.opengl.texture.BaseTextureHolder;

public abstract class BaseDrawableObject extends AbstractDrawable implements Comparable<BaseDrawableObject> {

    protected BaseTextureHolder texture = null;

    protected List<BaseDrawableObject> childDrawables;

    public int posX;
    public int posY;
    public float width;
    public float height;

    protected BaseGLSurfaceView mView = null;

    public BaseDrawableObject(BaseGLSurfaceView pView) {
        this(pView, 1.0f, 1.0f);
    }

    public BaseDrawableObject(BaseGLSurfaceView pView, float pSize) {
        this(pView, pSize, pSize);
    }

    public BaseDrawableObject(BaseGLSurfaceView pView, float pWidth, float pHeight) {
        this.mView = pView;
        this.width = pWidth;
        this.height = pHeight;
        childDrawables = new LinkedList<BaseDrawableObject>();
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

    @Override
    public final void draw(GL10 gl) {
        if (isActived()) {
            onDraw(gl);
            for (BaseDrawableObject childDrawable : childDrawables) {
                childDrawable.draw(gl);
            }
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
    @Override
    public final void activeDrawable(GL10 gl) {
        // Log.d("[BaseDrawableObject]", "initDrawable");
        doInitDrawable(gl);
        for (BaseDrawableObject childDrawable : childDrawables) {
            childDrawable.activeDrawable(gl);
        }
        activate();
    }

    protected abstract void doInitDrawable(GL10 gl);

    @Override
    public final void deactiveDrawable(GL10 gl) {
        if (isActived()) {
            if (texture != null) {
                texture.unLoadTexture(gl);
            }
            for (BaseDrawableObject childDrawable : childDrawables) {
                childDrawable.deactiveDrawable(gl);
            }
            childDrawables.clear();
        }
        deactive();
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
