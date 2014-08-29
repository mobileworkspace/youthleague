//
//  Staff.h
//  YouthLeague
//
//  Created by Allen on 13-4-26.
//  Copyright (c) 2013年 Allen. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Staff : NSObject

@property(retain, nonatomic) NSString *mobile;
@property(retain, nonatomic) NSString *name;
@property(retain, nonatomic) NSString *phone;
@property(retain, nonatomic) NSString *password;

@property(retain, nonatomic) NSString *positionName;
@property(retain, nonatomic) NSString *departmentName;
@property(retain, nonatomic) NSString *organizationName;

@property int staffId;
@property int positionId;
@property int departmentId;
@property int organizationId;

@property BOOL isSelected;

@end
