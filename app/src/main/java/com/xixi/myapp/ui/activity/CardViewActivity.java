package com.xixi.myapp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.xixi.myapp.R;
import com.xixi.myapp.model.bean.ModelBean;
import com.xixi.myapp.ui.adapter.CardViewAdapter;
import com.xixi.myapp.ui.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CardViewActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

	private RecyclerView recyclerView;
	private Spinner spinner;
	private TextView add;

	private List<ModelBean> beanList;
	private CardViewAdapter adapter;
	private String des[] = {"云层里的阳光", "好美的海滩1", "好美的海滩2", "夕阳西下的美景1", "夕阳西下的美景2"
			, "夕阳西下的美景3", "夕阳西下的美景4", "夕阳西下的美景5", "好美的海滩3"};

	private int resId[] = {R.drawable.img1, R.drawable.img2, R.drawable.img2, R.drawable.img3,
			R.drawable.img4, R.drawable.img5, R.drawable.img3, R.drawable.img1};

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@Override
	public int getContentViewId() {
		return R.layout.activity_cardview;
	}

	@Override
	protected void initAll(Bundle savedInstanceState) {
		init();
		data();
	}

	//初始化控件
	public void init() {
		setCommonToolbar(toolbar,"CardView");
		spinner = findView(R.id.toolbar_category);
		add = findView(R.id.tv_add);
		recyclerView = findView(R.id.recyclerView);

		add.setOnClickListener(this);
		initSpinner();
	}
	//下拉选项
	private void initSpinner() {
		String categorys[] = this.getResources().getStringArray(R.array.categorys);
		ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorys);

		// 为adapter设置下拉菜单样式
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// spinner设置adapter
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}

	//业务数据操作
	public void data() {
		beanList = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			ModelBean bean = new ModelBean();
			bean.setResId(resId[i]);
			bean.setTitle(des[i]);
			beanList.add(bean);
		}

		setRecyclerView();
	}

	public void setRecyclerView() {
		//设置固定大小
		recyclerView.setHasFixedSize(true);
		//给RecyclerView设置布局管理器
		recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayout.VERTICAL,false));//第三个参数：表示是否从最后的Item数据开始显示，ture表示是，false就是正常显示—从开头显示。
		//设置分割线
		recyclerView.addItemDecoration(new DividerItemDecoration(this,
				DividerItemDecoration.VERTICAL_LIST));
		//设置默认动画
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		//创建适配器，并且设置
		adapter = new CardViewAdapter(this, beanList);
		recyclerView.setAdapter(adapter);
		adapter.setOnItemClickListener(new CardViewAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(int position, Object object) {
				toast(((ModelBean) object).getTitle());
			}
		});
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
			case 0:
				recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
				break;
			case 1:
				recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, true));
				break;
			case 2:
				recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
				break;
			case 3:
				recyclerView.setLayoutManager(new GridLayoutManager(this, 2, LinearLayout.HORIZONTAL, true));
				break;
			case 4:
				recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
				break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}
    private int a = 0;
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.tv_add:
				a++;
				ModelBean bean = new ModelBean();
				bean.setTitle("这是新添加的"+a);
				bean.setResId(R.drawable.img5);
				beanList.add(0, bean);
				adapter.notifyDataSetChanged();//更新全部数据
//				adapter.notifyItemInserted(0);//在position位置插入数据的时候更新
//				adapter.notifyItemRemoved(0);//移除postion位置的数据的时候更新
//				adapter.notifyItemChanged(0);//当postion位置数据有改变时候更新
//				adapter.notifyItemMoved(0,1);//移除从位置formPosition到toPosition位置数据
//				adapter.notifyItemRangeChanged(0,2);
//				adapter.notifyItemRangeInserted(0,2);
//				adapter.notifyItemRangeRemoved(0,2);
				break;
		}
	}
}