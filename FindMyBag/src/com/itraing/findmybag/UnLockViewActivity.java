package com.itraing.findmybag;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.itraing.constantpart.StaticFunction;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class UnLockViewActivity extends Activity implements TextWatcher {

	 


	EditText editText1, editText2, editText3, editText4;
	TextView txtTextView, warnTextView;

	String inputString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
//		reloadLanguageAction();
		setContentView(R.layout.ae_unlock_view);

		txtTextView = (TextView) findViewById(R.id.ae_unlockview_password_txt1);
		warnTextView = (TextView) findViewById(R.id.ae_unlockview_password_txt2);

		editText1 = (EditText) findViewById(R.id.ae_unlockview_edittext_1);
		editText2 = (EditText) findViewById(R.id.ae_unlockview_edittext_2);
		editText3 = (EditText) findViewById(R.id.ae_unlockview_edittext_3);
		editText4 = (EditText) findViewById(R.id.ae_unlockview_edittext_4);

		editText1.addTextChangedListener(this);
		editText2.addTextChangedListener(this);
		editText3.addTextChangedListener(this);
		editText4.addTextChangedListener(this);
		showSoftInput();
	}
	
	private void reloadLanguageAction(){
		Locale locale = StaticFunction.getSystemLacate(UnLockViewActivity.this);
		Locale.setDefault(locale);  
        Configuration config = new Configuration();  
        config.locale = locale;  
       getBaseContext().getResources().updateConfiguration(config, null);
       getBaseContext().getResources().flushLayoutCache();
       
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

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

		if (s.length() > 0) {
			if (editText1.hasFocus()) {
				editText2.setFocusable(true);
				editText2.setFocusableInTouchMode(true);
				editText2.requestFocus();
			} else if (editText2.hasFocus()) {
				editText3.setFocusable(true);
				editText3.setFocusableInTouchMode(true);
				editText3.requestFocus();
			} else if (editText3.hasFocus()) {
				editText4.setFocusable(true);
				editText4.setFocusableInTouchMode(true);
				editText4.requestFocus();
			} else if (editText4.hasFocus()) {
				String ss = getEdit4String();
				String up = StaticFunction
						.getCurrentPassword(UnLockViewActivity.this);
				if (ss.equals(up)) {
					
					
					finish();

				} else {

					warnTextView.setText(R.string.text_string_31);
					warnTextView.setVisibility(View.VISIBLE);
					hiddenWarnTextView();
					editText1.setFocusable(true);
					editText1.setFocusableInTouchMode(true);
					editText1.requestFocus();
					editText1.setText("");
					editText2.setText("");
					editText3.setText("");
					editText4.setText("");
				}
			}
		}

	}

	private String getEdit4String() {

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

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
	        return true;
	    } else if(keyCode == KeyEvent.KEYCODE_MENU) {//MENU键
	        //监控/拦截菜单键
	         return true;
	    }    
	return super.onKeyDown(keyCode, event);
	} 
}
