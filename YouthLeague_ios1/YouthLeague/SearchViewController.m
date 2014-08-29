//
//  HomeViewController.m
//  YouthLeague
//
//  Created by Allen on 13-4-24.
//  Copyright (c) 2013年 Allen. All rights reserved.
//

#import "SearchViewController.h"
#import "RegisterViewController.h"
#import "StaffDetailViewController.h"
#import "SmsViewController.h"

#import "Organization.h"
#import "Staff.h"
#import "Department.h"
#import "Position.h"

#import "ConstantValues.h"

#import "Category.h"
#import "SectionInfo.h"


#import <sqlite3.h>


#define kSuperOrganization 0
#define kChildOrganization 1


@interface SearchViewController ()
{
    sqlite3 *database;
    NSMutableArray *superOrganization;
    NSMutableArray *childOrganization;
    NSMutableDictionary *organizationDic;
    
    NSArray *parentOrganization;
    NSArray *sonOrganization;

    BOOL isOpen;
    
    NSMutableArray *searchStaffArray;
    NSArray *searchResultArray;

    UIAlertView *netHintAlertView;
    NSURLConnection *getLastUpdateDateConnection;
    NSMutableData *receiveData;
}


@end

@implementation SearchViewController

@synthesize staff;
@synthesize delegate;

@synthesize handleImage, viewContainer, navBar, pickView, mySearchBar, searchTableView;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        
        isOpen = YES;
        
        // Custom initialization
        //数据库操作
        if (SQLITE_OK!=sqlite3_open([[self dataFilePath] UTF8String], &database)) {
            
            sqlite3_close(database);
            NSLog(@"数据库打开失败");
            
        }else{
            
            [self initPicker];
            
        }
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.searchTableView.sectionHeaderHeight = 45;
    self.searchTableView.sectionFooterHeight = 0;
    searchStaffArray = [[NSMutableArray alloc] init];

    
    UISwipeGestureRecognizer *upSwipeGesture = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleImageViewGesture:)];
    [upSwipeGesture setDirection:UISwipeGestureRecognizerDirectionUp];
    [handleImage addGestureRecognizer:upSwipeGesture];
    
    UISwipeGestureRecognizer *downGesture = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleImageViewGesture:)];
    [downGesture setDirection:UISwipeGestureRecognizerDirectionDown];
    [handleImage addGestureRecognizer:downGesture];
    
}

- (void)viewDidUnload {
    [self setViewContainer:nil];
    [self setNavBar:nil];
    [self setPickView:nil];

    
    [self setSearchTableView:nil];
    [self setMySearchBar:nil];
    [self setHandleImage:nil];
    [super viewDidUnload];
}

