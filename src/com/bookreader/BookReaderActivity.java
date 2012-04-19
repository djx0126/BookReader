package com.bookreader;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.android.opengl.BaseGLSurfaceView;
import com.android.opengl.BaseOpenGLActivity;
import com.bookreader.views.main.ListView;
import com.djx.bookreader.R;

public class BookReaderActivity extends BaseOpenGLActivity {
    public BookHelper bookHelper = null;
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

    public void setFavorView() {
        myGLView = new ListView(this);
        myGLView.createDefaultRenderer();
        ((ListView) myGLView).addListItem("1. ◊“≥");
        ((ListView) myGLView).addListItem("2.≤‚ ‘2");
        ((ListView) myGLView).addListItem("3.≤‚ ‘3");
        ((ListView) myGLView).addListItem("4.≤‚ ‘4");
        setContentView(myGLView);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookHelper = new BookHelper(this);
    }

    @Override
    protected void onPause() {
        bookHelper.saveCurrentPage();
        super.onPause();
    }

    @Override
    protected void onResume() {
        bookHelper.loadCurrentPage();
        super.onResume();

    }
}