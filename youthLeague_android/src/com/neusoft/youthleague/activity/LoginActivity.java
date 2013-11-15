package com.neusoft.youthleague.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Selection;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.neusoft.youthleague.R;
import com.neusoft.youthleague.exception.ConstantValues;
import com.neusoft.youthleague.model.DBService;
import com.neusoft.youthleague.model.Staff;
import com.neusoft.youthleague.utils.CheckDatabase;
import com.neusoft.youthleague.utils.HttpService;
import com.neusoft.youthleague.utils.UpdateDatabase;

public class LoginActivity extends Activity {

	private static final String TAG = "Login";
	
	// 以下是UI -----------------------------------------------------
	private EditText userNameEditView;
	private EditText passwordEditView;
	
	private CheckBox rememberMeChk;
	private CheckBox autologinChk;
	private CheckBox visiblePwdChk;
	
	private ProgressDialog hintDialog;
	
	private Button loginButton;
	private Button registerButton;
	
	private String updateDateString;
	private String oldUpdateDateString;
	private SharedPreferences prefs;
	
	@SuppressLint("HandlerLeak") 
	private Handler HomeHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			
				case ConstantValues.UPDATE_DB_START:
					showHintWindow(getString(R.string.downloading), 10, msg.arg2);
					break;
					
				case ConstantValues.UPDATE_DB_FAIL:
					hideHintWindow();
					Toast.makeText(LoginActivity.this, getString(R.string.download_fail), Toast.LENGTH_LONG).show();
					break;
				
				case ConstantValues.UPDATE_DB_NET_ERROR:
					hideHintWindow();
					Toast.makeText(LoginActivity.this, getString(R.string.download_net_error), Toast.LENGTH_LONG).show();
					break;
					
				case ConstantValues.UPDATE_DB_FINISH:
					hideHintWindow();
					prefs.edit().putString("SHARE_UPDATE_DB_DATE", updateDateString).commit();
					break;
					
				case ConstantValues.UPDATE_DB_PROGRESS:
					showHintWindow(getString(R.string.downloading), msg.arg1, msg.arg2);
					break;	
					
				case ConstantValues.CHECK_DB_ERROR:
					hideHintWindow();
					Toast.makeText(LoginActivity.this, getString(R.string.download_fail), Toast.LENGTH_LONG).show();
					break;	
					
				case ConstantValues.CHECK_DB_FINISH:
					
					try {
						
						String lastDateString = "197001010001";
						oldUpdateDateString = prefs.getString("SHARE_UPDATE_DB_DATE", "197001010001");
						String isExistString = "0";
						
						updateDateString = (String) msg.obj;
						lastDateString = updateDateString.substring(0, updateDateString.length()-2);
						isExistString = updateDateString.substring(updateDateString.length()-2, updateDateString.length()-1);
						updateDateString = lastDateString;
						
						Log.d(TAG, "isExist=[" + isExistString + "]");
						Log.d(TAG, "lastDate=[" + lastDateString + "]");
						
						if ("0".equals(isExistString)) {
							
							hideHintWindow();
							Toast.makeText(LoginActivity.this, getString(R.string.download_no_file), Toast.LENGTH_LONG).show();
//							HomeHandler.sendEmptyMessage(ConstantValues.UPDATE_DB_NO_EXIST);
							
						}else {
							
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
							
							Date lastDate = dateFormat.parse(lastDateString);
							Calendar calendarServer = Calendar.getInstance();
							calendarServer.setTime(lastDate);
							
							Date oldDate = dateFormat.parse(oldUpdateDateString);
							Calendar calendarLocal = Calendar.getInstance();
							calendarLocal.setTime(oldDate);
							
							if (calendarServer.after(calendarLocal)) {
								//升级数据
								UpdateDatabase updateDatabase = new UpdateDatabase(LoginActivity.this, HomeHandler);
								updateDatabase.execute(ConstantValues.UPDATE_DB_DATA_URL);
							}else{
								hideHintWindow();
								Toast.makeText(LoginActivity.this, getString(R.string.no_need_update), Toast.LENGTH_LONG).show();
							}
						}

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						hideHintWindow();
						Toast.makeText(LoginActivity.this, getString(R.string.no_need_update), Toast.LENGTH_LONG).show();
					}
					
					break;
			}
		}
		
	};
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (null!=hintDialog) {
			hintDialog.dismiss();
		}
	}
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        
        findViews();
        
        initView();
        
        visiblePwdChk.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (visiblePwdChk.isChecked()) {
					//文本正常显示  
					passwordEditView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				} else {
					//文本以密码形式显示
					passwordEditView.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
				
				//下面两行代码实现: 输入框光标一直在输入文本后面      
				Editable etable = passwordEditView.getText();              
				Selection.setSelection(etable, etable.length());
				passwordEditView.postInvalidate();
			}
		});
        
        loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String mobileNo = userNameEditView.getText().toString();
				String pwdString = passwordEditView.getText().toString();
				
				if (isCellphone(mobileNo)) {
					login(mobileNo, pwdString);
				}else {
					Toast.makeText(LoginActivity.this, getString(R.string.hint_wrong_mobile), Toast.LENGTH_LONG).show();
				}
			}

			private boolean isCellphone(String mobileNo) {
//				Pattern pattern = Pattern.compile("^13[0-9]{1}[0-9]{8}$|15[0125689]{1}[0-9]{8}$|18[0-3,5-9]{1}[0-9]{8}$");  //"1[0-9]{10}");
//				Matcher matcher = pattern.matcher(mobileNo);
//				if (matcher.matches()) {
//					return true;
//				} else {
//					return false;
//				}
				
				return true;
			}

