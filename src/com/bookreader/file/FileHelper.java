package com.bookreader.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

import com.bookreader.config.Settings;

public class FileHelper {

    private FileHelper() {
    }

    public static int readFile(Context pContext, String assestFileName, char[] buffer, int offset) {
        int charsRead = -1;
        InputStream is = null;
        try {
            is = pContext.getAssets().open(assestFileName);
            InputStreamReader isr = new InputStreamReader(is, Settings.CODESET);
            isr.skip(offset);
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
