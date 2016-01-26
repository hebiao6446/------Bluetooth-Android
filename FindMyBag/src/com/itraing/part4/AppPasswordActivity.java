package com.itraing.part4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.itraing.basebean.BaseActivity;
import com.itraing.constantpart.StaticFunction;
import com.itraing.findmybag.R;

public class AppPasswordActivity extends BaseActivity {
	
	
	TextView txtView1,txtView2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_apppasswor_view);
		txtView1=(TextView)findViewById(R.id.aa_apppasword_txt1);
		txtView2=(TextView)findViewById(R.id.aa_apppasword_txt2);
		
		
		
		
		
		
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		setTxtViewTextAction();
		
	}
	
	private void setTxtViewTextAction(){
		txtView1.setTextColor(Color.BLACK);
		txtView2.setText(R.string.cell_title_25);
		if (StaticFunction.isSystemHavePassword(AppPasswordActivity.this)) {
			txtView1.setText(R.string.cell_title_24);
			txtView2.setTextColor(Color.BLACK);
			findViewById(R.id.aa_apppasword_cell_2).setBackgroundResource(R.drawable.selector_device_list3);
			
		}else {
			txtView1.setText(R.string.cell_title_23);
			txtView2.setTextColor(Color.GRAY);
			findViewById(R.id.aa_apppasword_cell_2).setBackgroundColor(Color.WHITE);
		}
		
	}

	public void backAction(View v) {
		finish();
	}

	public void clickAction(View v) {

		int tag = Integer.parseInt(v.getTag().toString());

		if (StaticFunction.isSystemHavePassword(AppPasswordActivity.this)) {
			Intent intent = new Intent(AppPasswordActivity.this,
					SettingPasswordActivity.class);
			if (tag == 1) {
				intent.putExtra(
						"operaType",
						StaticFunction.PasswordOperationTypeClose);
			} else {
				intent.putExtra(
						"operaType",
						StaticFunction.PasswordOperationTypeChange);
			}

			startActivity(intent);

		} else {

			if (tag == 1) {
				Intent intent = new Intent(AppPasswordActivity.this,
						SettingPasswordActivity.class);
				intent.putExtra(
						"operaType",
						StaticFunction.PasswordOperationTypeOpen);
				startActivity(intent);
			}

		}

	}
}
