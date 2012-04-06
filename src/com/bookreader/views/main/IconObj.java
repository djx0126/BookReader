package com.bookreader.views.main;

import javax.microedition.khronos.opengles.GL10;

import com.android.object.drawable.BaseDrawableObject;
import com.android.opengl.BaseGLSurfaceView;
import com.android.opengl.texture.BitmapTextureHolder;
import com.android.opengl.utils.BaseGLUnit;
import com.djx.bookreader.R;

public class IconObj extends BaseDrawableObject {
    BitmapTextureHolder pic1 = null;

    public IconObj(BaseGLSurfaceView pView) {
        super(pView);
    }

    public IconObj(BaseGLSurfaceView pView, float pWidth, float pHeight) {
        this(pView);
        this.width = pWidth;
        this.height = pHeight;
    }

    @Override
    protected void onDraw(GL10 gl) {
        pic1.draw(gl, posX, posY, width, height);
    }

    @Override
    public void initDrawable(GL10 gl) {
        super.initDrawable(gl);
        pic1 = new BitmapTextureHolder(gl, mView.myContext, R.drawable.icon);
        pic1.setGLUnit(BaseGLUnit.NORMALSHORT);
        this.posX = 50;
        this.posY = 100;
    }
}
