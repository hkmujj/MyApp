
package com.xixi.myapp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.xixi.myapp.R;
import com.xixi.myapp.utils.DialogUtil;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseActivity extends AppCompatActivity {
	
	private Dialog progressDialog;
    public abstract int getContentViewId();
    protected abstract void initAll(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        //将当前Activity存起来
		BaseApplication.getInstance().addActivity(new WeakReference<Activity>(this));
        ButterKnife.bind(this);
        initAll(savedInstanceState);
    }

    public void setCommonToolbar(Toolbar toolbar, int titResId){
        setCommonToolbar(toolbar, getString(titResId));
    }
    public void setCommonToolbar(Toolbar toolbar,String tit){
        toolbar.setTitle(tit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
//		toolbar.setNavigationIcon(R.drawable.title_back);
//		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				finish();
//			}
//		});
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示dialog
     *
     */
    public void showProgressDialog() {
        try {

            if (progressDialog == null) {
                progressDialog = DialogUtil.createLoadingDialog(this);

            }
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 隐藏dialog
     */
    public void dismissProgressDialog() {
        try {

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * dialog是否显示
     */
    public boolean isShow() {
        try {

            if (progressDialog != null && progressDialog.isShowing()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更具类打开acitvity
     */
    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null, 0);

    }

    public void openActivityForResult(Class<?> pClass, int requestCode) {
        openActivity(pClass, null, requestCode);
    }

    /**
     * 更具类打开acitvity,并携带参数
     */
    public void openActivity(Class<?> pClass, Bundle pBundle, int requestCode) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (requestCode == 0) {
             startActivity(intent);
        } else {
             startActivityForResult(intent, requestCode);
        }
        finish();
    }
    
    /**
     * 更具类打开acitvity,不结束原Activity
     */
    public void goActivity(Class<?> pClass) {
    	goActivity(pClass, null, 0);

    }

    public void goActivityForResult(Class<?> pClass, int requestCode) {
    	goActivity(pClass, null, requestCode);
    }
    
    /**
     * 更具类打开acitvity,并携带参数,不结束原Activity
     */
    public void goActivity(Class<?> pClass, Bundle pBundle, int requestCode) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (requestCode == 0) {
             startActivity(intent);
        } else {
             startActivityForResult(intent, requestCode);
        }
        // actityAnim();
    }

    /**
     * 判断是否有网络
     * 
     * @return
     */
//    public boolean hasNetWork() {
//        return HttpUtil.isNetworkAvailable(this);
//    }

    public <T> void toSubscribe(Observable<T> o, DisposableObserver<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 显示LongToast
     */
    public void toast(String pMsg) {
        Toast.makeText(this, pMsg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 简化findView
     */
    public <T extends View> T findView(int id) {
        try {
            return (T) findViewById(id);
        } catch (ClassCastException ex) {
            throw ex;
        }
    }

    /**
     * 带动画效果的关闭
     */
    @Override
    public void finish() {
        super.finish();
//        actityAnim();
    }



//    public void actityAnim() {
        // overridePendingTransition(R.anim.slide_in_right,
        // R.anim.slide_right_out);
//    }

}
