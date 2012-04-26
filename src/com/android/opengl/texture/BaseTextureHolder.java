package com.android.opengl.texture;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import com.android.opengl.BaseRenderer;
import com.android.opengl.utils.BaseGLUnit;

public abstract class BaseTextureHolder {
    protected static final float Z = BaseRenderer.Z;
    // protected BaseRenderer myRenderer;
    protected int texBuffer[];
    protected int texture;
    protected BaseGLUnit GLUnit = BaseGLUnit.NORMALSHORT;

    /*
     * default draw at (0,0) after loadIdentity and translate and scale
     */
    public abstract void draw(GL10 gl);

    /*
     * draw at (posX,posY) by default at (0,0)
     */
    public void draw(GL10 gl, int posX, int posY) {
        draw(gl, posX, posY, 1.0f, 1.0f);
    }

    public void draw(GL10 gl, int posX, int posY, float scale) {
        draw(gl, posX, posY, scale, scale);
    }

    /*
     * draw at (posX,posY) by default at (0,0), no scale 1,loadIdentify 2,translatef 3,scalef 4,draw
     */
    public void draw(GL10 gl, int posX, int posY, float scaleX, float scaleY) {
        gl.glTranslatef(posX, posY, Z);
        gl.glScalef(scaleX, scaleY, 1.0f);
        draw(gl);
    }

    /**
     * @param texture
     * @param bitmap
     * @param recycleAfterBind
     *            if need to recycle the bitmap after the bind? default is recycle *
     */
    protected void bindTexture(GL10 gl, int texture, Bitmap bitmap, boolean recycleAfterBind) {
        // Log.d("[BaseTextureHolder]", "bindTexture");
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        if (recycleAfterBind) {
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
    }

    protected void bindTexture(GL10 gl, int texture, Bitmap bitmap) {
        bindTexture(gl, texture, bitmap, true);
    }

    protected void bindTexture(GL10 gl, Bitmap bitmap, boolean recycleAfterBind) {
        bindTexture(gl, texture, bitmap, recycleAfterBind);
    }

    protected void bindTexture(GL10 gl, Bitmap bitmap) {
        bindTexture(gl, texture, bitmap, true);
    }

    protected int initTexture(GL10 gl) {
        // Log.d("[BaseTextureHolder]", "initTexture");
        // //IntBuffer intBuffer=IntBuffer.allocate(1);
        // gl.glGenTextures(1, intBuffer);
        // texture = intBuffer.get();
        texBuffer = new int[1];
        gl.glGenTextures(1, texBuffer, 0);
        int texture = texBuffer[0];
        return texture;
    }

    public void unLoadTexture(GL10 gl) {
        if (texBuffer != null) {
            gl.glDeleteTextures(1, texBuffer, 0);
        }
    }

    public BaseTextureHolder(GL10 gl) {
        texture = initTexture(gl);
    }

    public BaseTextureHolder setGLUnit(BaseGLUnit GLUnit) {
        this.GLUnit = GLUnit;
        return this;
    }
}
