package com.xixi.myapp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.xixi.myapp.R;
import com.xixi.myapp.ui.adapter.SwipeAdapter;
import com.xixi.myapp.ui.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SwipeRefreshLayoutActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

	private RecyclerView recyclerView;
	private SwipeAdapter adapter;
	private List<String> datalist;
	private SwipeRefreshLayout swipe_refresh;
	private Handler handler = new Handler();

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@Override
	public int getContentViewId() {
		return R.layout.activity_pullrefresh;
	}

	@Override
	protected void initAll(Bundle savedInstanceState) {
		init();
		data();
	}

	//初始化控件
	public void init() {
		setCommonToolbar(toolbar,"SwipeRefreshLayout");
		recyclerView = findView(R.id.recyclerView);
		swipe_refresh = findView(R.id.swipe_refresh);
		swipe_refresh.setOnRefreshListener(this);

		// 顶部刷新的样式(4种颜色依次旋转)
		swipe_refresh.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_blue_bright,
				android.R.color.holo_red_light, android.R.color.holo_orange_light);
	}



	//业务数据操作
	public void data() {
		datalist = new ArrayList<String>();
		for(int i =0;i<12;i++){
			datalist.add("SwipeRefreshLayout"+i);
		}
		setRecyclerView();
	}
	public void setRecyclerView() {
		//设置固定大小
		recyclerView.setHasFixedSize(true);
		//给RecyclerView设置布局管理器
		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
		//设置分割线
		recyclerView.addItemDecoration(new DividerItemDecoration(this,
				DividerItemDecoration.VERTICAL_LIST));
		//设置默认动画
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		//创建适配器，并且设置
		adapter = new SwipeAdapter(this,datalist);
		recyclerView.setAdapter(adapter);
		adapter.setOnItemClickLitener(new SwipeAdapter.OnItemClickLitener() {
			@Override
			public void onItemClick(View view, int position) {
				toast(datalist.get(position));
			}

			@Override
			public void onItemLongClick(View view, int position) {
				toast(position+"");

			}
		});

	}

	//下拉刷新
	@Override
	public void onRefresh() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				swipe_refresh.setRefreshing(false);
				datalist.add("新增项");
				adapter.notifyDataSetChanged();
			}
		},2000);
	}

}
