package com.android.object.drawable;

import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.android.opengl.BaseGLSurfaceView;

public class LinkedListLayer extends BaseLayer {
    protected List<IDrawable> drawableList;// smaller pri will be out
                                           // first
    protected List<IDrawable> addList;
    protected List<IDrawable> removeList;
    protected Boolean toClear = false;

    public LinkedListLayer(BaseGLSurfaceView pView) {
        super(pView);
        drawableList = new LinkedList<IDrawable>();
        addList = new LinkedList<IDrawable>();
        removeList = new LinkedList<IDrawable>();
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
        Log.d("[PriorityLayer]", "initDrawable");
        updateQueue();
        for (IDrawable drawable : drawableList) {
            drawable.initDrawable(gl);
        }
        activate();
    }

}
