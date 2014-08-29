//
//  RegisterViewController.m
//  YouthLeague
//
//  Created by Allen on 13-4-23.
//  Copyright (c) 2013年 Allen. All rights reserved.
//

#import "RegisterViewController.h"
#import "StaffDetailCell.h"
#import "Organization.h"
#import "Department.h"
#import "Position.h"
#import "Staff.h"

#import <sqlite3.h>

#import <SystemConfiguration/SystemConfiguration.h>

#define kFilename @"data.sqlite"

#define kUserLoginCheckUrl @"http://192.168.1.102:8080/youthleague/StaffServlet?type=%@"
//#define kUserLoginCheckUrl @"http://19.129.136.23:8080/YouthLeague/StaffServlet?type=%@"


#define kPositionTextField     1;
#define kDepartmentTextField   2;
#define kOrganizationTextField 3;

#define kSuperOrganization 0
#define kChildOrganization 1


@interface RegisterViewController ()
{
    sqlite3 *database;

    NSArray *departmentArray;
    NSArray *positionArray;
    
    NSMutableArray *superOrganization;
    NSMutableArray *childOrganization;
    NSMutableDictionary *organizationDic;
    
    NSArray *parentOrganization;
    NSArray *sonOrganization;
    
    BOOL isOpenPosition;
    BOOL isOpenDepartment;
    BOOL isOpenOrganization;
    
    int ID;
    int positionID;
    int departmentID;
    int organizationID;
    NSString *mobile;
    NSString *userName;
    NSString *phone;
    NSString *password;
    
    UIAlertView *netHintAlertView;
    NSMutableData *receiveData;
}
@end

@implementation RegisterViewController

@synthesize isRegister; //用于标记是否是注册用户
@synthesize staff;  //登录用户
@synthesize delegate;

@synthesize positionPickView, departmentPickView, organizationPickerView;
@synthesize positionTextField, departmentTextField, organizationTextField;
@synthesize mobileTextField, nameTextField, phoneTextField, pwdTextField;

