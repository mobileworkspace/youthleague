package com.neusoft.youthleague.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.neusoft.youthleague.R;
import com.neusoft.youthleague.model.DBService;
import com.neusoft.youthleague.model.Department;
import com.neusoft.youthleague.model.Organization;
import com.neusoft.youthleague.model.Position;
import com.neusoft.youthleague.model.Staff;

public class SettingActivity extends Activity {

	private static final String TAG = "Setting";
	
	// 以下是UI -----------------------------------------------------
	private EditText phoneEditText;
	private EditText userNameEditView;
//	private EditText emailEditText;
	private EditText passwordEditView;
	
	private Spinner departmentSpinner;
	private Spinner positionSpinner;
	private Spinner organizationSpinner;
	
	private CheckBox forgetMeCheckBox;
	private CheckBox cancelAutoLoginCheckBox;
	
	private Button cancelButton;
	private Button registerButton;
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
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting);
        
        Intent intent = getIntent();
        originalStaff = (Staff) intent.getSerializableExtra("staff");
        
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
				
				String mobileNo = phoneEditText.getText().toString();
				
//				String emailString = emailEditText.getText().toString();

				
				if (isCellphone(mobileNo)) {
					
					String nameString = userNameEditView.getText().toString();
					String pwdString = passwordEditView.getText().toString();
					
					int departmentId = departmentList.get(departmentSpinner.getSelectedItemPosition()).getId();
					int positionId = positionList.get(positionSpinner.getSelectedItemPosition()).getId();
					int organizationId = organizationList.get(organizationSpinner.getSelectedItemPosition()).getId();
					
					boolean flag = false;
					
					flag = uploadToServer(mobileNo, nameString, pwdString,
			                              departmentId, positionId, organizationId);
					
					if (flag) {
						
						flag = updateToDb(mobileNo, nameString, pwdString,
						          departmentId, positionId, organizationId);
						if (flag) {
							Toast.makeText(SettingActivity.this, getString(R.string.hint_success), Toast.LENGTH_LONG).show();
						} 
						
					}else {
						Toast.makeText(SettingActivity.this, getString(R.string.hint_fail), Toast.LENGTH_LONG).show();
					}
					
				}else {
					Toast.makeText(SettingActivity.this, getString(R.string.hint_wrong_mobile), Toast.LENGTH_LONG).show();
				}
			}

			private boolean uploadToServer(String mobileNo, String nameString, String pwdString, 
					                       int departmentId, int positionId, int organizationId){
				
				return true;
			}
			
			private boolean updateToDb(String mobileNo, String nameString, String pwdString,
					                   int departmentId, int positionId, int organizationId) {
				
				Staff staff = new Staff();
				staff.setId(originalStaff.getId());
				staff.setPhone(mobileNo);
				staff.setName(nameString);
				staff.setPwd(pwdString);
				staff.setDepartmentId(departmentId);
				staff.setPositionId(positionId);
				staff.setOrganizationId(organizationId);
				
				DBService dbService = new DBService(SettingActivity.this);
				return dbService.updateStaff(staff);
			}

			private boolean isEmail(String emailString) {
				String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
				Pattern p = Pattern.compile(strPattern);
				Matcher m = p.matcher(emailString);
				
				if (m.matches()) {
					return true;
				} else {
					return false;
				}
			}
			
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
 		phoneEditText = (EditText) findViewById(R.id.phone_edittext);
 		userNameEditView = (EditText) findViewById(R.id.name_edittext);
// 		emailEditText = (EditText) findViewById(R.id.email_edittext);
 		passwordEditView = (EditText) findViewById(R.id.pwd_edittext);
 		
 		departmentSpinner = (Spinner) findViewById(R.id.department);
 		positionSpinner = (Spinner) findViewById(R.id.position);
 		organizationSpinner = (Spinner) findViewById(R.id.organization);
 		
 		forgetMeCheckBox = (CheckBox) findViewById(R.id.forgetCheckBox);
 		cancelAutoLoginCheckBox = (CheckBox) findViewById(R.id.cancelAutoLoginCheckBox);
 		
 		cancelButton = (Button) findViewById(R.id.cancel_btn);
 		registerButton = (Button) findViewById(R.id.confirm_btn);
 	}
 	
    private void initView() {
		// TODO Auto-generated method stub
    	prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	
    	getDataFromDb();
    	
    	if (originalStaff!=null) {
    		
			userNameEditView.setText(originalStaff.getName());
			phoneEditText.setText(originalStaff.getPhone());
			passwordEditView.setText(originalStaff.getPwd());
			
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


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
