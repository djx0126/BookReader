package com.bookreader;

import android.view.KeyEvent;
import android.widget.TextView;

import com.android.opengl.BaseGLSurfaceView;
import com.android.opengl.BaseOpenGLActivity;
import com.djx.bookreader.R;

public class BookReaderActivity extends BaseOpenGLActivity {

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

    public void setMainView() {
        setContentView(R.layout.main);
        myGLView = (BaseGLSurfaceView) this.findViewById(R.id.myView1);
        myGLView.createDefaultRenderer();
        // myGLView = new MainView(this);
        // myGLView.createDefaultRenderer();
        // setContentView(myGLView);
    }

}