@synthesize container;
@synthesize confirmButton, cancelButton;

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
    // Do any additional setup after loading the view from its nib.

    if ([UIImage instancesRespondToSelector: @selector(stretchableImageWithLeftCapWidth:topCapHeight:)]) {
        
        [cancelButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn"] stretchableImageWithLeftCapWidth:12.0 topCapHeight:20.0]
                               forState:UIControlStateNormal];
        [cancelButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn_pressed"] stretchableImageWithLeftCapWidth:12.0 topCapHeight:20.0]
                               forState:UIControlStateHighlighted];
        
        [confirmButton setBackgroundImage:[[UIImage imageNamed:@"ic_blue_btn"] stretchableImageWithLeftCapWidth:12.0 topCapHeight:20.0]
                                  forState:UIControlStateNormal];
        [confirmButton setBackgroundImage:[[UIImage imageNamed:@"ic_blue_btn_pressed"] stretchableImageWithLeftCapWidth:12.0 topCapHeight:20.0]
                                  forState:UIControlStateHighlighted];
        
    }else{
        
        [cancelButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn"] resizableImageWithCapInsets:UIEdgeInsetsMake(20.0, 12.0, 20.0, 12.0) resizingMode:UIImageResizingModeStretch]
                               forState:UIControlStateNormal];
        [cancelButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn_pressed"] resizableImageWithCapInsets:UIEdgeInsetsMake(20.0, 12.0, 20.0, 12.0) resizingMode:UIImageResizingModeStretch]
                               forState:UIControlStateHighlighted];
        
        [confirmButton setBackgroundImage:[[UIImage imageNamed:@"ic_blue_btn"] resizableImageWithCapInsets:UIEdgeInsetsMake(20.0, 12.0, 20.0, 12.0) resizingMode:UIImageResizingModeStretch]
                                  forState:UIControlStateNormal];
        [confirmButton setBackgroundImage:[[UIImage imageNamed:@"ic_blue_btn_pressed"] resizableImageWithCapInsets:UIEdgeInsetsMake(20.0, 12.0, 20.0, 12.0) resizingMode:UIImageResizingModeStretch]
                                  forState:UIControlStateHighlighted];
    }
    
    //数据库操作
    if (SQLITE_OK!=sqlite3_open([[self dataFilePath] UTF8String], &database)) {
        
        sqlite3_close(database);
        NSLog(@"数据库打开失败");
        
    }else{
        
        //获取组织信息
        superOrganization = [[NSMutableArray alloc] init];
        childOrganization = [[NSMutableArray alloc] init];
        
        Organization *organization;
        
        NSString *queryString = @"SELECT * FROM organization ORDER BY super_id ";
        sqlite3_stmt * statement;
        
        if (SQLITE_OK==sqlite3_prepare_v2(database, [queryString UTF8String], -1, &statement, nil)) {
            
            while (SQLITE_ROW==sqlite3_step(statement)) {
                
                organization = [[Organization alloc] init];
                organization.organizationId = sqlite3_column_int(statement, 0);
                char *temp = (char *) sqlite3_column_text(statement, 1);
                if (temp==NULL) {
                    organization.name = @"";
                }else{
                    organization.name = [[NSString alloc] initWithUTF8String: temp];
                }
                
                temp = (char *) sqlite3_column_text(statement, 2);
                if (temp==NULL) {
                    organization.address = @"";
                }else{
                    organization.address = [[NSString alloc] initWithUTF8String: temp];
                }

                organization.superId = sqlite3_column_int(statement, 3);
                
                if (-1==organization.superId) {
                    [superOrganization addObject:organization];
                }else{
                    [childOrganization addObject:organization];
                }
                
            }
            
            sqlite3_finalize(statement);
        }
        
        //
        organizationDic = [[NSMutableDictionary alloc] init];
        for (Organization *item in superOrganization) {
            
            int organizationId = item.organizationId;
            NSMutableArray *organizationItems = [[NSMutableArray alloc] init];
            
            for (Organization *childItem in childOrganization) {
                
                if (childItem.superId==organizationId) {
                    [organizationItems addObject:childItem];
                }
            }
            [organizationDic setObject:organizationItems forKey:[[NSString alloc] initWithFormat:@"%d", organizationId]];
        }
        
        parentOrganization = [[NSArray alloc] initWithArray:superOrganization];
        if ([parentOrganization count]>0) {
            sonOrganization = [organizationDic objectForKey:[[NSString alloc] initWithFormat:@"%d", ((Organization *)[parentOrganization objectAtIndex:0]).organizationId]];
        }
        
        //获取部门信息
        queryString = @"SELECT id,name FROM department ";
        
        NSMutableArray *tempArray2 = [[NSMutableArray alloc] init];
        Department *departmentItem;
        
        if (SQLITE_OK==sqlite3_prepare_v2(database, [queryString UTF8String], -1, &statement, nil)) {
            
            while (SQLITE_ROW==sqlite3_step(statement)) {
                departmentItem = [[Department alloc] init];
                departmentItem.departmentId = sqlite3_column_int(statement, 0);
                departmentItem.name = [[NSString alloc] initWithUTF8String: (const char *) sqlite3_column_text(statement, 1)];
                [tempArray2 addObject:departmentItem];
            }
            
            departmentArray = [[NSArray alloc] initWithArray:tempArray2];
            
            sqlite3_finalize(statement);
        }
        
        //获取职位信息
        queryString = @"SELECT id,name FROM position_ ";
        
        NSMutableArray *tempArray3 = [[NSMutableArray alloc] init];
        Position *positionItem;
        
        if (SQLITE_OK==sqlite3_prepare_v2(database, [queryString UTF8String], -1, &statement, nil)) {
            
            while (SQLITE_ROW==sqlite3_step(statement)) {
                positionItem = [[Position alloc] init];
                positionItem.positionId = sqlite3_column_int(statement, 0);
                positionItem.name = [[NSString alloc] initWithUTF8String: (const char *) sqlite3_column_text(statement, 1)];
                [tempArray3 addObject:positionItem];
            }
            
            positionArray = [[NSArray alloc] initWithArray:tempArray3];
            
            sqlite3_finalize(statement);
        }
    }

    ID = staff.staffId;
    positionID = staff.positionId;
    departmentID = staff.departmentId;
    organizationID = staff.organizationId;
    
    isOpenPosition = NO;
    isOpenDepartment = NO;
    isOpenOrganization = NO;
    
    if (!isRegister) {
        [self.mobileTextField setText:staff.mobile];
        [self.nameTextField setText:staff.name];
        [self.phoneTextField setText:staff.phone];
        [self.pwdTextField setText:staff.password];
        
        for (Position *item in positionArray) {
            if (item.positionId==positionID) {
                [self.positionTextField setText:item.name];
                break;
            }
        }
    
        for (Department *item in departmentArray) {
            if (item.departmentId==departmentID) {
                [self.departmentTextField setText:item.name];
                break;
            }
        }
        
        for (Organization *item in childOrganization) {
            if (item.organizationId==organizationID) {
                [self.organizationTextField setText:item.name];
                break;
            }
        }
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (void)viewDidUnload {
    [self setConfirmButton:nil];
    [self setCancelButton:nil];
    [self setMobileTextField:nil];
    [self setNameTextField:nil];
    [self setPhoneTextField:nil];
    [self setPwdTextField:nil];
    [self setPositionTextField:nil];
    [self setDepartmentTextField:nil];
    [self setOrganizationTextField:nil];
    [self setContainer:nil];
    [self setOrganizationPickerView:nil];
    [self setPositionPickView:nil];
    [self setDepartmentPickView:nil];

    [super viewDidUnload];
}

-(void)dealloc
{
    sqlite3_close(database);
}

-(NSString *)dataFilePath
{
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    return [documentsDirectory stringByAppendingPathComponent:kFilename];
}


#pragma mark picker data source
-(NSInteger) numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    if (pickerView==positionPickView || pickerView==departmentPickView) {
        return 1;
    }else {
        return 2;
    }
}

