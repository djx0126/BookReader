package com.android.opengl.texture.text;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.android.opengl.utils.OpenGLUtils;

public class StringBitmapFactory {
    // private static int charWidth;
    private static int stringWidth;
    private static int stringHeight;
    private static int bitmapWidth;
    private static int bitmapHeight;
    private static Paint paint = new Paint();
    private static Rect rect = new Rect();

    private static Canvas canvas = new Canvas();

    public static Bitmap createBitmap(final String s, final int size) {
        return createBitmap(s, size, Typeface.DEFAULT, 255, 255, 255, 255);
    }

    public static Bitmap createBitmap(final String s, final int size, final int alpha, final int colorR,
            final int colorG, final int colorB) {
        return createBitmap(s, size, Typeface.DEFAULT, alpha, colorR, colorG, colorB);
    }

    public static Bitmap createBitmap(final String string, final int size, final Typeface typeFace, final int alpha,
            final int colorR, final int colorG, final int colorB) {
        return createBitmap(string, 0, string.length(), size, typeFace, alpha, colorR, colorG, colorB);
    }

    public static Bitmap createBitmap(final String string, final int start, final int num, final int size,
            final Typeface typeFace, final int alpha, final int colorR, final int colorG, final int colorB) {
        paint.setAntiAlias(true);
        paint.setTextSize(size);
        paint.setTypeface(typeFace);

        paint.getTextBounds("hygpbdaq", 0, 8, rect);
        // charWidth = rect.width() / 8;
        stringHeight = rect.height();
        bitmapHeight = OpenGLUtils.getNext2N(stringHeight);

        paint.getTextBounds(string, 0, num, rect);

        // stringWidth = rect.width();
        stringWidth = (int) Math.ceil(paint.measureText(string.substring(0, num)));
        bitmapWidth = OpenGLUtils.getNext2N(stringWidth);

        Bitmap paintBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Config.ARGB_8888);
        canvas.setBitmap(paintBitmap);
        // paint.setColor(Color.CYAN);
        // paint.setColor(Color.TRANSPARENT);
        // canvas.drawRect(0, 0, bitmapWidth, bitmapHeight, paint);

        paint.setTextAlign(Align.CENTER);
        paint.setARGB(alpha, colorR, colorG, colorB);
        canvas.drawText(string.substring(start, num), stringWidth / 2, bitmapHeight - paint.descent(), paint);

        return paintBitmap;
    }

}
