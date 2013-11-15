package com.dao;

import java.util.List;

import com.vo.Position;

public interface PositionDao {
	
	//添加
	public boolean add(Position vo);
	//删除
	public boolean delete(int id);
	//修改
	public boolean amend(Position vo);
	//分页查询
	public List<Position> query(int page, int pageSize);
	//查看总页数
	public int countPosition();
	//返回一个对象
	public Position getPositionById(int id);
	
	//List
	public List<Position> ListPosition();
}