-(NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    if (pickerView==positionPickView) {
        return [positionArray count]>0?[positionArray count]:0;
    }else if (pickerView==departmentPickView){
        return [departmentArray count]>0?[departmentArray count]:0;
    }else{
        if (component==kSuperOrganization) {
            return [parentOrganization count]>0?[parentOrganization count]:0;
        }else{
            return [sonOrganization count]>0?[sonOrganization count]:0;
        }
    }
}

- (UIView *)pickerView:(UIPickerView *)pickerView viewForRow:(NSInteger)row forComponent:(NSInteger)component reusingView:(UIView *)view
{
    UILabel *myView;
    NSString *title;
    
    if (pickerView==organizationPickerView) {
        
        if (component==kSuperOrganization) {
            
            myView = view ? (UILabel *) view : [[UILabel alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 80.0f, 30.0f)];
            
            if ([parentOrganization count]>row) {
                title = ((Organization *)[parentOrganization objectAtIndex:row]).name;
            }
            
        }else{
            
            myView = view ? (UILabel *) view : [[UILabel alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 210.0f, 30.0f)];
            
            if ([sonOrganization count]>row) {
                title = ((Organization *)[sonOrganization objectAtIndex:row]).name;
            }
        }
        
    }else{
        
        myView = view ? (UILabel *) view : [[UILabel alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 280.0f, 30.0f)];
        if (pickerView==positionPickView) {
            if ([positionArray count]>row) {
                title = ((Position *)[positionArray objectAtIndex:row]).name;
            }
        }else{
            if ([departmentArray count]>row) {
                title = ((Department *)[departmentArray objectAtIndex:row]).name;
            }
        }
    }

    [myView setFont:[UIFont boldSystemFontOfSize:14]];
    myView.backgroundColor = [UIColor clearColor];
    myView.text = title;

    
    return myView;
}

//- (CGFloat)pickerView:(UIPickerView *)pickerView rowHeightForComponent:(NSInteger)component
//{
//    return 17.0f;
//}

