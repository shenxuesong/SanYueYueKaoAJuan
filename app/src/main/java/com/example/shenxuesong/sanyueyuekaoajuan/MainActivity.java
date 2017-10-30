package com.example.shenxuesong.sanyueyuekaoajuan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shenxuesong.sanyueyuekaoajuan.FGT.Fragment01;
import com.example.shenxuesong.sanyueyuekaoajuan.FGT.Fragment02;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
     private ViewPager vp;
     private RadioGroup rg;
    private List<Fragment> list=new ArrayList<Fragment>();
    private List<String> list1=new ArrayList<String>();
    private DrawerLayout dl;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //查找组件
        vp = (ViewPager) findViewById(R.id.vp);
        rg = (RadioGroup) findViewById(R.id.rg);
        dl = (DrawerLayout) findViewById(R.id.dl);
        lv = (ListView) findViewById(R.id.lv);
        //造数据
        list.add(new Fragment01());
        list.add(new Fragment02());

        //设置适配器

        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });


        //设置viewpager的监听事件，使vp与rg有联动效果
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rg.check(R.id.rb1);
                        break;
                    case 1:
                        rg.check(R.id.rb2);
                        break;


                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rg.setOnCheckedChangeListener(this);

        //实现侧拉的功能
        initData();

    }

    private void initData() {
        dl.openDrawer(lv);
        list1.add("关注");
        list1.add("新闻");
        list1.add("礼包");
        list1.add("我的");
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list1.size();
            }

            @Override
            public Object getItem(int position) {
                return list1.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView=View.inflate(MainActivity.this,R.layout.listview_list1,null);
                TextView tv = (TextView) convertView.findViewById(R.id.tview);
                tv.setText(list1.get(position));
                return convertView;
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              /* NewFragment newFragment = new NewFragment();

                if (position == 0) {
                    String name = "1";
                    newFragment.jiexi(name);
                } else if (position == 1) {
                    String name = "2";
                    newFragment.jiexi(name);
                } else if (position == 2) {
                    String name = "3";
                    newFragment.jiexi(name);
                } else if (position == 3) {
                    String name = "4";
                    newFragment.jiexi(name);
                } else if (position == 4) {
                    String name = "5";
                    newFragment.jiexi(name);
                }*/
                Toast.makeText(MainActivity.this, list1.get(position), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb1:
                vp.setCurrentItem(0);

                break;

            case R.id.rb2:
                vp.setCurrentItem(1);
                break;

        }




    }

    }
