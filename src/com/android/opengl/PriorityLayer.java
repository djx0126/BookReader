package com.android.opengl;

import java.util.PriorityQueue;

import com.android.object.drawable.IDrawable;

public class PriorityLayer extends BaseLayer {
    protected PriorityQueue<IDrawable> drawableList;// smaller pri will be out
                                                    // first
    protected PriorityQueue<IDrawable> addList;
    protected Boolean toClear = false;

    public PriorityLayer(BaseGLSurfaceView pView) {
        super(pView);
        drawableList = new PriorityQueue<IDrawable>();
        addList = new PriorityQueue<IDrawable>();
    }

    @Override
    public void insertDrawable(IDrawable drawableObj) {
        addList.add(drawableObj);
    }

    @Override
    public void clearDrawable() {
        synchronized (toClear) {
            toClear = true;
        }
    }

    protected void updateQueue() {
        synchronized (addList) {
            if (!addList.isEmpty()) {
                drawableList.addAll(addList);
                addList.clear();
            }
        }
        synchronized (toClear) {
            drawableList.clear();
            toClear = false;
        }
    }

    @Override
    public void draw() {
        for (IDrawable drawable : drawableList) {
            drawable.draw();
        }
        updateQueue();
    }
}
