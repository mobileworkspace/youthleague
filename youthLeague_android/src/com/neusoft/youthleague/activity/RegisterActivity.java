package com.neusoft.youthleague.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.neusoft.youthleague.R;
import com.neusoft.youthleague.exception.ConstantValues;
import com.neusoft.youthleague.model.DBService;
import com.neusoft.youthleague.model.Department;
import com.neusoft.youthleague.model.Organization;
import com.neusoft.youthleague.model.Position;
import com.neusoft.youthleague.model.Staff;
import com.neusoft.youthleague.utils.RegisterStaff;

public class RegisterActivity extends Activity {

//	private static final String TAG = "Register";
	
	// 以下是UI -----------------------------------------------------
	private EditText mobileEditText;
	private EditText userNameEditText;
	private EditText phoneEditText;
//	private EditText emailEditText;
	private EditText passwordEditText;
	
	private View settingContainer;
	
	private Spinner departmentSpinner;
	private Spinner positionSpinner;
	private Spinner organizationSpinner;
	
	private CheckBox forgetMeCheckBox;
	private CheckBox cancelAutoLoginCheckBox;
	
	private ImageButton backButton;
	private Button cancelButton;
	private Button registerButton;
	
	private ProgressDialog hintDialog;
	// UI -----------------------------------------------------
	
	private List<String> departmentStrings;
	private List<String> positionStrings;
	private List<String> organizationStrings;
	
	private List<Department> departmentList;
	private List<Position> positionList;
	private List<Organization> organizationList;
	
	private ArrayAdapter<String> departmentAdapter;
	private ArrayAdapter<String> positionAdapter;
	private ArrayAdapter<String> organizationAdapter;
	
	private Staff originalStaff;
	private SharedPreferences prefs;
	
	private String mobileNo;
	private String nameString;
	private String phoneString;
	private String pwdString;
	private int departmentId;
	private int positionId;
	private int organizationId;
	private String actionString;
	
	@SuppressLint("HandlerLeak") 
	private Handler RegisterHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			
				case ConstantValues.UPDATE_DB_START:
					showHintWindow(getString(R.string.hint_upload_date),0);
					break;
					
				case ConstantValues.UPDATE_DB_FAIL:
					hideHintWindow();
					Toast.makeText(RegisterActivity.this, getString(R.string.hint_fail), Toast.LENGTH_LONG).show();
					break;
				
//				case ConstantValues.UPDATE_DB_NET_ERROR:
//					hideHintWindow();
//					Toast.makeText(RegisterActivity.this, getString(R.string.download_net_error), Toast.LENGTH_LONG).show();
//					break;
					
				case ConstantValues.UPDATE_DB_FILE_ERROR:
					hideHintWindow();
					Toast.makeText(RegisterActivity.this, getString(R.string.hint_staff_exist), Toast.LENGTH_LONG).show();
					break;
					
				case ConstantValues.UPDATE_DB_FINISH:
					
					if (originalStaff!=null) {
						
						Boolean flag = updateToDb();
						
						if (flag) {
							
							originalStaff.setMobile(mobileNo);
							originalStaff.setName(nameString);
							originalStaff.setPwd(pwdString);
							originalStaff.setPhone(phoneString);
							originalStaff.setDepartmentId(departmentId);
							originalStaff.setPositionId(positionId);
							originalStaff.setOrganizationId(organizationId);
							
							Toast.makeText(RegisterActivity.this, getString(R.string.hint_success_modify), Toast.LENGTH_LONG).show();
							
							Intent intent = new Intent();
							Bundle bundle = new Bundle();
							bundle.putSerializable("login_staff", originalStaff);
							intent.putExtras(bundle);
							setResult(RESULT_OK, intent);
							
							onBackPressed();
							
						} else {
							Toast.makeText(RegisterActivity.this, getString(R.string.hint_fail_modify), Toast.LENGTH_LONG).show();
						}
						
					}else{
						hideHintWindow();
						Toast.makeText(RegisterActivity.this, getString(R.string.hint_success), Toast.LENGTH_LONG).show();
					}

					
					
