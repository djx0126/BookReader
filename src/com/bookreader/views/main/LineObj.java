package com.bookreader.views.main;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Paint;

import com.android.object.drawable.BaseDrawableObject;
import com.android.object.drawable.StaticTextObj;
import com.android.opengl.BaseGLSurfaceView;
import com.android.opengl.utils.OpenGLUtils;
import com.bookreader.config.Settings;

public class LineObj extends BaseDrawableObject {
    private static Paint paint = new Paint();
    private String lineStr = null;

    public LineObj(BaseGLSurfaceView pView, String pLineStr) {
        super(pView);
        this.lineStr = pLineStr;
        paint.setAntiAlias(true);
        paint.setTextSize(Settings.FONTSIZE);
    }

    public void makeLine(GL10 gl, String line) {
        // Log.d("[LineObj]", "linewidth=" + String.valueOf(paint.measureText(line)));
        int processed = 0;
        while (processed < line.length()) {

            processed += makeSubLine(gl, line, processed);
        }
    }

    private int makeSubLine(GL10 gl, String line, int startAt) {
        // Log.d("[LineObj]", "makeSubLine:" + line + ", startAt:" + String.valueOf(startAt));
        int processed = 0;
        int i = 1;
        while (startAt + i <= line.length() && paint.measureText(line.substring(startAt, startAt + i)) <= OpenGLUtils.MAXTEXTUREPIXEL) {
            processed = i;
            i++;
        }
        String subLineStr = line.substring(startAt, startAt + processed);

        int subLineX = (int) (0 + paint.measureText(line.substring(0, startAt)));
        StaticTextObj subLine = new StaticTextObj(mView, subLineStr, Settings.FONTSIZE, Settings.typeFace, Settings.colorA, Settings.colorR, Settings.colorG, Settings.colorB);
        subLine.setPos(subLineX, 0);
        childDrawables.add(subLine);
        return processed;
    }

    @Override
    protected void doInitDrawable(GL10 gl) {
        if (lineStr != null) {
            makeLine(gl, lineStr);
        }
    }
}
