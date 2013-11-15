package com.neusoft.youthleague.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.youthleague.R;
import com.neusoft.youthleague.exception.ConstantValues;
import com.neusoft.youthleague.exception.InternetException;
import com.neusoft.youthleague.model.DBService;
import com.neusoft.youthleague.model.Department;
import com.neusoft.youthleague.model.ItemPosition;
import com.neusoft.youthleague.model.Organization;
import com.neusoft.youthleague.model.Staff;
import com.neusoft.youthleague.utils.Holder;
import com.neusoft.youthleague.utils.HttpService;
import com.neusoft.youthleague.utils.MyExpandableListAdapter;
import com.neusoft.youthleague.utils.SearchAdapter;
import com.neusoft.youthleague.utils.UpdateDatabase;
import com.neusoft.youthleague.view.MultiDirectionSlidingDrawer;
import com.neusoft.youthleague.wheelView.ArrayWheelAdapter;
import com.neusoft.youthleague.wheelView.OnWheelChangedListener;
import com.neusoft.youthleague.wheelView.WheelView;

public class HomeActivity extends Activity {

	private static final String TAG = "Home";
	
	private static final int SPAN_X = 80;
	private static final int VELOCITY_X = 50;
	private static final int SPAN_Y = 50;
	private static final int VELOCITY_Y = 50;
	// 以下是UI -----------------------------------------------------
	private ImageButton staffDetailButton;
	private ImageButton refreshButton;
	private TextView titleTextView;
	private ProgressBar refreshProgressBar;
	
	private ExpandableListView userListView;
	private ListView searchUserListView;
	
	private MultiDirectionSlidingDrawer slidingdrawer;
	private ImageView handleImageView;
	private WheelView superWheelView;
	private WheelView wheelView;
	
	private AutoCompleteTextView searchTextView;
	private ImageView searchButton;
	private LinearLayout searchContainer;
	
	private ProgressDialog hintDialog;
	
//	private View popView;
//	private PopupWindow popupWindow;
	// -----------------------------------------------------
	
	/*----------wheel view ---------------------*/
	private String[] superUnitStrings;
	private String[][] childUnitStrings;
	
	private Map<Integer, List<com.neusoft.youthleague.model.Organization>> childOrganziations;
	private List<com.neusoft.youthleague.model.Organization> superOrganizations;

	private ArrayWheelAdapter<String> childArrayWheelAdapter;
	/*----------wheel view---------------------*/
	
	private ItemPosition childPositionByTouch;
	private GestureDetector gestureDetector;
	private MyExpandableListAdapter staffAdapter;
	private Map<Integer,List<Staff>> itemList;
	private List<Department> groupList;
    
	private Staff staffByTouch;
	private GestureDetector searchGestureDetector;
	private SearchAdapter searchAdapter;
	private List<Staff> searchStaffList;
	
	private SharedPreferences prefs;
	private String oldUpdateDateString;
	private String updateDateString;
	
	private Staff loginStaff;
	
