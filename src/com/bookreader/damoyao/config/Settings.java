package com.bookreader.damoyao.config;

import android.graphics.Typeface;

public class Settings {

    public static String FILENAME = "damoyao.txt";
    public static String CODESET = "gb2312";

    public static final int FONTSIZEMAX = 60;
    public static final int FONTSIZEMIN = 12;
    public static final int FONTSIZESTEP = 4;
    public static int FONTSIZE = 28;
    public static int LINEHEIGHT = (int) (FONTSIZE * 1.2);
    public static int BUFFERSIZE = 1024;
    public static int OFFSET = 0;
    public static int NEXTPAGEOFFSET = 100;
    public static int PREPAGEOFFSET = 100;
    public static int colorA = 255;
    public static int colorR = 0;
    public static int colorG = 0;
    public static int colorB = 0;
    public static int bkgColorA = 96;
    public static int bkgColorR = 250;
    public static int bkgColorG = 250;
    public static int bkgColorB = 194;
    public static Typeface typeFace = Typeface.DEFAULT;
}
