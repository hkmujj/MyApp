package com.xixi.myapp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xixi.myapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindViews({R.id.web,R.id.thirdlogin,/*R.id.uploadphone,*/R.id.pullRefresh,R.id.materialDesign,R.id.mPermission,R.id.mRetrofit,R.id.mBottomNavigation})
    List<TextView> textViewList;

    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        init();
        data();
    }

    private void init(){
        toolbar.setTitle("主页");
    }

    private void data(){
    }

    @OnClick({R.id.web,R.id.thirdlogin/*,R.id.uploadphone*/,R.id.pullRefresh,R.id.materialDesign,R.id.mPermission,R.id.mRetrofit,R.id.mBottomNavigation})
    public void web(View v) {
        switch (v.getId()){
            case R.id.web://JS交互
                goActivity(WebViewActivity.class);
                break;
            case R.id.thirdlogin://三方登陆
                goActivity(ThirdLoginActivity.class);
                break;
//            case R.id.uploadphone://选择头像上传
//                goActivity(UploadPhotoActivity.class);
//                break;
            case R.id.pullRefresh://下拉刷新
                goActivity(PullRefreshActivity.class);
                break;
            case R.id.materialDesign://Material Design
                goActivity(MaterialDesignActivity.class);
                break;
            case R.id.mPermission:// Android 6.0动态权限
                goActivity(EasyPermissionsActivity.class);
                break;
            case R.id.mRetrofit:// Retrofit2.0请求
                goActivity(LoginActivity.class);
                break;
            case R.id.mBottomNavigation:// BottomNavigation
                goActivity(BottomNavigationActivity.class);
                break;
        }
    }
}
