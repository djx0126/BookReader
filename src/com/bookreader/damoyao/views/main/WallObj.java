package com.bookreader.damoyao.views.main;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.android.object.drawable.BaseDrawableObject;
import com.android.opengl.BaseGLSurfaceView;
import com.android.opengl.texture.BitmapTextureHolder;
import com.djx.bookreader.R;

public class WallObj extends BaseDrawableObject {

    public WallObj(BaseGLSurfaceView pView, float pWidth, float pHeight) {
        super(pView, pWidth, pHeight);
    }

    @Override
    protected void doInitDrawable(GL10 gl) {
        Log.d("[WallObj]", "initDrawable");
        posX = 50;
        posY = 100;
        BitmapTextureHolder pic1 = new BitmapTextureHolder(gl, mView.mContext, R.drawable.wall);
        putTexture(pic1);
    }
}
