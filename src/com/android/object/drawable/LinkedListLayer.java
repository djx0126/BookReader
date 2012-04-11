package com.android.object.drawable;

import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

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
        synchronized (addList) {
            if (!drawableList.contains(drawableObj) && !addList.contains(drawableObj)) {
                // addList.add(drawableObj);
                synchronized (removeList) {
                    if (removeList.contains(drawableObj)) {
                        removeList.remove(drawableObj);
                    } else {
                        addList.add(drawableObj);
                    }
                }
            }
        }
    }

    @Override
    public void removeDrawable(IDrawable drawableObj) {
        synchronized (addList) {
            if (addList.contains(drawableObj)) {
                addList.remove(drawableObj);
            } else {
                synchronized (removeList) {
                    if (drawableList.contains(drawableObj) && !removeList.contains(drawableObj)) {
                        removeList.add(drawableObj);
                    }
                }
            }

        }
    }

    @Override
    public void clearDrawable() {
        synchronized (addList) {
            addList.clear();
        }
        synchronized (removeList) {
            removeList.clear();
        }
        synchronized (toClear) {
            toClear = true;
        }
    }

    protected void updateQueue(GL10 gl) {
        synchronized (toClear) {
            if (toClear) {
                toClear = false;
                drawableList.clear();
            }
        }
        synchronized (removeList) {
            for (IDrawable drawableObj : drawableList) {
                if (!drawableObj.isActived() && !removeList.contains(drawableObj)) {
                    removeList.add(drawableObj);
                }
            }
            if (!removeList.isEmpty()) {
                for (IDrawable drawableObj : removeList) {
                    if (drawableObj.isActived()) {
                        drawableObj.deactiveDrawable(gl);
                    }
                    if (drawableList.contains(drawableObj)) {
                        drawableList.remove(drawableObj);
                    }
                }
                removeList.clear();
            }
        }
        synchronized (addList) {
            if (!addList.isEmpty()) {
                for (IDrawable drawableObj : addList) {
                    if (!drawableObj.isActived()) {
                        drawableObj.activeDrawable(gl);
                    }
                    drawableList.add(drawableObj);
                }
                addList.clear();
            }
        }

    }

    @Override
    public void draw(GL10 gl) {
        for (IDrawable drawable : drawableList) {
            drawable.draw(gl);
        }
        updateQueue(gl);
    }

    @Override
    protected void doInitDrawable(GL10 gl) {
        updateQueue(gl);
    }

}
