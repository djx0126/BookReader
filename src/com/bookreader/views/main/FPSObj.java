package com.bookreader.views.main;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Typeface;

import com.android.object.drawable.BaseDrawableObject;
import com.android.opengl.BaseGLSurfaceView;
import com.android.opengl.texture.text.StringTextureHolder;
import com.android.utils.FPS;

public class FPSObj extends BaseDrawableObject {
    FPS fps = new FPS();

    public FPSObj(BaseGLSurfaceView pView) {
        super(pView);
    }

    @Override
    protected void onDraw(GL10 gl) {
        fps.tick();
        ((StringTextureHolder) texture).setText(fps.toString()).updateBitmap(gl);
        texture.draw(gl, posX, posY, width, height);
    }

    @Override
    protected void doInitDrawable(GL10 gl) {
        // Log.d("[FPSObj]", "doInitDrawable");
        posX = 0;
        posY = 0;
        StringTextureHolder fpsString = new StringTextureHolder(gl, "9.826", 50, Typeface.DEFAULT, 255, 0, 0, 200);
        putTexture(fpsString);
    }

}
