//
//  StaffDetailViewController.m
//  YouthLeague
//
//  Created by Allen on 13-5-5.
//  Copyright (c) 2013年 Allen. All rights reserved.
//

#import "StaffDetailViewController.h"
#import "StaffDetailCell.h"
#import "ConstantValues.h"
#import <sqlite3.h>


@interface StaffDetailViewController ()
{
    sqlite3 *database;
    NSString *positionString;
    NSString *departmentString;
    NSString *organizationString;
}
@end

@implementation StaffDetailViewController

@synthesize staff;
@synthesize delegate;
@synthesize myTableView;
@synthesize makeCallButton, sendSmsButton;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];

    // Uncomment the following line to preserve selection between presentations.
    
//    if ([self respondsToSelector:@selector(setBackgroundImage:forState:)]) {
    
        [makeCallButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn"] stretchableImageWithLeftCapWidth:12.0 topCapHeight:20.0]
                               forState:UIControlStateNormal];
        [makeCallButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn_pressed"] stretchableImageWithLeftCapWidth:12.0 topCapHeight:20.0]
                               forState:UIControlStateHighlighted];
        
        [sendSmsButton setBackgroundImage:[[UIImage imageNamed:@"ic_blue_btn"] stretchableImageWithLeftCapWidth:12.0 topCapHeight:20.0]
                                  forState:UIControlStateNormal];
        [sendSmsButton setBackgroundImage:[[UIImage imageNamed:@"ic_blue_btn_pressed"] stretchableImageWithLeftCapWidth:12.0 topCapHeight:20.0]
                                  forState:UIControlStateHighlighted];
        
//    }else{
//        
//        [makeCallButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn"] resizableImageWithCapInsets:UIEdgeInsetsMake(20.0, 12.0, 20.0, 12.0) resizingMode:UIImageResizingModeStretch]
//                               forState:UIControlStateNormal];
//        [makeCallButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn_pressed"] resizableImageWithCapInsets:UIEdgeInsetsMake(20.0, 12.0, 20.0, 12.0) resizingMode:UIImageResizingModeStretch]
//                               forState:UIControlStateHighlighted];
//        
//        [sendSmsButton setBackgroundImage:[[UIImage imageNamed:@"ic_blue_btn"] resizableImageWithCapInsets:UIEdgeInsetsMake(20.0, 12.0, 20.0, 12.0) resizingMode:UIImageResizingModeStretch]
//                                  forState:UIControlStateNormal];
//        [sendSmsButton setBackgroundImage:[[UIImage imageNamed:@"ic_blue_btn_pressed"] resizableImageWithCapInsets:UIEdgeInsetsMake(20.0, 12.0, 20.0, 12.0) resizingMode:UIImageResizingModeStretch]
//                                  forState:UIControlStateHighlighted];
//    }
    
    //数据库操作
    if (SQLITE_OK!=sqlite3_open([[self dataFilePath] UTF8String], &database)) {
        
        sqlite3_close(database);
        NSLog(@"数据库打开失败");
        
    }else{
        
        //获取部门信息
        sqlite3_stmt * statement;
        NSString *queryString = [NSString stringWithFormat:@"SELECT name FROM department WHERE id = %d", staff.departmentId];

        if (SQLITE_OK==sqlite3_prepare_v2(database, [queryString UTF8String], -1, &statement, nil)) {

            while (SQLITE_ROW==sqlite3_step(statement)) {
                departmentString = [[NSString alloc] initWithUTF8String: (const char *) sqlite3_column_text(statement, 0)];
            }

            sqlite3_finalize(statement);
        }
        
        queryString = [NSString stringWithFormat:@"SELECT name FROM organization WHERE id = %d", staff.organizationId];
        
        if (SQLITE_OK==sqlite3_prepare_v2(database, [queryString UTF8String], -1, &statement, nil)) {
            
            while (SQLITE_ROW==sqlite3_step(statement)) {
                organizationString = [[NSString alloc] initWithUTF8String: (const char *) sqlite3_column_text(statement, 0)];
            }
            
            sqlite3_finalize(statement);
        }

        queryString = [NSString stringWithFormat:@"SELECT name FROM position WHERE id = %d", staff.positionId];
        
        if (SQLITE_OK==sqlite3_prepare_v2(database, [queryString UTF8String], -1, &statement, nil)) {
            
            while (SQLITE_ROW==sqlite3_step(statement)) {
                positionString = [[NSString alloc] initWithUTF8String: (const char *) sqlite3_column_text(statement, 0)];
            }
            
            sqlite3_finalize(statement);
        }
        
        sqlite3_close(database);
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(NSString *)dataFilePath
{
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    return [documentsDirectory stringByAppendingPathComponent:kFilename];
}

#pragma mark - Table view data source
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 1;
}

-(NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
{
    return @"团员详细信息";
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    //获取Cell单元
    static NSString *cellIdentifier = @"StaffDetailIdentifier";
    
    UINib *nib = [UINib nibWithNibName:@"StaffDetailCell" bundle:nil];
    [myTableView registerNib:nib forCellReuseIdentifier:cellIdentifier];
    
    StaffDetailCell *cell = [myTableView dequeueReusableCellWithIdentifier:cellIdentifier];
    
    cell.mobileLabel.text = staff.mobile;
    cell.nameLabel.text = staff.name;
    cell.phoneLabel.text = staff.phone;
    cell.positionLabel.text = positionString;
    cell.departmentLabel.text = departmentString;
    cell.organizationLabel.text = organizationString;
    
    return cell;
}

- (IBAction)sendSms:(UIButton *)sender {
    
//    [[UIApplication sharedApplication]openURL:[NSURL URLWithString:[NSString stringWithFormat:@"sms://%@", @"12345678"]]];
    
    NSString *phone = [NSString stringWithString:staff.mobile];

    if (![phone isEqualToString:@""]) {

        NSLog(@"send message, phone =[%@]", phone);

        //send message
        [[UIApplication sharedApplication]openURL:[NSURL URLWithString:[NSString stringWithFormat:@"sms://%@", phone]]];
        
    }
}

- (IBAction)makeCall:(UIButton *)sender {
    
//    NSURL *phoneNumberURL = [NSURL URLWithString:[NSString stringWithFormat:@"telprompt://%@", @"12345678"]];
//    [[UIApplication sharedApplication] openURL:phoneNumberURL];
    
    NSString *phone;
    if ([staff.mobile isEqualToString:@""]) {
        phone = [NSString stringWithString:staff.phone];
    }else{
        phone = [NSString stringWithString:staff.mobile];
    }

    if (![phone isEqualToString:@""]) {

        NSLog(@"make call, phone =[%@]", phone);

        //telephone call
        NSURL *phoneNumberURL = [NSURL URLWithString:[NSString stringWithFormat:@"tel://%@", phone]];
        [[UIApplication sharedApplication] openURL:phoneNumberURL];
        
    }
}

- (IBAction)backButton:(UIBarButtonItem *)sender
{
    self.delegate.isNeedOpen = NO;
    
    if ([self respondsToSelector:@selector(dismissViewControllerAnimated:completion:)]) {
        [self dismissViewControllerAnimated:YES completion:nil];
    }else{
        [self dismissModalViewControllerAnimated:YES];
    }
}

- (void)viewDidUnload {
    [self setMyTableView:nil];
    [self setMakeCallButton:nil];
    [self setSendSmsButton:nil];
    [super viewDidUnload];
}

@end
