//
//  LoginViewController.m
//  YouthLeague
//
//  Created by Allen on 13-4-23.
//  Copyright (c) 2013年 Allen. All rights reserved.
//

#import "LoginViewController.h"
#import "RegisterViewController.h"
#import "HomeViewController.h"
#import "ConstantValues.h"

//#import "FMDatabase.h"
#import <sqlite3.h>



@interface LoginViewController ()
{
    sqlite3 *database;
    
     NSString *oldUpdateDateString;
    
    UIAlertView *netHintAlertView;
    NSURLConnection *getLastUpdateDateConnection;
    NSMutableData *receiveData;
}
@end


@implementation LoginViewController

@synthesize staff;
@synthesize userMobile, password;
@synthesize visibleSwitch,rememberMeswitch,autoLoginSwitch, registerButton, loginButton;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        
        //数据库操作
        if (SQLITE_OK!=sqlite3_open([[self dataFilePath] UTF8String], &database)) {
            
            sqlite3_close(database);
            NSLog(@"数据库打开失败");
            
        }else{
            
        }
//        database = [FMDatabase databaseWithPath:[self dataFilePath]];
        
        //数据库操作 
//        if ([database open]) {
        
//        char *errMsg;
//            NSString *createSql = @"CREATE TABLE IF NOT EXISTS staff(id INTEGER PRIMARY KEY, name TEXT, mobile TEXT, password_ TEXT, phone TEXT, organization_id INTEGER NOT NULL, department_id INTEGER, position_id INTEGER, is_administrator TEXT DEFAULT '0', is_leader TEXT  DEFAULT '0', is_hipe TEXT DEFAULT '0', is_departure TEXT DEFAULT '0', is_warrant TEXT  DEFAULT '0' ); ";
//        sqlite3_exec(database, [createSql UTF8String], NULL, NULL, &errMsg);
//            [database executeUpdate:createSql];
        
//            createSql = @"CREATE TABLE IF NOT EXISTS department(id INTEGER PRIMARY KEY, name TEXT NOT NULL, note TEXT); ";
//        sqlite3_exec(database, [createSql UTF8String], NULL, NULL, &errMsg);
//            [database executeUpdate:createSql];
        
//            createSql = @"CREATE TABLE IF NOT EXISTS position_(id INTEGER PRIMARY KEY, name TEXT NOT NULL, note TEXT); ";
//            [database executeUpdate:createSql];
//        sqlite3_exec(database, [createSql UTF8String], NULL, NULL, &errMsg);
//            createSql = @"CREATE TABLE IF NOT EXISTS organization(id INTEGER PRIMARY KEY, name TEXT NOT NULL, address TEXT NOT NULL, super_id INTEGER); ";
//            [database executeUpdate:createSql];
        
