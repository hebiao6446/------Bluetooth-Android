package com.itraing.adpter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itraing.basebean.RingSelectBean;
import com.itraing.findmybag.R;


public class RingSelectAdapter extends BaseExpandableListAdapter {
	
	
	private LayoutInflater mInflater;
	private List<RingSelectBean> list;
	
	
	public RingSelectAdapter(Context context, List<RingSelectBean> list) {
		this.mInflater = LayoutInflater.from(context);
		this.list = list;
		
		
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		if (groupPosition==0) {
			return list.size();
		}
		return 0;
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
		
		AddViewHolder addViewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.adapter_head_footer_add_5, null);
			addViewHolder = new AddViewHolder();
			addViewHolder.addView = convertView
					.findViewById(R.id.adapter_head_footer_view_1);
			convertView.setTag(addViewHolder);
		} else {
			addViewHolder = (AddViewHolder) convertView.getTag();
		}
		
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.adapter_languague_list_4, null);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.adapter_languague_list_imageview2);
			viewHolder.languageTextView = (TextView) convertView
					.findViewById(R.id.adapter_languague_list_textview1);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		RingSelectBean  ringSelectBean= list.get(childPosition);
		if (ringSelectBean.isFlag()) {
			viewHolder.imageView.setImageResource(R.drawable.ao_ok_25);
		} else {
			viewHolder.imageView.setImageResource(R.drawable.ap_blank_26);

		}
		viewHolder.languageTextView.setText(ringSelectBean.getEnname());
		
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	private final class AddViewHolder {

		public View addView;
	}

	private final class ViewHolder {

		public TextView languageTextView;
		public ImageView imageView;

	}

}
