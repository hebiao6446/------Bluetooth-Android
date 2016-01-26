package com.itraing.greendao;

import java.util.List;

import android.content.Context;

import com.itraing.findmybag.MainApplication;
 

import de.greenrobot.dao.query.QueryBuilder;

public class DBHelper {

	private static final String TAG = DBHelper.class.getSimpleName();
	private static DBHelper instance;
	private static Context appContext;
	private DaoSession mDaoSession;
	private DeviceBeanDao deviceBeanDao;
	private LocationBeanDao locationBeanDao;
	private LostBeanDao lostBeanDao;

	private DBHelper() {
	}

	public static DBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DBHelper();
			if (appContext == null) {
				appContext = context.getApplicationContext();
			}
			instance.mDaoSession = MainApplication.getDaoSession(context);
			instance.deviceBeanDao = instance.mDaoSession.getDeviceBeanDao();
			instance.locationBeanDao = instance.mDaoSession
					.getLocationBeanDao();
			instance.lostBeanDao = instance.mDaoSession.getLostBeanDao();
		}
		return instance;
	}

	/*
	 * public void dropDeviceBeanTable() {
	 * DeviceBeanDao.dropTable(mDaoSession.getDatabase(), true); } public void
	 * dropLocationBeanTable() {
	 * LocationBeanDao.dropTable(mDaoSession.getDatabase(), true); } public void
	 * dropLostBeanTable() { LostBeanDao.dropTable(mDaoSession.getDatabase(),
	 * true); }
	 */

	public void dropAllTable() {
		DeviceBeanDao.dropTable(mDaoSession.getDatabase(), true);
		LocationBeanDao.dropTable(mDaoSession.getDatabase(), true);
		LostBeanDao.dropTable(mDaoSession.getDatabase(), true);
	}

	public void createAllTable() {
		DeviceBeanDao.createTable(mDaoSession.getDatabase(), true);
		LocationBeanDao.createTable(mDaoSession.getDatabase(), true);
		LostBeanDao.createTable(mDaoSession.getDatabase(), true);
	}
	
	
	public void saveDeviceBean(DeviceBean deviceBean){
		
		if (!(loadAllDeviceBean(deviceBean.getUUIDString()).isEmpty())) {
			return;
		}
		
		deviceBeanDao.insert(deviceBean);
	}
	
	public List<DeviceBean> loadAllDeviceBean(String UUIDString){
		QueryBuilder<DeviceBean> mqBuilder = deviceBeanDao.queryBuilder();
		mqBuilder.where(com.itraing.greendao.DeviceBeanDao.Properties.UUIDString.eq(UUIDString));
		return mqBuilder.list();
	}
	public List<DeviceBean> loadAllDeviceBean(){
		return deviceBeanDao.loadAll();
	}
	public DeviceBean loadDeviceBean(String UUIDString){
		return deviceBeanDao.load(UUIDString);
	}
	
	public void deleteDeviceBean(String UUIDString){
		deviceBeanDao.deleteByKey(UUIDString);
	}
	public void deleteDeviceBean(DeviceBean deviceBean){
		deviceBeanDao.delete(deviceBean);
	}
	public void deleteAllDeviceBean(){
		deviceBeanDao.deleteAll();
	}
	
	public void update(DeviceBean deviceBean){
		deviceBeanDao.update(deviceBean);
		
	}
}
