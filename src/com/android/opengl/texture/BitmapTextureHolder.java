package com.android.opengl.texture;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.opengl.utils.BitmapUtils;

public class BitmapTextureHolder extends BaseTextureHolder {
    protected Bitmap bitmap = null;

    public BitmapTextureHolder(GL10 gl, Context context, int resourceId) {
        super(gl);
        bitmap = BitmapUtils.create2NBitmapFromResource(context, resourceId);
        bindTexture(gl, bitmap);
    }

    public BitmapTextureHolder(GL10 gl, Bitmap pBitmap) {
        super(gl);
        bitmap = pBitmap;
        bindTexture(gl, bitmap);
    }

    @Override
    public void draw(GL10 gl) {
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
        GLUnit.drawUnit(gl);
        gl.glDisable(GL10.GL_BLEND);
    }

}
