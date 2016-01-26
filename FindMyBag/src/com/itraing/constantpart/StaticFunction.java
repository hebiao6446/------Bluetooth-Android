package com.itraing.constantpart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;

public class StaticFunction {

	public static final String TAG = "StaticFunction-Tag";
	public static final String APPNAME = "FindMyBag";

	public static boolean SYSTEM_CHANGE_LANGUAGE_FLAG = false;

	public static final int SYSTEM_GREEN_COLOR = Color.rgb(42, 152, 62);
	public static final int SYSTEM_GRAY_COLOR = Color.rgb(64, 64, 64);
	public static final String SYSTEM_HAVE_PASSWORD = "system_have_password";
	public static final String SYSTEM_LOCK_PASSWORD = "system_lock_password";

	public static final int PasswordOperationType = 0;
	public static final int PasswordOperationTypeOpen = 1;
	public static final int PasswordOperationTypeClose = 2;
	public static final int PasswordOperationTypeChange = 3;

	public static final String MAP_TYPE = "map_type";
	public static final int MAP_TPYE_BAIDU = 1;
	public static final int MAP_TPYE_GOOGLE = 2;

	public static final String SYSTEM_LOCALE_LANGUAGUE_STRING = "system_locale_languague_string";
	public static final String SYSTEM_LOCALE_COUNTRY_STRING = "system_locale_country_string";
	public static final String SYSTEM_REMAIN_FLAG = "system_remain_flag";
	
	 
	
	public static final String BROADCAST_START_SCAN_BLUETOOTH = "broadcast_start_scan_bluetooth";
	public static final String BROADCAST_STOP_SCAN_BLUETOOTH = "broadcast_stop_scan_bluetooth";
	public static final String BROADCAST_GET_DEVICE_LIST = "broadcast_get_device_list";
	public static final String BROADCAST_CONNECT_DEVICE_LIST = "broadcast_connect_device_list";
	public static final String BROADCAST_DISCONNECT_DEVICE_LIST = "broadcast_disconnect_device_list";
	public static final String BROADCAST_CONNECT_DEVICE_LIST_RESPONSE = "broadcast_connect_device_list_response";
	public static final String BROADCAST_DISCONNECT_DEVICE_LIST_RESPONSE = "broadcast_disconnect_device_list_response";
	public static final String ENAME[] = { "zh", "en", "fr", "de", "ja", "ko",
			"es", "pt", "ar" };

	public static boolean isSystemHavePassword(Context context) {
		SharedPreferences sharedPreferences = getCurrentSharedPreferences(context);

		return sharedPreferences.getBoolean(SYSTEM_HAVE_PASSWORD, false);
	}

	public static void saveSystemPasswordStatus(Context context,
			boolean havePassword) {
		SharedPreferences sharedPreferences = getCurrentSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(SYSTEM_HAVE_PASSWORD, havePassword);
		editor.commit();
	}

	public static String getCurrentPassword(Context context) {
		SharedPreferences sharedPreferences = getCurrentSharedPreferences(context);
		return sharedPreferences.getString(SYSTEM_LOCK_PASSWORD, "1#2#3#4#");
	}

	public static void saveCurrentPassword(Context context,
			String passwordString) {
		SharedPreferences sharedPreferences = getCurrentSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(SYSTEM_LOCK_PASSWORD, passwordString);
		editor.commit();

	}

	public static int getSystemMap(Context context) {
		SharedPreferences sharedPreferences = getCurrentSharedPreferences(context);

		return sharedPreferences.getInt(MAP_TYPE, MAP_TPYE_BAIDU);
	}

	public static void setSystemMap(Context context, int mapType) {
		SharedPreferences sharedPreferences = getCurrentSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(MAP_TYPE, mapType);
		editor.commit();

	}

	public static Locale getSystemLacate(Context context) {
		SharedPreferences sharedPreferences = getCurrentSharedPreferences(context);
		String str = sharedPreferences.getString(
				SYSTEM_LOCALE_LANGUAGUE_STRING, "no_languague");
		String strc = sharedPreferences.getString(SYSTEM_LOCALE_COUNTRY_STRING,
				"");
		if ("no_languague".equals(str)) {

			Locale l = Locale.getDefault();
			String def = "en";
			for (int i = 0; i < ENAME.length; i++) {
				if (ENAME[i].equals(l.getLanguage())) {
					def = ENAME[i];
					break;
				}
			}

			Locale nLocale = null;
			if ("zh".equals(def)) {
				if ("CN".equals(l.getCountry())) {
					nLocale = Locale.SIMPLIFIED_CHINESE;
				} else {
					nLocale = Locale.TRADITIONAL_CHINESE;
				}

			} else {
				nLocale = new Locale(def);
			}

			setSystemLacate(context, nLocale);

			return nLocale;
		}

		return new Locale(str, strc);
	}

	public static void setSystemLacate(Context context, Locale locale) {
		SharedPreferences sharedPreferences = getCurrentSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(SYSTEM_LOCALE_LANGUAGUE_STRING, locale.getLanguage());
		editor.putString(SYSTEM_LOCALE_COUNTRY_STRING, locale.getCountry());
		editor.commit();

	}

	public static void setSystemRemainFlag(Context context, boolean flag) {
		SharedPreferences sharedPreferences = getCurrentSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(SYSTEM_REMAIN_FLAG, flag);
		editor.commit();
	}

	public static boolean getSystemRemainFlag(Context context) {
		SharedPreferences sharedPreferences = getCurrentSharedPreferences(context);
		return sharedPreferences.getBoolean(SYSTEM_REMAIN_FLAG, false);

	}

	public static SharedPreferences getCurrentSharedPreferences(Context context) {

		return context.getSharedPreferences(TAG, context.MODE_PRIVATE);
	}

	public static void saveMyBitmap(String bitName, Bitmap mBitmap) {
		String path = "/sdcard/" + bitName + ".png";
		File f = new File(path);
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static Bitmap loadMyBitmap(String bitName) {
		
		if (bitName==null||bitName.trim().length()==0) {
			return null;
		}
		
		String path = "/sdcard/" + bitName + ".png";
		try {

			File file = new File(path);
			if (file.exists()) {
				return BitmapFactory.decodeFile(path);
			} else {
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;

	}

	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		;
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

}
