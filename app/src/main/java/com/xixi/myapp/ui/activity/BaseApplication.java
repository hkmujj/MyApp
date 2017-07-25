package com.xixi.myapp.ui.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class BaseApplication extends Application {

	private List <WeakReference<Activity>> activityList = new LinkedList<WeakReference<Activity>>();
	
	private static BaseApplication instance;
	
	public BaseApplication() {
	}

	// 获取BaseApplication实例
	public static BaseApplication getInstance() {
		return instance;
	}
	public static Context getAppContext() {
		return instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}


	// 添加Activity到容器中
	public void addActivity(WeakReference<Activity> activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity并finish
	public void exit() {
		for (WeakReference<Activity> activity : activityList) {
//			Log.i("tags","<<<<-"+ activity+"--"+activity.get());
			if(null!=activity.get() && !activity.get().isFinishing()){
			activity.get().finish();
			}
		}
		System.exit(0);
	}	
	
	@Override  
    public void onLowMemory() {  
        super.onLowMemory();      
        System.gc();  
    }  
}
