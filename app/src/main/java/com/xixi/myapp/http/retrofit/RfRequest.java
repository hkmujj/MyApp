package com.xixi.myapp.http.retrofit;


import com.xixi.myapp.http.OkHttpUtils;
import com.xixi.myapp.model.common.Url;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by ${xixi} on 2017/3/24.
 */
public class RfRequest {


    public static String BASE_URL = Url.BASE_URL;

    private static RfRequest mInstance;
    private static OkHttpClient okHttpClient;
    private Retrofit retrofit;
    public RfService rfService;

    public RfRequest() {
        okHttpClient = OkHttpUtils.getInstance().getOkHttpClient();
        retrofit = getRetrofit(null);
        rfService = getRfService(RfService.class);
    }

    public static RfRequest getInstance()
    {
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new RfRequest();
                }
            }
        }
        return mInstance;
    }

    private Retrofit getRetrofit(OkHttpClient httpClient){
        OkHttpClient mHttpClient = null;
        if(httpClient!=null){
            mHttpClient = httpClient;
        }else{
            mHttpClient = okHttpClient;
        }

        Retrofit retrofit=  new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mHttpClient)
                    .build();
        return retrofit;
    }


    /**
     * @param serviceClass
     * @param <S>
     * @return
     */
    public  <S> S getRfService(Class<S> serviceClass) {

        return retrofit.create(serviceClass);
    }

    /**
     * 加入日志拦截
     */
    public  <S> S getRfService2(Class<S> serviceClass) {

        OkHttpClient httpClient = OkHttpUtils.getInstance().getOkHttpClient();

        OkHttpClient.Builder clientBuilder = httpClient.newBuilder();
////        clientBuilder.interceptors().clear();
////        clientBuilder.networkInterceptors().clear();
        //日志拦截打印
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(httpLoggingInterceptor);

        //统一添加头部
//        clientBuilder.addNetworkInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Interceptor.Chain chain) throws IOException {
//                    Request original = chain.request();
//                    // Request customization: add request headers
//                    Request request = original.newBuilder()
//                            .header("Authorization", Constant.header.trim())
//                            .header("Content-Type", "application/json")
//                            .method(original.method(), original.body())
//                            .build();
//                    return chain.proceed(request);
//            }
//        });

        Retrofit retrofit = getRetrofit(clientBuilder.build());
        return retrofit.create(serviceClass);
    }


//    private <T> void toSubscribe(Observable<T> o, DisposableObserver<T> s){
//        o.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(s);
//    }
}
