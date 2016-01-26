package com.itraing.broadcastreiver;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.itraing.constantpart.StaticFunction;

@SuppressLint("NewApi")
public class ItraingBluetoothService extends Service {

	private BluetoothAdapter mBluetoothAdapter;
	private BluetoothManager mBluetoothManager;
	private Map<String, BluetoothGatt> mMapBleGatt;
	private ItraingBluetoothGattCallback itraingBluetoothGattCallback;
	private ItraingLeScanCallback itraingLeScanCallback;
	 
	
	
	private Handler mHandler;
	private Handler rssiHandler;
	
	boolean mScanning;
	
	
	String connectedUUIDString;
	
	 
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		if (initialize()) {
			mMapBleGatt = new HashMap<String, BluetoothGatt>();
			itraingLeScanCallback=new ItraingLeScanCallback();
			itraingBluetoothGattCallback=new ItraingBluetoothGattCallback();
		}
		
		 mHandler = new Handler();
		 rssiHandler=new Handler();
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(StaticFunction.BROADCAST_START_SCAN_BLUETOOTH);
		filter.addAction(StaticFunction.BROADCAST_STOP_SCAN_BLUETOOTH);
		filter.addAction(StaticFunction.BROADCAST_CONNECT_DEVICE_LIST);
		filter.addAction(StaticFunction.BROADCAST_DISCONNECT_DEVICE_LIST);
		registerReceiver(broadcastReceiver, filter);
	 
		 
		connectedUUIDString="";
		
		
		super.onCreate();
	}
	
	 
	
	public void startFindDevice(){
	 
		scanLeDevice(true);
		
	 
	}
	
	public void stopFindDevice(){
		
		scanLeDevice(false);
		
	}
	
	
	private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(itraingLeScanCallback);
                }
            }, 10000);

            mScanning = true;
            mBluetoothAdapter.startLeScan(itraingLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(itraingLeScanCallback);
        }
    }

	public boolean initialize() {
		if (mBluetoothManager == null) {
			mBluetoothManager = (BluetoothManager) getSystemService("bluetooth");
			mBluetoothAdapter = mBluetoothManager.getAdapter();
			if (mBluetoothManager == null) {
				Log.e("Log.d(StaticFunction.APPNAME,",
						"Unable to initialize BluetoothManager.");
				return false;
			}
		}
		mBluetoothAdapter = mBluetoothManager.getAdapter();
		return (mBluetoothAdapter != null);

	}

	public boolean closeDevice(String address) {
		BluetoothGatt gatt = (BluetoothGatt) mMapBleGatt.get(address);
		if (gatt != null) {
			gatt.close();
		}
		mMapBleGatt.remove(address);
		
		return true;
	}
	
	
	
	 
	
	
	public void startReadRemoteRssi(String address){
		if ((mBluetoothAdapter == null) || (address == null)||address.length()==0) {
			Log.w("BleService---------->",
					"BluetoothAdapter not initialized or unspecified address.");
			return ;
		}
		BluetoothGatt gatt = (BluetoothGatt) mMapBleGatt.get(address);
		if (gatt != null) {
			gatt.close();
		}
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		if (device == null) {
			Log.w(StaticFunction.APPNAME,
					"Device not found.  Unable to connect.");
			return ;
		}
		gatt = device.connectGatt(this, false, itraingBluetoothGattCallback);
		gatt.readRemoteRssi();
		 
		mMapBleGatt.put(address, gatt);
	}
	
	
	final Runnable runnable = new Runnable() { 
		@Override
		public void run() {
		// TODO Auto-generated method stub
//		startReadRemoteRssi(connectedUUIDString);
//		rssiHandler.postDelayed(this, 1000);
		}
	};
	 
	

	public boolean connect(String address) {
		
		
		stopFindDevice();
		
		
		
		if ((mBluetoothAdapter == null) || (address == null)) {
			Log.w("BleService---------->",
					"BluetoothAdapter not initialized or unspecified address.");
			return false;
		}
		BluetoothGatt gatt = (BluetoothGatt) mMapBleGatt.get(address);
		if (gatt != null) {
			gatt.close();
		}
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		if (device == null) {
			Log.w(StaticFunction.APPNAME,
					"Device not found.  Unable to connect.");
			return false;
		}
		gatt = device.connectGatt(this, false,itraingBluetoothGattCallback);
		
		
		
		mMapBleGatt.put(address, gatt);
		return true;
	}

	 

	 
	 
	
	public class ItraingLeScanCallback implements BluetoothAdapter.LeScanCallback {


		@Override
		public void onLeScan(BluetoothDevice device, int rssi, byte[] arg2) {
			// TODO Auto-generated method stub
			
			
			if (device.getAddress()!=null) {
				Intent intent = new Intent();
				intent.setAction(StaticFunction.BROADCAST_GET_DEVICE_LIST);
				intent.putExtra("address", device.getAddress());
				intent.putExtra("name", device.getName());
				sendBroadcast(intent);
				
			}
			
			
			
			
			 
		}

	}


	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		 

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			
			Log.d(StaticFunction.APPNAME, "======================~~~~~~~~~~!!!!!!!!!!!!"+action);
			
		 
 
			if (StaticFunction.BROADCAST_START_SCAN_BLUETOOTH
					.equalsIgnoreCase(action)) {
				
				
				startFindDevice();

			} else if (StaticFunction.BROADCAST_STOP_SCAN_BLUETOOTH
					.equalsIgnoreCase(action)) {
				stopFindDevice();
			} else if (StaticFunction.BROADCAST_CONNECT_DEVICE_LIST
					.equalsIgnoreCase(action)) {

				String UUIDString = intent.getStringExtra("UUIDString");
				boolean b=connect(UUIDString);
				
				
				Intent intentc = new Intent();
				intentc.putExtra("UUIDString", UUIDString);
				intentc.putExtra("connected", b);
				intentc.setAction(StaticFunction.BROADCAST_CONNECT_DEVICE_LIST_RESPONSE);
				sendBroadcast(intentc);
				
				connectedUUIDString=UUIDString;
				
				
				rssiHandler.postDelayed(runnable, 1000);
				 
			} else if (StaticFunction.BROADCAST_DISCONNECT_DEVICE_LIST
					.equalsIgnoreCase(action)) {

				String UUIDString = intent.getStringExtra("UUIDString");
				
				Intent intentc = new Intent();
				intentc.putExtra("UUIDString", UUIDString);
				intentc.setAction(StaticFunction.BROADCAST_DISCONNECT_DEVICE_LIST_RESPONSE);
				sendBroadcast(intentc);

				 
			}
		 
		}

			
			
	};
	 
	 
	
}
