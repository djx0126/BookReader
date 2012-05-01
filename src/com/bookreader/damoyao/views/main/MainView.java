package com.bookreader.damoyao.views.main;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.android.object.drawable.BaseDrawableObject;
import com.android.object.drawable.StaticTextObj;
import com.android.opengl.BaseGLSurfaceView;
import com.bookreader.damoyao.BookReaderActivity;
import com.bookreader.damoyao.FavoritesDB;
import com.bookreader.damoyao.config.Settings;
import com.bookreader.damoyao.file.FileHelper;
import com.djx.bookreader.R;

public class MainView extends BaseGLSurfaceView {

    private static final int MENU_ADD_FAVOR = 1;
    private static final int MENU_FAVOR_LIST = 2;
    private static final int MENU_SETTINGS = 3;
    private static final int MENU_FONT_UP = 4;
    private static final int MENU_FONT_DOWN = 5;

    private final int padLeft = 10;
    private final int padTop = 10;
    private final int padButtom = 10;

    private float touchStartX = 0.0f;
    private float touchStartY = 0.0f;
    private boolean touchMovedFlag = false;

    // BaseDrawableObject mFPS = null;
    BaseDrawableObject bookName = null;
    BaseDrawableObject nowTime = null;
    BaseDrawableObject percentage = null;
    BaseDrawableObject currentPage = null;
    BaseDrawableObject nextPage = null;
    BaseDrawableObject prePage = null;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            FavoritesDB.toastInserResult(mContext, msg.what);
            super.handleMessage(msg);
        }

    };

    public MainView(Context context) {
        this(context, null);
    }

    public MainView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public BaseGLSurfaceView initView() {
        Log.d("[MainView]", "initView");
        layerMgr.clearDrawable();
        this.setBkgColor(Settings.bkgColorA, Settings.bkgColorR, Settings.bkgColorG, Settings.bkgColorB);

        bookName = new StaticTextObj(this, mContext.getResources().getString(R.string.BOOK_NAME), Settings.FONTSIZE * 3 / 4);
        bookName.setPos((int) ((this.viewWidth - bookName.getWidth()) / 2), 1);
        layerMgr.insertDrawable(bookName);

        nowTime = new TimeObj(this, Settings.FONTSIZE * 3 / 4);
        nowTime.setPos(padLeft, 1);
        layerMgr.insertDrawable(nowTime);

        percentage = new StaticTextObj(this, String.format("%.1f", FileHelper.getPercentage(mContext, Settings.FILENAME, Settings.OFFSET)) + "%", Settings.FONTSIZE * 3 / 4);
        percentage.setPos((int) (this.viewWidth - padLeft - percentage.getWidth()), 1);
        layerMgr.insertDrawable(percentage);

        createCurrentPage();

        return this;
    }

    private void nextPage() {
        layerMgr.removeDrawable(currentPage);
        Settings.PREPAGEOFFSET = Settings.OFFSET;
        Settings.OFFSET = Settings.NEXTPAGEOFFSET;
        createCurrentPage();

    }

    private void prePage() {
        layerMgr.removeDrawable(currentPage);
        Settings.NEXTPAGEOFFSET = Settings.OFFSET;
        Settings.OFFSET = Settings.PREPAGEOFFSET;

        createCurrentPage();
    }

    private void createCurrentPage() {
        currentPage = new PageObj(this, viewWidth - 2 * padLeft, viewHeight - 2 * (padTop + padButtom), Settings.OFFSET);
        currentPage.setPos(padLeft, padButtom);

        layerMgr.insertDrawable(currentPage);
        ((StaticTextObj) percentage).setText(String.format("%.1f", FileHelper.getPercentage(mContext, Settings.FILENAME, Settings.OFFSET)) + "%");
        percentage.setPos((int) (this.viewWidth - padLeft - percentage.getWidth()), 1);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (x > 0 && x < viewWidth && y > 0 && y < viewHeight) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Log.d("MotionEvent.ACTION_DOWN", "posX=" + String.valueOf(x) + " posY=" + String.valueOf(y));
                touchStartX = x;
                touchStartY = y;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                // Log.d("MotionEvent.ACTION_UP", "posX=" + String.valueOf(x) + " posY=" + String.valueOf(y));
                if (touchMovedFlag) {
                    touchMovedFlag = false;
                    if (x > viewWidth * 3 / 5 && touchStartX < viewWidth * 2 / 5) {
                        prePage();
                    } else if (x < viewWidth * 2 / 3 && touchStartX > viewWidth * 3 / 5) {
                        nextPage();
                    }
                } else {
                    if (x > viewWidth * 2 / 3) {
                        nextPage();
                    } else if (x < viewWidth / 3) {
                        prePage();
                    }
                }
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                // Log.d("MotionEvent.ACTION_MOVE", "posX=" + String.valueOf(x) + " posY=" + String.valueOf(y));
                touchMovedFlag = true;
            }
            return true;
        }

        return super.onTouchEvent(event);
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
            new Thread(new Runnable() {
                public void run() {
                    String previewStr = FileHelper.getPreview(mContext, Settings.FILENAME, Settings.OFFSET);
                    Message msg = new Message();
                    String percentage = String.format("%.1f", FileHelper.getPercentage(mContext, Settings.FILENAME, Settings.OFFSET));
                    msg.what = ((BookReaderActivity) mContext).bookHelper.saveFavor(Settings.OFFSET, previewStr, percentage + "%");
                    handler.sendMessage(msg);
                }
            }).start();

            break;
        case MENU_FAVOR_LIST:
            ((BookReaderActivity) mContext).setFavorView();
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
