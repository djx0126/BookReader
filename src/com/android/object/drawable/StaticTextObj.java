package com.android.object.drawable;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.android.opengl.BaseGLSurfaceView;
import com.android.opengl.texture.text.StringTextureHolder;

public class StaticTextObj extends BaseDrawableObject {
    protected String text = null;
    protected String tmpText = null;
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

    @Override
    public float getWidth() {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(fontSize);
        paint.setTypeface(typeFace);
        // Log.d("[StaticTextObj]", "getWidth=" + String.valueOf(paint.measureText(text)));
        return paint.measureText(text);

    }

    @Override
    public float getHeight() {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(fontSize);
        paint.setTypeface(typeFace);
        Rect rect = new Rect();
        paint.getTextBounds("hygpbdaq", 0, 8, rect);

        // Log.d("[StaticTextObj]", "getHeight=" + String.valueOf(rect.height()));

        return rect.height();

    }

    public void setText(String newText) {
        if (!text.equals(newText)) {
            tmpText = newText;
        }
    }

    @Override
    protected void onDraw(GL10 gl) {
        if (texture != null && tmpText != null && !tmpText.equals(text)) {
            ((StringTextureHolder) texture).setText(tmpText).updateBitmap(gl);
        }
        super.onDraw(gl);
    }

}
