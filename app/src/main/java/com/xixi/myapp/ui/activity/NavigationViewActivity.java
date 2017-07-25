package com.xixi.myapp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.xixi.myapp.R;
import com.xixi.myapp.ui.view.circleImageView.CircleImageView;

import butterknife.BindView;

public class NavigationViewActivity extends BaseActivity{

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ImageView iv_main;

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@Override
	public int getContentViewId() {
		return R.layout.activity_navigationview;
	}

	@Override
	protected void initAll(Bundle savedInstanceState) {
		init();
		data();
	}

	//初始化控件
	public void init() {
		iv_main = (ImageView) findViewById(R.id.iv_main);

		setCommonToolbar(toolbar,"NavigationView");

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem item) {
				drawerItemSelected(item);
				return true;
			}
		});
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
		mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar, R.string.open, R.string.close);

		//为抽屉设置toggle监听
		mDrawerLayout.addDrawerListener(mDrawerToggle);
		//设置了这个才能显示左上角图标，该图标可以根据抽屉状态的变化过程而变化
		mDrawerToggle.syncState();

		View headerView=navigationView.getHeaderView(0);
		CircleImageView headIconImg= (CircleImageView) headerView.findViewById(R.id.my_photo);
		headIconImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toast("头像");
			}
		});
	}

	private void drawerItemSelected(MenuItem menuItem) {

		switch (menuItem.getItemId()) {
			case R.id.nav_camera:
				toast("拍照");
				break;
			case R.id.nav_gallery:
				toast("图片库");
				break;
		}

		mDrawerLayout.closeDrawers();
	}


	//业务数据操作
	public void data() {


	}


}
