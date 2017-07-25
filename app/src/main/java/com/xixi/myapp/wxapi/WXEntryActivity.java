package com.xixi.myapp.wxapi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.ConstantsAPI;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendAuth;
import com.tencent.mm.sdk.openapi.SendAuth.Resp;
import com.tencent.mm.sdk.openapi.SendMessageToWX;

import java.util.HashMap;

/**
 * 微信授权登陆
 * 
 * @author xijun
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

	private String message = "";
	private IWXAPI api;
	SharedPreferences mSharedPreferences;
//	UserInfoObject obj;
	Handler hand = new Handler() {
		public void handleMessage(Message msg) {
			finish();
			Toast.makeText(WXEntryActivity.this, message, 1).show();
		};
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		api = ThirdLoginManager.registerwxapi(WXEntryActivity.this);
		api.handleIntent(getIntent(), this);
		mSharedPreferences = getSharedPreferences("App_mala", 0);
	}

	@Override
	public void onReq(BaseReq arg0) {
		switch (arg0.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:

			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:

			break;
		default:
			break;
		}
	}

	@Override
	public void onResp(final BaseResp arg0) {

		// 关于数值的类型，微信官方文档里有写：
		// 发送OpenAPI Auth验证 的数值为 1
		// 分享消息到微信 的数值为2
		if (arg0.getType() == 2) {
			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = String.valueOf(System.currentTimeMillis()); 
			finish();
		}
		if (arg0.getType() == 1) {
			SendAuth.Resp sr = (Resp) arg0;
			// 获得code
//			String code = sr.code;

			switch (arg0.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				if (arg0.errCode == 0) {
					// SendAuth.Resp sr = (Resp) arg0;
//					HashMap<String, String> paramsMap = new HashMap<String, String>();
//					MalaHttpUtils.sendHttpRequest(WXEntryActivity.this, paramsMap,
//							"https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Common.WX_APP_ID + "&secret="
//									+ Common.WX_SECRET_ID + "&code=" + sr.code + "&grant_type=authorization_code",
//							null, MalaHttpUtils.HTTP_GET, new MalaHttpUtils.OnRequestListener() {
//								@Override
//								public void OnRequestStart() {
//									ProgressDialog dialog = new ProgressDialog(WXEntryActivity.this,
//											ProgressDialog.THEME_HOLO_LIGHT);
//									dialog.setMessage("正在获取用户信息,请稍后...");
//									dialog.setCanceledOnTouchOutside(false);
//									dialog.show();
//								}
//
//								@Override
//								public void OnRequestSuccess(String responseStr) {
//									try {
//										JSONObject jsonObject = new JSONObject(responseStr);
//										String access_token = jsonObject.getString("access_token");
//										String openid = jsonObject.getString("openid");
//										Editor editor = mSharedPreferences.edit();
//										editor.putString("openid", openid);
//										editor.putString("access_token", access_token);
//										editor.commit();
//										getUserInfo(access_token, openid);
//
//									} catch (JSONException e) {
//										e.printStackTrace();
//									}
//								}
//
//								@Override
//								public void onRequestFailure(String errorMsg) {
//
//								}
//
//								@Override
//								public void onRequestLoading(long total, long current, boolean isUploading) {
//
//								}
//							});
				} else {
					finish();
				}

				// if (arg0.errCode == 0) {
				//
				// // 缓冲dialog
				// // mDialog = new CProgressDialog(WXEntryActivity.this);
				// // mDialog.loadDialog();
				// ProgressDialog dialog = new
				// ProgressDialog(WXEntryActivity.this,
				// ProgressDialog.THEME_HOLO_LIGHT);
				// dialog.setMessage("正在获取用户信息,请稍后...");
				// dialog.setCanceledOnTouchOutside(false);
				// dialog.show();
				//
				// new Thread(new Runnable() {
				//
				// @Override
				// public void run() {
				// String result;
				// SendAuth.Resp sr = (SendAuth.Resp) arg0;
				//
				// NameValuePair appidValue = new BasicNameValuePair("appid",
				// Common.WX_APP_ID);
				// NameValuePair secretValue = new BasicNameValuePair("secret",
				// Common.WX_SECRET_ID);
				// NameValuePair codeValue = new BasicNameValuePair("code",
				// sr.code);
				// NameValuePair gtyValue = new BasicNameValuePair("grant_type",
				// "authorization_code");
				// try {
				// result = HttpUtil.postByHttpClient(WXEntryActivity.this,
				// "https://api.weixin.qq.com/sns/oauth2/access_token",
				// appidValue, secretValue,
				// codeValue, gtyValue);
				// if (!StringUtils.isEmpty(result)) {
				// JSONObject jsonObject = new JSONObject(result);
				// String openid = jsonObject.getString("openid");
				// String access_token = jsonObject.getString("access_token");
				// Editor editor = mSharedPreferences.edit();
				// editor.putString("openid", openid);
				// editor.putString("access_token", access_token);
				// editor.commit();
				// getUserInfo(access_token, openid);
				// }
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				// }
				// }).start();
				//
				// } else {
				// finish();
				// }

				break;
			// 取消
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				message = "已取消";
				Message mes1 = new Message();
				mes1.what = 0;
				finish();
				break;
			// 拒绝
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				finish();
				break;
			default:
				finish();
				break;
			}
		}
	}

	private void getUserInfo(String access, final String openid) {
		HashMap<String, String> paramsMap = new HashMap<String, String>();

//		MalaHttpUtils.sendHttpRequest(WXEntryActivity.this, paramsMap,
//				"https://api.weixin.qq.com/sns/userinfo?access_token=" + access + "&openid=" + openid, null,
//				MalaHttpUtils.HTTP_GET, new MalaHttpUtils.OnRequestListener() {
//					@Override
//					public void OnRequestStart() {
//
//					}
//
//					@Override
//					public void OnRequestSuccess(String result) {
//						try {
//							JSONObject js = new JSONObject(result);
//							String nickname = js.getString("nickname");
//							String headimgurl = js.getString("headimgurl");
//							obj = new UserInfoObject();
//							obj.setNick(nickname);
//							obj.setHead(headimgurl);
//							obj.setOpen_id(openid);
//							obj.setType("2");
//							ThirdLoginManager.wxLoginSucessed(obj);
//							finish();
//
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//					}
//
//					@Override
//					public void onRequestFailure(String errorMsg) {
//
//					}
//
//					@Override
//					public void onRequestLoading(long total, long current, boolean isUploading) {
//
//					}
//				});

	}

}
