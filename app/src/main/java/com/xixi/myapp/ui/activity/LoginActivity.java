package com.xixi.myapp.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.xixi.myapp.R;
import com.xixi.myapp.http.AESCoder;
import com.xixi.myapp.http.retrofit.RfRequest;
import com.xixi.myapp.http.retrofit.RfService;
import com.xixi.myapp.model.bean.LoginBT;
import com.xixi.myapp.model.common.Constant;
import com.xixi.myapp.utils.LL;
import com.xixi.myapp.utils.SPKeepUtils;
import com.xixi.myapp.utils.SPUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 *  登录
 * @author xixi
 *
 */
public class LoginActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    private static final int RC_LOCATION_CONTACTS_PERM = 123;

    @BindView(R.id.phone)
    AutoCompleteTextView phone;

    @BindView(R.id.password)
    EditText mPasswordView;

    @BindView(R.id.login_form)
    View mLoginFormView;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        locationAndContactsTask();
        String user = (String) SPKeepUtils.get("userName","");
        phone.setText(user);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.login_button)
    public void login(View view){
        attemptLogin();
    }


    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    public void locationAndContactsTask() {
        String[] perms = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Have permissions, do the thing!
//            Toast.makeText(this, "TODO: Location and Contacts things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(this, "需要定位和读取存储权限",
                    RC_LOCATION_CONTACTS_PERM, perms);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        phone.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String userName = phone.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();
        SPKeepUtils.put("userName",userName);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(userName)) {
            phone.setError(getString(R.string.error_field_required));
            focusView = phone;
            cancel = true;
        } else if (!isPhoneValid(userName)) {
            phone.setError(getString(R.string.error_invalid_email));
            focusView = phone;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            login(userName,password);

        }
    }

    private void login(String userName,String password){
        showProgressDialog();
        HashMap<String, String> map = getLoginBody(userName, password);
        Map<String, String> headers = new HashMap<String,String>();
        headers.put("Content-Type","application/json");
        headers.put("Authorization",Constant.header);
        LL.i("header-- "+Constant.header);
//        Observable<LoginBT> observable =  RfRequest.getInstance().getRfService2(RfService.class).login(map);
        Observable<LoginBT> observable =  RfRequest.getInstance().getRfService2(RfService.class).login(headers,map);
        toSubscribe(observable, new DisposableObserver<LoginBT>() {
            @Override
            public void onNext(LoginBT loginBT) {
                dismissProgressDialog();
                LL.i("-onNext--"+loginBT.toString());
                SPUtils.put("access_token", loginBT.access_token);
                SPUtils.put("esurfing_token", loginBT.esurfing_token);
                SPUtils.put("expire_in", loginBT.expire_in);
                SPUtils.put("mobile_name", loginBT.mobile_name);
                SPUtils.put("userId", loginBT.userId);
                SPUtils.put("user_acct", loginBT.user_acct);
//                openActivity(HomeActivity.class);
                toast("获得Token: "+loginBT.access_token);
            }

            @Override
            public void onError(Throwable e) {
                dismissProgressDialog();
                e.printStackTrace();
                LL.i("-onError--"+e.getMessage());
            }

            @Override
            public void onComplete() {
                dismissProgressDialog();
                LL.i("-onComplete--");
            }
        });

    }

    @NonNull
    private HashMap<String, String> getLoginBody(String user, String password) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("response_type","password");
        map.put("username",user);
        map.put("password", AESCoder.encrypt(password));
        map.put("scope","");
        return map;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
    private boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic
        return phone.trim().length()==11 && phone.startsWith("1");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        phone.setAdapter(adapter);
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

//        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            new AppSettingsDialog.Builder(this).setTitle("需要权限").setRationale("你需在设置页放开权限")
//                    .build().show();
//        }
    }

}