-(void)viewDidAppear:(BOOL)animated
{
    [mySearchBar becomeFirstResponder];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldRecognizeSimultaneouslyWithGestureRecognizer:(UIGestureRecognizer *)otherGestureRecognizer {
    return YES;
}

-(NSString *)dataFilePath
{
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    return [documentsDirectory stringByAppendingPathComponent:kFilename];
}

#pragma mark search bar delegate
-(void)searchBarSearchButtonClicked:(UISearchBar *)searchBar
{
//    NSString *searchTerm = [searchBar text];
//    [self handleSearch:searchTerm];
}


-(void)searchBarCancelButtonClicked:(UISearchBar *)searchBar
{
    [mySearchBar resignFirstResponder];
    [self searchEnd];
}

-(void)searchBar:(UISearchBar *)searchBar textDidChange:(NSString *)searchText
{
    if ([searchText length]!=0) {
        [self handleSearch:searchText];
    }
    
}

-(void)searchBarTextDidBeginEditing:(UISearchBar *)searchBar
{
    searchBar.showsCancelButton = YES;
    for (id cc in [searchBar subviews]) {
        if ([cc isKindOfClass:[UIButton class]]) {
            UIButton *button = (UIButton *)cc;
            [button setTitle:@"取消" forState:UIControlStateNormal];
        }
    }
}

-(BOOL)searchBarShouldBeginEditing:(UISearchBar *)searchBar
{
//    [searchBar becomeFirstResponder];
    [searchBar.inputView becomeFirstResponder];
    return YES;
}

- (void)searchEnd
{
    self.delegate.isNeedOpen = NO;
    
    if ([self respondsToSelector:@selector(dismissViewControllerAnimated:completion:)]) {
        [self dismissViewControllerAnimated:NO completion:nil];
    }else{
        [self dismissModalViewControllerAnimated:NO];
    }
}

#pragma mark search bar method
-(void)handleSearch:(NSString *)searchTerm
{
    [searchStaffArray removeAllObjects];
    
    //获取员工信息
    sqlite3_stmt * statement;
    NSMutableString *tempString = [[NSMutableString alloc] initWithString:@"SELECT * FROM staff WHERE (name like \'%%"];
    [tempString appendFormat:@"%@",searchTerm];
    [tempString appendString:@"%%\' OR mobile like \'%%"];
    [tempString appendFormat:@"%@",searchTerm];
    [tempString appendString:@"%%\' OR phone like \'%%"];
    [tempString appendFormat:@"%@",searchTerm];
    [tempString appendString:@"%%\')"];
    [tempString appendString:@" AND is_warrant = '1' AND is_departure = '0' AND is_hipe = '0' "];    
    
    NSString *queryString = [[NSString alloc] initWithString:tempString];

    if (SQLITE_OK==sqlite3_prepare_v2(database, [queryString UTF8String], -1, &statement, nil)) {

        Staff *tempStaff;

        while (SQLITE_ROW==sqlite3_step(statement)) {

            tempStaff = [Staff alloc];
            tempStaff.staffId = sqlite3_column_int(statement, 0);
            char *temp = (char *) sqlite3_column_text(statement, 1);
            if (temp==NULL) {
                tempStaff.mobile = @"";
            }else{
                tempStaff.mobile = [[NSString alloc] initWithUTF8String: temp];

            }
            
            temp = (char *) sqlite3_column_text(statement, 2);
            if (temp==NULL) {
                tempStaff.phone = @"";
            }else{
                tempStaff.phone = [[NSString alloc] initWithUTF8String: temp];
                
            }

            tempStaff.organizationId = sqlite3_column_int(statement,5);
            tempStaff.departmentId = sqlite3_column_int(statement,6);
            tempStaff.positionId = sqlite3_column_int(statement,7);

            temp = (char *) sqlite3_column_text(statement, 11);
            if (temp==NULL) {
                tempStaff.password = @"";
            }else{
                tempStaff.password = [[NSString alloc] initWithUTF8String: temp];
                
            }
            
            temp = (char *) sqlite3_column_text(statement, 12);
            if (temp==NULL) {
                tempStaff.name = @"";
            }else{
                tempStaff.name = [[NSString alloc] initWithUTF8String: temp];
                
            }
            
            [searchStaffArray addObject:tempStaff];
        }
        
        sqlite3_finalize(statement);
    }

    
    
    searchResultArray = [[NSArray alloc] initWithArray:searchStaffArray];
    NSLog(@"get staff information finished! [%d]", [searchResultArray count]);
   
    [searchTableView reloadData];

}

#pragma mark picker data source
-(NSInteger) numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    return 2;
}

-(NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    if (component==kSuperOrganization) {
        return [parentOrganization count]>0?[parentOrganization count]:0;
    }else{
        return [sonOrganization count]>0?[sonOrganization count]:0;
    }
    
}

- (UIView *)pickerView:(UIPickerView *)pickerView viewForRow:(NSInteger)row forComponent:(NSInteger)component reusingView:(UIView *)view
{
    UILabel *myView;
    NSString *title;
    
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

    [myView setFont:[UIFont boldSystemFontOfSize:14]];
    myView.backgroundColor = [UIColor clearColor];
    myView.text = title;
    
    return myView;
}

