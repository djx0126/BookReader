package com.bookreader.damoyao.views.main;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Paint;

import com.android.object.drawable.BaseDrawableObject;
import com.android.opengl.BaseGLSurfaceView;
import com.bookreader.damoyao.config.Settings;
import com.bookreader.damoyao.file.FileHelper;

public class PageObj extends BaseDrawableObject {
    private static Paint paint = new Paint();
    private int pageOffset = 0;

    public PageObj(BaseGLSurfaceView pView, float pWidth, float pHeight) {
        super(pView, pWidth, pHeight);

    }

    public PageObj(BaseGLSurfaceView pView, float pWidth, float pHeight, int pageOffset) {
        super(pView, pWidth, pHeight);
        this.pageOffset = pageOffset;
    }

    @Override
    protected void doInitDrawable(GL10 gl) {
        // Log.d("[PageObj]", "doInitDrawable");
        int nextPageOffset = addLines(gl, pageOffset);
        Settings.NEXTPAGEOFFSET = nextPageOffset;
        toSetPrePage(pageOffset, nextPageOffset);

    }

    private int addLines(GL10 gl, int offset) {
        List<String> pageLines = new ArrayList<String>();
        int nextPageOffset = getPageStr(offset, pageLines);
        int lineNum = 0;
        for (String lineStr : pageLines) {
            int linePosY = (int) (posY + height - (++lineNum) * Settings.LINEHEIGHT);
            LineObj aLine = new LineObj(mView, lineStr);
            aLine.setPos(0, linePosY);
            childDrawables.add(aLine);
        }
        pageLines.clear();
        pageLines = null;

        return nextPageOffset;
    }

    /**
     * @param offset
     *            the offset of this page
     * @param pageLines
     *            the buffer to store the page string lines
     * @return the offset for next page
     */
    private int getPageStr(int offset, List<String> pageLines) {

        char[] buffer = new char[Settings.BUFFERSIZE];

        int charsRead = FileHelper.readFile(mView.mContext, Settings.FILENAME, buffer, offset);
        String src = "";
        if (charsRead > 0) {
            src = FileHelper.unicodesToStr(buffer, charsRead);
        }
        int lineNum = ((int) height) / Settings.LINEHEIGHT;
        int startAt = 0;

        int lineLength = 0;
        for (int i = 0; i < lineNum && startAt + lineLength < src.length(); i++) {
            lineLength = getLineStr(src, startAt, pageLines);
            startAt += lineLength;
        }

        return offset + startAt;
    }

    /**
     * @param src
     *            the whole page string source
     * @param startAt
     *            start for the line
     * @param pageLines
     *            buffer to store the line string
     * @return number of characters read (count '\n', but not store in the line string since the String texture not support it now)
     */
    private int getLineStr(final String src, final int startAt, List<String> pageLines) {
        paint.setAntiAlias(true);
        paint.setTextSize(Settings.FONTSIZE);
        int charsToLine = 0;
        int newLineFlag = 0;
        for (int i = 1; i + startAt < src.length(); i++) {
            if (paint.measureText(src.substring(startAt, startAt + i)) > width) {
                break;
            } else if (src.substring(startAt, startAt + i).endsWith("\n")) {
                newLineFlag = 1;
                break;
            } else {
                charsToLine = i;
            }
        }
        String lineStr = src.substring(startAt, startAt + charsToLine);
        pageLines.add(lineStr);
        if (newLineFlag == 1) {
            charsToLine++;
        }
        return charsToLine;
    }

    private void toSetPrePage(final int offset, final int nextPageOffset) {

        Thread t1 = new Thread(new Runnable() {

            public void run() {
                int prepageOffset = Settings.PREPAGEOFFSET;
                List<String> pageLines = new ArrayList<String>();
                int tryThisPageOffset = getPageStr(prepageOffset, pageLines);
                if (tryThisPageOffset != offset) {
                    Settings.PREPAGEOFFSET = Math.max(0, offset - (nextPageOffset - offset));
                }

            }

        });
        t1.start();
    }
    // private voi addLines(GL10 gl, int offset) {
    // linesHolder = new LinkedList<LineObj>();
    //
    // char[] buffer = new char[Settings.BUFFERSIZE];
    //
    // int charsRead = FileHelper.readFile(mView.mContext, Settings.FILENAME, buffer, offset);
    // String src = "";
    // if (charsRead > 0) {
    // src = FileHelper.unicodesToStr(buffer, charsRead);
    // // Log.d("[PageObj]", "charsRead=" + String.valueOf(charsRead));
    // }
    // int lineNum = ((int) height) / Settings.LINEHEIGHT;
    // int startAt = 0;
    // for (int i = 0; i < lineNum; i++) {
    // startAt += addLine(gl, src, startAt, i);
    // }
    // Settings.NEXTPAGEOFFSET = offset + startAt;
    // }

    // private int addLine(GL10 gl, final String src, final int startAt, final int lineNum) {
    // // Log.d("[PageObj]", "addLine, startAt=" + String.valueOf(startAt));
    //
    // paint.setAntiAlias(true);
    // paint.setTextSize(Settings.FONTSIZE);
    // int charsToLine = 0;
    // int newLineFlag = 0;
    // for (int i = 1; startAt + i < src.length(); i++) {
    // if (paint.measureText(src.substring(startAt, startAt + i)) > width) {
    // break;
    // } else if (src.substring(startAt, startAt + i).endsWith("\n")) {
    // newLineFlag = 1;
    // break;
    // } else {
    // charsToLine = i;
    // }
    // }
    //
    // // Log.d("[PageObj]", "StrWid=" + String.valueOf(paint.measureText(src.substring(startAt, startAt + charsToLine))));
    // // Log.d("[PageObj]", "charsToLine=" + String.valueOf(charsToLine) + ":" + src.substring(startAt, startAt + charsToLine));
    // if (charsToLine > 0) {
    // String line = src.substring(startAt, startAt + charsToLine);
    //
    // int linePosY = (int) (posY + height - (lineNum + 1) * Settings.LINEHEIGHT);
    // LineObj aLine = new LineObj(mView, posX, linePosY);
    // aLine.makeLine(gl, line);
    // linesHolder.add(aLine);
    // }
    //
    // if (newLineFlag == 1) {
    // charsToLine++;
    // }
    // return charsToLine;
    // }
}
