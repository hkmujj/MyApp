package com.xixi.myapp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xixi.myapp.R;
import com.xixi.myapp.ui.adapter.ViewPagerAdapter;
import com.xixi.myapp.ui.fragment.FragmentViewPager;

import butterknife.BindView;

public class TabLayoutActivity extends BaseActivity implements FragmentViewPager.OnFragmentInteractionListener{

	private TabLayout tab_bar;
	private ViewPager view_pager;
	private ViewPagerAdapter mViewPagerAdapter;

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@Override
	public int getContentViewId() {
		return R.layout.activity_tablayout;
	}

	@Override
	protected void initAll(Bundle savedInstanceState) {
		init();
		data();
	}


	//初始化控件
	public void init() {
		tab_bar = (TabLayout) findViewById(R.id.tab_bar);
		view_pager = (ViewPager) findViewById(R.id.view_pager);
		setCommonToolbar(toolbar,"TabLayout");

		setupViewPager();
	}

	//业务数据操作
	public void data() {
		
		
	}

	private void setupViewPager() {
		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
		view_pager.setAdapter(mViewPagerAdapter);
		tab_bar.setupWithViewPager(view_pager);//this is the new nice thing ;必须在setAdapter之后
		view_pager.addOnPageChangeListener(pageChangeListener);
	}

	PageChangeListener pageChangeListener;


	public  class PageChangeListener implements ViewPager.OnPageChangeListener{
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {

		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}

	}

	@Override
	public void onFragmentInteraction(Bundle bundle) {
		tab_bar.getTabAt(0).select();
	}
}
