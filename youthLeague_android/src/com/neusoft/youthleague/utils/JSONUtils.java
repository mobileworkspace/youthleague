package com.neusoft.youthleague.utils;

import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.neusoft.youthleague.exception.JSonParseException;

public class JSONUtils {
	
	private static final String TAG = "JSonUtils";
	
	/**
	 * 
	 * @param jsonObject
	 * @param path
	 * @return Object , after get it,  can transfer to other type if you confirm it can
	 * @throws JSonParseException
	 */
	public static Object get(JSONObject jsonObject, String path) throws JSonParseException {
		try {
			Pattern pattern = Pattern.compile("/");
			String[] pathString = pattern.split(path);
			JSONObject object = jsonObject;
			
			for (int i = 0; i < pathString.length; i++) {
				if (object.has(pathString[i])){
					if (i==pathString.length-1) {						
						return object.get(pathString[i]);						
					}else {
						object = (JSONObject)object.get(pathString[i]);
					}
				}
			}
			
			return null;
			
		}catch(JSONException e) {
			throw new JSonParseException(e);		
		}
	}
	
	/**
	 * 
	 * @param jsonObject
	 * @param path
	 * @return JSONArray
	 * @throws JSonParseException
	 */
	public static JSONArray getArray(JSONObject jsonObject, String path) throws JSonParseException {
		try {	
			if (path.lastIndexOf("/")!=-1) {
				JSONObject object = (JSONObject) get(jsonObject, path.substring(0,path.lastIndexOf("/")));
				return object.getJSONArray(path.substring(path.lastIndexOf("/") + 1 , path.length()));
			}else {
				return jsonObject.getJSONArray(path);
			}
		}catch(JSONException e) {
			throw new JSonParseException(e);
		}
	}
	
	public static JSONObject parseJSONOjbect (String str) throws JSonParseException {
		try {
			return (JSONObject) new JSONTokener(str).nextValue();
		}catch(JSONException e) {
			throw new JSonParseException(e);
		}
	}
	
	//升级时间解析，格式如： {"lastUpdateDate":20131205}
	public static String parseLogoutJSon(String jsonString) throws JSonParseException, JSONException {
		JSONObject jsonObject = JSONUtils.parseJSONOjbect(jsonString);
		String retString = (String) JSONUtils.get(jsonObject, "lastUpdateDate");
		return retString;
	}
}