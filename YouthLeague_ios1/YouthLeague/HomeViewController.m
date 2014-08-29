//
//  HomeViewController.m
//  YouthLeague
//
//  Created by Allen on 13-4-24.
//  Copyright (c) 2013年 Allen. All rights reserved.
//

#import "SearchViewController.h"
#import "HomeViewController.h"
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

#import "UIExpandableTableView.h"
#import "GHCollapsingAndSpinningTableViewCell.h"

#import "NIDropDown.h"

#import <sqlite3.h>


#define kUITableExpandableSection  1

#define kSuperOrganization 0
#define kChildOrganization 1


@interface HomeViewController ()
{
    sqlite3 *database;
    
    NSMutableArray *superOrganization;
    NSMutableArray *childOrganization;
    NSMutableDictionary *organizationDic;
    
    NSArray *parentOrganization;
    NSArray *sonOrganization;

    BOOL isOpen;   //标记pickview的打开状态
    
    NSMutableArray *searchStaffArray;
    
    NSString *oldUpdateDateString;
    
    NIDropDown *dropDown;
    
    UIAlertView *netHintAlertView;
    NSURLConnection *getLastUpdateDateConnection;
    NSMutableData *receiveData;
}

//about expansion table view
@property (nonatomic, assign) NSInteger openSectionIndex;
@property (nonatomic, strong) NSMutableArray *sectionInfoArray;
@property (nonatomic, strong) NSMutableArray *categoryList;

- (void) setCategoryArray:(int)index;

@end


@implementation HomeViewController

@synthesize staff;
@synthesize isNeedOpen;

@synthesize handleImage, viewContainer, navBar, pickView, myTableView, mySearchBar;


@synthesize openSectionIndex;
@synthesize sectionInfoArray;
@synthesize categoryList;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        

    }
    return self;
}

- (void)initPicker
{
    superOrganization = [[NSMutableArray alloc] init];
    childOrganization = [[NSMutableArray alloc] init];
    
    Organization *organization;
    
    //获取组织信息
    NSString *queryString = [NSString stringWithFormat:@"SELECT * FROM organization WHERE super_id!=-100 ORDER BY super_id "];
    sqlite3_stmt * statement;
    
    if (SQLITE_OK==sqlite3_prepare_v2(database, [queryString UTF8String], -1, &statement, nil)) {
        
        while (SQLITE_ROW==sqlite3_step(statement)) {
            
            organization = [Organization alloc];
            organization.organizationId = sqlite3_column_int(statement, 0);
            char *name = (char *) sqlite3_column_text(statement, 1);
            if (name!=NULL) {
                organization.name = [[NSString alloc] initWithUTF8String: name];
            }else{
                organization.name = @"";
            }
            
            char *address = (char *) sqlite3_column_text(statement, 2);
            if (address!=NULL) {
                organization.address = [[NSString alloc] initWithUTF8String: address];
            }else{
                organization.address = @"";
            }
            
            organization.superId = sqlite3_column_int(statement, 3);
            
            if (-1==organization.superId) {
                [superOrganization addObject:organization];
            }else{
                [childOrganization addObject:organization];
            }
            
            NSLog(@"organizationId: %d , name:[%@]", organization.organizationId, organization.name);
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
        int tempId = ((Organization *)[parentOrganization objectAtIndex:0]).organizationId;
        sonOrganization = [organizationDic objectForKey:[[NSString alloc] initWithFormat:@"%d", tempId]];
    }
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    //升级数据库相关
    NSUserDefaults *defaults =[NSUserDefaults standardUserDefaults];
    oldUpdateDateString = [defaults objectForKey:@"UPDATE_DB_DATE"];
    if (oldUpdateDateString==nil) {
        oldUpdateDateString = @"197001010001";
    }
    
    NSCalendar *calendar = [[NSCalendar alloc] initWithCalendarIdentifier:NSGregorianCalendar];
    NSDateComponents *comps = [[NSDateComponents alloc] init];
    NSInteger unitFlags = NSYearCalendarUnit | NSMonthCalendarUnit | NSDayCalendarUnit | NSWeekdayCalendarUnit | NSHourCalendarUnit | NSMinuteCalendarUnit | NSSecondCalendarUnit;
    NSDate *curDate = [NSDate date];   //获取当前日期
    comps = [calendar components:unitFlags fromDate:curDate];
    int day = [comps day];

    //每月的1号和15号进行数据库自动升级
    if (day==1 || day==15 ) {
        [self updateDb];
    }
         
    isNeedOpen = YES;
    isOpen = NO;
    
    //数据库操作
    if (SQLITE_OK!=sqlite3_open([[self dataFilePath] UTF8String], &database)) {
        
        sqlite3_close(database);
        NSLog(@"数据库打开失败");
        
    }else{
        
        [self initPicker];
        
    }
    
    //可扩展tableview的设置
    [self setCategoryArray:0];
    self.myTableView.sectionHeaderHeight = 45;
    self.myTableView.sectionFooterHeight = 0;
    self.openSectionIndex = NSNotFound;
    
    //add gesture recognizer
    UISwipeGestureRecognizer *leftSwipeGesture = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleSwipeGesture:)];
    [leftSwipeGesture setDirection:UISwipeGestureRecognizerDirectionLeft];
    [myTableView addGestureRecognizer:leftSwipeGesture];
    
    UISwipeGestureRecognizer *rightGesture = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleSwipeGesture:)];
    [rightGesture setDirection:UISwipeGestureRecognizerDirectionRight];
    [myTableView addGestureRecognizer:rightGesture];
    
    UILongPressGestureRecognizer *longPressGesture = [[UILongPressGestureRecognizer alloc] initWithTarget:self action:@selector(handleLongPressGesture:)];
    [myTableView addGestureRecognizer:longPressGesture];
    
    
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
    [self setMyTableView:nil];
    
    [self setSectionInfoArray:nil];
    [self setCategoryList:nil];
    
    [self setMySearchBar:nil];
    [self setHandleImage:nil];
    
    [super viewDidUnload];
}