//            if (SQLITE_OK!=sqlite3_exec(database, [createSql UTF8String], NULL, NULL, &errMsg)) {
//                
//            }
//        
//                NSString *insertSql = nil;
//
//        
//                insertSql = @"insert into organization values(1,'市团委','',-1);";
//            sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(2,'南海区团委','',-1);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(3,'顺德区团委','',-1);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(4,'禅城区团委','',-1);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(5,'高明区团委','',-1);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(6,'三水区团委','',-1);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(8,'市直机关','',-1);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(9,'市直学校','',-1);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(10,'市直企业','',-1);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(11,'驻外团工委','',-1);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//
//                insertSql = @"insert into organization values(21,'团市委-11','',1);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(22,'团市委-12','',1);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(23,'团市委-13','',1);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(24,'团市委-14','',1);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(25,'团市委-15','',1);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(26,'南海区团委-11','',2);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(27,'南海区团委-12','',2);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(28,'南海区团委-13','',2);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(29,'南海区团委-14','',2);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(30,'南海区团委-15','',2);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into organization values(31,'南海区团委-16','',2);";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//
//                insertSql = @"insert into department values(1,'科室1','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into department values(2,'科室2','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into department values(3,'科室3','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into department values(4,'科室4','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into department values(5,'科室5','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into department values(6,'科室6','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into department values(7,'科室7','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into department values(8,'科室8','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into department values(9,'科室9','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//
//        
//        insertSql = @"insert into department values(10,'科室10','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//
//
//
//                insertSql = @"insert into position_ values(1,'司令','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into position__ values(2,'军长','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into position_ values(3,'师长','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into position_ values(4,'营长','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into position_ values(5,'连长','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into position_ values(6,'排长','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into position_ values(7,'工兵','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into position__ values(8,'地雷','');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(1,'张三','13688888888','11111111','075723456789','21','1','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(2,'张三1','13688888888','11111111','075723456789','21','1','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(3,'张三2','13688888888','222222','075723456789','21','1','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(4,'张三3','13688888888','11111111','075723456789','21','2','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(5,'张三5','13688888888','11111111','075723456789','21','2','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(6,'张三6','13688888888','11111111','075723456789','21','2','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(7,'张三7','13688888888','11111111','075723456789','21','3','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(8,'张三8','13688888888','11111111','075723456789','21','3','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(9,'张三9','13688888888','11111111','075723456789','21','3','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(10,'张三10','13688888888','11111111','075723456789','22','1','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) vvalues(11,'张三11','13688888888','11111111','075723456789','22','1','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(12,'张三12','13688888888','11111111','075723456789','22','1','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(13,'张三13','13688888888','11111111','075723456789','22','1','4');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(14,'张三14','13688888888','11111111','075723456789','22','1','5');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(15,'张三15','13688888888','11111111','075723456789','22','1','6');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(16,'张三16','13688888888','11111111','075723456789','22','1','7');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(21,'张三','13688888888','11111111','075723456789','21','1','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(22,'张三1','13688888888','11111111','075723456789','21','1','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(23,'张三2','13688888888','222222','075723456789','21','1','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(24,'张三3','13688888888','11111111','075723456789','21','2','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(25,'张三5','13688888888','11111111','075723456789','21','2','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(26,'张三6','13688888888','11111111','075723456789','21','2','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(27,'张三7','13688888888','11111111','075723456789','21','3','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(28,'张三8','13688888888','11111111','075723456789','21','3','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(29,'张三9','13688888888','11111111','075723456789','21','3','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(30,'张三10','13688888888','11111111','075723456789','22','1','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) vvalues(31,'张三11','13688888888','11111111','075723456789','22','1','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(32,'张三12','13688888888','11111111','075723456789','22','1','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(33,'张三13','13688888888','11111111','075723456789','22','1','4');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(34,'张三14','13688888888','11111111','075723456789','22','1','5');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(35,'张三15','13688888888','11111111','075723456789','22','1','6');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(36,'张三16','13688888888','11111111','075723456789','22','1','7');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(41,'张三','13688888888','11111111','075723456789','21','1','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(42,'张三1','13688888888','11111111','075723456789','21','1','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(43,'张三2','13688888888','222222','075723456789','21','1','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(44,'张三3','13688888888','11111111','075723456789','21','2','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(45,'张三5','13688888888','11111111','075723456789','21','2','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(46,'张三6','13688888888','11111111','075723456789','21','2','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(47,'张三7','13688888888','11111111','075723456789','21','3','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(48,'张三8','13688888888','11111111','075723456789','21','3','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(49,'张三9','13688888888','11111111','075723456789','21','3','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(50,'张三10','13688888888','11111111','075723456789','22','1','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) vvalues(51,'张三11','13688888888','11111111','075723456789','22','1','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(52,'张三12','13688888888','11111111','075723456789','22','1','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(53,'张三13','13688888888','11111111','075723456789','22','1','4');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(54,'张三14','13688888888','11111111','075723456789','22','1','5');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(55,'张三15','13688888888','11111111','075723456789','22','1','6');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(56,'张三16','13688888888','11111111','075723456789','22','1','7');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);

