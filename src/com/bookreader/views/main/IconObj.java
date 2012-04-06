package com.bookreader.views.main;

import com.android.object.drawable.BaseDrawableObject;
import com.android.opengl.BaseRenderer;
import com.android.opengl.texture.BitmapTextureHolder;
import com.android.opengl.utils.BaseGLUnit;
import com.djx.bookreader.R;

public class IconObj extends BaseDrawableObject {
    BitmapTextureHolder pic1 = null;

    public IconObj(BaseRenderer pRenderer) {
        super(pRenderer);
    }

    public IconObj(BaseRenderer pRenderer, float pWidth, float pHeight) {
        this(pRenderer);
        this.width = pWidth;
        this.height = pHeight;
    }

    @Override
    protected void onDraw() {
        pic1.draw(posX, posY, width, height);
    }

    @Override
    protected void initDrawable() {
        pic1 = new BitmapTextureHolder(this.mRenderer, this.mRenderer.myView.myContext, R.drawable.icon);
        pic1.setGLUnit(BaseGLUnit.NORMALSHORT);
        this.posX = 50;
        this.posY = 100;
    }
}
