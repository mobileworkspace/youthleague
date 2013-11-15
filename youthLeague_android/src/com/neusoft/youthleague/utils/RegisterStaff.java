package com.neusoft.youthleague.utils;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.neusoft.youthleague.exception.ConstantValues;
import com.neusoft.youthleague.exception.InternetException;

/**
 * 有关数据库升级的手动方式，如果版本不同，就下载服务器上的，否则无操作
 * 
 * @author Administrator
 *
 */
public class RegisterStaff extends AsyncTask<String, Void , Void> {

	Context mContext;
	Handler handler;
	
	int retCode = 0;
	boolean flag = false;
	
	public RegisterStaff(Context mContext,Handler handler) {
		super();
		this.mContext = mContext;
		this.handler = handler;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		handler.sendEmptyMessage(ConstantValues.UPDATE_DB_START);
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (retCode==-2) {
			handler.sendEmptyMessage(ConstantValues.UPDATE_DB_FAIL);
		}else if (retCode==-1) {
			handler.sendEmptyMessage(ConstantValues.UPDATE_DB_FILE_ERROR);
		}else{
			handler.sendEmptyMessage(ConstantValues.UPDATE_DB_FINISH);
		}
	}
	

	@Override
	protected Void doInBackground(String... params) {
		
        Map<String, String> data = new HashMap<String, String>();
		
		data.put("mobile", params[0]);
		data.put("name", params[1]);
		data.put("password1", params[2]);
		data.put("phone", params[3]);
		data.put("department_id", params[4]);
		data.put("position_id", params[5]);
		data.put("organization_id", params[6]);
		data.put("id", params[7]);
		data.put("password2", params[2]);
		
		data.put("web", "0");
		
		String operateString = params[8];
		
		String resutlString = "0";
		
		try {
			
			resutlString = HttpService.httpPost(ConstantValues.REGISTER_USER_URL + operateString, null, data);
			
			if ("-2\n".equals(resutlString)) {
				retCode = -2;
			}else if ("-1\n".equals(resutlString)) {
				retCode = -1;
			}else {
				retCode = 0;
			}
			
		} catch (InternetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			retCode = -2;
		}
		
		return null;
		
	}
	
	
}