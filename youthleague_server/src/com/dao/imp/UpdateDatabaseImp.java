package com.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dao.UpdateDatabaseDao;
import com.dbc.Jdbc_driver;
import com.util.DateTime;

public class UpdateDatabaseImp implements UpdateDatabaseDao {

	PreparedStatement pre;
	ResultSet result;

	public String getLastDate() {
		
		String datatime = "197001010001";
		Connection connc = Jdbc_driver.Connec();
		
		try {
			
			pre = connc.prepareStatement("select last_date from update_database");
			result = pre.executeQuery();
			while (result.next()) {
				datatime = result.getString(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close(connc);
//		}
		
		return datatime;
	}

	public void updateLastDate() {
		
		Connection connc = Jdbc_driver.Connec();
		
		try {
			
			pre = connc.prepareStatement("update update_database set last_date = ?");
			pre.setString(1, new DateTime().now_Time());
			pre.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close(connc);
//		}
	}


	public void updateDbDate() {
		// TODO Auto-generated method stub
		
		Connection connc = Jdbc_driver.Connec();
		
		try {

			pre = connc
					.prepareStatement("update update_database set update_file_date = ?");
			pre.setString(1, new DateTime().now_Time());
			pre.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close(connc);
//		}
	}

	public String getDbDate() {

		String datatime = "197001010001";
		Connection connc = Jdbc_driver.Connec();

		try {

			pre = connc
					.prepareStatement("select update_file_date from update_database");
			result = pre.executeQuery();
			while (result.next()) {
				datatime = result.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close(connc);
//		}

		return datatime;
	}
	
	public void deleteAll() {
		
		Connection connc = Jdbc_driver.Connec();
		
		try {
			
			pre = connc.prepareStatement("delete from update_database ");
			pre.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close(connc);
//		}
	}

	// 关闭资源
	public void close(Connection connc) {
		
		try {
			
			if (result != null) {
				result.close();
				result = null;
			}
			try {
				if (connc != null) {
					connc.close();
					connc = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