//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(60,'张三10','13688888888','11111111','075723456789','21','4','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) vvalues(61,'张三11','13688888888','11111111','075723456789','21','4','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(62,'张三12','13688888888','11111111','075723456789','21','4','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(63,'张三13','13688888888','11111111','075723456789','21','4','4');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(64,'张三14','13688888888','11111111','075723456789','21','4','5');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(65,'张三15','13688888888','11111111','075723456789','21','4','6');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//
//                insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(66,'张三16','13688888888','11111111','075723456789','21','4','7');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(70,'张三10','13688888888','11111111','075723456789','21','5','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) vvalues(71,'张三11','13688888888','11111111','075723456789','21','5','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(72,'张三12','13688888888','11111111','075723456789','21','5','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(73,'张三13','13688888888','11111111','075723456789','21','5','4');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(74,'张三14','13688888888','11111111','075723456789','21','5','5');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(75,'张三15','13688888888','11111111','075723456789','21','5','6');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(76,'张三16','13688888888','11111111','075723456789','21','5','7');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(80,'张三10','13688888888','11111111','075723456789','21','6','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) vvalues(81,'张三11','13688888888','11111111','075723456789','21','6','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(82,'张三12','13688888888','11111111','075723456789','21','6','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(83,'张三13','13688888888','11111111','075723456789','21','6','4');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(84,'张三14','13688888888','11111111','075723456789','21','6','5');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(85,'张三15','13688888888','11111111','075723456789','21','6','6');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(86,'张三16','13688888888','11111111','075723456789','21','6','7');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(90,'张三10','13688888888','11111111','075723456789','21','7','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) vvalues(91,'张三11','13688888888','11111111','075723456789','21','7','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(92,'张三12','13688888888','11111111','075723456789','21','7','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(93,'张三13','13688888888','11111111','075723456789','21','7','4');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(94,'张三14','13688888888','11111111','075723456789','21','7','5');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(95,'张三15','13688888888','11111111','075723456789','21','7','6');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(96,'张三16','13688888888','11111111','075723456789','21','7','7');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(100,'张三10','13688888888','11111111','075723456789','21','8','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) vvalues(101,'张三11','13688888888','11111111','075723456789','21','8','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(102,'张三12','13688888888','11111111','075723456789','21','8','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(103,'张三13','13688888888','11111111','075723456789','21','8','4');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(104,'张三14','13688888888','11111111','075723456789','21','8','5');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(105,'张三15','13688888888','11111111','075723456789','21','8','6');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(106,'张三16','13688888888','11111111','075723456789','21','8','7');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(110,'张三10','13688888888','11111111','075723456789','21','9','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) vvalues(111,'张三11','13688888888','11111111','075723456789','21','9','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(112,'张三12','13688888888','11111111','075723456789','21','9','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(113,'张三13','13688888888','11111111','075723456789','21','9','4');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(114,'张三14','13688888888','11111111','075723456789','21','9','5');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(115,'张三15','13688888888','11111111','075723456789','21','9','6');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(116,'张三16','13688888888','11111111','075723456789','21','9','7');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(120,'张三10','13688888888','11111111','075723456789','21','10','1');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) vvalues(121,'张三11','13688888888','11111111','075723456789','21','10','2');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(122,'张三12','13688888888','11111111','075723456789','21','10','3');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(123,'张三13','13688888888','11111111','075723456789','21','10','4');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(124,'张三14','13688888888','11111111','075723456789','21','10','5');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(125,'张三15','13688888888','11111111','075723456789','21','10','6');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
//        
//        insertSql = @"insert into staff(id, name, mobile, password_, phone, organization_id, department_id, position_id) values(126,'张三16','13688888888','11111111','075723456789','21','10','7');";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
        
//        insertSql = @"update staff set is_warrant = '1' ";
//        sqlite3_exec(database, [insertSql UTF8String], NULL, NULL, &errMsg);
        
//        }
        
        
        NSUserDefaults *defaults =[NSUserDefaults standardUserDefaults];
        
        if ([defaults boolForKey:@"autoLogin"]) {
            [self autoLogin:[defaults objectForKey:@"userMobile"] password:[defaults objectForKey:@"password"]];
        } 
    }
    return self;
}

