package com.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dao.PositionDao;
import com.dbc.Jdbc_driver;
import com.vo.Position;

public class PositionImp implements PositionDao {

	Connection connc;
	PreparedStatement pre;
	ResultSet result;

	public PositionImp() {
		connc = Jdbc_driver.Connec();
	}

	public boolean add(Position vo) {
		boolean flag = false;
		try {
			pre = connc.prepareStatement("insert into position_(name,note) values(?,?)");
			pre.setString(1, vo.getName());
			pre.setString(2, vo.getNote());
			if(pre.executeUpdate()>0){
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		return flag;
	}

	public boolean amend(Position vo) {
		boolean flag = false;
		try {
			pre = connc.prepareStatement("update position_  set name=?,note=? where id=?");
			pre.setString(1, vo.getName());
			pre.setString(2, vo.getNote());
			pre.setInt(3, vo.getId());
			int i = pre.executeUpdate();
			if (i > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		finally {
//			close();
//		}
		return flag;
	}

	public int countPosition() {
		int count = 0;
		try {
			pre = connc.prepareStatement("select count(*) from position_");
			result = pre.executeQuery();
			while (result.next()){
				count = result.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		return count;
	}

	public boolean delete(int id) {
		boolean flag = false;
		try {
			pre = connc.prepareStatement("delete from position_ where id=?");
			pre.setInt(1, id);
			if(pre.executeUpdate()>0){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		return flag;
	}

	public Position getPositionById(int id) {
		Position position = new Position();
		try {
			pre = connc.prepareStatement("select * from position_  where id=?");
			pre.setInt(1, id);
			result = pre.executeQuery();
			while (result.next()) {
				int idd = result.getInt("id");
				String name = result.getString("name");
				String note = result.getString("note");
				position.setId(idd);
				position.setName(name);
				position.setNote(note);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		finally {
//			close();
//		}
		return position;
	}

	public List<Position> query(int page, int pageSize) {
		List<Position> list = new ArrayList<Position>();
		try {
			pre = connc.prepareStatement("select * from position_ order by id desc limit ?,?");
			pre.setInt(1, (page - 1) * pageSize);
			pre.setInt(2, pageSize);
			result = pre.executeQuery();
			while (result.next()) {
				Position department = new Position();
				department.setId(result.getInt("id"));
				department.setName(result.getString("name"));
				department.setNote(result.getString("note"));
				list.add(department);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		return list;
	}

	//返回所有对象
	public List<Position> ListPosition() {
		List<Position> list = new ArrayList<Position>();
		try {
			pre = connc.prepareStatement("select * from position_ ");
			result = pre.executeQuery();
			while (result.next()) {
				Position position = new Position();
				position.setId(result.getInt("id"));
				position.setName(result.getString("name"));
				position.setNote(result.getString("note"));
				list.add(position);
			}
		} catch (Exception e) {
		} 
//		finally {
//			close();
//		}
		return list;
	}

	//关闭资源
	public void close() {
		try {
			if (result != null) {
				result.close();
				result = null;
			}
			if (pre != null) {
				pre.close();
				pre = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
