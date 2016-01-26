package com.itraing.adpter;

import com.itraing.findmybag.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MissingHistoryAdapter extends BaseExpandableListAdapter {

	
	private LayoutInflater mInflater;
	
	
	public MissingHistoryAdapter(Context context){
		mInflater=LayoutInflater.from(context);
	}
	
	
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}
	
	 

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextHolder textHolder;
		if (convertView==null) {
			convertView = mInflater.inflate(R.layout.adapter_miss_headview_3,
					null);
			textHolder=new TextHolder();
			textHolder.textView=(TextView)convertView.findViewById(R.id.adapter_miss_head_text_1);
			convertView.setTag(textHolder);
		}else {
			textHolder = (TextHolder) convertView.getTag(); 
		}
		
		
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_location_list_2,
					null);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.adapter_location_image1);
			viewHolder.itagTextView = (TextView) convertView
					.findViewById(R.id.adapter_location_itag_txt2);
			viewHolder.timeTextView = (TextView) convertView
					.findViewById(R.id.adapter_location_time_txt3);
			viewHolder.latlotTextView = (TextView) convertView
					.findViewById(R.id.adapter_location_latlot_txt4);
			viewHolder.addressTextView = (TextView) convertView
					.findViewById(R.id.adapter_location_addres_txt5);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag(); 
		}

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public final class TextHolder {
		 
		public TextView textView;
		 

	}
	
	public final class ViewHolder {
		public ImageView imageView;
		public TextView itagTextView;
		public TextView timeTextView;
		public TextView latlotTextView;
		public TextView addressTextView;

	}

}
