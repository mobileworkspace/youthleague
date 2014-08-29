//
//  HomeViewController.h
//  YouthLeague
//
//  Created by Allen on 13-4-24.
//  Copyright (c) 2013年 Allen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SectionView.h"
#import "StaffCell.h"
#import "Staff.h"

#import "UIExpandableTableView.h"

@interface HomeViewController : UIViewController<UITableViewDataSource,UITableViewDelegate,UIPickerViewDataSource,UIPickerViewDelegate,SectionView, UISearchBarDelegate, UIActionSheetDelegate, NSURLConnectionDataDelegate>



@property Staff *staff;
@property BOOL isNeedOpen;

@property (weak, nonatomic) IBOutlet UIImageView *handleImage;
@property (weak, nonatomic) IBOutlet UIView *viewContainer;
@property (weak, nonatomic) IBOutlet UINavigationBar *navBar;
@property (weak, nonatomic) IBOutlet UIPickerView *pickView;
@property (weak, nonatomic) IBOutlet UITableView *myTableView;

@property (weak, nonatomic) IBOutlet UISearchBar *mySearchBar;



- (IBAction)homeMenu:(UIBarButtonItem *)sender;
- (IBAction)exit:(id)sender;


-(IBAction)handleImageViewGesture:(UIGestureRecognizer *) recognizer;



@end