					break;
					
			}
		}
		
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);
        
        Intent intent = getIntent();
        originalStaff = (Staff) intent.getSerializableExtra("login_staff");
        
        findViews();
        
        initView();
        
        cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}

		});
        
        registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (forgetMeCheckBox.isChecked()) {
					Editor editor = prefs.edit();
					editor.putBoolean("SHARE_REMEMBER_ME", false);
					editor.remove("SHARE_LOGIN_USERNAME");
					editor.remove("SHARE_LOGIN_PASSWORD");
					editor.commit();
				}
				
				if (cancelAutoLoginCheckBox.isChecked()) {
					prefs.edit().putBoolean("SHARE_LOGIN_AUTO", false).commit();
				}
				
				mobileNo = mobileEditText.getText().toString();
				
				if (isCellphone(mobileNo)) {
					
					nameString = userNameEditText.getText().toString();
					phoneString = phoneEditText.getText().toString();
					pwdString = passwordEditText.getText().toString();
					
					departmentId = departmentList.get(departmentSpinner.getSelectedItemPosition()).getId();
					positionId = positionList.get(positionSpinner.getSelectedItemPosition()).getId();
					organizationId = organizationList.get(organizationSpinner.getSelectedItemPosition()).getId();
					
					RegisterStaff registerStaff = new RegisterStaff(RegisterActivity.this, RegisterHandler);
					
					String IdString = "";
					//新增用户
					if (originalStaff==null) {
						actionString = "logon";
					}else {
						actionString = "oneStaffupdate";
						IdString = originalStaff.getId() + "";
					}
					
					registerStaff.execute(mobileNo, nameString, pwdString, phoneString, "" + departmentId, 
							              "" + positionId, "" + organizationId, IdString, actionString);
				}else {
					Toast.makeText(RegisterActivity.this, getString(R.string.hint_wrong_mobile), Toast.LENGTH_LONG).show();
				}
			}