//			public boolean isEmail(String strEmail) {
//				String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
//				Pattern p = Pattern.compile(strPattern);
//				Matcher m = p.matcher(strEmail);
//				
//				if (m.matches()) {
//					return true;
//				} else {
//					return false;
//				}
//			}

		});
        
        registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent("com.neusoft.REGISTER");
//				startActivity(intent);
				updateData();
			}
		});
    }

    // 获取View组件
 	private void findViews() {
 		// TODO Auto-generated method stub
 		userNameEditView = (EditText) findViewById(R.id.loginUserNameEdit);
 		passwordEditView = (EditText) findViewById(R.id.loginPasswordEdit);
 		
 		rememberMeChk = (CheckBox) findViewById(R.id.loginRememberMeCheckBox);
 		autologinChk = (CheckBox) findViewById(R.id.autoLoginCheckBox);
 		visiblePwdChk = (CheckBox) findViewById(R.id.visiblePwdCheckBox);
 		
 		loginButton = (Button) findViewById(R.id.loginSubmit);
 		registerButton = (Button) findViewById(R.id.regSubmit);
 	}
 	
    private void initView() {
		// TODO Auto-generated method stub
    	
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		String userName = prefs.getString("SHARE_LOGIN_USERNAME", "");
		String password = prefs.getString("SHARE_LOGIN_PASSWORD", "");
		
		Boolean rememberMeBoolean = prefs.getBoolean("SHARE_REMEMBER_ME", false);
		Boolean autoLoginBoolean = prefs.getBoolean("SHARE_LOGIN_AUTO", false);
		Boolean visiblePwdBoolean = prefs.getBoolean("SHARE_VISIBLE_PWD", false);
		
		if (rememberMeBoolean) {
			
			userNameEditView.setText(userName);
			passwordEditView.setText(password);
			rememberMeChk.setChecked(rememberMeBoolean);
			autologinChk.setChecked(autoLoginBoolean);
			visiblePwdChk.setChecked(visiblePwdBoolean);
			
			if (autoLoginBoolean) {
				doLogin(userName, password); //开始自动登录
			}
			
		}else {
			
			TelephonyManager telephoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);  
			String mobileNo = telephoneManager.getLine1Number();
			
			if (mobileNo!=null) {
				userNameEditView.setText(mobileNo);
			}
		}
	}

    private void doLogin(String mobileNo, String pwdString) {

    	DBService dbService = new DBService(this);
		Staff staff = dbService.staffLogin(mobileNo, pwdString);
		
		if (staff!=null) {
			Intent intent = new Intent("com.neusoft.HOME");
			Bundle bundle = new Bundle();
			bundle.putSerializable("login_user", staff);
			intent.putExtras(bundle);
			startActivity(intent);
		}else {
			Toast.makeText(this, getString(R.string.hint_no_user), Toast.LENGTH_LONG).show();
		}
	}

    private void login(String mobileNo, String pwdString) {

    	Log.d(TAG, "userName=[" + mobileNo + "]  password=[" + pwdString + "]  进行登录中...");
    	
    	Editor editor = prefs.edit();
    	
    	if (rememberMeChk.isChecked()) {
    		editor.putBoolean("SHARE_REMEMBER_ME", true);
    		editor.putString("SHARE_LOGIN_USERNAME", mobileNo);
    		editor.putString("SHARE_LOGIN_PASSWORD", pwdString);
		}
    	
    	if (autologinChk.isChecked()) {
    		editor.putBoolean("SHARE_LOGIN_AUTO", true);
		}
    	
    	editor.commit();
    	
    	doLogin(mobileNo, pwdString);
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
	private void updateData() {
		
		if (HttpService.isNetworkAvailable(this)) {
			
			//首先查看需要不需要升级
			CheckDatabase checkDatabase = new CheckDatabase(this, HomeHandler);
			checkDatabase.execute("");
			
		}else {
			Toast.makeText(this, getString(R.string.download_net_error), Toast.LENGTH_LONG).show();
		}
	}
	
	private void showHintWindow(String message, int length, int count) {

		if (null==hintDialog) {
			hintDialog = new ProgressDialog(this);
			hintDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			hintDialog.setMessage(message);
			hintDialog.setMax(count);
			hintDialog.setProgress(0);
			hintDialog.setCancelable(true);
		}else {
			if (hintDialog.isShowing()) {
				hintDialog.hide(); 
			}
			hintDialog.setMessage(message);
			hintDialog.setProgress(length);
		}
		hintDialog.show();
	}
	
	private void hideHintWindow() {
		if (null!=hintDialog && hintDialog.isShowing()) {
			hintDialog.hide();
		}
	}
}
