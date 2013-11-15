package com.dbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Jdbc_driver {
	
	static Connection Connec;
	
	public static String driver;
	public static String url;
	public static String username;
	public static String password;

	public static Connection Connec() {
		
		if(Connec==null){
		
			try {
				Properties pros = new Properties();
				InputStream in = Jdbc_driver.class.getResourceAsStream("/db.properties");
				pros.load(in);
				in.close();
				driver = pros.getProperty("driver");
				url = pros.getProperty("url");
				username = pros.getProperty("user");
				password = pros.getProperty("password");

				Class.forName(driver);
				Connec = DriverManager.getConnection(url, username, password);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		return Connec;
	}
}