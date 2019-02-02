package cn.wangzg.mynewsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

import cn.wangzg.mynewsapp.utils.HttpUtil;
import cn.wangzg.mynewsapp.utils.JsonUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private String address = "http://api.jisuapi.com/news/get?channel=头条&start=0&num=10&appkey=8c9dc97cca7a0f30";
    private ArrayList<Title> mDatas = null;
    private ListView listView = null;
    private TitleAdapter adapter = null;
    private View footer;
    private boolean loadFinishFlag;
    private int startIndex = 0;
    //private int endIndex = 0;
    private final int pageSize = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.mylist);
        footer = getLayoutInflater().inflate(R.layout.footer_layout, null);
        listView.addFooterView(footer);
        adapter = new TitleAdapter(this,R.layout.list_view_item,mDatas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = new Intent(MainActivity.this, ContentActivity.class);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Title title = mDatas.get(position);
                intent.putExtra("title","社会新闻");
                intent.putExtra("uri",title.getUri());
                startActivity(intent);
            }
        });

        loadFinishFlag = true;
        //startIndex = 0;
        //endIndex = pageSize;
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //获取屏幕最后Item的ID
                int lastVisibleItem = listView.getLastVisiblePosition();
                if (lastVisibleItem + 1 == totalItemCount) {
                    if (loadFinishFlag) {
                        //标志位，防止多次加载
                        loadFinishFlag = false;
                        footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
                        final String requestUrl = getAddress("头条", startIndex, pageSize);
                        System.out.println(requestUrl);
                        //开线程加载数据
//                        new Thread() {
//                            @Override
//                            public void run() {
//                                try {
//                                    String sRet = HttpUtil.sendGet(requestUrl);
//                                    mDatas = JsonUtil.getNewsList(sRet);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                                startIndex += pageSize;
//                                //endIndex += pageSize;
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        adapter.addmDatas(mDatas);
//                                        adapter.notifyDataSetChanged();
//                                        footer.findViewById(R.id.load_layout).setVisibility(View.INVISIBLE);
//                                        loadFinishFlag = true;
//                                    }
//                                });
//                            }
//                        }.start();

                        HttpUtil.sendOkHttpRequest(requestUrl, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                //mDatas = JsonUtil.getNewsList(response.body().string());
                                //使用Gson解析完成该功能
                                mDatas = JsonUtil.getNewsListByGson(response.body().string());
                                startIndex += pageSize;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.addmDatas(mDatas);
                                        adapter.notifyDataSetChanged();
                                        footer.findViewById(R.id.load_layout).setVisibility(View.INVISIBLE);
                                        loadFinishFlag = true;
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });
    }

    private String getAddress(String channel,int start,int pageSize){
        System.out.println("------------start-------------------");
        String address = "http://api.jisuapi.com/news/get?channel=${channel}&start=${start}&num=${pageSize}&appkey=8c9dc97cca7a0f30";
        return address.replace("${channel}",channel)
                .replace("${start}",start+"")
                .replace("${pageSize}",pageSize+"");

    }
}
