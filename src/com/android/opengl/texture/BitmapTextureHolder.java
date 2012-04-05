package com.android.opengl.texture;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.opengl.BaseRenderer;
import com.android.opengl.utils.BitmapUtils;

public class BitmapTextureHolder extends BaseTextureHolder {
    protected Bitmap bitmap = null;

    public BitmapTextureHolder(BaseRenderer pRenderer, Context context, int resourceId) {
        super(pRenderer);
        bitmap = BitmapUtils.create2NBitmapFromResource(context, resourceId);
        bindTexture(bitmap);
    }

    public BitmapTextureHolder(BaseRenderer pRenderer, Bitmap pBitmap) {
        super(pRenderer);
        bitmap = pBitmap;
        bindTexture(bitmap);
    }

    @Override
    public void draw() {
        myRenderer.gl.glEnable(GL10.GL_BLEND);
        myRenderer.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        myRenderer.gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
        GLUnit.drawUnit(myRenderer.gl);
        myRenderer.gl.glDisable(GL10.GL_BLEND);
    }

}