-(void)viewWillAppear:(BOOL)animated
{
    if (isNeedOpen) {
        
        [UIView beginAnimations:@"myAnimation" context:nil];
        [UIView setAnimationDuration:0.5f];   //动画执行时
        [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];  //设置动画的执行速度
        
        viewContainer.center = CGPointMake(viewContainer.center.x,
                                           viewContainer.center.y + 216 );
        
        [UIView commitAnimations];
        
        isOpen = YES;
        isNeedOpen = NO;
        
        [mySearchBar resignFirstResponder];
        
        for (id cc in [mySearchBar subviews]) {
            if ([cc isKindOfClass:[UIButton class]]) {
                UIButton *button = (UIButton *)cc;
                [button sendActionsForControlEvents:UIControlEventTouchUpInside];
            }
        }
    }
}

#pragma mark - 
#pragma mark - picker touch -

-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
//    UITouch* touch = [[event allTouches] anyObject];
//    if(touch.tapCount >=1){
//        [UIView beginAnimations:@"myAnimation" context:nil];
//        [UIView setAnimationDuration:0.5f];   //动画执行时
//        [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];  //设置动画的执行速度
//        /*setAnimationCurve有四种常量：
//         UIViewAnimationCurveLinear 在执行动画的时间内，速度始终保持如一。
//         UIViewAnimationCurveEaseInOut 执行动画的时候，速度开始慢，然后加速，结束时再次变慢
//         UIViewAnimationCurveEaseIn 速度开始慢，然后逐渐加速直到结束
//         UIViewAnimationCurveEaseOut 速度开始快，然后逐渐减速直到结束*/
//        viewContainer.center = CGPointMake(viewContainer.center.x,
//                                           viewContainer.center.y - 216 );
//        
//        [UIView commitAnimations];
//    }
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
    [self searchBegin];
}


-(void)searchBarCancelButtonClicked:(UISearchBar *)searchBar
{
    [mySearchBar resignFirstResponder];
}

-(void)searchBar:(UISearchBar *)searchBar textDidChange:(NSString *)searchText
{
    [self searchBegin];

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
    [self searchBegin];
    
    return YES;
}

