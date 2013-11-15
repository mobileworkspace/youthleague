package com.neusoft.youthleague.exception;







public class ConstantValues {
	
	//关于升级数据库
	public static final int UPDATE_DB_START = 1;
	public static final int UPDATE_DB_FAIL = 2;
	public static final int UPDATE_DB_FINISH = 3;
	public static final int UPDATE_DB_FILE_ERROR = 4;
	public static final int UPDATE_DB_NET_ERROR = 5;
	public static final int UPDATE_DB_PROGRESS = 6;
	public static final int UPDATE_DB_NO_EXIST = 7;
	
	public static final int CHECK_DB_FINISH = 8;
	public static final int CHECK_DB_ERROR = 9;
	
	public static final String REGISTER_USER_URL = "http://neusoftallen.gotoip55.com/youthleague/StaffServlet?type=";
	public static final String UPDATE_DB_CHECK_URL = "http://neusoftallen.gotoip55.com/youthleague/GetDatabaseVersionServlet?type=new_dateTime";
	public static final String UPDATE_DB_DATA_URL = "http://neusoftallen.gotoip55.com/youthleague/GetDatabaseVersionServlet?type=get_file";
	public static final String databasePath = "/databases";
	
	
	
	

	
}
