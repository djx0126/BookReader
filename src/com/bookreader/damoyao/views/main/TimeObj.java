package com.bookreader.damoyao.views.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.microedition.khronos.opengles.GL10;

import com.android.object.drawable.StaticTextObj;
import com.android.opengl.BaseGLSurfaceView;

public class TimeObj extends StaticTextObj {
    private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    public TimeObj(BaseGLSurfaceView pView, int pFontSize) {
        super(pView, "", pFontSize);
    }

    @Override
    protected void onDraw(GL10 gl) {
        this.setText(sdf.format(new Date()));
        super.onDraw(gl);
    }
}
