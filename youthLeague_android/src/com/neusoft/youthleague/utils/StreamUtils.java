package com.neusoft.youthleague.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.neusoft.youthleague.exception.InternetException;


public class StreamUtils {
	
	public static String convertStreamToString(InputStream in) throws InternetException {   
		
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();   

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        	throw new InternetException(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw new InternetException(e);
            }
        }   

        return sb.toString();
    }

	public static int copyStream(InputStream is, OutputStream os, int length) {
        final int buffer_size = 4096;
        int sum = 0;
        try {
            byte[] bytes = new byte[buffer_size];
            int count = -1;
            
            while(true) {
            	count = is.read(bytes, 0, buffer_size);
            	if(count==-1)  break;
            	sum += count;
            	if (sum>=length) {
            		os.write(bytes, 0, count-sum+length);
            		break;
				}else {
					os.write(bytes, 0, count);
				}

            }
        } catch(Exception ex){
        	return 0;
        }
        return sum;
    }

	public static Boolean copyStream(InputStream is, OutputStream os) {
		
        final int buffer_size = 4096;
        try {
        	
            byte[] bytes = new byte[buffer_size];
            int count = -1;
            
            while(true) {
            	count = is.read(bytes, 0, buffer_size);
            	if(count==-1)  break;
				os.write(bytes, 0, count);
            }
        } catch(Exception ex){
        	return false;
        }
        return true;
    }
}
