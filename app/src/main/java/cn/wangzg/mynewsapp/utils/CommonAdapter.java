package cn.wangzg.mynewsapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Time: 2019/2/1
 * Author: wangzhiguo
 * Description: 功能描述
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected ArrayList<T> mDatas;
    protected final int mItemLayoutId;
    public CommonAdapter(Context context, ArrayList<T> mDatas, int itemLayoutId)
    {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;
        mItemLayoutId = itemLayoutId;
    }
    @Override
    public int getCount() {
        return mDatas==null?0:mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();

    }

    public abstract void convert(ViewHolder helper, T item);

    private ViewHolder getViewHolder(int position, View convertView,
                                     ViewGroup parent)
    {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }

    public ArrayList<T> getmDatas() {
        return mDatas;
    }

    public void setmDatas(ArrayList<T> mDatas) {
        this.mDatas = mDatas;
    }
    public void addmDatas(ArrayList<T> mDatas) {
        if(this.mDatas==null) {
            this.mDatas = mDatas;
        }else {
            this.mDatas.addAll(mDatas);
        }
    }
}
