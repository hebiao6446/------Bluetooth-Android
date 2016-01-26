package com.itraing.adpter;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.itraing.constantpart.StaticFunction;
import com.itraing.findmybag.R;
import com.itraing.greendao.DBHelper;
import com.itraing.greendao.DeviceBean;

public class DeviceListAdapter extends BaseAdapter {
	
	 
	private LayoutInflater mInflater;
	 private List<String> list;
	 private List<String> connectedList;
	 private Context context;
	 public interface DeviceListAdapterListener{
		 public void deviceListCellDidConnectButtonClick(int index);
	 }
	 
	private DeviceListAdapterListener deviceListAdapterListener=null;
		
	public DeviceListAdapter(Context context,List<String> list,List<String> connectedList){
		this.context=context;
		 this.mInflater = LayoutInflater.from(context);
		 this.list=list;
		 this.connectedList=connectedList;
	}
	 
	public void setOnDeviceListAdapterListener(DeviceListAdapterListener deviceListAdapterListener){
		this.deviceListAdapterListener=deviceListAdapterListener;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		
		if (convertView==null) {
			convertView = mInflater.inflate(R.layout.adapter_device_list_1,null);
			viewHolder = new ViewHolder();
			viewHolder.imageView=(ImageView)convertView.findViewById(R.id.device_list_image1);
			viewHolder.itagTextView=(TextView)convertView.findViewById(R.id.device_list_textview2);
			viewHolder.connectTextView=(TextView)convertView.findViewById(R.id.device_list_textview3);
			viewHolder.connectButton=(Button)convertView.findViewById(R.id.device_list_button4);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag();//ȡ��ViewHolder���� 
		}
		
		
		String uuidString=list.get(position);
		
		
//		DeviceBean deviceBean= DBHelper.getInstance(context).loadDeviceBean(uuidString);
		
		if (true) {
//			viewHolder.itagTextView.setText(deviceBean.getTagName());
		}else {
			viewHolder.itagTextView.setText("iTag");
		}
 	
		
		Bitmap bitmap=StaticFunction.loadMyBitmap("");
		if (bitmap!=null) {
			viewHolder.imageView.setImageBitmap(bitmap);
		}else {
			viewHolder.imageView.setImageResource(R.drawable.ag_cellimage16);
		}
		/*
		if(position%2==0){
			viewHolder.imageView.setImageResource(R.drawable.ag_cellimage17);
			
			
			viewHolder.connectTextView.setText("unconnected");
			viewHolder.connectTextView.setTextColor(StaticFunction.SYSTEM_GRAY_COLOR);
		}else {
			viewHolder.imageView.setImageResource(R.drawable.ag_cellimage16);
			
			viewHolder.connectTextView.setText("connected");
			viewHolder.connectTextView.setTextColor(StaticFunction.SYSTEM_GREEN_COLOR);
		}
		*/
		
		
		if (connectedList.contains(uuidString)) {
			viewHolder.connectTextView.setText(context.getResources().getString(R.string.text_string_33));
			viewHolder.connectTextView.setTextColor(context.getResources().getColor(R.color.sys_green_color));
			viewHolder.connectButton.setText(context.getResources().getString(R.string.cell_title_38));
		}else {
			viewHolder.connectTextView.setText(context.getResources().getString(R.string.text_string_34));
			viewHolder.connectTextView.setTextColor(context.getResources().getColor(R.color.sys_gray_color));
			viewHolder.connectButton.setText(context.getResources().getString(R.string.text_string_32));
		}
		
		
		viewHolder.connectButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.err.println("viewHolder.connectButton.setOnClickListen ------");
				deviceListAdapterListener.deviceListCellDidConnectButtonClick(position);
				
			}
		});
		
		
		return convertView;
	}
	
	
	public final class ViewHolder{
		public ImageView imageView;
		public TextView itagTextView;
		public TextView connectTextView;
		public Button connectButton;
		
	}
	
	
	
	

}
