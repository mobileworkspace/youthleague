//
//  StaffDetailCell.m
//  YouthLeague
//
//  Created by Allen on 13-4-30.
//  Copyright (c) 2013年 Allen. All rights reserved.
//

#import "StaffDetailCell.h"

@implementation StaffDetailCell

@synthesize mobileLabel, nameLabel, phoneLabel, positionLabel, departmentLabel, organizationLabel;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
