package com.android.object.drawable;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Typeface;

import com.android.opengl.BaseGLSurfaceView;
import com.android.opengl.texture.text.StringTextureHolder;

public class StaticTextObj extends BaseDrawableObject {
    private String text = null;
    private int fontSize = 20;
    private Typeface typeFace = Typeface.DEFAULT;
    private int colorA = 255;
    private int colorR = 0;
    private int colorG = 0;
    private int colorB = 0;

    public StaticTextObj(BaseGLSurfaceView pView, String pText, int pFontSize) {
        this(pView, pText, pFontSize, Typeface.DEFAULT, 255, 0, 0, 0);
    }

    public StaticTextObj(BaseGLSurfaceView pView, String pText, int pFontSize, int pA, int pR, int pG, int pB) {
        this(pView, pText, pFontSize, Typeface.DEFAULT, pA, pR, pG, pB);
    }

    public StaticTextObj(BaseGLSurfaceView pView, String pText, int pFontSize, Typeface pTypeface, int pA, int pR, int pG, int pB) {
        super(pView);
        this.text = pText;
        this.fontSize = pFontSize;
        this.typeFace = pTypeface;
        this.colorA = pA;
        this.colorR = pR;
        this.colorG = pG;
        this.colorB = pB;
    }

    @Override
    protected void doInitDrawable(GL10 gl) {
        if (text != null) {
            texture = new StringTextureHolder(gl, text, fontSize, typeFace, colorA, colorR, colorG, colorB);
        }
    }

}
