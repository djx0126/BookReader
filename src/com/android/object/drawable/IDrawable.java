package com.android.object.drawable;

import javax.microedition.khronos.opengles.GL10;

public interface IDrawable {
    public void initDrawable(GL10 gl);

    public void draw();
}