- (void)searchBegin
{
    SearchViewController *searchViewController = [[SearchViewController alloc] initWithNibName:@"SearchViewController" bundle:nil];
    searchViewController.staff = staff;
    searchViewController.delegate = self;
    
    if ([self respondsToSelector:@selector(presentViewController:animated:completion:)]) {
        [self presentViewController:searchViewController animated:NO completion:nil];   // ios 5 and 以上
    }else {
        [self presentModalViewController:searchViewController animated:NO];
    }
    
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
-(NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component{
    
    NSString *title;
    
    if (component==kSuperOrganization) {
        
        if ([parentOrganization count]>row) {
            title = ((Organization *)[parentOrganization objectAtIndex:row]).name;
        }
        
    }else{
        
        if ([sonOrganization count]>row) {
            title = ((Organization *)[sonOrganization objectAtIndex:row]).name;
        }
    }
    return title;
}

-(void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    int organziationId;
    
    if (component==kSuperOrganization) {
        
        if ([parentOrganization count]>row) {
            organziationId = ((Organization *)[parentOrganization objectAtIndex:row]).organizationId;
            sonOrganization = [organizationDic objectForKey:[[NSString alloc] initWithFormat:@"%d", organziationId]];
            [pickerView reloadAllComponents];
        }

    }else{
        
        if ([sonOrganization count]>row) {
            organziationId = ((Organization *)[sonOrganization objectAtIndex:row]).organizationId;
            [self getStaff:organziationId];
            [self.myTableView reloadData];
        }

    }
}

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
    return [self.categoryList count];
}


-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    SectionInfo *array = [self.sectionInfoArray objectAtIndex:section];
    NSInteger rows = [[array.category sectionList] count];
    return (array.isOpen) ? rows : 0;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static BOOL nibsRegistered = NO;
    static NSString *cellName = @"StaffCell";
    static NSString *cellIdentifier = @"StaffCellIdentifier";
    
    if (!nibsRegistered) {
        UINib *nib = [UINib nibWithNibName:cellName bundle:nil];
        [myTableView registerNib:nib forCellReuseIdentifier:cellIdentifier];
        nibsRegistered = YES;
    }

    Category *category = (Category *)[self.categoryList objectAtIndex:indexPath.section];
        
    StaffCell *cell = [myTableView dequeueReusableCellWithIdentifier:cellIdentifier];
    
    if (cell==nil) {
        UINib *nib = [UINib nibWithNibName:cellName bundle:nil];
        [myTableView registerNib:nib forCellReuseIdentifier:cellIdentifier];
        cell = [myTableView dequeueReusableCellWithIdentifier:cellIdentifier];
    }
    
//    NSLog(@"staff is %@", category);
//    NSLog(@"row:[%d]", indexPath.row);
//    NSLog(@"cell is %@", cell);
    
    SSCheckBoxView *checkbox = [[SSCheckBoxView alloc] initWithFrame:CGRectMake(278, 18, 40, 30)
                                                               style:kSSCheckBoxViewStyleGlossy
                                                             checked:NO];    
    [cell.contentView addSubview:checkbox];
    checkbox.checked = ((Staff *)[category.sectionList objectAtIndex:indexPath.row]).isSelected;
    [checkbox setStateChangedBlock:^(SSCheckBoxView *checkbox) {
        ((Staff *)[category.sectionList objectAtIndex:indexPath.row]).isSelected = checkbox.checked;
    }];

    
    cell.userName.text = ((Staff *)[category.sectionList objectAtIndex:indexPath.row]).name;
    cell.mobile.text = ((Staff *)[category.sectionList objectAtIndex:indexPath.row]).mobile;
    cell.phone.text = ((Staff *)[category.sectionList objectAtIndex:indexPath.row]).phone;
    
    return cell;
    
}

- (void)updateCheckboxAtIndexPath:(NSIndexPath *)indexPath
{
    
}

-(CGFloat)tableView:(UITableView*)tableView heightForRowAtIndexPath:(NSIndexPath*)indexPath
{
    SectionInfo *array = [self.sectionInfoArray objectAtIndex:indexPath.section];
    return [[array objectInRowHeightsAtIndex:indexPath.row] floatValue];
}

- (UIView *) tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{

    SectionInfo *sectionInfo  = [self.sectionInfoArray objectAtIndex:section];
    if (!sectionInfo.sectionView)
    {
        NSString *title = sectionInfo.category.sectionName;
        sectionInfo.sectionView = [[SectionView alloc] initWithFrame:CGRectMake(0, 0, self.myTableView.bounds.size.width, 45) WithTitle:title Section:section delegate:self];
    }
    return sectionInfo.sectionView;

}

- (void) setCategoryArray:(int) index
{
    if (sonOrganization.count>0) {
        int organziationId = ((Organization *)[sonOrganization objectAtIndex:index]).organizationId;
        [self getStaff:organziationId];
        [myTableView reloadData];
    }
    
}

