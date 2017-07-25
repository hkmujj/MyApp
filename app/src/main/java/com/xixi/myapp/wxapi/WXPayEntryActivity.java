package com.xixi.myapp.wxapi;/*package com.xixi.myapp.wxapi;

import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xixi.myapp.R;
import com.xixi.myapp.base.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler{
	
	private static final String TAG = "WXPayEntryActivity";
	public static final String WX_APP_ID = "wx7420479f552cccef";
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, WX_APP_ID);

        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.e(TAG, intent.getDataString());
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		jumpPayFailure();
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.e(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			//AlertDialog.Builder builder = new AlertDialog.Builder(this);
			//builder.setTitle(R.string.app_tip);
			//builder.setMessage(getString(R.string.pay_result_callback_msg, resp.errStr +";code=" + String.valueOf(resp.errCode)));
			//builder.show();
			switch (resp.errCode) {
			case ErrCode.ERR_OK:
				showShortToast("支付成功");
				jumpPaySuccess();
				break;
			case ErrCode.ERR_AUTH_DENIED:
			case ErrCode.ERR_COMM:
			case ErrCode.ERR_SENT_FAILED:
			case ErrCode.ERR_UNSUPPORT:
			case ErrCode.ERR_USER_CANCEL:
				showShortToast("支付失败");
				jumpPayFailure();
				break;
			default:
				jumpPayFailure();
				break;
			}
		}
	}

	private void jumpPayFailure() {
		Intent intent = new Intent();
		intent.setAction(Url.payReceiver);
		intent.putExtra("command", Url.wxPayFailure);
		sendBroadcast(intent);
		finish();
	}
	*//**
     * 跳转到支付成功
     *//*
	private void jumpPaySuccess(){
		Intent intent = new Intent();
		intent.setAction(Url.payReceiver);
		intent.putExtra("command", Url.wxPaySuccess);
		sendBroadcast(intent);
		finish();
	}

	@Override
	public int bindLayout() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doData(Context mContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResp(BaseResp arg0) {
		// TODO Auto-generated method stub
		
	}
}*/