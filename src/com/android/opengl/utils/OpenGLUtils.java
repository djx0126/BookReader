package com.android.opengl.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class OpenGLUtils {
    public static ShortBuffer initBuffer(short shortArray[]) {
        ByteBuffer vbb;
        vbb = ByteBuffer.allocateDirect(shortArray.length * 2);
        vbb.order(ByteOrder.nativeOrder());
        ShortBuffer buffer = vbb.asShortBuffer();
        buffer.put(shortArray);
        buffer.position(0);
        return buffer;
    }

    public static FloatBuffer initBuffer(float floatArray[]) {
        ByteBuffer vbb;
        vbb = ByteBuffer.allocateDirect(floatArray.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = vbb.asFloatBuffer();
        buffer.put(floatArray);
        buffer.position(0);
        return buffer;
    }

    public static int getNext2N(int n) {
        int i = 2;
        while (i < n && i < 256) {
            i <<= 1;
        }
        return i;
    }
}