#pragma mark table view delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
//    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}


- (void) sectionClosed : (NSInteger) section{
    /*
     Create an array of the index paths of the rows in the section that was closed, then delete those rows from the table view.
     */
	SectionInfo *sectionInfo = [self.sectionInfoArray objectAtIndex:section];
	
    sectionInfo.isOpen = NO;
    NSInteger countOfRowsToDelete = [self.myTableView numberOfRowsInSection:section];
    
    if (countOfRowsToDelete > 0) {
        NSMutableArray *indexPathsToDelete = [[NSMutableArray alloc] init];
        for (NSInteger i = 0; i < countOfRowsToDelete; i++) {
            [indexPathsToDelete addObject:[NSIndexPath indexPathForRow:i inSection:section]];
        }
        [self.myTableView deleteRowsAtIndexPaths:indexPathsToDelete withRowAnimation:UITableViewRowAnimationTop];
    }
    self.openSectionIndex = NSNotFound;
}

- (void) sectionOpened : (NSInteger) section
{
    SectionInfo *array = [self.sectionInfoArray objectAtIndex:section];
    
    array.isOpen = YES;
    NSInteger count = [array.category.sectionList count];
    NSMutableArray *indexPathToInsert = [[NSMutableArray alloc] init];
    for (NSInteger i = 0; i<count;i++)
    {
        [indexPathToInsert addObject:[NSIndexPath indexPathForRow:i inSection:section]];
    }
    
    NSMutableArray *indexPathsToDelete = [[NSMutableArray alloc] init];
    NSInteger previousOpenIndex = self.openSectionIndex;
    
    if (previousOpenIndex != NSNotFound)
    {
        SectionInfo *sectionArray = [self.sectionInfoArray objectAtIndex:previousOpenIndex];
        sectionArray.isOpen = NO;
        NSInteger counts = [sectionArray.category.sectionList count];
        [sectionArray.sectionView toggleButtonPressed:FALSE];
        for (NSInteger i = 0; i<counts; i++)
        {
            [indexPathsToDelete addObject:[NSIndexPath indexPathForRow:i inSection:previousOpenIndex]];
        }
    }
    UITableViewRowAnimation insertAnimation;
    UITableViewRowAnimation deleteAnimation;
    if (previousOpenIndex == NSNotFound || section < previousOpenIndex)
    {
        insertAnimation = UITableViewRowAnimationTop;
        deleteAnimation = UITableViewRowAnimationBottom;
    }
    else
    {
        insertAnimation = UITableViewRowAnimationBottom;
        deleteAnimation = UITableViewRowAnimationTop;
    }
    
    [self.myTableView beginUpdates];
    [self.myTableView insertRowsAtIndexPaths:indexPathToInsert withRowAnimation:insertAnimation];
    [self.myTableView deleteRowsAtIndexPaths:indexPathsToDelete withRowAnimation:deleteAnimation];
    [self.myTableView endUpdates];

    self.openSectionIndex = section;
    
}

#pragma mark staff cell delegate (table view cell gesture)
-(void)handleSwipeGesture:(UISwipeGestureRecognizer *) recognizer
{
    //Get location of the swipe
    CGPoint location = [recognizer locationInView:self.myTableView];
    NSIndexPath *indexPath = [self.myTableView indexPathForRowAtPoint:location];
    
    //Check if index path is valid
    if(indexPath)
    {
        int section = indexPath.section;
        int row = indexPath.row;
        
        Staff *tempStaff = (Staff *)[[[self.categoryList objectAtIndex:section] sectionList] objectAtIndex:row];
        
        
        NSString *phone;
        
        //send message
        if (recognizer.direction==UISwipeGestureRecognizerDirectionLeft) {
            
            NSLog(@"left swipe %d, %d", indexPath.section, indexPath.row);
            
            phone = [NSString stringWithString:tempStaff.mobile];
            
            if (![phone isEqualToString:@""]) {
                
                NSArray *staffArray = [[NSArray alloc] initWithObjects:tempStaff, nil];
                [self sendSms:staffArray];
            }
        }

        if (recognizer.direction==UISwipeGestureRecognizerDirectionRight) {
            
            NSLog(@"right swipe %d, %d", indexPath.section, indexPath.row);
            
            if ([tempStaff.mobile isEqualToString:@""]) {
                phone = [NSString stringWithString:tempStaff.phone];
            }else{
                phone = [NSString stringWithString:tempStaff.mobile];
            }
            
            if (![phone isEqualToString:@""]) {
                
                NSLog(@"make call, phone =[%@]", phone);
                
                //telephone call                
                NSURL *phoneNumberURL = [NSURL URLWithString:[NSString stringWithFormat:@"telprompt://%@", phone]];
                [[UIApplication sharedApplication] openURL:phoneNumberURL];                
                
            }
        }
        
    }   
    
}

