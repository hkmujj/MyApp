package com.xixi.myapp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.xixi.myapp.R;
import com.xixi.myapp.ui.adapter.RecyclerMainAdapter;
import com.xixi.myapp.ui.view.DividerItemDecoration;

import butterknife.BindView;

public class CollapsingToolbarActivity extends BaseActivity{

	private RecyclerView recyclerView;
	private String mTitles[] = new String[15];

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@Override
	public int getContentViewId() {
		return R.layout.activity_collapsingtoolbar;
	}

	@Override
	protected void initAll(Bundle savedInstanceState) {
		init();
		data();
	}


	//初始化控件
	public void init() {
		setCommonToolbar(toolbar,"CollapsingToolbarLayout");

		recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

	}

	//业务数据操作
	public void data() {
		for(int i=0;i<15;i++){
			mTitles[i] = "item"+i;
		}

		setRecyclerViewData();
	}

	public void setRecyclerViewData() {
		//设置固定大小
		recyclerView.setHasFixedSize(true);
		//创建线性布局
		LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
		//垂直方向
		mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
		//给RecyclerView设置布局管理器
		recyclerView.setLayoutManager(mLayoutManager);
		//设置分割线
		recyclerView.addItemDecoration(new DividerItemDecoration(this,
				DividerItemDecoration.VERTICAL_LIST));
		//设置默认动画
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		//创建适配器，并且设置
		RecyclerMainAdapter adapter = new RecyclerMainAdapter(this,mTitles);
		recyclerView.setAdapter(adapter);
	}
}