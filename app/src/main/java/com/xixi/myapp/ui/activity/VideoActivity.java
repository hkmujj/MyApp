package com.xixi.myapp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;

import com.xixi.myapp.R;
import com.xixi.myapp.ui.view.CustomVideoView;

public class VideoActivity extends Activity{
	
	CustomVideoView videoView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
                   WindowManager.LayoutParams.FLAG_FULLSCREEN);  //去掉状态栏
		setContentView(R.layout.video);
		init();
	}

	@SuppressLint("NewApi") 
	private void init() {
		String url = getIntent().getExtras().getString("url","");
		videoView = (CustomVideoView) findViewById(R.id.videoView1);
		Uri uri = Uri.parse(url);   
        videoView.setMediaController(new MediaController(this));  
        videoView.setVideoURI(uri);  
        videoView.start();  
        videoView.requestFocus();  
	}

}
