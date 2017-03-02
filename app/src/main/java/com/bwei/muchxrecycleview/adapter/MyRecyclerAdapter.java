package com.bwei.muchxrecycleview.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.muchxrecycleview.R;
import com.bwei.muchxrecycleview.bean.Content;
import com.bwei.muchxrecycleview.bean.Items;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * 作    者 ： 文欢
 * 时    间 ： 2017/3/2.
 * 描    述 ：
 * 修改时间 ：
 */

public class MyRecyclerAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<Items> items;
    private ArrayList<Content> list;
    private int ONLY_TEXT = 1;
    private int RIGHT_PIC = 2;

    public MyRecyclerAdapter(Context context, ArrayList<Items> items, ArrayList<Content> list) {
        this.context = context;
        this.items = items;
        this.list = list;
    }

    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 1:
                MiddleViewHolder middleViewHolder=new MiddleViewHolder
                        (View.inflate(context,R.layout.itemimg,null));
                return middleViewHolder;
            case 2:
                TextViewHolder textViewHolder=new TextViewHolder
                        (View.inflate(context,R.layout.item,null));
                return textViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(XRecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case 1:
                MiddleViewHolder middleViewHolder= (MiddleViewHolder) holder;
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.init(new ImageLoaderConfiguration.Builder(context).build());
                imageLoader.displayImage("http://img.dxycdn.com/avatars/120/"+items.get(position).infoAvatar, middleViewHolder.infoAvatar);

                middleViewHolder.subject.setText(list.get(position).subject);
                imageLoader.displayImage("http://res.dxycdn.com/upload/"+list.get(position).url, middleViewHolder.url);
                break;
            case 2:
                TextViewHolder textViewHolder= (TextViewHolder) holder;
                ImageLoader imageLoader2 = ImageLoader.getInstance();
                imageLoader2.init(new ImageLoaderConfiguration.Builder(context).build());
                imageLoader2.displayImage("http://img.dxycdn.com/avatars/120/"+items.get(position).infoAvatar, textViewHolder.infoAvatar);
                textViewHolder.subject.setText(list.get(position).subject);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    @Override
    public int getItemViewType(int position) {

        if (list.get(position).canVote) {
            if (!TextUtils.isEmpty(list.get(position).url)) {

                return ONLY_TEXT;
            }
        } else
        {

            return RIGHT_PIC;
        }

        return 2;
    }
    class MiddleViewHolder extends XRecyclerView.ViewHolder{

        ImageView infoAvatar;
        TextView subject;
        ImageView url;


        public MiddleViewHolder(View itemView) {
            super(itemView);
            infoAvatar= (ImageView) itemView.findViewById(R.id.infoAvatar);
            subject= (TextView) itemView.findViewById(R.id.subject);
            url= (ImageView) itemView.findViewById(R.id.url);
        }
    }
    class TextViewHolder extends XRecyclerView.ViewHolder{
        ImageView infoAvatar;
        TextView subject;
        public TextViewHolder(View itemView) {
            super(itemView);
            infoAvatar= (ImageView) itemView.findViewById(R.id.infoAvatar);
            subject= (TextView) itemView.findViewById(R.id.subject);

        }
    }
}
