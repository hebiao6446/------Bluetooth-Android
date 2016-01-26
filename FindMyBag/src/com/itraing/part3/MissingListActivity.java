package com.itraing.part3;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.itraing.adpter.MissingHistoryAdapter;
import com.itraing.basebean.BaseActivity;
import com.itraing.findmybag.R;

public class MissingListActivity extends BaseActivity implements OnGroupClickListener,OnChildClickListener{
	
	
	ExpandableListView listView=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a7_missing_list);
		
		listView=(ExpandableListView)findViewById(R.id.a7_missing_listview_2);
		
		MissingHistoryAdapter missingHistoryAdapter=new MissingHistoryAdapter(MissingListActivity.this);
		 listView.setDivider(new ColorDrawable(Color.LTGRAY)); 
		  listView.setDividerHeight(1); 
		  listView.setChildDivider(new ColorDrawable(Color.LTGRAY));
		  listView.setDividerHeight(1);
		  listView.setGroupIndicator(null);
		listView.setAdapter(missingHistoryAdapter);
		for (int i = 0; i < missingHistoryAdapter.getGroupCount(); i++) {
			listView.expandGroup(i);
		}
		
		listView.setOnGroupClickListener(this);
		listView.setOnChildClickListener(this);
		
		
		
		
		
	}
	
	
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		// TODO Auto-generated method stub
		
		System.out.println("---------------------"+groupPosition);
		
		return true;
	}
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		
		
		System.out.println("---------------------"+groupPosition);
		
		return false;
	}

	
	public void backAction(View v){
		
		finish();
		
	}


	
	
	
}
