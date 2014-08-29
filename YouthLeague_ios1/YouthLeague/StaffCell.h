//
//  StaffCell.h
//  YouthLeague
//
//  Created by Allen on 13-4-26.
//  Copyright (c) 2013年 Allen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SSCheckBoxView.h"

//@protocol StaffCellDelegate;

@interface StaffCell : UITableViewCell

@property int index;
@property (weak, nonatomic) IBOutlet UILabel *userName;
@property (weak, nonatomic) IBOutlet UILabel *mobile;
@property (weak, nonatomic) IBOutlet UILabel *phone;

//@property (weak, nonatomic) IBOutlet UILabel *placeLabel;
@property (weak, nonatomic) IBOutlet SSCheckBoxView *chkbox;



@end

