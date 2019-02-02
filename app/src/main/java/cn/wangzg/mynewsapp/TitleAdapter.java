package cn.wangzg.mynewsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;



public class TitleAdapter extends BaseAdapter {
    private int resourceId;
    private Context context;
    private ArrayList<Title> mDatas;
    public TitleAdapter( Context context, int resource, ArrayList<Title> mDatas) {
        this.context = context;
        resourceId = resource;
        this.mDatas = mDatas;
    }
    //设置，用于该分类第一次加载后调用
    public void setmDatas(ArrayList<Title> mDatas) {
        this.mDatas = mDatas;
    }
    //添加，用于加载更多。。。
    public void addmDatas(ArrayList<Title> mDatas) {
        if(this.mDatas==null) {
            this.mDatas = mDatas;
        }else {
            this.mDatas.addAll(mDatas);
        }
    }
    @Override
    public int getCount() {
        return mDatas==null?0:mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Title title = mDatas.get(position);
        View view;
        ViewHolder viewHolder;
        /**
         * 缓存布局和实例，优化 listView
         */
        if (convertView == null){
            view = LayoutInflater.from(context).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.titleText = view.findViewById(R.id.title_text);
            viewHolder.titlePic =  view.findViewById(R.id.title_pic);
            viewHolder.titleSrc = view.findViewById(R.id.descr_text);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        Glide.with(context).load(title.getImageUrl()).into(viewHolder.titlePic);
        viewHolder.titleText.setText(title.getTitle());
        viewHolder.titleSrc.setText(title.getSrc());

        return view;

    }

    public class ViewHolder{
        TextView titleText;
        TextView titleSrc;
        ImageView titlePic;
    }
}