#pragma mark picker delegate
-(NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component{
    
    NSString *title;
    
    if (pickerView==positionPickView) {
        
        if ([positionArray count]>row) {
            title = ((Position *)[positionArray objectAtIndex:row]).name;
        }
        
    }else if (pickerView==departmentPickView){
        
        if ([departmentArray count]>row) {
            title = ((Department *)[departmentArray objectAtIndex:row]).name;
        }
        
    }else{
        
        if (component==kSuperOrganization) {
            
            if ([parentOrganization count]>row) {
                title = ((Organization *)[parentOrganization objectAtIndex:row]).name;
            }
            
        }else{
            
            if ([parentOrganization count]>row) {
                title = ((Organization *)[sonOrganization objectAtIndex:row]).name;
            }
        }

    }
    
    return title;
}

-(void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    if (pickerView==positionPickView) {
        
        if ([positionArray count]>row) {
            positionID = ((Position *)[positionArray objectAtIndex:row]).positionId;
            [self.positionTextField setText:((Position *)[positionArray objectAtIndex:row]).name];
        }
        
    }else if (pickerView==departmentPickView){
        
        if ([departmentArray count]>row) {
            departmentID = ((Department *)[departmentArray objectAtIndex:row]).departmentId;
            [self.departmentTextField setText:((Department *)[departmentArray objectAtIndex:row]).name];
        }
        
    }else{
        
        if (component==kSuperOrganization) {
            
            if ([parentOrganization count]>row) {
                int tempId = ((Organization *)[parentOrganization objectAtIndex:row]).organizationId;
                sonOrganization = [organizationDic objectForKey:[[NSString alloc] initWithFormat:@"%d", tempId]];
                [pickerView reloadAllComponents];
            }

        }else{
            
            if ([sonOrganization count]>row) {
                organizationID = ((Organization *)[sonOrganization objectAtIndex:row]).organizationId;
                [self.organizationTextField setText:((Organization *)[sonOrganization objectAtIndex:row]).name];
            }

        }
    }

}

-(CGFloat)pickerView:(UIPickerView *)pickerView widthForComponent:(NSInteger)component
{
    if (pickerView==positionPickView || pickerView==departmentPickView) {
        return 320;
    }else{
        if (component==kSuperOrganization) {
            return 106;
        }else{
            return 232;
        }
    }
}

#pragma mark UIAlertView delegate
- (void)back
{
    self.delegate.isNeedOpen = NO;
    
    if ([self respondsToSelector:@selector(dismissViewControllerAnimated:completion:)]) {
        [self dismissViewControllerAnimated:YES completion:nil];
    }else{
        [self dismissModalViewControllerAnimated:true];
    }
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    [self back];
}

#pragma mark textField delegate
-(BOOL)textFieldShouldBeginEditing:(UITextField *)textField
{
    if (textField==positionTextField) {
        
        if (!isOpenPosition) {
            [self openPickView:positionPickView];
            isOpenPosition = YES;
        }else{
            [self closePickView:positionPickView];
            isOpenPosition = NO;
        }
        
        if (isOpenDepartment) {
            [self closePickView:departmentPickView];
            isOpenDepartment = NO;
        }

        if (isOpenOrganization) {
            [self closePickView:organizationPickerView];
            isOpenOrganization = NO;
        }

    }else if (textField==departmentTextField){
        
        if (!isOpenDepartment) {
            [self openPickView:departmentPickView];
            isOpenDepartment = YES;
        }else{
            [self closePickView:departmentPickView];
            isOpenDepartment = NO;
        }
        
        if (isOpenPosition) {
            [self closePickView:positionPickView];
            isOpenPosition = NO;
        }

        if (isOpenOrganization) {
            [self closePickView:organizationPickerView];
            isOpenOrganization = NO;
        }
        
    }else if(textField==organizationTextField){
        
        if (!isOpenOrganization) {
            [self openPickView:organizationPickerView];
            isOpenOrganization = YES;
        }else{
            [self closePickView:organizationPickerView];
            isOpenOrganization = NO;
        }
        
        if (isOpenPosition) {
            [self closePickView:positionPickView];
            isOpenPosition = NO;
        }

        if (isOpenDepartment) {
            [self closePickView:departmentPickView];
            isOpenDepartment = NO;
        }

    }else {
        
        if (isOpenPosition) {
            [self closePickView:positionPickView];
            isOpenPosition = NO;
        }
        
        if (isOpenDepartment) {
            [self closePickView:departmentPickView];
            isOpenDepartment = NO;
        }
        
        if (isOpenOrganization) {
            [self closePickView:organizationPickerView];
            isOpenOrganization = NO;
        }
        
        return YES;
    }
    
    return NO;
}

#pragma mark button action

- (IBAction)confirm:(UIButton *)sender {
    
    mobile = self.mobileTextField.text;
    userName = self.nameTextField.text;
    phone = self.phoneTextField.text;
    password = self.pwdTextField.text;
    
    //数据库操作
    sqlite3_stmt * statement;
    NSString *queryString = [NSString stringWithFormat:@"SELECT count(*) FROM staff WHERE is_departure = '0' AND  mobile = \"%@\"", mobile];
    if (SQLITE_OK==sqlite3_prepare_v2(database, [queryString UTF8String], -1, &statement, nil)) {
        
        if (sqlite3_data_count(statement)>0) {
            UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:@"指定的手机号码已注册" delegate:nil cancelButtonTitle:@"确定" otherButtonTitles:nil];
            [alertView show];
        }
        
        sqlite3_finalize(statement);
    }

    //先进行服务器端的数据添加或更新
    [self sendDataToServer];

}

    //本地数据的添加或更新
