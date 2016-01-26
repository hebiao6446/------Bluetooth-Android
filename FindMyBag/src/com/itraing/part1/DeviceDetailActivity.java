package com.itraing.part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itraing.basebean.BaseActivity;
import com.itraing.constantpart.StaticFunction;
import com.itraing.findmybag.R;
import com.itraing.greendao.DBHelper;
import com.itraing.greendao.DeviceBean;
import com.itraing.views.ActionSheet;
import com.itraing.views.ActionSheet.ActionSheetListener;

public class DeviceDetailActivity extends BaseActivity implements ActionSheetListener, TextWatcher{
	
	
	
	
	private DBHelper dBManager;
	
	public static final int CHOICE_CAMERA = 1;
	public static final int CHOICE_PHOTO = 2;
	public static final String SAVE_IMAGE_CAMERA = "/mnt/sdcard/itraing/headImage/";
	ImageView imageView=null;
	EditText editText=null;
	String capturePath=null;
	
	String UUIDString=null;
	
	DeviceBean deviceBean=null;
	
	TextView titleTextView=null;
	
	private static final File tmpFiel=new File(Environment.getExternalStorageDirectory()+"/itraing-img/aaaa.jpg");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a5_device_detail);
		imageView=(ImageView)findViewById(R.id.a5_device_detail_imageview_1);
		titleTextView=(TextView)findViewById(R.id.a5_device_detail_nagation_tv1);
		editText=(EditText)findViewById(R.id.a5_device_detail_edittext_2);
		editText.addTextChangedListener(this);
		dBManager = DBHelper.getInstance(this);
		UUIDString=getIntent().getStringExtra("UUIDString");
		deviceBean=dBManager.loadDeviceBean(UUIDString);
		allocViewData();
		
	}
	
	private void allocViewData(){
		
		if (deviceBean==null) {
			return;
		}
		
		
		
		editText.setText(deviceBean.getTagName());
		titleTextView.setText(deviceBean.getTagName());
		System.out.println("==================="+deviceBean.getImageName());
		
	 
		Bitmap bitmap=StaticFunction.loadMyBitmap(deviceBean.getImageName());
		if (bitmap!=null) {
			imageView.setImageBitmap(bitmap);
		}
	 
		
		 
	}

	public void backAction(View v) {
		finish();
	}
	public void takePhotoAction(View v){
//     	getImageFromCamera();
//		getImageFromAlbum();
	 setTheme(R.style.ActionSheetStyleiOS7);
		ActionSheet.createBuilder(this, getSupportFragmentManager())
        .setCancelButtonTitle(getString(R.string.actionsheet_txt_44))
        .setOtherButtonTitles(getString(R.string.actionsheet_txt_45),getString(R.string.actionsheet_txt_46))
        .setCancelableOnTouchOutside(true)
        .setListener(this).show();
		
	}
	
	@Override
	public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOtherButtonClick(ActionSheet actionSheet, int index) {
		// TODO Auto-generated method stub
		if (index==0) {
			getImageFromCamera();
		}else if (index==1) {
			getImageFromAlbum();
		}
	}
	 
	 
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		if (s.toString().trim().length()>0) {
			titleTextView.setText(s.toString().trim());
			deviceBean.setTagName(s.toString().trim());
			dBManager.update(deviceBean);
		}
		
	}
	protected void getImageFromCamera() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			Intent getImageByCamera = new Intent(
					"android.media.action.IMAGE_CAPTURE");
			String out_file_path = SAVE_IMAGE_CAMERA;
			File dir = new File(out_file_path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			capturePath = SAVE_IMAGE_CAMERA + System.currentTimeMillis()
					+ ".jpg";
			getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(capturePath)));
			getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			startActivityForResult(getImageByCamera, CHOICE_CAMERA);
		} else {
//			Toast.makeText(getApplicationContext(), "请确认已经插入SD卡",
//					Toast.LENGTH_LONG).show();
		}
	}
	
	protected void getImageFromAlbum() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");// 相片类型
		startActivityForResult(intent, CHOICE_PHOTO);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		if (requestCode == CHOICE_CAMERA && resultCode == RESULT_OK) {
			getCropImage(Uri.fromFile(new File(capturePath)));
		}
		if (requestCode == CHOICE_PHOTO && resultCode == RESULT_OK) {
			Uri uri = data.getData();
			getCropImage(uri);

		}
		if (requestCode == 200 && resultCode == RESULT_OK) {
			Bitmap bmap = data.getParcelableExtra("data");
			imageView.setImageBitmap(bmap);
		 String strName=System.currentTimeMillis()+"";
        deviceBean.setImageName(strName);
        dBManager.update(deviceBean);
 		StaticFunction.saveMyBitmap(strName, bmap);
		}
	}
	
	
	 
	
	private void getCropImage(Uri mUri) {
		if (null == mUri)
			return;

		Intent intent = new Intent();

		intent.setAction("com.android.camera.action.CROP");
		intent.setDataAndType(mUri, "image/*");// mUri是已经选择的图片Uri
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 300);// 输出图片大小
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, 200);
	}

	public void clickAction(View v) {
		hiddenKeyborad(v);
		
		if (Integer.parseInt((String)v.getTag())==3) {
			
			
			return;
		}
		
		Intent intent=new Intent(DeviceDetailActivity.this, DeviceSettingActivity.class);
		intent.putExtra("indexFlag", (String)v.getTag());
		intent.putExtra("UUIDString", UUIDString);
		startActivity(intent);
		
		
		
	}
	public void clickBackAction(View v){
		hiddenKeyborad(v);
	}
	
	private void hiddenKeyborad(View view){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	

	

	
}
