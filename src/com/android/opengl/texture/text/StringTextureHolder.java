package com.android.opengl.texture.text;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Typeface;

import com.android.opengl.texture.BaseTextureHolder;
import com.android.opengl.utils.BaseGLUnit;

/**
 * @author Dave the holder holds all the printable string's texture and buffer
 */
public class StringTextureHolder extends BaseTextureHolder {

    private int bitmapWitdh;
    private int bitmapHeight;

    private String text = "";
    private int size = 10;
    private Typeface typeface = Typeface.DEFAULT;

    private float scaleH = 1.0f;
    private float scaleW = 1.0f;

    private int colorA = 255;
    private int colorR = 255;
    private int colorG = 255;
    private int colorB = 255;

    public StringTextureHolder(GL10 gl, final String text, final int size) {
        this(gl, text, size, Typeface.DEFAULT);
    }

    public StringTextureHolder(GL10 gl, final String text, final int size, final Typeface typeface) {
        this(gl, text, size, Typeface.DEFAULT, 255, 255, 255, 255);
    }

    public StringTextureHolder(GL10 gl, final String text, final int size, final Typeface typeface, final int alpha, final int colorR, final int colorG, final int colorB) {
        super(gl);
        if (text == null || text.length() == 0) {
            this.text = "";
        } else {
            this.text = text;
        }
        this.size = size;
        this.typeface = typeface;
        this.colorA = alpha;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;

        updateBitmap(gl);
    }

    public StringTextureHolder setText(final String newText) {
        if (newText == null || text == null) {
            text = "";
        } else {
            if (!newText.equals(text)) {
                text = newText;
            }
        }
        return this;
    }

    public StringTextureHolder setARGB(int a, int r, int g, int b) {
        this.colorA = a;
        this.colorR = r;
        this.colorG = g;
        this.colorB = b;
        return this;
    }

    public void updateBitmap(GL10 gl) {
        Bitmap bitmap = StringBitmapFactory.createBitmap(text, 0, text.length(), size, typeface, colorA, colorR, colorG, colorB);
        bitmapWitdh = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        // scaleH = bitmapHeight/(float)size;
        // scaleW = bitmapWitdh /(float)size; // =
        // scaleH*bitmapWitdh/bitmapHeight;
        scaleH = bitmapHeight;
        scaleW = bitmapWitdh;

        bindTexture(gl, bitmap);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        bitmap = null;
    }

    @Override
    public void draw(GL10 gl) {
        drawText(gl);
    }

    private void drawText(GL10 gl) {
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glScalef(scaleW, scaleH, 1.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
        BaseGLUnit.NORMALSHORT.drawUnit(gl);
        gl.glDisable(GL10.GL_BLEND);
    }

}