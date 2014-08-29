//
//  RegisterViewController.h
//  YouthLeague
//
//  Created by Allen on 13-4-23.
//  Copyright (c) 2013年 Allen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Staff.h"
#import "StaffDetailCell.h"
#import "HomeViewController.h"


@interface RegisterViewController : UIViewController<UIAlertViewDelegate, UIPickerViewDataSource, UIPickerViewDelegate, UITextFieldDelegate, UIActionSheetDelegate, NSURLConnectionDataDelegate>

@property Boolean isRegister;
@property Staff *staff;
@property HomeViewController *delegate;


@property (weak, nonatomic) IBOutlet UIPickerView *organizationPickerView;
@property (weak, nonatomic) IBOutlet UIPickerView *positionPickView;
@property (weak, nonatomic) IBOutlet UIPickerView *departmentPickView;

@property (weak, nonatomic) IBOutlet UIView *container;

@property (weak, nonatomic) IBOutlet UITextField *mobileTextField;
@property (weak, nonatomic) IBOutlet UITextField *nameTextField;
@property (weak, nonatomic) IBOutlet UITextField *phoneTextField;
@property (weak, nonatomic) IBOutlet UITextField *pwdTextField;
@property (weak, nonatomic) IBOutlet UITextField *positionTextField;
@property (weak, nonatomic) IBOutlet UITextField *departmentTextField;
@property (weak, nonatomic) IBOutlet UITextField *organizationTextField;


@property (weak, nonatomic) IBOutlet UIButton *confirmButton;
@property (weak, nonatomic) IBOutlet UIButton *cancelButton;



- (IBAction)confirm:(UIButton *)sender;
- (IBAction)cancel:(id)sender;
- (IBAction)backgroudTap:(id)sender;

@end
