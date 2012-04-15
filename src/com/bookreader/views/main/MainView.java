package com.bookreader.views.main;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.android.object.drawable.BaseDrawableObject;
import com.android.opengl.BaseGLSurfaceView;
import com.bookreader.BookReaderActivity;
import com.bookreader.config.Settings;
import com.djx.bookreader.R;

public class MainView extends BaseGLSurfaceView {

    private static final int MENU_ADD_FAVOR = 1;
    private static final int MENU_FAVOR_LIST = 2;
    private static final int MENU_SETTINGS = 3;
    private static final int MENU_FONT_UP = 4;
    private static final int MENU_FONT_DOWN = 5;

    BaseDrawableObject mWall = null;
    BaseDrawableObject mFPS = null;
    BaseDrawableObject currentPage = null;
    BaseDrawableObject nextPage = null;
    BaseDrawableObject prePage = null;

    public MainView(Context context) {
        this(context, null);
    }

    public MainView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public BaseGLSurfaceView initView() {
        Log.d("[MainView]", "initView");

        // mFPS = new FPSObj(this);
        // layerMgr.insertDrawable(mFPS);

        currentPage = new PageObj(this, viewWidth, viewHeight);
        layerMgr.insertDrawable(currentPage);

        return this;
    }

    @Override
    public void afterRendererCreated() {
        super.afterRendererCreated();
        this.setBkgColor(Settings.bkgColorA, Settings.bkgColorR, Settings.bkgColorG, Settings.bkgColorB);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            if (x > 0 && x < viewWidth && y > 0 && y < viewHeight) {
                Log.d("[MainView]", "x=" + String.valueOf(x) + ", y=" + String.valueOf(y));
                if (x > viewWidth * 2 / 3) {
                    nextPage();
                } else if (x < viewWidth / 3) {
                    prePage();
                }
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private void nextPage() {
        layerMgr.removeDrawable(currentPage);
        currentPage = new PageObj(this, viewWidth, viewHeight);
        layerMgr.insertDrawable(currentPage);
        Settings.PREPAGEOFFSET = Settings.OFFSET;
        Settings.OFFSET = Settings.NEXTPAGEOFFSET;

    }

    private void prePage() {
        layerMgr.removeDrawable(currentPage);
        Settings.NEXTPAGEOFFSET = Settings.OFFSET;
        Settings.OFFSET = Settings.PREPAGEOFFSET;
        currentPage = new PageObj(this, viewWidth, viewHeight);
        layerMgr.insertDrawable(currentPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, MENU_ADD_FAVOR, MENU_ADD_FAVOR, R.string.MENU_ADD_FAVOR);
        menu.add(0, MENU_FAVOR_LIST, MENU_FAVOR_LIST, R.string.MENU_FAVOR_LIST);
        // menu.add(0, MENU_SETTINGS, MENU_SETTINGS, R.string.MENU_SETTINGS);
        if (Settings.FONTSIZE <= Settings.FONTSIZEMAX) {
            menu.add(0, MENU_FONT_UP, MENU_FONT_UP, R.string.MENU_FONT_UP);
        }
        if (Settings.FONTSIZE >= Settings.FONTSIZEMIN) {
            menu.add(0, MENU_FONT_DOWN, MENU_FONT_DOWN, R.string.MENU_FONT_DOWN);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MENU_ADD_FAVOR:
            break;
        case MENU_FAVOR_LIST:
            break;
        case MENU_SETTINGS:
            // setSetView();
            break;
        case MENU_FONT_UP:
            if (Settings.FONTSIZE <= Settings.FONTSIZEMAX) {
                Settings.FONTSIZE += Settings.FONTSIZESTEP;
                Settings.LINEHEIGHT = (int) (Settings.FONTSIZE * 1.2);
            }
            ((BookReaderActivity) mContext).setMainView();
            break;
        case MENU_FONT_DOWN:
            if (Settings.FONTSIZE > Settings.FONTSIZEMIN) {
                Settings.FONTSIZE -= Settings.FONTSIZESTEP;
                Settings.LINEHEIGHT = (int) (Settings.FONTSIZE * 1.2);
            }
            ((BookReaderActivity) mContext).setMainView();
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
