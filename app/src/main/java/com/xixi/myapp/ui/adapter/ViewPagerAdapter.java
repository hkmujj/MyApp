package com.xixi.myapp.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xixi.myapp.ui.fragment.FragmentViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public final static int US_BOX_PAGER_POSITON = 0;


    private Context mContext;


    List<Fragment> mFragments = new ArrayList<>() ;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        initFragments();
    }

    private void initFragments() {
        mFragments.add(FragmentViewPager.newInstance(0,"哈哈"));
        mFragments.add(FragmentViewPager.newInstance(1,"呵呵"));
        mFragments.add(FragmentViewPager.newInstance(2,"嘿嘿"));
        for (int i=3;i<9;i++){
            mFragments.add(FragmentViewPager.newInstance(i,"item"+i));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "title"+position;
       /* switch (position) {
            case 0:
                title = "title"+position;
                break;
        }*/
        return title;
    }

    public Fragment getFragment(int position) {
        return mFragments.get(position);
    }

}
