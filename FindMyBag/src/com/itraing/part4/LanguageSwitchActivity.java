package com.itraing.part4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.itraing.adpter.LanguageListAdapter;
import com.itraing.basebean.BaseActivity;
import com.itraing.constantpart.StaticFunction;
import com.itraing.findmybag.MainActivity;
import com.itraing.findmybag.R;

public class LanguageSwitchActivity extends BaseActivity implements OnChildClickListener{

	ExpandableListView listView = null;

	String name[]={"简体中文","繁體中文","English","Français","Deutsch","日本語","한국어","Español","Português","العربية"};
	String enname[]={"zh_CN","zh_TW","en","fr","de","ja","ko","es","pt","ar"};
	Locale locales[]={Locale.SIMPLIFIED_CHINESE,Locale.TRADITIONAL_CHINESE,Locale.ENGLISH,Locale.FRANCE,Locale.GERMANY,Locale.JAPAN,Locale.KOREA,new Locale("es"),new Locale("pt"),new Locale("ar")};
    List<Map<String,String>> list=null;
    
    Locale currentLocale=null;
    LanguageListAdapter languageListAdapter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ab_langua_switch);
		
		 Resources resources = getResources();//获得res资源对象
	     Configuration config = resources.getConfiguration();//获得设置对象
	     currentLocale=config.locale;
	     
	    
		
		
		list=new ArrayList<Map<String,String>>();
		allocLocateData();
		
		
		
		
		listView = (ExpandableListView) findViewById(R.id.ab_language_switch_listview2);
		languageListAdapter = new LanguageListAdapter(
				LanguageSwitchActivity.this,list);
		listView.setDivider(new ColorDrawable(Color.LTGRAY)); 
		  listView.setDividerHeight(1); 
		  listView.setVerticalScrollBarEnabled(false);
		  listView.setChildDivider(new ColorDrawable(Color.LTGRAY));
		  listView.setDividerHeight(1);
		  listView.setGroupIndicator(null);
		listView.setAdapter(languageListAdapter);
		for (int i = 0; i < languageListAdapter.getGroupCount(); i++) {
			listView.expandGroup(i);
		}
		
	 
		listView.setOnChildClickListener(this);
	     
 
		  

	}

	 public void allocLocateData(){
		 
		 list.clear();
		 
		  
		 
		 
		 for (int i = 0; i < name.length; i++) {
			Map<String, String> map=new HashMap<String, String>();
			map.put("name", name[i]);
			map.put("enname", enname[i]);
			if (currentLocale.equals(locales[i])) {
				map.put("flag", "1");
			}else {
				map.put("flag", "0");
			}
			
			list.add(map);
		}
		  
		 
	 }

	public void backAction(View v) {
		finish();
	}



 
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		if (position==0||position==locales.length-1) {
			return;
		}		 
		
        
        
        
        

        
        
        
       
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		if (groupPosition!=0) {
			return false;
		}
		
		Locale locate=locales[childPosition];
        StaticFunction.SYSTEM_CHANGE_LANGUAGE_FLAG=true;
        StaticFunction.setSystemLacate(LanguageSwitchActivity.this, locate);
        Intent intent = new Intent();
        intent.setClass(LanguageSwitchActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
		
		
		return true;
	}
}
