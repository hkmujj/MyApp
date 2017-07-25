package com.xixi.myapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileCache {
	private File cacheDir;
	private static FileCache instance;
	public final String imgCacheDir = "imgCacheDir/";
	private static String cachePath = Environment.getExternalStorageDirectory()+"/xixi/imgCacheDir/";
	private static String cachePath2 = Environment.getExternalStorageDirectory()+"/xixi/uploadImg/";
//	public static FileCache getInstance() {
//		return getInstance(MyApplication.getApp());
//	}
	
	public static FileCache getInstance(Context context) {
		if (null == instance)
			instance = new FileCache(context);
		return instance;
	}

	/**
	 * 优先存放SD卡揯目录 其次系统默认缓存
	 * 
	 * @param context
	 */
	private FileCache(Context context) {
		if (hasSDCard()) {
			
			cacheDir = createFilePath(cachePath);

		} else {
			cacheDir = createFilePath(context.getCacheDir() + imgCacheDir);
		}
	}
	
	/**
	 * 初始化检查SD卡
	 */
	public boolean hasSDCard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 初始化路径
	 * 
	 * @param filePath
	 * @return boolean
	 */
	private File createFilePath(String filePath) {
		return createFilePath(new File(filePath));
	}
	
	private File createFilePath(File filejia) {
		if (!filejia.exists()) {
			filejia.mkdirs();// 按照文件夹路径创建文件夹
		}
		return filejia;
	}
	
	/**
	 * 保存Bitmap到SdCard，返回本地文件路径，不插入记录到数据库
	 * @return String
	 */
	public String addBitmapToSdCard(InputStream is){
		if (is == null) {
			return null;
		}
		Bitmap bitmap = BitmapFactory.decodeStream(is);
		return addBitmapToSdCard(bitmap);
	}
	
	/**
	 * 自定义Uri(ImageUtils.imageUriFromCamera),用于保存拍照后图S片地址
	 * @return uri
	 */
	public Uri createImagePathUri(){
		Uri uri = null;
		createFilePath(cacheDir);
		File bitmapFile = new File(cacheDir, getPhotoFileName());
		uri = Uri.fromFile(bitmapFile);
		return uri;
	}
	
	// 使用系统当前日期加以调整作为照片的名称
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}
	
	/**
	 * 保存Bitmap到SdCard，返回本地文件路径，不插入记录到数据库
	 * @param bitmap
	 * @return String
	 */
	public String addBitmapToSdCard(Bitmap bitmap){
		if (bitmap == null) {
			return null;
		}
		//若图片太大，反应慢，此处放线程里
		try {
//			String fileName = System.currentTimeMillis() + ".jpg";
//			// 创建文件夹
//			createFilePath(cacheDir);
//			File bitmapFile = new File(cacheDir, fileName);
//			bitmapFile.createNewFile();
			File upload = createFilePath(cachePath2);
			File bitmapFile = new File(upload,getPhotoFileName());
//			if(!bitmapFile.exists()){
//				bitmapFile.createNewFile();
//			}
			Log.e("dawn", "file.path"+bitmapFile.getAbsolutePath());
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(bitmapFile));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();

			String filePath = bitmapFile.getAbsolutePath();
			return filePath;
		} catch (IOException e) {
			Log.e("", "FileCache中：保存图片错误-------为：" + e);
		}
		return null;
	}

	
}