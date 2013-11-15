package com.neusoft.youthleague.utils;

import com.neusoft.youthleague.model.Staff;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class Holder {
	
	ImageView imageView;
	TextView nameTextView;
	TextView positionTextView;
	TextView mobileTextView;
	TextView phoneTextView;
	CheckBox selectCheckBox;
	
	public Staff getStaffTag() {
		return (Staff) nameTextView.getTag();
	}
}
