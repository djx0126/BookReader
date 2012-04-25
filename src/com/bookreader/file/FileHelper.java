package com.bookreader.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

import com.bookreader.config.Settings;

public class FileHelper {

    private FileHelper() {
    }

    public static String getPreview(Context pContext, String assestFileName, int offset) {
        int BUFFERSIZE = 15;
        char[] buffer = new char[BUFFERSIZE];

        int charsRead = FileHelper.readFile(pContext, assestFileName, buffer, offset);
        String src = "";
        if (charsRead > 0) {
            src = FileHelper.unicodesToStr(buffer, charsRead);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < src.length() - 1; i++) {
            if (!src.substring(i, i + 1).equals("\n") && !src.substring(i, i + 1).equals("\r")) {
                sb.append(src.substring(i, i + 1));
            }
        }
        return sb.toString();
    }

    public static int readFile(Context pContext, String assestFileName, char[] buffer, int offset) {
        int charsRead = -1;
        InputStream is = null;
        try {
            is = pContext.getAssets().open(assestFileName);
            // Log.d("is avail", String.valueOf(is.available()) + " offset is " + String.valueOf(offset));
            InputStreamReader isr = new InputStreamReader(is, Settings.CODESET);
            isr.skip(offset);
            // Log.d("is avail after skip", String.valueOf(is.available()));
            charsRead = isr.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return charsRead;
    }

    public static float getPercentage(Context pContext, String assestFileName, int offset) {
        float percentage = 0.0f;
        InputStream is = null;
        try {
            is = pContext.getAssets().open(assestFileName);
            int all = is.available();
            // Log.d("is avail", String.valueOf(is.available()) + " offset is " + String.valueOf(offset));
            InputStreamReader isr = new InputStreamReader(is, Settings.CODESET);
            isr.skip(offset);
            int now = is.available();
            percentage = 100 * ((float) (all - now)) / all;
            Log.d("Percentage after skip", String.format("%.3f", percentage));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return percentage;
    }

    public static String unicodesToStr(char[] buffer, int charsToRead) {
        String result = "";
        if (charsToRead >= buffer.length) {
            result = unicodesToStr(buffer);
        } else {
            StringBuffer strTmp = new StringBuffer();
            for (int i = 0; i < charsToRead; i++) {
                strTmp.append(buffer[i]);
            }
            result = strTmp.toString();
        }
        return result;
    }

    public static String unicodesToStr(char[] buffer) {
        StringBuffer strTmp = new StringBuffer();
        strTmp.append(buffer);
        return strTmp.toString();
    }

    public static String unicodeToStr(int c) {
        return Character.toString((char) c);
    }

    public static String getCodeSet(Context mContext, String assestFileName) {
        InputStream inputStream = null;
        String code = "";
        code = "gb2312";
        try {
            inputStream = mContext.getAssets().open(Settings.FILENAME);
            byte[] head = new byte[3];
            inputStream.read(head);

            if (head[0] == -1 && head[1] == -2) {
                code = "UTF-16";
            }
            if (head[0] == -2 && head[1] == -1) {
                code = "Unicode";
            }
            if (head[0] == -17 && head[1] == -69 && head[2] == -65) {
                code = "UTF-8";
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return code;
    }
}
