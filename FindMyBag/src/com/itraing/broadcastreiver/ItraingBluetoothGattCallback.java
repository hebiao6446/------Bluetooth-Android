package com.itraing.broadcastreiver;

import java.util.Timer;
import java.util.TimerTask;

import com.itraing.constantpart.StaticFunction;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

public class ItraingBluetoothGattCallback extends BluetoothGattCallback {

	
	Timer mRssiTimer;
	@Override
	public void onCharacteristicChanged(BluetoothGatt gatt,
			BluetoothGattCharacteristic characteristic) {
		// TODO Auto-generated method stub
		super.onCharacteristicChanged(gatt, characteristic);
		
		Log.d(StaticFunction.APPNAME, "=========================  onCharacteristicChanged");
	}

	@Override
	public void onCharacteristicRead(BluetoothGatt gatt,
			BluetoothGattCharacteristic characteristic, int status) {
		// TODO Auto-generated method stub
		super.onCharacteristicRead(gatt, characteristic, status);
		
		Log.d(StaticFunction.APPNAME, "=========================  onCharacteristicRead");
	}

	@Override
	public void onCharacteristicWrite(BluetoothGatt gatt,
			BluetoothGattCharacteristic characteristic, int status) {
		// TODO Auto-generated method stub
		super.onCharacteristicWrite(gatt, characteristic, status);
		
		Log.d(StaticFunction.APPNAME, "=========================  onCharacteristicWrite");
		
	}

	@Override
	public void onConnectionStateChange(final BluetoothGatt gatt, int status,
			int newState) {
		// TODO Auto-generated method stub
		super.onConnectionStateChange(gatt, status, newState);
		
		
		Log.d(StaticFunction.APPNAME, "=========================  onConnectionStateChange");
		
	
		
		 if (newState == BluetoothProfile.STATE_CONNECTED)
	       {            
	          TimerTask task = new TimerTask()
	         {
	            @Override
	            public void run()
	            {
	            	gatt.readRemoteRssi();
	            }
	         };
	         mRssiTimer = new Timer();
	         mRssiTimer.schedule(task, 1000, 1000);
	      }
	      else if (newState == BluetoothProfile.STATE_DISCONNECTED)
	      {
	    	  if (mRssiTimer!=null) {
	    		  mRssiTimer.cancel();
			}
	       
	      }
	}

	@Override
	public void onDescriptorRead(BluetoothGatt gatt,
			BluetoothGattDescriptor descriptor, int status) {
		// TODO Auto-generated method stub
		super.onDescriptorRead(gatt, descriptor, status);
		
		
		Log.d(StaticFunction.APPNAME, "=========================  onDescriptorRead");
	}

	@Override
	public void onDescriptorWrite(BluetoothGatt gatt,
			BluetoothGattDescriptor descriptor, int status) {
		// TODO Auto-generated method stub
		super.onDescriptorWrite(gatt, descriptor, status);
		
		
		Log.d(StaticFunction.APPNAME, "=========================  onDescriptorWrite");
	}

	@Override
	public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
		// TODO Auto-generated method stub
//		super.onReadRemoteRssi(gatt, rssi, status);
		
		
		Log.d(StaticFunction.APPNAME, "=========================  onReadRemoteRssi       "+rssi);
	}

	@Override
	public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
		// TODO Auto-generated method stub
		super.onReliableWriteCompleted(gatt, status);
		
		Log.d(StaticFunction.APPNAME, "=========================  onReliableWriteCompleted");
	}

	@Override
	public void onServicesDiscovered(BluetoothGatt gatt, int status) {
		// TODO Auto-generated method stub
		super.onServicesDiscovered(gatt, status);
		
		Log.d(StaticFunction.APPNAME, "=========================  onServicesDiscovered");
	}
	
}
