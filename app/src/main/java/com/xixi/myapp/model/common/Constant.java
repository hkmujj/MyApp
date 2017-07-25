package com.xixi.myapp.model.common;


import android.util.Base64;

/**
 * @author xijun
 *  常量
 */
public class Constant {

    public static final String client_id = "1000000043397789";
    public static final String client_key = "8e5fe6076997444d";

    public static String str = client_id + ":" + client_key;
    public static String header = "Basic" + Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);

    public static final String WX_APP_ID = "wx7420479f552cccef";
    public static final String WX_SECRET_ID = "16035b1707c3dc02cd44ac3d149f0caf";
    public static final String QQ_APP_ID = "222222";//此为测试appId
    public static final String Wb_APP_KEY = "902398679";

    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read," + "follow_app_official_microblog,"
            + "invitation_write";
}
