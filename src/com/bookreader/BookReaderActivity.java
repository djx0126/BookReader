package com.bookreader;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.android.opengl.BaseGLSurfaceView;
import com.android.opengl.BaseOpenGLActivity;
import com.bookreader.config.Settings;
import com.bookreader.file.FileHelper;
import com.djx.bookreader.R;

public class BookReaderActivity extends BaseOpenGLActivity {
    TextView text1 = null;

    @Override
    public void initView() {

        setContentView(R.layout.main);

        myGLView = (BaseGLSurfaceView) this.findViewById(R.id.myView1);
        myGLView.createDefaultRenderer();

        // myGLView = new MainView(this);
        // myGLView.createDefaultRenderer();
        // setContentView(myGLView);

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                loadText();
                return true;
            default:
                break;
            }
        }
        Log.d("[BookReaderActivity]", "other key event");
        return super.onKeyUp(keyCode, event);
    }

    private void loadText() {
        text1 = (TextView) this.findViewById(R.id.textView1);
        char[] buffer = new char[Settings.BUFFERSIZE];
        int offset = Settings.OFFSET;
        int charsRead = FileHelper.readFile(this, Settings.FILENAME, buffer, offset);
        String s = "11";
        if (charsRead > 0) {
            s = FileHelper.unicodesToStr(buffer, charsRead);
        }

        Log.d("[BookReaderActivity]", "loadText:" + s);
        text1.setText(s);
    }

}