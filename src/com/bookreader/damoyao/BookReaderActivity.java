package com.bookreader.damoyao;

import net.youmi.android.AdManager;
import net.youmi.android.AdView;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.opengl.BaseGLSurfaceView;
import com.android.opengl.BaseOpenGLActivity;
import com.bookreader.damoyao.FavorCursorAdapter.ViewHolder;
import com.bookreader.damoyao.config.Settings;
import com.djx.bookreader.R;

public class BookReaderActivity extends BaseOpenGLActivity {
    public BookHelper bookHelper = null;

    private LinearLayout mainLayout;
    private ListView listView;
    private Cursor mCursor;

    private static final int STATUS_MAIN = 0;
    private static final int STATUS_FAVOR_LIST = 1;
    private int status = STATUS_MAIN;

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
        Log.d("[BookReaderActivity]", "setMainView");
        if (mCursor != null) {
            mCursor.deactivate();
            mCursor.close();
        }
        setContentView(R.layout.main);
        myGLView = (BaseGLSurfaceView) this.findViewById(R.id.myView1);
        myGLView.createDefaultRenderer();

        mainLayout = (LinearLayout) this.findViewById(R.id.linearLayout1);

        AdView adView = new AdView(this);
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

        mainLayout.addView(adView, params);
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
        super.onResume();
        bookHelper.loadCurrentPage();
        switch (status) {
        case STATUS_MAIN:
            setMainView();
            break;
        case STATUS_FAVOR_LIST:
            setFavorView();
            break;
        default:
            break;
        }
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
                    ViewHolder holder;
                    ImageButton rmItemBtn = (ImageButton) itemView.findViewById(R.id.rmItemBtn1);
                    holder = (ViewHolder) rmItemBtn.getTag();
                    Settings.OFFSET = holder.offset;
                    setMainView();

                }
            });
        }

    }

    @Override
    public void initView() {
        AdManager.init(this, "a53789bcadec330e", "e00c0ec70751a15d", 30, false);
        super.initView();
    }
}