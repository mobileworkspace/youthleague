//
//  StaffDetailCell.h
//  YouthLeague
//
//  Created by Allen on 13-4-30.
//  Copyright (c) 2013年 Allen. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface StaffDetailCell : UITableViewCell


@property (weak, nonatomic) IBOutlet UILabel *mobileLabel;
@property (weak, nonatomic) IBOutlet UILabel *nameLabel;
@property (weak, nonatomic) IBOutlet UILabel *phoneLabel;
@property (weak, nonatomic) IBOutlet UILabel *positionLabel;
@property (weak, nonatomic) IBOutlet UILabel *departmentLabel;
@property (weak, nonatomic) IBOutlet UILabel *organizationLabel;

@end
