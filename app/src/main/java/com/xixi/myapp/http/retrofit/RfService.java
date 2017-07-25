package com.xixi.myapp.http.retrofit;

import com.xixi.myapp.model.bean.LoginBT;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ${xixi} on 2016/4/25.
 */
public interface RfService {
    //检查电话是否注册
    @GET("user/toCheckPhone")
    Call<Object> checkRegister(@Query("clientId") String clientId,
                               @Query("clientSecret") String clientSecret,
                               @Query("phone") String phone);

    //悦me登录
    @POST("oauth2.0/authorize?response_type=esurfingpassword")
    Observable<LoginBT> login(@HeaderMap Map<String ,String> headMap,@Body HashMap map);

//    @POST("user/list")
//    Call<Users> loadUsers();
}
