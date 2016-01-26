package com.itraing.part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.itraing.adpter.DeviceListAdapter;
import com.itraing.adpter.DeviceListAdapter.DeviceListAdapterListener;
import com.itraing.basebean.BaseActivity;
import com.itraing.constantpart.StaticFunction;
import com.itraing.findmybag.R;
import com.itraing.greendao.DBHelper;
import com.itraing.greendao.DeviceBean;

public class DeviceListActivity extends BaseActivity implements
		OnItemClickListener, OnItemLongClickListener,
		DeviceListAdapterListener {

	private DBHelper dBManager;

	  private List<String> deviceList;
	  
	  private List<String> connectedDeviceList;

	private ListView listView;
	private DeviceListAdapter deviceListAdapter;
	
	
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a1_device_list);

		dBManager = DBHelper.getInstance(this);
		
		
		addDeviceListFilter();
		

		Intent intent = new Intent(this,
				com.itraing.broadcastreiver.ItraingBluetoothService.class);
		startService(intent);

		

		 deviceList = new ArrayList<String>();
		 connectedDeviceList=new ArrayList<String>();
		/*
		 * for (int i = 0; i < 20; i++) { deviceList.add(new DeviceBean()); }
		 */

		deviceListAdapter = new DeviceListAdapter(DeviceListActivity.this,
				deviceList,connectedDeviceList);
		deviceListAdapter.setOnDeviceListAdapterListener(this);

		listView = (ListView) findViewById(R.id.device_listview_2);
		listView.setDivider(new ColorDrawable(Color.LTGRAY));
		listView.setDividerHeight(1);
		listView.setAdapter(deviceListAdapter);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);

	}
	
	
	private void addDeviceListFilter(){
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(StaticFunction.BROADCAST_GET_DEVICE_LIST);
		filter.addAction(StaticFunction.BROADCAST_CONNECT_DEVICE_LIST_RESPONSE);
		filter.addAction(StaticFunction.BROADCAST_DISCONNECT_DEVICE_LIST_RESPONSE);
		registerReceiver(broadcastReceiver, filter);
		
		
	}
	BroadcastReceiver broadcastReceiver=new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (StaticFunction.BROADCAST_GET_DEVICE_LIST
					.equalsIgnoreCase(action)) {
				
				String UUIDString = intent.getStringExtra("address");
				String iTagName = intent.getStringExtra("name");
				if (!deviceList.contains(UUIDString)) {
//					addDeviceBean(UUIDString, iTagName);
					deviceList.add(UUIDString);
					deviceListAdapter.notifyDataSetChanged();
				}
			}else if (StaticFunction.BROADCAST_CONNECT_DEVICE_LIST_RESPONSE
					.equalsIgnoreCase(action)) {
				
				String UUIDString = intent.getStringExtra("UUIDString");
				boolean connect=intent.getBooleanExtra("connected", true);
				
				
				if (connect&&!connectedDeviceList.contains(UUIDString)) {
					connectedDeviceList.add(UUIDString);
				}
				
				deviceListAdapter.notifyDataSetChanged();
				
				
			}else if (StaticFunction.BROADCAST_DISCONNECT_DEVICE_LIST_RESPONSE
					.equalsIgnoreCase(action)) {
				String UUIDString = intent.getStringExtra("UUIDString");
				if (connectedDeviceList.contains(UUIDString)) {
					connectedDeviceList.remove(UUIDString);
				}
				deviceListAdapter.notifyDataSetChanged();
			}
		}
		
	};
	
	 
	private void addDeviceBean(String UUIDString, String iTagName) {
		DeviceBean deviceBean = new DeviceBean();
		deviceBean.setUUIDString(UUIDString);
		deviceBean.setTagName(iTagName);
		deviceBean.setImageName("");
		deviceBean.setWarnVoiceLevel(5);
		deviceBean.setFindVoiceLevel(5);
		deviceBean.setWarnLight(true);
		deviceBean.setFindLight(true);
		deviceBean.setWarnVoice("alarm_bird");
		deviceBean.setFindVoice("alarm_bird");
		deviceBean.setWarnStatus(true);
		dBManager.saveDeviceBean(deviceBean);
	}

	@SuppressLint("NewApi")
	public void searchAction(View v) {
		
		Intent intent = new Intent();
		intent.setAction(StaticFunction.BROADCAST_START_SCAN_BLUETOOTH);
		sendBroadcast(intent);
	}

 

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String UUIDString = deviceList.get(position);

		Intent intent = new Intent();
		intent.setClass(DeviceListActivity.this, DeviceDetailActivity.class);
		intent.putExtra("UUIDString", UUIDString);
		startActivity(intent);

	}

	@Override
	public void deviceListCellDidConnectButtonClick(int index) {
		
		
		Log.d(StaticFunction.APPNAME, "deviceListCellDidConnectButtonClick ---------"+index);
		String UUIDString = deviceList.get(index);
		Intent intent = new Intent();
		intent.putExtra("UUIDString", UUIDString);
		intent.setAction(StaticFunction.BROADCAST_CONNECT_DEVICE_LIST);
		sendBroadcast(intent);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub

		System.err.println("---------------" + position);
		return false;
	}

 

}