-(void)handleLongPressGesture:(UILongPressGestureRecognizer *) recognizer
{
    //Get location of the swipe
    CGPoint location = [recognizer locationInView:self.myTableView];
    
    //Get the corresponding index path within the table view
    NSIndexPath *indexPath = [self.myTableView indexPathForRowAtPoint:location];
    
    //Check if index path is valid
    if(indexPath)
    {
        int section = indexPath.section;
        int row = indexPath.row;
        
        NSLog(@"long press %d, %d", indexPath.section, indexPath.row);
        
        Staff *tmpStaff = (Staff *)[[[self.categoryList objectAtIndex:section] sectionList] objectAtIndex:row];
        
        StaffDetailViewController *detailViewController = [[StaffDetailViewController alloc] initWithNibName:@"StaffDetailViewController" bundle:nil];
        detailViewController.modalPresentationStyle = UIModalPresentationCurrentContext;
        detailViewController.staff = tmpStaff;
        detailViewController.delegate = self;
        
        if ([self respondsToSelector:@selector(presentViewController:animated:completion:)]) {
            [self presentViewController:detailViewController animated:YES completion:nil];   // ios 5 and 以上
        }else{
            [self presentModalViewController:detailViewController animated:YES];    // ios 4 and  以下
        }
    }        
    
}

-(void)getStaff:(int) organziationId
{
    //获取部门信息(即tableview中的section数量)
    NSMutableArray *categoryArray = [[NSMutableArray alloc] init];
    
    sqlite3_stmt * statement;
    NSString *queryString = [NSString stringWithFormat:@"SELECT DISTINCT b.* FROM staff a, department b WHERE a.organization_id = \"%d\" and a.department_id = b.id order by b.id", organziationId];
    
    if (SQLITE_OK==sqlite3_prepare_v2(database, [queryString UTF8String], -1, &statement, nil)) {
        
        Category *category;
        
        while (SQLITE_ROW==sqlite3_step(statement)) {
            category = [[Category alloc] init];
            category.sectionId = sqlite3_column_int(statement, 0);
            char *name = (char *) sqlite3_column_text(statement, 1);
            if (name!=NULL) {
                category.sectionName = [[NSString alloc] initWithUTF8String: name];
            }
            
            
            NSLog(@"sectionId:%d,sectionName:%@",category.sectionId,category.sectionName);
            
            [categoryArray addObject:category];
        }
        
        sqlite3_finalize(statement);
    }else{
        NSLog(@"home(get staff) department:%s", sqlite3_errmsg(database));
    }
    self.categoryList = categoryArray;
    
    //获取员工信息
    queryString = [NSString stringWithFormat:@"SELECT * FROM staff WHERE is_warrant = '1' AND is_departure = '0' AND is_hipe = '0' AND organization_id = \"%d\" order by department_id", organziationId];
    
    NSMutableArray *staffArray = [[NSMutableArray alloc] init];
    
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
                        
            tempStaff.organizationId = sqlite3_column_int(statement,3);
            tempStaff.departmentId = sqlite3_column_int(statement,4);
            tempStaff.positionId = sqlite3_column_int(statement,5);
            
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
            
            tempStaff.isSelected = NO;
            
            [staffArray addObject:tempStaff];
        }
        
        NSLog(@"staff size:%d",staffArray.count);
        
        sqlite3_finalize(statement);
        
    }else{
        NSLog(@"home(get staff) staff:%s", sqlite3_errmsg(database));
    }
    
    
    //整理数据，使其符合tableview的要求
    NSMutableArray *tempArray;
    for (Category *item in self.categoryList) {
        int departmentId = item.sectionId;
        tempArray = [[NSMutableArray alloc] init];
        for (Staff *staffItem in staffArray) {
            if (staffItem.departmentId==departmentId) {
                [tempArray addObject:staffItem];
            }
        }
        
        NSLog(@"departmentId:%d,staff count:%d",departmentId,tempArray.count);
        
        item.sectionList = tempArray;
    }
    
    //
    NSMutableArray *tmpArray = [[NSMutableArray alloc] init];
    
    for (Category *categoryItem in self.categoryList) {
        SectionInfo *sectionInfo = [[SectionInfo alloc] init];
        sectionInfo.category = categoryItem;
        sectionInfo.isOpen = NO;
        
        NSNumber *defaultHeight = [NSNumber numberWithInt:73];
        NSInteger count = [[sectionInfo.category sectionList] count];
        for (NSInteger i= 0; i<count; i++) {
            [sectionInfo insertObject:defaultHeight inRowHeightsAtIndex:i];
        }
        
        [tmpArray addObject:sectionInfo];
    }
    self.sectionInfoArray = tmpArray;
    
    self.openSectionIndex = NSNotFound;
    
}

