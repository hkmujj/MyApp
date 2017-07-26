package com.xixi.myapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.xixi.myapp.R;
import com.xixi.myapp.ui.adapter.RecyclerMainAdapter;
import com.xixi.myapp.ui.view.DividerItemDecoration;

import butterknife.BindView;

public class MaterialDesignActivity extends BaseActivity{

	private RecyclerView recycler_view;
	private RecyclerMainAdapter adapter;
	private String mTitles[] = new String[]{"简单的DrawerLayout侧滑","DrawerLayout与NavigationView","TabLayout","CollapsingToolbarLayout",
			"RecyclerView+CardView+Palette","UI控件","MaterialSpinner"};
	private Class<?>[] ACTIVITY = {DrawerlayoutActivity.class, NavigationViewActivity.class, TabLayoutActivity.class,
			CollapsingToolbarActivity.class,CardViewActivity.class,FloatingActionButtonActivity.class,MaterialSpinnerActivity.class};

	@BindView(R.id.toolbar)
	Toolbar toolbar;


	@Override
	public int getContentViewId() {
		return R.layout.activity_material_design;
	}

	@Override
	protected void initAll(Bundle savedInstanceState) {
		init();
		data();
	}

	//初始化控件
	public void init() {
		recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
//		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setLogo(R.drawable.title_menu);
		toolbar.setTitle("Material");
		toolbar.inflateMenu(R.menu.toolbar_menu);
//		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.title_back);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()){
					case R.id.action_search:
						toast("搜索");
						break;
					case R.id.action_notification:
						toast("通知");
						break;
					case R.id.action_item1:
						toast("设置");
						break;
					case R.id.action_item2:
						toast("关于");
						break;
				}
				return true;
			}
		});

	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.toolbar_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}*/

	//业务数据操作
	public void data() {
		//设置固定大小
		recycler_view.setHasFixedSize(true);
		//创建线性布局
		LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
		//垂直方向
		mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
		//给RecyclerView设置布局管理器
		recycler_view.setLayoutManager(mLayoutManager);
		//设置分割线
		recycler_view.addItemDecoration(new DividerItemDecoration(this,
				DividerItemDecoration.VERTICAL_LIST));
		//设置默认动画
		recycler_view.setItemAnimator(new DefaultItemAnimator());
		//创建适配器，并且设置
		adapter = new RecyclerMainAdapter(this,mTitles);
		recycler_view.setAdapter(adapter);
		adapter.setOnItemClickLitener(new RecyclerMainAdapter.OnItemClickLitener() {
			@Override
			public void onItemClick(View view, int position) {
				Intent intent = new Intent(MaterialDesignActivity.this, ACTIVITY[position]);
				startActivity(intent);
			}

			@Override
			public void onItemLongClick(View view, int position) {
				toast(position+"");

			}
		});

	}
	
}