-(void)dealloc
{
    
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    if ([UIImage instancesRespondToSelector: @selector(stretchableImageWithLeftCapWidth:topCapHeight:)]) {
        
        [loginButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn"] stretchableImageWithLeftCapWidth:12.0 topCapHeight:20.0]
                               forState:UIControlStateNormal];
        [loginButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn_pressed"] stretchableImageWithLeftCapWidth:12.0 topCapHeight:20.0]
                               forState:UIControlStateHighlighted];
        
        [registerButton setBackgroundImage:[[UIImage imageNamed:@"ic_blue_btn"] stretchableImageWithLeftCapWidth:12.0 topCapHeight:20.0]
                                  forState:UIControlStateNormal];
        [registerButton setBackgroundImage:[[UIImage imageNamed:@"ic_blue_btn_pressed"] stretchableImageWithLeftCapWidth:12.0 topCapHeight:20.0]
                                  forState:UIControlStateHighlighted];
        
    }else{
        
        [loginButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn"] resizableImageWithCapInsets:UIEdgeInsetsMake(20.0, 12.0, 20.0, 12.0) resizingMode:UIImageResizingModeStretch]
                               forState:UIControlStateNormal];
        [loginButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn_pressed"] resizableImageWithCapInsets:UIEdgeInsetsMake(20.0, 12.0, 20.0, 12.0) resizingMode:UIImageResizingModeStretch]
                               forState:UIControlStateHighlighted];
        
        [registerButton setBackgroundImage:[[UIImage imageNamed:@"ic_blue_btn"] resizableImageWithCapInsets:UIEdgeInsetsMake(20.0, 12.0, 20.0, 12.0) resizingMode:UIImageResizingModeStretch]
                               forState:UIControlStateNormal];
        [registerButton setBackgroundImage:[[UIImage imageNamed:@"ic_blue_btn_pressed"] resizableImageWithCapInsets:UIEdgeInsetsMake(20.0, 12.0, 20.0, 12.0) resizingMode:UIImageResizingModeStretch]
                               forState:UIControlStateHighlighted];
    }

    
    NSUserDefaults *defaults =[NSUserDefaults standardUserDefaults];
    if ([defaults boolForKey:@"rememberMe"]) {
        userMobile.text = [defaults objectForKey:@"userMobile"];  //根据键值取出
        password.text = [defaults objectForKey:@"password"];
        [rememberMeswitch setOn:YES];
    }else{
        [rememberMeswitch setOn:NO];
    }
    
    if ([defaults boolForKey:@"autoLogin"]) {
        [autoLoginSwitch setOn:YES];
    }else{
        [autoLoginSwitch setOn:NO];
    }
    
    //userMobile.text = [[NSUserDefaults standardUserDefaults] objectForKey:@"SBFormattedPhoneNumber"];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)viewDidUnload
{
    [self setUserMobile:nil];
    [self setPassword:nil];
    [self setVisibleSwitch:nil];
    [self setRememberMeswitch:nil];
    [self setAutoLoginSwitch:nil];

    sqlite3_close(database);
    
    [self setRegisterButton:nil];
    [self setLoginButton:nil];
    [super viewDidUnload];
}


-(NSString *)dataFilePath{
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    return [documentsDirectory stringByAppendingPathComponent:kFilename];
}

- (IBAction)rememberMeChange:(UISwitch *)sender
{
    if (![sender isOn]) {
        [autoLoginSwitch setOn:NO animated:YES];
    }
}

- (IBAction)autoLoginChange:(UISwitch *)sender
{
    if ([sender isOn]) {
        [rememberMeswitch setOn:YES animated:YES];
    }
}

-(IBAction)visibleChange:(UISwitch *)sender
{
    if ([sender isOn]) {
        [password setSecureTextEntry:NO];
    }else{
        [password setSecureTextEntry:YES];
    }
}

- (IBAction)userRegister:(id)sender {
//    RegisterViewController *registerViewController = [[RegisterViewController alloc] initWithNibName:@"RegisterViewController" bundle:nil];
//    registerViewController.modalPresentationStyle = UIModalPresentationCurrentContext;
//    registerViewController.staff = nil;
//    registerViewController.isRegister = YES;
//    
//    if ([self respondsToSelector:@selector(presentViewController:animated:completion:)]) {
//        [self presentViewController:registerViewController animated:YES completion:nil];   // ios 5 and 以上
//    }else{
//        [self presentModalViewController:registerViewController animated:YES];    // ios 4 and  以下
//    }
    
    [self updateDb];
}

- (IBAction)login:(id)sender {
    
    NSUserDefaults *defaults =[NSUserDefaults standardUserDefaults];
    if ([rememberMeswitch isOn]) {
        [defaults setBool:YES forKey:@"rememberMe"];
        [defaults setObject:userMobile.text forKey:@"userMobile"];
        [defaults setObject:password.text forKey:@"password"];
    }else{
        [defaults setBool:NO forKey:@"rememberMe"];
    }
    
    if ([autoLoginSwitch isOn]) {
        [defaults setBool:YES forKey:@"autoLogin"];
    }else {
        [defaults setBool:NO forKey:@"autoLogin"];
    }
    
    [self autoLogin:userMobile.text password:password.text];
    
//    NSArray* db_array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
//    NSString* db_str = [db_array objectAtIndex:0];
//    NSString* database_str = [db_str stringByAppendingPathComponent:@"data.sqlite"];
//    FMDatabase* db = [FMDatabase databaseWithPath:database_str];
//    if([db open]){
//        FMResultSet* fResult = [db executeQuery:@"SELECT* FROM staff WHERE mobile=?",userMobile.text];
//    }
//    [db close];
//    HomeViewController* view= [[HomeViewController alloc] init];
//    [self presentViewController:view animated:YES completion:nil];
    
}

-(void)autoLogin:(NSString *)usermobile password:(NSString *)pwd
{
    //check database
    if ([self checkUser:usermobile password:pwd]) {
        [self doLogin];
    }else{
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"" message:@"用户不存在或密码错误" delegate:nil cancelButtonTitle:@"确定" otherButtonTitles:nil];
        [alert show];
    }
}

