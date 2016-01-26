package com.itraing.part1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.itraing.basebean.BaseActivity;
import com.itraing.findmybag.R;
import com.itraing.greendao.DBHelper;
import com.itraing.greendao.DeviceBean;

public class DeviceSettingActivity extends BaseActivity implements
		OnSeekBarChangeListener {
	private String indexFlag = null;
	private ToggleButton toggleButton1 = null;
	private ToggleButton toggleButton2 = null;
	private SeekBar seekBar = null;

	private DBHelper dBManager;

	private DeviceBean deviceBean = null;

	private String UUIDString = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a6_device_setting);

		dBManager = DBHelper.getInstance(this);

		indexFlag = getIntent().getStringExtra("indexFlag");
		UUIDString = getIntent().getStringExtra("UUIDString");
		deviceBean = dBManager.loadDeviceBean(UUIDString);
		if ("2".equals(indexFlag)) {
			findViewById(R.id.device_setting_headview1)
					.setVisibility(View.GONE);
			findViewById(R.id.device_setting_headview2)
					.setVisibility(View.GONE);
		}

		toggleButton1 = (ToggleButton) findViewById(R.id.a6_device_switch_1);
		toggleButton2 = (ToggleButton) findViewById(R.id.a6_device_switch_2);
		seekBar = (SeekBar) findViewById(R.id.a6_device_seekbar_3);
		seekBar.setOnSeekBarChangeListener(this);

		allocData();

	}

	private void allocData() {

		((TextView) findViewById(R.id.a6_device_detail_nagation_tv1))
				.setText(deviceBean.getTagName());

		if ("1".equals(indexFlag)) {
			toggleButton1.setChecked(deviceBean.getWarnStatus());
			toggleButton2.setChecked(deviceBean.getWarnLight());
			seekBar.setProgress(deviceBean.getWarnVoiceLevel());

		} else {
			toggleButton2.setChecked(deviceBean.getFindLight());
			seekBar.setProgress(deviceBean.getFindVoiceLevel());
		}

	}

	public void onValueChange1(View v) {

		deviceBean.setWarnStatus(toggleButton1.isChecked());
		dBManager.update(deviceBean);

	}

	public void onValueChange2(View v) {
		
		if ("1".equals(indexFlag)) {
			deviceBean.setWarnLight(toggleButton2.isChecked());
		} else {
			deviceBean.setFindLight(toggleButton2.isChecked());
		}
		
		dBManager.update(deviceBean);
	}

	
	public void alamSettingAction(View v){
		System.out.println("=========================");
		Intent intent=new Intent(DeviceSettingActivity.this,RingSelectActivity.class);
		intent.putExtra("indexFlag", indexFlag);
		intent.putExtra("UUIDString", UUIDString);
		startActivity(intent);
	}
	
	public void backAction(View v) {
		finish();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub

		// System.out.println("-----------------------------"+progress);

		if ("1".equals(indexFlag)) {

			deviceBean.setWarnVoiceLevel(progress);

		} else {
			deviceBean.setFindVoiceLevel(progress);
		}

		dBManager.update(deviceBean);

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}
}
