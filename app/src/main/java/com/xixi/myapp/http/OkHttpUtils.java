package com.xixi.myapp.http;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * Created by ${xixi} on 2016/4/25.
 */
public class OkHttpUtils
{
    public static long connectTimeOut = 6;
    public static long readTimeOut = 6;
    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;

    public OkHttpUtils(OkHttpClient okHttpClient)
    {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(connectTimeOut, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(readTimeOut, TimeUnit.SECONDS);
        if (okHttpClient == null)
        {
            mOkHttpClient = okHttpClientBuilder.build();
        } else
        {
            mOkHttpClient = okHttpClient;
        }
    }


    public static OkHttpUtils getInstance(OkHttpClient okHttpClient)
    {
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance()
    {
        return getInstance(null);
    }

    public OkHttpClient getOkHttpClient()
    {
        return mOkHttpClient;
    }

    /**
     * for https-way authentication
     *
     * @param certificates
     */
    public OkHttpClient httpsClient(InputStream... certificates)
    {
        return httpsClient(certificates, null, null);
    }

    /**
     * for https mutual authentication
     *
     * @param certificates
     * @param bksFile
     * @param password
     */
    public OkHttpClient httpsClient(InputStream[] certificates, InputStream bksFile, String password)
    {
        SSLSocketFactoryEx.SSLParams sslParams = SSLSocketFactoryEx.getSslSocketFactory(certificates, bksFile, password);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .hostnameVerifier(new HostnameVerifier()
                {
                    @Override
                    public boolean verify(String hostname, SSLSession session)
                    {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        return okHttpClient;
    }

//    public void setHostNameVerifier(HostnameVerifier hostNameVerifier)
//    {
//        mOkHttpClient = getOkHttpClient().newBuilder()
//                .hostnameVerifier(hostNameVerifier)
//                .build();
//    }
//    public void setCertificates(InputStream... certificates) {
//        SSLSocketFactoryEx.SSLParams sslParams = SSLSocketFactoryEx.getSslSocketFactory(certificates, null, null);
//        OkHttpClient.Builder builder = getOkHttpClient().newBuilder();
//        builder = builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
//        mOkHttpClient = builder.build();
//    }


    public void setConnectTimeout(int timeout, TimeUnit units)
    {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .connectTimeout(timeout, units)
                .build();
    }

    public void setReadTimeout(int timeout, TimeUnit units)
    {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .readTimeout(timeout, units)
                .build();
    }

    public void setWriteTimeout(int timeout, TimeUnit units)
    {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .writeTimeout(timeout, units)
                .build();
    }


//    public static class METHOD
//    {
//        public static final String HEAD = "HEAD";
//        public static final String DELETE = "DELETE";
//        public static final String PUT = "PUT";
//        public static final String PATCH = "PATCH";
//    }
}

