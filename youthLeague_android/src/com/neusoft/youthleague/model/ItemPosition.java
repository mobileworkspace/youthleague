package com.neusoft.youthleague.model;

public class ItemPosition {

	int groupPosition;
	int childPosition;
	
	
	public ItemPosition(int groupPosition, int childPosition) {
		super();
		this.groupPosition = groupPosition;
		this.childPosition = childPosition;
	}
	
	public int getGroupPosition() {
		return groupPosition;
	}
	public void setGroupPosition(int groupPosition) {
		this.groupPosition = groupPosition;
	}
	public int getChildPosition() {
		return childPosition;
	}
	public void setChildPosition(int childPosition) {
		this.childPosition = childPosition;
	}
		
}
