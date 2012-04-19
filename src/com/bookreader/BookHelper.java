package com.bookreader;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.bookreader.FavoritesDB.Favor;
import com.bookreader.config.Settings;

public class BookHelper {
    private Context mContext = null;
    private static final String PREF = "CurrentOffset";
    private static final String OFFSET_TAG = "OFFSET";
    private static final String NEXT_OFFSET_TAG = "NEXTOFFSET";
    private static final String PRE_OFFSET_TAG = "PREOFFSET";

    private static final String DBNAME = "Favorites";
    private static final int DBVERSION = 3;
    private static FavoritesDB favorDB;

    public BookHelper(Context pContext) {
        mContext = pContext;
        favorDB = new FavoritesDB(pContext, DBNAME, null, DBVERSION);
    }

    public void loadCurrentPage() {
        SharedPreferences settings = mContext.getSharedPreferences(PREF, 0);
        Settings.OFFSET = settings.getInt(OFFSET_TAG, 0);
        Settings.NEXTPAGEOFFSET = settings.getInt(NEXT_OFFSET_TAG, 0);
        Settings.PREPAGEOFFSET = settings.getInt(PRE_OFFSET_TAG, 0);

        initFavorDB();

    }

    public void saveCurrentPage() {
        SharedPreferences settings = mContext.getSharedPreferences(PREF, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(OFFSET_TAG, Settings.OFFSET);
        editor.putInt(NEXT_OFFSET_TAG, Settings.NEXTPAGEOFFSET);
        editor.putInt(PRE_OFFSET_TAG, Settings.PREPAGEOFFSET);

        // Commit the edits!
        editor.commit();

        closeFavorDB();
    }

    public List<Favor> getFavorList() {
        List<Favor> favorList = favorDB.getFavoriteList();
        return favorList;
    }

    private void initFavorDB() {
        favorDB.createTable();
    }

    private void closeFavorDB() {
        favorDB.close();
    }

    public void saveFavor(int favorOffset, String favorText, String extraInfo) {
        Favor favor = favorDB.new Favor(favorOffset);
        favor.setExtra1(favorText).setExtra2(extraInfo);
        favorDB.saveFavorite(favor);
    }

    public void removeFavor(int favorOffset) {
        favorDB.removeFavorite(favorOffset);
    }
}
