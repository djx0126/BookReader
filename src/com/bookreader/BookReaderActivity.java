package com.bookreader;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.opengl.BaseGLSurfaceView;
import com.android.opengl.BaseOpenGLActivity;
import com.bookreader.config.Settings;
import com.djx.bookreader.R;

public class BookReaderActivity extends BaseOpenGLActivity {
    private static final int MENU_ADD_FAVOR = 1;
    private static final int MENU_FAVOR_LIST = 2;
    private static final int MENU_SETTINGS = 3;
    private static final int MENU_FONT_UP = 4;
    private static final int MENU_FONT_DOWN = 5;

    TextView text1 = null;

    @Override
    public void initView() {

        setMainView();

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {

        }
        // Log.d("[BookReaderActivity]", "other key event");
        return super.onKeyUp(keyCode, event);
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
            this.setMainView();
            break;
        case MENU_FONT_DOWN:
            if (Settings.FONTSIZE > Settings.FONTSIZEMIN) {
                Settings.FONTSIZE -= Settings.FONTSIZESTEP;
                Settings.LINEHEIGHT = (int) (Settings.FONTSIZE * 1.2);
            }
            this.setMainView();
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setMainView() {
        setContentView(R.layout.main);
        myGLView = (BaseGLSurfaceView) this.findViewById(R.id.myView1);
        myGLView.createDefaultRenderer();
        text1 = (TextView) this.findViewById(R.id.textView1);
        // myGLView = new MainView(this);
        // myGLView.createDefaultRenderer();
        // setContentView(myGLView);
    }

}