#pragma mark picker delegate

-(CGFloat)pickerView:(UIPickerView *)pickerView widthForComponent:(NSInteger)component
{
    if (component==kSuperOrganization) {
        return 106;
    }
    return 232;
}

#pragma mark table data source
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [searchResultArray count];
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static BOOL nibsRegistered = NO;
    static NSString *cellName = @"StaffCell";
    static NSString *cellIdentifier = @"StaffCellIdentifier";
    
    if (!nibsRegistered) {
        UINib *nib = [UINib nibWithNibName:cellName bundle:nil];
        [searchTableView registerNib:nib forCellReuseIdentifier:cellIdentifier];
        nibsRegistered = YES;
    }

    Staff *tempStaff = (Staff *)[searchResultArray objectAtIndex:indexPath.row];
    
    StaffCell *cell = [searchTableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (cell==nil) {
        UINib *nib = [UINib nibWithNibName:cellName bundle:nil];
        [searchTableView registerNib:nib forCellReuseIdentifier:cellIdentifier];
        cell = [searchTableView dequeueReusableCellWithIdentifier:cellIdentifier];
    }
    
//    NSLog(@"staff is %@", tempStaff);
//    NSLog(@"count:[%d], row:[%d]",[searchResultArray count], indexPath.row);
//    NSLog(@"cell is %@", cell);
    
    UISwipeGestureRecognizer *leftSwipeGesture = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleSwipeGesture:)];
    [leftSwipeGesture setDirection:UISwipeGestureRecognizerDirectionLeft];
    [cell addGestureRecognizer:leftSwipeGesture];
    
    UISwipeGestureRecognizer *rightGesture = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleSwipeGesture:)];
    [rightGesture setDirection:UISwipeGestureRecognizerDirectionRight];
    [cell addGestureRecognizer:rightGesture];
    
    UILongPressGestureRecognizer *longPressGesture = [[UILongPressGestureRecognizer alloc] initWithTarget:self action:@selector(handleLongPressGesture:)];
    [cell addGestureRecognizer:longPressGesture];
    
    SSCheckBoxView *checkbox = [[SSCheckBoxView alloc] initWithFrame:CGRectMake(278, 18, 40, 30)
                                                               style:kSSCheckBoxViewStyleGlossy
                                                             checked:NO];
    [cell.contentView addSubview:checkbox];
    checkbox.checked = tempStaff.isSelected;
    [checkbox setStateChangedBlock:^(SSCheckBoxView *checkbox) {
        tempStaff.isSelected = checkbox.checked;
        NSLog(@"is selected %@", tempStaff.name);
    }];
    
    cell.userName.text = tempStaff.name;
    cell.mobile.text = tempStaff.mobile;
    cell.phone.text = tempStaff.phone;
    
    return cell;
    

}


#pragma mark table view delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    [mySearchBar resignFirstResponder];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 73.0f;
}

#pragma mark staff cell delegate (table view cell gesture)
-(void)handleSwipeGesture:(UISwipeGestureRecognizer *) recognizer
{
    NSLog(@"2 swipe ");
    
    //Get location of the swipe
    CGPoint location = [recognizer locationInView:self.searchTableView];
    NSIndexPath *indexPath = [self.searchTableView indexPathForRowAtPoint:location];
    
    //Check if index path is valid
    if(indexPath)
    {
        int row = indexPath.row;
                
        Staff *tempStaff = (Staff *)[searchResultArray objectAtIndex:row];
        
        
        NSString *mobile;
        
        //send message
        if (recognizer.direction==UISwipeGestureRecognizerDirectionLeft) {
            
            NSLog(@"2 left swipe %d, %d", indexPath.section, indexPath.row);
            
            mobile = [NSString stringWithString:tempStaff.mobile];
            
            if (![mobile isEqualToString:@""]) {
                
                NSMutableArray *staffArray = [[NSMutableArray alloc] initWithObjects:tempStaff, nil];
                [self sendSms:staffArray];
            }
        }
        
        //make call
        if (recognizer.direction==UISwipeGestureRecognizerDirectionRight) {
            
            NSLog(@"2 right swipe %d, %d", indexPath.section, indexPath.row);
            
            if ([tempStaff.mobile isEqualToString:@""]) {
                mobile = [NSString stringWithString:tempStaff.phone];
            }else{
                mobile = [NSString stringWithString:tempStaff.mobile];
            }
            
            if (![mobile isEqualToString:@""]) {
                
                NSLog(@"2 make call, phone =[%@]", mobile);
                
                //telephone call
                NSURL *phoneNumberURL = [NSURL URLWithString:[NSString stringWithFormat:@"telprompt://%@", mobile]];
                [[UIApplication sharedApplication] openURL:phoneNumberURL];
                
            }
        }
        
    }
    
}

