package xiyou3g.example.com.newqiy.framents;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import xiyou3g.example.com.newqiy.Adapter.MyRecommendPageAdapter;
import xiyou3g.example.com.newqiy.R;

/**
 * Created by 蒲家旺 on 2017/5/26.
 * 推荐视频的界面；
 * 类似与官方给的demo
 */

public class Recom_Frament extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.recomlayout,container,false);
        initview(view);
        return view;
    }

    private void initview(View view) {
        //Fragment+ViewPager+FragmentViewPager组合的使用
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        MyRecommendPageAdapter adapter = new MyRecommendPageAdapter(
                getActivity().getSupportFragmentManager(),getActivity().getBaseContext());
        viewPager.setAdapter(adapter);
        //TabLayout 与  Viewpager 绑定
        final TabLayout tabLayout = (TabLayout)view.findViewById(R.id.recomtablayout);
        tabLayout.setupWithViewPager(viewPager);

        //利用反射把 tablayout 的下划线变小
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 30, 30);
            }
        });
    }

    //利用反射将tablayout的下划线变短。
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        if (tabStrip != null) {
            tabStrip.setAccessible(true);
        }
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                rightDip, Resources.getSystem().getDisplayMetrics());

        if (llTab != null) {
            for (int i = 0; i < llTab.getChildCount(); i++) {
                View child = llTab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = left;
                params.rightMargin = right;
                child.setLayoutParams(params);
                child.invalidate();
            }
        }

    }
}