-(Boolean)checkUser:(NSString *)usermobile password:(NSString *)pwd
{
    if (usermobile==nil || pwd==nil) {
        return FALSE;
//        return TRUE;  //for test
    }
    
    //数据库操作
    sqlite3_stmt * statement;
    NSString *queryString = [NSString stringWithFormat:@"SELECT a.id, a.name, a.mobile, a.password_, a.phone, a.organization_id, a.department_id, a.position_id, b.name as positionName, c.name as departmentName, d.name as organizationName FROM staff a, position_ b, department c, organization d WHERE a.position_id=b.id AND a.department_id=c.id AND a.organization_id=d.id AND is_warrant = '1' AND is_departure = '0' AND a.mobile = '%@' AND a.password_ = '%@' LIMIT 1 " , usermobile, pwd];
    
    NSLog(@"login:%@", queryString);
    
    if (SQLITE_OK==sqlite3_prepare_v2(database, [queryString UTF8String], -1, &statement, nil)) {
        
        while (SQLITE_ROW==sqlite3_step(statement)) {

            staff = [Staff alloc];
            
            staff.staffId = sqlite3_column_int(statement, 0);
            
            staff.name = [[NSString alloc] initWithUTF8String: (const char *) sqlite3_column_text(statement, 1)];
            NSLog(@"staff = %@",staff.name);
            staff.mobile = [[NSString alloc] initWithUTF8String: (const char *) sqlite3_column_text(statement, 2)];
            staff.password = [[NSString alloc] initWithUTF8String: (const char *) sqlite3_column_text(statement, 3)];
            staff.phone = [[NSString alloc] initWithUTF8String: (const char *) sqlite3_column_text(statement, 4)];
            
            staff.organizationId = sqlite3_column_int(statement, 5);
            staff.departmentId = sqlite3_column_int(statement, 6);
            staff.positionId = sqlite3_column_int(statement, 7);
            
            staff.positionName = [[NSString alloc] initWithUTF8String: (const char *) sqlite3_column_text(statement, 8)];
            staff.departmentName = [[NSString alloc] initWithUTF8String: (const char *) sqlite3_column_text(statement, 9)];
            staff.organizationName = [[NSString alloc] initWithUTF8String: (const char *) sqlite3_column_text(statement, 10)];
            
            sqlite3_finalize(statement);
            
            return TRUE;
        }
    }else{
        NSLog(@"login:%s", sqlite3_errmsg(database));
    }
    
    return FALSE;
//    return TRUE;  //for test
}


-(void)doLogin
{
    HomeViewController *homeViewController = [[HomeViewController alloc] initWithNibName:@"HomeViewController" bundle:nil];
    homeViewController.staff = staff;
    
    [homeViewController setModalTransitionStyle:UIModalTransitionStyleCrossDissolve];
    
    if ([self respondsToSelector:@selector(presentViewController:animated:completion:)]) {
        [self presentViewController:homeViewController animated:YES completion:nil];
    }else{
        [self presentModalViewController:homeViewController animated:YES];
    }
}

- (IBAction)backgroudTap:(id)sender {
    [userMobile resignFirstResponder];
    [password resignFirstResponder];
}

