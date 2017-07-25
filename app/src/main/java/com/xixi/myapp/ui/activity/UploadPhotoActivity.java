package com.xixi.myapp.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.xixi.myapp.R;
import com.xixi.myapp.ui.view.BottomDialog;
import com.xixi.myapp.ui.view.circleImageView.CircleImageView;
import com.xixi.myapp.utils.BitmapUtils;
import com.xixi.myapp.utils.FileCache;
import com.xixi.myapp.utils.UploadUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class UploadPhotoActivity extends BaseActivity implements OnClickListener{

	private CircleImageView photo;
	/** 拍照 */
	private final int CODE_CAMERA = 0x12;
	/** 相册取 */
	private final int CODE_ALBUM = 0x13;
	/** 裁剪 */
	private final int CODE_CROP = 0x14;
	
	public static final int GET_PHOTO = 20;//上传头像成功
	public static final int GET_PHOTO_Failure = 21;//上传头像失败
	
	private Uri tempUri = null;
	private Uri photoUri = null;
	private Bitmap photoBitmap;
	private FileCache cachefile ;

	private ImageView picasso_img;
	String mUrl = "http://pic39.nipic.com/20140321/17561764_000020626150_2.jpg";
	
	private Handler handler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case GET_PHOTO:
				// 设置个人头像
				String result = (String) msg.obj;
				try {
					JSONObject jsonob = new JSONObject(result);
//					int code = jsonob.getInt("status");
//					if(code==1){
//						JSONObject obj = new JSONObject(jsonob.getString("data"));
//						String iconurl = obj.getString("icon");						
//
//					}else{
//						toast("修改头像失败");	
//					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				break;
			case GET_PHOTO_Failure:
				toast("修改头像失败");
				break;

			default:
				break;
			}
		}
	};

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@Override
	public int getContentViewId() {
		return R.layout.activity_uploadphoto;
	}

	@Override
	protected void initAll(Bundle savedInstanceState) {
		init();
		data();
	}
	
	//初始化控件
	public void init() {
		setCommonToolbar(toolbar,"上传头像");
		photo = (CircleImageView) findViewById(R.id.my_photo);
		cachefile = FileCache.getInstance(this);
		photo.setOnClickListener(this);

		picasso_img = findView(R.id.picasso_img);
	}

	//业务数据操作
	public void data() {
		Glide.with(UploadPhotoActivity.this)
				.load(mUrl)
				.placeholder(R.drawable.cat)
				.error(R.drawable.cat)
				.into(picasso_img);

	}
	
    private BottomDialog popupWindow;
	private View Imageview;

	/**
	 * 显示获取图片对话框，从相册？还是从相机拍照？
	 */
	public void showGetImageDlg() {
		if (popupWindow == null) {
			Imageview = UploadPhotoActivity.this.getLayoutInflater().inflate(R.layout.my_pop_choose,
					null);
			Button cameraBtn = (Button) Imageview
					.findViewById(R.id.btn_camera);
			Button albumBtn = (Button) Imageview
					.findViewById(R.id.btn_album);
			Button cancelBtn = (Button) Imageview
					.findViewById(R.id.btn_cancel);
			cameraBtn.setText("拍照");
			albumBtn.setText("从手机相册选取");

			cameraBtn.setOnClickListener(this);
			albumBtn.setOnClickListener(this);
			cancelBtn.setOnClickListener(this);

			// 具体视图绑定具体按键监听
			popupWindow = new BottomDialog(Imageview,
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			popupWindow.setOutsideTouchable(true);
			// 设置为null不能触发点击外面隐藏popWindow；
			popupWindow.setBackgroundDrawable(null);
			popupWindow.setAnimationStyle(R.style.popup_in_out);
			popupWindow.setOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {

				}
			});
		}
		popupWindow.showAtLocation(photo, Gravity.CENTER | Gravity.BOTTOM,
				0, 0);
	}	

	// 头像回调
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case CODE_CAMERA://相机
			Log.e("tags", "~~resultCode~~"+resultCode);
			if(resultCode == Activity.RESULT_OK){
			startPhotoZoom(photoUri, 300);
			}
			break;
			
		case CODE_ALBUM: // 相册
			if (data != null) {
				Log.e("tags", "data0--"+data.getData());
				startPhotoZoom(data.getData(), 300);
			}
			break;
			
		case CODE_CROP: // 头像裁剪
			Log.e("tags", "-裁剪-");
			if (data != null) {
				setPicToView(data);
			}else if(data==null){
				return;
			}
			break;	
		}

	}
	
	/**
	 * @Author xijun
	 * @Time 2015年11月24日 上午11:01:44
	 * @TODO 调用相机
	 */
	private void startCamearPicCut() {
		photoUri = cachefile.createImagePathUri();
		// 调用系统的拍照功能
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		intent.putExtra("camerasensortype", 2);// 调用前置摄像头
		intent.putExtra("autofocus", true);// 自动对焦
		intent.putExtra("fullScreen", false);// 全屏
		intent.putExtra("showActionIcons", false);
		// 指定调用相机拍照后照片的储存路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
		startActivityForResult(intent, CODE_CAMERA);
	}

	/**
	 * @Author xijun
	 * @Time 2015年11月24日 上午11:01:44
	 * @TODO 打开相册
	 */
	private void startImageCaptrue() {

		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, CODE_ALBUM);
	}
	
	//剪裁图片
	private void startPhotoZoom(Uri uri, int size) {
		tempUri = uri;
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");
		intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", false);
		try {
		startActivityForResult(intent, CODE_CROP);
		} catch (Exception e) {
			e.printStackTrace();
			toast("手机未安装看图工具！");
		}
	}

	// 将进行剪裁后的图片显示到UI界面上
	private void setPicToView(Intent picdata) {
		Log.e("tags", "getData-1-"+picdata.getData());
		Log.e("tags", "tempUri-2-"+tempUri);
//		Bundle bundle = picdata.getExtras();

//		if (bundle != null ||null!=picdata.getData()) {
		if(tempUri!=null){
			Log.e("tags", "<<<<<<<<2");
			photoBitmap = BitmapUtils.getBitmapFromUri(tempUri,this);
			photo.setImageBitmap(photoBitmap);
			//上传到服务器
			String filePath = FileCache.getInstance(this).addBitmapToSdCard(photoBitmap);

				SharedPreferences preferences = getSharedPreferences("App_mala", 0);
	        	String token = preferences.getString("data_token", "");
	        	String user_id = preferences.getString("data_userId", "");
					Map<String, String> param = new HashMap<String, String>();
					param.put("token", token);
					param.put("user_id", user_id);

					UploadUtil.getInstance(handler).uploadFile(filePath, "file", "URL地址", param);
				
		}else{
			
		}
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_photo:
			showGetImageDlg();
			break;
			
		case R.id.btn_camera:
			// 打开照相程序
			startCamearPicCut();
			popupWindow.dismiss();
			break;
		case R.id.btn_album: // 相册去照片
			startImageCaptrue();
			popupWindow.dismiss();
			break;
			
		case R.id.btn_cancel: // 取消拍照
			if (null != popupWindow && popupWindow.isShowing()) {
				popupWindow.dismiss();
			}
			break;	
		}
	}
	
}
