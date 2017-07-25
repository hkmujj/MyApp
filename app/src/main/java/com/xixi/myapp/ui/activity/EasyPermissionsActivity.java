package com.xixi.myapp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.xixi.myapp.R;
import com.xixi.myapp.utils.LL;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class EasyPermissionsActivity extends BaseActivity implements OnClickListener,EasyPermissions.PermissionCallbacks{

	private TextView tv_camera,tv_sdcard;
	private static final int RC_CAMERA_PERM = 123;
	private static final int RC_LOCATION_CONTACTS_PERM = 124;

	@BindView(R.id.toolbar)
	Toolbar toolbar;


	@Override
	public int getContentViewId() {
		return R.layout.activity_easy_permission;
	}

	@Override
	protected void initAll(Bundle savedInstanceState) {
		init();
		data();
	}

	private void init(){
		toolbar.setTitle("EasyPermissions");
		tv_camera = findView(R.id.tv_camera);
		tv_sdcard = findView(R.id.tv_sdcard);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setCommonToolbar(toolbar," EasyPermissions");

		tv_camera.setOnClickListener(this);
		tv_sdcard.setOnClickListener(this);
	}

	//业务数据操作
	private void data(){
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_camera:
			cameraTask();
			break;
		case R.id.tv_sdcard:
			locationAndContactsTask();
			break;

		default:
			break;
		}		
	}

	@AfterPermissionGranted(RC_CAMERA_PERM)
	public void cameraTask() {
		if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
			// Have permission, do the thing!
			Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show();
		} else {
			// Ask for one permission
			EasyPermissions.requestPermissions(this,"需要相机权限",
					RC_CAMERA_PERM, Manifest.permission.CAMERA);
		}
	}

	@AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
	public void locationAndContactsTask() {
		String[] perms = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS };
		if (EasyPermissions.hasPermissions(this, perms)) {
			// Have permissions, do the thing!
			Toast.makeText(this, "TODO: Location and Contacts things", Toast.LENGTH_LONG).show();
		} else {
			// Ask for both permissions
			EasyPermissions.requestPermissions(this, "需要定位和读取存储权限",
					RC_LOCATION_CONTACTS_PERM, perms);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		// EasyPermissions handles the request result.
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
	}

	@Override
	public void onPermissionsGranted(int requestCode, List<String> perms) {
		LL.i("onPermissionsGranted:" + requestCode + ":" + perms.size());
	}

	@Override
	public void onPermissionsDenied(int requestCode, List<String> perms) {
		LL.i("onPermissionsDenied:" + requestCode + ":" + perms.size());

		// (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
		// This will display a dialog directing them to enable the permission in app settings.
		if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
			new AppSettingsDialog.Builder(this).setTitle("需要权限").setRationale("你需在设置页放开权限")
					.build().show();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
			// Do something after user returned from app settings screen, like showing a Toast.
			Toast.makeText(this, "应用设置返回", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
