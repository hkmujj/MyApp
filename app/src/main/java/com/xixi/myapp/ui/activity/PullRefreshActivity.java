package com.xixi.myapp.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.xixi.myapp.R;
import com.xixi.myapp.ui.adapter.RecyclerMainAdapter;
import com.xixi.myapp.ui.view.DividerItemDecoration;

import butterknife.BindView;

public class PullRefreshActivity extends BaseActivity{

	private RecyclerView recyclerView;
	private RecyclerMainAdapter adapter;
	private SwipeRefreshLayout swipe_refresh;
	private String mTitles[] = new String[]{"SwipeRefreshLayout","MaterialRefreshLayout"
			};


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
		setCommonToolbar(toolbar,"下拉刷新");
		recyclerView = findView(R.id.recyclerView);
		swipe_refresh = findView(R.id.swipe_refresh);
		swipe_refresh.setEnabled(false);//禁止下拉刷新
	}



	//业务数据操作
	public void data() {
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
		adapter = new RecyclerMainAdapter(this,mTitles);
		recyclerView.setAdapter(adapter);
		adapter.setOnItemClickLitener(new RecyclerMainAdapter.OnItemClickLitener() {
			@Override
			public void onItemClick(View view, int position) {
				switch (position){
					case 0:
						goActivity(SwipeRefreshLayoutActivity.class);
						break;
					case 1:
//						goActivity(MaterialRefreshLayoutActivity.class);
						break;
					case 2:
//						goActivity(MaterialRefreshLayoutActivity.class);
					break;
				}
			}

			@Override
			public void onItemLongClick(View view, int position) {
				toast(position+"");

			}
		});

	}
	
}
