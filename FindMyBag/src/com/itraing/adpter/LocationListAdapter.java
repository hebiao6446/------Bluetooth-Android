package com.itraing.adpter;

import java.util.List;

import com.itraing.adpter.DeviceListAdapter.ViewHolder;
import com.itraing.findmybag.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationListAdapter extends BaseAdapter {

	List<Object> list;
	private LayoutInflater mInflater;

	public LocationListAdapter(Context context, List<Object> list) {
		this.list = list;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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

	public final class ViewHolder {
		public ImageView imageView;
		public TextView itagTextView;
		public TextView timeTextView;
		public TextView latlotTextView;
		public TextView addressTextView;

	}
}
