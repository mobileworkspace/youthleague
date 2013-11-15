package com.neusoft.youthleague.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.neusoft.youthleague.exception.InternetException;

public class HttpService {

	private static final String TAG = "HttpService";
	
	public static int fileSize;
	
	public static final String registerUrl = "";
	
	public static String httpGet(String url, Map<String, String> attributes) throws InternetException {
		
		InputStream in = null;
		String result = null;

		try {
			
			URL serverUrl = new URL(url);  
			HttpURLConnection conn = (HttpURLConnection)serverUrl.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(15000);		
			
	       if(attributes!=null){
				for (Map.Entry<String, String> m : attributes.entrySet()) {
					conn.addRequestProperty(m.getKey(),m.getValue());
				}
			}
	       
			Log.d(TAG, "request url:[" +  conn.getURL() + "]");
			
			int retCode = ((HttpURLConnection)conn).getResponseCode();
			
			Log.d(TAG, "response code :[" + retCode +"]");
			
			if(retCode==HttpURLConnection.HTTP_OK){
				in = conn.getInputStream(); 
				fileSize = conn.getContentLength();
				result = convertStreamToString(in);				
			}else {
				result = null;
			}

		} catch (Exception e) {
			result = null;
			throw new InternetException(e);
		}finally{
			
			if (in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					result = null;
					throw new InternetException(e);
				}
			}
		}
		return result;
	}

//	public static InputStream httpGet(String url,Map<String, String> attributes,Boolean bool) throws Exception {
//		
//		InputStream in = null;
//
//		try {
//			
//			URL serverUrl = new URL(url);  
//			HttpURLConnection conn = (HttpURLConnection)serverUrl.openConnection();
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//			conn.setRequestMethod("GET");
//			conn.setConnectTimeout(15000);		
//			
//	        if(attributes!=null){
//				for (Map.Entry<String, String> m : attributes.entrySet()) {
//					conn.addRequestProperty(m.getKey(),m.getValue());
//					Log.d(TAG, "Key:[" +  m.getKey() + "] Value:[" + m.getValue() + "]");
//				}
//	        }
//			
//			Log.d(TAG, "request url:[" +  conn.getURL() + "]");
//			
//			int retCode = ((HttpURLConnection)conn).getResponseCode();
//			
//			Log.d(TAG, "response code for "+ conn.getURL() +":[" + retCode +"]");
//			
//			if(retCode==HttpURLConnection.HTTP_OK){
//				in = conn.getInputStream();			
//			}
//
//		} catch (Exception e) {
//			throw e;
//		}
//		
//		return in;
//	}
	
	public static String httpPost(String url, Map<String, String> headers, Map<String, String> attributes) throws InternetException {
		
		InputStream in = null;
		String result = null;
		
       try {    	    
		    DefaultHttpClient httpClient = new DefaultHttpClient();
		    HttpPost httpPost = new HttpPost(url);
			
		    if (headers!=null) {
			    for (Map.Entry<String, String> m : headers.entrySet()) {
			    	httpPost.addHeader(m.getKey(),m.getValue());
				}
			}

		    if (attributes!=null) {
			    ArrayList<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();	  
			    for (Map.Entry<String, String> m : attributes.entrySet()) {
			         postData.add(new BasicNameValuePair(m.getKey(), m.getValue()));
			         Log.d(TAG, "post parameters: key->[" + m.getKey() + "] value->[" + m.getValue() + "]");
			    }
			
			    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postData, HTTP.UTF_8);
			    httpPost.setEntity(entity);
			}
		
		    httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 0);   
		    httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
            		
		    HttpResponse response = httpClient.execute(httpPost);
		
		    int retCode = response.getStatusLine().getStatusCode();
		    
		    Log.d(TAG, "request url:[" +  url + "]");
		    
			Log.d(TAG, "response code:[" + retCode +"]");
			
			if(retCode==HttpURLConnection.HTTP_OK){
				 HttpEntity httpEntity = response.getEntity();
			     in = httpEntity.getContent();
			     result = convertStreamToString(in);
			}else {
				result = null;
			}

        } catch (Exception e) {
        	e.printStackTrace();
        	throw new InternetException(e);
		}finally{       
			
			if (in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new InternetException(e);
				}
			}
		}
        
		return result;
   }

	public static String httpCheckVersion(String url) throws InternetException { 
		
		InputStream in = null;
		
		try {
			URL serverUrl = new URL(url);  
			HttpURLConnection conn = (HttpURLConnection)serverUrl.openConnection();
			conn.setRequestMethod("POST");
			
			Log.d(TAG, "request url:[" +  conn.getURL() + "]");
			
			int retCode = ((HttpURLConnection)conn).getResponseCode();
			Log.d(TAG, "response code:[" + retCode +"]");
			
			if(retCode==HttpURLConnection.HTTP_OK){
				in = conn.getInputStream();
				if (in==null) {
					return null;
				}
				return convertStreamToString(in);
			}
		} catch (Exception e) {
			throw new InternetException(e);
		} 
		return null;
	}
	
	//For downloading files
	public static InputStream httpGetFile(String url) throws InternetException { 
		
		InputStream in = null;
		
		try {
			URL serverUrl = new URL(url);  
			HttpURLConnection conn = (HttpURLConnection)serverUrl.openConnection();
			conn.setRequestMethod("GET");
			
			Log.d(TAG, "request url:[" +  conn.getURL() + "]");
			
			int retCode = ((HttpURLConnection)conn).getResponseCode();
			Log.d(TAG, "response code:[" + retCode +"]");
			
			if(retCode==HttpURLConnection.HTTP_OK){
				in = conn.getInputStream();
				fileSize = conn.getContentLength();
				return in;
			}
		} catch (Exception e) {
			throw new InternetException(e);
		} 
		return null;
	}
	
	public static String convertStreamToString(InputStream in) throws IOException {   
		
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();   

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        	throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw e;
            }
        }   

        return sb.toString();
    }
	
	/**
	 * return trur if there is a available network 
	 * @param ctx
	 * @return
	 */
	public static boolean isNetworkAvailable(Context ctx) {
    	ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);                 
    	NetworkInfo networkinfo = manager.getActiveNetworkInfo();  
    	if (networkinfo != null && networkinfo.isAvailable()) { 
    		return true;
    	}
		
    	return false;
	}
	
}