-(void)handleLongPressGesture:(UILongPressGestureRecognizer *) recognizer
{
    NSLog(@"2 long press ");
    
    //Get location of the swipe
    CGPoint location = [recognizer locationInView:self.searchTableView];
    NSIndexPath *indexPath = [self.searchTableView indexPathForRowAtPoint:location];
    
//    NSIndexPath *indexPath = [self.searchTableView indexPathForCell:((UITableViewCell *)recognizer.view)];
    
    //Check if index path is valid
    if(indexPath)
    {
        int row = indexPath.row;
        
        NSLog(@"2 long press %d, %d", indexPath.section, indexPath.row);
        
        Staff *tmpStaff = (Staff *)[searchResultArray objectAtIndex:row];
        
        StaffDetailViewController *detailViewController = [[StaffDetailViewController alloc] initWithNibName:@"StaffDetailViewController" bundle:nil];
        detailViewController.modalPresentationStyle = UIModalPresentationCurrentContext;
        detailViewController.staff = tmpStaff;
        
        if ([self respondsToSelector:@selector(presentViewController:animated:completion:)]) {
            [self presentViewController:detailViewController animated:YES completion:nil];   // ios 5 and 以上
        }else{
            [self presentModalViewController:detailViewController animated:YES];    // ios 4 and  以下
        }
    }        
    
}

-(IBAction)handleImageViewGesture:(UIGestureRecognizer *) recognizer
{
    self.delegate.isNeedOpen = YES;
    
    if ([self respondsToSelector:@selector(dismissViewControllerAnimated:completion:)]) {
        [self dismissViewControllerAnimated:NO completion:nil];
    }else{
        [self dismissModalViewControllerAnimated:NO];
    }
}


//- (IBAction)setting:(id)sender {
//    RegisterViewController *registerViewController = [[RegisterViewController alloc] initWithNibName:@"RegisterViewController" bundle:nil];
//    registerViewController.isRegister = NO;
//    registerViewController.staff = staff;
//    
//    if ([self respondsToSelector:@selector(presentViewController:animated:completion:)]) {
//        [self presentViewController:registerViewController animated:YES completion:nil];   // ios 5 and 以上
//    }else {
//        [self presentModalViewController:registerViewController animated:YES];
//    }
//}

- (IBAction)homeMenu:(UIBarButtonItem *)sender {
    
    UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:nil delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:@"更新数据" otherButtonTitles:@"个人信息",@"发送短信",nil];
    actionSheet.actionSheetStyle = UIActionSheetStyleBlackTranslucent;
    [actionSheet showInView:self.view];
}

- (IBAction)exit:(id)sender {
    if ([self respondsToSelector:@selector(dismissViewControllerAnimated:completion:)]) {
        [self dismissViewControllerAnimated:YES completion:nil];
    }else{
        [self dismissModalViewControllerAnimated:true];
    }
}

#pragma mark Action Sheet Delegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    
}

