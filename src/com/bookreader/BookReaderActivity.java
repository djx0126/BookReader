package com.bookreader;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.opengl.BaseGLSurfaceView;
import com.android.opengl.BaseOpenGLActivity;
import com.djx.bookreader.R;

public class BookReaderActivity extends BaseOpenGLActivity {
    public BookHelper bookHelper = null;
    TextView text1 = null;

    private ListView listView;
    private Cursor mCursor;
    private static final int STATUS_MAIN = 0;
    private static final int STATUS_FAVOR_LIST = 1;
    private int status = STATUS_MAIN;

    @Override
    public void initView() {

        setMainView();

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // Log.d("[BookReaderActivity]", "other key event");
        if (status == STATUS_FAVOR_LIST) {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    setMainView();
                    break;
                default:
                    break;
                }
            }
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }

    }

    public void setMainView() {
        setContentView(R.layout.main);
        myGLView = (BaseGLSurfaceView) this.findViewById(R.id.myView1);
        myGLView.createDefaultRenderer();
        // myGLView = new MainView(this);
        // myGLView.createDefaultRenderer();
        // setContentView(myGLView);

        status = STATUS_MAIN;
    }

    public void setFavorView() {
        changeListView(bookHelper.getFavorCursor());
        status = STATUS_FAVOR_LIST;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookHelper = new BookHelper(this);
    }

    @Override
    protected void onPause() {
        bookHelper.saveCurrentPage();
        super.onPause();
    }

    @Override
    protected void onResume() {
        bookHelper.loadCurrentPage();
        super.onResume();

    }

    private void changeListView(Cursor cur) {

        if (cur != null) {
            if (mCursor != null) {
                mCursor.deactivate();
                mCursor.close();
            }

            mCursor = cur;
            this.setContentView(R.layout.favorlist);
            listView = (ListView) this.findViewById(R.id.listView1);

            String[] from = { FavoritesDB.COL_OFFSET, FavoritesDB.COL_OFFSET };
            int[] to = { R.id.listItemText1, R.id.listItemText2 };

            ListAdapter adapter = new FavorCursorAdapter(this, R.layout.listitem, mCursor, from, to);

            listView.setAdapter(adapter);

            Log.d("listView getCount", String.valueOf(listView.getTouchables().size()));

            listView.setOnItemClickListener(new OnItemClickListener() {

                public void onItemClick(AdapterView<?> adapter, View itemView, int position, long id) {
                    Log.d("onItemClick", "position = " + String.valueOf(position));
                    Log.d("onItemClick", "id = " + String.valueOf(id));
                    TextView tv1 = (TextView) itemView.findViewById(R.id.listItemText1);
                    TextView tv2 = (TextView) itemView.findViewById(R.id.listItemText2);
                    Log.d("onItemClick", "text1 = " + tv1.getText());
                    Log.d("onItemClick", "text2 = " + tv2.getText());

                }
            });
        }

    }

}