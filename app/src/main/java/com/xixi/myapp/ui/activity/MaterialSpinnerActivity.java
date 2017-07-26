package com.xixi.myapp.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.xixi.myapp.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MaterialSpinnerActivity extends BaseActivity{


	@BindView(R.id.fab)
	FloatingActionButton fab;

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@BindView(R.id.spinner)
	MaterialSpinner spinner;

	private static final String[] ANDROID_VERSIONS = {
			"Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "Jelly Bean", "KitKat",
			"Lollipop", "Marshmallow"
	};

	@Override
	public int getContentViewId() {
		return R.layout.activity_material_spinner;
	}

	@Override
	protected void initAll(Bundle savedInstanceState) {
		init();
		data();
	}

	//初始化控件
	public void init() {
		setCommonToolbar(toolbar,"MaterialSpinner");

		spinner.setItems(ANDROID_VERSIONS);
		spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

			@Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
				Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
			}
		});
		spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

			@Override public void onNothingSelected(MaterialSpinner spinner) {
				Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
			}
		});
	}



	//业务数据操作
	public void data() {


	}
	@OnClick(R.id.fab)
	public void fab(View v) {
		try {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/jaredrummler/Material-Spinner")));
		} catch (ActivityNotFoundException ignored) {
		}
	}

}
