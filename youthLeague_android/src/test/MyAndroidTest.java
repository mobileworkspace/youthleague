package test;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.neusoft.youthleague.model.DBHelper;

/*
 * 测试类必须继承androidTestCase类，这个类可以为我们提供上下文的环境
 * 单元测试方法前面必须有test
 */
public class MyAndroidTest extends AndroidTestCase {

	public void db(){
		insertDepartment();
		insertPosition();
		insertSuperOrganization();
		insertChildOrganization();
		insertStaff();
	}
	
	public void insertSuperOrganization() {
		
		SQLiteDatabase database = null;
		
		try {
			database = DBHelper.getInstance(getContext()).getWritableDatabase();
			
			database.beginTransaction();
			
			database.execSQL("insert into organization "
					+ "values(1,'团市委','',-1)");
			database.execSQL("insert into organization "
					+ "values(2,'南海区团委','',-1)");
			database.execSQL("insert into organization "
					+ "values(3,'顺德区团委','',-1)");
			database.execSQL("insert into organization "
					+ "values(4,'禅城区团委','',-1)");
			database.execSQL("insert into organization "
					+ "values(5,'高明区团委','',-1)");
			database.execSQL("insert into organization "
					+ "values(6,'三水区团委','',-1)");
//			database.execSQL("insert into organization "
//					+ "values(7,'团市委','',-1)");
			database.execSQL("insert into organization "
					+ "values(8,'市直机关','',-1)");
			database.execSQL("insert into organization "
					+ "values(9,'市直学校','',-1)");
			database.execSQL("insert into organization "
					+ "values(10,'市直企业','',-1)");
			database.execSQL("insert into organization "
					+ "values(11,'驻外团工委','',-1)");
			
			database.setTransactionSuccessful();
			
		} finally {
			
			database.endTransaction();
			database.close();
		}
	}
	
