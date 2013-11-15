package com.neusoft.youthleague.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.youthleague.R;

public class SmsActivity extends Activity {

	private static final String TAG = "Sms";

	// 以下是UI -----------------------------------------------------
	private ImageButton backButton;
	private EditText msEditText;
	private TextView toTextView;
	private Button sendButton;

	private List<String> sendNameList;
	private List<String> sendMobileList;
	
	
	private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context _context, Intent _intent) {
			
			switch (getResultCode()) {
			
				case Activity.RESULT_OK:
					Toast.makeText(SmsActivity.this, getString(R.string.send_success), Toast.LENGTH_SHORT).show();
					break;
					
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
				case SmsManager.RESULT_ERROR_RADIO_OFF:
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(SmsActivity.this, getString(R.string.send_fail), Toast.LENGTH_SHORT).show();
					break;
			}
		}
	}; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sms);

		Intent intent = getIntent();
		sendNameList = intent.getStringArrayListExtra("name");
		sendMobileList = intent.getStringArrayListExtra("mobile");
		
		findViews();

		initViews();
		
		sendSmsReceier();
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});

		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String msString = msEditText.getText().toString();
				
				SmsManager smsManager = SmsManager.getDefault();  //得到短信管理器

				//由于短信可能较长，故将短信拆分
				for (String phoneString : sendMobileList) {
					
					List<String> texts = smsManager.divideMessage(msString);
					for (String text : texts) {
						smsManager.sendTextMessage(phoneString, null, text, null, null);// 分别发送每一条短信
					}
				}
				
				onBackPressed();
			}
		});

	}

	private void initViews() {
		// TODO Auto-generated method stub
		
		toTextView.setMovementMethod(new ScrollingMovementMethod());
		
		String sendToString = "";
		int loop = sendNameList.size();
				
		for (int i = 0; i < loop; i++) {
			sendToString = sendToString + sendNameList.get(i) + "<" + sendMobileList.get(i) + ">，";
		}
		
		sendToString = sendToString.length()>0?sendToString.substring(0, sendToString.length()-1):"";
		toTextView.setText(sendToString);
		
		Log.d(TAG, sendToString);
	} 
 	
	// 获取View组件
	private void findViews() {
		// TODO Auto-generated method stub
		toTextView = (TextView) findViewById(R.id.to_textview);
		msEditText = (EditText) findViewById(R.id.ms_editview);
		sendButton = (Button) findViewById(R.id.send_btn);
		backButton = (ImageButton) findViewById(R.id.back_btn);
	}

	private void sendSmsReceier() {

		String SENT_SMS_ACTION = "SENT_SMS_ACTION";
//		Intent sentIntent = new Intent(SENT_SMS_ACTION);
//		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);
		
		// register the Broadcast Receivers
		registerReceiver(myBroadcastReceiver, new IntentFilter(SENT_SMS_ACTION));
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		unregisterReceiver(myBroadcastReceiver);
	}

	
}
