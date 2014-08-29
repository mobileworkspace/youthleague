//
//  DBService.m
//  YouthLeague
//
//  Created by neusoft on 5/21/13.
//  Copyright (c) 2013 Allen. All rights reserved.
//

#import "DBService.h"

#define kFilename @"data.sqlite"

@implementation DBService
{
    sqlite3 *database;
}






-(sqlite3 *)openDb
{
    if (SQLITE_OK!=sqlite3_open([[self dataFilePath] UTF8String], &database)) {
        [self closeDb];
        NSLog(@"数据库打开失败");
    }
    
    return database;
}

-(void)closeDb
{
    sqlite3_close(database);
}

-(NSString *)dataFilePath{
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    return [documentsDirectory stringByAppendingPathComponent:kFilename];
}

@end
