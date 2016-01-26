package com.itraing.part4;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.itraing.basebean.BaseActivity;
import com.itraing.constantpart.StaticFunction;
import com.itraing.findmybag.R;

public class MapSwitchActivity extends BaseActivity {
	
	ImageView mapImageView1,mapImageView2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_map_switch);
		mapImageView1=(ImageView)findViewById(R.id.ac_map_switch_map1);
		mapImageView2=(ImageView)findViewById(R.id.ac_map_switch_map2);
		
		if (StaticFunction.getSystemMap(MapSwitchActivity.this)==StaticFunction.MAP_TPYE_BAIDU) {
			mapImageView1.setVisibility(View.VISIBLE);
			mapImageView2.setVisibility(View.GONE);
		}else {
			mapImageView2.setVisibility(View.VISIBLE);
			mapImageView1.setVisibility(View.GONE);
		}
	}
	
	public void backAction(View v){
		finish();
	}
	
	public void clickAction(View v){
		int tag=Integer.parseInt((String)v.getTag());
		
		if (tag==1) {
			mapImageView1.setVisibility(View.VISIBLE);
			mapImageView2.setVisibility(View.GONE);
			StaticFunction.setSystemMap(MapSwitchActivity.this, StaticFunction.MAP_TPYE_BAIDU);
		}else{
			mapImageView2.setVisibility(View.VISIBLE);
			mapImageView1.setVisibility(View.GONE);
			StaticFunction.setSystemMap(MapSwitchActivity.this, StaticFunction.MAP_TPYE_GOOGLE);
		}
		
		
	}
}