-(IBAction)handleImageViewGesture:(UIGestureRecognizer *) recognizer
{

    if (isOpen) {  //关闭
        
        if ([recognizer isKindOfClass:[UITapGestureRecognizer class]] || ([recognizer isKindOfClass:[UISwipeGestureRecognizer class]] && ((UISwipeGestureRecognizer *)recognizer).direction==UISwipeGestureRecognizerDirectionUp)) {
            
            [UIView beginAnimations:@"myAnimation" context:nil];
            [UIView setAnimationDuration:0.5f];   //动画执行时
            [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];  //设置动画的执行速度
            /*setAnimationCurve有四种常量：
             UIViewAnimationCurveLinear 在执行动画的时间内，速度始终保持如一。
             UIViewAnimationCurveEaseInOut 执行动画的时候，速度开始慢，然后加速，结束时再次变慢
             UIViewAnimationCurveEaseIn 速度开始慢，然后逐渐加速直到结束
             UIViewAnimationCurveEaseOut 速度开始快，然后逐渐减速直到结束*/
            viewContainer.center = CGPointMake(viewContainer.center.x,
                                               viewContainer.center.y - 216 );
            
            [UIView commitAnimations];
            
            isOpen = NO;
        }
        
    }else{   //打开
        
        
        if ([recognizer isKindOfClass:[UITapGestureRecognizer class]] || ([recognizer isKindOfClass:[UISwipeGestureRecognizer class]] && ((UISwipeGestureRecognizer *)recognizer).direction==UISwipeGestureRecognizerDirectionDown)) {
            
            [UIView beginAnimations:@"myAnimation" context:nil];
            [UIView setAnimationDuration:0.5f];   //动画执行时
            [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];  //设置动画的执行速度
            
            viewContainer.center = CGPointMake(viewContainer.center.x,
                                               viewContainer.center.y + 216 );
            
            [UIView commitAnimations];
            
            isOpen = YES;
            
            [mySearchBar resignFirstResponder];
            
            for (id cc in [mySearchBar subviews]) {
                if ([cc isKindOfClass:[UIButton class]]) {
                    UIButton *button = (UIButton *)cc;
                    [button sendActionsForControlEvents:UIControlEventTouchUpInside];
                }
            }
        }
        
    }

}


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
        int count = [self.categoryList count];
        for (int i=0; i<count; i++) {
            Category *category = [self.categoryList objectAtIndex:i];
            for (Staff *staffItem in category.sectionList) {
                if (staffItem.isSelected) {
                    [staffArray addObject:staffItem];
                }
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
    registerViewController.delegate = self;
    
    
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
    smsViewController.delegate = self;
    
    
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
                //数据库操作(先关闭再打开，以便读取最新数据)            
            sqlite3_close(database);
            
            NSString *tempFileName = [[NSString alloc] initWithFormat:@"%@",[self dataFilePath]];
            NSFileManager *fileManager = [NSFileManager defaultManager];
            [fileManager removeItemAtPath:tempFileName error:nil];

            if ([receiveData writeToFile:tempFileName atomically:YES]) {
                
                NSUserDefaults *defaults =[NSUserDefaults standardUserDefaults];
                [defaults setObject:lastUpdateDateString forKey:@"UPDATE_DB_DATE"];
                
                UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"" message:@"下载数据库文件成功" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
                [alertView show];
                

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


@end
