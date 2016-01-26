package com.itraing.broadcastreiver;


import com.baidu.mapapi.SDKInitializer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BaiduSDKReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String s = intent.getAction();
		if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
			System.out.println("key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
		} else if (s
				.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
			System.out.println("网络出错");
		}
	}

}
