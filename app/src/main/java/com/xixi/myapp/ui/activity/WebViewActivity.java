package com.xixi.myapp.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.xixi.myapp.R;
import com.xixi.myapp.utils.LL;

import butterknife.BindView;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {
	@BindView(R.id.webview)
	WebView webview;
	@BindView(R.id.top_head_container)
	RelativeLayout top_head_container;

	@Override
	public int getContentViewId() {
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
//			getWindow().addFlags(
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		return R.layout.activity_webview;
	}

	@Override
	protected void initAll(Bundle savedInstanceState) {
		init();
		data();
	}

	//初始化控件
	public void init() {

		WebSettings settings = webview.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);

		settings.setAllowFileAccess(true);
		settings.setBuiltInZoomControls(true);
		settings.setDomStorageEnabled(true);

		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setSupportZoom(true);
		settings.setDisplayZoomControls(false);

		// 从assets目录下面的加载html  
		webview.loadUrl("file:///android_asset/aaaa.html");
		
//		webview.loadUrl("http://kfb.mobi/app.html");
		webview.addJavascriptInterface(new JavaScriptInterface(), "toandroid");
		webview.setWebViewClient(new WebViewClient());
//		webView.loadUrl("javascript:ToMyApp()");
		  /*** 
         *webChromeClient是一个比较神奇的东西，其里面提供了一系列的方法， 
         *分别作用于我们的javascript代码调用特定方法时执行，我们一般在其内部 
         *将javascript形式的展示切换为android的形式。  
         * 例如：我们重写了onJsAlert方法，那么当页面中需要弹出alert窗口时，便 
         * 会执行我们的代码。 
         */  
 
		webview.setWebChromeClient(new MyWebChromeClient());  
	}

	private	class MyWebChromeClient extends WebChromeClient {		
		
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			toast(message); 
//			return super.onJsAlert(view, url, message, result);
			return false;
		}		
	}
	
	class JavaScriptInterface {

		JavaScriptInterface() {
		}
		  @JavascriptInterface
		  public void startFunction(String str) {  
			  LL.e("tags", "********"+str);
//		        runOnUiThread(new Runnable() {  
//		  
//		            @Override  
//		            public void run() {  
//		            	textView1.setText(str);  
//		            }  
//		        }); 
			  Bundle bundle = new Bundle();
			  bundle.putString("url", str);
		      goActivity(VideoActivity.class, bundle, 0);
		
		    } 
		}
	

	//业务数据操作
	public void data() {
		
		
	}

	@OnClick(R.id.top_head_container)
	public void top_head_container(View v) {
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK&&webview!=null){
			if(webview.canGoBack()){
//				finish();
				webview.goBack();
				return true;
			}else{
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
