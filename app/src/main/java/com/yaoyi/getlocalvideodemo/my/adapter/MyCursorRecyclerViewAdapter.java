package com.yaoyi.getlocalvideodemo.my.adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yaoyi.getlocalvideodemo.R;

/**
 * Created by c on 13/03/2018.
 *
 * 自定义一个可以加载Cursor的RecycleView.Adapter
 */

public class MyCursorRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private Cursor mCursor;
    public MyCursorRecyclerViewAdapter(Context context,Cursor cursor) {
        super();
        mCursor=cursor;
        mContext=context;
    }



    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView videoThumbnail=new ImageView(mContext);//一个imageView用于展示本地视频的一个简图
        RecyclerView.ViewHolder vh=new RecyclerView.ViewHolder(videoThumbnail) {//创建一个Viewolder对象vh，vh用来传递imageiew
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return vh;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,int position){
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        onBindViewHolder(viewHolder, mCursor);

    }

    public void onBindViewHolder(RecyclerView.ViewHolder ViewHolder, Cursor cursor) {

        String id = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
        Uri uri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
        Glide.with(mContext)
                .load(uri)
                .placeholder(R.mipmap.video_list_init)
                .error(R.mipmap.video_list_init)
                .crossFade()
                .into((ImageView) ViewHolder.itemView);

    }

    @Override
    public int getItemCount() {
        if (  mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }


    public void swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {

        } else {//若有新的Cursor 需要更新
            final Cursor oldCursor = mCursor;
            if (oldCursor != null) {
                oldCursor.close();//关闭oldCursor,
            }
            mCursor = newCursor;
            notifyDataSetChanged();//当adapter发生结构性变化（比如每一item的删除、增加），
            // 调用此函数，会重新加载发生变化后的结构性视图
        }
    }
}
