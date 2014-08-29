//
//  DBService.h
//  YouthLeague
//
//  Created by neusoft on 5/21/13.
//  Copyright (c) 2013 Allen. All rights reserved.
//

#import <Foundation/Foundation.h>

#import <sqlite3.h>



@interface DBService : NSObject

-(sqlite3 *)openDb;
-(void)closeDb;

@end
