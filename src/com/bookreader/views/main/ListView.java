package com.bookreader.views.main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.android.object.drawable.BaseDrawableObject;
import com.android.object.drawable.StaticTextObj;
import com.android.opengl.BaseGLSurfaceView;

public class ListView extends BaseGLSurfaceView {
    private static final int FONT_SIZE = 32;
    private static final int LINE_HEIGHT = 36;
    private static final int FONT_COLOR_A = 255;
    private static final int FONT_COLOR_R = 0;
    private static final int FONT_COLOR_G = 0;
    private static final int FONT_COLOR_B = 0;
    private static final int PAD = 10;
    private float offset = 0;
    // private final List<String> strList;
    private List<String> preStrList;
    private List<BaseDrawableObject> listItems = null;

    public ListView(Context pContext) {
        this(pContext, null);
    }

    public ListView(Context pContext, AttributeSet pAttributeSet) {
        super(pContext, pAttributeSet);
        offset = 0;
        // strList = new LinkedList<String>();
        preStrList = new LinkedList<String>();
        listItems = new ArrayList<BaseDrawableObject>();
    }

    @Override
    protected BaseGLSurfaceView initView() {
        comsumePreStrList();
        return super.initView();
    }

    private void comsumePreStrList() {
        Log.d("[ListView]", "comsumePreStrList with " + String.valueOf(initialized()));
        for (String str : preStrList) {
            addListItem(str);
        }
        preStrList.clear();
        preStrList = null;
    }

    private void removeListItem(int index) {
        if (index < listItems.size() && index > 0) {
            // strList.remove(index);
            BaseDrawableObject listItem = listItems.get(index);
            listItems.remove(index);
            layerMgr.removeDrawable(listItem);
            updateItemPos();
        }
    }

    public void addListItem(String itemText) {
        if (initialized()) {
            // if (strList != null) {
            // strList.add(itemText);
            BaseDrawableObject listItem = new StaticTextObj(this, itemText, FONT_SIZE, FONT_COLOR_A, FONT_COLOR_R, FONT_COLOR_G, FONT_COLOR_B);

            // Log.d("[ListView]", "addListItem with " + itemText);
            listItems.add(listItem);
            layerMgr.insertDrawable(listItem);
            updateItemPos();
            // }
        } else {
            preStrList.add(itemText);
        }
    }

    private void updateItemPos() {
        for (BaseDrawableObject listItemObj : listItems) {
            int index = listItems.indexOf(listItemObj);
            int x = PAD;
            int y = viewHeight - PAD - (index + 1) * LINE_HEIGHT;
            // Log.d("[ListView]", "addListItem with (" + String.valueOf(x) + "," + String.valueOf(y) + ")");
            listItemObj.setPos(x, y);
        }
    }

    public class ListItem {
        private BaseDrawableObject drawObj;
        private String mainText = "";
        private String infoText = "";
        private Object extraData = null;
        private int index = 0;

        public ListItem() {
            this("");
        }

        public ListItem(String itemText) {
            mainText = itemText;
        }

        public ListItem setIndex(int i) {
            index = i;
            return this;
        }

        public ListItem setMainText(String pMainText) {
            this.mainText = pMainText;
            return this;
        }

        public ListItem setInfoText(String pInfoText) {
            this.infoText = pInfoText;
            return this;
        }

        public ListItem setExtraData(Object pData) {
            this.extraData = pData;
            return this;
        }
    }
}
