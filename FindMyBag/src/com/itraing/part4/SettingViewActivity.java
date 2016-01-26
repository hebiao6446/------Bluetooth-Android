package com.itraing.part4;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.itraing.basebean.BaseActivity;
import com.itraing.constantpart.StaticFunction;
import com.itraing.findmybag.R;


public class SettingViewActivity extends BaseActivity implements OnCheckedChangeListener{
	
	
	
	@SuppressWarnings("rawtypes")
	List<Class> list=null;
	
	
	ToggleButton toggleButton=null;
	
	TextView passwdTextView;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a4_setting_view);
		passwdTextView=(TextView)findViewById(R.id.a4_setting_view_password_status);
		toggleButton=(ToggleButton)findViewById(R.id.a4_setting_view_togglebutton);
		toggleButton.setOnCheckedChangeListener(this);
		toggleButton.setChecked(StaticFunction.getSystemRemainFlag(SettingViewActivity.this));
		list=new ArrayList<Class>();
		list.add(BaseActivity.class);
		list.add(AppPasswordActivity.class);
		list.add(LanguageSwitchActivity.class);
		list.add(MapSwitchActivity.class);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (StaticFunction.isSystemHavePassword(SettingViewActivity.this)) {
			passwdTextView.setText(R.string.cell_title_14_a);
		}else{
			passwdTextView.setText(R.string.cell_title_14);
		}
		
	}
	
	public void clickAction(View v){
		int index=Integer.parseInt((String)v.getTag());
		Intent intent=new Intent(SettingViewActivity.this, list.get(index));
		startActivity(intent);
		
		
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		
		 StaticFunction.setSystemRemainFlag(SettingViewActivity.this, isChecked);
		
	}
}
