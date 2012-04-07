package com.bookreader.views.main;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Paint;

import com.android.object.drawable.BaseDrawableObject;
import com.android.opengl.BaseGLSurfaceView;
import com.android.opengl.texture.text.StringTextureHolder;
import com.android.opengl.utils.OpenGLUtils;
import com.bookreader.config.Settings;

public class LineObj extends BaseDrawableObject {
    private List<StringTextureHolder> subLines;
    private List<Integer> subLinesPosX;
    private static Paint paint = new Paint();

    public LineObj(BaseGLSurfaceView pView, int posX, int posY) {
        super(pView);
        // Log.d("[LineObj]", "X=" + String.valueOf(posX) + ",Y=" + String.valueOf(posY));
        this.posX = posX;
        this.posY = posY;
        paint.setAntiAlias(true);
        paint.setTextSize(Settings.FONTSIZE);
    }

    public void makeLine(GL10 gl, String line) {
        // Log.d("[LineObj]", "makeLine:" + line);
        subLines = new ArrayList<StringTextureHolder>();
        subLinesPosX = new ArrayList<Integer>();

        // Log.d("[LineObj]", "linewidth=" + String.valueOf(paint.measureText(line)));
        int processed = 0;
        while (processed < line.length()) {
            processed += makeSubLine(gl, line, processed);
        }
        activate();
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
        StringTextureHolder subLine = new StringTextureHolder(gl, subLineStr, Settings.FONTSIZE, Settings.typeFace, Settings.colorA, Settings.colorR, Settings.colorG,
                Settings.colorB);
        subLines.add(subLine);
        subLinesPosX.add((int) (posX + paint.measureText(line.substring(0, startAt))));
        return processed;
    }

    @Override
    protected void onDraw(GL10 gl) {
        int subLinePosX = this.posX;
        for (int idx = 0; idx < subLines.size(); idx++) {
            StringTextureHolder subline = subLines.get(idx);
            subLinePosX = subLinesPosX.get(idx);
            subline.draw(gl, subLinePosX, posY);
        }
    }
}
