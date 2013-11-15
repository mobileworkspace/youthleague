package com.neusoft.youthleague.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.neusoft.youthleague.exception.ConstantValues;
import com.neusoft.youthleague.exception.InternetException;

/**
 * 有关数据库升级的手动方式，如果版本不同，就下载服务器上的，否则无操作
 * 
 * @author Administrator
 *
 */
public class UpdateDatabase extends AsyncTask<String, Void , Void> {

	String pathString;
	
	Context mContext;
	Handler handler;
	
	int retCode = 1;
	boolean flag = false;
	
	public UpdateDatabase(Context mContext,Handler handler) {
		super();
		this.mContext = mContext;
		this.handler = handler;
		pathString = mContext.getDatabasePath("youth_league.db").getAbsolutePath(); //ConstantValues.databasePath;
		pathString = pathString.substring(0, pathString.lastIndexOf("/"));
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
		if (retCode==-1) {
			handler.sendEmptyMessage(ConstantValues.UPDATE_DB_FAIL);
		}else if (retCode==-2) {
			handler.sendEmptyMessage(ConstantValues.UPDATE_DB_FILE_ERROR);
		}else if (retCode==-3) {
			handler.sendEmptyMessage(ConstantValues.UPDATE_DB_NET_ERROR);
		}else{
			handler.sendEmptyMessage(ConstantValues.UPDATE_DB_FINISH);
		}
	}
	

	@Override
	protected Void doInBackground(String... params) {
		
        InputStream inputStream = null;   
        
		try {
			
			inputStream = HttpService.httpGetFile(params[0]);
			int fileSize = HttpService.fileSize;
			
			if(inputStream!=null){	
				
				//数据库文件不存在，则创建它
				File file = new File(pathString + "/youth_league.db");	
				if (!file.exists()) {
					file = new File(pathString);
					if (!file.exists()) {
						File dir = new File(pathString);
						dir.mkdirs();
						file = new File(pathString + "/youth_league.db");
						try {
							file.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							retCode = -2;
							file = null;
							return null;
						}
						file = null;
					}
					
				}else {
							
					file = new File(pathString + "/youth_league.db.bak");
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						retCode = -2;
						file = null;
						return null;
					}
					file = null;
					
					flag = true;
				}				
				
				//将下载的数据写入指定的数据库文件中
				OutputStream outStream = null;
				try {
					
					if (flag) {
						outStream = new FileOutputStream(pathString + "/youth_league.db.bak");
					}else {
						outStream = new FileOutputStream(pathString + "/youth_league.db");
					}
					
					Message msg;
					int downLoadFileSize = 10;
					
					final int buffer_size = 4096;
					
		            byte[] bytes = new byte[buffer_size];
		            int count = -1;
		            
		            while(true) {
		            	count = inputStream.read(bytes, 0, buffer_size);
		            	if(count==-1)  break;
		            	outStream.write(bytes, 0, count);
		            	
		            	downLoadFileSize += count;
		            	msg = Message.obtain();
		            	msg.arg1 = downLoadFileSize;
		            	msg.arg2 = fileSize;
		            	msg.what = ConstantValues.UPDATE_DB_PROGRESS;
		            	handler.sendMessage(msg);
		            }
			        
					outStream.flush();
					outStream.close();
					inputStream.close();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					retCode = -1;
					return null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					retCode = -1;
					return null;
				} 
				
			}else {
				retCode = -3;
				return null;
			}					
				
		} catch (InternetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			retCode = -3;
		} 
		
		//将下载的新数据库文件替换旧的数据库文件
		if (flag) { 

			File newFile = new File(pathString + "/youth_league.db");
			newFile.delete();
			
			File file = new File(pathString + "/youth_league.db.bak");
			if(!file.renameTo(newFile)){
				retCode=-1;
			}

		}
			
		return null;
		
	}
	
	
}