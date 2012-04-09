package com.android.object.drawable;

import javax.microedition.khronos.opengles.GL10;

public abstract class AbstractDrawable implements IDrawable {
    public final static int STATE_NOT_INIT = 0;
    public final static int STATE_ACTIVE = 1;
    private int state = STATE_NOT_INIT;

    protected final void activate() {
        state = STATE_ACTIVE;
    }

    protected final void deactive() {
        state = STATE_NOT_INIT;
    }

    public final boolean isActived() {
        return state == STATE_ACTIVE;
    }

    public abstract void activeDrawable(GL10 gl);

    public abstract void deactiveDrawable(GL10 gl);

    public abstract void draw(GL10 gl);
}
