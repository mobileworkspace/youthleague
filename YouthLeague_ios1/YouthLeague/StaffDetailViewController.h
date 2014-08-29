//
//  StaffDetailViewController.h
//  YouthLeague
//
//  Created by Allen on 13-5-5.
//  Copyright (c) 2013年 Allen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Staff.h"
#import "HomeViewController.h"

@interface StaffDetailViewController : UIViewController<UITableViewDataSource,UITableViewDelegate>

@property Staff *staff;
@property HomeViewController *delegate;

@property (weak, nonatomic) IBOutlet UITableView *myTableView;
@property (weak, nonatomic) IBOutlet UIButton *makeCallButton;
@property (weak, nonatomic) IBOutlet UIButton *sendSmsButton;


- (IBAction)sendSms:(UIButton *)sender;
- (IBAction)makeCall:(UIButton *)sender;
- (IBAction)backButton:(UIBarButtonItem *)sender;

@end
