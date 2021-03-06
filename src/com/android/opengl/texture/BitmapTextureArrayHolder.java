package com.android.opengl.texture;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.opengl.utils.BitmapUtils;

public class BitmapTextureArrayHolder extends BaseTextureHolder {
    public int index = 0;
    private final List<Bitmap> bitmapList = new ArrayList<Bitmap>();
    private final List<BitmapTextureHolder> textureHolders = new ArrayList<BitmapTextureHolder>();

    /**
     * @param context
     * @param resourceId
     *            like R.drawable.icon
     * @param nInRow
     *            the number of sub pics in a row
     * @param length
     *            how many sub pics in all (in all rows)
     */
    public BitmapTextureArrayHolder(GL10 gl, Context context, int resourceId, int nInRow, int length) {
        super(gl);
        bitmapList.addAll(BitmapUtils.splitBitmap(BitmapUtils.createFromResource(context, resourceId), nInRow, length));
        for (Bitmap bitmap : bitmapList) {
            textureHolders.add(new BitmapTextureHolder(gl, bitmap));
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
    public void draw(GL10 gl) {
        if (index >= 0 && index < textureHolders.size()) {
            item(index).draw(gl);
        }
    }

    @Override
    public void unLoadTexture(GL10 gl) {
        for (BitmapTextureHolder holder : textureHolders) {
            holder.unLoadTexture(gl);
        }
        super.unLoadTexture(gl);
    }

}
