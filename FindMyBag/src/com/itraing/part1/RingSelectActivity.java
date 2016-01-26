package com.itraing.part1;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.itraing.adpter.RingSelectAdapter;
import com.itraing.basebean.BaseActivity;
import com.itraing.basebean.RingSelectBean;
import com.itraing.findmybag.R;
import com.itraing.greendao.DBHelper;
import com.itraing.greendao.DeviceBean;

public class RingSelectActivity extends BaseActivity implements OnChildClickListener {

	DBHelper dBManager = null;

	ExpandableListView listView = null;

	String resname[] = {  "alarm_bird", "alarm_car", "alarm_cat",
			"alarm_chatcall", "alarm_dog", "alarm_fire", "alarm_music",
			"alarm_radar", "alarm_trumpet", "alarm_whistle", "alarm" };
	int enname[] = {  R.string.res_name_47, R.string.res_name_48,
			R.string.res_name_49, R.string.res_name_50, R.string.res_name_51,
			R.string.res_name_52, R.string.res_name_53, R.string.res_name_54,
			R.string.res_name_55, R.string.res_name_56, R.string.res_name_57 };
	int raws[] = {  R.raw.alarm_bird, R.raw.alarm_car,
			R.raw.alarm_cat, R.raw.alarm_chatcall, R.raw.alarm_dog,
			R.raw.alarm_fire, R.raw.alarm_music, R.raw.alarm_radar,
			R.raw.alarm_trumpet,R.raw.alarm_whistle, R.raw.alarm };

	String indexFlag = null;
	String UUIDString = null;
	DeviceBean deviceBean = null;

	List<RingSelectBean> list = null;

	RingSelectAdapter ringSelectAdapter = null;
	
	
	MediaPlayer mediaPlayer=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.af_ring_select_list);
		 
	 
		
		 
		dBManager = DBHelper.getInstance(this);
		indexFlag = getIntent().getStringExtra("indexFlag");
		UUIDString = getIntent().getStringExtra("UUIDString");
		deviceBean = dBManager.loadDeviceBean(UUIDString);

		list = new ArrayList<RingSelectBean>();
		allocLocateData();

		
		listView = (ExpandableListView) findViewById(R.id.af_ring_switch_listview2);
		ringSelectAdapter = new RingSelectAdapter(this, list);
		listView.setDivider(new ColorDrawable(Color.LTGRAY)); 
		  listView.setDividerHeight(1); 
		  listView.setVerticalScrollBarEnabled(false);
		  listView.setChildDivider(new ColorDrawable(Color.LTGRAY));
		  listView.setDividerHeight(1);
		  listView.setGroupIndicator(null);
		  
		listView.setAdapter(ringSelectAdapter);
		for (int i = 0; i < ringSelectAdapter.getGroupCount(); i++) {
			listView.expandGroup(i);
		}
		
	 
		listView.setOnChildClickListener(this);
 
		
	}

	private void allocLocateData() {
		// TODO Auto-generated method stub
		list.clear();
		for (int i = 0; i < enname.length; i++) {
			RingSelectBean ringSelectBean = new RingSelectBean();
			if ("1".equals(indexFlag)) {

				ringSelectBean.setFlag(deviceBean.getWarnVoice().equals(
						resname[i]));

			} else {
				ringSelectBean.setFlag(deviceBean.getFindVoice().equals(
						resname[i]));

			}
			ringSelectBean.setResname(resname[i]);
			ringSelectBean.setRaw(raws[i]);
			ringSelectBean.setEnname(enname[i]);
			list.add(ringSelectBean);

		}
	}

	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (mediaPlayer!=null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer=null;
		}
	}
	
	 
	
	public  void playMusic(int raw){
		if (mediaPlayer!=null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer=null;
		}
		mediaPlayer=MediaPlayer.create(RingSelectActivity.this, raw);
		mediaPlayer.setLooping(false);
//		mediaPlayer.setVolume(0.5f, 0.5f);
		mediaPlayer.start();
	}

	public void backAction(View v) {
		finish();
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		
		if (groupPosition!=0) {
			return false;
		}
	 
		
		RingSelectBean ringSelectBean = list.get(childPosition);

		if ("1".equals(indexFlag)) {
			deviceBean.setWarnVoice(ringSelectBean.getResname());
			// ringSelectBean.setFlag(deviceBean.getWarnVoice().equals(resname[i]));
		} else {
			// ringSelectBean.setFlag(deviceBean.getFindVoice().equals(resname[i]));
			deviceBean.setFindVoice(ringSelectBean.getResname());

		}
		
		playMusic(ringSelectBean.getRaw());
		
		dBManager.update(deviceBean);
		allocLocateData();
		ringSelectAdapter.notifyDataSetChanged();
		
		
		
		
		return true;
	}

 
}
