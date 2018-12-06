package xiyou3g.example.com.newqiy.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import xiyou3g.example.com.newqiy.framents.RecomFragment;


/**
 * Created by DickJampink on 2017/6/1.
 * view pager中加载的fragment的适配器
 */

public class MyRecommendPageAdapter extends FragmentPagerAdapter {

    private String[] tabTitle = {"推荐", "电影", "电视剧", "纪录片", "动漫", "综艺"};
    private Context context;
    public MyRecommendPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public Fragment getItem(int position) {
        return RecomFragment.newInstance(position+1);
    }

    @Override
    public int getCount() {
        return tabTitle.length;
    }

    //tablayout选项的数据设定
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
