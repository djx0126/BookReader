package com.bookreader;

import com.android.opengl.BaseGLSurfaceView;
import com.android.test.BaseOpenGLActivity;
import com.djx.bookreader.R;

public class BookReaderActivity extends BaseOpenGLActivity {

    @Override
    public void initView() {

        setContentView(R.layout.main);

        myGLView = (BaseGLSurfaceView) this.findViewById(R.id.myView1);
        myGLView.createDefaultRenderer();

        // myGLView = new MainView(this);
        // myGLView.createDefaultRenderer();
        // setContentView(myGLView);

    }

}