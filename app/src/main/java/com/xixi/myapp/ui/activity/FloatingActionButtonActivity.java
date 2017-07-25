package com.xixi.myapp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xixi.myapp.R;

import butterknife.BindView;

public class FloatingActionButtonActivity extends BaseActivity implements View.OnClickListener{

	private TextInputLayout textInput;
	private FloatingActionButton fab;
	private Button snackbar;
	private CoordinatorLayout main_content;


	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@Override
	public int getContentViewId() {
		return R.layout.activity_material_ui;
	}

	@Override
	protected void initAll(Bundle savedInstanceState) {
		init();
		data();
	}

	//初始化控件
	public void init() {
		setCommonToolbar(toolbar,"Material design 控件");
		textInput = findView(R.id.textInput);
		fab = findView(R.id.fab);
		main_content = findView(R.id.main_content);
		snackbar = findView(R.id.snackbar);
		fab.setOnClickListener(this);
		snackbar.setOnClickListener(this);

//		textInput.setHint("请输入姓名:");
		EditText editText = textInput.getEditText();
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start,int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length()>4){
					textInput.setErrorEnabled(true);
					textInput.setError("姓名长度不能超过4个");
				}else{
					textInput.setErrorEnabled(false);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}



	//业务数据操作
	public void data() {


	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.fab:
				toast("FloatingActionButton");
				break;
			case R.id.snackbar:

				final Snackbar snackbar = Snackbar.make(main_content,"小楼一夜听春雨",Snackbar.LENGTH_LONG);
				snackbar.show();
				snackbar.setAction("取消", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						snackbar.dismiss();
					}
				});
				break;

		}
	}
}
