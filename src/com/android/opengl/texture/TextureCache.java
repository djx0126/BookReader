package com.android.opengl.texture;

import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

public class TextureCache {
    private static final Map<Integer, TextreCahceItem> textures = new HashMap<Integer, TextreCahceItem>();

    private static TextreCahceItem newTexture(GL10 gl) {
        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl.glGenTextures(1, intBuffer);
        TextreCahceItem textureItem = new TextreCahceItem();
        textureItem.active = true;
        textureItem.intBuffer = intBuffer;
        textureItem.key = ++TextreCahceItem.count;
        textures.put(textureItem.key, textureItem);
        return textureItem;
    }

    public static TextreCahceItem getTexture(GL10 gl) {
        TextreCahceItem texture = null;
        for (int i : textures.keySet()) {
            TextreCahceItem item = textures.get(i);
            if (!item.active) {
                texture = item;
            }
        }
        if (texture == null) {
            texture = newTexture(gl);
        }
        return texture;
    }

    public static void returnTexture(int key) {
        textures.get(key).active = false;
    }

    protected static class TextreCahceItem {
        private static int count = 0;
        private IntBuffer intBuffer;
        private boolean active;
        private int key;

        public int getTexture() {
            return intBuffer.get();
        }

        public int getKey() {
            return key;
        }
    }
}
