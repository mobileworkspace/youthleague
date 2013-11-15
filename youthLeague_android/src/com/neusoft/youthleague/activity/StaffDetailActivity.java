package com.neusoft.youthleague.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.youthleague.R;
import com.neusoft.youthleague.model.Staff;

public class StaffDetailActivity extends Activity {

	private static final String TAG = "Detail";

	// 以下是UI -----------------------------------------------------
	private ImageButton backButton;
	private Button callButton;
	private Button smsButton;
	
	private TextView nameTextView;
	private TextView mobileTextView;
	private TextView phoneTextView;
	private TextView positionTextView;
	
	private Staff staff;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail);

		Intent intent = getIntent();
		staff = (Staff) intent.getSerializableExtra("staff");

		findViews();

		initView();

	}

	private void initView() {

		if (staff!=null) {  //演示
			nameTextView.setText(staff.getName());
			positionTextView.setText(staff.getPositionName());
			mobileTextView.setText(staff.getMobile());
			phoneTextView.setText(staff.getPhone());
		}
		
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		callButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phoneString = staff.getMobile();
				
				if ("".equals(phoneString)) {
					phoneString = staff.getPhone();
				}
				
				if (!"".equals(phoneString)) {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:" + phoneString));
					startActivity(intent);
				}else {
					Toast.makeText(StaffDetailActivity.this, getString(R.string.no_vaild_phone), Toast.LENGTH_LONG).show();
				}
			}
		});
		
		smsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phoneString = staff.getMobile();
				if ("".equals(phoneString)) {
					Toast.makeText(StaffDetailActivity.this, getString(R.string.no_vaild_phone), Toast.LENGTH_LONG).show();
				}else {
					
					Intent intent = new Intent();
					intent.setAction("com.neusoft.SMS");
					intent.putExtra("phone", phoneString);
					startActivity(intent);
				}
				
			}
		});
	}

	// 获取View组件
	private void findViews() {
		
		phoneTextView = (TextView) findViewById(R.id.phone_textview);
		nameTextView = (TextView) findViewById(R.id.name_textview);
		mobileTextView = (TextView) findViewById(R.id.mobile_textview);
		positionTextView = (TextView) findViewById(R.id.position_textview);
		
		backButton = (ImageButton) findViewById(R.id.back_btn);
		callButton = (Button)findViewById(R.id.call_btn);
		smsButton = (Button) findViewById(R.id.sms_btn);
	}
	
}
