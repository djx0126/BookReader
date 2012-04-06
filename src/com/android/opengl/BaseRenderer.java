package com.android.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.util.Log;

import com.android.object.drawable.IDrawable;
import com.android.utils.FPS;

public class BaseRenderer implements GLSurfaceView.Renderer {
    public BaseGLSurfaceView mView = null;
    public GL10 gl;
    public static final float Z = -1f;
    public static final float TARGETFPS = 60f;
    // public int viewWidth = 0;
    // public int viewHeight = 0;
    public int logicWidth = 0;
    public int logicHeight = 0;
    public boolean isInitiated = false;
    protected IDrawable myDrawable;

    protected final FPS myFPS = new FPS();

    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        if (myDrawable != null) {
            myDrawable.draw(gl);
        }
        myFPS.tick(TARGETFPS);
    }

    public void setGLSurfaceView(BaseGLSurfaceView pView) {
        mView = pView;
    }

    /**
     * @param pDrawable
     *            the IDrawable to be draw the IDrawable should be initialized.
     *            if the IDrawable is set before the renderer is Created, the
     *            IDrawable can be initialize at the end of creating function.
     */
    public void setDrawable(IDrawable pDrawable) {
        myDrawable = pDrawable;
    }

    public void onSurfaceChanged(GL10 gl, int pWidth, int pHeight) {
        Log.d("[MyRenderer]", "onSurfaceChanged");
        Log.d("[MyRenderer]", "view width=" + String.valueOf(pWidth));
        Log.d("[MyRenderer]", "view height=" + String.valueOf(pHeight));

        if (logicWidth == 0) {
            logicWidth = pWidth;
        }
        if (logicHeight == 0) {
            logicHeight = pHeight;
        }

        Log.d("[MyRenderer]", "logic width=" + String.valueOf(logicWidth));
        Log.d("[MyRenderer]", "logic height=" + String.valueOf(logicHeight));

        mView.viewWidth = pWidth;
        mView.viewHeight = pHeight;

        gl.glViewport(0, 0, logicWidth, logicHeight);

        // make adjustments for screen ratio
        // float ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION); // set matrix to projection mode
        gl.glLoadIdentity(); // reset the matrix to its default state
        // set leftdown(0,0) rightup(viewWidth,viewHeight)
        gl.glFrustumf(-logicWidth / 2, logicWidth / 2, -logicHeight / 2, logicHeight / 2, 1f, 3f); // apply
        // the
        // projection
        // matrix

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        // AfterSurfaceChanged();
    }

    protected void AfterSurfaceChanged() {
        // TODO Auto-generated method stub
        if (mView != null) {
            mView.viewWidth = logicWidth;
            mView.viewHeight = logicHeight;
        }
    }

    public void onSurfaceCreated(GL10 pGl, EGLConfig config) {
        Log.d("[MyRenderer]", "onSurfaceCreated:" + Thread.currentThread().toString() + "/"
                + Thread.currentThread().getId());
        gl = pGl;

        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

        gl.glEnable(GL10.GL_LINE_SMOOTH);
        gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
        gl.glShadeModel(GL10.GL_SMOOTH);

        gl.glEnable(GL10.GL_TEXTURE_2D); // ///////////////////////////////////////
                                         // Enable 2D Textrue
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        if (myDrawable != null) {
            myDrawable.initDrawable(gl);
        }

        isInitiated = true;
        Log.d("[MyRenderer]", "after onSurfaceCreated");

    }

    public BaseRenderer(BaseGLSurfaceView pView) {
        Log.d("[MyRenderer]", "Constructor");
        mView = pView;
    }

    public BaseRenderer(BaseGLSurfaceView pView, int pLogicWidth, int pLogicHeight) {
        Log.d("[MyRenderer]", "Constructor");
        mView = pView;
        logicWidth = pLogicWidth;
        logicHeight = pLogicHeight;
    }

    public void loadIdentity() {
        gl.glLoadIdentity();
        gl.glTranslatef(-logicWidth / 2, -logicHeight / 2, 0f);
    }

}