//			private boolean insertToDb(int newId, String mobileNo, String nameString, String pwdString, String phoneString,
//					                   int departmentId, int positionId, int organizationId) {
//				
//				Staff staff = new Staff();
//				staff.setId(newId);
//				staff.setMobile(mobileNo);
//				staff.setPhone(phoneString);
//				staff.setName(nameString);
//				staff.setPwd(pwdString);
//				staff.setDepartmentId(departmentId);
//				staff.setPositionId(positionId);
//				staff.setOrganizationId(organizationId);
//				
//				DBService dbService = new DBService(RegisterActivity.this);
//				return dbService.registerStaff(staff);
//			}


			
//			private boolean isEmail(String emailString) {
//				String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
//				Pattern p = Pattern.compile(strPattern);
//				Matcher m = p.matcher(emailString);
//				
//				if (m.matches()) {
//					return true;
//				} else {
//					return false;
//				}
//			}
			
			private boolean isCellphone(String mobileNo) {
				Pattern pattern = Pattern.compile("^13[0-9]{1}[0-9]{8}$|15[0125689]{1}[0-9]{8}$|18[0-3,5-9]{1}[0-9]{8}$");  //"1[0-9]{10}");
				Matcher matcher = pattern.matcher(mobileNo);
				if (matcher.matches()) {
					return true;
				} else {
					return false;
				}
			}
			
		});
    }

    // 获取View组件
 	private void findViews() {
 		// TODO Auto-generated method stub
 		mobileEditText = (EditText) findViewById(R.id.mobile_edittext);
 		userNameEditText = (EditText) findViewById(R.id.name_edittext);
 		phoneEditText = (EditText) findViewById(R.id.phone_edittext);
// 		emailEditText = (EditText) findViewById(R.id.email_edittext);
 		passwordEditText = (EditText) findViewById(R.id.pwd_edittext);
 		
 		departmentSpinner = (Spinner) findViewById(R.id.department);
 		positionSpinner = (Spinner) findViewById(R.id.position);
 		organizationSpinner = (Spinner) findViewById(R.id.organization);
 		
 		forgetMeCheckBox = (CheckBox) findViewById(R.id.forgetCheckBox);
 		cancelAutoLoginCheckBox = (CheckBox) findViewById(R.id.cancelAutoLoginCheckBox);
 
 		backButton = (ImageButton) findViewById(R.id.back_btn);
 		cancelButton = (Button) findViewById(R.id.cancel_btn);
 		registerButton = (Button) findViewById(R.id.confirm_btn);
 		
 		settingContainer = findViewById(R.id.container);
 	}
 	
    private void initView() {
		// TODO Auto-generated method stub
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});

		getDataFromDb();
		
		if (originalStaff!=null) {
			
			settingContainer.setVisibility(View.VISIBLE);
			
			mobileEditText.setText(originalStaff.getMobile());
			userNameEditText.setText(originalStaff.getName());
			phoneEditText.setText(originalStaff.getPhone());
			passwordEditText.setText(originalStaff.getPwd());
			
			registerButton.setText(getString(R.string.ok_btn));
			
			int count = departmentList.size();
			for (int i=0; i<count; i++) {
				if (departmentList.get(i).getId()==originalStaff.getDepartmentId()) {
					departmentSpinner.setSelection(i);
					break;
				}
			}
			
			count = positionList.size();
			for (int i=0; i<count; i++) {
				if (positionList.get(i).getId()==originalStaff.getPositionId()) {
					positionSpinner.setSelection(i);
					break;
				}
			}
			
			count = organizationList.size();
			for (int i=0; i<count; i++) {
				if (organizationList.get(i).getId()==originalStaff.getOrganizationId()) {
					organizationSpinner.setSelection(i);
					break;
				}
			}
			
		}else {
			settingContainer.setVisibility(View.GONE);
		}
	}
	
	private void getDataFromDb() {
		
		DBService dbService = new DBService(this);
		departmentList = dbService.getAllDepartment();
		positionList = dbService.getAllPosition();
		organizationList = dbService.getAllOrganization();
		
		departmentStrings = new ArrayList<String>();
		for (Department departmentItem : departmentList) {
			departmentStrings.add(departmentItem.getName());
		}
		departmentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departmentStrings);
		departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		departmentSpinner.setAdapter(departmentAdapter);
		departmentSpinner.setPrompt("请选择所属部门");
		
		positionStrings = new ArrayList<String>();
		for (Position positionItem : positionList) {
			positionStrings.add(positionItem.getName());
		}
		positionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, positionStrings);
		positionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		positionSpinner.setAdapter(positionAdapter);
		positionSpinner.setPrompt("请选择职位");
		
		organizationStrings = new ArrayList<String>();
		for (Organization organizationItem : organizationList) {
			organizationStrings.add(organizationItem.getName());
		}
		organizationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, organizationStrings);
		organizationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		organizationSpinner.setAdapter(organizationAdapter);
		organizationSpinner.setPrompt("请选择所属组织");
	}


/*	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/
    
	private Boolean updateToDb() {
		// TODO Auto-generated method stub
		Staff staff = new Staff();
		staff.setId(originalStaff.getId());
		staff.setMobile(mobileNo);
		staff.setPhone(phoneString);
		staff.setName(nameString);
		staff.setPwd(pwdString);
		staff.setDepartmentId(departmentId);
		staff.setPositionId(positionId);
		staff.setOrganizationId(organizationId);
		
		DBService dbService = new DBService(RegisterActivity.this);
		
		return dbService.updateStaff(staff);
	}
	
	private void hideHintWindow() {
		if (null!=hintDialog && hintDialog.isShowing()) {
			hintDialog.hide();
		}
	}
	
	private void showHintWindow(String message, int length) {

		if (null==hintDialog) {
			hintDialog = new ProgressDialog(this);
			hintDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			hintDialog.setMessage(message);
			hintDialog.setMax(100);
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

}
