package com.itraing.part2;




import java.util.Observable;

import com.itraing.basebean.BaseActivity;
import com.itraing.findmybag.R;

import android.os.Bundle;
import android.view.View;


public class StopWarnActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a2_stop_warn);
	}
	
	public void aaaaClick(View v){
		
		Observable o=new  Observable();
		o.notifyObservers();
		
	}

}