	public void insertChildOrganization() {
		
		SQLiteDatabase database = null;
		
		try {
			
			database = DBHelper.getInstance(getContext()).getWritableDatabase();
			
			database.beginTransaction();
			
			database.execSQL("insert into organization "
					+ "values(21,'团市委-11','',1)");
			database.execSQL("insert into organization "
					+ "values(22,'团市委-12','',1)");
			database.execSQL("insert into organization "
					+ "values(23,'团市委-13','',1)");
			database.execSQL("insert into organization "
					+ "values(24,'团市委-14','',1)");
			database.execSQL("insert into organization "
					+ "values(25,'团市委-15','',1)");
			
			database.execSQL("insert into organization "
					+ "values(26,'南海区团委-11','',2)");
			database.execSQL("insert into organization "
					+ "values(27,'南海区团委-12','',2)");
			database.execSQL("insert into organization "
					+ "values(28,'南海区团委-13','',2)");
			database.execSQL("insert into organization "
					+ "values(29,'南海区团委-14','',2)");
			database.execSQL("insert into organization "
					+ "values(30,'南海区团委-15','',2)");
			database.execSQL("insert into organization "
					+ "values(31,'南海区团委-16','',2)");
			
			database.execSQL("insert into organization "
					+ "values(32,'顺德区团委-11','',3)");
			database.execSQL("insert into organization "
					+ "values(33,'顺德区团委-12','',3)");
			database.execSQL("insert into organization "
					+ "values(34,'顺德区团委-13','',3)");
			database.execSQL("insert into organization "
					+ "values(35,'顺德区团委-14','',3)");
			database.execSQL("insert into organization "
					+ "values(36,'顺德区团委-15','',3)");
			database.execSQL("insert into organization "
					+ "values(37,'顺德区团委-16','',3)");
			
			database.execSQL("insert into organization "
					+ "values(38,'禅城区团委-22','',4)");
			database.execSQL("insert into organization "
					+ "values(39,'禅城区团委-23','',4)");
			database.execSQL("insert into organization "
					+ "values(40,'禅城区团委-24','',4)");
			database.execSQL("insert into organization "
					+ "values(41,'禅城区团委-25','',4)");
			database.execSQL("insert into organization "
					+ "values(42,'禅城区团委-26','',4)");
			database.execSQL("insert into organization "
					+ "values(43,'禅城区团委-27','',4)");
			
			database.execSQL("insert into organization "
					+ "values(44,'高明区团委-11','',5)");
			database.execSQL("insert into organization "
					+ "values(45,'高明区团委-12','',5)");
			database.execSQL("insert into organization "
					+ "values(46,'高明区团委-13','',5)");
			database.execSQL("insert into organization "
					+ "values(47,'高明区团委-14','',5)");
			database.execSQL("insert into organization "
					+ "values(48,'高明区团委-15','',5)");
			database.execSQL("insert into organization "
					+ "values(49,'高明区团委-16','',5)");
			
			database.execSQL("insert into organization "
					+ "values(50,'三水区团委-11','',6)");
			database.execSQL("insert into organization "
					+ "values(51,'三水区团委-12','',6)");
			database.execSQL("insert into organization "
					+ "values(52,'三水区团委-13','',6)");
			database.execSQL("insert into organization "
					+ "values(53,'三水区团委-14','',6)");
			database.execSQL("insert into organization "
					+ "values(54,'三水区团委-15','',6)");
			database.execSQL("insert into organization "
					+ "values(55,'三水区团委-16','',6)");
			
//			database.execSQL("insert into organization "
//					+ "values(56,'三水区团委','',7)");
//			database.execSQL("insert into organization "
//					+ "values(57,'团市委','',7)");
//			database.execSQL("insert into organization "
//					+ "values(58,'市直机关','',7)");
//			database.execSQL("insert into organization "
//					+ "values(59,'市直学校','',7)");
//			database.execSQL("insert into organization "
//					+ "values(60,'市直企业','',7)");
//			database.execSQL("insert into organization "
//					+ "values(61,'驻外团工委','',7)");
			
			
			database.execSQL("insert into organization "
					+ "values(68,'市直机关-11','',8)");
			database.execSQL("insert into organization "
					+ "values(69,'市直机关-12','',8)");
			database.execSQL("insert into organization "
					+ "values(70,'市直机关-13','',8)");
			database.execSQL("insert into organization "
					+ "values(71,'市直机关-14','',8)");
			database.execSQL("insert into organization "
					+ "values(72,'市直机关-15','',8)");
			database.execSQL("insert into organization "
					+ "values(73,'市直机关-16','',8)");
			
			database.execSQL("insert into organization "
					+ "values(74,'市直学校-55','',9)");
			database.execSQL("insert into organization "
					+ "values(75,'市直学校-56','',9)");
			database.execSQL("insert into organization "
					+ "values(76,'市直学校-57','',9)");
			database.execSQL("insert into organization "
					+ "values(77,'市直学校-58','',9)");
			database.execSQL("insert into organization "
					+ "values(78,'市直学校-59','',9)");
			database.execSQL("insert into organization "
					+ "values(79,'市直学校-60','',9)");
			
			database.execSQL("insert into organization "
					+ "values(80,'市直企业--11','',10)");
			database.execSQL("insert into organization "
					+ "values(81,'市直企业--12','',10)");
			database.execSQL("insert into organization "
					+ "values(82,'市直企业--13','',10)");
			database.execSQL("insert into organization "
					+ "values(83,'市直企业--14','',10)");
			database.execSQL("insert into organization "
					+ "values(84,'市直企业--15','',10)");
			database.execSQL("insert into organization "
					+ "values(85,'市直企业--16','',10)");
			
			database.execSQL("insert into organization "
					+ "values(86,'驻外团工委-11','',11)");
			database.execSQL("insert into organization "
					+ "values(87,'驻外团工委-12','',11)");
			database.execSQL("insert into organization "
					+ "values(88,'驻外团工委-13','',11)");
			database.execSQL("insert into organization "
					+ "values(89,'驻外团工委-14','',11)");
			database.execSQL("insert into organization "
					+ "values(90,'驻外团工委-15','',11)");
			database.execSQL("insert into organization "
					+ "values(91,'驻外团工委-16','',11)");
 
			database.setTransactionSuccessful();
			
		} finally {
			
			database.endTransaction();
			database.close();
		}
	}
	
