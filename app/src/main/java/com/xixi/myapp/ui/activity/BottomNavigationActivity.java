package com.xixi.myapp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.xixi.myapp.R;
import com.xixi.myapp.ui.adapter.RecyclerMainAdapter;
import com.xixi.myapp.ui.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BottomNavigationActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.layFrame)
	TextView layFrame;
	@BindView(R.id.bottom_navigation_bar)
	BottomNavigationBar bottomNavigationBar;
	private List<String> list;

	@Override
	public int getContentViewId() {
		return R.layout.activity_bottom;
	}

	@Override
	protected void initAll(Bundle savedInstanceState) {
		init();
		data();
	}

	//初始化控件
	public void init() {
		setCommonToolbar(toolbar,"BottomNavigation");
		setBottomNavigationBar();
	}


	//业务数据操作
	public void data() {
		list = new ArrayList<String>();
		list.add("Home");
		list.add("Books");
		list.add("Music");
		list.add("Movies");
		list.add("Games");
	}

	private void setBottomNavigationBar(){
		bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
		bottomNavigationBar
				.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
				);
		BadgeItem numberBadgeItem = new BadgeItem()
				.setBorderWidth(4)
				.setBackgroundColor(Color.RED)
				.setText("5")
				.setHideOnSelect(true);
		bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange))
				.addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColorResource(R.color.teal))
				.addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.blue))
				.addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies&TV").setActiveColorResource(R.color.brown))
				.addItem(new BottomNavigationItem(R.drawable.ic_videogame_white_24dp, "Games").setActiveColorResource(R.color.red).setBadgeItem(numberBadgeItem))
				.setFirstSelectedPosition(0)
				.initialise();

		bottomNavigationBar.setTabSelectedListener(this);
	}

	@Override
	public void onTabSelected(int position) {
		layFrame.setText(list.get(position));
	}

	@Override
	public void onTabUnselected(int position) {

	}

	@Override
	public void onTabReselected(int position) {

	}
}
