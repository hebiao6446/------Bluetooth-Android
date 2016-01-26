package com.itraing.findmybag;


import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.baidu.mapapi.SDKInitializer;
import com.itraing.greendao.DaoMaster;
import com.itraing.greendao.DaoMaster.OpenHelper;
import com.itraing.greendao.DaoSession;

public class MainApplication extends Application {
	
	
	
	private static DaoMaster daoMaster;
	private static DaoSession daoSession;
	public static SQLiteDatabase db;
	public static final String DB_NAME = "db_findmybag.db";
	
	@Override
	public void onCreate() {
		super.onCreate();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
		
	}
	
	public static DaoMaster getDaoMaster(Context context) { 
	      if (daoMaster == null) { 
           OpenHelper helper = new DaoMaster.DevOpenHelper(context,DB_NAME, null); 
          daoMaster = new DaoMaster(helper.getWritableDatabase()); 
	      } 
	      return daoMaster;    
	   } 
	
	 public static DaoSession getDaoSession(Context context) { 
	      if (daoSession == null) { 
	          if (daoMaster == null) { 
	              daoMaster = getDaoMaster(context); 
	          } 
	          daoSession = daoMaster.newSession(); 
	      } 
	      return daoSession;    
	}    
	 
	 public static SQLiteDatabase getSQLDatebase(Context context) { 
	      if (daoSession == null) { 
	          if (daoMaster == null) { 
	              daoMaster = getDaoMaster(context); 
	          } 
	          db = daoMaster.getDatabase(); 
	      } 
	          return db;    
	      } 
}
