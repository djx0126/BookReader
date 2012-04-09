package com.android.object.drawable;

import javax.microedition.khronos.opengles.GL10;

public interface IDrawable {

    // public boolean isInitiated();

    public boolean isActived();

    // public void activate();

    public void deactiveDrawable(GL10 gl);

    public void activeDrawable(GL10 gl);

    public void draw(GL10 gl);

    // public void putTexture(BaseTextureHolder texture);
}
