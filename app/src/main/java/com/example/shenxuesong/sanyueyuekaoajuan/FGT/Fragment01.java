package com.example.shenxuesong.sanyueyuekaoajuan.FGT;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shenxuesong.sanyueyuekaoajuan.JavaBean;
import com.example.shenxuesong.sanyueyuekaoajuan.JavaUtils;
import com.example.shenxuesong.sanyueyuekaoajuan.MyAdapter;
import com.example.shenxuesong.sanyueyuekaoajuan.R;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.maxwin.view.XListView;

/**
 * Created by shenxuesong on 2017/9/20.
 */

public class Fragment01 extends Fragment {
    private ViewPager vp;
    private TextView tv;
    private LinearLayout ll;
    private XListView xlv;
    private static  final String stt="http://api.kkmh.com/v1/daily/comic_lists/0?since=0&gender=0&sa_event=" +
            "                        eyJwcm9qZWN0Ijoia3VhaWthbl9hcHAiLCJ0aW1lIjoxNDg3NzQyMjQwNjE1LCJwcm9wZXJ0a" +
            "                        WVzIjp7IkhvbWVwYWdlVGFiTmFtZSI6IueDremXqCIsIlZDb21tdW5pdHlUYWJOYW1lIjoi54Ot" +
            "6ZeoIiwiJG9zX3ZlcnNpb24iOiI0LjQuMiIsIkdlbmRlclR5cGUiOiLlpbPniYgiLCJGcm9tSG9tZXBhZ2VUYWJOYW1lIjoi54Ot6Z" +
            "             eoIiwiJGxpYl92ZXJzaW9uIjoiMS42LjEzIiwiJG5ldHdvcmtfdHlwZSI6IldJRkkiLCIkd2lmaSI6dHJ1ZSwiJG1hbnVmYW" +
            "N0dXJlciI6ImJpZ25veCIsIkZyb21Ib21lcGFnZVVwZGF0ZURhdGUiOjAsIiRzY3JlZW5faGVpZ2h0IjoxMjgwLCJIb21lcGFnZVVwZGF0ZURhdGUiOj" +
            "AsIlByb3BlcnR5RXZlbnQiOiJSZWFkSG9tZVBhZ2UiLCJGaW5kVGFiTmFtZSI6IuaOqOiNkCIsImFidGVzdF9ncm91cCI6MTEsIiRzY3JlZW5fd2lkdGgiOj" +
            "cyMCwiJG9zIjoiQW5kcm9pZCIsIlRyaWdnZXJQYWdlIjoiSG9tZVBhZ2UiLCIkY2FycmllciI6IkNoaW5hIE1vYmlsZSIsIiRtb2RlbCI6IlZQaG9uZSIsI" +
            "iRhcHBfdmVyc2lvbiI6IjMuNi4yIn0sInR5cGUiOiJ0cmFjayIsImRpc3RpbmN0X2lkIjoiQTo2YWRkYzdhZTQ1MjUwMzY1Iiwib3JpZ2luYWxfaWQiOiJ" +
            "BOjZhZGRjN2FlNDUyNTAzNjUiLCJldmVudCI6IlJlYWRIb21lUGFnZSJ9 ";
    private List<View> dolist=new ArrayList<View>();
    private List<String> str=new ArrayList<String>();
    private List<ImageView> imagelist=new ArrayList<ImageView>();
    private int current=0;
    private int old_list=0;
    private  String string;
    private Handler h=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){


                int i=msg.arg1;
                //设置viewpager当前的播放位置
                vp.setCurrentItem(i);
                //设置当前的标题对应的图片
                tv.setText(str.get(i%imagelist.size()));
                //设置圆点当然播放对应的图片的颜色的变化
                dolist.get(old_list).setBackgroundResource(R.drawable.shape1);
                dolist.get(i%imagelist.size()).setBackgroundResource(R.drawable.shape1);
                old_list=i%imagelist.size();
            }

        }
    };
    private List<JavaBean.DataBean.ComicsBean> comics;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment01,container,false);
        vp= (ViewPager) view.findViewById(R.id.vp);
        tv=(TextView)view.findViewById(R.id.tv) ;
        ll=(LinearLayout)view.findViewById(R.id.ll);
        xlv=(XListView)view.findViewById(R.id.xlv);
       //设置开关
        xlv.setPullLoadEnable(true);
        //判断网络是否可用
        boolean info = new JavaUtils().getInfo(getActivity());
        if(info){
            //解析图片
            jiexi();

            //实现上拉 下载
            initData();
        }


       return view;
    }

    private void initData() {
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            //刷新第一幕的数据
            @Override
            public void onRefresh() {
                new AsyncTask<String,Integer,String>(){
                    @Override
                    protected String doInBackground(String... params) {
                        String string = new JavaUtils().getString("http://api.kkmh.com/v1/daily/comic_lists/0?since=0&gender=0&sa_event=eyJwcm9qZWN0Ijoia3VhaWthbl9hcHAiLCJ0aW1lIjoxNDg3NzQyMjQwNjE1LCJwcm9wZXJ0aWVzIjp7IkhvbWVwYWdlVGFiTmFtZSI6IueDremXqCIsIlZDb21tdW5pdHlUYWJOYW1lIjoi54Ot6ZeoIiwiJG9zX3ZlcnNpb24iOiI0LjQuMiIsIkdlbmRlclR5cGUiOiLlpbPniYgiLCJGcm9tSG9tZXBhZ2VUYWJOYW1lIjoi54Ot6ZeoIiwiJGxpYl92ZXJzaW9uIjoiMS42LjEzIiwiJG5ldHdvcmtfdHlwZSI6IldJRkkiLCIkd2lmaSI6dHJ1ZSwiJG1hbnVmYWN0dXJlciI6ImJpZ25veCIsIkZyb21Ib21lcGFnZVVwZGF0ZURhdGUiOjAsIiRzY3JlZW5faGVpZ2h0IjoxMjgwLCJIb21lcGFnZVVwZGF0ZURhdGUiOjAsIlByb3BlcnR5RXZlbnQiOiJSZWFkSG9tZVBhZ2UiLCJGaW5kVGFiTmFtZSI6IuaOqOiNkCIsImFidGVzdF9ncm91cCI6MTEsIiRzY3JlZW5fd2lkdGgiOjcyMCwiJG9zIjoiQW5kcm9pZCIsIlRyaWdnZXJQYWdlIjoiSG9tZVBhZ2UiLCIkY2FycmllciI6IkNoaW5hIE1vYmlsZSIsIiRtb2RlbCI6IlZQaG9uZSIsIiRhcHBfdmVyc2lvbiI6IjMuNi4yIn0sInR5cGUiOiJ0cmFjayIsImRpc3RpbmN0X2lkIjoiQTo2YWRkYzdhZTQ1MjUwMzY1Iiwib3JpZ2luYWxfaWQiOiJBOjZhZGRjN2FlNDUyNTAzNjUiLCJldmVudCI6IlJlYWRIb21lUGFnZSJ9 ");
                        return string;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        List<JavaBean.DataBean.ComicsBean> comics = new Gson().fromJson(s,JavaBean.class).getData().getComics();

                        MyAdapter myAdapter = new MyAdapter(comics, getActivity());
                        xlv.setAdapter(myAdapter);
                        //停止刷新
                        stopLoad();
                    }
                }.execute();
            }

            @Override
            public void onLoadMore() {
                new AsyncTask<String,Integer,String>(){
                    @Override
                    protected String doInBackground(String... params) {
                        int i=0;
                        i++;
                 String string = new JavaUtils().getString("http://api.kkmh.com/v1/daily/comic_lists/0?since=0&gender="+i+"&sa_event=eyJwcm9qZWN0Ijoia3VhaWthbl9hcHAiLCJ0aW1lIjoxNDg3NzQyMjQwNjE1LCJwcm9wZXJ0aWVzIjp7IkhvbWVwYWdlVGFiTmFtZSI6IueDremXqCIsIlZDb21tdW5pdHlUYWJOYW1lIjoi54Ot6ZeoIiwiJG9zX3ZlcnNpb24iOiI0LjQuMiIsIkdlbmRlclR5cGUiOiLlpbPniYgiLCJGcm9tSG9tZXBhZ2VUYWJOYW1lIjoi54Ot6ZeoIiwiJGxpYl92ZXJzaW9uIjoiMS42LjEzIiwiJG5ldHdvcmtfdHlwZSI6IldJRkkiLCIkd2lmaSI6dHJ1ZSwiJG1hbnVmYWN0dXJlciI6ImJpZ25veCIsIkZyb21Ib21lcGFnZVVwZGF0ZURhdGUiOjAsIiRzY3JlZW5faGVpZ2h0IjoxMjgwLCJIb21lcGFnZVVwZGF0ZURhdGUiOjAsIlByb3BlcnR5RXZlbnQiOiJSZWFkSG9tZVBhZ2UiLCJGaW5kVGFiTmFtZSI6IuaOqOiNkCIsImFidGVzdF9ncm91cCI6MTEsIiRzY3JlZW5fd2lkdGgiOjcyMCwiJG9zIjoiQW5kcm9pZCIsIlRyaWdnZXJQYWdlIjoiSG9tZVBhZ2UiLCIkY2FycmllciI6IkNoaW5hIE1vYmlsZSIsIiRtb2RlbCI6IlZQaG9uZSIsIiRhcHBfdmVyc2lvbiI6IjMuNi4yIn0sInR5cGUiOiJ0cmFjayIsImRpc3RpbmN0X2lkIjoiQTo2YWRkYzdhZTQ1MjUwMzY1Iiwib3JpZ2luYWxfaWQiOiJBOjZhZGRjN2FlNDUyNTAzNjUiLCJldmVudCI6IlJlYWRIb21lUGFnZSJ9 ");
                        return string;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);

                        List<JavaBean.DataBean.ComicsBean> comics1 = new Gson().fromJson(s,JavaBean.class).getData().getComics();

                         comics.addAll(comics1);

                        myAdapter.notifyDataSetChanged();

                        //停止刷新
                        stopLoad();
                    }
                }.execute();
            }
        });



    }
    private void stopLoad(){
        xlv.stopRefresh();
        xlv.stopLoadMore();
        xlv.setRefreshTime("刚刚");
    }

   //解析第一幕的数据
    private void jiexi() {
        new AsyncTask<String,Integer,String>(){
            @Override
            protected String doInBackground(String... params) {
                String string = new JavaUtils().getString("http://api.kkmh.com/v1/daily/comic_lists/0?since=0&gender=0&sa_event=eyJwcm9qZWN0Ijoia3VhaWthbl9hcHAiLCJ0aW1lIjoxNDg3NzQyMjQwNjE1LCJwcm9wZXJ0aWVzIjp7IkhvbWVwYWdlVGFiTmFtZSI6IueDremXqCIsIlZDb21tdW5pdHlUYWJOYW1lIjoi54Ot6ZeoIiwiJG9zX3ZlcnNpb24iOiI0LjQuMiIsIkdlbmRlclR5cGUiOiLlpbPniYgiLCJGcm9tSG9tZXBhZ2VUYWJOYW1lIjoi54Ot6ZeoIiwiJGxpYl92ZXJzaW9uIjoiMS42LjEzIiwiJG5ldHdvcmtfdHlwZSI6IldJRkkiLCIkd2lmaSI6dHJ1ZSwiJG1hbnVmYWN0dXJlciI6ImJpZ25veCIsIkZyb21Ib21lcGFnZVVwZGF0ZURhdGUiOjAsIiRzY3JlZW5faGVpZ2h0IjoxMjgwLCJIb21lcGFnZVVwZGF0ZURhdGUiOjAsIlByb3BlcnR5RXZlbnQiOiJSZWFkSG9tZVBhZ2UiLCJGaW5kVGFiTmFtZSI6IuaOqOiNkCIsImFidGVzdF9ncm91cCI6MTEsIiRzY3JlZW5fd2lkdGgiOjcyMCwiJG9zIjoiQW5kcm9pZCIsIlRyaWdnZXJQYWdlIjoiSG9tZVBhZ2UiLCIkY2FycmllciI6IkNoaW5hIE1vYmlsZSIsIiRtb2RlbCI6IlZQaG9uZSIsIiRhcHBfdmVyc2lvbiI6IjMuNi4yIn0sInR5cGUiOiJ0cmFjayIsImRpc3RpbmN0X2lkIjoiQTo2YWRkYzdhZTQ1MjUwMzY1Iiwib3JpZ2luYWxfaWQiOiJBOjZhZGRjN2FlNDUyNTAzNjUiLCJldmVudCI6IlJlYWRIb21lUGFnZSJ9 ");
                return string;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                comics = new Gson().fromJson(s,JavaBean.class).getData().getComics();
                for(int i = 0; i< comics.size(); i++){
                    String image_url = comics.get(i).getCover_image_url();
                    String desc = comics.get(i).getTitle();
                    str.add(desc);

                    //获得图片的视图
                    getImageview(image_url);

                    //获得圆的
                    getYuanDian();

                }
               //为Viewpager写适配器
                initView();
                //为Xlistview写适配器
                myAdapter = new MyAdapter(comics, getActivity());
                xlv.setAdapter(myAdapter);
            }
        }.execute();
    }

    private void initView() {
     vp.setAdapter(new PagerAdapter() {
         @Override
         public int getCount() {
             return Integer.MAX_VALUE;
         }

         @Override
         public boolean isViewFromObject(View view, Object object) {
             return view==object;
         }

         @Override
         public Object instantiateItem(ViewGroup container, int position) {
             View imageView = imagelist.get(position % imagelist.size());
             container.addView(imageView);
             return imageView;

         }

         @Override
         public void destroyItem(ViewGroup container, int position, Object object) {
             container.removeView((View) object);
         }
     });
        //设置圆点的第一个被选中
        dolist.get(0).setBackgroundResource(R.drawable.shape1);
                //设置viewpager的当前位置<br>
                vp.setCurrentItem(50000000);
        //启动定时器
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {

                    @Override

                    public void run() {

                                current=vp.getCurrentItem()+1;
                                Message msg = Message.obtain();
                                msg.arg1=current;
                                msg.what=1;
                                h.sendMessage(msg);

                    }
                }, 3000, 2000);
    }



    private void getYuanDian() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_item, null);
                View view1 = view.findViewById(R.id.view);
//将试图添加到收集圆点的集合中 <br>
                dolist.add(view1);
//将圆点的视图加载在Linearlayout的占位上 <br>
                ll.addView(view);
    }

    private void getImageview(String image_url) {

                ImageView imageView = new ImageView(getActivity());

                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                ImageSize size=new ImageSize(200,100);

                ImageLoader.getInstance().displayImage(image_url,imageView,size);

                imagelist.add(imageView);
    }
}
