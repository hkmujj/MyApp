package com.xixi.myapp.http;

import android.util.Log;

import com.xixi.myapp.model.common.Constant;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESCoder {
	private static final String PASSWORD = Constant.client_key;

	public static String encrypt(String content) {
		return encrypt(content, PASSWORD);

	}

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static String encrypt(String content, String password) {
		try {
			password = getMD5(password.getBytes("UTF-8")).toUpperCase();
			SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"),
					"AES");

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(content.getBytes());
			// return Hex.encodeHexString(result);
			return byte2hex(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String content) {
		return decrypt(content, PASSWORD);
	}

	public static String decrypt(String content, String password) {
		try {
			password = getMD5(password.getBytes("UTF-8")).toUpperCase();
			Log.i("tags", "=========md5loid==" + password);
			SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"),
					"AES");

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);

			byte[] result = cipher.doFinal(hex2byte(content));
			return new String(result, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转化成十六进制
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	/**
	 * 
	 * @param hexString
	 * @return 将十六进制转换为字节数组
	 */
	public static byte[] hex2byte(String hexString) {
		String hexStr = "0123456789ABCDEF";
		int len = hexString.length() / 2;
		byte[] bytes = new byte[len];
		byte high = 0;
		byte low = 0;

		for (int i = 0; i < len; i++) {
			high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
			low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
			bytes[i] = (byte) (high | low);
		}
		return bytes;
	}

	// public static void main(String[] args) {
	// String text =
	// "blxd0011                        201404171012550036BF5803D48CF215E4D7A5C0C1293C7550E8B1F54E";
	// String text1 = AESCoder.encrypt("123456"); // 加密
	// System.out.println(text1);
	// // text1 =
	// "761A9C0FCF2881EBAB785FB0EC880FD1E17DBE72082F30CB57F520909B1DC2F605241F7F2AE2E637D1D195663C4D87DEF71180EDC376FACD81518A08DEF2BD44EA58E3743788764575628D768AD63CC47BD128EB6EBA9C14C6B5FBC987DAB758";
	// System.out.println("---" + AESCoder.decrypt(text1));
	// System.out.println(AESCoder.byte2hex(AESCoder.hex2byte("DE08136CD2F22D2D04E62A2415F78805")));
	// }

	/**
	 * @param source
	 * @return 获取数据的MD5
	 */
	public static String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			// 获得MD5摘要算法的 MessageDigest 对象
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			// 使用指定的字节更新摘要
			md.update(source);
			// 获得密文
			byte tmp[] = md.digest();
			// 把密文转换成十六进制的字符串形式
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];

				str[k++] = hexDigits[byte0 & 0xf];

			}
			s = new String(str);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return s;
	}

/*	*//**
	 * 
	 * @author xingx
	 * @TODO sha1算法
	 * @TIME 2015年7月25日 上午11:17:27
	 * @param data
	 *            需要转码的数据
	 * @return 转码后的数据
	 *//*
	public static String hamcsha1(String data) {
		byte[] byteHMAC = null;
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec spec = new SecretKeySpec(
					RouterAppData.eCloudApp_Secret.getBytes(), "HmacSHA1");
			mac.init(spec);

			byteHMAC = mac.doFinal(data.getBytes());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return byte2hex(byteHMAC);
	}*/
}
