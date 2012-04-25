package com.bookreader;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.bookreader.config.Settings;
import com.djx.bookreader.R;

public class FavorCursorAdapter extends SimpleCursorAdapter {
    private Context mContext = null;
    private Cursor mCursor = null;

    public FavorCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.mContext = context;
        this.mCursor = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Log.d("MyAdapter", "Position:" + position + "---" + String.valueOf(System.currentTimeMillis()));
        ViewHolder holder;

        if (convertView == null) {
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem, null);
            holder = new ViewHolder();
            holder.text1 = (TextView) convertView.findViewById(R.id.listItemText1);
            convertView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    ViewHolder holder = (ViewHolder) v.getTag();
                    int offset = holder.offset;
                    Settings.OFFSET = offset;
                    ((BookReaderActivity) mContext).setMainView();
                }

            });
            holder.text2 = (TextView) convertView.findViewById(R.id.listItemText2);
            holder.rmItemBtn = (ImageButton) convertView.findViewById(R.id.rmItemBtn1);
            holder.rmItemBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ViewHolder holder = (ViewHolder) v.getTag();
                    int position = holder.id;
                    int offset = holder.offset;
                    Log.d("rmItemBtn", "position=" + String.valueOf(position) + "  offset=" + String.valueOf(offset));
                    ((BookReaderActivity) mContext).bookHelper.removeFavorByOffset(offset);
                    FavorCursorAdapter.this.getCursor().requery();
                }
            });
            holder.rmItemBtn.setTag(holder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        mCursor.moveToPosition(position);

        holder.id = mCursor.getInt(mCursor.getColumnIndex(FavoritesDB.COL_ID));
        holder.offset = mCursor.getInt(mCursor.getColumnIndex(FavoritesDB.COL_OFFSET));
        holder.text1.setText(mCursor.getString(mCursor.getColumnIndex(FavoritesDB.COL_EXTRA_INFO_1)));
        holder.text2.setText(mCursor.getString(mCursor.getColumnIndex(FavoritesDB.COL_DATE)) + "   " + mCursor.getString(mCursor.getColumnIndex(FavoritesDB.COL_EXTRA_INFO_2)));

        return convertView;

        // return super.getView(position, convertView, parent);
    }

    static class ViewHolder {
        int id;
        int offset;
        TextView text1;
        TextView text2;
        ImageButton rmItemBtn;
    }

}
