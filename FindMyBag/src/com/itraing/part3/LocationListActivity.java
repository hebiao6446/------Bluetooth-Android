package com.itraing.part3;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.itraing.adpter.LocationListAdapter;
import com.itraing.basebean.BaseActivity;
import com.itraing.findmybag.R;

public class LocationListActivity extends BaseActivity {

	ListView listView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a8_location_list);
		listView = (ListView) findViewById(R.id.a8_locationlist_listview_2);

		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < 100; i++) {
			list.add("");
		}

		LocationListAdapter locationListAdapter = new LocationListAdapter(
				LocationListActivity.this, list);
		listView.setAdapter(locationListAdapter);
		listView.setDivider(new ColorDrawable(Color.LTGRAY));
		listView.setDividerHeight(1);

	}

	public void backAction(View v) {

		finish();
	}
}
