package com.xixi.myapp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xixi.myapp.R;

import butterknife.BindView;

public class DrawerlayoutActivity extends BaseActivity{

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	@BindView(R.id.item1)
	TextView item1;
	@BindView(R.id.item2)
	TextView item2;
	@BindView(R.id.iv_main)
	ImageView iv_main;

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@Override
	public int getContentViewId() {
		return R.layout.activity_drawerlayout;
	}

	@Override
	protected void initAll(Bundle savedInstanceState) {
		init();
		data();
	}

	//初始化控件
	public void init() {
		setCommonToolbar(toolbar,"Drawerlayout");
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar, R.string.open, R.string.close){
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}
		};
		//为抽屉设置toggle监听
		mDrawerLayout.addDrawerListener(mDrawerToggle);
		//设置了这个才能显示左上角图标，该图标可以根据抽屉状态的变化过程而变化
		mDrawerToggle.syncState();

		item1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toast("item1");
				mDrawerLayout.closeDrawer(GravityCompat.START);
				iv_main.setImageResource(R.drawable.my_photo_default);
			}
		});
		item2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toast("item2");
				mDrawerLayout.closeDrawer(GravityCompat.START);
				iv_main.setImageResource(R.drawable.cat);
			}
		});

	}



	//业务数据操作
	public void data() {


	}


}
