package com.yaoyi.getlocalvideodemo;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yaoyi.getlocalvideodemo.my.adapter.MyCursorRecyclerViewAdapter;
import com.yaoyi.getlocalvideodemo.my.util.MyPermissionRequestUtil;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    /**/
    private static final String PROJECT_VIDEO = MediaStore.MediaColumns._ID;
    /**/
    private MyCursorRecyclerViewAdapter videoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_video_list_layout);


        init();
    }

    /**
     * Init.
     */
    private void init() {
        //权限检查
        new MyPermissionRequestUtil(this).externalStorage();

        //
        RecyclerView videoList = findViewById(R.id.video_list);
        videoListAdapter = new MyCursorRecyclerViewAdapter(this,null);
        videoList.setAdapter(videoListAdapter);

        int SPAN_COUNT=2;
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, SPAN_COUNT);//设置RecycleView以网格状展示
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//设置RecycleView以垂直为方向
        videoList.setLayoutManager(mLayoutManager);//

        myLoaderInit();

    }


    private void myLoaderInit() {
        getLoaderManager().initLoader(0, null, this);//？？用Loader来创建异步访问数据库模式，创建的loader Id号

    }


    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;//本地视频uri
        String order = MediaStore.MediaColumns.DATE_ADDED + " DESC";//排序，以视频添加时间为标准
        return new CursorLoader(getApplicationContext(), videoUri,
                new String[]{MediaStore.Video.Media.DATA, PROJECT_VIDEO, MediaStore.Video.Media.DURATION},//_data列，_ID列
                null, null, order);//以添加时间排序cursor
//        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int count=
                data.getCount();
        videoListAdapter.swapCursor(data);
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
