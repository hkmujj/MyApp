package com.xixi.myapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.xixi.myapp.R;
import com.xixi.myapp.model.common.Constant;
import com.xixi.myapp.utils.BitmapUtils;
import com.xixi.myapp.utils.LL;

import org.json.JSONObject;

import butterknife.BindView;

public class ThirdLoginActivity extends BaseActivity implements OnClickListener {

	private ImageView icon;//头像
	private TextView name;//昵称
	private TextView QQ, weixin, weibo;

	private UserInfo mInfo;
	public static Tencent mTencent;

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@Override
	public int getContentViewId() {
		return R.layout.activity_thirdlogin;
	}

	@Override
	protected void initAll(Bundle savedInstanceState) {
		init();
		data();
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				JSONObject response = (JSONObject) msg.obj;
				QQ.setText("退出登录");
				if (response.has("nickname")) {
					try {
						 name.setText(response.getString("nickname"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (msg.what == 1) {
				 Bitmap bitmap = (Bitmap)msg.obj;
				 icon.setImageBitmap(bitmap);
			}
		}

	};

	// 初始化控件
	public void init() {
		setCommonToolbar(toolbar,"第三方登录");
		QQ = (TextView) findViewById(R.id.qq);
		weixin = (TextView) findViewById(R.id.weixin);
		weibo = (TextView) findViewById(R.id.weibo);
		icon = (ImageView) findViewById(R.id.icon);
		name = (TextView) findViewById(R.id.name);

		QQ.setOnClickListener(this);
		weixin.setOnClickListener(this);
		weibo.setOnClickListener(this);
	}

	// 业务数据操作
	public void data() {
		// QQ注册
		if (mTencent == null) {
			mTencent = Tencent.createInstance(Constant.QQ_APP_ID, this);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.qq:// QQ
			QQLogin();
			break;
		case R.id.weixin:// 微信

			break;
		case R.id.weibo:// 微博

			break;

		default:
			break;
		}
	}
	
    /*QQ---START*/
	private void QQLogin() {
		if (!mTencent.isSessionValid()) {
			mTencent.login(this, "all", loginListener);
			Log.d("SDKQQAgentPref",
					"FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
		} else {
			// 退出登录
			mTencent.logout(this);
			toast("退出登录");
			QQ.setText("QQ登录");
			icon.setImageResource(R.drawable.my_photo_default);
			name.setText("");
		}
	}

	private void updateUserInfo() {
		if (mTencent != null && mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {

				@Override
				public void onError(UiError e) {

				}

				@Override
				public void onComplete(final Object response) {
					JSONObject jo = (JSONObject) response;
					LL.i("===================response===" + String.valueOf(jo));
					Message msg = new Message();
					msg.obj = response;
					msg.what = 0;
					mHandler.sendMessage(msg);
					new Thread() {

						@Override
						public void run() {
							JSONObject json = (JSONObject) response;
							if (json.has("figureurl")) {
								Bitmap bitmap = null;
								try {
									bitmap = BitmapUtils.getbitmap(json
											.getString("figureurl_qq_2"));
								} catch (Exception e) {

								}
								LL.e("bitmap == " + bitmap);
								Message msg = new Message();
								msg.obj = bitmap;
								msg.what = 1;
								mHandler.sendMessage(msg);
							}
						}

					}.start();
				}

				@Override
				public void onCancel() {

				}
			};
			mInfo = new UserInfo(this, mTencent.getQQToken());
			mInfo.getUserInfo(listener);

		}
	}

	IUiListener loginListener = new BaseUiListener() {
		@Override
		protected void doComplete(JSONObject values) {
			Log.d("SDKQQAgentPref",
					"AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
			initOpenidAndToken(values);
			updateUserInfo();

		}
	};

	public static void initOpenidAndToken(JSONObject jsonObject) {
		try {
			LL.i("jsonObject--" + jsonObject.toString());
			String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
			String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
			String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
					&& !TextUtils.isEmpty(openId)) {
				mTencent.setAccessToken(token, expires);
				mTencent.setOpenId(openId);
				LL.e("token ==" + token);
			}
		} catch (Exception e) {
		}
	}

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			if (null == response) {
				toast("返回为空登录失败");
				return;
			}
			JSONObject jsonResponse = (JSONObject) response;
			if (null != jsonResponse && jsonResponse.length() == 0) {
				toast("返回为空, 登录失败");
				return;
			}
			toast("登录成功");

			doComplete((JSONObject) response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			toast(e.errorDetail);

		}

		@Override
		public void onCancel() {
			toast("onCancel: ");

		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    LL.i("-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
	    if (requestCode == Constants.REQUEST_LOGIN ||
	    	requestCode == Constants.REQUEST_APPBAR) {
	    	Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
	    }

	    super.onActivityResult(requestCode, resultCode, data);
	}
	/*QQ---END*/
	
}
