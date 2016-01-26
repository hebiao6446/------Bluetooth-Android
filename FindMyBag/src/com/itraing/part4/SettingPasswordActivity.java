package com.itraing.part4;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.itraing.basebean.BaseActivity;
import com.itraing.constantpart.StaticFunction;
import com.itraing.findmybag.R;

public class SettingPasswordActivity extends BaseActivity implements
		TextWatcher {

	int passwordOperationType;
	EditText editText1, editText2, editText3, editText4;
	TextView txtTextView, warnTextView;

	int inputCount = 0;
	
	String inputString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ad_setting_password_view);

		txtTextView = (TextView) findViewById(R.id.ad_setting_password_txt1);
		passwordOperationType = getIntent().getIntExtra("operaType",
				StaticFunction.PasswordOperationType);
		warnTextView = (TextView) findViewById(R.id.ad_setting_password_txt2);

		if (passwordOperationType == StaticFunction.PasswordOperationTypeOpen) {
			txtTextView.setText(R.string.text_string_27);
		} else {
			txtTextView.setText(R.string.text_string_29);
		}

		editText1 = (EditText) findViewById(R.id.ad_setting_pass_edittext_1);
		editText2 = (EditText) findViewById(R.id.ad_setting_pass_edittext_2);
		editText3 = (EditText) findViewById(R.id.ad_setting_pass_edittext_3);
		editText4 = (EditText) findViewById(R.id.ad_setting_pass_edittext_4);

		editText1.addTextChangedListener(this);
		editText2.addTextChangedListener(this);
		editText3.addTextChangedListener(this);
		editText4.addTextChangedListener(this);
		showSoftInput();

	}

	private void showSoftInput() {
		editText1.setFocusable(true);
		editText1.setFocusableInTouchMode(true);
		editText1.requestFocus();
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				InputMethodManager inputManager = (InputMethodManager) editText1
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(editText1, 0);

			}
		}, 998);

	}

	public void backAction(View v) {

		finish();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		System.out.println("beforeTextChanged--------" + s);
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		if (s.length()>0) {
			if (editText1.hasFocus()) {
				editText2.setFocusable(true);
				editText2.setFocusableInTouchMode(true);
				editText2.requestFocus();
			}else if (editText2.hasFocus()){
				editText3.setFocusable(true);
				editText3.setFocusableInTouchMode(true);
				editText3.requestFocus();
			}else if (editText3.hasFocus()){
				editText4.setFocusable(true);
				editText4.setFocusableInTouchMode(true);
				editText4.requestFocus();
			}else if (editText4.hasFocus()){
				inputCount ++;
				if (passwordOperationType==StaticFunction.PasswordOperationTypeOpen) {
					passwordOperationTypeOpenAction();
				}else if (passwordOperationType==StaticFunction.PasswordOperationTypeClose) {
					passwordOperationTypeCloseAction();
				}else if (passwordOperationType==StaticFunction.PasswordOperationTypeChange) {
					passwordOperationTypeChangeAction();
				}
			}
		}

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

		System.out.println("afterTextChanged--------" + s);

	}

	private void passwordOperationTypeOpenAction() {
		String s=getEdit4String();
		if (inputCount==1) {
			inputString=s;
		}else{
			if (s.equals(inputString)) {
				StaticFunction.saveCurrentPassword(SettingPasswordActivity.this, inputString);
				StaticFunction.saveSystemPasswordStatus(SettingPasswordActivity.this, true);
				finish();
			}else {
				warnTextView.setText(R.string.text_string_30);
				warnTextView.setVisibility(View.VISIBLE);
				hiddenWarnTextView();
			}
		}
		txtTextView.setText(R.string.text_string_28);
		
		editText1.setFocusable(true);
		editText1.setFocusableInTouchMode(true);
		editText1.requestFocus();
		editText1.setText("");
		editText2.setText("");
		editText3.setText("");
		editText4.setText("");

	}

	private void passwordOperationTypeCloseAction() {
		String s=getEdit4String();
		String up=StaticFunction.getCurrentPassword(SettingPasswordActivity.this);
		if (s.equals(up)) {
			StaticFunction.saveCurrentPassword(SettingPasswordActivity.this, "1#2#3#4#");
			StaticFunction.saveSystemPasswordStatus(SettingPasswordActivity.this, false);
			finish();
		}else {
			warnTextView.setText(R.string.text_string_31);
			warnTextView.setVisibility(View.VISIBLE);
			hiddenWarnTextView();
		}
		
		inputString=s;
	
		editText1.setFocusable(true);
		editText1.setFocusableInTouchMode(true);
		editText1.requestFocus();
		editText1.setText("");
		editText2.setText("");
		editText3.setText("");
		editText4.setText("");
		
	}

	private void passwordOperationTypeChangeAction() {
		String s=getEdit4String();
		String up=StaticFunction.getCurrentPassword(SettingPasswordActivity.this);
		if (inputCount==1) {
			
			if (s.equals(up)) {
				inputCount++;
				txtTextView.setText(R.string.text_string_27);
			}else{
				inputCount=0;
				warnTextView.setText(R.string.text_string_31);
				warnTextView.setVisibility(View.VISIBLE);
				hiddenWarnTextView();
			}
			
		}else{
			
			if (inputString==null||inputString.length()==0) {
				inputString=s;
				txtTextView.setText(R.string.text_string_28);
			}else {
				if (inputString.equals(s)) {
					StaticFunction.saveCurrentPassword(SettingPasswordActivity.this, inputString);
					StaticFunction.saveSystemPasswordStatus(SettingPasswordActivity.this, true);
					finish();
				}else {
					warnTextView.setText(R.string.text_string_30);
					warnTextView.setVisibility(View.VISIBLE);
					hiddenWarnTextView();
				}
			}
			
		}
		
		
		editText1.setFocusable(true);
		editText1.setFocusableInTouchMode(true);
		editText1.requestFocus();
		editText1.setText("");
		editText2.setText("");
		editText3.setText("");
		editText4.setText("");
	}
	
	private String getEdit4String(){
		
		String st1 = editText1.getText().toString().trim();
		String st2 = editText2.getText().toString().trim();
		String st3 = editText3.getText().toString().trim();
		String st4 = editText4.getText().toString().trim();
		String str = "1" + (st1.length() > 0 ? st1 : "*") + "2"
				+ (st2.length() > 0 ? st2 : "*") + "3"
				+ (st3.length() > 0 ? st3 : "*") + "4"
				+ (st4.length() > 0 ? st4 : "*");
		
		return str;
	}

	private void hiddenWarnTextView() {

		new Handler().postDelayed(new Runnable() {
			public void run() {
				// execute the task
				warnTextView.setVisibility(View.GONE);
			}
		}, 1500);
	}

}
