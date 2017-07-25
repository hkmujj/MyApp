package com.xixi.myapp.utils;

import android.os.Handler;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;


public class UploadUtil {
	private static UploadUtil uploadUtil;
	private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
																			// 随机生成
	private static final String PREFIX = "--";
	private static final String LINE_END = "\r\n";
	private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
	public static Boolean judgeSeccuss = false;
	private Handler handler;
	private UploadUtil(Handler handler) {
		this.handler = handler;
	}

	/**
	 * 单例模式获取上传工具类
	 * 
	 * @return
	 */
	public static UploadUtil getInstance(Handler handler) {
		
		if (null == uploadUtil) {
			uploadUtil = new UploadUtil(handler);
		}
		return uploadUtil;
	}

	private static final String TAG = "UploadUtil";
	private int readTimeOut = 10 * 1000; // 读取超时
	private int connectTimeout = 10 * 1000; // 超时时间
	private static int requestTime = 0; // 请求使用多长时间
	private static final String CHARSET = "utf-8"; // 设置编码
	public static final int UPLOAD_SUCCESS_CODE = 1; // 上传成功
	// 文件不存在
	public static final int UPLOAD_FILE_NOT_EXISTS_CODE = 2;
	// 服务器出错
	public static final int UPLOAD_SERVER_ERROR_CODE = 3;
	protected static final int WHAT_TO_UPLOAD = 1;
	protected static final int WHAT_UPLOAD_DONE = 2;

	/**
	 * android上传文件到服务器
	 * 
	 * @param filePath
	 *            需要上传的文件的路径
	 * @param fileKey
	 *            在网页上<input type=file name=xxx/> xxx就是这里的fileKey
	 * @param RequestURL
	 *            请求的URL
	 */
	public void uploadFile(String filePath, String fileKey, String RequestURL,
			Map<String, String> param) {
		if (filePath == null) {
			Log.e(TAG, "文件不存在");
			return;
		}
		try {
			File file = new File(filePath);
			uploadFile(file, fileKey, RequestURL, param);
		} catch (Exception e) {
			Log.e(TAG, "文件不存在");
			e.printStackTrace();
			return;
		}
	}

	/**
	 * android上传文件到服务器
	 * 
	 * @param file
	 *            需要上传的文件
	 * @param fileKey
	 *            在网页上<input type=file name=xxx/> xxx就是这里的fileKey
	 * @param RequestURL
	 *            请求的URL
	 */
	public void uploadFile(final File file, final String fileKey,
			final String RequestURL, final Map<String, String> param) {
		if (file == null || (!file.exists())) {
			Log.e(TAG, "文件不存在");
			return;
		}

		Log.i(TAG, "请求的URL=" + RequestURL);
		Log.i(TAG, "请求的fileName=" + file.getName());
		Log.i(TAG, "请求的fileKey=" + fileKey);
		new Thread(new Runnable() { // 开启线程上传文件

					public void run() {
						toUploadFile(file, fileKey, RequestURL, param);
					}
				}).start();

	}

	private void toUploadFile(File file, String fileKey, String RequestURL,
			Map<String, String> param) {
		String result = null;
		requestTime = 0;

		long requestTime = System.currentTimeMillis();
		long responseTime = 0;

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(readTimeOut);
			conn.setConnectTimeout(connectTimeout);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);
			// conn.setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded");

			// 当文件不为空，把文件包装并且上传
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			StringBuffer sb = null;
			String params = "";

			/***
			 * 以下是用于上传参数
			 */
			if (param != null && param.size() > 0) {
				Iterator<String> it = param.keySet().iterator();
				while (it.hasNext()) {
					sb = null;
					sb = new StringBuffer();
					String key = it.next();
					String value = param.get(key);
					sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
					sb.append("Content-Disposition: form-data; name=\"")
							.append(key).append("\"").append(LINE_END)
							.append(LINE_END);
					sb.append(value).append(LINE_END);
					params = sb.toString();
					Log.i(TAG, key + "=" + params + "##");
					dos.write(params.getBytes());
					// dos.flush();
				}
			}

			sb = null;
			params = null;
			sb = new StringBuffer();
			/**
			 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件  (file)
			 * filename是文件的名字，包含后缀名的 比如:abc.png
			 */
			sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
			sb.append("Content-Disposition:form-data; name=\"" + fileKey
					+ "\"; filename=\"" + file.getName() + "\"" + LINE_END);
			sb.append("Content-Type:image/pjpeg" + LINE_END); // 这里配置的Content-type很重要的
																// ，用于服务器端辨别文件的类型的
			sb.append(LINE_END);
//			Log.i(TAG,"###--"+sb.toString());
			params = sb.toString();
			sb = null;
			Log.i(TAG,"###--"+params);
			dos.write(params.getBytes());
			/** 上传文件 */
			InputStream is = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			int len = 0;
			int curLen = 0;
			while ((len = is.read(bytes)) != -1) {
				curLen += len;
				dos.write(bytes, 0, len);
			}
			is.close();

			dos.write(LINE_END.getBytes());
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
					.getBytes();
			dos.write(end_data);
			dos.flush();
			// dos.write(tempOutputStream.toByteArray());

			// 获取响应码 200=成功 当响应成功，获取响应的流
			int res = conn.getResponseCode();
			responseTime = System.currentTimeMillis();
			this.requestTime = (int) ((responseTime - requestTime) / 1000);
			Log.e(TAG, "response code:" + res);
			if (res == 200) {
				Log.e(TAG, "request success");
				InputStream input = conn.getInputStream();
				StringBuffer sb1 = new StringBuffer();
				int ss;
				while ((ss = input.read()) != -1) {
					sb1.append((char) ss);
				}
				result = sb1.toString();
//				if(!StringUtils.checkNull(result)){
//					Js
//				}
				Log.e(TAG, "上传结果：" + result);
				judgeSeccuss = true;
//				Message msg = handler.obtainMessage();
//				msg.what = Activity_My.GET_PHOTO;
//				msg.obj = result;				
//				handler.sendMessage(msg);
				return;
			} else {
				Log.e(TAG, "request error");
				Log.e(TAG, "上传失败：code=" + res);
				judgeSeccuss = false;
//				handler.sendEmptyMessage(Activity_My.GET_PHOTO_Failure);
				return;
			}
		} catch (MalformedURLException e) {
			Log.e(TAG,"上传失败：error=" + e.getMessage());
			e.printStackTrace();
			return;
		} catch (IOException e) {
			Log.e(TAG,"上传失败：error=" + e.getMessage());
			e.printStackTrace();
			return;
		}
	}
}
