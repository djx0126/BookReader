package com.android.opengl.texture;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.opengl.BaseRenderer;
import com.android.opengl.utils.BitmapUtils;

public class BitmapTextureArrayHolder extends BaseTextureHolder {
    public int index = 0;
    private final List<Bitmap> bitmapList = new ArrayList<Bitmap>();
    private final List<BitmapTextureHolder> textureHolders = new ArrayList<BitmapTextureHolder>();

    /**
     * @param pRenderer
     * @param context
     * @param resourceId
     *            like R.drawable.icon
     * @param nInRow
     *            the number of sub pics in a row
     * @param length
     *            how many sub pics in all (in all rows)
     */
    public BitmapTextureArrayHolder(BaseRenderer pRenderer, Context context, int resourceId, int nInRow, int length) {
        super(pRenderer);
        bitmapList.addAll(BitmapUtils.splitBitmap(BitmapUtils.createFromResource(context, resourceId), nInRow, length));
        for (Bitmap bitmap : bitmapList) {
            textureHolders.add(new BitmapTextureHolder(pRenderer, bitmap));
        }
    }

    public BitmapTextureHolder item(int i) {
        BitmapTextureHolder holder = null;
        if (i >= 0 && i < textureHolders.size()) {
            holder = textureHolders.get(i);
        }
        return holder;
    }

    @Override
    public void draw() {
        if (index >= 0 && index < textureHolders.size()) {
            item(index).draw();
        }
    }
}
