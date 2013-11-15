package com.neusoft.youthleague.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBService {

	private DBHelper mDbHelper;

	public DBService(Context context) {
		mDbHelper = DBHelper.getInstance(context); //new DBHelper(context);
	}

	public Department getDepartmentById(int id){
		
		Department department = null;
		
		SQLiteDatabase database = mDbHelper.getReadableDatabase();
		Cursor cursor;
		
		cursor = database.rawQuery("SELECT name FROM department WHERE id = ? ", new String[] { "" + id });
		
		while (cursor.moveToNext()) {
			department = new Department();
			department.setName(cursor.getString(cursor.getColumnIndex("name")));
			department.setId(id);
		}
		
		cursor.close();
		database.close();
		
		return department;
	}
	
	public Department getDepartmentByName(String name){
		
		Department department = null;
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
		
		cursor = db.rawQuery("SELECT id FROM department WHERE name = ? ", new String[] { name });
		
		while (cursor.moveToNext()) {
			department = new Department();
			department.setId(cursor.getInt(cursor.getColumnIndex("id")));
			department.setName(name);
		}
		
		cursor.close();
		db.close();
		
		return department;
	}
	
	public List<Department> getAllDepartment(){
		
		ArrayList<Department> result = new ArrayList<Department>();
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
		
		Department department;
		String nameString;
		int idInt;
		
		cursor = db.rawQuery("SELECT id, name FROM department ", null);
		
		while (cursor.moveToNext()) {
			
			department = new Department();
			
			idInt = cursor.getInt(cursor.getColumnIndex("id"));
			nameString = cursor.getString(cursor.getColumnIndex("name"));
			
			department.setId(idInt);
			department.setName(nameString);
			
			result.add(department);
		}
		
		cursor.close();
		db.close();
		
		return result;
	}
	
	public Position getPositionById(int id){
		
		Position position = null;
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
		
		cursor = db.rawQuery("SELECT name FROM position_ WHERE id = ? ", new String[] { "" + id });
		
		while (cursor.moveToNext()) {
			
			position = new Position();
			
			position.setId(id);
			position.setName(cursor.getString(cursor.getColumnIndex("name")));
		}
		
		cursor.close();
		db.close();
		
		return position;
	}
	
	public Position getPositionByName(String name){
		
		Position position = null;
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
		
		cursor = db.rawQuery("SELECT id FROM position_ WHERE name = ? ", new String[] { name });
		
		while (cursor.moveToNext()) {
			
			position = new Position();
			
			position.setId(cursor.getInt(cursor.getColumnIndex("id")));
			position.setName(name);
		}
		
		cursor.close();
		db.close();
		
		return position;
	}
	
	public List<Position> getAllPosition(){
		
		ArrayList<Position> result = new ArrayList<Position>();
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
		
		Position position;
		
		cursor = db.rawQuery("SELECT id, name FROM position_", null);
		
		while (cursor.moveToNext()) {
			
			position = new Position();
			
			position.setId(cursor.getInt(cursor.getColumnIndex("id")));
			position.setName(cursor.getString(cursor.getColumnIndex("name")));
			
			result.add(position);
		}
		
		cursor.close();
		db.close();
		
		return result;
	}
	
	public Organization getOrganizationById(int id){
		
		Organization organization = null;
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
			
		cursor = db.rawQuery("SELECT name, address, super_id FROM organization WHERE id = ? ", new String[] { "" + id });
		
		while (cursor.moveToNext()) {
			
			organization = new Organization();
			
			organization.setId(id);
			organization.setName(cursor.getString(cursor.getColumnIndex("name")));
			organization.setAddress(cursor.getString(cursor.getColumnIndex("address")));
			organization.setSuperId(cursor.getInt(cursor.getColumnIndex("super_id")));
		}
		
		cursor.close();
		db.close();
		
		return organization;
	}
	
	public Organization getOrganizationByName(String name){
		
		Organization organization = null;
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
		
		cursor = db.rawQuery("SELECT id, address, super_id FROM organization WHERE name = ? ", new String[] { name });
		
		while (cursor.moveToNext()) {
			
			organization = new Organization();
			
			organization.setId(cursor.getInt(cursor.getColumnIndex("id")));
			organization.setName(name);
			organization.setAddress(cursor.getString(cursor.getColumnIndex("address")));
			organization.setSuperId(cursor.getInt(cursor.getColumnIndex("super_id")));
		}
		
		cursor.close();
		db.close();
		
		return organization;
	}
	
	public List<Organization> getAllOrganization(){
		
		ArrayList<Organization> result = new ArrayList<Organization>();
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
		
		Organization organization;
		
		int idInt;
		String name;
		String address;
		int superId;
		
		cursor = db.rawQuery("SELECT id, name, address, super_id FROM organization where id != '-1' ", null);
		
		while (cursor.moveToNext()) {
			
			organization = new Organization();
			
			idInt = cursor.getInt(cursor.getColumnIndex("id"));
			name = cursor.getString(cursor.getColumnIndex("name"));
			address = cursor.getString(cursor.getColumnIndex("address"));
			superId = cursor.getInt(cursor.getColumnIndex("super_id"));
			
			organization.setId(idInt);
			organization.setName(name);
			organization.setAddress(address);
			organization.setSuperId(superId);
			
			result.add(organization);
		}
		
		cursor.close();
		db.close();
		
		return result;
	}
	
	public List<Organization> getChildsOrganizationBySuperId(int superId) {
		// TODO Auto-generated method stub
		ArrayList<Organization> result = new ArrayList<Organization>();
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
		
		Organization organization;
		
		int idInt;
		String name;
		String address;
		int super_id;
		
		Boolean flag = false;
		String allSuperId = "," + superId;
		
		do{
			
			allSuperId = allSuperId.substring(1, allSuperId.length());
			
			cursor = db.rawQuery("SELECT id, name, address, super_id FROM organization WHERE super_id in ( " + allSuperId + " )", null);
			
			allSuperId = "";
			
			while (cursor.moveToNext()) {
				
				flag = true;
				
				organization = new Organization();
				
				idInt = cursor.getInt(cursor.getColumnIndex("id"));
				name = cursor.getString(cursor.getColumnIndex("name"));
				address = cursor.getString(cursor.getColumnIndex("address"));
				super_id = cursor.getInt(cursor.getColumnIndex("super_id"));
				
				organization.setId(idInt);
				organization.setName(name);
				organization.setAddress(address);
				organization.setSuperId(super_id);
				
				result.add(organization);
				
				allSuperId = allSuperId + "," + idInt;
			}
			
			cursor.close();
			
			if (!flag) {
				break;
			}
			
			flag = false;
			
		}while(true);

		db.close();
		
		return result;
	}
	
	public List<Organization> getAllParentOrganization() {
		// TODO Auto-generated method stub
		ArrayList<Organization> result = new ArrayList<Organization>();
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
		
		Organization organization;
		
		int idInt;
		String name;
		String address;
		int superId;
		
		cursor = db.rawQuery("SELECT a.id, a.name, a.address, a.super_id FROM organization a WHERE a.super_id == '-1'", null);
		
		while (cursor.moveToNext()) {
			
			organization = new Organization();
			
			idInt = cursor.getInt(cursor.getColumnIndex("id"));
			name = cursor.getString(cursor.getColumnIndex("name"));
			address = cursor.getString(cursor.getColumnIndex("address"));
			superId = cursor.getInt(cursor.getColumnIndex("super_id"));
			
			organization.setId(idInt);
			organization.setName(name);
			organization.setAddress(address);
			organization.setSuperId(superId);
			
			result.add(organization);
		}
		
		cursor.close();
		db.close();
		
		return result;
	}
	
	public Staff getStaffById(int id){
		
		Staff staff = null;
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
			
		cursor = db.rawQuery("SELECT name, password_ as pwd, mobile, phone, organization_id, department_id, position_id "
				           + "FROM staff WHERE id = ? ", new String[] { "" + id });
		
		while (cursor.moveToNext()) {
			
			staff = new Staff();
			
			staff.setId(id);
			staff.setName(cursor.getString(cursor.getColumnIndex("name")));
			staff.setPwd(cursor.getString(cursor.getColumnIndex("pwd")));
			staff.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
			staff.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
			staff.setOrganizationId(cursor.getInt(cursor.getColumnIndex("organization_id")));
			staff.setDepartmentId(cursor.getInt(cursor.getColumnIndex("department_id")));
			staff.setPositionId(cursor.getInt(cursor.getColumnIndex("position_id")));
		}
		
		cursor.close();
		db.close();
		
		return staff;
	}
	
	public Staff getStaffByMobileNo(String mobileNo){
		
		Staff staff = null;
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
			
		cursor = db.rawQuery("SELECT id, name, password_ as pwd, mobile, phone, organization_id, department_id, position_id "
				           + "FROM staff "
				           + "WHERE mobile = ? AND is_warrant = '1' AND is_departure = '0' ", new String[] { mobileNo });
		
		while (cursor.moveToNext()) {
			
			staff = new Staff();
			
			staff.setId(cursor.getInt(cursor.getColumnIndex("id")));
			staff.setName(cursor.getString(cursor.getColumnIndex("name")));
			staff.setPwd(cursor.getString(cursor.getColumnIndex("pwd")));
			staff.setMobile(mobileNo);
			staff.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
			staff.setOrganizationId(cursor.getInt(cursor.getColumnIndex("organization_id")));
			staff.setDepartmentId(cursor.getInt(cursor.getColumnIndex("department_id")));
			staff.setPositionId(cursor.getInt(cursor.getColumnIndex("position_id")));
		}
		
		cursor.close();
		db.close();
		
		return staff;
	}

	public Staff staffLogin(String mobileNo, String passwordString){
		
		Staff staff = null;
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
			
		cursor = db.rawQuery("SELECT id, name, password_ as pwd, mobile, phone, organization_id, department_id, position_id "
				           + "FROM staff "
				           + "WHERE is_warrant = '1' AND is_departure = '0' "
				           + "AND mobile = ? AND password_ = ? "
				           + " LIMIT 1 ", new String[] { mobileNo, passwordString });
		
		while (cursor.moveToNext()) {
			
			staff = new Staff();
			
			staff.setId(cursor.getInt(cursor.getColumnIndex("id")));
			staff.setName(cursor.getString(cursor.getColumnIndex("name")));
			staff.setPwd(passwordString);
			staff.setMobile(mobileNo);
			staff.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
			staff.setOrganizationId(cursor.getInt(cursor.getColumnIndex("organization_id")));
			staff.setDepartmentId(cursor.getInt(cursor.getColumnIndex("department_id")));
			staff.setPositionId(cursor.getInt(cursor.getColumnIndex("position_id")));
		}
		
		cursor.close();
		db.close();
		
		return staff;
	}
	
	public Staff getStaffByName(String name){
		
		Staff staff = null;
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
		
		cursor = db.rawQuery("SELECT id, password_ as pwd, mobile, phone, organization_id, department_id, position_id "
		                   + "FROM staff "
		                   + "WHERE  is_warrant = '1' AND is_departure = '0' AND is_hipe = '0' AND name = ? ", new String[] { name });
		
		while (cursor.moveToNext()) {
			
			staff = new Staff();
			
			staff.setId(cursor.getInt(cursor.getColumnIndex("id")));
			staff.setName(name);
			staff.setPwd(cursor.getString(cursor.getColumnIndex("pwd")));
			staff.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
			staff.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
			staff.setOrganizationId(cursor.getInt(cursor.getColumnIndex("organization_id")));
			staff.setDepartmentId(cursor.getInt(cursor.getColumnIndex("department_id")));
			staff.setPositionId(cursor.getInt(cursor.getColumnIndex("position_id")));
		}
		
		cursor.close();
		db.close();
		
		return staff;
	}
	
	public List<Staff> getStaffsByOrganizationId(int organizationId){
		
		ArrayList<Staff> result = new ArrayList<Staff>();
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
		
		Staff staff;
		
		cursor = db.rawQuery("SELECT a.id, a.name, a.password_ as pwd , a.mobile, a.phone, a.organization_id, a.department_id, a.position_id, " 
		                   + "b.name as department_name, c.name as position_name "
                           + "FROM staff a, department b, position_ c "
                           + "WHERE a.is_warrant = '1' AND is_departure = '0' AND is_hipe = '0' AND a.department_id = b.id AND a.organization_id = ? "
                           + " AND  a.position_id = c.id "
                           + "ORDER BY a.department_id ", 
                           new String[] { "" + organizationId });
		
		while (cursor.moveToNext()) {
			
			staff = new Staff();
			
			staff.setId(cursor.getInt(cursor.getColumnIndex("id")));
			staff.setName(cursor.getString(cursor.getColumnIndex("name")));
			staff.setPwd(cursor.getString(cursor.getColumnIndex("pwd")));
			staff.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
			staff.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
			staff.setOrganizationId(cursor.getInt(cursor.getColumnIndex("organization_id")));
			staff.setDepartmentId(cursor.getInt(cursor.getColumnIndex("department_id")));
			staff.setPositionId(cursor.getInt(cursor.getColumnIndex("position_id")));
			staff.setDepartmentName(cursor.getString(cursor.getColumnIndex("department_name")));
			staff.setPositionName(cursor.getString(cursor.getColumnIndex("position_name")));
			staff.setSelected(false);
			
			result.add(staff);
		}
		
		cursor.close();
		db.close();
		
		return result;
	}
	
	public List<Staff> getAllStaff(){
		
		ArrayList<Staff> result = new ArrayList<Staff>();
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
		
		Staff staff;
		
		cursor = db.rawQuery("SELECT id, name, password_ as pwd, mobile, phone, organization_id, department_id, position_id "
                           + "FROM staff "
				           + "WHERE  is_warrant = '1' AND is_departure = '0' AND is_hipe = '0' ", null);
		
		while (cursor.moveToNext()) {
			
			staff = new Staff();
			
			staff.setId(cursor.getInt(cursor.getColumnIndex("id")));
			staff.setName(cursor.getString(cursor.getColumnIndex("name")));
			staff.setPwd(cursor.getString(cursor.getColumnIndex("pwd")));
			staff.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
			staff.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
			staff.setOrganizationId(cursor.getInt(cursor.getColumnIndex("organization_id")));
			staff.setDepartmentId(cursor.getInt(cursor.getColumnIndex("department_id")));
			staff.setPositionId(cursor.getInt(cursor.getColumnIndex("position_id")));
			
			result.add(staff);
		}
		
		cursor.close();
		db.close();
		
		return result;
	}

	public boolean registerStaff(Staff staff){
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		
		try {
			
			db.execSQL("INSERT INTO staff (id, name, password_, mobile, phone, organization_id, department_id, position_id) "
	                 + "VALUES(" + staff.getId() + "," + staff.getName() + "," + staff.getPwd() + ","
					 + staff.getMobile() + "," + staff.getPhone() + ","
	                 + staff.getOrganizationId() + "," + staff.getDepartmentId() + "," + staff.getPositionId() + ")" );
			
		} catch (Exception e) {
			return false;
		} finally {
			db.close();
		}
		
		return true;
	}
	
	public boolean updateStaff(Staff staff){
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		
		try {
			
			db.execSQL("UPDATE staff SET name = '" + staff.getName() + "', password_ = '" + staff.getPwd()
					 + "', mobile = '" + staff.getMobile() + "', phone = '" + staff.getPhone() + "' "
	                 + "WHERE id = " + staff.getId() );
			
		} catch (Exception e) {
			return false;
		} finally {
			db.close();
		}
		
		return true;
	}
	
	public List<Staff> searchStaff(String searchString) {
		// TODO Auto-generated method stub
		
		ArrayList<Staff> result = new ArrayList<Staff>();
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor;
		
		Staff staff;
		
		cursor = db.rawQuery("SELECT a.id, a.name, password_ as pwd, mobile, phone, organization_id, "
				         + " department_id, position_id, b.name as position_name "
                         + "FROM staff a, position_ b "
                         + "WHERE (a.name like '%" + searchString + "%' OR mobile like '%" + searchString 
                         + "%' OR phone like '%" + searchString + "%') AND a.position_id = b.id " 
		                 + " AND  is_warrant = '1' AND is_departure = '0' AND is_hipe = '0' ", null);
		
		while (cursor.moveToNext()) {
			
			staff = new Staff();
			
			staff.setId(cursor.getInt(cursor.getColumnIndex("id")));
			staff.setName(cursor.getString(cursor.getColumnIndex("name")));
			staff.setPwd(cursor.getString(cursor.getColumnIndex("pwd")));
			staff.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
			staff.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
			staff.setOrganizationId(cursor.getInt(cursor.getColumnIndex("organization_id")));
			staff.setDepartmentId(cursor.getInt(cursor.getColumnIndex("department_id")));
			staff.setPositionId(cursor.getInt(cursor.getColumnIndex("position_id")));
			staff.setPositionName(cursor.getString(cursor.getColumnIndex("position_name")));
			staff.setSelected(false);
			
			result.add(staff);
		}
		
		cursor.close();
		db.close();
		
		return result;
	}


}