	private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context _context, Intent _intent) {
			Toast.makeText(HomeActivity.this, getString(R.string.sms_feedback), Toast.LENGTH_SHORT).show();
		}
		
	};
	
	@SuppressLint("HandlerLeak") 
	private Handler HomeHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			
				case ConstantValues.UPDATE_DB_START:
					showUpdateProgressBar();
					showHintWindow(getString(R.string.downloading), 0, msg.arg2);
					break;
					
				case ConstantValues.UPDATE_DB_FAIL:
					hideUpdateProgressBar();
					hideHintWindow();
					Toast.makeText(HomeActivity.this, getString(R.string.download_fail), Toast.LENGTH_LONG).show();
					break;
				
				case ConstantValues.UPDATE_DB_NET_ERROR:
					hideUpdateProgressBar();
					hideHintWindow();
					Toast.makeText(HomeActivity.this, getString(R.string.download_net_error), Toast.LENGTH_LONG).show();
					break;
					
				case ConstantValues.UPDATE_DB_FINISH:
					hideUpdateProgressBar();
					hideHintWindow();
					initPicker();
					prefs.edit().putString("SHARE_UPDATE_DB_DATE", updateDateString).commit();
					break;
					
				case ConstantValues.UPDATE_DB_PROGRESS:
					showHintWindow(getString(R.string.downloading), msg.arg1, msg.arg2);
					break;	
			}
		}
		
	};
	

	

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
//    	SubMenu sendSmsMenu = menu.addSubMenu("发送短信");
//    	sendSmsMenu.setIcon(icon);
    	
    	menu.add(0, 0x111, 0, "发送短信");
    	menu.getItem(0).setIcon(R.drawable.msg_fjx);
    	
		return super.onCreateOptionsMenu(menu);
	}
    


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId()==0x111) {
			
			List<Staff> tempList;
			ArrayList<String> sendNameList = new ArrayList<String>();
			ArrayList<String> sendMobileList = new ArrayList<String>();
			
			if (userListView.getVisibility()==View.VISIBLE) {
				
				int idKey, loop = groupList.size();
				for (int j = 0; j < loop; j++) {
					
					idKey= groupList.get(j).getId();
					
					tempList = itemList.get(idKey);
					
					for (Staff staff : tempList) {
						if (staff.isSelected()) {
							sendNameList.add(staff.getName());
							sendMobileList.add(staff.getMobile());
						}
					}
				}
				
			}else {
				
				for (Staff staff : searchStaffList) {
					if (staff.isSelected()) {
						sendNameList.add(staff.getName());
						sendMobileList.add(staff.getMobile());
					}
				}
			}

			if (sendNameList.size()>0) {
				
				Intent intent = new Intent();
				intent.setAction("com.neusoft.SMS");
				intent.putStringArrayListExtra("name", sendNameList);
				intent.putStringArrayListExtra("mobile", sendMobileList);
				startActivity(intent);
			}
			
		}
		return true;
	}
	


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home);
        
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
        	loginStaff = (Staff) bundle.getSerializable("login_user");
		}
        
        findViews();
        
        initView();
        
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        searchGestureDetector = new GestureDetector(this, new SecondGestureDetector());
        
        //对Expanablelistview中的子view增加手势（双击和滑动）      
        userListView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				
				int x = (int) event.getX();
				int y = (int) event.getY();
				
				//根据单击位置，计算出对应的View
				int touchPoint = userListView.pointToPosition(x, y);
				View itemView = (View) userListView.getChildAt(touchPoint - userListView.getFirstVisiblePosition());
				
				if (itemView!=null && itemView.getTag()!=null) {
 
					Holder holder = (Holder) itemView.getTag();
					if (holder.getStaffTag()!=null) {
						staffByTouch = (Staff) holder.getStaffTag();
						return gestureDetector.onTouchEvent(event);
					}else {
						return false;
					}
					
				}else {
					return false;
				}
				
			}
		});
       
        //对listview中的view增加手势（长按和滑动）
        searchUserListView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int x = (int) event.getX();
				int y = (int) event.getY();
				
				//根据单击位置，计算出对应的View
				int touchPoint = searchUserListView.pointToPosition(x, y);
				View itemView = (View) searchUserListView.getChildAt(touchPoint - searchUserListView.getFirstVisiblePosition());
				
				if (itemView!=null && itemView.getTag()!=null) {
					
					Holder holder = (Holder) itemView.getTag();
					staffByTouch = (Staff) holder.getStaffTag();
					
					return searchGestureDetector.onTouchEvent(event);
				}else {
					return false;
				}
			}
		});
        
        staffDetailButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("com.neusoft.REGISTER");
				Bundle bundle = new Bundle();
				bundle.putSerializable("login_staff", loginStaff);
				intent.putExtras(bundle);
				startActivityForResult(intent, 0);
			}

		});
        
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				userListView.setVisibility(View.GONE);
				searchUserListView.setVisibility(View.VISIBLE);
				
				String searchString = searchTextView.getText().toString();
				if (searchString.length()>0) {
					DBService dbService = new DBService(HomeActivity.this);
					searchStaffList = dbService.searchStaff(searchString);
					
					searchAdapter = new SearchAdapter(HomeActivity.this, searchStaffList);
					searchUserListView.setAdapter(searchAdapter);
					
					searchContainer.requestFocus();
				}
			}
		});
		
        //手动升级数据库
        refreshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateData();
			}

		});
        
        //自动升级数据库(每月的1号和15号升级)
        Date date = new Date();
        if (date.getDate()==15 || date.getDate()==1) {
        	updateData();
		}
        
    }
    

    // 获取View组件
 	private void findViews() {
 		// TODO Auto-generated method stub
 		userListView = (ExpandableListView) findViewById(R.id.staff_listview);
 		searchUserListView = (ListView) findViewById(R.id.search_listview);
 		
 		slidingdrawer = (MultiDirectionSlidingDrawer) findViewById(R.id.slidingdrawer);
 		superWheelView = (WheelView) findViewById(R.id.super_wheel_view);
 		wheelView = (WheelView) findViewById(R.id.wheel_view);
 		handleImageView = (ImageView) findViewById(R.id.handle);
 		
 		staffDetailButton = (ImageButton) findViewById(R.id.exit_btn);
 		refreshButton = (ImageButton) findViewById(R.id.img_refresh);
 		titleTextView = (TextView) findViewById(R.id.nuit_view);
 		refreshProgressBar = (ProgressBar) findViewById(R.id.progress_refresh);
 		
 		searchTextView = (AutoCompleteTextView) findViewById(R.id.search_content);
 		searchButton = (ImageView) findViewById(R.id.search_btn);
 		searchContainer = (LinearLayout) findViewById(R.id.search);
 	}
 	
 	
    private void initView() {
		// TODO Auto-generated method stub

		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		oldUpdateDateString = prefs.getString("SHARE_UPDATE_DB_DATE", "197001010001");

		titleTextView.setText(getString(R.string.app_name));
		titleTextView.clearFocus();
		
		initPicker();
		
		//更新ListView
		if (superUnitStrings.length>0 && childUnitStrings.length>0) {
			updateListView(superOrganizations.get(0).getId(), 0);
		}
		
		superWheelView.addChangingListener(new OnWheelChangedListener() {
			
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {

				childArrayWheelAdapter = new ArrayWheelAdapter<String>(HomeActivity.this, childUnitStrings[newValue]);
				childArrayWheelAdapter.setTextSize(15);
				wheelView.setViewAdapter(childArrayWheelAdapter);
				
				if (childUnitStrings[newValue].length>0) {
					
					wheelView.setCurrentItem(0);
					
					//更新ListView
					updateListView(superOrganizations.get(newValue).getId(), 0);
				}
			}
		});
		
		wheelView.addChangingListener(new OnWheelChangedListener() {
			
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				//更新ListView
				updateListView(superOrganizations.get(superWheelView.getCurrentItem()).getId(), newValue);
			}

		});
		/*---------------------------about wheel view end---------------------------------------------*/
		
	}



	private void initPicker() {
		
		itemList = new HashMap<Integer, List<Staff>>();
		groupList = new ArrayList<Department>();
		
//		handleImageView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (!slidingdrawer.isOpened() && searchUserListView.getVisibility()==View.VISIBLE) {
//					searchUserListView.setVisibility(View.GONE);
//					userListView.setVisibility(View.VISIBLE);
//				}
//			}
//		});
		

		
		/*---------------------------about wheel view start---------------------------------------------*/
		superWheelView.setVisibleItems(5);
		wheelView.setVisibleItems(5);
		
		childOrganziations = new HashMap<Integer, List<Organization>>();
		superOrganizations = new ArrayList<Organization>();
		
		//从数据库获取机构的数据
		DBService dbService = new DBService(this);
		superOrganizations = dbService.getAllParentOrganization();
		
		//将数据组织成父-子关系
		for (Organization organization : superOrganizations) {
			List<com.neusoft.youthleague.model.Organization> items = new ArrayList<Organization>();
			items = dbService.getChildsOrganizationBySuperId(organization.getId());
			childOrganziations.put(organization.getId(), items);
		}
		
		//根据机构数据，组织在WheelView上显示的字符串数组
		int childCount, superId;
		int superCount = superOrganizations.size();
		superUnitStrings = new String[superCount];
		childUnitStrings = new String[superCount][];
		
		for (int i = 0; i < superCount; i++) {
			superId = superOrganizations.get(i).getId();
			superUnitStrings[i] = superOrganizations.get(i).getName();
			childCount = childOrganziations.get(superId).size();
			childUnitStrings[i] = new String[childCount];
			for (int j = 0; j < childCount; j++) {
				childUnitStrings[i][j] = childOrganziations.get(superId).get(j).getName();
			}
		}

		//设置两个滚轮的初始值
		ArrayWheelAdapter<String> superArrayWheelAdapter = new ArrayWheelAdapter<String>(this, superUnitStrings);
		superArrayWheelAdapter.setTextSize(15);
		superWheelView.setViewAdapter(superArrayWheelAdapter);
		if (superUnitStrings.length>0) {
			superWheelView.setCurrentItem(0);
		}
		
		if (childUnitStrings.length>0) {
			
			childArrayWheelAdapter = new ArrayWheelAdapter<String>(this, childUnitStrings[0]);
			childArrayWheelAdapter.setTextSize(15);
			wheelView.setViewAdapter(childArrayWheelAdapter);
		
			wheelView.setCurrentItem(0);
		}
	}

	private void updateListView(int superId, int childIndex) {
		// TODO Auto-generated method stub
		
		userListView.setVisibility(View.VISIBLE);
		searchUserListView.setVisibility(View.GONE);
		
		groupList.clear();
		itemList.clear();
		
		Organization organization = childOrganziations.get(superId).get(childIndex);
		DBService dbService = new DBService(this);
		List<Staff> staffs = dbService.getStaffsByOrganizationId(organization.getId());
		
		Department department;
		List<Staff> item;
		
		for (Staff staff : staffs) {
			
			department = new Department();
			
			int departmentId = staff.getDepartmentId();
			String departmentName = staff.getDepartmentName();
			department.setId(departmentId);
			department.setName(departmentName);
			
			Boolean flag = false;
			for (Department department2 : groupList) {
				if (departmentId==department2.getId()) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				groupList.add(department);
			}
			
			if (!itemList.containsKey(departmentId)) {
				item = new ArrayList<Staff>();
				item.add(staff);
				itemList.put(departmentId, item);
			}else {
				item = itemList.get(departmentId);
				item.add(staff);
			}
			
		}
		
		if (staffAdapter==null) {
			staffAdapter = new MyExpandableListAdapter(this, itemList, groupList);
			userListView.setAdapter(staffAdapter);
			Log.d(TAG, "create adapter");
		}else {
			staffAdapter.notifyDataSetChanged();
			Log.d(TAG, "adapter notify");
		}

	}
	
	private void updateData() {
		
		if (HttpService.isNetworkAvailable(this)) {
			
			showUpdateProgressBar();
			
			String lastDateString = "197001010001";
			oldUpdateDateString = prefs.getString("SHARE_UPDATE_DB_DATE", "197001010001");
			String isExistString = "0";
			
			try {
				
				//首先查看需要不需要升级
				updateDateString = HttpService.httpCheckVersion(ConstantValues.UPDATE_DB_CHECK_URL);
				lastDateString = updateDateString.substring(0, updateDateString.length()-2);
				isExistString = updateDateString.substring(updateDateString.length()-2, updateDateString.length()-1);
				updateDateString = lastDateString;
				
				Log.d(TAG, "isExist=[" + isExistString + "]");
				Log.d(TAG, "lastDate=[" + lastDateString + "]");
				
				if ("0".equals(isExistString)) {
					
					Toast.makeText(this, getString(R.string.download_no_file), Toast.LENGTH_LONG).show();
//					HomeHandler.sendEmptyMessage(ConstantValues.UPDATE_DB_NO_EXIST);
					
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
						UpdateDatabase updateDatabase = new UpdateDatabase(HomeActivity.this, HomeHandler);
						updateDatabase.execute(ConstantValues.UPDATE_DB_DATA_URL);
					}else{
						Toast.makeText(this, getString(R.string.no_need_update), Toast.LENGTH_LONG).show();
					}
				}

			} catch (InternetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(this, getString(R.string.download_fail), Toast.LENGTH_LONG).show();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(this, getString(R.string.download_fail), Toast.LENGTH_LONG).show();
			} finally{
				hideUpdateProgressBar();
			}
			
		}else {
			Toast.makeText(this, getString(R.string.download_net_error), Toast.LENGTH_LONG).show();
		}
	}
	
	private void showUpdateProgressBar() {
		//更新UI，启动更新动画
		refreshButton.setVisibility(View.GONE);
		refreshProgressBar.setVisibility(View.VISIBLE);
	}
	
	private void hideUpdateProgressBar() {
		//更新UI，关闭更新动画
		refreshButton.setVisibility(View.VISIBLE);
		refreshProgressBar.setVisibility(View.GONE);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//注册短信反馈    接收器
		smsFeedbackReceiver();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (null!=hintDialog) {
			hintDialog.dismiss();
		}
		//注销短信反馈    接收器
		try {
			unregisterReceiver(myBroadcastReceiver);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode==0 && resultCode==RESULT_OK) {
			loginStaff = (Staff) data.getExtras().get("login_staff");
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}


	private void smsFeedbackReceiver() {
		
		String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
		
		// create the deilverIntent parameter
//		Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
//		PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);
		
		registerReceiver(myBroadcastReceiver, new IntentFilter(DELIVERED_SMS_ACTION));

	}
	
	private void hideHintWindow() {
		if (null!=hintDialog && hintDialog.isShowing()) {
			hintDialog.hide();
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
    
	private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			super.onLongPress(e);
			
			Intent intent = new Intent("com.neusoft.DETAIL");
			intent.putExtra("staff", staffByTouch);
			startActivity(intent);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			
			Intent intent = new Intent();
			
			if (e1!=null && e2!=null) {
				
				float span = e2.getX() - e1.getX();
				float spanY = e2.getY() - e2.getY();
				
				if (Math.abs(spanY)<SPAN_Y && Math.abs(velocityY)<VELOCITY_Y) {
					
					if (Math.abs(span)>=SPAN_X && Math.abs(velocityX)>VELOCITY_X) {
						
						Log.d(TAG,"水平滑动!");
						
						if (span>0) { //右滑动
							
							if (!"".equals(staffByTouch.getMobile())) {
								intent.setAction(Intent.ACTION_CALL);
								intent.setData(Uri.parse("tel:" + staffByTouch.getMobile()));
								startActivity(intent);
							}else if (!"".equals(staffByTouch.getPhone())) {
								intent.setAction(Intent.ACTION_CALL);
								intent.setData(Uri.parse("tel:" + staffByTouch.getPhone()));
								startActivity(intent);
							} else {
								Toast.makeText(HomeActivity.this, getString(R.string.invaild_phone), Toast.LENGTH_LONG).show();
							}

						}else {  //左滑动
							if (!"".equals(staffByTouch.getMobile())) {
								ArrayList<String> sendNameList = new ArrayList<String>();
								ArrayList<String> sendMobileList = new ArrayList<String>();
								sendMobileList.add(staffByTouch.getMobile());
								sendNameList.add(staffByTouch.getName());
								intent.setAction("com.neusoft.SMS");
								intent.putExtra("name", sendNameList);
								intent.putExtra("mobile", sendMobileList);
								startActivity(intent);
							}else {
								Toast.makeText(HomeActivity.this, getString(R.string.invaild_phone), Toast.LENGTH_LONG).show();
							}
						}
					
					}
					
					return true;
					
				}else {
					Log.d(TAG,"垂直滑动!");
				}
			}
			
			return false;
		}
		
	}
	
	
	private class SecondGestureDetector extends GestureDetector.SimpleOnGestureListener {

		
		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			super.onLongPress(e);
			
			Intent intent = new Intent("com.neusoft.DETAIL");
			intent.putExtra("staff", staffByTouch);
			startActivity(intent);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			
			Intent intent = new Intent();
			
			if (e1!=null && e2!=null) {
				
				float span = e2.getX() - e1.getX();
				float spanY = e2.getY() - e2.getY();
				
				if (Math.abs(spanY)<SPAN_Y && Math.abs(velocityY)<VELOCITY_Y) {
					
					if (Math.abs(span)>=SPAN_X && Math.abs(velocityX)>VELOCITY_X) {
						
						Log.d(TAG,"水平滑动!");
						
						if (span>0) { //右滑动
							
							if (!"".equals(staffByTouch.getMobile())) {
								intent.setAction(Intent.ACTION_CALL);
								intent.setData(Uri.parse("tel:" + staffByTouch.getMobile()));
								startActivity(intent);
							}else if (!"".equals(staffByTouch.getPhone())) {
								intent.setAction(Intent.ACTION_CALL);
								intent.setData(Uri.parse("tel:" + staffByTouch.getPhone()));
								startActivity(intent);
							}else {
								Toast.makeText(HomeActivity.this, getString(R.string.invaild_phone), Toast.LENGTH_LONG).show();
							}

						}else {  //左滑动
							if (!"".equals(staffByTouch.getMobile())) {
								ArrayList<String> sendNameList = new ArrayList<String>();
								ArrayList<String> sendMobileList = new ArrayList<String>();
								sendMobileList.add(staffByTouch.getMobile());
								sendNameList.add(staffByTouch.getName());
								intent.setAction("com.neusoft.SMS");
								intent.putExtra("name", sendNameList);
								intent.putExtra("mobile", sendMobileList);
								startActivity(intent);
							}else {
								Toast.makeText(HomeActivity.this, getString(R.string.invaild_phone), Toast.LENGTH_LONG).show();
							}
						}
					}
					
					return true;
					
				}else {
					Log.d(TAG,"垂直滑动!");
				}
			}
			
			return false;
		}
		
	}
}
