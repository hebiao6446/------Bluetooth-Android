package com.itraing.part3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.itraing.basebean.BaseActivity;
import com.itraing.findmybag.R;

public class OnLocationActivity extends BaseActivity  implements BDLocationListener{

	private MapView mMapView;
	String keyString = "DD279b2a90afdf0ae7a3796787a0742e";
	private BaiduMap mBaiduMap;
	boolean isFirstLoc = true;// 是否首次定位
	LocationClient mLocClient;
	LocationMode mCurrentMode=LocationMode.FOLLOWING;
	BitmapDescriptor mCurrentMarker= BitmapDescriptorFactory
			.fromResource(R.drawable.ag_cellimage16);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a3_on_loaction);
		
		
		mMapView = (MapView) findViewById(R.id.a3_on_location_baidumap);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		mBaiduMap
		.setMyLocationConfigeration(new MyLocationConfiguration(
				mCurrentMode, true, mCurrentMarker));
		 mBaiduMap.setMaxAndMinZoomLevel(15, 19);
		
		
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		

	}
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		isFirstLoc = true;
	}
	
	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}

	public void leftAction(View v) {
		Intent intent = new Intent(OnLocationActivity.this,
				MissingListActivity.class);
		startActivity(intent);
	}

	public void rightAction(View v) {
		Intent intent = new Intent(OnLocationActivity.this,
				LocationListActivity.class);
		startActivity(intent);
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		// TODO Auto-generated method stub
		
		if (location == null || mMapView == null)
			return;
		MyLocationData locData = new MyLocationData.Builder()
				.accuracy(location.getRadius())
				// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(100).latitude(location.getLatitude())
				.longitude(location.getLongitude()).build();
		mBaiduMap.setMyLocationData(locData);
		if (isFirstLoc) {
			isFirstLoc = false;
			LatLng ll = new LatLng(location.getLatitude(),
					location.getLongitude());
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
			mBaiduMap.animateMapStatus(u);
		}
		
	}

}
