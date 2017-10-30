package com.example.shenxuesong.sanyueyuekaoajuan.FGT;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shenxuesong.sanyueyuekaoajuan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenxuesong on 2017/9/20.
 */

public class Fragment02 extends Fragment {
    private TabLayout tab;
    private ViewPager vp1;
    private List<String> list=new ArrayList<String>();//收集标题的集合
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment02,container,false);
        //查找控件
        tab = (TabLayout) view.findViewById(R.id.table);
        vp1 = (ViewPager) view.findViewById(R.id.pager);
        //tab的标题
    list.clear();
        list.add("头条");
        list.add("社会");
        list.add("国内");
        list.add("国际");
        list.add("娱乐");
        list.add("体育");
        list.add("军事");
        list.add("科技");
        list.add("财经");
        list.add("时尚");
        //tablayout和viewpager关联
        tab.setupWithViewPager(vp1);
        vp1.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {

            @Override
            public CharSequence getPageTitle(int position) {

                return list.get(position);
            }

             public Fragment getItem(int position) {
                //实例化一个Fragment加载Xlistview
                NewFragment  newfragment = new NewFragment();
                Bundle bundle = new Bundle();
                if(list.get(position).equals("头条")){
                    bundle.putString("name","1");
                }
                else
                if(list.get(position).equals("社会")){
                    bundle.putString("name","10");
                }
                else
                if(list.get(position).equals("国内")){
                    bundle.putString("name","2");
                }
                else
                if(list.get(position).equals("国际")){
                    bundle.putString("name","3");
                }
                else
                if(list.get(position).equals("娱乐")){
                    bundle.putString("name","4");
                }
                else
                if(list.get(position).equals("财经")){
                    bundle.putString("name","5");
                }
                else
                if(list.get(position).equals("军事")){
                    bundle.putString("name","6");
                }
                else
                if(list.get(position).equals("科技")){
                    bundle.putString("name","7");
                }
                else
                if(list.get(position).equals("体育")){
                    bundle.putString("name","8");
                }
                else
                if(list.get(position).equals("时尚")){
                    bundle.putString("name","9");
                }
                //activity与fragment 1.getset，2.接口回调，3.setArguments ,getAraguments
                newfragment.setArguments(bundle);
                //相当于加载了一个Fragment
                return newfragment;
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });


        return view;

    }

    }
