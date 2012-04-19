package com.android.object.drawable;

import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.android.opengl.BaseGLSurfaceView;

public class LinkedListLayer extends BaseLayer {
    protected List<BaseDrawableObject> drawableList;// smaller pri will be out
    // first
    protected List<BaseDrawableObject> addList;
    protected List<BaseDrawableObject> removeList;
    protected Boolean toClear = false;

    protected int offsetX = 0;
    protected int offsetY = 0;
    protected int offsetXtmp = 0;
    protected int offsetYtmp = 0;

    public LinkedListLayer(BaseGLSurfaceView pView) {
        super(pView);
        drawableList = new LinkedList<BaseDrawableObject>();
        addList = new LinkedList<BaseDrawableObject>();
        removeList = new LinkedList<BaseDrawableObject>();
    }

    @Override
    public void insertDrawable(BaseDrawableObject drawableObj) {
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
    public void removeDrawable(BaseDrawableObject drawableObj) {
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
            for (BaseDrawableObject drawableObj : drawableList) {
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
                for (BaseDrawableObject drawableObj : addList) {
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
        updateOffset();
        for (BaseDrawableObject drawable : drawableList) {
            drawable.draw(gl);
        }
        updateQueue(gl);
    }

    @Override
    protected void doInitDrawable(GL10 gl) {
        updateQueue(gl);
    }

    private void updateOffset() {
        if (offsetX != offsetXtmp || offsetY != offsetYtmp) {
            offsetX = offsetXtmp;
            offsetY = offsetYtmp;
            for (BaseDrawableObject drawable : drawableList) {
                drawable.setPos(drawable.posX + this.offsetX, drawable.posY + this.offsetY);
            }
        }

    }

    public void setOffset(int pOffsetX, int pOffsetY) {
        offsetXtmp = pOffsetX;
        offsetYtmp = pOffsetY;
    }

}
