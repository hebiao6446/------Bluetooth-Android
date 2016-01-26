package com.itraing.findmybag;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.baidu.mapapi.SDKInitializer;
import com.itraing.broadcastreiver.BaiduSDKReceiver;
import com.itraing.constantpart.StaticFunction;
import com.itraing.greendao.DBHelper;
import com.itraing.part1.DeviceListActivity;
import com.itraing.part2.StopWarnActivity;
import com.itraing.part3.OnLocationActivity;
import com.itraing.part4.SettingViewActivity;

@SuppressLint("InlinedApi")
@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	public static TabHost mTabHost;

	public static TabHost getmTabHost() {
		return mTabHost;
	}

	private RadioGroup main_radio;
	private RadioButton tab_1, tab_2, tab_3, tab_4;

	BaiduSDKReceiver baiduSDKReceiver = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		reloadLanguageAction();
		setContentView(R.layout.host);

		DBHelper.getInstance(this).createAllTable();

		mTabHost = getTabHost();
		final TabWidget tabWidget = mTabHost.getTabWidget();
		tabWidget.setStripEnabled(false);// 圆角边线不启用
		// 添加n个tab选项卡，定义他们的tab名，指示名，目标屏对应的类 import com.itraing.part1.;
		mTabHost.addTab(mTabHost.newTabSpec("TAG1").setIndicator("0")
				.setContent(new Intent(this, DeviceListActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("TAG2").setIndicator("1")
				.setContent(new Intent(this, StopWarnActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("TAG3").setIndicator("2")
				.setContent(new Intent(this, OnLocationActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("TAG4").setIndicator("3")
				.setContent(new Intent(this, SettingViewActivity.class)));
		// 视觉上,用单选按钮替代TabWidget
		main_radio = (RadioGroup) findViewById(R.id.main_radio);
		tab_1 = (RadioButton) findViewById(R.id.tab_1);
		tab_2 = (RadioButton) findViewById(R.id.tab_2);
		tab_3 = (RadioButton) findViewById(R.id.tab_3);
		tab_4 = (RadioButton) findViewById(R.id.tab_4);
		main_radio
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int id) {
						if (id == tab_1.getId()) {
							mTabHost.setCurrentTab(0);
						} else if (id == tab_2.getId()) {
							mTabHost.setCurrentTab(1);
						} else if (id == tab_3.getId()) {
							mTabHost.setCurrentTab(2);
						} else if (id == tab_4.getId()) {
							mTabHost.setCurrentTab(3);
						}
					}
				});

		// 设置当前显示哪一个标签
		mTabHost.setCurrentTab(0);
		// 遍历tabWidget每个标签，设置背景图片 无
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			View vv = tabWidget.getChildAt(i);
			vv.getLayoutParams().height = 40;
			// vv.getLayoutParams().width = 65;
			vv.setBackgroundDrawable(null);
		}
		// findViewById(R.id.tab_icon_brand).setOnClickListener(this);

		addIntentFilterBaidu();
		checkPassword();
	}

	private void reloadLanguageAction() {
		Locale locale = StaticFunction.getSystemLacate(MainActivity.this);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config, null);
		getBaseContext().getResources().flushLayoutCache();

	}

	private void checkPassword() {

		if (StaticFunction.SYSTEM_CHANGE_LANGUAGE_FLAG) {
			StaticFunction.SYSTEM_CHANGE_LANGUAGE_FLAG = false;
			return;
		}

		if (StaticFunction.isSystemHavePassword(MainActivity.this)) {
			Intent intent = new Intent(MainActivity.this,
					UnLockViewActivity.class);
			startActivity(intent);
		}
	}

	private void addIntentFilterBaidu() {
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		baiduSDKReceiver = new BaiduSDKReceiver();
		registerReceiver(baiduSDKReceiver, iFilter);
	}
	/*
	 * private LinearLayout container = null;
	 * 
	 * @SuppressWarnings("rawtypes") private List<Class> listActivity;
	 * 
	 * private List<ImageView> listImageView = null; private List<TextView>
	 * listTextView = null;
	 * 
	 * BaiduSDKReceiver baiduSDKReceiver = null;
	 * 
	 * private int[] unselectImage = { R.drawable.a1_tabbar1,
	 * R.drawable.a2_tabbar2, R.drawable.a3_tabbar3, R.drawable.a4_tabbar4 };
	 * private int[] selectImage = { R.drawable.a5_tabbar5,
	 * R.drawable.a6_tabbar6, R.drawable.a7_tabbar7, R.drawable.a8_tabbar8 };
	 * 
	 * 
	 * @SuppressWarnings("rawtypes")
	 * 
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState);
	 * 
	 * 
	 * reloadLanguageAction(); setContentView(R.layout.activity_main);
	 * 
	 * DBHelper.getInstance(this).createAllTable();
	 * 
	 * container = (LinearLayout) findViewById(R.id.main_container);
	 * listActivity = new ArrayList<Class>();
	 * listActivity.add(DeviceListActivity.class);
	 * listActivity.add(StopWarnActivity.class);
	 * listActivity.add(OnLocationActivity.class);
	 * listActivity.add(SettingViewActivity.class);
	 * 
	 * listImageView = new ArrayList<ImageView>(); listImageView.add((ImageView)
	 * findViewById(R.id.main_tabbar1_img)); listImageView.add((ImageView)
	 * findViewById(R.id.main_tabbar2_img)); listImageView.add((ImageView)
	 * findViewById(R.id.main_tabbar3_img)); listImageView.add((ImageView)
	 * findViewById(R.id.main_tabbar4_img));
	 * 
	 * listTextView = new ArrayList<TextView>(); listTextView.add((TextView)
	 * findViewById(R.id.main_tabbar1_txt)); listTextView.add((TextView)
	 * findViewById(R.id.main_tabbar2_txt)); listTextView.add((TextView)
	 * findViewById(R.id.main_tabbar3_txt)); listTextView.add((TextView)
	 * findViewById(R.id.main_tabbar4_txt));
	 * 
	 * launchActivity(listActivity.get(0));
	 * 
	 * addIntentFilterBaidu(); checkPassword(); } private void
	 * reloadLanguageAction(){ Locale locale =
	 * StaticFunction.getSystemLacate(MainActivity.this);
	 * Locale.setDefault(locale); Configuration config = new Configuration();
	 * config.locale = locale;
	 * getBaseContext().getResources().updateConfiguration(config, null);
	 * getBaseContext().getResources().flushLayoutCache();
	 * 
	 * }
	 * 
	 * 
	 * 
	 * private void checkPassword(){
	 * 
	 * if (StaticFunction.SYSTEM_CHANGE_LANGUAGE_FLAG) {
	 * StaticFunction.SYSTEM_CHANGE_LANGUAGE_FLAG=false; return; }
	 * 
	 * if (StaticFunction.isSystemHavePassword(MainActivity.this)) { Intent
	 * intent = new Intent(MainActivity.this, UnLockViewActivity.class);
	 * startActivity(intent); } }
	 * 
	 * private void addIntentFilterBaidu() { IntentFilter iFilter = new
	 * IntentFilter(); iFilter.addAction(SDKInitializer.
	 * SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
	 * iFilter.addAction(SDKInitializer
	 * .SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR); baiduSDKReceiver = new
	 * BaiduSDKReceiver(); registerReceiver(baiduSDKReceiver, iFilter); }
	 * 
	 * 
	 * 
	 * private void addIntentFilterLockScreen() { IntentFilter filter = new
	 * IntentFilter(); filter.addAction(Intent.ACTION_SCREEN_ON);
	 * filter.addAction(Intent.ACTION_SCREEN_OFF);
	 * filter.addAction(Intent.ACTION_USER_PRESENT);
	 * filter.addAction(Intent.ACTION_USER_BACKGROUND);
	 * filter.addAction(Intent.ACTION_USER_FOREGROUND);
	 * registerReceiver(mScreenReceiver, filter); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @Override protected void onRestart() { // TODO Auto-generated method stub
	 * super.onRestart(); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @Override protected void onResume() { // TODO Auto-generated method stub
	 * super.onResume();
	 * 
	 * }
	 * 
	 * @Override protected void onDestroy() { super.onDestroy(); // 取消监听 SDK 广播
	 * unregisterReceiver(baiduSDKReceiver); }
	 * 
	 * private void launchActivity(Class<?> activityClass) { Intent intent = new
	 * Intent(MainActivity.this, activityClass); container.removeAllViews();
	 * intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); Window subActivity =
	 * getLocalActivityManager().startActivity( "subActivity", intent);
	 * container.addView(subActivity.getDecorView(), LayoutParams.MATCH_PARENT,
	 * LayoutParams.MATCH_PARENT);
	 * 
	 * }
	 * 
	 * public void tabbarClick(View v) {
	 * 
	 * int index = Integer.parseInt((String) v.getTag()) - 1;
	 * 
	 * for (int i = 0; i < listTextView.size(); i++) { TextView t =
	 * listTextView.get(i);
	 * 
	 * if (i == index) { t.setTextColor(StaticFunction.SYSTEM_GREEN_COLOR); }
	 * else { t.setTextColor(StaticFunction.SYSTEM_GRAY_COLOR); } }
	 * 
	 * for (int i = 0; i < listImageView.size(); i++) { ImageView img =
	 * listImageView.get(i); if (index == i) {
	 * img.setImageResource(selectImage[i]);
	 * 
	 * } else { img.setImageResource(unselectImage[i]); }
	 * 
	 * }
	 * 
	 * launchActivity(listActivity.get(index));
	 * 
	 * }
	 * 
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.main, menu); return true; }
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
	 * action bar item clicks here. The action bar will // automatically handle
	 * clicks on the Home/Up button, so long // as you specify a parent activity
	 * in AndroidManifest.xml. // int id = item.getItemId(); // if (id ==
	 * R.id.action_settings) { // return true; // } return
	 * super.onOptionsItemSelected(item); }
	 */

	/*
	 * private class ScreenBroadcastReceiver extends BroadcastReceiver { private
	 * String action = null;
	 * 
	 * @Override public void onReceive(Context context, Intent intent) { // TODO
	 * Auto-generated method stub
	 * 
	 * action = intent.getAction(); if (Intent.ACTION_SCREEN_ON.equals(action))
	 * { // 开屏
	 * 
	 * System.out
	 * .println("============================================================" +
	 * "99999999999999999");
	 * 
	 * } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
	 * 
	 * System.out .println("]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]" +
	 * "99999999999999999");
	 * 
	 * } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
	 * 
	 * System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +
	 * "99999999999999999"); } else if
	 * (Intent.ACTION_USER_BACKGROUND.equals(action)) {
	 * System.out.println("||||||||||||||||||||||||||||||||||||||" +
	 * "99999999999999999"); } else if
	 * (Intent.ACTION_USER_FOREGROUND.equals(action)) {
	 * System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~" + "99999999999999999"); }
	 * 
	 * } }
	 */

}
