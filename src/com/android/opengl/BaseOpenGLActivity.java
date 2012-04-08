package com.android.opengl;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class BaseOpenGLActivity extends Activity {
    protected BaseGLSurfaceView myGLView;
    public BaseGLSurfaceView nextView;

    public static BaseOpenGLActivity context;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Android3DBasicActivity", "onCreate");

        context = this;

        // 去掉标题
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置全屏
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        System.gc();
    }

    public void initView() {

        myGLView = new BaseGLSurfaceView(this);
        myGLView.createDefaultRenderer();
        setView(myGLView);

    }

    public void setView(BaseGLSurfaceView pView) {
        // Log.d("[BaseActivity]", "setView:" +
        // Thread.currentThread().toString()
        // + "/" + Thread.currentThread().getId());
        // Log.d("BaseActivity", "Old View:" + myGLView.mRenderer +
        // "  next view:"
        // + pView.mRenderer);

        this.myGLView = pView;
        System.gc();
        setContentView(myGLView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Android3DBasicActivity", "onPause");

        // de-allocate objects
        // myGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Android3DBasicActivity", "onResume");

        // re-allocate objects;
        // myGLView.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (myGLView != null) {
            return myGLView.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

}