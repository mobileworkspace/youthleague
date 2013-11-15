package com.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileCreateTime {

	public static String getFileCreateDate(File file) {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return format.format(new Date(file.lastModified()));
	}

}
