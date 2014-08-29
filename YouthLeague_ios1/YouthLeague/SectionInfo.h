//
//  SectionArray.h
//  CustomTableTest
//
//  Created by Punit Sindhwani on 7/16/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class SectionView;
@class Category;

@interface SectionInfo : NSObject

@property (assign) BOOL isOpen;
@property (strong) Category *category;
@property (strong) SectionView *sectionView;
@property (nonatomic,strong,readonly) NSMutableArray *rowHeights;


- (NSUInteger)countOfRowHeights;

- (id)objectInRowHeightsAtIndex:(NSUInteger)index;

- (void)insertObject:(id)anObject inRowHeightsAtIndex:(NSUInteger)index;
- (void)removeObjectFromRowHeightsAtIndex:(NSUInteger)index;
- (void)replaceObjectInRowHeightsAtIndex:(NSUInteger)index withObject:(id)anObject;
- (void)insertRowHeights:(NSArray *)rowHeightArray atIndexes:(NSIndexSet *)indexes;
- (void)removeRowHeightsAtIndexes:(NSIndexSet *)indexes;
- (void)replaceRowHeightsAtIndexes:(NSIndexSet *)indexes withRowHeights:(NSArray *)rowHeightArray;

@end