-(void)actionSheet:(UIActionSheet *)actionSheet didDismissWithButtonIndex:(NSInteger)buttonIndex
{
    if (buttonIndex == 0) {  //更新数据
        
        [self updateDb];
        
    }else if (buttonIndex == 1) {  //个人信息
        
        [self settingProfile];
        
    }else if(buttonIndex == 2) {  //发送短信
        
        NSMutableArray *staffArray = [[NSMutableArray alloc] init];

        for (Staff *staffItem in searchResultArray) {
            if (staffItem.isSelected) {
                [staffArray addObject:staffItem];
            }
        }
    
        if ([staffArray count]>0) {
            
            [self sendSms:staffArray];
            
        }else{
            
            UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:@"请选择发送到的人员" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
            [alertView show];
        }
        
        
    }else if(buttonIndex == 3) {
        
    }
}

-(void)settingProfile
{
    RegisterViewController *registerViewController = [[RegisterViewController alloc] initWithNibName:@"RegisterViewController" bundle:nil];
    registerViewController.isRegister = NO;
    registerViewController.staff = staff;
    
    
    if ([self respondsToSelector:@selector(presentViewController:animated:completion:)]) {
        [self presentViewController:registerViewController animated:YES completion:nil];   // ios 5 and 以上
    }else {
        [self presentModalViewController:registerViewController animated:YES];
    }
}

-(void)sendSms:(NSArray *)staffArray
{
    SmsViewController *smsViewController = [[SmsViewController alloc] initWithNibName:@"SmsViewController" bundle:nil];
    
    smsViewController.staffArray = staffArray;
    
    
    if ([self respondsToSelector:@selector(presentViewController:animated:completion:)]) {
        [self presentViewController:smsViewController animated:YES completion:nil];   // ios 5 and 以上
    }else {
        [self presentModalViewController:smsViewController animated:YES];
    }
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
    if (connection==getLastUpdateDateConnection) {
        
        if (receiveData!=nil) {
            
            NSString *result = [[NSString alloc] initWithData:receiveData encoding:NSUTF8StringEncoding];
            NSLog(@"user login check result:[%@]",result);
            
            int length = [result length];
            NSString *lastUpdateDateString = [result substringToIndex:length - 2];
            NSString *isExistFile = [result substringWithRange:NSMakeRange(length - 1, 1)];
            
            if ([isExistFile isEqualToString:@"0"]) {
                
                UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:@"所需文件不存在,请与管理员联系" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
                [alertView show];
                [netHintAlertView dismissWithClickedButtonIndex:0 animated:YES];
                
            }else{

                NSUserDefaults *defaults =[NSUserDefaults standardUserDefaults];
                NSString *oldUpdateDateString = [defaults objectForKey:@"UPDATE_DB_DATE"];
                if (oldUpdateDateString==nil) {
                    oldUpdateDateString = @"197001010001";
                }
                    
                NSDateFormatter *lastDateFormatter = [[NSDateFormatter alloc] init];
                [lastDateFormatter setLocale:[NSLocale currentLocale]];
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
                
                UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:@"下载数据库文件成功" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
                [alertView show];
                
                //数据库操作(先关闭再打开，以便读取最新数据)
                if (SQLITE_OK!=sqlite3_open([[self dataFilePath] UTF8String], &database)) {
                    
                    sqlite3_close(database);
                    NSLog(@"数据库打开失败");
                    
                }
                
                [self initPicker];
                
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

-(void)initPicker
{
    superOrganization = [[NSMutableArray alloc] init];
    childOrganization = [[NSMutableArray alloc] init];
    
    Organization *organization;
    
    //获取组织信息
    NSString *queryString = [NSString stringWithFormat:@"SELECT * FROM organization ORDER BY super_id "];
    sqlite3_stmt * statement;
    
    if (SQLITE_OK==sqlite3_prepare_v2(database, [queryString UTF8String], -1, &statement, nil)) {
        
        while (SQLITE_ROW==sqlite3_step(statement)) {
            
            organization = [Organization alloc];
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
    }else{
        NSLog(@"home get organization:%s", sqlite3_errmsg(database));
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
}

@end
