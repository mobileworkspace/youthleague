package com.neusoft.youthleague.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.neusoft.youthleague.exception.ConstantValues;

/**
 * 有关数据库升级的手动方式，如果版本不同，就下载服务器上的，否则无操作
 * 
 * @author Administrator
 *
 */
public class CheckDatabase extends AsyncTask<String, Void , String> {

	Context mContext;
	Handler handler;
	
	int retCode = 1;
	boolean flag = false;
	
	public CheckDatabase(Context mContext,Handler handler) {
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
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if ("".equals(result)) {
			handler.sendEmptyMessage(ConstantValues.CHECK_DB_ERROR);
		}else{
			Message msg;
			msg = Message.obtain();
        	msg.obj = result;
        	msg.what = ConstantValues.CHECK_DB_FINISH;
        	handler.sendMessage(msg);
		}
	}
	

	@Override
	protected String doInBackground(String... params) {
		
        String resultString = "";   
        
		try {
			
			resultString = HttpService.httpCheckVersion(ConstantValues.UPDATE_DB_CHECK_URL);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return resultString;
	}
	
	
}