	public void insertDepartment() {
		
		SQLiteDatabase database = null;
		
		try {
			database = DBHelper.getInstance(getContext()).getWritableDatabase();
			
			database.beginTransaction();
			
			database.execSQL("insert into department "
					+ "values(1,'部门1','')");
			database.execSQL("insert into department "
					+ "values(2,'部门2','')");
			database.execSQL("insert into department "
					+ "values(3,'部门3','')");
			database.execSQL("insert into department "
					+ "values(4,'部门4','')");
			database.execSQL("insert into department "
					+ "values(5,'部门5','')");
			database.execSQL("insert into department "
					+ "values(6,'部门6','')");
			database.execSQL("insert into department "
					+ "values(7,'部门7','')");
			database.execSQL("insert into department "
					+ "values(8,'部门8','')");
			
			database.setTransactionSuccessful();
			
		} finally {
			
			database.endTransaction();
			database.close();
		}
	}
	
	public void insertPosition() {
		
		SQLiteDatabase database = null;
		
		try {
			database = DBHelper.getInstance(getContext()).getWritableDatabase();
			
			database.beginTransaction();
			
			database.execSQL("insert into position_ "
					+ "values(1,'司令','')");
			database.execSQL("insert into position_ "
					+ "values(2,'军长','')");
			database.execSQL("insert into position_ "
					+ "values(3,'师长','')");
			database.execSQL("insert into position_ "
					+ "values(4,'营长','')");
			database.execSQL("insert into position_ "
					+ "values(5,'连长','')");
			database.execSQL("insert into position_ "
					+ "values(6,'排长','')");
			database.execSQL("insert into position_ "
					+ "values(7,'工兵','')");
			database.execSQL("insert into position_ "
					+ "values(8,'地雷','')");
			
			database.setTransactionSuccessful();
			
		} finally {
			
			database.endTransaction();
			database.close();
		}
	}
	
	public void insertStaff() {
		
		SQLiteDatabase database = null;
		
		try {
			database = DBHelper.getInstance(getContext()).getWritableDatabase();
			
			database.beginTransaction();
			
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(21,'张三','13688888888','11111111','075723456789','21','1','1')");
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(22,'张三1','13688888888','11111111','075723456789','21','1','2')");
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(23,'张三2','13688888888','11111111','075723456789','21','1','3')");
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(24,'张三3','13688888888','11111111','075723456789','21','2','1')");
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(25,'张三5','13688888888','11111111','075723456789','21','2','2')");
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(26,'张三6','13688888888','11111111','075723456789','21','2','3')");
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(27,'张三7','13688888888','11111111','075723456789','21','3','1')");
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(28,'张三8','13688888888','11111111','075723456789','21','3','2')");
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(29,'张三9','13688888888','11111111','075723456789','21','3','3')");
			
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(30,'张三10','13688888888','11111111','075723456789','22','1','1')");
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(31,'张三11','13688888888','11111111','075723456789','22','1','2')");
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(32,'张三12','13688888888','11111111','075723456789','22','1','3')");
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(33,'张三13','13688888888','11111111','075723456789','22','1','4')");
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(34,'张三14','13688888888','11111111','075723456789','22','1','5')");
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(35,'张三15','13688888888','11111111','075723456789','22','1','6')");
			database.execSQL("insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) "
					+ "values(36,'张三16','13688888888','11111111','075723456789','22','1','7')");			
			
			database.setTransactionSuccessful();
			
		} finally {
			
			database.endTransaction();
			database.close();
		}
	}
	
	public void updateWarrant() {
		
		SQLiteDatabase database = null;
		
		try {
			database = DBHelper.getInstance(getContext()).getWritableDatabase();
			
			database.beginTransaction();
			
			database.execSQL("update staff set is_warrant = '1'");
			database.execSQL("insert into organization "
					+ "values(100,'南海区团委-11-1','',26)");
			
			database.setTransactionSuccessful();
			
		} finally {
			
			database.endTransaction();
			database.close();
		}
	}
	
}