#pragma mark database update
- (void)updateDb
{
    
    netHintAlertView = [[UIAlertView alloc] initWithTitle:nil message: @"正在从服务器上获取数据"
                                                 delegate: self cancelButtonTitle: nil otherButtonTitles: nil];
    
    UIActivityIndicatorView *activityView = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
    activityView.frame = CGRectMake(120.f, 48.0f, 37.0f, 37.0f);
    [netHintAlertView addSubview:activityView];
    [activityView startAnimating];
    [netHintAlertView show];
    
    //-----------获取最后更新数据时间 ------------------------------
    //定义NSMutableURLRequest
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
    //设置提交目的url
    [request setURL:[NSURL URLWithString:kUpdateDbCheck]];
    //设置提交方式为 POST
    [request setHTTPMethod:@"POST"];
    //设置http-header:Content-Type
    //这里设置为 application/x-www-form-urlencoded ，如果设置为其它的，比如text/html;charset=utf-8，或者 text/html 等，都会出错。不知道什么原因。
    [request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    //设置http-header:Content-Length
    [request setValue:@"0" forHTTPHeaderField:@"Content-Length"];
    NSData *postData = [@"" dataUsingEncoding:NSUTF8StringEncoding allowLossyConversion:YES];
    //设置需要post提交的内容
    [request setHTTPBody:postData];
    
    
    getLastUpdateDateConnection = [NSURLConnection connectionWithRequest:request delegate:self];
}

#pragma mark url connection data delegate
-(void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response
{
    receiveData = [NSMutableData data];
}

-(void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
{
    [receiveData appendData:data];
}

-(void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error
{
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:@"下载数据库文件失败" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
    [alertView show];
    [netHintAlertView dismissWithClickedButtonIndex:0 animated:YES];
}

-(void)connectionDidFinishLoading:(NSURLConnection *)connection
{
    NSString *lastUpdateDateString = @"197001010001";
    
    if (connection==getLastUpdateDateConnection) {
        
        if (receiveData!=nil) {
            
            NSString *result = [[NSString alloc] initWithData:receiveData encoding:NSUTF8StringEncoding];
            NSLog(@"user login check result:[%@]",result);
            
            int length = [result length];
            lastUpdateDateString = [result substringToIndex:length - 1];
            NSString *isExistFile = [result substringWithRange:NSMakeRange(length - 1, 1)];
            
            if ([isExistFile isEqualToString:@"0"]) {
                
                UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:@"所需文件不存在,请与管理员联系" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
                [alertView show];
                [netHintAlertView dismissWithClickedButtonIndex:0 animated:YES];
                
            }else{
                
                //升级数据库相关
                NSUserDefaults *defaults =[NSUserDefaults standardUserDefaults];
                oldUpdateDateString = [defaults objectForKey:@"UPDATE_DB_DATE"];
                if (oldUpdateDateString==nil) {
                    oldUpdateDateString = @"197001010001";
                }
                
                NSDateFormatter *lastDateFormatter = [[NSDateFormatter alloc] init];
//                [lastDateFormatter setLocale:[NSLocale currentLocale]];
                [lastDateFormatter setDateFormat:@"yyyyMMddHHmmss"];
                NSDate* lastUpdateDate = [lastDateFormatter dateFromString:lastUpdateDateString];
                NSDate* oldUpdateDate = [lastDateFormatter dateFromString:oldUpdateDateString];
                
                if ([oldUpdateDate laterDate:lastUpdateDate]==lastUpdateDate) {
                    //------------------获取新的数据库文件------------------------------------------
                    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
                    [request setURL:[NSURL URLWithString:kDownloadDbFile]];
                    [NSURLConnection connectionWithRequest:request delegate:self];
                    
                }else{
                    
                    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:@"当前数据已经是最新的数据" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
                    [alertView show];
                    [netHintAlertView dismissWithClickedButtonIndex:0 animated:YES];
                }
                
            }
            
        }else{
            
            UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:@"下载数据库文件失败" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
            [alertView show];
            [netHintAlertView dismissWithClickedButtonIndex:0 animated:YES];
        }
        
    }else{
        
        if (receiveData != nil){
            
            sqlite3_close(database);
            
            NSString *tempFileName = [[NSString alloc] initWithFormat:@"%@",[self dataFilePath]];
            NSFileManager *fileManager = [NSFileManager defaultManager];
            [fileManager removeItemAtPath:tempFileName error:nil];
            
            if ([receiveData writeToFile:tempFileName atomically:YES]) {
                
                NSUserDefaults *defaults =[NSUserDefaults standardUserDefaults];
                [defaults setObject:lastUpdateDateString forKey:@"UPDATE_DB_DATE"];
                
                UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:@"下载数据库文件成功" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
                [alertView show];
                
                //数据库操作(先关闭再打开，以便读取最新数据)
                
                if (SQLITE_OK!=sqlite3_open([[self dataFilePath] UTF8String], &database)) {
                    
                    sqlite3_close(database);
                    NSLog(@"数据库打开失败");
                }
                
                
            } else {
                
                UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:@"保存数据库文件失败" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
                [alertView show];
                
            }
            
        } else {
            
            UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:@"下载数据库文件失败" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
            [alertView show];
            
        }
        
        [netHintAlertView dismissWithClickedButtonIndex:0 animated:YES];
    }
}

@end