- (void)saveToLocalDb
{
    
    NSString *queryString;
    char *errMsg;
    NSString *msg;
    
    if (!isRegister) {
        
        queryString = [NSString stringWithFormat:@"UPDATE staff SET name=\"%@\" , mobile=\"%@\", password_=\"%@\", phone=\"%@\", organization_id=%d, department_id=%d, position_id=%d WHERE id = \"%d\"", userName, mobile, password, phone, organizationID, departmentID, positionID, staff.staffId];
    
        
        if (SQLITE_OK==sqlite3_exec(database, [queryString UTF8String], NULL, NULL, &errMsg)) {
            
            [staff setName:userName];
            [staff setMobile:mobile];
            [staff setPhone:phone];
            [staff setPassword:password];
            [staff setOrganizationId:organizationID];
            [staff setDepartmentId:departmentID];
            [staff setPositionId:positionID];
            
            msg = @"更新用户成功！";      
            
            UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:msg delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
            [alertView show];

            
        }else{
                    
            NSLog(@"更新用户失败:%s", errMsg);
            msg = @"更新用户失败！";
            
            UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:msg delegate:nil cancelButtonTitle:@"确定" otherButtonTitles:nil];
            [alertView show];
        }
    }
}


- (IBAction)cancel:(id)sender {
    [self back];
}

- (IBAction)backgroudTap:(id)sender {
    [self.mobileTextField resignFirstResponder];
    [self.nameTextField resignFirstResponder];
    [self.phoneTextField resignFirstResponder];
    [self.pwdTextField resignFirstResponder];
    [self.organizationTextField resignFirstResponder];
    [self.departmentTextField resignFirstResponder];
    [self.positionTextField resignFirstResponder];
    
    if (isOpenPosition) {
        [self closePickView:positionPickView];
        isOpenPosition = NO;
    }
    
    if (isOpenDepartment) {
        [self closePickView:departmentPickView];
        isOpenDepartment = NO;
    }
    
    if (isOpenOrganization) {
        [self closePickView:organizationPickerView];
        isOpenOrganization = NO;
    }
    
}

-(void)openPickView:(UIPickerView *)pickView
{
    [UIView beginAnimations:@"myAnimation" context:nil];
    [UIView setAnimationDuration:0.5f];   //动画执行时
    [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];  //设置动画的执行速度
    /*setAnimationCurve有四种常量：
     UIViewAnimationCurveLinear 在执行动画的时间内，速度始终保持如一。
     UIViewAnimationCurveEaseInOut 执行动画的时候，速度开始慢，然后加速，结束时再次变慢
     UIViewAnimationCurveEaseIn 速度开始慢，然后逐渐加速直到结束
     UIViewAnimationCurveEaseOut 速度开始快，然后逐渐减速直到结束*/
     pickView.center = CGPointMake(pickView.center.x, pickView.center.y - 305 );
    
    [UIView commitAnimations];
}

-(void)closePickView:(UIPickerView *)pickView
{
    [UIView beginAnimations:@"myAnimation" context:nil];
    [UIView setAnimationDuration:0.5f];   //动画执行时
    [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];  //设置动画的执行速度
    
    pickView.center = CGPointMake(pickView.center.x, pickView.center.y + 305 );
    
    [UIView commitAnimations];

}

