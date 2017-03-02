package com.bwei.muchxrecycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.bwei.muchxrecycleview.adapter.MyRecyclerAdapter;
import com.bwei.muchxrecycleview.bean.Bean;
import com.bwei.muchxrecycleview.bean.Content;
import com.bwei.muchxrecycleview.bean.Items;
import com.bwei.muchxrecycleview.okhttp.OkHttpUtil;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public String url1 = "http://i.dxy.cn/snsapi/home/feeds/list/all?sid=4df0360f-2a20-4198-beb8-4dc5660c4f08&u=zhetianyishou&s=10&mc=0000000049029dcaffffffff99d603a9&token=TGT-13165-buaw5fHpqLlefw9bSOB0oF41fobaV4rMZmK-50&hardName=iToolsAVM_T0008098S&ac=4124c5f1-2029-4fda-b06f-a87ac5ad8d11&bv=2013&vc=6.0.6&tid=c25e673d-e82a-4e46-bd4e-c1e86d497126&vs=4.4.4&ref_tid=54720e1a-7eed-4993-9f51-3d760f3d0b2e";
    private XRecyclerView xrecyclerview;
    private MyRecyclerAdapter myRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        xrecyclerview = (XRecyclerView) findViewById(R.id.xrecyclerview);
        //网络请求
        OkHttpUtil okHttpUtil = new OkHttpUtil();
        okHttpUtil.getJson(url1,new HttpData());



    }
    class HttpData extends OkHttpUtil.HttpCallBack{


        @Override
        public void onSusscess(String data) {

            Gson gson = new Gson();
            Bean bean = gson.fromJson(data, Bean.class);
            ArrayList<Items> items = bean.items;
            ArrayList<Content> list = new ArrayList<>();
            for (int i=0; i<items.size();i++){
                Gson gson2 = new Gson();
                Content content = gson2.fromJson(items.get(i).content, Content.class);
                list.add(content);
            }
            getData(items,list);

        }
    }

    private void getData(ArrayList<Items> items, ArrayList<Content> list) {
        // 创建一个线性布局管理器

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // 设置布局管理器

        xrecyclerview.setLayoutManager(layoutManager);
        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener(){


            @Override
            public void onRefresh() {
                myRecyclerAdapter.notifyDataSetChanged();
                xrecyclerview.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                myRecyclerAdapter.notifyDataSetChanged();
                xrecyclerview.loadMoreComplete();
            }
        });
        myRecyclerAdapter = new MyRecyclerAdapter(MainActivity.this,items,list);
        xrecyclerview.setAdapter(myRecyclerAdapter);
    }

}
