package com.android.object.drawable;

import java.util.PriorityQueue;

import javax.microedition.khronos.opengles.GL10;

import com.android.opengl.BaseGLSurfaceView;

public class PriorityLayer extends BaseLayer {
    protected PriorityQueue<IDrawable> drawableList;// smaller pri will be out
                                                    // first
    protected PriorityQueue<IDrawable> addList;
    protected PriorityQueue<IDrawable> removeList;
    protected Boolean toClear = false;

    public PriorityLayer(BaseGLSurfaceView pView) {
        super(pView);
        drawableList = new PriorityQueue<IDrawable>();
        addList = new PriorityQueue<IDrawable>();
        removeList = new PriorityQueue<IDrawable>();
    }

    @Override
    public void insertDrawable(IDrawable drawableObj) {
        if (!drawableList.contains(drawableObj) && !addList.contains(drawableObj)) {
            addList.add(drawableObj);
        }
    }

    @Override
    public void removeDrawable(IDrawable drawableObj) {
        if (drawableList.contains(drawableObj) && !removeList.contains(drawableObj)) {
            removeList.add(drawableObj);
        }
    }

    @Override
    public void clearDrawable() {
        synchronized (toClear) {
            toClear = true;
        }
    }

    protected void updateQueue() {
        synchronized (removeList) {
            if (!removeList.isEmpty()) {
                drawableList.removeAll(removeList);
                removeList.clear();
            }
        }
        synchronized (addList) {
            if (!addList.isEmpty()) {
                drawableList.addAll(addList);
                addList.clear();
            }
        }
        synchronized (toClear) {
            if (toClear) {
                drawableList.clear();
                addList.clear();
                removeList.clear();
                toClear = false;
            }
        }
    }

    @Override
    public void draw(GL10 gl) {
        for (IDrawable drawable : drawableList) {
            drawable.draw(gl);
        }
        updateQueue();
    }

    @Override
    public void initDrawable(GL10 gl) {
        for (IDrawable drawable : drawableList) {
            drawable.initDrawable(gl);
        }
        activate();
    }

}
