//
//  LoginViewController.h
//  YouthLeague
//
//  Created by Allen on 13-4-23.
//  Copyright (c) 2013年 Allen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Staff.h"

@interface LoginViewController : UIViewController

@property Staff *staff;

@property (weak, nonatomic) IBOutlet UITextField *userMobile;
@property (weak, nonatomic) IBOutlet UITextField *password;
@property (weak, nonatomic) IBOutlet UISwitch *visibleSwitch;
@property (weak, nonatomic) IBOutlet UISwitch *rememberMeswitch;
@property (weak, nonatomic) IBOutlet UISwitch *autoLoginSwitch;
@property (weak, nonatomic) IBOutlet UIButton *registerButton;
@property (weak, nonatomic) IBOutlet UIButton *loginButton;

- (IBAction)rememberMeChange:(UISwitch *)sender;
- (IBAction)autoLoginChange:(UISwitch *)sender;
- (IBAction)visibleChange:(UISwitch *)sender;
- (IBAction)userRegister:(id)sender;
- (IBAction)login:(id)sender;
- (IBAction)backgroudTap:(id)sender;

@end
