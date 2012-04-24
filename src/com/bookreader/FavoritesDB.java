package com.bookreader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoritesDB extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "favorites";
    public static final String COL_ID = "_id";
    public static final String COL_OFFSET = "offset";
    public static final String COL_DATE = "add_date";
    public static final String COL_EXTRA_INFO_1 = "extra_info_1";// to save book text
    public static final String COL_EXTRA_INFO_2 = "extra_info_2";// to save percentage and reserved
    public static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_OFFSET + " INTEGER, " + COL_DATE
            + " TIMESTAMP, " + COL_EXTRA_INFO_1 + " TEXT, " + COL_EXTRA_INFO_2 + " TEXT);";

    public FavoritesDB(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTable() {
        try {
            this.getWritableDatabase().execSQL(CREATE_TABLE_SQL);
        } catch (Exception e) {

        }
    }

    public Cursor getFavoriteCur() {
        Cursor cur = this.getReadableDatabase().query(TABLE_NAME, new String[] { COL_ID, COL_OFFSET, COL_DATE, COL_EXTRA_INFO_1, COL_EXTRA_INFO_2 }, null, null, null, null, null);
        return cur;
    }

    public List<Favor> getFavoriteList() {
        List<Favor> resultList = new LinkedList<Favor>();
        try {
            Cursor cur = this.getReadableDatabase().query(TABLE_NAME, null, null, null, null, null, null);
            if (cur != null && cur.getCount() > 0) {
                cur.moveToFirst();
                while (!cur.isAfterLast()) {
                    Favor favor = new Favor(cur.getInt(cur.getColumnIndex(COL_OFFSET)));
                    favor.setDate(cur.getString(cur.getColumnIndex(COL_DATE)));
                    favor.setExtra1(cur.getString(cur.getColumnIndex(COL_EXTRA_INFO_1)));
                    favor.setExtra2(cur.getString(cur.getColumnIndex(COL_EXTRA_INFO_2)));
                    resultList.add(favor);
                    cur.moveToNext();
                }
            }
            cur.close();
        } catch (Exception e) {
            //
        }
        return resultList;
    }

    public void saveFavorite(Favor pFavor) {
        if (!containFavorite(pFavor)) {
            try {
                ContentValues values = new ContentValues();
                values.put(COL_OFFSET, pFavor.offset);
                values.put(COL_DATE, pFavor.date);
                values.put(COL_EXTRA_INFO_1, pFavor.extra1);
                values.put(COL_EXTRA_INFO_2, pFavor.extra2);
                this.getWritableDatabase().insert(TABLE_NAME, null, values);
            } catch (Exception e) {

            }
        } else {
            try {
                ContentValues values = new ContentValues();
                values.put(COL_OFFSET, pFavor.offset);
                values.put(COL_DATE, pFavor.date);
                values.put(COL_EXTRA_INFO_1, pFavor.extra1);
                values.put(COL_EXTRA_INFO_2, pFavor.extra2);
                this.getWritableDatabase().update(TABLE_NAME, values, COL_OFFSET + "==" + String.valueOf(pFavor.offset), null);
            } catch (Exception e) {

            }
        }
    }

    public void removeFavorite(Favor pFavor) {
        removeFavorite(pFavor.offset);
    }

    public void removeFavorite(int pOffset) {
        try {
            this.getWritableDatabase().delete(TABLE_NAME, COL_OFFSET + "==" + String.valueOf(pOffset), null);
        } catch (Exception e) {

        }
    }

    public boolean containFavorite(Favor pFavor) {
        return containFavorite(pFavor.offset);
    }

    public boolean containFavorite(int pOffset) {
        boolean result = false;
        try {
            Cursor cur = this.getReadableDatabase().query(TABLE_NAME, null, COL_OFFSET + "==" + String.valueOf(pOffset), null, null, null, null);
            if (cur != null) {
                if (cur.getCount() > 0) {
                    result = true;
                }
            }
            cur.close();
        } catch (Exception e) {
            //
        }

        return result;
    }

    public static class Favor {
        public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
        private final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        private int offset = 0;
        private String date = "";
        private String extra1 = "";
        private String extra2 = "";

        public Favor(int pOffset) {
            this.offset = pOffset;
            setDate();
        }

        public Favor setOffset(int pOffset) {
            this.offset = pOffset;
            return this;
        }

        public int getOffset() {
            return this.offset;
        }

        public Favor setDate(String dateStr) {
            this.date = dateStr;
            return this;
        }

        private Favor setDate() {
            this.date = sdf.format(new Date());
            return this;
        }

        public String getDate() {
            return this.date;
        }

        public Favor setExtra1(String str) {
            this.extra1 = str;
            return this;
        }

        public String getExtra1() {
            return extra1;
        }

        public Favor setExtra2(String str) {
            this.extra2 = str;
            return this;
        }

        public String getExtra2() {
            return extra2;
        }

    }

}
