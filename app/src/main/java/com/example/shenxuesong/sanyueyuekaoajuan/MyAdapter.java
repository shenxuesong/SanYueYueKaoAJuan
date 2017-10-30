package com.example.shenxuesong.sanyueyuekaoajuan;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by shenxuesong on 2017/9/20.
 */

public class MyAdapter extends BaseAdapter {
   private  List<JavaBean.DataBean.ComicsBean>  channel;
    private Context context;

    public MyAdapter(List<JavaBean.DataBean.ComicsBean> channel, Context context) {
        this.channel = channel;
        this.context = context;
    }

    @Override
    public int getCount() {
        return channel.size();
    }

    @Override
    public Object getItem(int position) {
        return channel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1=null;
        if(convertView==null){
            
            convertView=View.inflate(context,R.layout.item,null);
            holder1=new ViewHolder1();
            holder1.image1=(ImageView)convertView.findViewById(R.id.image1) ;

            holder1.tv=(TextView)convertView.findViewById(R.id.tv1);
            convertView.setTag(holder1);
            
        }else {
            holder1=(ViewHolder1)convertView.getTag();
        }
        String channel_me = channel.get(position).getTitle();
        String channel_more = channel.get(position).getTopic().getUser().getNickname();
        String cover_image_url = channel.get(position).getCover_image_url();



        holder1.tv.setText(channel_me+"\n"+channel_more);
        ImageLoader.getInstance().displayImage(cover_image_url,holder1.image1);

        return convertView;
    }
    class ViewHolder1{
        ImageView image1;

        TextView tv;
    }
}