-(void)sendDataToServer
{
    netHintAlertView = [[UIAlertView alloc] initWithTitle:nil message: @"请稍候，正在向服务器传送数据"
                                                 delegate:self cancelButtonTitle: nil otherButtonTitles: nil];
    
    UIActivityIndicatorView *activityView = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
    activityView.frame = CGRectMake(120.f, 48.0f, 37.0f, 37.0f);
    [netHintAlertView addSubview:activityView];
    [activityView startAnimating];
    [netHintAlertView show];
    
    //post提交的参数，格式如下：
    //参数1名字=参数1数据&参数2名字＝参数2数据&参数3名字＝参数3数据&...
    NSString *post = [NSString stringWithFormat:@"id=%d&mobile=%@&name=%@&password1=%@&phone=%@&department_id=%d&position_id=%d&organization_id=%d&password2=%@&no_web='1'", ID, mobile, userName, password, phone, departmentID, positionID, organizationID, password];
    
    NSLog(@"post:%@",post);
    
    //将NSSrring格式的参数转换格式为NSData，POST提交必须用NSData数据。
    NSData *postData = [post dataUsingEncoding:NSUTF8StringEncoding allowLossyConversion:YES];
    //计算POST提交数据的长度
    NSString *postLength = [NSString stringWithFormat:@"%d",[postData length]];
    NSLog(@"postLength=%@",postLength);
    //定义NSMutableURLRequest
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
    //设置提交目的url
    NSString *operator;
    if (isRegister) {
        operator = @"logon";
    }else{
        operator = @"oneStaffupdate";
    }
    [request setURL:[NSURL URLWithString:[[NSString alloc] initWithFormat:kUserLoginCheckUrl, operator]]];
    //设置提交方式为 POST
    [request setHTTPMethod:@"POST"];
    //设置http-header:Content-Type
    //这里设置为 application/x-www-form-urlencoded ，如果设置为其它的，比如text/html;charset=utf-8，或者 text/html 等，都会出错。不知道什么原因。
    [request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    //设置http-header:Content-Length
    [request setValue:postLength forHTTPHeaderField:@"Content-Length"];
    //设置需要post提交的内容
    [request setHTTPBody:postData];
    
    [NSURLConnection connectionWithRequest:request delegate:self];
    
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
    NSString *msg;
    if (isRegister) {
        msg = @"注册用户失败";
    }else{
        msg = @"修改用户失败";
    }
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:msg delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
    [alertView show];
    [netHintAlertView dismissWithClickedButtonIndex:0 animated:YES];
}

-(void)connectionDidFinishLoading:(NSURLConnection *)connection
{
    
    [netHintAlertView dismissWithClickedButtonIndex:0 animated:YES];
    
    NSString *msg;
    
    if (receiveData!=nil) {
            
            NSString *result = [[NSString alloc] initWithData:receiveData encoding:NSUTF8StringEncoding];
            NSLog(@"user login check result:[%@]",result);
            
//            int length = [result length];
//            NSString *newStaffId = [result substringToIndex:length - 1];
        
            @try
            {
                UIAlertView *alertView;
                
                switch ([result intValue]) {
                        
                    case 0:
                        if (isRegister) {
                            
                            alertView = [[UIAlertView alloc] initWithTitle:@"" message:@"注册用户成功" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
                            [alertView show];
                            
                            if ([self respondsToSelector:@selector(dismissViewControllerAnimated:completion:)]) {
                                [self dismissViewControllerAnimated:NO completion:nil];
                            }else{
                                [self dismissModalViewControllerAnimated:NO];
                            }
                            
                        }else{
                            [self saveToLocalDb];
                        }

                        break;
                        
                   case -1:

                        alertView = [[UIAlertView alloc] initWithTitle:@"" message:@"指定的手机号码已被注册" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
                        [alertView show];

                        break;
                        
                    case -5:
                        alertView = [[UIAlertView alloc] initWithTitle:@"" message:@"操作失败，请联系管理员" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
                        [alertView show];
                        break;
                        
                };
                
                                
            }@catch (NSException *e) {
                
                if (isRegister) {
                    msg = @"注册用户失败，请联系管理员";
                }else{
                    msg = @"修改用户失败，请联系管理员";
                }
                
                UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:msg delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
                [alertView show];
            }
            
            
    }else{
        
        if (isRegister) {
            msg = @"注册用户失败，请联系管理员";
        }else{
            msg = @"修改用户失败，请联系管理员";
        }
        
        UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:msg delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
        [alertView show];
        
    }
        
    
}

@end
