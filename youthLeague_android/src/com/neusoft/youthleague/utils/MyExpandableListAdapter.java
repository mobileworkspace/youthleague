package com.neusoft.youthleague.utils;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.neusoft.youthleague.R;
import com.neusoft.youthleague.model.Department;
import com.neusoft.youthleague.model.Staff;


public class MyExpandableListAdapter extends BaseExpandableListAdapter {
	  
	private Map<Integer,List<Staff>> itemList;
	private List<Department> groupList;
	private LayoutInflater inflater;
    
    
    public MyExpandableListAdapter(Context context, Map<Integer,List<Staff>> childData, List<Department> groupData) {
		super();
		// TODO Auto-generated constructor stub
		this.itemList = childData;
		this.groupList = groupData;
		this.inflater = LayoutInflater.from(context);
	}

	public Object getChild(int groupPosition, int childPosition) {
		int id = groupList.get(groupPosition).getId();
        return itemList.get(id).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
    	int id = groupList.get(groupPosition).getId();
        return itemList.get(id).get(childPosition).getId();
    }

    public int getChildrenCount(int groupPosition) {
    	int id = groupList.get(groupPosition).getId();
        return itemList.get(id).size();
    }

    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {
    	
		Holder holder;
		
//		if (childPosition >= 0 && childPosition < itemList.size()){
			
			final int id = groupList.get(groupPosition).getId();
			
			if (convertView==null) {
				convertView = inflater.inflate(R.layout.search_list_item, null);
				holder = new Holder();   
				holder.nameTextView = (TextView)convertView.findViewById(R.id.name_textview);
				holder.positionTextView = (TextView)convertView.findViewById(R.id.position_textview);
				holder.mobileTextView = (TextView)convertView.findViewById(R.id.mobile_textview);
				holder.phoneTextView = (TextView)convertView.findViewById(R.id.phone_textview);
				holder.selectCheckBox = (CheckBox) convertView.findViewById(R.id.chkbox);
				
				convertView.setTag(holder);
				holder.nameTextView.setTag(itemList.get(id).get(childPosition));
				
			}else {
				holder = (Holder) convertView.getTag();
				holder.nameTextView.setTag(itemList.get(id).get(childPosition));
			}
			
			holder.nameTextView.setText(itemList.get(id).get(childPosition).getName());
			holder.positionTextView.setText(itemList.get(id).get(childPosition).getPositionName());
			holder.mobileTextView.setText(itemList.get(id).get(childPosition).getMobile());
			holder.phoneTextView.setText(itemList.get(id).get(childPosition).getPhone());
			holder.selectCheckBox.setChecked(itemList.get(id).get(childPosition).isSelected());
			
			holder.selectCheckBox.setVisibility(View.VISIBLE);
			holder.selectCheckBox.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					itemList.get(id).get(childPosition).setSelected(((CheckBox)v).isChecked());
				}
			}); 
//		}
		
        return convertView;
    }

    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    public int getGroupCount() {
        return groupList.size();
    }

    public long getGroupId(int groupPosition) {
        return groupList.get(groupPosition).getId();
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    	
    	Holder groupHolder = null;
    	
		if (convertView == null) {
			
			groupHolder = new Holder();
			convertView = inflater.inflate(R.layout.group, null);
			groupHolder.nameTextView = (TextView) convertView.findViewById(R.id.group);
			groupHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
			groupHolder.selectCheckBox = (CheckBox) convertView.findViewById(R.id.chkbox);
			
			convertView.setTag(groupHolder);
			
		} else {
			groupHolder = (Holder) convertView.getTag();
		}

		groupHolder.nameTextView.setText(groupList.get(groupPosition).getName());
		
		if (isExpanded){   // ture is Expanded or false is not isExpanded
			groupHolder.imageView.setImageResource(R.drawable.expanded);
		}else{
			groupHolder.imageView.setImageResource(R.drawable.collapse);
		}
		
		return convertView;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean hasStableIds() {
        return true;
    }

}

