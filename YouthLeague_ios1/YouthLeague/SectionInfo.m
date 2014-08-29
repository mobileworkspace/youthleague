//
//  SectionArray.m
//  CustomTableTest
//
//  Created by Punit Sindhwani on 7/16/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "SectionInfo.h"

@implementation SectionInfo

@synthesize isOpen;
@synthesize category;
@synthesize sectionView;
@synthesize rowHeights;

- init {
	
	self = [super init];
	if (self) {
		rowHeights = [[NSMutableArray alloc] init];
	}
	return self;
}


- (NSUInteger)countOfRowHeights {
	return [rowHeights count];
}

- (id)objectInRowHeightsAtIndex:(NSUInteger)index {
	return [rowHeights objectAtIndex:index];
}

- (void)insertObject:(id)anObject inRowHeightsAtIndex:(NSUInteger)index {
	[rowHeights insertObject:anObject atIndex:index];
}

- (void)insertRowHeights:(NSArray *)rowHeightArray atIndexes:(NSIndexSet *)indexes {
	[rowHeights insertObjects:rowHeightArray atIndexes:indexes];
}

- (void)removeObjectFromRowHeightsAtIndex:(NSUInteger)index {
	[rowHeights removeObjectAtIndex:index];
}

- (void)removeRowHeightsAtIndexes:(NSIndexSet *)indexes {
	[rowHeights removeObjectsAtIndexes:indexes];
}

- (void)replaceObjectInRowHeightsAtIndex:(NSUInteger)index withObject:(id)anObject {
	[rowHeights replaceObjectAtIndex:index withObject:anObject];
}

- (void)replaceRowHeightsAtIndexes:(NSIndexSet *)indexes withRowHeights:(NSArray *)rowHeightArray {
	[rowHeights replaceObjectsAtIndexes:indexes withObjects:rowHeightArray];
}

@end
